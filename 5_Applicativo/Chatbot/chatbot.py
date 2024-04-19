import os
from dotenv import load_dotenv
from langchain_community.utilities import SQLDatabase
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnablePassthrough
from langchain_core.prompts import ChatPromptTemplate

class Chatbot:
    def __init__(self):
        load_dotenv()

        self.DB_USER = os.getenv('DB_USER')
        self.DB_PASSWORD = os.getenv('DB_PASSWORD')
        uri = f'mysql+mysqlconnector://{self.DB_USER}:{self.DB_PASSWORD}@localhost:3306/NebulaWatches'
        
        self.db = SQLDatabase.from_uri(uri)
        self.llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0.5)

        self.define_sql_chain()
        self.define_full_chain()

    def get_schema(self, db):
        schema = self.db.get_table_info()
        return schema
    
    def run_query(self, query):
        return self.db.run(query)

    def define_sql_chain(self):
        template = """You are a MySQL expert with access to a database containing information about watches, clients, storage, purchases, and other related data. Based on the table schema below, write a SQL query that would answer the user's question.

        You do not have access to users table.
        
        Some data is linked to users, like clients, teams, storage, sold storage, etc. in this situation you must query for data where the user id is 1

        DO NOT make any DML statements (INSERT, UPDATE, DELETE, DROP etc.) to the database.
        If the question does not seem related to the database, just return "SELECT NULL;"

        Only use the given tools. Only use the information returned by the tools to construct your final answer.

        Never query for all columns from a table, never query image, BLOB columns.

        Prefer getting with a query names instead of ids, or something that a human understands, except for the watch reference, always give the watch reference.

        {schema}

        Question: {question}
        SQL Query:"""
        prompt = ChatPromptTemplate.from_template(template)

        self.sql_chain = (
            RunnablePassthrough.assign(schema=self.get_schema)
            | prompt
            | self.llm.bind(stop=["\nSQLResult:"])
            | StrOutputParser()
        )
    
    def define_full_chain(self):
        template = """Based on the table schema below, question, sql query, and sql response, write a natural language response.

        If you don't have enough information in the database to answer the question, you can provide suggestions or additional context as a watch expert, but make it clear that the information is not coming from the database.

        If the question is not related to watches, clients, storage, purchases, or any other data in the database, reply with "As a watch expert, this question goes beyond my knowledge. I would suggest consulting [relevant resource or expert]."

        When suggesting a watch, always provide the watch reference. Format the response in a way that is easily readable for humans.

        {schema}

        Question: {question}
        SQL Query: {query}
        SQL Response: {response}"""
        prompt_response = ChatPromptTemplate.from_template(template)

        self.full_chain = (
            RunnablePassthrough.assign(query=self.sql_chain).assign(
                schema=self.get_schema,
                response=lambda vars: self.run_query(vars["query"]),
            )
            | prompt_response
            | self.llm
        )

    def ask_question(self, question):
        response = self.full_chain.invoke({"question": question})
        return response.content
    

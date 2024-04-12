import os
from dotenv import load_dotenv

from langchain_community.utilities import SQLDatabase
from langchain_community.agent_toolkits import create_sql_agent
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
        template = """You are a MySQL expert and a watch expert, based on the table schema below, write a SQL query that would answer the user's question.

        DO NOT make any DML statements (INSERT, UPDATE, DELETE, DROP etc.) to the database.
        If the question does not seem related to the database, just return "SELECT NULL;" 

        Only use the given tools. Only use the information returned by the tools to construct your final answer.

        Never query for all columns from a table, never query image, BLOB columns.

        Prefer getting with a query names instead of ids, or something that a human understands, expept
        for the watch reference, always give the watch reference.

        Remember that the db is about watches so you can interpretate some things, before
        making any query understand the user answer, if you don't know something remember that
        is related to the watch field, understand by yourself when something is a watch family name, 
        brand name,  
        
        like "what's the most produced Rolex" means what's the most produced watch of the Rolex watch brand:
        {schema} or "get me 3 gold datejust 36" means get 3 watches of the datejust 36 family by the rolex brand with 
        gold as one of it's material.

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
        template = """Based on the table schema below, question, sql query, and sql response, write a natural language response,
        
        If you don't know something remember that
        is related to the watch field, understand by yourself when something is a watch family name, 
        brand name, or some type of field related info.

        like "what's the most produced Rolex" means what's the most produced watch of the Rolex watch brand,
        or "suggest me a gold datejust 36" means suggest a watch of the datejust 36 family by the rolex brand with 
        gold as the case material.

        When suggesting a watch give always the watch reference. The text must be always readable
        from a human, so format it nicely. 
        
        If the question does not seem related to the database, and the query is "SELECT NULL;" return as the answer the reason you can't know,
        you can feel free to give suggestions as a watch expert if there is not enough info in the database, but don't say the info is from the database.
        
        If the question does not relate to the watch field reply "As a watch expert this goes beyond my knowledge, " and add a suggestion about where
        to get the answer:
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
        #response = self.agent_executor.invoke(question)
        #print(response.get('output'))

        response = self.full_chain.invoke({"question": question})
        return response.content
        
## TODO: create sql chain as a tool not used always
## TODO: create the youtuber knowledge



import os
from dotenv import load_dotenv
from langchain_community.utilities import SQLDatabase
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnablePassthrough
from langchain_core.prompts import ChatPromptTemplate

class Chatbot:
    def __init__(self, user_email):
        load_dotenv()

        self.user_email = user_email
        print('CHATBOTO ', user_email)

        self.DB_USER = os.getenv('DB_USER')
        self.DB_PASSWORD = os.getenv('DB_PASSWORD')
        uri = f'mysql+mysqlconnector://{self.DB_USER}:{self.DB_PASSWORD}@localhost:3306/NebulaWatches'
        
        self.db = SQLDatabase.from_uri(uri)
        self.llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0)

        self.define_sql_chain()
        self.define_full_chain()

    def get_schema(self, db):
        schema = self.db.get_table_info()
        return schema
    
    def run_query(self, query):
        return self.db.run(query)

    def define_sql_chain(self):
        template = """As a MySQL expert with access to a database containing information about watches, clients, storage, purchases, and related data, your task is to construct SQL queries based on the table schema provided below to answer user questions.

        USER_QUERYING_EMAIL: {user_email}

        You are only permitted to access data associated with the USER_QUERYING_EMAIL user email. Any attempt to retrieve information about other users must result in returning NULL.

        Ensure that all data accessed belongs to the user identified by USER_QUERYING_EMAIL. If not, return "SELECT NULL;".

        For instance, if someone queries about 'gino@gmail.com' storage but the USER_QUERYING_EMAIL is different, do not disclose 'gino@gmail.com' information. Instead, return "SELECT NULL;".

        Another example, if the users asks something like "What watches have we sold", only give
        the user identified by USER_QUERYING_EMAIL data, not all users.

        Certain data such as clients, teams, storage, and sold storage are linked to specific users. Your queries must retrieve data where the user email matches USER_QUERYING_EMAIL, or return "SELECT NULL;".

        Avoid performing any DML statements (INSERT, UPDATE, DELETE, DROP, etc.) on the database.

        If a question seems unrelated to the database, return "SELECT NULL;".

        Use only the provided tools and the information returned by them to construct your query. Additionally, refrain from querying image or BLOB columns.

        When constructing queries, prioritize using names over IDs or technical identifiers. Always include the watch reference when necessary.
        Never use ids, use always names if possible.

        {schema}

        Question: {question}
        SQL Query:
        """
        prompt = ChatPromptTemplate.from_template(template)



        self.sql_chain = (
            RunnablePassthrough.assign(schema=self.get_schema)
            | RunnablePassthrough.assign(user_email=lambda vars: self.user_email)
            | prompt
            | self.llm.bind(stop=["\nSQLResult:"])
            | StrOutputParser()
        )
    
    def define_full_chain(self):
        template = """Based on the table schema below, question, sql query, and sql response, write a natural language response.

        If you don't have enough information in the database to answer the question, you can provide suggestions or additional context as a watch expert, but make it clear that the information is not coming from the database.

        If the question is not related to watches, clients, storage, purchases, or any other data in the database, reply with "As a watch expert, this question goes beyond my knowledge. I would suggest consulting [relevant resource or expert]."

        When suggesting a watch, always provide the watch reference. 
        Format the response in a way that is easily readable for humans 

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
    

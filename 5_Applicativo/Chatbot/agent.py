import os
from dotenv import load_dotenv

from langchain_community.utilities import SQLDatabase
from langchain_community.agent_toolkits import create_sql_agent
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnablePassthrough
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

from langchain.agents.format_scratchpad.openai_tools import (
    format_to_openai_tool_messages,
)
from langchain.agents.output_parsers.openai_tools import OpenAIToolsAgentOutputParser

from langchain.agents import AgentExecutor

from tools import *

class Agent:
    def __init__(self) -> None:
        load_dotenv()

        self.DB_USER = os.getenv('DB_USER')
        self.DB_PASSWORD = os.getenv('DB_PASSWORD')
        uri = f'mysql+mysqlconnector://{self.DB_USER}:{self.DB_PASSWORD}@localhost:3306/NebulaWatches'
        
        self.db = SQLDatabase.from_uri(uri)
        self.llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0.5)

        self.define_tools()
        self.define_prompt()
        self.define_agent()

    def define_tools(self):
        self.tools = [GetDbData, GetSuggestion]
        self.llm_with_tools = self.llm.bind_tools(self.tools)

    def define_prompt(self):
        self.prompt = ChatPromptTemplate.from_messages(
            [
                (
                    "system",
                    "You are very powerful assistant, but don't know current events",
                ),
                ("user", "{input}"),
                MessagesPlaceholder(variable_name="agent_scratchpad"),
            ]
        )

    def define_agent(self):
        self.agent = (
            {
                "input": lambda x: x["input"],
                "agent_scratchpad": lambda x: format_to_openai_tool_messages(
                    x["intermediate_steps"]
                ),
            }
            | self.prompt
            | self.llm_with_tools
            | OpenAIToolsAgentOutputParser()
        )

        self.agent_executor = AgentExecutor(agent=self.agent, tools=self.tools, verbose=True)

    def ask_question(self, question):
        output = self.agent_executor.stream({"input": question})
        return output.content
    
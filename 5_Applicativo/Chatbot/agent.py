
from chatbot import Chatbot
from langchain_openai import ChatOpenAI
from langchain.agents import tool
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain.agents.format_scratchpad.openai_tools import (
    format_to_openai_tool_messages,
)
from langchain.agents.output_parsers.openai_tools import OpenAIToolsAgentOutputParser
from langchain.agents import AgentExecutor
from dotenv import load_dotenv

load_dotenv()

@tool
def watch_database_tool(query: str) -> str:
    """A tool to query a MySQL database about personal info, user, watches, clients, storage, purchases, prices and other related information, and get natural language responses. Not used for suggestions"""

    chatbot = Chatbot()
    chatbot_answer = chatbot.ask_question(query)
    return chatbot_answer

@tool
def ask_chat_gpt(query: str) -> str:
    """A function to query ChatGPT for suggestions about watches, lifestyle, etc."""

    llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0.5)
    response = llm.invoke(query)
    return response

class Agent:
    def __init__(self):
        self.initialize_agent()

    def initialize_agent(self):
        llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0)
        tools = [watch_database_tool, ask_chat_gpt]
        prompt = ChatPromptTemplate.from_messages(
            [
                (
                    "system",
                    "You are very powerful assistant, called Gio, but don't know current events",
                ),
                ("user", "{input}"),
                MessagesPlaceholder(variable_name="agent_scratchpad"),
            ]
        )
        llm_with_tools = llm.bind_tools(tools)

        agent = (
            {
                "input": lambda x: x["input"],
                "agent_scratchpad": lambda x: format_to_openai_tool_messages(
                    x["intermediate_steps"]
                ),
            }
            | prompt
            | llm_with_tools
            | OpenAIToolsAgentOutputParser()
        )

        self.agent_executor = AgentExecutor(agent=agent, tools=tools, verbose=True)

    def ask(self, query):
        return list(self.agent_executor.stream({"input": query}))[-1]['output']

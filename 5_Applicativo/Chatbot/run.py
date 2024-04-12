import streamlit as st
#from chatbot import Chatbot
from agent import Agent

chatboto = Agent()


st.title('Watch chatbot')
prompt = st.chat_input(placeholder="Write a message", key=None, max_chars=None, disabled=False, on_submit=None, args=None, kwargs=None)

if prompt:
    message = st.chat_message("Watch expert")

    message.write(chatboto.ask_question(prompt))
    


# while True:
#     question = input('Ask a question: ')
#     chatboto.ask_question(question)
#     print()
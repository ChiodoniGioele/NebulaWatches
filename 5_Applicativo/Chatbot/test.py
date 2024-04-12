import os
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI

load_dotenv('.env')

llm = ChatOpenAI(model='gpt-4', temperature=0)
transcription_content = ''

with open('prova.txt', 'r+') as f:
    transcription_content = f.read()

response = llm.invoke(f'''
    You are a watch expert, now i will give you an auto generated transcription of a youtube video,
    watch names, brands, ecc. are not given correctly understand them by yourself wich could be corrected, 
    find those mistakes and give me back the full text as the original of the transcription input but with
    the small changes applied.
    Also make sure to add syntax and grammar.
    Some times names of brands like Tudor are reported like tutor or other english words, understand when
    it's the case.
    Be sure that words near correspond, like watch brand name and family name, like Citizen TSUYOSA is not a
    real thing, should be .
    The returned text should be near the same length as the input text, if the text input is about
    30 min spoken the text you generate should be the same.

    Input text:
    {transcription_content}
''').content

print(response)

with open('response.txt', 'w+') as f:
    f.write(response)

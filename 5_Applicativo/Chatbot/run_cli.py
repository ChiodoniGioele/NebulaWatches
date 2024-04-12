from agent import Agent

chatboto = Agent()

while True:
    question = input('Ask question: ')
    print(chatboto.ask_question(question))
    print()
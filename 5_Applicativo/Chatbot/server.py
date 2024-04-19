from fastapi import FastAPI, Header, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import requests
from functools import wraps
from agent import Agent  

AUTH_SERVER = 'http://localhost:64321'
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["GET", "POST"],
    allow_headers=["*"],
)

def token_valid_decorator(func):
    @wraps(func)
    async def wrapper(*args, **kwargs):
        jwt = kwargs.get("jwt")  
        r_url = AUTH_SERVER + '/auth/isTokenValid'
        params = {'jwt': jwt}
        r = requests.get(r_url, params=params)
        print('|' + r.text + '|')
        if not r.text:
            print('ERROR')
            raise HTTPException(status_code=401, detail='JWT not valid')
        
        return await func(*args, **kwargs)
    return wrapper

agent_map = {}

@app.get('/ask_bot')
@token_valid_decorator
async def ask_bot(jwt: str, query: str):
    if jwt not in agent_map:
        agent_map[jwt] = Agent()
    
    result = agent_map[jwt].ask(query)
    return {'result': result}

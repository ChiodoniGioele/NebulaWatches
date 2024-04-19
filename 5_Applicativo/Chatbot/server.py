from fastapi import FastAPI, Header, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import requests
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

async def authentication_middleware(request, call_next):
    token = request.headers.get("jwt")

    if not is_token_valid(token):
        print(is_token_valid(token))
        #raise HTTPException(status_code=401, detail="Invalid JWT token")

    response = await call_next(request)
    return response

def is_token_valid(jwt):
    r_url = f"{AUTH_SERVER}/auth/isTokenValid"
    params = {'jwt': jwt}
    r = requests.get(r_url, params=params)
    
    return r.text

agent_map = {}

@app.get('/ask_bot')
async def ask_bot(query: str, jwt: str = Header(...)):
    email = is_token_valid(jwt)
    if jwt not in agent_map:
        agent_map[jwt] = Agent(email)
    
    result = agent_map[jwt].ask(query)
    return {'result': result}

app.middleware("http")(authentication_middleware)

from fastapi import FastAPI, Header, HTTPException
import requests
from functools import wraps


AUTH_SERVER = "http://localhost:64321"  # replace with your auth server address
app = FastAPI()

def token_valid_decorator(func):
    @wraps(func)
    async def wrapper(*args, **kwargs):
        jwt = kwargs.get("jwt")  # assuming the JWT is passed in as a keyword argument
        r_url = AUTH_SERVER + '/auth/isTokenValid'
        params = {'jwt': jwt}
        r = requests.get(r_url, params=params)
        print('|' + r.text + '|')
        if not r.text:
            print('ERROR')
            raise HTTPException(status_code=401, detail='JWT not valid')
        
        return await func(*args, **kwargs)
    return wrapper


@app.get("/some_protected_route")
@token_valid_decorator
async def some_protected_route(jwt: str, sus: str):
    # your protected route logic here
    return {"message": "Ciao " + sus}
from langchain.tools import BaseTool

# Da fare quando user storage, favourite watches, clients, team members sono fatti
class GetDbData(BaseTool):
    name = 'Get application database data'
    description = '''Returns informations about the user storage, favourite watches,
    clients, team members, ecc.'''

    def _run(self, query:str) -> str:
        return '["banana", "stawberry", "apple"]'
    

class GetSuggestion(BaseTool):
    name = 'Get suggestion from watch expert'
    description = '''Returns tailored suggestion from a watch expert'''

    def _run(self, query:str) -> str:
        return 'Nico Leonard suggests a GOD TIER CASIO'
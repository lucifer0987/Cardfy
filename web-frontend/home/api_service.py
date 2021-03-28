
import requests
from home import config

def loginUser(email,password):
    url = config.url + config.login_user
    print('log: posting to login ')
    body = {"email": email,"password":password}
    response = requests.post(url,json=body)
    if response.status_code == 200 :
        response = response.json()
        return response
    return response.status_code
   

def createUser(name,email,password,username,profile_image):
    url = config.url + config.create_user
    print('log: posting to create user... ')
    body = {"name":name,"email": email,"password":password,"username":username,"profile_image":profile_image}
    response = requests.post(url,json=body)
    if response.status_code == 200 :
        response = response.json()
        return response
    return response.status_code



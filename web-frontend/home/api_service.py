
import requests
from home import config,custom_models

def loginUser(email,password):
    url = config.url + config.login_user
    print('log: posting to login ')
    body = {"email": email,"password":password}
    response = requests.post(url,json=body)
    if response.status_code == 200 :
        response = response.json()
        print(response)
        return response
    return response.status_code
   

def createUser(name,email,password,username):
    url = config.url + config.create_user
    print('log: posting to create user... ')
    body = {"name":name,"email": email,"password":password,"username":username,"profile_image":"https://media.istockphoto.com/vectors/default-profile-picture-avatar-photo-placeholder-vector-illustration-vector-id1223671392?k=6&m=1223671392&s=612x612&w=0&h=NGxdexflb9EyQchqjQP0m6wYucJBYLfu46KCLNMHZYM="}
    response = requests.post(url,json=body)
    if response.status_code == 200 :
        response = response.json()
        return response
    return response.status_code


def getCard(token):
    url = config.url + config.user_card
    print('log: fetching card')
    header = {"x-auth-token":token}
    response = requests.get(url,headers =header)
    if response.status_code == 200 :
        response = response.json()
        print(response['name'])
        card = custom_models.Card(
            response['name'],
            response['email'],
            response['username'],
            response['profile_image'],
            response['rating'],
            response['isVerified'],

            response['facebook'],
            response['instagram'],
            response['twitter'],
            response['linkedin'],
            response['whatsapp'],
            response['gmail'],

            response['discord'],
            response['slack'],
            response['skype'],
            response['youtube'],
            response['intro'],
            response['short_desc'],
            response['long_desc'],

            response['address'],
            response['city'],
            response['state'],
            response['country'],
            response['address_slug'],

            response['upi'],
            response['gpay'],
            response['ppay'],
            response['paytm'],
            response['fund_desc']
        )

        card = make_default_card(card)
        return card
    else:
        return None

def getShareCard(username):
    url = config.url + "card/"+username
    print(url)

    response = requests.get(url)
    if response.status_code == 200 :
        response = response.json()
        print(response['name'])
        card = custom_models.Card(
            response['name'],
            response['email'],
            response['username'],
            response['profile_image'],
            response['rating'],
            response['isVerified'],

            response['facebook'],
            response['instagram'],
            response['twitter'],
            response['linkedin'],
            response['whatsapp'],
            response['gmail'],

            response['discord'],
            response['slack'],
            response['skype'],
            response['youtube'],
            response['intro'],
            response['short_desc'],
            response['long_desc'],

            response['address'],
            response['city'],
            response['state'],
            response['country'],
            response['address_slug'],

            response['upi'],
            response['gpay'],
            response['ppay'],
            response['paytm'],
            response['fund_desc']
        )

        card = make_default_card(card)
        return card
    else:
        return None


def updateCard(card,token):
    url = config.url + config.update_card
    header = {"x-auth-token":token}
    body = {
    "rating": card.rating,
    "isVerified": bool(card.isVerified),
    "facebook": card.facebook,
    "instagram": card.insagram,
    "twitter": card.twitter,
    "linkedin": card.linkedin,
    "whatsapp": card.whatsapp,
    "gmail": card.gmail,
    "discord": card.discord,
    "slack": card.slack,
    "skype": card.skype,
    "youtube": card.youtube,
    "intro": card.intro,
    "short_desc": card.short_desc,
    "long_desc": card.long_desc,
    "address": card.address,
    "city": card.city,
    "state": card.state,
    "country": card.country,
    "address_slug": card.address_slug,
    "upi": card.upi,
    "gpay": card.gpay,
    "ppay": card.ppay,
    "paytm": card.paytm,
    "fund_desc": card.fund_desc,
    "name": card.name,
    "email": card.email,
    "username": card.username,
    "profile_image": card.profile_image
    }
 
    print(body)
    response = requests.put(url,json=body,headers =header)
    print(response.status_code)
    if response.status_code == 200 :
        # print("updates")
        return True
    else:
        # print('update failed')
        return False

def make_default_card(card):
   
    if card.intro == "":
        card.intro = "Your intro / company / bio" 
    if card.short_desc == "":
        card.short_desc = "Tell something about yourself in about 30 words"

    if card.long_desc == "":
        card.long_desc = "Describe yourself in about 50 words"

    if card.address == "":
        card.address = "Your meetup / buisness / personal address eg. Main Market Market Ln Block E, Hauz Khas New Delhi, Delhi 110016"

    if card.address_slug == "":
        card.address_slug = "india"

    if card.fund_desc == "":
        card.fund_desc="Tell something about your motive if your are raising any funds or payment in about 30 words "

    return card
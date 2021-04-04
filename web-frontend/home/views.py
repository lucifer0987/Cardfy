from django.shortcuts import render
from django.shortcuts import redirect,render,HttpResponse
from home import api_service ,firebase_services,custom_models
from django.contrib import messages,sessions
from django.template import RequestContext
from django.conf import settings
from django.core.mail import send_mail

from random import randint

# Create your views here.


def index(request):
    context = {
        "pageName":"index"
    }
    if 'username' in request.COOKIES and "name" in request.COOKIES and "email" in request.COOKIES and "profile_image" in request.COOKIES and "token" in request.COOKIES :
        context['username'] = request.COOKIES['username']
        context['isLogged'] = True
    else:
        context['isLogged'] = False

    
    return render(request,'main.html',context)

def login(request):
    password = request.POST.get("password")
    email = request.POST.get("email")
    if 'username' in request.COOKIES and "name" in request.COOKIES and "email" in request.COOKIES and "profile_image" in request.COOKIES and "token" in request.COOKIES :

        username = request.COOKIES['username']
        # print('user is ',user,type(user))
        return redirect('/dashboard/'+ username)
    if email and password :
        email = email.strip()
        password = password.strip()
        try:
            response =  api_service.loginUser(email,password)
            if response == 400:
                messages.warning(request,'Invalid email or password !')
            elif response == 404:
                messages.warning(request,'Email not registered ! Please register yourself')
            elif response == 403:
                messages.warning(request,'Wrong password ! ')
            else:
                isLogged = True
                user = custom_models.User(response['name'],response['email'],response['username'],response['profile_image'],response['token'])
                response = redirect('/dashboard/'+ user.username)  
                response.set_cookie('username', user.username)    
                response.set_cookie('name', user.name)    
                response.set_cookie('email', user.email)    
                response.set_cookie('profile_image', user.profile_image)  
                response.set_cookie('token', user.token)    


                return response   

        except Exception as e:
            print('exception is',e)
            messages.warning(request,'Invalid request ! please try again ')
           

    return render(request,'login.html')


def register(request):
    if request.method == 'POST':

        name = request.POST.get("name")
        password = request.POST.get("password")
        email = request.POST.get("email")
        username = request.POST.get("username")
        # files = request.FILES['files']
        try:
            # profile_image = firebase_services.uploadProfileImage(request,files,username)
            # messages.info(request,'Updated Profile Image Successfully !')
            if email and password and name and username :

                name = name.strip()
                email = email.strip()
                password = password.strip()
                username = username.strip()

                if len(password) < 8 :
                    messages.info(request,'Password must be atleast 8 character long !')
                else:
                    try:
                        response =  api_service.createUser(name,email,password,username)
                        if response == 400:
                            messages.warning(request,'Something went wrong ! Please try again later ')
                        elif response == 401:
                            messages.warning(request,'User already registered ! Please login ')
                        elif response == 405 :
                            messages.info(request,"Username already taken ! try again ")
                        else: 
                            messages.success(request,'User registered successfully ! please login into your dashboard ')
                            return redirect('/login')

                    except Exception as e:
                        print(e)
                        messages.warning(request,'Invalid request ! please try again ')
            


        except Exception as e:
            messages.info(request,'Uploading email failed ! Please try again later ')
            print(e)

        


    return render(request,'register.html')


def dashboard(request,username):
    
     if 'username' in request.COOKIES and "name" in request.COOKIES and "email" in request.COOKIES and "profile_image" in request.COOKIES and "token" in request.COOKIES :
          username = request.COOKIES['username']
          name = request.COOKIES['name']
          email = request.COOKIES['email']
          profile_image = request.COOKIES['profile_image']
          token = request.COOKIES['token']
          user = custom_models.User(name,email,username,profile_image,token)
          context={"user":user}
          card = api_service.getCard(token)
          card.isVerified = str(card.isVerified)
          context['card'] = card
          render(request,'base_dashboard.html',context=context)

          response = render(request,'dash.html',context=context)
          response.set_cookie('username',card.username)    
          response.set_cookie('name', card.name)    
          response.set_cookie('email', card.email)    
          response.set_cookie('rating', card.rating)  
          response.set_cookie('isVerified', card.isVerified)    
          response.set_cookie('facebook', card.facebook)    
          response.set_cookie('instagram', card.insagram)    
          response.set_cookie('twitter', card.twitter)    
          response.set_cookie('linkedin', card.linkedin)  
          response.set_cookie('whatsapp', card.whatsapp)  
          response.set_cookie('gmail', card.email)    
          response.set_cookie('discord', card.discord)    
          response.set_cookie('slack', card.slack)    
          response.set_cookie('skype', card.skype)  
          response.set_cookie('intro',card.intro)  
          response.set_cookie('youtube', card.youtube)    
          response.set_cookie('short_desc', card.short_desc)    
          response.set_cookie('long_desc', card.long_desc)    
          response.set_cookie('address', card.address)  
          response.set_cookie('city', card.city)  
          response.set_cookie('state', card.state)      
          response.set_cookie('country', card.country)    
          response.set_cookie('address_slug', card.address_slug)   
          response.set_cookie('upi', card.upi)    
          response.set_cookie('gpay', card.gpay)  
          response.set_cookie('ppay', card.ppay)  
          response.set_cookie('paytm', card.paytm)    
          response.set_cookie('fund_desc', card.fund_desc)    
          return response
     return redirect('/')

def logout(request):
    response = redirect('/')
    response.delete_cookie('username')
    response.delete_cookie('name')
    response.delete_cookie('email')
    response.delete_cookie('profile_image')
    response.delete_cookie('token')

    return response

def showSections(request,username,section):
       if 'username' in request.COOKIES and "name" in request.COOKIES and "email" in request.COOKIES and "profile_image" in request.COOKIES and "token" in request.COOKIES and "short_desc" in request.COOKIES :       
            username = request.COOKIES['username']
            name = request.COOKIES['name']
            email = request.COOKIES['email']
            profile_image =  request.COOKIES['profile_image']
            token = request.COOKIES['token']
            user = custom_models.User(name,email,username,profile_image,token)
            context={"user":user,section:section}
            card = custom_models.Card(
            request.COOKIES['name'],
            request.COOKIES['email'],
            request.COOKIES['username'],
            request.COOKIES['profile_image'],
            request.COOKIES['rating'],
            request.COOKIES['isVerified'],

            request.COOKIES['facebook'],
            request.COOKIES['instagram'],
            request.COOKIES['twitter'],
            request.COOKIES['linkedin'],
            request.COOKIES['whatsapp'],
            request.COOKIES['gmail'],

            request.COOKIES['discord'],
            request.COOKIES['slack'],
            request.COOKIES['skype'],
            request.COOKIES['youtube'],
            request.COOKIES['intro'],
            request.COOKIES['short_desc'],
            request.COOKIES['long_desc'],

            request.COOKIES['address'],
            request.COOKIES['city'],
            request.COOKIES['state'],
            request.COOKIES['country'],
            request.COOKIES['address_slug'],

            request.COOKIES['upi'],
            request.COOKIES['gpay'],
            request.COOKIES['ppay'],
            request.COOKIES['paytm'],
            request.COOKIES['fund_desc']
        )
            card.isVerified = str(card.isVerified)
            context['card'] = card
            response = render(request,'dash.html',context=context)
            
            
            
            if request.method == "POST":
                
                if section == "general":
                    print('post to genereal')
                    
                    name = request.POST.get("name")
                    print(name,"came to")
                    response.set_cookie('name',name)
                    card.name = name
                
                elif section == "social":
                    facebook = request.POST.get("facebook")
                    instagram = request.POST.get("instagram")
                    twitter  = request.POST.get("twitter")
                    linkedin = request.POST.get("linkedin")
                    whatsapp = request.POST.get("whatsapp")
                    gmail    = request.POST.get("gmail")

                    response.set_cookie('facebook', facebook)    
                    response.set_cookie('instagram', instagram)    
                    response.set_cookie('twitter', twitter)    
                    response.set_cookie('linkedin', linkedin)  
                    response.set_cookie('whatsapp', whatsapp)  
                    response.set_cookie('gmail', email) 
                
                elif section == "address":

                    address = request.POST.get("address")
                    city = request.POST.get("city")
                    state=request.POST.get("state")
                    country = request.POST.get("country")
                    address_slug = request.POST.get("address_slug")

                    response.set_cookie('address', address)  
                    response.set_cookie('city', city)  
                    response.set_cookie('state', state)      
                    response.set_cookie('country', country)    
                    response.set_cookie('address_slug', address_slug)

                elif section == "storyline":

                    intro = request.POST.get("intro")
                    short_desc = request.POST.get("short_desc")
                    long_desc = request.POST.get("long_desc")
                    fund_desc = request.POST.get("fund_desc")

                    response.set_cookie('short_desc', short_desc)    
                    response.set_cookie('long_desc',long_desc) 
                    response.set_cookie('intro',card.intro)
                    response.set_cookie('fund_desc', card.fund_desc) 

                elif section == "payment":

                     upi = request.POST.get("upi")
                     gpay = request.POST.get("gpay")
                     ppay = request.POST.get("ppay")
                     paytm = request.POST.get("paytm")
                     response.set_cookie('upi',upi)  
                     response.set_cookie('gpay',gpay)  
                     response.set_cookie('ppay',ppay)  
                     response.set_cookie('paytm',paytm)

                elif section == "communities":

                     discord = request.POST.get("discord")
                     slack = request.POST.get("slack")
                     skype = request.POST.get("skype")
                     youtube = request.POST.get("youtube")
                     response.set_cookie('discord', discord)    
                     response.set_cookie('slack', slack) 
                     response.set_cookie('youtube', youtube) 
                     response.set_cookie('skype', skype) 

                elif section == "verification":
                    try:

                        otpVer = request.POST.get("otp")
                        if otpVer :
                            print("got otp")
                            print(otpVer)
                            optSent = request.COOKIES['otp']
                            print('sent is',optSent)
                            if otpVer == optSent:
                                print("verified")
                                card.isVerified = True
                                result = api_service.updateCard(card,token)
                                if result :
                                    messages.success(request,"Wohoo! You are now verified creator !")
                                    
                                else:
                                    messages.info(request,"Something failed ! Please try again later")


                                context['otp'] ="False"
                                response = render(request,'dash.html',context=context)
                                return response
                            else:
                                messages.warning(request,"Wrong OTP Please try again !")
                                context['otp'] = "True"
                                response = render(request,'dash.html',context=context)
                                return response

                        context['otp'] = "True"
                        response = render(request,'dash.html',context=context)
                      
                        print(email)
                        otp = randint(100000, 999999) 
                        otp = str(otp)
                        response.set_cookie('otp', otp)    
                        subject = 'Email Verification Cardfy '
                        message = f'Hi {user.username}, thank you for registering in cardfy . We just need to verify your email id for providing you a verified tag \n Your One Time Passcode is : {otp}'
                        email_from = settings.EMAIL_HOST_USER
                        recipient_list = [user.email,"ishaan.dwivedi99@gmail.com" ]
                        send_mail( subject, message, email_from, recipient_list )
                        
                        return response
                    except Exception as e:
                        print('caught exceptopn',e)



            context['card'] = card

            return response



def updateCookies(request,username,section):
    if 'username' in request.COOKIES and "name" in request.COOKIES and "email" in request.COOKIES and "profile_image" in request.COOKIES and "token" in request.COOKIES and "short_desc" in request.COOKIES :       
            username = request.COOKIES['username']
            name = request.COOKIES['name']
            email = request.COOKIES['email']
            profile_image =  request.COOKIES['profile_image']
            token = request.COOKIES['token']

            user = custom_models.User(name,email,username,profile_image,token)
            card = custom_models.Card(
            request.COOKIES['name'],
            request.COOKIES['email'],
            request.COOKIES['username'],
            request.COOKIES['profile_image'],
            request.COOKIES['rating'],
            request.COOKIES['isVerified'],

            request.COOKIES['facebook'],
            request.COOKIES['instagram'],
            request.COOKIES['twitter'],
            request.COOKIES['linkedin'],
            request.COOKIES['whatsapp'],
            request.COOKIES['gmail'],

            request.COOKIES['discord'],
            request.COOKIES['slack'],
            request.COOKIES['skype'],
            request.COOKIES['youtube'],
            request.COOKIES['intro'],
            request.COOKIES['short_desc'],
            request.COOKIES['long_desc'],

            request.COOKIES['address'],
            request.COOKIES['city'],
            request.COOKIES['state'],
            request.COOKIES['country'],
            request.COOKIES['address_slug'],

            request.COOKIES['upi'],
            request.COOKIES['gpay'],
            request.COOKIES['ppay'],
            request.COOKIES['paytm'],
            request.COOKIES['fund_desc']
        )




            response = redirect('/dashboard/'+user.username+"/"+section)
            if request.method == "POST":
                
                if section == "general":
                    files=None 
                    name = request.POST.get("name")
                    if request.method == "POST" and 'files' in request.FILES :
                        files = request.FILES['files']
                        print(files)
                    response.set_cookie('name',name)
                    card.name = name
                    try:
                        if files !=None:
                            profile_image = firebase_services.uploadProfileImage(request,files,user.username)
                            messages.info(request,'Updated Profile Image Successfully !')
                            response.set_cookie('profile_image',profile_image)
                            print(profile_image)
                            card.profile_image = profile_image
                        else:
                            messages.info(request,'Please choose profile image !')


                    except Exception as e:
                            print(e)
                elif section == "social":
                    facebook = request.POST.get("facebook")
                    insagram = request.POST.get("instagram")
                    twitter  = request.POST.get("twitter")
                    linkedin = request.POST.get("linkedin")
                    whatsapp = request.POST.get("whatsapp")
                    gmail    = request.POST.get("gmail")

                    response.set_cookie('facebook', facebook)    
                    response.set_cookie('instagram', insagram)    
                    response.set_cookie('twitter', twitter)    
                    response.set_cookie('linkedin', linkedin)  
                    response.set_cookie('whatsapp', whatsapp)  
                    response.set_cookie('gmail', email) 

                    card.facebook = facebook
                    card.insagram = insagram
                    card.twitter = twitter
                    card.linkedin = linkedin
                    card.whatsapp = whatsapp
                    card.gmail = gmail

                
                elif section == "address":

                    address = request.POST.get("address")
                    city = request.POST.get("city")
                    state=request.POST.get("state")
                    country = request.POST.get("country")
                    address_slug = request.POST.get("address_slug")

                    response.set_cookie('address', address)  
                    response.set_cookie('city', city)  
                    response.set_cookie('state', state)      
                    response.set_cookie('country', country)    
                    response.set_cookie('address_slug', address_slug)

                    card.address = address
                    card.city =city
                    card.state = state
                    card.country = country
                    card.address_slug = address_slug


                elif section == "storyline":

                    intro = request.POST.get("intro")
                    short_desc = request.POST.get("short_desc")
                    long_desc = request.POST.get("long_desc")
                    fund_desc = request.POST.get("fund_desc")

                    response.set_cookie('short_desc', short_desc)    
                    response.set_cookie('long_desc',long_desc) 
                    response.set_cookie('intro',card.intro)
                    response.set_cookie('fund_desc', card.fund_desc) 


                    card.short_desc = short_desc
                    card.long_desc =long_desc
                    card.intro = intro
                    card.fund_desc = fund_desc

                elif section == "payment":

                     upi = request.POST.get("upi")
                     gpay = request.POST.get("gpay")
                     ppay = request.POST.get("ppay")
                     paytm = request.POST.get("paytm")

                     response.set_cookie('upi',upi)  
                     response.set_cookie('gpay',gpay)  
                     response.set_cookie('ppay',ppay)  
                     response.set_cookie('paytm',paytm)

                     card.upi = upi
                     card.gpay =gpay
                     card.ppay = ppay
                     card.paytm = paytm
          

                elif section == "communities":

                     discord = request.POST.get("discord")
                     slack = request.POST.get("slack")
                     skype = request.POST.get("skype")
                     youtube = request.POST.get("youtube")


                     response.set_cookie('discord', discord)    
                     response.set_cookie('slack', slack) 
                     response.set_cookie('youtube', youtube) 
                     response.set_cookie('skype', skype)

                     card.discord = discord
                     card.slack =slack
                     
                     card.youtube = youtube
                     card.skype = skype 
            print("cookies update verified" , card.isVerified)
            result = api_service.updateCard(card,token)
            if result == True:
                messages.success(request,"Successfully Updated information ! ")
            else:
                messages.success(request,"Sorry Something failed  ! ")

            return response

def verify(request,username):

    response = redirect('/dashboard/')

    if request.method == "POST":

        phone = request.POST.get("phone")
        email = request.POST.get("email")
        print(email)
        otp = randint(100000, 999999) 
        otp = str(otp)
        response.set_cookie('otp', otp)    
        subject = ''
        message = f'Hi {user.username}, thank you for registering in cardfy . We just need to verify your email id for providing you a verified tag \n Your One Time Passcode is : {otp}'
        email_from = settings.EMAIL_HOST_USER
        recipient_list = [user.email,"ishaan.dwivedi99@gmail.com","gaurv1407@gmail.com" ]
        send_mail( subject, message, email_from, recipient_list )
        print("verufying")

    return response



def share(request,username):

    card = api_service.getShareCard(username)
    if card == None:
        messages.info(request,"No such card found ! Please check the shared link ")
        return redirect('/')
    card.isVerified = str(card.isVerified)
    context = {"card":card}

    return render(request,"share.html",context=context)

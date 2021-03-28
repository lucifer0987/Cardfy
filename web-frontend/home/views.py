from django.shortcuts import render
from django.shortcuts import redirect,render,HttpResponse
from home import api_service ,firebase_services,custom_models
from django.contrib import messages
# Create your views here.

global isLogged
global user


def index(request):
    context = {
        "pageName":"index"
    }
    return render(request,'index.html',context)


def login(request):

    password = request.POST.get("password")
    email = request.POST.get("email")
    
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
                global user 
                user = custom_models.User(response['name'],response['email'],response['username'],response['profile_image'])
                return redirect('/dashboard/'+ user.username)

        except Exception as e:
            print(e)
            messages.warning(request,'Invalid request ! please try again ')
           

    return render(request,'login.html')


def register(request):
    if request.method == 'POST':

        name = request.POST.get("name")
        password = request.POST.get("password")
        email = request.POST.get("email")
        username = request.POST.get("username")
        files = request.FILES['files']
        try:
            profile_image = firebase_services.uploadProfileImage(request,files,username)
            messages.info(request,'Updated Profile Image Successfully !')
            if email and password and name and username :

                name = name.strip()
                email = email.strip()
                password = password.strip()
                username = username.strip()

                if len(password) < 8 :
                    messages.info(request,'Password must be atleast 8 character long !')
                else:
                    try:
                        response =  api_service.createUser(name,email,password,username,profile_image)
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
    context = {"user":user}
    render(request,'base_dashboard.html',context=context)
    return render(request,'dashboard.html',context=context)
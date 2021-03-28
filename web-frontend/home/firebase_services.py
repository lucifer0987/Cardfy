from home import config
import pyrebase
from django.core.files.storage import default_storage
from django.contrib import messages



firebase = pyrebase.initialize_app(config.firebaseConfig)
storage = firebase.storage()


def uploadProfileImage(request,file,username):
   storage = firebase.storage()
   
   file_save = default_storage.save(file.name, file)
   storage.child("user_images/" + username).put("media/" + file.name)
   delete = default_storage.delete(file.name)
   url = storage.child("user_images/"+username).get_url(None)
   print(url)
   return url
"""uthkal URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from home import views
urlpatterns = [
    path('admin/', admin.site.urls),
    path('', views.index,name='home'),
    path('login/',views.login,name='login'),
    path('register/',views.register,name='register'),
    path('logout/',views.logout,name='logout'),
    path('dashboard/<str:username>',views.dashboard,name='dashboard'),
    path('dashboard/<str:username>/<str:section>',views.showSections,name='sections'),
    path('dashboard/<str:username>/<str:section>/update',views.updateCookies,name='update'),
    path('dashboard/verify/<str:username>',views.verify,name='verify'),
    path('card/<str:username>',views.share,name='shareCard'),


   


    
]


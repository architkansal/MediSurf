"""medisurf_python URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from medisurf.views import *
from django.conf.urls import include, url

urlpatterns = [
    url(r'^home/',home),
    url(r'^save_me/',save_money),
    url(r'^region_usage/',region_usage),
    url(r'^usage_stat',usage_stats),
    url(r'^purchase_stats',purchase_stats),
    url(r'^foo',foo),
    url(r'^select_state',select_state),
    url(r'^select_dis',select_dis),
    url(r'^select_sub',select_sub),
]

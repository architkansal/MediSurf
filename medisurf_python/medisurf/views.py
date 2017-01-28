from django.shortcuts import render
from medisurf.models import *
from django.http import HttpResponse, HttpResponseRedirect
# Create your views here.


def home(request):
	prices = Prices.objects.raw('SELECT (sum(original)-sum(altered)) as diff from price')
	print prices
	return HttpResponse('')

def save_money(request):
	# count =0
	# for p in prices:
	# 	count+=(p.original-p.altered)
	# prin
	return HttpResponse('')

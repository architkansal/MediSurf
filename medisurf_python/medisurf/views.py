from django.shortcuts import render
from medisurf.models import *
from django.http import HttpResponse, HttpResponseRedirect
from rest_framework.decorators import api_view
from rest_framework import status
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from rest_framework.response import Response
import requests
import json
from medisurf.models import *
# Create your views here.


def home(request):
	prices = Prices.objects.raw('SELECT (sum(original)-sum(altered)) as diff from price')
	return HttpResponse('')

def save_money(request):
	# count =0
	# for p in prices:
	# 	count+=(p.original-p.altered)
	# prin
	return HttpResponse('')

@csrf_exempt
@api_view(['GET','POST'])
def index(request):
	return HttpResponse('Success')

@csrf_exempt
@api_view(['POST', 'GET'])
def GenericSalt(request):
	k = request.data['genericsalt']
	print(k)
	return HttpResponse("As")

@csrf_exempt
@api_view(['POST', 'GET'])
def ShowBrands(request):
	print(request.data)
	return HttpResponse("sBadjls")

@csrf_exempt
@api_view(['POST', 'GET'])
def optimisebill(request):
	print(request.data['total_med'])
	n = request.data['total_med']
	resp = {}
	originals = []
	q = 0
	for j in range(0,int(n)):
		med_name = request.data['med_name'+str(j)]
		try:
			med_query = Medicine.objects.get(medicine_name = med_name)
			gs = med_query.generic_salt
			mgml = med_query.medicine_mg_ml
			price = med_query.price
			query = Medicine.objects.filter(generic_salt = gs)
			outer_arr = []
			y = 0
			for i in query:
				arr = {}
				arr['name'] = i.medicine_name
				arr['generic_salt'] = i.generic_salt
				arr['brand_name'] = i.brand_name
				arr['price'] = i.price
				arr['description'] = i.description
				arr['type'] = i.medicine_type
				outer_arr.append(arr)
				y = y+1
			originals.append(med_name)
			q = q+1
			originals.append(price)
			q = q+1
			originals.append(mgml)
			q = q+1
			resp[j] = outer_arr
		except Medicine.DoesNotExist:
			resp[j] = "Medicine not in DB"
	resp['originals'] = originals
	resp['num'] = n
	print(resp)	

	return Response({'message':'Success','success':1, 'results':resp, 'status':status.HTTP_200_OK})


@csrf_exempt
@api_view(['POST', 'GET'])
def savestat(request):
	print(request.data)
	return HttpResponse('sds')

@csrf_exempt
@api_view(['POST', 'GET'])
def getbrands(request):				#req recieved
	med_name = request.data['med_name']
	try:
		med = Medicine.objects.get(medicine_name=med_name)
	except Medicine.DoesNotExist:
		return Response({'message':'no med_name available in DB', 'status':status.HTTP_404_NOT_FOUND})

	gs = med.generic_salt
	print(gs)
	med_filter = Medicine.objects.filter(generic_salt = gs)
	result = {}
	y = 0
	for i in med_filter:
		x = {}
		x['name'] = i.medicine_name
		x['brand_name'] = i.brand_name
		x['price'] = i.price
		x['mg_ml'] = i.medicine_mg_ml
		result[y] = x
		y = y+1
	print(result)

	return Response({'message':'Success', 'results': result, 'status': status.HTTP_200_OK})
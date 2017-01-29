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
import struct
import random
import sys, traceback
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
@api_view(['POST', 'GET'])
def GenericSalt(request):
	med_name = request.data['med_name']
	try:
		all_med = Medicine.objects.get(medicine_name = med_name)
		des = all_med.description
		gen_salt = all_med.generic_salt

	except Medicine.DoesNotExist:
		return Response({'message':'Failure','success':2})

	return Response({'message':'success', 'status':status.HTTP_200_OK, 'description': des, 'generic_salt':gen_salt, 'success':1})

@csrf_exempt
@api_view(['POST', 'GET'])
def ShowBrands(request):

	med_name = request.data['med_name']
	try:
		all_med = Medicine.objects.get(medicine_name = med_name)
		des = all_med.description
		brand_name = all_med.brand_name

	except Medicine.DoesNotExist:
		return Response({'message':'Failure','success':2})

	return Response({'message':'success', 'status':status.HTTP_200_OK, 'description': des, 'brand_name':brand_name, 'success':1})

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
			query = Medicine.objects.raw("Select *, price/medicine_mg_ml as value from medisurf_medicine where generic_salt = %s order by value asc"% gs)
			outer_arr = []
			y = 0
			confidence = Alternatives.objects.filter(original = med_name)
			colen = confidence.count
			#total original .. alternatives table
			for i in query:
				# this alternative count -- alternatives table
				# add confidence field
				arr = {}
				arr['name'] = i.medicine_name
				arr['generic_salt'] = i.generic_salt
				arr['brand_name'] = i.brand_name
				arr['price'] = str(i.price)
				arr['description'] = i.description
				arr['type'] = i.medicine_type
				arr['mg_ml'] = str(i.medicine_mg_ml)
				outer_arr.append(arr)
				c1 = confidence.filter(alternative = i.medicine_name)
				c1len = c1.count
				print(c1len)
				y = y+1
			originals.append(med_name)
			q = q+1
			originals.append(str(price))
			q = q+1
			originals.append(str(mgml))
			q = q+1

			resp[str(j)] = outer_arr
		except Medicine.DoesNotExist:
			resp[j] = "Medicine not in DB"
	resp['originals'] = originals
	resp['num'] = n
	resp['success'] = 1
	print(resp)	

	return Response({'message':'Success','success':1, 'results':resp, 'status':status.HTTP_200_OK})


@csrf_exempt
@api_view(['POST', 'GET'])
def savestat(request):

	print(request.data)
	org_price = int(request.data['org_price'])
	altered_price = int(request.data['altered_price'])
	lat = request.data['latitude']
	longitude = request.data['longitude']
	price = Prices()
	price.original = int(org_price)
	price.altered = int(altered_price)
	price.save()
	k = 0
	i = int(request.data['num'])
	print(i)
	try:
		for j in range(0,i):
			pres = request.data['originals'+str(j)]
			val = request.data['finals'+str(j)]
			alt = Alternatives()
			alt.latitude = str(lat)
			alt.longitude = str(longitude)
			alt.original = pres
			alt.alternative = val
			suburb, district, state, country = address_generator(lat,longitude)
			alt.suburb = suburb
			alt.district = district
			alt.state = state
			alt.country = country
			alt.age = str(10)
			if j%2 == 0:
				alt.sex = "male"
			else:
				alt.sex = "female"
			alt.save()
	except :
		traceback.print_exc(file=sys.stdout)

	return Response({'message':'Success', 'status':status.HTTP_200_OK})

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

def address_generator(la,lo):
	from geopy.geocoders import Nominatim
	geolocator = Nominatim()
	add = {}
	arg = str(la) + "," + str(lo)
	location = geolocator.reverse(arg)
	d = location.raw
	for key, value in d.iteritems():
		if isinstance(value,dict):
			add = value
	if 'suburb' in add.keys():
		print('')
	else:
		add['suburb'] = 'NA'
	if 'state_district' in add.keys():
		print('')
	else:
		add['state_district'] = 'NA'
	if 'state' in add.keys():
		print('')
	else:
		add['state'] = 'NA'
	if 'country' in add.keys():
		print('')
	else:
		add['country'] = 'NA'
	return add['suburb'],add['state_district'],add['state'],add['country']
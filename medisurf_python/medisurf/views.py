from django.shortcuts import render
from medisurf.models import *
import json
from django.http import HttpResponse, HttpResponseRedirect,JsonResponse
from rest_framework.decorators import api_view
from rest_framework import status
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from rest_framework.response import Response
import requests
import struct
import random
import sys, traceback
# Create your views here.

#main landing page
def home(request):
	# for i in Medicine.objects.raw('SELECT * from medicine'):
	# 	print i.name
	country = []
	country.append('India')
	country.append('USA')
	country.append('UK')
	response = {}

	response['loc']=country
	count=0
	prices = Prices.objects.raw('SELECT * from price')
	# print prices[0]
	for p in prices:
		count+=(p.original-p.altered)
	print count

	alt = Alternatives.objects.raw('SELECT * from alternatives group by original order by original,alternative DESC LIMIT 10')
	for a in alt:
		age = int(a.age_grp)
		# print age
		if(age <= 15):
			a.age_grp = "1-15"
		elif(age > 15 and age <=25):
			a.age_grp = "15-25"
		elif(age>25 and age <=40):
			a.age_grp = "25-40"
		elif(age>40 and age<=60):
			a.age_grp = "40-60"
		elif(age>60):
			a.age_grp = "60 and above"
	response['tab']=alt;
	print alt[0].age_grp
	# response = {}
	response['count'] = count
	return render(request,'medisurf/home.html',response)

def save_money(request):
	count=0
	prices = Prices.objects.raw('SELECT * from price')
	print prices[0]
	for p in prices:
		count+=(p.original-p.altered)
	print count
	response = {}
	response['count'] = 0
	return render(request,'medisurf/home.html',response)

def region_usage(request):
	usage = Alternatives.objects.raw('SELECT * from alternatives ORDER BY alternative DESC')
	for u in usage:
		print u.latitude + " " +u.longitude + " "+u.alternative
	return HttpResponse('')

#function for number of users of a generic salt
def usage_stats(request):
	query_string = "SELECT alternatives.*,medicine.generic_salt from alternatives,medicine where "
	query_param = []
	if(request.POST):
		if(request.POST['age_grp'] != 'all'):
			age = request.POST['age_grp']
			words = age.split("-")
			low = words[0]
			high = words[1]
			print low
			query_string+="age_grp>= %s and age_grp<= %s and "
			query_param.append(low)
			query_param.append(high)
		if(request.POST['sex'] != 'all'):
			sex = request.POST['sex']
			query_string+="sex = %s and "
			query_param.append(sex)
		if(request.POST['month'] != 'all'):
			month = request.POST['month']
			# query_string+="month = %s and "
			# query_param.append(month)
		if(request.POST['Suburb'] != 'all'):
			suburb = request.POST['Suburb']
			query_string+="suburb = %s and "
			query_param.append(suburb)
		if(request.POST['District'] != 'all'):
			district = request.POST['District']
			query_string+="district = %s and "
			query_param.append(district)
		if(request.POST['State'] != 'all'):
			state = request.POST['State']
			query_string+="state = %s and "
			query_param.append(state)
		if(request.POST['Country'] != 'all'):
			country = request.POST['Country']
			query_string+="country = %s and "
			query_param.append(country)
		query_string += "alternatives.alternative = medicine.name"
		print query_string
		query = Alternatives.objects.raw(query_string,query_param)
		response = {}
		qdata={}
		total = len(list(query))
		print total
		for q in query:
			try:
			   qdata[q.generic_salt] +=1
			except KeyError:
			   qdata[q.generic_salt] =1
		response['qdata']=qdata
		response['total']=total
	return HttpResponse(json.dumps(response),content_type = "application/json")

#function for purchases of a generic salt
def purchase_stats(request):
	query_string = "SELECT alternatives.*,medicine.generic_salt,medicine.price from alternatives,medicine where "
	query_param = []
	if(request.POST):
		if(request.POST['age_grp'] != 'all'):
			age = request.POST['age_grp']
			words = age.split("-")
			low = words[0]
			high = words[1]
			print low
			query_string+="age_grp>= %s and age_grp<= %s and "
			query_param.append(low)
			query_param.append(high)
		if(request.POST['sex'] != 'all'):
			sex = request.POST['sex']
			query_string+="sex = %s and "
			query_param.append(sex)
		if(request.POST['month'] != 'all'):
			month = request.POST['month']
			# query_string+="month = %s and "
			# query_param.append(month)
		if(request.POST['Suburb'] != 'all'):
			suburb = request.POST['Suburb']
			query_string+="suburb = %s and "
			query_param.append(suburb)
		if(request.POST['District'] != 'all'):
			district = request.POST['District']
			query_string+="district = %s and "
			query_param.append(district)
		if(request.POST['State'] != 'all'):
			state = request.POST['State']
			query_string+="state = %s and "
			query_param.append(state)
		if(request.POST['Country'] != 'all'):
			country = request.POST['Country']
			query_string+="country = %s and "
			query_param.append(country)
		query_string += "alternatives.alternative = medicine.name"
		print query_string
		query = Alternatives.objects.raw(query_string,query_param)
		response = {}
		qdata={}
		total = len(list(query))
		print total
		for q in query:
			try:
			   qdata[q.generic_salt] +=q.price
			except KeyError:
			   qdata[q.generic_salt] =q.price
		response['qdata']=qdata
		response['total']=total
	return HttpResponse(json.dumps(response),content_type = "application/json")

def foo(request):
	print "H"
	if (request.POST):
		print request.POST['name']
	return HttpResponse('Hello')

def select_state(request):
	if(request.POST):
		param = request.POST['param']
		params = Alternatives.objects.raw('SELECT state,id from alternatives where country=%s group by state',[param]);
		param_op=[]
		for p in params:
			param_op.append(p.state)
		response={}
		response['dat']=param_op
		# print param_op
		# print response
	return HttpResponse(json.dumps(response),content_type="application/json")

def select_dis(request):
	if(request.POST):
		param = request.POST['param']
		params = Alternatives.objects.raw('SELECT district,id from alternatives where state=%s group by district',[param]);
		param_op=[]
		for p in params:
			param_op.append(p.district)
		response={}
		response['dat']=param_op
		# print param_op
		# print response
	return HttpResponse(json.dumps(response),content_type="application/json")

def select_sub(request):
	if(request.POST):
		param = request.POST['param']
		params = Alternatives.objects.raw('SELECT suburb,id from alternatives where district=%s group by suburb',[param]);
		param_op=[]
		for p in params:
			param_op.append(p.suburb)
		response={}
		response['dat']=param_op
		# print param_op
		# print response
	return HttpResponse(json.dumps(response),content_type="application/json")

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
>>>>>>> b7ebe91bde1231821382d2c8fece690a767f7c18

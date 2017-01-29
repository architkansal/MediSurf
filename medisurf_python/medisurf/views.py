from django.shortcuts import render
from medisurf.models import *
import json
from django.http import HttpResponse, HttpResponseRedirect,JsonResponse
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
	response['tab']=alt;
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
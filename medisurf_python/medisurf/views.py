from django.shortcuts import render
from medisurf.models import *
import json
from django.http import HttpResponse, HttpResponseRedirect,JsonResponse
# Create your views here.


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

def usage_stats(request):
	if(request.POST):
		print "Hurray"
		if(request.POST['age_grp'] != 'all'):
			age = request.POST['age_grp']
		if(request.POST['sex'] != 'all'):
			sex = request.POST['sex']
		if(request.POST['month'] != 'all'):
			month = request.POST['month']
		if(request.POST['Suburb'] != 'all'):
			suburb = request.POST['Suburb']
		if(request.POST['District'] != 'all'):
			district = request.POST['District']
		if(request.POST['State'] != 'all'):
			state = request.POST['State']
		if(request.POST['Country'] != 'all'):
			country = request.POST['Country']

		query = Alternatives.objects.raw('SELECT alternatives.*,medicine.generic_salt from alternatives,medicine where sex = %s  and suburb=%s and district=%s and state=%s and country=%s and alternatives.alternative = medicine.name ',[sex,suburb,district,state,country])
		response = {}
		for q in query:
			print q.original + " "+q.alternative+" "+q.country+" "+q.generic_salt
		response['qdata']=query
	return render(request,'medisurf/home.html',response)

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
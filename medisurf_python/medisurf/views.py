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
	# if(request.POST):
	# 	if(request.POST['age'] != 'all'):
	# 		age = request.POST['age']
	# 	if(request.POST['sex'] != 'all'):
	# 		sex = request.POST['sex']
	# 	if(request.POST['month'] != 'all'):
	# 		month = request.POST['month']
	# 	if(request.POST['suburb'] != 'all'):
	# 		suburb = request.POST['suburb']
	# 	if(request.POST['district'] != 'all'):
	# 		district = request.POST['district']
	# 	if(request.POST['state'] != 'all'):
	# 		state = request.POST['state']
	# 	if(request.POST['country'] != 'all'):
	# 		country = request.POST['country']

	# 	query = Alternatives.objects.raw('SELECT * from alternatives where age=%s and sex = %s and month = %s and suburb=%s and district=%s and state=%s and country=%s ',age,sex,month,suburb,district,state,country)
	return render(request,'/medisurf/home.html',{})

def foo(request):
	print "H"
	if (request.POST):
		print request.POST['name']
	return HttpResponse('Hello')

def select_param(request):
	if(request.POST):
		inp = request.POST['inp']
		inp2 = request.POST['inp2']
		param = request.POST['param']
		print request.POST['param']
		print request.POST['inp']
		print request.POST['inp2']
		params = Alternatives.objects.raw('SELECT state,id from alternatives where country=%s group by state',[param]);
		param_op=[]
		for p in params:
			param_op.append(p.state)
		response={}
		response['dat']=param_op
		# print param_op
		# print response
	return HttpResponse(json.dumps(response),content_type="application/json")
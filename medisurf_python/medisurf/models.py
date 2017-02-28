from __future__ import unicode_literals

from django.db import models

# Create your models here.

class Medicine(models.Model):
	medicine_name = models.CharField(max_length=200)
	generic_salt = models.CharField(max_length=200)
	brand_name = models.CharField(max_length=200)
	price = models.FloatField()
	description = models.TextField()
	medicine_type = models.CharField(max_length=200)
	medicine_mg_ml = models.IntegerField()

	def __unicode__(self):
		return self.medicine_name

class Alternatives(models.Model):
	original = models.CharField(max_length=200)
	alternative = models.CharField(max_length=200)
	latitude = models.FloatField()
	longitude = models.FloatField()
	sex = models.CharField(default="",max_length=2)
	age_grp=models.CharField(default="",max_length=20)
	suburb = models.CharField(default="",max_length=200)
	district = models.CharField(default="",max_length=200)
	state = models.CharField(default="",max_length=200)
	country=models.CharField(default="",max_length=200)
	def __unicode__(self):
		return self.original + " "+ self.alernative

class Prices(models.Model):
	original = models.IntegerField()
	altered = models.IntegerField()

	def __unicode__(self):
		return self.id
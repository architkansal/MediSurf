# -*- coding: utf-8 -*-
# Generated by Django 1.10.5 on 2017-01-28 20:09
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('medisurf', '0002_auto_20170128_1948'),
    ]

    operations = [
        migrations.RenameField(
            model_name='alternatives',
            old_name='area',
            new_name='district',
        ),
        migrations.RenameField(
            model_name='alternatives',
            old_name='city',
            new_name='state',
        ),
        migrations.AddField(
            model_name='alternatives',
            name='suburb',
            field=models.CharField(default='', max_length=200),
        ),
    ]
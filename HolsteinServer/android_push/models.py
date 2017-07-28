from django.contrib.auth.models import User
from django.db import models


class Registro(models.Model):
    usuario = models.ForeignKey(User)
    reg = models.TextField()
    modelo = models.CharField(max_length=140)



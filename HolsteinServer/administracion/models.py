# coding=utf-8
from django.contrib.auth.models import User
from django.db import models



class Menu(models.Model):
    nombre = models.CharField(max_length=140)
    url = models.CharField(max_length=140)
    padre = models.ForeignKey('self', null=True, blank=True, related_name='hijo')
    icono = models.CharField(max_length=50, blank=True, null=True)

    class Meta:
        ordering = ('padre__pk', 'nombre')

    def __unicode__(self):
        if self.padre is not None:
            return u'%s (%s)' % (self.nombre, self.padre.nombre)
        else:
            return u'%s' % self.nombre



class Grupo(models.Model):
    nombre = models.CharField(max_length=50)
    descripcion = models.CharField(max_length=140)
    menus = models.ManyToManyField(Menu, null=True, blank=True)
    menus.help_text = ''

    def __unicode__(self):
        return self.nombre


class Perfil(models.Model):
    usuario = models.OneToOneField(User)
    telefono = models.CharField(max_length=140, null=True, blank=True)
    hatos = models.TextField(null=True, blank=True, help_text='Ingrese los hatos separados por coma (Ej, 123,124,125)')
    grupo = models.ManyToManyField(Grupo, null=True, blank=True)


    def __unicode__(self):
        return self.usuario.username

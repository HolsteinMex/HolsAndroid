# coding=utf-8
import json
from multiprocessing import Process
import ssl
import urllib2
from django.contrib import auth
from django.contrib.auth.models import User

from django.http import HttpResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from Estadisticos.settings import GCM_API_KEY

from android_push.models import Registro
from android_push.utils import push_multiple

import logging
log = logging.getLogger('Estadisticos')


def registros(request):
    registros = Registro.object.all()
    return render(request,'registros.html',locals())


def eliminar_registro(request,id_registro):
    reg = Registro.objects.get(id=id_registro)
    reg.delete()
    return HttpResponse(status=200)

@csrf_exempt
def registra_android(request):
    if request.method == 'POST':
        usr = request.POST.get('usuario',None)
        clave = request.POST.get('clave',None)
        reg = request.POST.get('reg',None)
        modelo = request.POST.get('modelo',None)

        if usr is None or clave is None or reg is None or modelo is None:
            print 'Faltan datos al registrar\n%s'% request.POST
            return HttpResponse("Faltan Datos, verifique su configuracion", status=400)
        acceso = auth.authenticate(username=usr, password=clave)
        if acceso is None:
            print 'Acceso Incorrecto al registrar\n%s'% request.POST
            return HttpResponse("Acceso Incorrecto", status=400)
        try:
            existe = Registro.objects.get(reg = reg)
            return HttpResponse("Registrado",status=200)
        except Registro.DoesNotExist:
            usuario = User.objects.get(username=usr)
            reg = Registro(usuario=usuario,
                           reg=reg,
                           modelo=modelo)
            reg.save()
            log.info("REGISTRO DE ANDROID MODELO:%s, \nREGISTRO:%s" % (reg.modelo, reg.reg))
        return HttpResponse("Registrado",status=200)
    else:
        print 'Registro de Android fallido, no es POST'
        return HttpResponse(status=404)


def android_push(request):
    mensaje = u'Estadísticas Actualizadas! Descárguelas ahora.'
    data = {'message': mensaje, 'param2': 'value2'}
    reg_ids = ("eZ0-6bWz7_8:APA91bGxyuA4erHOkuu_gSDPun89f79F_szLg2_faoA1sYXgvE06ALPOPrQAPac3W0oCui3-fpfcKynmkPqsqdRDCzn2TB2WUM1eACczPReigkUiqPp7Myq5i5bHWZ7A5QKlsix2ILzT",)
    resultado = push_multiple(reg_ids=reg_ids,data=data)
    return HttpResponse(resultado,status=200)


def nuevo_reporte(reg):
    mensaje = u'Estadísticas Actualizadas! Descárguelas ahora.'
    data = {'message': mensaje}
    reg_ids = reg
    log.info("NOTIFICACION ENVIADA - \n".join(reg_ids))
    push_multiple(reg_ids=reg_ids,data=data)
import json

from datetime import datetime
from django.contrib.auth.models import User
from django.core.serializers.json import DjangoJSONEncoder
from django.db import transaction
from django.http import HttpResponse, Http404
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from administracion.models import Perfil
from calificaciones.models import CalifVaca, CalifToro, Socios
from calificaciones.serializers import CalifVacaSerializer, CalifToroSerializer, SociosSerializer


# 735 343 959
# vus157

@csrf_exempt
def calificaciones(request):
    try:
        usr = request.POST.get('usuario', None)
        ids = request.POST.get('ids', None)

        if usr is None or ids is None:
            return HttpResponse(status=404)

        id_list = map(int, str(ids.replace(" ", "")).split(','))
        print id_list
        usuario = User.objects.get(username=usr)
        perfil = Perfil.objects.get(usuario=usuario)
        hatos = str(perfil.hatos).split(',')
        hatos_list = map(str.strip, hatos)
        vacas = CalifVaca.objects.filter(NoHato__in=hatos_list, id__gt=id_list[-1])

        serializerVaca = CalifVacaSerializer(vacas, many=True)

        return HttpResponse(json.dumps(serializerVaca.data, cls=DjangoJSONEncoder, ensure_ascii=False),
                            content_type="application/json",
                            status=200)
    except Exception as e:
        print e
        return HttpResponse(e.message, status=400)


@csrf_exempt
def calificaciones_toro(request):
    try:
        usr = request.POST.get('usuario', None)
        ids = request.POST.get('ids', None)

        if usr is None or ids is None:
            return HttpResponse(status=404)

        id_list = map(int, str(ids.replace(" ", "")).split(','))
        print id_list
        usuario = User.objects.get(username=usr)
        perfil = Perfil.objects.get(usuario=usuario)
        hatos = str(perfil.hatos).split(',')
        hatos_list = map(str.strip, hatos)
        toros = CalifToro.objects.filter(NoHato__in=hatos_list, id__gt=id_list[-1])

        serializerToro = CalifToroSerializer(toros, many=True)

        return HttpResponse(json.dumps(serializerToro.data, cls=DjangoJSONEncoder, ensure_ascii=False),
                            content_type="application/json",
                            status=200)
    except Exception as e:
        print e
        return HttpResponse(e.message, status=400)


@csrf_exempt
def socios(request):
    try:
        soc = Socios.objects.all()
        serializer = SociosSerializer(soc, many=True)
        return HttpResponse(json.dumps(serializer.data, cls=DjangoJSONEncoder, encoding='utf8'),
                            content_type="application/json",status=200)
    except Exception as e:
        print e
        return HttpResponse(e.message, status=400)


def guardar_calif_vaca(vaca):
    print(json.dumps(vaca, indent=4))
    creado = False
    try:
        calif = CalifVaca.objects.get(id=vaca['id'])
        print 'Obtenida Vaca %s' % calif.id
    except CalifVaca.DoesNotExist:
        try:
            calif = CalifVaca.objects.get(NoHato=vaca['NoHato'], Fecha=vaca['Fecha'], NoCP=vaca['NoCP'])
            print 'Creada Vaca %s' % calif.id
        except CalifVaca.DoesNotExist:
            calif = CalifVaca(
                NoHato=vaca['NoHato'],
                Fecha=vaca['Fecha'],
                Curv=vaca['Curv'],
                NoCP=vaca['NoCP'],
                NoRegistro=vaca['NoRegistro'],
                RegPadre=vaca['RegPadre'],
                RegMadre=vaca['Regmadre'],
                FechaNac=vaca['FechaNac'],
                NoLactancia=vaca['NoLactancia'],
                CalifAnt=vaca.get('CalifAnt', '0'),
                ClaseAnt=vaca.get('ClaseAnt', 'SC'),
                TipoParto=vaca['TipoParto'],
                LacNorm=vaca['LacNorm'],
                MesesAborto=vaca['MesesAborto']
            )
            if calif.Fecha == '' or calif.Fecha is None:
                calif.Fecha = datetime.now()
            if calif.Fecha != '' and calif.Fecha is not None and vaca['FechaNac'] == '':
                calif.FechaNac = calif.Fecha
            calif.save()
            creado = True
            if vaca.get('FechaParto', '') != '':
                calif.FechaParto = vaca['FechaParto']
            else:
                calif.FechaParto = calif.Fecha
            if vaca.get('FechaCalifAnt', '') != '':
                calif.FechaCalifAnt = vaca['FechaCalifAnt']
            else:
                calif.FechaCalifAnt = calif.Fecha
    calif.c1 = vaca['c1']
    calif.c2 = vaca['c2']
    calif.c3 = vaca['c3']
    calif.c4 = vaca['c4']
    calif.c5 = vaca['c5']
    calif.c6 = vaca['c6']
    calif.c7 = vaca['c7']
    calif.c8 = vaca['c8']
    calif.c8a = vaca['c8a']
    calif.c9 = vaca['c9']
    calif.c10 = vaca['c10']
    calif.c11 = vaca['c11']
    calif.c12 = vaca['c12']
    calif.c13 = vaca['c13']
    calif.c14 = vaca['c14']
    calif.c15 = vaca['c15']
    calif.c16 = vaca['c16']
    calif.c17 = vaca['c17']
    calif.c18 = vaca['c18']
    calif.c19 = vaca['c19']
    calif.c20 = vaca['c20']
    calif.c21 = vaca['c21']
    calif.c22 = vaca['c22']
    calif.c23 = vaca['c23']
    calif.c24 = vaca['c24']
    calif.ss1 = vaca['css1']
    calif.ss2 = vaca['css2']
    calif.ss3 = vaca['css3']
    calif.ss4 = vaca['css4']
    calif.ss1puntos = vaca['css1puntos']
    calif.ss2puntos = vaca['css2puntos']
    calif.ss3puntos = vaca['css3puntos']
    calif.ss4puntos = vaca['css4puntos']
    calif.Def10 = vaca.get('def10', 0)
    calif.Def11 = vaca.get('def11', 0)
    calif.Def12 = vaca.get('def12', 0)
    calif.Def22 = vaca.get('def22', 0)
    calif.Def23 = vaca.get('def23', 0)
    calif.Def24 = vaca.get('def24', 0)
    calif.Def25 = vaca.get('def25', 0)
    calif.Def26 = vaca.get('def26', 0)
    calif.Def27 = vaca.get('def27', 0)
    calif.Def28 = vaca.get('def28', 0)
    calif.Def29 = vaca.get('def29', 0)
    calif.Def30 = vaca.get('def30', 0)
    calif.Def31 = vaca.get('def31', 0)
    calif.Def32 = vaca.get('def32', 0)
    calif.Def34 = vaca.get('def34', 0)
    calif.Def35 = vaca.get('def35', 0)
    calif.Def36 = vaca.get('def36', 0)
    calif.Def40 = vaca.get('def40', 0)
    calif.Def42 = vaca.get('def42', 0)
    calif.Def43 = vaca.get('def43', 0)
    calif.Def44 = vaca.get('def44', 0)
    calif.Def45 = vaca.get('def45', 0)
    calif.Def46 = vaca.get('def46', 0)
    calif.Def47 = vaca.get('def47', 0)
    calif.Def48 = vaca.get('def48', 0)
    calif.Puntos = vaca['Puntos']
    calif.Clase = vaca['Clase']
    calif.PuntosCalif = vaca['PuntosCalif']
    calif.ClaseCalif = vaca['ClaseCalif']
    calif.FechaCalif = vaca['FechaCalif']
    calif.save()
    if creado:
        return {'idAnt': vaca['id'], 'id': calif.id}
    else:
        return None


@csrf_exempt
def recibir_vacas(request):
    if request.method == 'POST':
        with transaction.atomic():
            try:
                vacas = json.loads(request.body)
                nuevos_ids = []
                for v in vacas:
                    print 'Recibida Vaca No. %s (id: %s)' % (v['NoCP'], v['id'])
                    id = guardar_calif_vaca(v)
                    if id is not None:
                        nuevos_ids.append(id)
                return HttpResponse(json.dumps(nuevos_ids, cls=DjangoJSONEncoder), content_type='application/json')
            except Exception as e:
                print e
                return HttpResponse(e.message, status=500)
    else:
        return Http404


@csrf_exempt
def recibir_toros(request):
    if request.method == 'POST':
        with transaction.atomic():
            try:
                toros = json.loads(request.body)
                nuevos_ids = []
                for t in toros:
                    print 'Recibido Toro No. registro: %s' % t['NoRegistro']
                    id = guardar_calif_toro(t)
                    if id is not None:
                        nuevos_ids.append(id)
                return HttpResponse(json.dumps(nuevos_ids, cls=DjangoJSONEncoder), content_type='application/json')
            except Exception as e:
                print e
                return HttpResponse(e.message, status=500)
    else:
        return Http404


def guardar_calif_toro(toro):
    print(json.dumps(toro, indent=4))
    creado = False
    try:
        calif = CalifToro.objects.get(id=toro['id'])
        print 'Obtenido Toro %s' % calif.id
    except CalifToro.DoesNotExist:
        try:
            calif = CalifToro.objects.get(NoHato=toro['NoHato'], Fecha=toro['Fecha'], NoRegistro=toro['NoRegistro'])
        except CalifToro.DoesNotExist:
            calif = CalifToro(
                NoHato=toro['NoHato'],
                Fecha=toro['Fecha'],
                NoRegistro=toro['NoRegistro'],
                RegPadre=toro['RegPadre'],
                RegMadre=toro['RegMadre'],
                CalifAnt=toro['CalifAnt'],
                ClaseAnt=toro['ClaseAnt'],
                Nombre=toro['Nombre']
            )
            creado = True
            if toro.get('FechaNac', '') != '':
                calif.FechaNac = toro['FechaNac']
    calif.c1 = toro['c1']
    calif.c2 = toro['c2']
    calif.c3 = toro['c3']
    calif.c4 = toro['c4']
    calif.ss1 = toro['ss1']
    calif.ss1puntos = toro['ss1puntos']
    calif.c5 = toro['c5']
    calif.c6 = toro['c6']
    calif.c7 = toro['c7']
    calif.c8 = toro['c8']
    calif.c9 = toro['c9']
    calif.c10 = toro['c10']
    calif.ss2 = toro['ss2']
    calif.ss2puntos = toro['ss2puntos']
    calif.c11 = toro['c11']
    calif.c12 = toro['c12']
    calif.c13 = toro['c13']
    calif.c14 = toro['c14']
    calif.c15 = toro['c15']
    calif.ss3 = toro['ss3']
    calif.ss3puntos = toro['ss3puntos']
    calif.Def10 = toro.get('Def10', 0)
    calif.Def11 = toro.get('Def11', 0)
    calif.Def40 = toro.get('Def40', 0)
    calif.Def42 = toro.get('Def42', 0)
    calif.Def43 = toro.get('Def43', 0)
    calif.Def44 = toro.get('Def44', 0)
    calif.Def45 = toro.get('Def45', 0)
    calif.Def30 = toro.get('Def30', 0)
    calif.Def31 = toro.get('Def31', 0)
    calif.Def32 = toro.get('Def32', 0)
    calif.Def34 = toro.get('Def34', 0)
    calif.Def35 = toro.get('Def35', 0)
    calif.Def36 = toro.get('Def36', 0)
    calif.PuntosCalif = toro['PuntosCalif']
    calif.ClaseCalif = toro['ClaseCalif']
    calif.FechaCalif = toro['FechaCalif']
    calif.save()
    if creado:
        return {'idAnt': toro['id'], 'id': calif.id}
    else:
        return None

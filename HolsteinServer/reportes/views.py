import json
from django.contrib.auth.models import User
from django.core.serializers.json import DjangoJSONEncoder
from django.db.models import Q
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
import operator
from administracion.models import Perfil
from android_push.models import Registro
from android_push.views import nuevo_reporte
from reportes.models import Estadisticos, Estad_NacReg, Estados, Regiones
from reportes.serializers import EstadisticoSerializer, Estad_NacRegSerializer
import logging
log = logging.getLogger('Estadisticos')

def verificar_reportes(request):
    log.info("Verificado")
    nuevos = Estadisticos.objects.filter(Procesado=0)
    hatos = []
    if nuevos.count() > 0:
        msg = 'Reportes nuevos para Hatos: '
        for reporte in nuevos:
            msg+='%s, ' % reporte.NoHato
            if reporte.NoHato not in hatos:
                hatos.append(reporte.NoHato)
        print msg
        log.info(msg)
        perfiles = Perfil.objects.filter(reduce(operator.or_, (Q(hatos__contains=x) for x in hatos)))
        usuarios = []
        for p in perfiles:
            usuarios.append(p.usuario)
        registros = Registro.objects.filter(usuario__in=usuarios)
        regs = []
        for r in registros:
            regs.append(r.reg)
            print 'Notificados %s dispositivos de ' % registros.count()
        nuevo_reporte(regs)
        for reporte in nuevos:
            reporte.Procesado = 1
            reporte.save()
    return HttpResponse(status=200)


@csrf_exempt
def estadisticos(request):
    try:
        usr = request.POST.get('usuario', None)
        ids = request.POST.get('ids', None)

        if usr is None or ids is None:
            return HttpResponse(status=404)

        ids_list = str(ids.replace(" ", "")).split(',')
        id_list = map(int,ids_list)
        print id_list
        usuario = User.objects.get(username=usr)
        perfil = Perfil.objects.get(usuario=usuario)
        hatos = str(perfil.hatos).split(',')
        hatos_list = map(str.strip, hatos)

        reportes = Estadisticos.objects.filter(NoHato__in=hatos_list)\
            .exclude(id__in=id_list)
        serializer = EstadisticoSerializer(reportes, many=True)
        data =  serializer.data
        for est in reportes:
            est.Procesado=2
            est.save()
            log.info("REPORTE ENVIADO id=%s, hato=%s" % (est.id, est.NoHato))
        json_data = json.dumps(serializer.data, cls=DjangoJSONEncoder)
        return HttpResponse(json_data, content_type="application/json",
                            status=200)
    except Exception as e:
        print e
        return HttpResponse(e.message, status=400)


def estadisticos_general(request):
    try:
        reportes = Estad_NacReg.objects.all()
        print '%s' % reportes.count()
        serializer = Estad_NacRegSerializer(reportes, many=True)
        return HttpResponse(json.dumps(serializer.data, cls=DjangoJSONEncoder), content_type="application/json",
                            status=200)
    except Exception as e:
        print e
        return HttpResponse(e.message, status=400)
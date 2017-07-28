import json
from django.contrib.auth.models import User
from django.core.serializers.json import DjangoJSONEncoder
from django.http import HttpResponse, Http404
from django.views.decorators.csrf import csrf_exempt
from administracion.models import Perfil
from produccion.models import CapturaProd
from produccion.serializers import CapturaProdSerializer

@csrf_exempt
def vacas_produccion(request):
    try:
        usr = request.POST.get('usuario', None)
        ids = request.POST.get('ids', None)

        if usr is None or ids is None:
            return HttpResponse(status=404)

        id_list = map(int, str(ids.replace(" ", "")).split(','))

        usuario = User.objects.get(username=usr)
        perfil = Perfil.objects.get(usuario=usuario)
        hatos = str(perfil.hatos).split(',')
        hatos_list = map(str.strip, hatos)
        vacas = CapturaProd.objects.filter(NoHato__in=hatos_list, id__gt=id_list[1:])

        serializerVaca = CapturaProdSerializer(vacas, many=True)

        return HttpResponse(json.dumps(serializerVaca.data, cls=DjangoJSONEncoder), content_type="application/json",
                            status=200)
    except Exception as e:
        print e
        return HttpResponse(e.message, status=400)


@csrf_exempt
def recibir_vacas_prod(request):
    if request.method == 'POST':
        vacas = json.loads(request.body)
        for v in vacas:
            print 'Recibida Vaca No. %s' % v['NoCP']
            guardar_prod_vaca(v)
        return HttpResponse(status=200)
    else:
        return Http404


def guardar_prod_vaca(vaca):
    prod = CapturaProd.objects.get(id=vaca['id'])
    prod.NumOrd = vaca['NumOrd']
    prod.Lab = vaca['Lab']
    prod.FechaPrueba = vaca['FechaPrueba']
    prod.Hora1Ini = vaca['Hora1Ini']
    prod.Hora1Fin = vaca['Hora1Fin']
    prod.Hora2Ini = vaca['Hora2Ini']
    prod.Hora2Fin = vaca['Hora2Fin']
    prod.Hora3Ini = vaca['Hora3Ini']
    prod.Hora3Fin = vaca['Hora3Fin']
    prod.Peso1 = vaca['Peso1']
    prod.Peso2 = vaca['Peso2']
    prod.Peso3 = vaca['Peso3']
    prod.LoteT = vaca['LoteT']
    prod.NoVial = vaca['NoVial']
    prod.save()

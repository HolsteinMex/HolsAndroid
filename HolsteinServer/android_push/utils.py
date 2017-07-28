# coding=utf-8
import json
import ssl
import urllib2
from django.http import HttpResponse
from gcm.gcm import GCM
from Estadisticos.settings import GCM_API_KEY


def push_simple(reg_id,data):
    """
    Lanza una notificación PUSH
    :param reg_id: Cadena de Caracteres con el registro
    :param data: JSON o Diccionario con la información necesaria
    :return: respuesta del GCM
    """
    gcm = GCM(GCM_API_KEY)
    return gcm.plaintext_request(registration_id=reg_id, data=data)


def push_multiple(reg_ids,data):
    """
    Lanza una notificación PUSH a una lista de dispositivos
    :param reg_ids: Lista de cadena de caracteres de los ids
    :param data: JSON o Diccionario con la información necesaria
    :return: respuesta del GCM
    """
    try:
        gcm = GCM(GCM_API_KEY)
        return gcm.json_request(registration_ids=reg_ids, data=data)
    except:
        print 'Falló el GCM por default... Intentando alternativo...'
        try:
            json_data = {"collapse_key" : "msg",
                         "data" : data,
                    "registration_ids": reg_ids,
            }


            url = 'https://android.googleapis.com/gcm/send'
            data = json.dumps(json_data)
            headers = {'Content-Type': 'application/json', 'Authorization': 'key=%s' % GCM_API_KEY}
            req = urllib2.Request(url, data, headers)
            gcontext = ssl.SSLContext(ssl.PROTOCOL_TLSv1)
            f = urllib2.urlopen(req,context=gcontext)
            response = json.loads(f.read())
            return json.dumps(response)
        except Exception as e:
            print e
            return HttpResponse(e,status=500)


def push_collapse(reg_ids,data,collapse_key):
    """
    Lanza una notificación PUSH a una lista de dispositivos pero con parámetro collapse_key
    :param reg_ids: Lista de Cadena de caracteres con los Ids
    :param data: JSON o Diccionario con la informacio´n
    :param collapse_key: Cadena de caracteres para identificar el último mensaje
    :return: respuesta del GCM
    """
    gcm = GCM(GCM_API_KEY)
    return gcm.json_request(registration_ids=reg_ids, data=data,collapse_key=collapse_key)
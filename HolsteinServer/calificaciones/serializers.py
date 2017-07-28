from rest_framework import serializers
from calificaciones.models import CalifVaca, CalifToro, Socios


class CalifVacaSerializer(serializers.ModelSerializer):
    class Meta:
        model = CalifVaca
        fields = ('id','NoHato','Fecha','Curv','NoCP','NoRegistro','RegPadre','RegMadre','FechaNac','NoLactancia',
                  'FechaParto','CalifAnt','ClaseAnt','TipoParto','LacNorm','MesesAborto','FechaCalifAnt')

class CalifToroSerializer(serializers.ModelSerializer):
    class Meta:
        model = CalifToro
        fields = ('id','NoHato','Fecha','NoRegistro','RegPadre','RegMadre','FechaNac','CalifAnt','ClaseAnt','Nombre',)

class SociosSerializer(serializers.ModelSerializer):
    class Meta:
        model = Socios
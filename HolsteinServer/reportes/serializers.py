
from rest_framework import serializers
from rest_framework.fields import ReadOnlyField
from reportes.models import Estadisticos, Regiones, Estad_NacReg


class EstadisticoSerializer(serializers.ModelSerializer):
    Region = ReadOnlyField(source='Region.Region')
    Estado = ReadOnlyField(source='Estado.ClaveEdo')

    class Meta:
        model = Estadisticos
        fields = ('id','NoHato','FechaVisita','Region','Estado','VacasHato','LechekgH','LecheEM','DiasLeche','PVacasCarga',
                  'DiasPico','LechePico','PDesecho','EdadMM','Dias1Serv','PFertil1Serv','InterServ',
                  'ServxConc','DiasAb','InterPartos','PAbortos','PGrasa','PProteina','PSolidos','NitrogU','CCSMiles',
                  )


class Estad_NacRegSerializer(serializers.ModelSerializer):

    class Meta:
        model = Estad_NacReg
        fields = ('id','Tipo','FechaProceso','VacasHato','LechekgH','LecheEM','DiasLeche','PVacasCarga','DiasPico',
                  'LechePico','PDesecho','EdadMM','Dias1Serv','PFertil1Serv','InterServ','ServxConc','DiasAb',
                  'InterPartos','PAbortos','PGrasa','PProteina','PSolidos','NitrogU','CCSMiles',)
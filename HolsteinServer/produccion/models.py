# coding=utf-8
from django.db import models


class CapturaProd(models.Model):
    """
    Valores posibles para CodEst
        1,2 = parida
        3= comprada seca
        4= comprada en leche
        5= aborto
        6= seca
        7= vendida
        8= rastro
        9= muerta
    """
    id = models.IntegerField(primary_key=True)
    NoHato = models.CharField(max_length=10, null=True)    # ** X1...X6 varchar(10) Número de hato
    NoVaca = models.CharField(max_length=6, null=True)     # ** X1...X6 varchar(6) No. de Vaca o NoCP.
    Curv = models.IntegerField(null=True)                  # ** 999999 int Identificador único.
    NumOrd = models.IntegerField(null=True)                # 9 tinyint Número de ordeñas 2 ó 3
    Lab = models.IntegerField(null=True)                   # 9 tinyint Laboratorio 1= Si, 0= No
    FechaEst = models.DateField(null=True)                 # ** dd/mm/aaaa datetime Fecha del estado de la vaca.
    CodEst = models.CharField(max_length=1, null=True)     # ** 9 varchar(1) Código del estado de la vaca.
    FechaPrueba = models.DateField(null=True)              # dd/mm/aaaa datetime Fecha cuando se muestrea el hato.
    Hora1Ini = models.CharField(max_length=5, null=True)   # hh:mm varchar(5) Horario de inicio de la ordeña 1
    Hora1Fin = models.CharField(max_length=5, null=True)   # hh:mm varchar(5) Horario final de la ordeña 1
    Hora2Ini = models.CharField(max_length=5, null=True)   # hh:mm varchar(5) Horario de inicio de la ordeña 2
    Hora2Fin = models.CharField(max_length=5, null=True)   # hh:mm varchar(5) Horario final de la ordeña 2
    Hora3Ini = models.CharField(max_length=5, null=True)   # hh:mm varchar(5) Horario de inicio de la ordeña 3
    Hora3Fin = models.CharField(max_length=5, null=True)   # hh:mm varchar(5) Horario final de la ordeña 3
    Peso1 = models.IntegerField(null=True)                 # 999 smallint Peso en kgs. de la ordeña 1 multiplicado *10
    Peso2 = models.IntegerField(null=True)                 # 999 smallint Peso en kgs. de la ordeña 2 multiplicado *10
    Peso3 = models.IntegerField(null=True)                 # 999 smallint Peso en kgs. de la ordeña 3 multiplicado *10
    LoteT = models.IntegerField(null=True)                 # 9 tinyint Número de lote o corral (0 a 9)
    NoVial = models.CharField(max_length=6, null=True)     # 999999 varcha(6) Número de vial en el que se toma la muestra de leche.
    Fecha = models.DateField()                             # Fecha cuando se genera el reporte.

    class Meta:
        db_table = 'CapturaProd'

    def __unicode__(self):
        return '%s (%s)' % (self.NoVaca, self.NoHato)
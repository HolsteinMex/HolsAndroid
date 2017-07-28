# coding=utf-8
from django.db import models


class Estados(models.Model):
    ClaveEdo = models.IntegerField(primary_key=True)
    Abrev = models.CharField(max_length=6)

    class Meta:
        db_table = 'Estados'

    def __unicode__(self):
        return self.Abrev


class Regiones(models.Model):
    Region = models.IntegerField(primary_key=True)
    Nombre = models.CharField(max_length=50)

    class Meta:
        db_table = 'Regiones'


    def __unicode__(self):
        return self.Nombre


class Estadisticos(models.Model):
    id = models.IntegerField(primary_key=True)
    NoHato = models.CharField(max_length=10)
    FechaVisita = models.DateTimeField()
    Region = models.ForeignKey(Regiones, db_column='Region')
    Estado = models.ForeignKey(Estados,db_column='Estado')
    VacasHato = models.IntegerField()
    LechekgH = models.DecimalField(max_digits=10, decimal_places=3)
    LecheEM = models.DecimalField(max_digits=10, decimal_places=3)
    DiasLeche = models.DecimalField(max_digits=10, decimal_places=3)
    PVacasCarga = models.DecimalField(max_digits=10, decimal_places=3)
    DiasPico = models.DecimalField(max_digits=10, decimal_places=3)
    LechePico = models.DecimalField(max_digits=10, decimal_places=3)
    PDesecho = models.IntegerField()
    EdadMM = models.DecimalField(max_digits=10, decimal_places=3)
    Dias1Serv = models.IntegerField()
    PFertil1Serv = models.IntegerField()
    InterServ = models.DecimalField(max_digits=10, decimal_places=3)
    ServxConc = models.DecimalField(max_digits=10, decimal_places=3)
    DiasAb = models.IntegerField()
    InterPartos = models.DecimalField(max_digits=10, decimal_places=3)
    PAbortos = models.IntegerField()
    PGrasa = models.DecimalField(max_digits=10, decimal_places=3)
    PProteina = models.DecimalField(max_digits=10, decimal_places=3)
    PSolidos = models.DecimalField(max_digits=10, decimal_places=3)
    NitrogU = models.DecimalField(max_digits=10, decimal_places=3)
    CCSMiles = models.IntegerField()
    Procesado = models.IntegerField(default=0)

    class Meta:
        db_table = 'Estadisticos'
    def __unicode__(self):
        return '%s (%s) - Status:%s' % (self.NoHato, self.FechaVisita,self.Procesado)


class Estad_NacReg(models.Model):
    Tipo = models.CharField(max_length=4)
    FechaProceso = models.DateTimeField()
    VacasHato = models.IntegerField()
    LechekgH = models.DecimalField(max_digits=10, decimal_places=0)
    LecheEM = models.DecimalField(max_digits=10, decimal_places=0)
    DiasLeche = models.DecimalField(max_digits=10, decimal_places=0)
    PVacasCarga = models.DecimalField(max_digits=10, decimal_places=0)
    DiasPico = models.DecimalField(max_digits=10, decimal_places=0)
    LechePico = models.DecimalField(max_digits=10, decimal_places=1)
    PDesecho = models.IntegerField()
    EdadMM = models.DecimalField(max_digits=10, decimal_places=0)
    Dias1Serv = models.IntegerField()
    PFertil1Serv = models.IntegerField()
    InterServ = models.DecimalField(max_digits=10, decimal_places=0)
    ServxConc = models.DecimalField(max_digits=10, decimal_places=1)
    DiasAb = models.IntegerField()
    InterPartos = models.DecimalField(max_digits=10, decimal_places=1)
    PAbortos = models.IntegerField()
    PGrasa = models.DecimalField(max_digits=10, decimal_places=2)
    PProteina = models.DecimalField(max_digits=10, decimal_places=2)
    PSolidos = models.DecimalField(max_digits=10, decimal_places=2)
    NitrogU = models.DecimalField(max_digits=10, decimal_places=2)
    CCSMiles = models.IntegerField()

    class Meta:
        db_table = 'Estad_NacReg'
    def __unicode__(self):
        return '%s (%s)' % (self.Tipo,self.FechaProceso)
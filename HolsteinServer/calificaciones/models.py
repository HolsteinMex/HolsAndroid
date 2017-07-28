# coding=utf-8
from django.db import models


class CalifVaca(models.Model):
    #id = models.IntegerField(primary_key=True)           # IDENTIFICADOR ÚNICO
    NoHato = models.CharField(max_length=10)              # Número de hato.                                                  varchar(10)
    Fecha = models.DateField()                            # Fecha cuando se genera el reporte.                               datetime
    Curv = models.IntegerField()                          # Identificador único.                                             int
    NoCP = models.CharField(max_length=6)                 # No. de Vaca o NoCP.                                              varchar(6)
    NoRegistro = models.CharField(max_length=10)          # No. de registro de la vaca.                                      varchar(10)
    RegPadre = models.CharField(max_length=10)            # No. de registro del padre.                                       varchar(10)
    RegMadre = models.CharField(max_length=10)            # No. de registro de la madre.                                     varchar(10)
    FechaNac = models.DateField()                         # Fecha de nacimiento.                                             datetime
    NoLactancia = models.IntegerField()                   # Número de parto                                                  tinyint
    FechaParto = models.DateField()                       # Fecha de parto.                                                  datetime
    CalifAnt = models.IntegerField()                      # Calificación anterior, son los puntos.                           tinyint
    ClaseAnt = models.CharField(max_length=3)             # Clase anterior.                                                  varchar(3)
    TipoParto = models.CharField(max_length=1)            # N=Normal, A=Anormal                                              char(1)
    LacNorm = models.IntegerField()                       # Número de lactancias normales.                                   tinyint
    MesesAborto = models.IntegerField()                   # Meses al aborto.                                                 tinyint
    c1 = models.IntegerField(null=True)                   # valor de 1 a 9 para Estatura                                     tinyint
    c2 = models.IntegerField(null=True)                   # valor de 1 a 9 para Altura a la cruz                             tinyint
    c3 = models.IntegerField(null=True)                   # valor de 1 a 9 para Tamaño                                       tinyint
    c4 = models.IntegerField(null=True)                   # valor de 1 a 9 para Anchura de pecho                             tinyint
    c5 = models.IntegerField(null=True)                   # valor de 1 a 9 para Profundidad corporal                         tinyint
    c6 = models.IntegerField(null=True)                   # valor de 1 a 9 para Fortaleza de lomo                            tinyint
    c7 = models.IntegerField(null=True)                   # valor de 1 a 9 para Punta o inclinación de anca                  tinyint
    c8 = models.IntegerField(null=True)                   # valor de 1 a 9 para Anchura de anca                              tinyint
    c8a = models.IntegerField(null=True)                  # valor de 1 a 9 para Colocación de la cadera                      tinyint
    c9 = models.IntegerField(null=True)                   # valor de 1 a 9 para Angulo pezuña                                tinyint
    c10 = models.IntegerField(null=True)                  # valor de 1 a 9 para Upezuña                                      tinyint
    c11 = models.IntegerField(null=True)                  # valor de 1 a 9 para Porfundidad de talón                         tinyint
    c12 = models.IntegerField(null=True)                  # valor de 1 a 9 para Calidad de huesos                            tinyint
    c13 = models.IntegerField(null=True)                  # valor de 1 a 9 para Aplomo o Patas traseras vista lateral        tinyint
    c14 = models.IntegerField(null=True)                  # valor de 1 a 9 para Vista posterior                              tinyint
    c15 = models.IntegerField(null=True)                  # valor de 1 a 9 para Profundidad ubre                             tinyint
    c16 = models.IntegerField(null=True)                  # valor de 1 a 9 para Textura ubre                                 tinyint
    c17 = models.IntegerField(null=True)                  # valor de 1 a 9 para Ligamento medio suspensorio                  tinyint
    c18 = models.IntegerField(null=True)                  # valor de 1 a 9 para Inserción delantera                          tinyint
    c19 = models.IntegerField(null=True)                  # valor de 1 a 9 para Posición delantera                           tinyint
    c20 = models.IntegerField(null=True)                  # valor de 1 a 9 para Longitud de pezones                          tinyint
    c21 = models.IntegerField(null=True)                  # valor de 1 a 9 para Altura ubre posterior                        tinyint
    c22 = models.IntegerField(null=True)                  # valor de 1 a 9 para Anchura ubre posterior                       tinyint
    c23 = models.IntegerField(null=True)                  # valor de 1 a 9 para Posición ubre posterior                      tinyint
    c24 = models.IntegerField(null=True)                  # valor de 1 a 9 para Angularidad                                  tinyint
    ss1 = models.CharField(max_length=3, null=True)       # Clase que otorga el calificador al sistema1 Fortaleza Lechera    varchar(3)
    ss2 = models.CharField(max_length=3, null=True)       # Clase que otorga el calificador al sistema2 Anca                 varchar(3)
    ss3 = models.CharField(max_length=3, null=True)       # Clase que otorga el calificador al sistema3 Patas y pezuñas      varchar(3)
    ss4 = models.CharField(max_length=3, null=True)       # Clase que otorga el calificador al sistema4 Sistema mamario      varchar(3)
    Def10 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def11 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def12 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def22 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def23 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def24 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def25 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def26 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def27 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def28 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def29 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def30 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def31 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def32 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def34 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def35 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def36 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def40 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def42 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def43 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def44 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def45 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def46 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def47 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Def48 = models.IntegerField(default=0)                # valor 1 para indicar defecto                                     bit
    Puntos = models.IntegerField(null=True)               # Puntos finales que otorga la aplicación.                         tinyint
    Clase = models.CharField(max_length=3,null=True)      # Clase final que otorga la aplicación.                            varchar(3)
    PuntosCalif = models.IntegerField(null=True)          # Puntos finales que otorga el calificador.                        tinyint
    ClaseCalif = models.CharField(max_length=3,null=True) # Clase final que otorga el calificador.                           varchar(3)
    FechaCalif = models.DateField(null=True)              # Fecha de calificación                                            datetime
    ss1puntos = models.IntegerField(null=True)            # Puntos que otorga el calificador al sistema1 Fortaleza Lechera   tinyint
    ss2puntos = models.IntegerField(null=True)            # Puntos que otorga el calificador al sistema2 Anca                tinyint
    ss3puntos = models.IntegerField(null=True)            # Puntos que otorga el calificador al sistema3 Patas y pezuñas     tinyint
    ss4puntos = models.IntegerField(null=True)            # Puntos que otorga el calificador al sistema4 Sistema mamario     tinyint
    FechaCalifAnt = models.DateField(null=True)              # Fecha de calificación                                            datetime

    class Meta:
        db_table = 'CalifVaca'

    def __unicode__(self):
        return '%s (%s)' % (self.NoRegistro, self.NoHato)


class CalifToro(models.Model):
    #id = models.IntegerField(primary_key=True)
    NoHato = models.CharField(max_length=10,null=True)       # Número de hato.                                                varchar(10)
    Fecha = models.DateField(null=True)                      # Fecha cuando se genera el reporte.                             datetime
    NoRegistro = models.CharField(max_length=10,null=True)   # No. de registro del toro.                                      varchar(10)
    RegPadre = models.CharField(max_length=10,null=True)     # No. de registro del padre.                                     varchar(10)
    RegMadre = models.CharField(max_length=10,null=True)     # No. de registro de la madre.                                   varchar(10)
    FechaNac = models.DateField(null=True)                   # Fecha de nacimiento.                                           datetime
    CalifAnt = models.IntegerField(null=True)                # Calificación anterior, son los puntos.                         tinyint
    ClaseAnt = models.CharField(max_length=3,null=True)      # Clase anterior.                                                varchar(3)
    Nombre = models.CharField(max_length=30,null=True)       # Nombre del toro                                                 varchar(30)
    c1 = models.IntegerField(null=True)                      # valor de 1 a 9 para Inclinación del anca.                       tinyint
    c2 = models.IntegerField(null=True)                      # valor de 1 a 9 para Anchura de anca.                            tinyint
    c3 = models.IntegerField(null=True)                      # valor de 1 a 9 para Fortaleza lomo.                             tinyint
    c4 = models.IntegerField(null=True)                      # valor de 1 a 9 para Colocación de la cadera.                    tinyint
    ss1 = models.CharField(max_length=3,null=True)           # Clase que otorga el calificador al sistema1 Anca.               varchar(3)
    ss1puntos = models.IntegerField(null=True)               # Puntos que otorga el calificador al sistema1 Anca.              tinyint
    c5 = models.IntegerField(null=True)                      # valor de 1 a 9 para Estatura.                                   tinyint
    c6 = models.IntegerField(null=True)                      # valor de 1 a 9 para Altura a la cruz.                           tinyint
    c7 = models.IntegerField(null=True)                      # valor de 1 a 9 para Anchura de pecho.                           tinyint
    c8 = models.IntegerField(null=True)                      # valor de 1 a 9 para Profundidad corporal.                       tinyint
    c9 = models.IntegerField(null=True)                      # valor de 1 a 9 para Angularidad.                                tinyint
    c10 = models.IntegerField(null=True)                     # valor de 1 a 9 para Tamaño.                                     tinyint
    ss2 = models.CharField(max_length=3,null=True)           # Clase que otorga el calificador al sistema2 Fortaleza lechera.  varchar(3)
    ss2puntos = models.IntegerField(null=True)               # Puntos que otorga el calificador al sistema2 Fortaleza lechera. tinyint
    c11 = models.IntegerField(null=True)                     # valor de 1 a 9 para Ángulo pezuña.                              tinyint
    c12 = models.IntegerField(null=True)                     # valor de 1 a 9 para Profundidad de talón.                       tinyint
    c13 = models.IntegerField(null=True)                     # valor de 1 a 9 para Calidad de huesos.                          tinyint
    c14 = models.IntegerField(null=True)                     # valor de 1 a 9 para Aplomo o Patas traseras vista lateral       tinyint
    c15 = models.IntegerField(null=True)                     # valor de 1 a 9 para patas posteriores vista trasera.            tinyint
    ss3 = models.CharField(max_length=3,null=True)           # Clase que otorga el calificador al sistema3 Patas y pezuñas.    varchar(3)
    ss3puntos = models.IntegerField(null=True)               # Puntos que otorga el calificador al sistema3 Patas y pezuñas.   tinyint
    Def10 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def11 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def40 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def42 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def43 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def44 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def45 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def30 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def31 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def32 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def34 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def35 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    Def36 = models.IntegerField(default=0)                   # valor 1 para indicar defecto                                    bit
    PuntosCalif = models.IntegerField(null=True)             # Puntos finales que otorga el calificador.                       tinyint
    ClaseCalif = models.CharField(max_length=3,null=True)    # Clase final que otorga el calificador.                          varchar(3)
    FechaCalif = models.CharField(max_length=30,null=True)   # Fecha de calificación                                           datetime
    Comentarios = models.CharField(max_length=50,null=True)  # Comentarios                                                     varchar(50)

    class Meta:
        db_table = 'CalifToro'

    def __unicode__(self):
        return '%s' % self.id


class Socios(models.Model):
    NoHato = models.CharField(max_length=10)
    Nombre = models.CharField(max_length=80)

    class Meta:
        db_table = 'Socios'

    def __unicode__(self):
        return self.Nombre
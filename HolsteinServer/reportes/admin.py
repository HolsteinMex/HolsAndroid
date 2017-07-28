from django.contrib import admin
from reportes.models import Estadisticos, Regiones, Estados, Estad_NacReg

admin.site.register(Regiones)
admin.site.register(Estados)
admin.site.register(Estadisticos)
admin.site.register(Estad_NacReg)
from django.conf.urls import patterns, include, url
from django.contrib import admin

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'Estadisticos.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^accounts/login/$', 'administracion.views.login_view'),
    url(r'^$', 'administracion.views.home', name='home'),
    url(r'^administracion/', include('administracion.urls')),

    url(r'^registros/$', 'android_push.views.registros', name='registros'),

    url(r'^API/registrar/$', 'android_push.views.registra_android', name='registrar'),
    url(r'^verificar/$', 'reportes.views.verificar_reportes', name='verificar_reportes'),
    url(r'^push/$', 'android_push.views.android_push'),
    url(r'^API/estadisticos/$', 'reportes.views.estadisticos'),
    url(r'^API/estgenerales/$', 'reportes.views.estadisticos_general'),
    url(r'^API/calificaciones/$', 'calificaciones.views.calificaciones'),
    url(r'^API/calificacionestoro/$', 'calificaciones.views.calificaciones_toro'),
   url(r'^API/socios/$', 'calificaciones.views.socios'),
    url(r'^API/enviar_vacas/$', 'calificaciones.views.recibir_vacas'),
    url(r'^API/enviar_toros/$', 'calificaciones.views.recibir_toros'),

    url(r'^API/produccion/$', 'produccion.views.vacas_produccion'),
)

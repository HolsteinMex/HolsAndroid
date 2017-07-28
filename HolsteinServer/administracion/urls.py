from django.conf.urls import patterns, url

urlpatterns = patterns('',

    #url(r'^encriptar/$', 'administracion.views.encriptar'),
    #url(r'^desencriptar/$', 'administracion.views.desencriptar'),


    url(r'^login/$', 'administracion.views.login_view', name='login'),
    url(r'^logout/$', 'administracion.views.logout_view', name='logout'),

    url(r'^usuarionuevo/$', 'administracion.views.nuevo_usuario', name='usuarioNuevo'),
    url(r'^usuarios/$', 'administracion.views.usuarios', name='usuarios'),
    url(r'^usuarios/(\d+)$', 'administracion.views.usuarios', name='editar_usuario'),
    url(r'^eliminarusr/(\d+)$', 'administracion.views.eliminar_usuario', name='eliminar_usuario'),
    url(r'^cambiarpsw/(\d+)$', 'administracion.views.cambiar_pass', name='cambiarpsw'),

    url(r'^grupos/$', 'administracion.views.grupos', name='grupos'),
    url(r'^grupos/(\d+)$', 'administracion.views.grupos', name='editar_grupos'),
    url(r'^eliminargpo/(\d+)$', 'administracion.views.eliminar_grupo', name='eliminar_grupo'),


    url(r'^menus/$', 'administracion.views.menus', name='menus'),
    url(r'^menus/(\d+)$', 'administracion.views.menus', name='menus'),
    url(r'^eliminarmnu/(?P<id_menu>\d+)$', 'administracion.views.eliminar_menu', name='eliminar_menu'),

)
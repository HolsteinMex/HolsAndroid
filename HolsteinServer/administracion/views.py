# -*- encoding: utf-8 -*-


from django.contrib import auth
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.contrib.auth.views import login, logout
from django.core.urlresolvers import reverse
from django.http import HttpResponseRedirect, HttpResponse, Http404
from django.shortcuts import render, get_object_or_404, redirect


from administracion.forms import UsuarioEditarForm, PerfilForm, UsuarioForm, GrupoForm, \
    MenuForm, LoginForm
from administracion.models import Perfil, Grupo, Menu
from administracion.serializers import UserSerializer


def login_view(request):
    """
    Vista para iniciar sesión.

    Verifica que se encuentre el usuario registrado, si se encuentra activo y de ser así, lo loguea en el sistema.
    :param request:
    "username" - Nombre de Usuario
    "password" - Contraseña
    """
    if request.user.is_authenticated():
        return HttpResponseRedirect(reverse('home'))
    if request.method == 'POST':
        form = LoginForm(data=request.POST)
        if form.is_valid():
            usuario = request.POST['username']
            clave = request.POST['password']
            acceso = auth.authenticate(username=usuario, password=clave)
            if acceso is not None:
                if acceso.is_active:
                    login(request, acceso)

                    return HttpResponseRedirect(reverse('home'))
                else:
                    form = LoginForm()
                    script = "alert('Usuario no activo');"
                    return render(request, 'login.html', locals())
            else:
                form = LoginForm()
                return render(request, 'login.html', locals())
    else:
        form = LoginForm()
    return render(request, 'login.html', locals())


def logout_view(request):
    logout(request)
    return HttpResponseRedirect(reverse('home'))

@login_required()
def home(request):
    """
    Index y/o home del sitio.
    """
    return render(request, 'index.html', locals())


@login_required()
def usuarios(request, id_usuario=None):
    """
    Página para el registro y modificación de usuarios.
    :param request:
    Si es un POST entonces se obtienen los datos del formulario
    :param id_usuario: ID de Usuario para la consulta de información
    """
    script_ready = "$('#id_grupo').multiSelect();"
    usuarios = User.objects.all()
    if request.method == 'POST':
        if id_usuario is not None:
            usr = User.objects.get(pk=id_usuario)
            prf = Perfil.objects.get(usuario=usr)
            usuario_form = UsuarioEditarForm(request.POST, instance=usr)
            perfil_form = PerfilForm(request.POST, instance=prf)
        else:
            usuario_form = UsuarioEditarForm(request.POST)
            perfil_form = PerfilForm(request.POST)
        if usuario_form.is_valid() and perfil_form.is_valid():
            if id_usuario is None:
                usuario = User.objects.create_user(username=request.POST.get("username", ""),
                                                   email=request.POST.get("email", ""),
                                                   password=request.POST.get("password", ""))
                usuario.first_name = request.POST.get("first_name", "")
                usuario.last_name = request.POST.get("last_name", "")
                usuario.is_active = request.POST.get("is_active", "True")
                usuario.save()

            else:
                usuario = usuario_form.save(commit=False)
                if request.POST.get("password", "") is not "":
                    usuario.set_password(request.POST.get("password", ""))
                usuario.save()

            perfil = perfil_form.save(commit=False)
            perfil.usuario = usuario
            perfil.save()
            perfil.grupo.clear()
            grupos = request.POST.getlist("grupo")
            gpo = Grupo.objects.filter(pk__in=grupos)
            for m in gpo:
                perfil.grupo.add(m)
            perfil.save()
            return HttpResponseRedirect(reverse('usuarios'))
    if id_usuario is not None:
        usr = User.objects.get(pk=id_usuario)
        prf = Perfil.objects.get(usuario=usr)
        usuario_form = UsuarioEditarForm(instance=usr)
        perfil_form = PerfilForm(instance=prf)
        script_ready += "$('#btnEliminarUsuario').removeProp('hidden');$('#btnCambiar').removeProp('hidden');" \
                        "$('#contraForm').validator();"
        usuario_accion = 'Editar Usuario "' + usr.username + '"'
    else:
        usuario_form = UsuarioForm(request.POST or None)
        perfil_form = PerfilForm(request.POST or None)
        usuario_accion = 'Nuevo Usuario'

    return render(request, 'usuarios.html', locals())


@login_required()
def cambiar_pass(request, id_usuario):
    """
    Cambia la contraseña del usuario
    :param request: inputPassword
    :param id_usuario: Id del usuario
    :return: Redirecciona a la vista de usuarios
    """
    if request.method == 'POST':
        contra = request.POST.get("inputPassword", "")
        contra2 = request.POST.get("inputPassword", "")
        usuario = User.objects.get(pk=id_usuario)
        usuario.set_password(contra)
        usuario.save()
        if usuario.username == request.user.username:
            return HttpResponseRedirect(reverse('login'))
        return HttpResponseRedirect(reverse('usuarios'))
    else:
        return Http404


@login_required()
def nuevo_usuario(request):
    """
    Crea un usuario Nuevo
    """
    scripts = '<link href="/static/css/multi-select.css" rel="stylesheet" type="text/css"/><script ' \
              'src="/static/js/jquery.multi-select.js" type="text/javascript"></script>'
    script_ready = "$('#id_grupo').multiSelect()"
    usuario_form = UsuarioForm(request.POST or None)
    perfil_form = PerfilForm(request.POST or None)
    if request.method == 'POST':
        if usuario_form.is_valid() and perfil_form.is_valid():
            usuario = usuario_form.save()
            perfil = perfil_form.save(commit=False)
            perfil.usuario = usuario
            perfil.save()
    return render(request, 'nuevousuario.html', locals())


@login_required()
def eliminar_usuario(request, id_usuario):
    """
    Elimina el usuario con id="id_usuario"
    """
    usuario = User.objects.get(pk=id_usuario)
    perfil = Perfil.objects.get(usuario=usuario)
    usuario.delete()
    perfil.delete()

    return HttpResponseRedirect(reverse('usuarios'))


@login_required()
def grupos(request, id_grupo=None):
    """
    Gestiona los grupos y asigna los menus a cada grupo
    """
    script_ready = "$('#id_menus').multiSelect();"
    grupos = Grupo.objects.all()
    if request.method == 'POST':
        if id_grupo is not None:
            gpo = Grupo.objects.get(pk=id_grupo)
            grupo_form = GrupoForm(request.POST, instance=gpo)
        else:
            grupo_form = GrupoForm(request.POST)
        if grupo_form.is_valid():
            grupo = grupo_form.save()
            grupo.menus.clear()
            menus = request.POST.getlist("menus")
            mnu = Menu.objects.filter(pk__in=menus)
            for m in mnu:
                grupo.menus.add(m)
            grupo.save()
            return HttpResponseRedirect(reverse('grupos'))
    if id_grupo is not None:
        gpo = Grupo.objects.get(pk=id_grupo)
        grupo_form = GrupoForm(instance=gpo)
        script_ready += "$('#btnEliminarGrupo').removeProp('hidden');"
        accion = 'Editar Grupo "' + gpo.nombre + '"'
    else:
        grupo_form = GrupoForm(request.POST or None)
        accion = 'Nuevo Grupo'
    return render(request, 'grupos.html', locals())


@login_required()
def eliminar_grupo(request, id_grupo):
    """
    Elimina el menú con el id="id_grupo"
    """
    grupo = Grupo.objects.get(pk=id_grupo)
    grupo.delete()
    return HttpResponseRedirect(reverse('grupos'))


@login_required()
def menus(request, id_menu=None):
    """
    Gestión de menús
    """
    menus = Menu.objects.all()
    if request.method == 'POST':
        if id_menu is not None:
            mnu = Menu.objects.get(pk=id_menu)
            menu_form = MenuForm(request.POST, instance=mnu)
        else:
            menu_form = MenuForm(request.POST)
        if menu_form.is_valid():
            menu = menu_form.save()
            return HttpResponseRedirect(reverse('menus'))
    if id_menu is not None:
        mnu = Menu.objects.get(pk=id_menu)
        menu_form = MenuForm(instance=mnu)
        script_ready = "$('#btnEliminarMenu').removeProp('hidden');"
        accion = u'Editar Menú "' + mnu.nombre + '"'
    else:
        menu_form = MenuForm(request.POST or None)
        accion = u'Nuevo Menú'
    return render(request, 'menus.html', locals())


@login_required()
def eliminar_menu(request, id_menu):
    """
    Elimina el menú con id="id_menu"
    """
    menu = Menu.objects.get(pk=id_menu)
    menu.delete()
    return HttpResponseRedirect(reverse('menus'))
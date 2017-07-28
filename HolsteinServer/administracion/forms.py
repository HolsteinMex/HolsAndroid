# -*- encoding: utf-8 -*-
from django import forms
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.models import User
from django.forms import ModelForm, Textarea

from administracion.models import Perfil, Menu, Grupo


class LoginForm(AuthenticationForm):
    error_messages = {
        'invalid_login':u"El usuario y/o contraseña son incorrectos. Verifique números, mayúsculas y caracteres especiales.",
        'inactive': u"La cuenta está inactiva.",
    }


class UsuarioForm(ModelForm):
    """
    Formulario para registrar un usuario
    """
    confirmar = forms.CharField(widget=forms.PasswordInput())

    class Meta:
        model = User
        fields = ("username", "password", "confirmar", "first_name", "last_name", "email", "is_active",)
        widgets = {
            'password': forms.PasswordInput(),
            'confirmar': forms.PasswordInput(),
        }
        labels = {
            "email": u"Correo Electrónico",
        }

    def clean(self):
        password = self.cleaned_data.get('password')
        confirmar = self.cleaned_data.get('confirmar')

        if password and password != confirmar:
            raise forms.ValidationError("No coinciden las contraseñas")
        return self.cleaned_data


class UsuarioEditarForm(ModelForm):
    """
    Formulario para editar un Usuario
    """

    class Meta:
        model = User
        fields = ("first_name", "last_name", "email", "is_active",)
        labels = {
            "email": u"Correo Electrónico",
        }


class GrupoForm(ModelForm):
    """
    Formulario para Grupos
    """

    class Meta:
        model = Grupo
        exclude = ('bloqueado',)

    def __init__(self, *args, **kwargs):
        super(GrupoForm, self).__init__(*args, **kwargs)
        self.fields['menus'].help_text = u'Pase a la derecha los Menús que desea incluir.'


class PerfilForm(ModelForm):
    """
    Formulario de Perfil
    """

    class Meta:
        model = Perfil
        exclude = ("usuario",)
        widgets = {
            'hatos': Textarea(attrs={'cols': 30, 'rows': 1}),
        }

    def __init__(self, *args, **kwargs):
        super(PerfilForm, self).__init__(*args, **kwargs)
        self.fields['grupo'].help_text = u'Pase a la derecha el grupo al que pertenece el usuario.'


class MenuForm(ModelForm):
    """
    Formulario de Menú
    """

    class Meta:
        model = Menu
        exclude = ('bloqueado',)
        widgets = {'icono': forms.HiddenInput()}
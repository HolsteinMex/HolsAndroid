# -*- encoding: utf-8 -*-
from django.core.urlresolvers import reverse
import unicodedata
from django.db.models import Q

from administracion.models import Menu, Grupo


def menu(request):
    """
    Crea un menú a partir de las asignaciones de grupo en el que se encuentre el usuario.
    """

    def elimina_tildes(s):
        """
        Elimina las tildes en una cadena de texto
        :param s: Cadena de texto
        :return: Cadena de texto sin acentos
        """
        return ''.join((c for c in unicodedata.normalize('NFD', s) if unicodedata.category(c) != 'Mn'))

    def obtener_hijos(hijos_db, hijos_padre, padre, nivel):
        """
        Esta función permite obtener los hijos del menú en el que se encuentre.

        Se utiliza para crear el arbol de menús como contest_processor
        :param hijos_db: Listado de menús hijo
        :param hijos_padre: Listado de menús hijo/padre
        :param padre: Menú padre
        :return: Listado de menús hijo del actual padre
        """
        hijos = []
        for hijo in hijos_db:
            if hijo.padre.nombre == padre.nombre:
                data_hijo = {"name": hijo.nombre, "id": elimina_tildes(hijo.nombre).replace(" ", "")}
                if hijo.url == '#':
                    data_hijo["url"] = '#'
                else:
                    data_hijo["url"] = reverse(hijo.url)
                data_hijo["padre"] = hijo.padre.nombre
                if hijo in hijos_padre:
                    data_hijo["menu"] = []
                    data_hijo["menu"].extend(obtener_hijos(hijos_db, hijos_padre, hijo,nivel+1))
                data_hijo["padre"] = False
                data_hijo["nivel"] = nivel
                hijos.append(data_hijo)
        return hijos
    if request.user.is_authenticated():
        grupos = Grupo.objects.filter(perfil__usuario__username=request.user.username)
        if request.user.is_superuser:
            menudb = Menu.objects.all()  # Superusuario de DJANGO
        else:
            menudb = Menu.objects.filter(grupo__in=grupos).distinct()
        if menudb.count() > 0:
            padresdb = menudb.filter(padre=None)
            hijosdb = menudb.exclude(padre=None)
            padre_pks = []
            for h in hijosdb:
                padre_pks.append(h.padre.pk)
            padres_hijosdb = menudb.filter(Q(padre=None) | Q(id__in=padre_pks))

            menus = {"menu": []}
            for m in padresdb:
                data = {"name": m.nombre, "id": elimina_tildes(m.nombre).replace(" ", "")}
                if m.url == '#':
                    data["url"] = '#'
                else:
                    data["url"] = reverse(m.url)
                data["padre"] = True
                data["icono"] = m.icono
                data["nivel"] = 1
                data["menu"] = []
                data["menu"].extend(obtener_hijos(hijosdb, padres_hijosdb, m,2))
                menus["menu"].append(data)

            # menus = {"menu":[]}
            # hijos = []
            # for m in hijosdb:
            # data = {}
            # data["name"]=m.nombre
            # data["id"]=elimina_tildes(m.nombre).replace(" ","")
            # if m.url=='#':
            # data["url"]='#'
            # else:
            # data["url"]=reverse(m.url)
            # data["padre"]=m.padre.nombre
            #
            # hijos.append(data)
            # for m in padresdb:
            # data = {}
            # data["name"]=m.nombre
            # data["id"]=elimina_tildes(m.nombre).replace(" ","")
            # if m.url=='#':
            # data["url"]='#'
            # else:
            # data["url"]=reverse(m.url)
            # data["menu"]=[]
            # for sub in hijos:
            #         if sub["padre"]==m.nombre:
            #             data["menu"].append(sub)
            #     menus["menu"].append(data)

            #logout = {"name": "Cerrar Sesión", "id": "cerrar_sesion", "url": reverse('logout'),"icono":"fa-sign-out", "padre":True}
            #menus["menu"].append(logout)
        else:
            menus = {'menu': [
                {'name': 'Admin', 'url': 'admin/'}
            ]}
            #logout = {"name": "Cerrar Sesión", "id": "cerrar_sesion", "url": reverse('logout'),"icono":"fa-sign-out", "padre":True}
            #menus["menu"].append(logout)
    else:
        menus = {'menu': [
            {'name': 'Iniciar Sesion', 'url': reverse('login')},
        ]}
    return menus
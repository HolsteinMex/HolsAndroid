from rest_framework.serializers import ModelSerializer
from produccion.models import CapturaProd


class CapturaProdSerializer(ModelSerializer):
    class Meta:
        model = CapturaProd
        fields = ('id', 'NoHato', 'NoVaca', 'Curv', 'FechaEst', 'CodEst', 'Fecha')

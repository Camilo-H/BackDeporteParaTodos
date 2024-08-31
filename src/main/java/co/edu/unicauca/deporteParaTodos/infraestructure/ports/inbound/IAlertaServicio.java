package co.edu.unicauca.deporteParaTodos.infraestructure.ports.inbound;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlertaEntidad;

public interface IAlertaServicio {
    public Iterable<AlertaEntidad> obtenerAlertas();
}

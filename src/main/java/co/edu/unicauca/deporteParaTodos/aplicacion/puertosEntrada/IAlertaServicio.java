package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlertaEntidad;

public interface IAlertaServicio {
    public Iterable<AlertaEntidad> obtenerAlertas();
}

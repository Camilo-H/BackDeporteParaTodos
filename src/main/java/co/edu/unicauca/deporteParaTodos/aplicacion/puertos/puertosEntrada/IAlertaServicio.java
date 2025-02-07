package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlertaEntidad;

public interface IAlertaServicio {
    public Iterable<AlertaEntidad> obtenerAlertas();
}

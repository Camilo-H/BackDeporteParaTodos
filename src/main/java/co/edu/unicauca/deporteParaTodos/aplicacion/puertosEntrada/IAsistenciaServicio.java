package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.AsistenciaEntidad;

public interface IAsistenciaServicio {
    public Iterable<AsistenciaEntidad> obtenerAsistencias();
}

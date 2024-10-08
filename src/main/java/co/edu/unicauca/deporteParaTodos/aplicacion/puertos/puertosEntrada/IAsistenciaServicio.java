package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AsistenciaEntidad;

public interface IAsistenciaServicio {
    public Iterable<AsistenciaEntidad> obtenerAsistencias();
}

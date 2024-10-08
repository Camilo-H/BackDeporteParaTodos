package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;

public interface IGrupoServicio {
    public Iterable<GrupoEntidad> obtenerGrupos();
}

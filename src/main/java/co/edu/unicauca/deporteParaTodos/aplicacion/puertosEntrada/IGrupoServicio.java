package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.GrupoEntidad;

public interface IGrupoServicio {
    public Iterable<GrupoEntidad> obtenerGrupos();
}

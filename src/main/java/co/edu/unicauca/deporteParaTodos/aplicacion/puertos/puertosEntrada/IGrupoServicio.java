package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.GrupoEntidad;

public interface IGrupoServicio {
    public Iterable<GrupoEntidad> obtenerGrupos();
}

package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.CursoEntidad;

public interface ICursoServicio {
    public Iterable<CursoEntidad> obtenerCursos();
}

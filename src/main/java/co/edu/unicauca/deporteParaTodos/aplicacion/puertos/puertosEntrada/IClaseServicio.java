package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;

public interface IClaseServicio {
    public Iterable<ClaseEntidad> obtenerClases();
}

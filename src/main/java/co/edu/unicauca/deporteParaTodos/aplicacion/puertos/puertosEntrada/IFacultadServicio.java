package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.FacultadEntidad;

public interface IFacultadServicio {
    public Iterable<FacultadEntidad> obtenerFacultades();
}

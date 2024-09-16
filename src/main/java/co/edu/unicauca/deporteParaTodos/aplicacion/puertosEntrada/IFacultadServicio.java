package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.FacultadEntidad;

public interface IFacultadServicio {
    public Iterable<FacultadEntidad> obtenerFacultades();
}

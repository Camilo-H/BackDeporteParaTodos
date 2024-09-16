package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.FacultadEntidad;

public interface IFacultadServicio {
    public Iterable<FacultadEntidad> obtenerFacultades();
}

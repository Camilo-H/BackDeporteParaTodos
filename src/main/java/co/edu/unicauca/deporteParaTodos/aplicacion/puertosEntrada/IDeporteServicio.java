package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.DeporteEntidad;

public interface IDeporteServicio {
    public Iterable<DeporteEntidad> obtenerDeportes();
}

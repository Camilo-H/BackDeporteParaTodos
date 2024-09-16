package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.DeporteEntidad;

public interface IDeporteServicio {
    public Iterable<DeporteEntidad> obtenerDeportes();
}

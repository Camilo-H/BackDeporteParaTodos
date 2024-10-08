package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.DeporteEntidad;

public interface IDeporteServicio {
    public Iterable<DeporteEntidad> obtenerDeportes();
}

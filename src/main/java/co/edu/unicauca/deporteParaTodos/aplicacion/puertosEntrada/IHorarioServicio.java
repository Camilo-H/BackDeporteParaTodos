package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.HorarioEntidad;

public interface IHorarioServicio {
    public Iterable<HorarioEntidad> obtenerHorarios();
}

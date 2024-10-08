package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InstructorEntidad;

public interface IInstructorServicio {
    public Iterable<InstructorEntidad> obtenerInstructores();
}

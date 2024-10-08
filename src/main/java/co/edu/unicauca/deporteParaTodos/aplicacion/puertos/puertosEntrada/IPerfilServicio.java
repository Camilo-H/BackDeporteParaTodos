package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;

public interface IPerfilServicio {
    public Iterable<PerfilEntidad> obtenerPerfiles();
}

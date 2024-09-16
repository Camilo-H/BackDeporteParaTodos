package co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.PerfilEntidad;

public interface IPerfilServicio {
    public Iterable<PerfilEntidad> obtenerPerfiles();
}

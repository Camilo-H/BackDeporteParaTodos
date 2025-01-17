package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;

public interface IGrupoServicio {
    public List<Grupo> obtenerGrupos();

    public Grupo insertarGrupo(Grupo datosGrupo);

    public Grupo obtenerGrupoPorId(String nombre, int anio, int iterable);

    public Grupo actualizarGrupo(String nombre, int anio, int iterable, Grupo datosGrupo);

    public Grupo eliminarGrupo(String nombre, int anio, int iterable);

}

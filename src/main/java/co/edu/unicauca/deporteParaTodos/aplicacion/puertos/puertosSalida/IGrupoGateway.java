package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;

public interface IGrupoGateway {
    public List<Grupo> obtenerGrupos();

    public boolean existeGrupo(String nombre, int anio, int iterable);
    
    public Grupo insertarGrupo(Grupo datosGrupo);

    public Optional<Grupo> obtenerGrupoPorId(String nombre, int anio, int iterable);

    public Grupo actualizarGrupo(String nombre, int anio, int iterable, Grupo datosGrupo);

    public Grupo eliminarGrupo(String nombre, int anio, int iterable);

}
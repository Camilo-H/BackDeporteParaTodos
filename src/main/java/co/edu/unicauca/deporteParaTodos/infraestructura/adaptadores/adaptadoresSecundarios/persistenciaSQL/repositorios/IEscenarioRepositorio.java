package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.EscenarioEntidad;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEscenarioRepositorio extends CrudRepository<EscenarioEntidad, Integer> {
    List<EscenarioEntidad> findByEliminado(int eliminado);
    boolean existsByNombre(String nombre);
}

package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.AsistenciaId;
import java.util.List;


public interface IAsistenciaRepositorio extends CrudRepository<AsistenciaEntidad,AsistenciaId>{
    List<AsistenciaEntidad> findByClaseCodigo(Integer claseCodigo);
    Boolean existsByClaseCodigoAndPerfilId(Integer claseCodigo, String perfilId);
}

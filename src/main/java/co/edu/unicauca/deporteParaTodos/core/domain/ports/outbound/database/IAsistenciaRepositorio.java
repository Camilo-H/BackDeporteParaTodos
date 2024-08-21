package co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.models.ids.AsistenciaId;

public interface IAsistenciaRepositorio extends CrudRepository<AsistenciaEntidad,AsistenciaId>{
    
}

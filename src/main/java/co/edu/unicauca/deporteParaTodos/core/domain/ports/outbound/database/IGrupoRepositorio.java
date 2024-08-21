package co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.models.ids.GrupoId;

public interface IGrupoRepositorio extends CrudRepository<GrupoEntidad, GrupoId>{
    
}

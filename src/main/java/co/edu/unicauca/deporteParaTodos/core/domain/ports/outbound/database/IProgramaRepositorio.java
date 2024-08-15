package co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ProgramaEntidad;

public interface IProgramaRepositorio extends CrudRepository<ProgramaEntidad, String>{
    
}

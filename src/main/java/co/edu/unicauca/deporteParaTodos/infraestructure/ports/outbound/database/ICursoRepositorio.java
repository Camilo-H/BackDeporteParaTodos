package co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.CursoEntidad;

public interface ICursoRepositorio extends CrudRepository<CursoEntidad,String>{
    
}

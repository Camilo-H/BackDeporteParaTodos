package co.edu.unicauca.deporteParaTodos.core.domain.ports.outbound.database;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.ClaseEntidad;

public interface IClaseRepositorio extends CrudRepository<ClaseEntidad,Integer>{
    
}

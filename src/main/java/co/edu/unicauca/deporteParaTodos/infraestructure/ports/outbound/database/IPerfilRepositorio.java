package co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.PerfilEntidad;

public interface IPerfilRepositorio extends CrudRepository<PerfilEntidad,String>{

}

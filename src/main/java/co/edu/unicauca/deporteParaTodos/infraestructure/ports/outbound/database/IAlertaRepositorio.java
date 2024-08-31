package co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.AlertaEntidad;
import co.edu.unicauca.deporteParaTodos.core.domain.models.ids.GrupoId;

public interface IAlertaRepositorio extends CrudRepository<AlertaEntidad,GrupoId>{

}

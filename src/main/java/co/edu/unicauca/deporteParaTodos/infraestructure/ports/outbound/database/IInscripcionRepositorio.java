package co.edu.unicauca.deporteParaTodos.infraestructure.ports.outbound.database;

import java.sql.Timestamp;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.core.domain.models.InscripcionEntidad;

public interface IInscripcionRepositorio extends CrudRepository<InscripcionEntidad,Timestamp> {
    
}

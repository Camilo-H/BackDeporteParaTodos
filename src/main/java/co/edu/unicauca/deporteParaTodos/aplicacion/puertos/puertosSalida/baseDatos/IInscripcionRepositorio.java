package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos;

import java.sql.Timestamp;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.InscripcionEntidad;

public interface IInscripcionRepositorio extends CrudRepository<InscripcionEntidad,Timestamp> {
    
}

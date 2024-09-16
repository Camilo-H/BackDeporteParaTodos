package co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.ids.AsistenciaId;

public interface IAsistenciaRepositorio extends CrudRepository<AsistenciaEntidad,AsistenciaId>{
    
}

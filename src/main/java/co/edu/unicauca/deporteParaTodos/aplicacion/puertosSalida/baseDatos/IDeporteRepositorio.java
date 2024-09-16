package co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.DeporteEntidad;

public interface IDeporteRepositorio extends CrudRepository<DeporteEntidad, String>{
    
}

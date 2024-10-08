package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.FacultadEntidad;

public interface IFacultadRepositorio extends CrudRepository<FacultadEntidad,String>{
    
}

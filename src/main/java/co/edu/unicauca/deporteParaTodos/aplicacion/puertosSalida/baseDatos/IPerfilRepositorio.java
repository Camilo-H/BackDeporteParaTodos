package co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.PerfilEntidad;

public interface IPerfilRepositorio extends CrudRepository<PerfilEntidad,String>{

}

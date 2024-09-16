package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos;

import org.springframework.data.repository.CrudRepository;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.AlertaEntidad;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.ids.GrupoId;

public interface IAlertaRepositorio extends CrudRepository<AlertaEntidad,GrupoId>{

}

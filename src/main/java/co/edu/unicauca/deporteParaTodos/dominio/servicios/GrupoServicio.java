package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.GrupoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IGrupoRepositorio;

@Service
public class GrupoServicio implements IGrupoServicio{
    
    @Autowired
    private IGrupoRepositorio repoGrupo;

    @Override
    public Iterable<GrupoEntidad> obtenerGrupos() {
        return repoGrupo.findAll();
    }


}

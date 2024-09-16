package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos.IGrupoRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.GrupoEntidad;

@Service
public class GrupoServicio implements IGrupoServicio{
    
    @Autowired
    private IGrupoRepositorio repoGrupo;

    @Override
    public Iterable<GrupoEntidad> obtenerGrupos() {
        return repoGrupo.findAll();
    }


}

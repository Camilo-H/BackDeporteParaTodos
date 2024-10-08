package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IPerfilServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;

@Service
public class PerfilServicio implements IPerfilServicio{
    
    @Autowired
    private IPerfilRepositorio repoPerfil;

    @Override
    public Iterable<PerfilEntidad> obtenerPerfiles() {
        Iterable<PerfilEntidad> perfiles = repoPerfil.findAll();
        return perfiles;
    }

}

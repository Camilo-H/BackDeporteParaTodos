package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertosEntrada.IPerfilServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertosSalida.baseDatos.IPerfilRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.PerfilEntidad;

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

package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAutenticacionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class AutenticacionServicio implements IAutenticacionServicio{
    @Autowired
    private IPerfilGateway gatePerfil;

    @Override
    public PerfilDto login(String email) {
        Perfil perfil = gatePerfil.obtenerUsuario(email);
        if(perfil==null){
            throw new NoExisteExcepcion();
        }
        PerfilDto dto = PerfilDto.fabricarDeModelo(perfil);
        return dto;
    }

    @Override
    public PerfilDto registrarAlumno(PerfilDto datosPerfil) {
        if (gatePerfil.existePerfil(datosPerfil.getId())) {
            throw new YaExisteElementoExcepcion("El perfil con la identificacion ya se encuentra registrado");
        }
        Perfil perfil = Perfil.fabricarDeDto(datosPerfil);
        if (perfil == null) {
            throw new ErrorInternoException();
        }
        Perfil perfilRegistrado = gatePerfil.registrarAlumno(perfil);
        PerfilDto respuesta = PerfilDto.fabricarDeModelo(perfilRegistrado);
        if (respuesta == null) {
            throw new ErrorInternoException();
        }
        return respuesta;
    }

    

    //El logOut es implementado por google internamente, no es necesario hacerlo aqui
    
}

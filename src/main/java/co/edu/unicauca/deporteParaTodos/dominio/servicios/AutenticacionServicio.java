package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAutenticacionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

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

    

    //El logOut es implementado por google internamente, no es necesario hacerlo aqui
    
}

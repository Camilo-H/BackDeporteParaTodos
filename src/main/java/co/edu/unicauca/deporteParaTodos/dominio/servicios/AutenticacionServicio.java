package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAutenticacionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.PerfilMapper;

@Service
public class AutenticacionServicio implements IAutenticacionServicio{

    // [DIAG-TEMP] eliminar tras validacion
    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacionServicio.class);

    @Autowired
    private IPerfilGateway gatePerfil;

    @Override
    public PerfilDto login(String email) {
        Perfil perfil = gatePerfil.obtenerUsuario(email);
        // [DIAG-TEMP] Perfil crudo de BD antes de conversion a DTO
        LOGGER.info("[DIAG] obtenerUsuario({}) → id={} correo={} rol={}",
                email,
                perfil != null ? perfil.getId() : "NULL",
                perfil != null ? perfil.getCorreo() : "NULL",
                perfil != null ? perfil.getRol() : "NULL");
        if(perfil==null){
            throw new NoExisteExcepcion();
        }
        return PerfilMapper.toDto(perfil);
    }

    @Override
    public PerfilDto registrarAlumno(PerfilDto datosPerfil) {
        if (gatePerfil.existePerfil(datosPerfil.getId())) {
            throw new YaExisteElementoExcepcion("El perfil con la identificacion ya se encuentra registrado");
        }
        Perfil perfil = PerfilMapper.fromDto(datosPerfil);
        Perfil perfilRegistrado = gatePerfil.registrarAlumno(perfil);
        return PerfilMapper.toDto(perfilRegistrado);
    }

    

    //El logOut es implementado por google internamente, no es necesario hacerlo aqui
    
}

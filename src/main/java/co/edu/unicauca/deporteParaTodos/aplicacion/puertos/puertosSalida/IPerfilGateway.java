package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;

public interface IPerfilGateway {

    public Perfil obtenerUsuario(String email);

    public boolean existePerfil(String perfilId);

    public List<Perfil> obtenerPerfiles();

    public Perfil insertarPerfil(Perfil perfil);

    public Perfil registrarAlumno(Perfil perfil);

    public Optional<Perfil> obtenerPerfil(String perfilId);

    public Perfil actualizarPerfil(String perfilId, Perfil datosPerfil);

    public Perfil eliminarPerfil(String perfilId);
}

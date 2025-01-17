package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import java.util.List;

public interface IPerfilServicio {
    public List<Perfil> obtenerPerfiles();

    public Perfil insertarPerfil(Perfil perfil);

    public Perfil obtenerPerfil(String perfilId);

    public Perfil actualizarPerfil(String perfilId, Perfil datosPerfil);

    public Perfil eliminarPerfil(String perfilId);
}

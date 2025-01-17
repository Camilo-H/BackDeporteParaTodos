package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;

public interface IFacultadServicio {

    public List<Facultad> obtenerFacultades();

    public Facultad obtenerFacultad(String nombre);

    public Facultad insertarFacultad(Facultad datosFacultad);

    public Facultad eliminarFacultad(String nombre);
}

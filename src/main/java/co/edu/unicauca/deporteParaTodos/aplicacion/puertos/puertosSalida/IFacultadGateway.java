
package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;

public interface IFacultadGateway {
    public boolean existeFacultad(String nombre);

    public List<Facultad> obtenerFacultades();

    public Optional<Facultad> obtenerFacultad(String nombre);

    public Facultad insertarFacultad(Facultad datosFacultad);

    public Facultad eliminarFacultad(String nombre);
}

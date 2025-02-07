package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;

public interface IClaseGateway {
    public boolean existeClase(int id);

    public List<Clase> obtenerClases();

    public Optional<Clase> obtenerClase(int id);

    public Clase insertarClase(Clase datoClase);

    public Clase actualizarClase(int id, Clase datoClase);

    public Clase eliminarClase(int id);
}

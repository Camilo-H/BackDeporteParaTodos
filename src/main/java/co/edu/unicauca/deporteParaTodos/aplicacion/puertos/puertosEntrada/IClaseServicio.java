package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;

public interface IClaseServicio {
    public List<Clase> obtenerClases();

    public Clase obtenerClase(int id);

    public Clase insertarClase(Clase datoClase);

    public Clase actualizarClase(int id, Clase datoClase);

    public Clase eliminarClase(int id);
}

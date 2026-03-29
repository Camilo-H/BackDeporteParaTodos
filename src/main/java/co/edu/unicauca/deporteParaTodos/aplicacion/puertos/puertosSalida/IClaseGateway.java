package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;

public interface IClaseGateway {
    public boolean existeClase(int id);

    public List<Clase> obtenerClasesGrupo(String categoria, String curso, Integer anio, Integer iterable);

    public Clase insertarClase(Clase datoClase);

    public Clase eliminarClase(int id);
}

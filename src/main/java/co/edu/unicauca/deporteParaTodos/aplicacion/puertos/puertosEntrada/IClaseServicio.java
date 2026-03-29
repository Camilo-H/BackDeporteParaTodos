package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ClaseDto;

public interface IClaseServicio {
    public List<ClaseDto> obtenerClasesGrupo(String categoria, String curso, Integer anio, Integer iterable);

    public ClaseDto insertarClase(ClaseDto datoClase);

    public ClaseDto eliminarClase(int id);
}

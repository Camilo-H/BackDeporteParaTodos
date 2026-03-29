package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;

public interface IDeporteServicio {
    //public Iterable<DeporteEntidad> obtenerDeportes();
    public List<DeporteDto> listaDeportes();
    public DeporteDto insertarDeporte(DeporteDto datosDeporte);
    public Deporte obtenerDeportePorId(String nombreDeporte);
    public Deporte actualizarDeporte(String nombre, Deporte datosDeporte);
    public Deporte eliminarDeporte(String nombreDeporte);

}

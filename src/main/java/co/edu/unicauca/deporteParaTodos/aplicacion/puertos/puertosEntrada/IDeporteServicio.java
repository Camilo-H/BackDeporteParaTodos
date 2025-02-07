package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import java.util.List;

public interface IDeporteServicio {
    //public Iterable<DeporteEntidad> obtenerDeportes();
    public List<Deporte> listaDeportes();
    public Deporte insertarDeporte(Deporte datosDeporte);
    public Deporte obtenerDeportePorId(String nombreDeporte);
    public Deporte actualizarDeporte(String nombre, Deporte datosDeporte);
    public Deporte eliminarDeporte(String nombreDeporte);

}

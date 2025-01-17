package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;

public interface IDeporteGateway {

    public List<Deporte> listaDeportes();

    public boolean existeDeporte(String nombreDeporte);

    public Deporte insertarDeporte(Deporte datosDeporte);

    public Deporte obtenerDeportePorId(String nombreDeporte);

    public Deporte actualizarDeporte(Deporte datosDeporte);

    public Deporte eliminarDeporte(String nombreDeporte);

}

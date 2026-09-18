package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IDeporteGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.DeporteMapper;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class DeporteServicio implements IDeporteServicio {

    private final IDeporteGateway deporteGateway;

    public DeporteServicio(IDeporteGateway deporteGateway) {
        this.deporteGateway = deporteGateway;
    }

    @Override
    public List<DeporteDto> listaDeportes() {
        List<Deporte> deportes = deporteGateway.listaDeportes();
        if (deportes.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encontraron deportes registrados");
        }
        List<DeporteDto> listaDtos = new ArrayList<>();
        deportes.forEach(modelo -> listaDtos.add(DeporteMapper.toDto(modelo)));
        return listaDtos;
    }

    @Override
    public DeporteDto insertarDeporte(DeporteDto datosDeporte) {
        if (deporteGateway.existeDeporte(datosDeporte.getNombre())) {
            throw new InsercionFallidaExepcion("El deporte ya existe");
        }
        Deporte deporte = DeporteMapper.fromDto(datosDeporte);
        Deporte guardado = deporteGateway.insertarDeporte(deporte);
        return DeporteMapper.toDto(guardado);
    }

    @Override
    public Deporte obtenerDeportePorId(String nombreDeporte) {
        Deporte deporte = deporteGateway.obtenerDeportePorId(nombreDeporte);
        if (deporte == null) {
            throw new NoExisteExcepcion("Deporte no encontrado");
        }
        return deporte;
    }

    @Override
    public Deporte actualizarDeporte(String nombre, Deporte datosDeporte) {
        if (!deporteGateway.existeDeporte(nombre)) {
            throw new NoExisteExcepcion("El deporte no existe para actualizarlo");
        }
        return deporteGateway.actualizarDeporte(datosDeporte);
    }

    @Override
    public Deporte eliminarDeporte(String nombreDeporte) {
        if (!deporteGateway.existeDeporte(nombreDeporte)) {
            throw new NoExisteExcepcion("El deporte no existe para eliminarlo");
        }
        return deporteGateway.eliminarDeporte(nombreDeporte);
    }
}

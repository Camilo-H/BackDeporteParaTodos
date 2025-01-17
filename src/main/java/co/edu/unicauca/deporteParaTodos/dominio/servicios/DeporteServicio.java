package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IDeporteGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class DeporteServicio implements IDeporteServicio {

    @Autowired
    private IDeporteGateway deporteGateway;

    @Override
    public List<Deporte> listaDeportes() {
        // TODO Auto-generated method stub
        List<Deporte> deportes = deporteGateway.listaDeportes();
        if(deportes.isEmpty()){
            throw new ListadoVacioExcepcion("No se encontraron deportes registrados");
        }
        return deportes;
    }

    @Override
    public Deporte insertarDeporte(Deporte datosDeporte) {
        // TODO Auto-generated method stub
        if (deporteGateway.existeDeporte(datosDeporte.getNombre())) {
            throw new InsercionFallidaExepcion("El deporte ya existe");
        }
        return deporteGateway.insertarDeporte(datosDeporte);
    }

    @Override
    public Deporte obtenerDeportePorId(String nombreDeporte) {
        // TODO Auto-generated method stub
      Deporte deporte = deporteGateway.obtenerDeportePorId(nombreDeporte);
        if (deporte == null) {
            throw new NoExisteExcepcion("Deporte no encontrado");
        }
        return deporte;
    }

    @Override
    public Deporte actualizarDeporte(String nombre, Deporte datosDeporte) {
        // TODO Auto-generated method stub
       
        if (!deporteGateway.existeDeporte(nombre)) {
            throw new NoExisteExcepcion("El deporte no existe para actualizarlo");
        }
        return deporteGateway.actualizarDeporte(datosDeporte);
    }

    @Override
    public Deporte eliminarDeporte(String nombreDeporte) {
        // TODO Auto-generated method stub
        if (!deporteGateway.existeDeporte(nombreDeporte)) {
            throw new NoExisteExcepcion("El deporte no existe para eliminarlo");
        }
        return deporteGateway.eliminarDeporte(nombreDeporte);
    }


    

}

package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IClaseGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class ClaseServicio implements IClaseServicio {

    @Autowired
    private IClaseGateway claseGateway;

    @Override
    public List<Clase> obtenerClases() {
        List<Clase> listado = claseGateway.obtenerClases();
        if (listado.isEmpty()) {
            throw new ListadoVacioExcepcion("No existen registros");
        }
        return listado;
    }

    @Override
    public Clase obtenerClase(int id) {
        return claseGateway.obtenerClase(id).orElseThrow(() -> new NoExisteExcepcion());
    }

    @Override
    public Clase insertarClase(Clase datoClase) {
        return claseGateway.insertarClase(datoClase);
    }

    @Override
    public Clase actualizarClase(int id, Clase datoClase) {
        if (!claseGateway.existeClase(id)) {
            throw new NoExisteExcepcion();
        }
        return claseGateway.actualizarClase(id, datoClase);
    }

    @Override
    public Clase eliminarClase(int id) {
        if (!claseGateway.existeClase(id)) {
            throw new NoExisteExcepcion();
        }
        return claseGateway.eliminarClase(id);
    }

}

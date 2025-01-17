package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IFacultadServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IFacultadGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class FacultadServicio implements IFacultadServicio {

    @Autowired
    private IFacultadGateway facultadGateway;

    @Override
    public List<Facultad> obtenerFacultades() {
        List<Facultad> facultades = facultadGateway.obtenerFacultades();
        if (facultades.isEmpty()) {
            throw new ListadoVacioExcepcion("No hay facultades registradas");
        }
        return facultades;
    }

    @Override
    public Facultad obtenerFacultad(String nombre) {
        return facultadGateway.obtenerFacultad(nombre).orElseThrow(() -> new NoExisteExcepcion());
    }

    @Override
    public Facultad insertarFacultad(Facultad datosFacultad) {
        if (!facultadGateway.existeFacultad(datosFacultad.getNombre())) {
            throw new NoExisteExcepcion("No existe la facultad");
        }
        return facultadGateway.insertarFacultad(datosFacultad);
    }

    @Override
    public Facultad eliminarFacultad(String nombre) {
        if (!facultadGateway.existeFacultad(nombre)) {
            throw new NoExisteExcepcion("No existe la facultad");
        }
        return facultadGateway.eliminarFacultad(nombre);
    }

}
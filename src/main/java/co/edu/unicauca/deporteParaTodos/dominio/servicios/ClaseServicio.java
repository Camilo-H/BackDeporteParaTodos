package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IClaseGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class ClaseServicio implements IClaseServicio {

    @Autowired
    private IClaseGateway claseGateway;

    @Override
    public List<Clase> obtenerClasesGrupo(String categoria, String curso, Integer anio, Integer iterable) {
        List<Clase> listado = claseGateway.obtenerClasesGrupo(categoria, curso, anio, iterable);
        if (listado.isEmpty()) {
            throw new ListadoVacioExcepcion("No existen registros");
        }
        return listado;
    }

    @Override
    public Clase insertarClase(Clase datoClase) {
        return claseGateway.insertarClase(datoClase);
    }

    @Override
    public Clase eliminarClase(int id) {
        if (!claseGateway.existeClase(id)) {
            throw new NoExisteExcepcion();
        }
        return claseGateway.eliminarClase(id);
    }
}

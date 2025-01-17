package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IGrupoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class GrupoServicio implements IGrupoServicio {

    @Autowired
    private IGrupoGateway grupoGateway;

    @Override
    public List<Grupo> obtenerGrupos() {

        List<Grupo> listaGrupos = grupoGateway.obtenerGrupos();
        if (listaGrupos.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran grupos registrados");
        }
        return listaGrupos;
    }

    @Override
    public Grupo insertarGrupo(Grupo datosGrupo) {

        if (grupoGateway.existeGrupo(datosGrupo.getNombre(), datosGrupo.getAnio(), datosGrupo.getIterable())) {
            throw new YaExisteElementoExcepcion("Ya existe el grupo en el sistema");
        }

        Grupo grupoInsertado = grupoGateway.insertarGrupo(datosGrupo);
        if (grupoInsertado == null) {
            throw new InsercionFallidaExepcion("La insercion no se pudo realizar con exito");
        }
        return grupoInsertado;
    }

    @Override
    public Grupo obtenerGrupoPorId(String nombre, int anio, int iterable) {
        // TODO Auto-generated method stub
        return grupoGateway.obtenerGrupoPorId(nombre, anio, iterable).orElseThrow(
                () -> new NoExisteExcepcion("El grupo con el nombre " + nombre + " no existe"));
    }

    @Override
    public Grupo actualizarGrupo(String nombre, int anio, int iterable, Grupo datosGrupo) {
        if (!grupoGateway.existeGrupo(nombre, anio, iterable)) {
            throw new NoExisteExcepcion("El grupo con el nombre " + nombre + " no existe");
        }
        return grupoGateway.actualizarGrupo(nombre, anio, iterable, datosGrupo);
    }

    @Override
    public Grupo eliminarGrupo(String nombre, int anio, int iterable) {
        if (!grupoGateway.existeGrupo(nombre, anio, iterable)) {
            throw new NoExisteExcepcion("El grupo con el nombre " + nombre + " no existe");
        }
        return grupoGateway.eliminarGrupo(nombre, anio, iterable);
    }

}

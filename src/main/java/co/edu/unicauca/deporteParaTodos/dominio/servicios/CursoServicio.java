package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.ICursoGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.InsercionFallidaExepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class CursoServicio implements ICursoServicio {

    @Autowired
    private ICursoGateway cursoGateway;

    @Override
    public List<Curso> recuperarCursos() {
        List<Curso> cursos = cursoGateway.obtenerCursos();
        if (cursos.isEmpty()) {
            throw new ListadoVacioExcepcion("No se encuentran cursos registrados");
        }
        return cursos;
    }

    @Override
    @Transactional
    public Curso insertarCurso(Curso datosCurso) {
        // TODO Auto-generated method stub
        Curso cursoInsertado = cursoGateway.insertarCurso(null);
        if (cursoInsertado == null) {
            throw new InsercionFallidaExepcion("La insercion no se pudo realizar con exito");
        }
        return cursoInsertado;
    }

    @Override
    public Curso obtenerCurso(String nombre) {
        return cursoGateway.obtenerCurso(nombre)
                .orElseThrow(() -> new NoExisteExcepcion("El curso con el nombre " + nombre + " no existe"));
    }

    @Override
    public Curso actualizarCurso(Curso datCurso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCurso'");
    }

    @Override
    public Curso eliminarCurso(String nombre) {
        // TODO Auto-generated method stub
        if (!cursoGateway.existeCurso(nombre)) {
            throw new NoExisteExcepcion("El curso con el nombre " + nombre + " no existe");
        }
        return cursoGateway.eliminarCurso(nombre);
    }

}

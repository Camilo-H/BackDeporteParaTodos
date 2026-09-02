package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IClaseGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ClaseEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IClaseRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class ClaseGateway implements IClaseGateway {

    @Autowired
    private IClaseRepositorio repoClase;

    @Override
    public boolean existeClase(int id) {
        return repoClase.existsById(id);
    }

    @Override
    public List<Clase> obtenerClasesGrupo(String categoria, String curso, Integer anio, Integer iterable) {
        List<ClaseEntidad> entidades = repoClase
                .findByIdGrupoCategoriaAndIdGrupoCursoAndIdGrupoAnioAndIdGrupoIterableAndEliminado(
                        categoria, curso, anio, iterable, 0);
        List<Clase> listado = new ArrayList<>();
        entidades.forEach(entidad -> {
            Clase modelo = Clase.fabricarDeEntidad(entidad);
            if (modelo != null) {
                listado.add(modelo);
            }
        });
        return listado;
    }

    @Override
    public Clase insertarClase(Clase datoClase) {
        ClaseEntidad entidad = new ClaseEntidad();
        entidad.setCodigo(null);
        entidad.setIdGrupoCategoria(datoClase.getCategoria());
        entidad.setIdGrupoCurso(datoClase.getCurso());
        entidad.setIdGrupoAnio(datoClase.getAnio());
        entidad.setIdGrupoIterable(datoClase.getIterable());
        entidad.setIdInstructor(datoClase.getIdInstructor());
        entidad.setFecha(datoClase.getFecha());
        entidad.setHoras(datoClase.getHoras());
        entidad.setMinutos(datoClase.getMinutos());
        entidad.setObservacion(datoClase.getObservacion());
        entidad.setEliminado(0);
        ClaseEntidad claseInsertada = repoClase.save(entidad);
        return Clase.fabricarDeEntidad(claseInsertada);
    }

    @Override
    public Clase eliminarClase(int id) {
        ClaseEntidad entidad = repoClase.findById(id).orElseThrow(NoExisteExcepcion::new);
        repoClase.marcarComoEliminado(id);
        entidad.setEliminado(1);
        return Clase.fabricarDeEntidad(entidad);

    }

}

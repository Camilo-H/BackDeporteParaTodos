package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInscripcionGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.InscripcionId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInscripcionRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class InscripcionGateway implements IInscripcionGateway {

    @Autowired
    private IInscripcionRepositorio repoInscrp;

    @Override
    public boolean existeInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable) {
        InscripcionId id = new InscripcionId(categoria, curso, anio, iterable, alumnoId);
        return repoInscrp.existsById(id);
    }

    @Override
    public boolean existeInscripcionActiva(String alumnoId, String categoria, String curso, int anio, int iterable) {
        return repoInscrp.existeInscripcionActiva(alumnoId, categoria, curso, anio, iterable);
    }

    @Override
    public Inscripcion obtenerInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable) {
        InscripcionId id = new InscripcionId(categoria, curso, anio, iterable, alumnoId);
        Optional<InscripcionEntidad> op = repoInscrp.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("La inscripcion buscada no existe");
        }
        return Inscripcion.fabricarDeEntidad(op.get());
    }

    @Override
    public Inscripcion guardarInscripcion(Inscripcion inscripcion) {
        InscripcionEntidad entidad = InscripcionEntidad.fabricarDeModelo(inscripcion);
        InscripcionEntidad guardada = repoInscrp.save(entidad);
        return Inscripcion.fabricarDeEntidad(guardada);
    }

    @Override
    public Inscripcion desvincularInscripcion(String alumnoId, String categoria, String curso, int anio, int iterable) {
        InscripcionId id = new InscripcionId(categoria, curso, anio, iterable, alumnoId);
        Optional<InscripcionEntidad> op = repoInscrp.findById(id);
        if (op.isEmpty()) {
            throw new NoExisteExcepcion("La inscripcion a desvincular no existe");
        }
        InscripcionEntidad entidad = op.get();
        entidad.setFechaDesvinculacion(Timestamp.from(Instant.now()));
        InscripcionEntidad guardada = repoInscrp.save(entidad);
        return Inscripcion.fabricarDeEntidad(guardada);
    }
}

package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.HorarioEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.InscripcionEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.InscripcionId;

public interface IInscripcionRepositorio extends CrudRepository<InscripcionEntidad,InscripcionId> {
    
    /**
     * Valida que una inscripcion dada este activa o no
     * @param alumnoId parte id compuesto
     * @param categoria parte id compuesto
     * @param curso parte id compuesto
     * @param anio parte id compuesto
     * @param iterable parte id compuesto
     * @return true si esta activa, false de lo contrario
     */
    @Query("""
SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END
FROM InscripcionEntidad i
WHERE i.alumnoId = :alumnoId
  AND i.categoria = :categoria
  AND i.curso = :curso
  AND i.anio = :anio
  AND i.iterable = :iterable
  AND i.eliminado = 0
  AND i.fechaInscripcion <= CURRENT_TIMESTAMP
  AND (i.fechaDesvinculacion IS NULL OR i.fechaDesvinculacion > CURRENT_TIMESTAMP)
""")
boolean existeInscripcionActiva(
    @Param("alumnoId") String alumnoId,
    @Param("categoria") String categoria,
    @Param("curso") String curso,
    @Param("anio") int anio,
    @Param("iterable") int iterable
);
}

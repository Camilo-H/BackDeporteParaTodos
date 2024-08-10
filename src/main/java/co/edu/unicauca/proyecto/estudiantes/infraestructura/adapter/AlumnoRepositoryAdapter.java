package co.edu.unicauca.proyecto.estudiantes.infraestructura.adapter;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import co.edu.unicauca.proyecto.estudiantes.dominio.model.Alumno;
import co.edu.unicauca.proyecto.estudiantes.dominio.repository.AlumnoRepository;
import co.edu.unicauca.proyecto.estudiantes.infraestructura.Entity.AlumnoEntity;

@Component
public class AlumnoRepositoryAdapter implements AlumnoRepository{

    private final AlumnoJPARepository alumnoJpaRepository;

     @Autowired
    public AlumnoRepositoryAdapter(AlumnoJPARepository alumnoJpaRepository) {
        this.alumnoJpaRepository = alumnoJpaRepository;
    }
    @Override
    public Alumno save(Alumno prmAlumno) {
        AlumnoEntity entity = mapToEntity(prmAlumno);
    }

    @Override
    public Optional<Alumno> findById(String codigo) {
       
    }

    @Override
    public List<Alumno> findAll() {
       
    }

    @Override
    public void deleteById(String codigo) {
       
    }
    
}

package co.edu.unicauca.proyecto.estudiantes.infraestructura.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.proyecto.estudiantes.infraestructura.Entity.AlumnoEntity;
@Repository
public interface AlumnoJPARepository extends JpaRepository<AlumnoEntity, String>{    
}

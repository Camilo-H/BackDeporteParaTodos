package co.edu.unicauca.proyecto.cursosdeportivos.dominio.port;

import java.util.List;
import java.util.Optional;
import co.edu.unicauca.proyecto.cursosdeportivos.dominio.model.Curso;

public interface CursoRepository {
    
    Curso save (Curso prmCurso);
    Optional <Curso> findById (String prmCodigo);
    List<Curso> findAll();
    Void deleteById(String prmCodigo);
}

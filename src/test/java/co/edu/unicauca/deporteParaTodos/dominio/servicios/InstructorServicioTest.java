package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IInstructorGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Instructor;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.InstructorDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServicioTest {

    @Mock
    private IInstructorGateway instructorsGateway;

    @InjectMocks
    private InstructorServicio instructorServicio;

    @Test
    void registrarInstructor_sinAlumnoCodigo_seRegistraCorrectamente() {
        PerfilDto dto = new PerfilDto();
        dto.setId("98765432");
        dto.setNombre("Prof. García");
        dto.setCorreo("garcia@unicauca.edu.co");
        dto.setTipoId("CC");
        dto.setSexo("M");
        dto.setTipoAlumno("Instructor");
        // alumnoCodigo NOT set — remains null (regression: SCRUM-137)

        when(instructorsGateway.existeInstructor(dto.getId())).thenReturn(false);
        when(instructorsGateway.registrarInstructor(any(Perfil.class), eq("Instructor")))
                .thenReturn(new Instructor());

        InstructorDto resultado = instructorServicio.registrarInstructor(dto);

        assertNotNull(resultado);
        verify(instructorsGateway).registrarInstructor(any(Perfil.class), eq("Instructor"));
    }
}

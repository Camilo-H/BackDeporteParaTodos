package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AlumnoEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.PerfilEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAlumnoRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.ICoordinadorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IImagenRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IInstructorRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IPerfilRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilGatewayTest {

    @Mock
    private IPerfilRepositorio repoPerfil;

    @Mock
    private ICoordinadorRepositorio repoCoordinador;

    @Mock
    private IInstructorRepositorio repoInstructor;

    @Mock
    private IAlumnoRepositorio repoAlumno;

    @Mock
    private IImagenRepositorio repoImagen;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PerfilGateway perfilGateway;

    @Test
    void registrarAlumno_conAlumnoCodigo_persisteEnAlmCodigo() {
        String id = "12345678";
        String codigoEstudiantil = "2025-CIEN-001";

        Perfil perfil = new Perfil();
        perfil.setId(id);
        perfil.setNombre("Juan Test");
        perfil.setCorreo("juan@unicauca.edu.co");
        perfil.setTipoId("CC");
        perfil.setSexo("M");
        perfil.setTipoAlumno("Regular");
        perfil.setAlumnoCodigo(codigoEstudiantil);

        when(repoPerfil.existsById(id)).thenReturn(false);
        when(repoPerfil.save(any(PerfilEntidad.class))).thenReturn(new PerfilEntidad());
        when(repoAlumno.save(any(AlumnoEntidad.class))).thenAnswer(inv -> inv.getArgument(0));

        perfilGateway.registrarAlumno(perfil);

        ArgumentCaptor<AlumnoEntidad> captor = ArgumentCaptor.forClass(AlumnoEntidad.class);
        verify(repoAlumno).save(captor.capture());
        assertEquals(codigoEstudiantil, captor.getValue().getAlm_codigo());
    }
}

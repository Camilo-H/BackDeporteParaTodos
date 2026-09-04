package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
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
class AutenticacionServicioTest {

    @Mock
    private IPerfilGateway gatePerfil;

    @InjectMocks
    private AutenticacionServicio autenticacionServicio;

    private Perfil perfilBase() {
        Perfil p = new Perfil();
        p.setId("12345678");
        p.setNombre("Juan García");
        p.setCorreo("juan@unicauca.edu.co");
        p.setTipoId("CC");
        p.setSexo("M");
        p.setRol("ALUMNO");
        p.setFacultad("Ingeniería");
        p.setTipoAlumno("Regular");
        return p;
    }

    private PerfilDto dtoBase() {
        PerfilDto d = new PerfilDto();
        d.setId("12345678");
        d.setNombre("Juan García");
        d.setCorreo("juan@unicauca.edu.co");
        d.setTipoId("CC");
        d.setSexo("M");
        d.setRole("ALUMNO");
        d.setFacultad("Ingeniería");
        d.setTipoAlumno("Regular");
        return d;
    }

    @Test
    void login_emailExistente_retornaPerfilDto() {
        when(gatePerfil.obtenerUsuario("juan@unicauca.edu.co")).thenReturn(perfilBase());

        PerfilDto resultado = autenticacionServicio.login("juan@unicauca.edu.co");

        assertNotNull(resultado);
        assertEquals("12345678", resultado.getId());
        assertEquals("ALUMNO",   resultado.getRole());
        verify(gatePerfil).obtenerUsuario("juan@unicauca.edu.co");
    }

    @Test
    void login_emailNoExistente_lanzaNoExisteExcepcion() {
        when(gatePerfil.obtenerUsuario(anyString())).thenReturn(null);

        assertThrows(NoExisteExcepcion.class,
                () -> autenticacionServicio.login("desconocido@externo.com"));
    }

    @Test
    void registrarAlumno_perfilNoExistente_retornaPerfilDto() {
        when(gatePerfil.existePerfil(anyString())).thenReturn(false);
        when(gatePerfil.registrarAlumno(any(Perfil.class))).thenReturn(perfilBase());

        PerfilDto resultado = autenticacionServicio.registrarAlumno(dtoBase());

        assertNotNull(resultado);
        assertEquals("12345678", resultado.getId());
        assertEquals("ALUMNO",   resultado.getRole());
        verify(gatePerfil).registrarAlumno(any(Perfil.class));
    }

    @Test
    void registrarAlumno_perfilYaExistente_lanzaYaExisteElementoExcepcion() {
        when(gatePerfil.existePerfil(anyString())).thenReturn(true);

        assertThrows(YaExisteElementoExcepcion.class,
                () -> autenticacionServicio.registrarAlumno(dtoBase()));

        verify(gatePerfil, never()).registrarAlumno(any());
    }
}

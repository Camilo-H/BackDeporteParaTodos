package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServicioTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private TokenServicio tokenServicio;

    private PerfilDto perfil;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenServicio, "expirationMs", 86400000L);

        perfil = new PerfilDto();
        perfil.setId("12345678");
        perfil.setCorreo("alumno@unicauca.edu.co");
        perfil.setRole("Estudiante");
    }

    private Jwt fakeJwtDeSalida() {
        return Jwt.withTokenValue("sistema-jwt-test")
                .header("alg", "HS256")
                .claim("sub", perfil.getCorreo())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(86400))
                .build();
    }

    @Test
    void emitirToken_retornaValorQueProduceElEncoder() {
        when(jwtEncoder.encode(any())).thenReturn(fakeJwtDeSalida());

        String resultado = tokenServicio.emitirToken(perfil);

        assertEquals("sistema-jwt-test", resultado);
    }

    @Test
    void emitirToken_usaCorreoDelPerfilComoSubject() {
        when(jwtEncoder.encode(any())).thenReturn(fakeJwtDeSalida());
        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);

        tokenServicio.emitirToken(perfil);

        verify(jwtEncoder).encode(captor.capture());
        assertEquals(perfil.getCorreo(), captor.getValue().getClaims().getSubject());
    }

    @Test
    void emitirToken_incluyeRolYPerfilIdComoClaimsAdicionales() {
        when(jwtEncoder.encode(any())).thenReturn(fakeJwtDeSalida());
        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);

        tokenServicio.emitirToken(perfil);

        verify(jwtEncoder).encode(captor.capture());
        assertEquals(perfil.getRole(), captor.getValue().getClaims().getClaim("rol"));
        assertEquals(perfil.getId(), captor.getValue().getClaims().getClaim("perf_id"));
    }

    @Test
    void emitirToken_cabeceraTieneAlgoritmoHS256() {
        when(jwtEncoder.encode(any())).thenReturn(fakeJwtDeSalida());
        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);

        tokenServicio.emitirToken(perfil);

        verify(jwtEncoder).encode(captor.capture());
        assertEquals(MacAlgorithm.HS256, captor.getValue().getJwsHeader().getAlgorithm());
    }
}

package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Disponibilidad;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.deporteParaTodos;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = deporteParaTodos.class, properties = {
        "app.jwt.secret=dGVzdFNlY3JldEtleUZvckNJMTIzNDU2Nzg5MDEyMzQ1Njc4OTA="
})
@AutoConfigureMockMvc
class InscripcionRestSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInscripcionServicio servicio;

    private static final String TEST_SECRET = "dGVzdFNlY3JldEtleUZvckNJMTIzNDU2Nzg5MDEyMzQ1Njc4OTA=";

    private String buildJwt(String rol, String perfId) {
        byte[] keyBytes = Base64.getDecoder().decode(TEST_SECRET);
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
        OctetSequenceKey jwk = new OctetSequenceKey.Builder(key).build();
        JWKSource<SecurityContext> source = new ImmutableJWKSet<>(new JWKSet(jwk));
        NimbusJwtEncoder encoder = new NimbusJwtEncoder(source);
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject("test@unicauca.edu.co")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .claim("rol", rol)
                .claim("perf_id", perfId)
                .build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    // ── POST /inscripcion — BOLA: Alumno solo puede inscribirse a si mismo ────

    @Test
    void inscribir_alumnoIntentaInscribirOtro_retorna403() throws Exception {
        // Regresion del mismo tipo de BOLA que se encontro y corrigio hoy en
        // desvincularInscripcion(): un Alumno autenticado como "alum1" no debe
        // poder crear una inscripcion a nombre de "alum2".
        mockMvc.perform(post("/api/v2/inscripcion")
                .contentType("application/json")
                .content("{\"alumnoId\":\"alum2\",\"categoria\":\"cat1\",\"curso\":\"cur1\",\"anio\":2026,\"iterable\":1}")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void inscribir_alumnoInscribeAsiMismo_retorna201() throws Exception {
        Inscripcion resultado = new Inscripcion("alum1", "cat1", "cur1", 2026, 1,
                Timestamp.from(Instant.now()), null, "INSCRITO");
        when(servicio.inscribir(any(Inscripcion.class))).thenReturn(resultado);

        mockMvc.perform(post("/api/v2/inscripcion")
                .contentType("application/json")
                .content("{\"alumnoId\":\"alum1\",\"categoria\":\"cat1\",\"curso\":\"cur1\",\"anio\":2026,\"iterable\":1}")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isCreated());
    }

    // ── GET /validarInscripcion — BOLA: Alumno solo consulta su propio estado ─

    @Test
    void validarInscripcion_alumnoConsultaOtro_retorna403() throws Exception {
        // Mismo patron BOLA: "alum1" no debe poder consultar el estado de "alum2".
        mockMvc.perform(get("/api/v2/validarInscripcion")
                .param("alumnoId", "alum2")
                .param("categoria", "cat1")
                .param("curso", "cur1")
                .param("anio", "2026")
                .param("iterable", "1")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void validarInscripcion_alumnoConsultaPropio_retorna200() throws Exception {
        when(servicio.validarInscripcion(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(true);

        mockMvc.perform(get("/api/v2/validarInscripcion")
                .param("alumnoId", "alum1")
                .param("categoria", "cat1")
                .param("curso", "cur1")
                .param("anio", "2026")
                .param("iterable", "1")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isOk());
    }

    // ── GET /inscripcion/disponibilidad — sin BOLA, alcanzable por Alumno o Coordinador ─

    @Test
    void obtenerDisponibilidad_retorna200() throws Exception {
        Disponibilidad disponibilidad = new Disponibilidad(10, 5, 2);
        when(servicio.obtenerDisponibilidad(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(disponibilidad);

        mockMvc.perform(get("/api/v2/inscripcion/disponibilidad")
                .param("prmCategoria", "cat1")
                .param("prmCurso", "cur1")
                .param("prmAnio", "2026")
                .param("prmIterable", "1")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuposTotales").value(10))
                .andExpect(jsonPath("$.cuposDisponibles").value(5))
                .andExpect(jsonPath("$.tamanoListaEspera").value(2));
    }

    // ── PATCH /inscripcion/promover — solo Coordinador ────────────────────────

    @Test
    void promoverInscripcion_rolAlumno_retorna403() throws Exception {
        mockMvc.perform(patch("/api/v2/inscripcion/promover")
                .param("prmPerfId", "alum1")
                .param("prmCategoria", "cat1")
                .param("prmCurso", "cur1")
                .param("prmAnio", "2026")
                .param("prmIterable", "1")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void promoverInscripcion_rolCoordinador_retorna200() throws Exception {
        Inscripcion promovida = new Inscripcion("alum1", "cat1", "cur1", 2026, 1,
                Timestamp.from(Instant.now()), null, "INSCRITO");
        when(servicio.promoverManualmente(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(promovida);

        mockMvc.perform(patch("/api/v2/inscripcion/promover")
                .param("prmPerfId", "alum1")
                .param("prmCategoria", "cat1")
                .param("prmCurso", "cur1")
                .param("prmAnio", "2026")
                .param("prmIterable", "1")
                .header("Authorization", "Bearer " + buildJwt("Coordinador", "coord1")))
                .andExpect(status().isOk());
    }

    // ── PUT /desvincularInscripcion — BOLA: Alumno solo puede desvincularse a si mismo

    @Test
    void desvincularInscripcion_alumnoDesvinculaOtro_retorna403() throws Exception {
        mockMvc.perform(put("/api/v2/desvincularInscripcion")
                .contentType("application/json")
                .content("{\"alumnoId\":\"alum2\",\"categoria\":\"cat1\",\"curso\":\"cur1\",\"anio\":2026,\"iterable\":1}")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isForbidden());
    }

    // ── GET /inscripcion/listaEspera — solo Coordinador ──────────────────────

    @Test
    void listaEspera_rolAlumno_retorna403() throws Exception {
        mockMvc.perform(get("/api/v2/inscripcion/listaEspera")
                .param("prmCategoria", "cat1")
                .param("prmCurso", "cur1")
                .param("prmAnio", "2026")
                .param("prmIterable", "1")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void desvincularInscripcion_alumnoDesvinculaPropio_retorna200() throws Exception {
        Inscripcion desvinculada = new Inscripcion("alum1", "cat1", "cur1", 2026, 1,
                Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), "INSCRITO");
        when(servicio.desvincularInscripcion(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(desvinculada);

        mockMvc.perform(put("/api/v2/desvincularInscripcion")
                .contentType("application/json")
                .content("{\"alumnoId\":\"alum1\",\"categoria\":\"cat1\",\"curso\":\"cur1\",\"anio\":2026,\"iterable\":1}")
                .header("Authorization", "Bearer " + buildJwt("Alumno", "alum1")))
                .andExpect(status().isOk());
    }
}

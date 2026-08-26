package co.edu.unicauca.deporteParaTodos.configurations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private static final String JWKS_URI = "https://www.googleapis.com/oauth2/v3/certs";
    // Clave de prueba: 32 bytes aleatorios en Base64 -- solo para tests, nunca en produccion
    private static final String TEST_SECRET = "dGVzdFNlY3JldEtleUZvckNJMTIzNDU2Nzg5MDEyMzQ1Njc4OTA=";

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
        ReflectionTestUtils.setField(securityConfig, "googleJwksUri", JWKS_URI);
        ReflectionTestUtils.setField(securityConfig, "jwtSecret", TEST_SECRET);
    }

    @Test
    void corsConfigurationSource_permiteOrigenLocalhost4200() {
        CorsConfigurationSource source = securityConfig.corsConfigurationSource();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v2/categorias2");
        CorsConfiguration config = source.getCorsConfiguration(request);

        assertNotNull(config);
        assertNotNull(config.getAllowedOrigins());
        assertTrue(config.getAllowedOrigins().contains("http://localhost:4200"),
                "CORS debe permitir el origen del frontend en localhost:4200");
    }

    @Test
    void corsConfigurationSource_noPermiteCredentials() {
        CorsConfigurationSource source = securityConfig.corsConfigurationSource();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/");
        CorsConfiguration config = source.getCorsConfiguration(request);

        assertNotNull(config);
        assertNotSame(Boolean.TRUE, config.getAllowCredentials(),
                "JWT viaja en Authorization header -- credentials (cookies) no deben estar habilitadas");
    }

    @Test
    void jwtEncoder_seConstruyeConSecretoBase64Valido() {
        JwtEncoder encoder = securityConfig.jwtEncoder();
        assertNotNull(encoder, "jwtEncoder debe construirse sin excepcion con un secreto Base64 valido");
    }
}

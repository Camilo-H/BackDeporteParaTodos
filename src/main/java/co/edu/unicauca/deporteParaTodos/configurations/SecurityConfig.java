package co.edu.unicauca.deporteParaTodos.configurations;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String googleJwksUri;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // Fase 1a: permitAll() — no rompe endpoints existentes.
    // Fase 1b (siguiente commit): restringir endpoints con .authenticated()
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            // CSRF deshabilitado intencionalmente: API stateless con JWT en Authorization header.
            // El riesgo CSRF aplica solo a flujos basados en cookies; aqui no se usan cookies de sesion
            // (SessionCreationPolicy.STATELESS) y CORS esta restringido a origenes explicitamente permitidos.
            // Referencia: https://docs.spring.io/spring-security/reference/features/exploits/csrf.html
            .csrf(AbstractHttpConfigurer::disable) // NOSONAR: jwt-stateless-no-csrf-risk
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    // Decodifica tokens Google ID (valida firma via JWKS publico de Google)
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(googleJwksUri).build();
    }

    // Firma los JWT propios del sistema con HMAC-SHA256
    @Bean
    public JwtEncoder jwtEncoder() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
        OctetSequenceKey jwk = new OctetSequenceKey.Builder(key).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    // Unica fuente de verdad para CORS en toda la aplicacion.
    // webConfig.java fue eliminado: Spring Security intercepta requests antes que el
    // DispatcherServlet, por lo que el CorsFilter de seguridad debe tener su propia
    // CorsConfigurationSource -- delegar a WebMvcConfigurer.addCorsMappings() no es confiable.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        // JWT viaja en Authorization header, no en cookies — credentials no requeridas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenServicio {

    private final JwtEncoder jwtEncoder;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    public TokenServicio(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String emitirToken(PerfilDto perfil) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(perfil.getCorreo())
                .issuedAt(now)
                .expiresAt(now.plusMillis(expirationMs))
                .claim("rol", perfil.getRole())
                .claim("perf_id", perfil.getId())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}

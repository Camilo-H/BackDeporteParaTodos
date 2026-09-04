package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.api;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAutenticacionServicio;
import co.edu.unicauca.deporteParaTodos.dominio.servicios.TokenServicio;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.PerfilDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.TokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/auth")
public class TokenInterchangeRest {

    // [DIAG-TEMP] eliminar tras validacion
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInterchangeRest.class);

    private final JwtDecoder googleDecoder;
    private final IAutenticacionServicio autenticacionServicio;
    private final TokenServicio tokenServicio;

    public TokenInterchangeRest(@Qualifier("googleJwtDecoder") JwtDecoder googleDecoder,
                                IAutenticacionServicio autenticacionServicio,
                                TokenServicio tokenServicio) {
        this.googleDecoder = googleDecoder;
        this.autenticacionServicio = autenticacionServicio;
        this.tokenServicio = tokenServicio;
    }

    @Operation(summary = "Intercambia un Google ID Token por un JWT propio del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token emitido correctamente"),
        @ApiResponse(responseCode = "401", description = "Google ID Token invalido o ausente"),
        @ApiResponse(responseCode = "404", description = "Correo no registrado en el sistema")
    })
    @PostMapping("/token")
    public ResponseEntity<TokenResponseDto> intercambiarToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String googleToken = authHeader.substring(7);
        Jwt googleJwt;
        try {
            googleJwt = googleDecoder.decode(googleToken);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = googleJwt.getClaimAsString("email");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // autenticacionServicio.login lanza NoExisteExcepcion (→ 404 via handler global)
        // si el correo no esta registrado en el sistema
        PerfilDto perfil = autenticacionServicio.login(email);
        // [DIAG-TEMP] PerfilDto final justo antes de serializar a JSON
        LOGGER.info("[DIAG] intercambiarToken email={} → dto.id={} dto.correo={} dto.role={}",
                email,
                perfil != null ? perfil.getId() : "NULL",
                perfil != null ? perfil.getCorreo() : "NULL",
                perfil != null ? perfil.getRole() : "NULL");
        String token = tokenServicio.emitirToken(perfil);
        return ResponseEntity.ok(new TokenResponseDto(token, perfil));
    }
}

package co.edu.unicauca.desarrolloprueba;

import co.edu.unicauca.deporteParaTodos.deporteParaTodos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// app.jwt.secret: valor de prueba para que el contexto cargue sin JWT_SECRET en el entorno de CI.
// El valor real en produccion siempre viene de la variable de entorno JWT_SECRET (nunca commiteado).
@SpringBootTest(classes = deporteParaTodos.class, properties = {
        "app.jwt.secret=dGVzdFNlY3JldEtleUZvckNJMTIzNDU2Nzg5MDEyMzQ1Njc4OTA="
})
class DesarrollopruebaApplicationTests {

	@Test
	void contextLoads() {
	}

}

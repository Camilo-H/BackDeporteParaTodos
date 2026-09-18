package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;

/**
 * Test de concurrencia para control de cupos en inscripciones.
 *
 * REQUIERE: base de datos MySQL activa con datos de prueba:
 *   - Un curso con inscripciones ABIERTO
 *   - Un grupo con exactamente 1 cupo disponible
 *
 * Ejecutar manualmente: mvn test -Dtest=InscripcionConcurrenciaIT
 * No forma parte del pipeline automatico (requiere BD real y es no-deterministico).
 */
@Disabled("Test de integracion: requiere BD MySQL activa y datos de prueba. Ejecutar manualmente.")
@SpringBootTest
class InscripcionConcurrenciaIT {

    @Autowired
    private IInscripcionServicio servicio;

    @Test
    void inscripcion_concurrente_soloUnaPermitida_cuandoHayUnCupo() throws Exception {
        // Preparar: 3 alumnos distintos intentan inscribirse al mismo grupo con 1 cupo
        String categoria = "Deportes";
        String curso = "Natacion";
        int anio = 2026;
        int iterable = 1;

        List<String> alumnosIds = List.of("alumno-conc-1", "alumno-conc-2", "alumno-conc-3");
        int hilos = alumnosIds.size();

        CountDownLatch listo = new CountDownLatch(hilos);
        CountDownLatch inicio = new CountDownLatch(1);
        AtomicInteger exitosos = new AtomicInteger(0);
        AtomicInteger rechazados = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(hilos);
        List<Future<?>> futures = new ArrayList<>();

        for (String alumnoId : alumnosIds) {
            futures.add(executor.submit(() -> {
                listo.countDown();
                assertDoesNotThrow(() -> inicio.await());
                try {
                    servicio.inscribir(new Inscripcion(alumnoId, categoria, curso, anio, iterable, null, null));
                    exitosos.incrementAndGet();
                } catch (Exception e) {
                    rechazados.incrementAndGet();
                }
            }));
        }

        listo.await();
        inicio.countDown();
        for (Future<?> f : futures) {
            f.get();
        }
        executor.shutdown();

        // Con 1 cupo disponible, exactamente 1 debe tener exito y 2 deben ser rechazados
        assertTrue(exitosos.get() <= 1,
                "Con 1 cupo, a lo sumo 1 inscripcion debe ser exitosa, pero fueron: " + exitosos.get());
        assertTrue(rechazados.get() >= 2,
                "Con 1 cupo y 3 intentos concurrentes, al menos 2 deben ser rechazados");
    }
}

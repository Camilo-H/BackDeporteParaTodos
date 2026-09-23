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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Base64;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICategoriaCursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.ICursoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IGrupoServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IImagenServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IInscripcionServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IPerfilGateway;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.YaExisteElementoExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Curso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Disponibilidad;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.EstadoCurso;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.EstadoInscripciones;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Grupo;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Inscripcion;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Perfil;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;

/**
 * Test de integracion (BD MySQL real) para control de cupos en inscripciones.
 *
 * El @BeforeAll crea sus propios fixtures (categoria/curso/grupo con 1 cupo) a
 * traves de los servicios reales de la app -- no via SQL directo -- para que
 * cualquier cambio futuro en el modelo de dominio o en las validaciones de
 * negocio haga fallar la creacion del fixture de forma explicita, en vez de
 * insertar silenciosamente una fila que la app misma nunca produciria.
 * La creacion es idempotente (verifica existencia antes de insertar) para
 * poder correr repetidas veces contra una BD persistente en local.
 */
// app.jwt.secret: valor de prueba para que el contexto cargue sin JWT_SECRET en el entorno de CI.
// El valor real en produccion siempre viene de la variable de entorno JWT_SECRET (nunca commiteado).
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(properties = {
        "app.jwt.secret=dGVzdFNlY3JldEtleUZvckNJMTIzNDU2Nzg5MDEyMzQ1Njc4OTA="
})
class InscripcionConcurrenciaIT {

    private static final String CATEGORIA = "Deportes";
    private static final String CURSO = "Natacion";
    private static final int ANIO = 2026;
    private static final int ITERABLE = 1;
    // Referencias a filas ya insertadas por el seed data de DDL-MYSQL.sql -- existen
    // desde que se carga el esquema, no las crea este fixture.
    private static final String DEPORTE_SEED = "Natacion";
    private static final String INSTRUCTOR_SEED_ID = "2";

    private static final List<String> ALUMNOS_CONCURRENCIA =
            List.of("alumno-conc-1", "alumno-conc-2", "alumno-conc-3");
    private static final String ALUMNO_OCUPA_CUPO = "alumno-it-ocupa-cupo";
    private static final String ALUMNO_EN_ESPERA = "alumno-it-espera";

    // Grupo separado (mismo curso, otro iterable) para el escenario de
    // promoverManualmente(), asi no interfiere con los otros dos tests.
    private static final int ITERABLE_PROMOCION = 2;
    private static final String ALUMNO_FILLER_PROMOCION = "alumno-it-filler-promocion";
    private static final String ALUMNO_CANDIDATO_1 = "alumno-it-candidato-1";
    private static final String ALUMNO_CANDIDATO_2 = "alumno-it-candidato-2";

    @Autowired
    private IInscripcionServicio servicio;

    @Autowired
    private ICategoriaCursoServicio categoriaCursoServicio;

    @Autowired
    private ICursoServicio cursoServicio;

    @Autowired
    private IGrupoServicio grupoServicio;

    @Autowired
    private IImagenServicio imagenServicio;

    // No hay una via a nivel de servicio para registrar un alumno nuevo:
    // IAlumnoServicio.insertAlumno() esta sin implementar (retorna null). Se usa el
    // gateway (registrarAlumno crea tbl_perfil + tbl_alumno atomicamente, es el mismo
    // codigo que usa el flujo real de registro) en vez de SQL directo.
    @Autowired
    private IPerfilGateway perfilGateway;

    @BeforeAll
    void crearFixtures() {
        // Categoria/Curso/Grupo exigen (en el gateway, no solo en el DDL) una imagen
        // ya existente -- CAT_IMAGEN/CUR_IMAGEN/GRP_IMAGEN son nullable en la BD, pero
        // CategoriaGateway/CursoGateway/GrupoGateway llaman repoImagen.existsById(...)
        // igual, que lanza IllegalArgumentException si el id es null. Se crea una sola
        // imagen de prueba y se reutiliza en los tres.
        ImagenDto imagenDto = new ImagenDto();
        imagenDto.setNombre("imagen-fixture-it.png");
        imagenDto.setTipoArchivo("image/png");
        imagenDto.setDatosBase64(Base64.getEncoder().encodeToString(new byte[]{1, 2, 3, 4}));
        Integer imagenId = imagenServicio.insertarImagen(imagenDto).getId();

        try {
            categoriaCursoServicio.obtenerCategoriaCursoPorId(CATEGORIA);
        } catch (NoExisteExcepcion e) {
            Categoria categoria = new Categoria();
            categoria.setTitulo(CATEGORIA);
            categoria.setDescripcion("Categoria de prueba para InscripcionConcurrenciaIT");
            categoria.setImagen(imagenId);
            categoria.setEliminado(0);
            categoriaCursoServicio.insertarCategoria(categoria);
        }

        try {
            Curso curso = new Curso();
            curso.setNombre(CURSO);
            curso.setCategoriaCurso(CATEGORIA);
            curso.setDescripcion("Curso de prueba para InscripcionConcurrenciaIT");
            curso.setImagenId(imagenId);
            curso.setDeporte(DEPORTE_SEED);
            curso.setEstadoCurso(EstadoCurso.ACTIVO);
            curso.setEstadoInscripciones(EstadoInscripciones.ABIERTO);
            cursoServicio.insertarCurso(curso);
        } catch (YaExisteElementoExcepcion e) {
            // Ya existe de una corrida anterior contra BD persistente; se reutiliza.
        }

        crearGrupoSiNoExiste(ITERABLE, 1, imagenId);
        crearGrupoSiNoExiste(ITERABLE_PROMOCION, 1, imagenId);

        // tbl_inscripcion.PERF_ID tiene FK hacia tbl_alumno -- cada alumno usado por
        // los tests debe existir antes de poder inscribirlo.
        for (String alumnoId : ALUMNOS_CONCURRENCIA) {
            crearAlumnoSiNoExiste(alumnoId);
        }
        crearAlumnoSiNoExiste(ALUMNO_OCUPA_CUPO);
        crearAlumnoSiNoExiste(ALUMNO_EN_ESPERA);
        crearAlumnoSiNoExiste(ALUMNO_FILLER_PROMOCION);
        crearAlumnoSiNoExiste(ALUMNO_CANDIDATO_1);
        crearAlumnoSiNoExiste(ALUMNO_CANDIDATO_2);
    }

    private void crearGrupoSiNoExiste(int iterable, int cupos, Integer imagenId) {
        try {
            grupoServicio.obtenerGrupoPorId(CATEGORIA, CURSO, ANIO, iterable);
        } catch (NoExisteExcepcion e) {
            Grupo grupo = new Grupo();
            grupo.setCategoria(CATEGORIA);
            grupo.setCurso(CURSO);
            grupo.setAnio(ANIO);
            grupo.setIterable(iterable);
            grupo.setImagenGrupo(imagenId);
            grupo.setIdInstructor(INSTRUCTOR_SEED_ID);
            grupo.setCupos(cupos);
            grupo.setFechaCreacion(LocalDate.now());
            grupo.setPeriodo(1);
            grupoServicio.insertarGrupo(grupo);
        }
    }

    private void crearAlumnoSiNoExiste(String alumnoId) {
        try {
            Perfil perfil = new Perfil();
            perfil.setId(alumnoId);
            perfil.setNombre("Alumno Prueba " + alumnoId);
            perfil.setCorreo(alumnoId + "@it.unicauca.edu.co");
            perfil.setTipoId("CC");
            perfil.setSexo("M");
            perfil.setTipoAlumno("Estudiante");
            perfilGateway.registrarAlumno(perfil);
        } catch (YaExisteElementoExcepcion e) {
            // Ya existe de una corrida anterior contra BD persistente; se reutiliza.
        }
    }

    @Test
    void inscripcion_concurrente_soloUnaPermitida_cuandoHayUnCupo() throws Exception {
        // Preparar: 3 alumnos distintos intentan inscribirse al mismo grupo con 1 cupo
        String categoria = CATEGORIA;
        String curso = CURSO;
        int anio = ANIO;
        int iterable = ITERABLE;

        List<String> alumnosIds = ALUMNOS_CONCURRENCIA;
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
                    // inscribir() nunca lanza excepcion cuando el grupo esta lleno --
                    // retorna estado EN_ESPERA. "Exitoso" significa realmente obtener
                    // el cupo (INSCRITO), no solo que la llamada no haya fallado.
                    Inscripcion resultado = servicio.inscribir(
                            new Inscripcion(alumnoId, categoria, curso, anio, iterable, null, null, null));
                    if ("INSCRITO".equals(resultado.getEstado())) {
                        exitosos.incrementAndGet();
                    } else {
                        rechazados.incrementAndGet();
                    }
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

    /**
     * Regresion HALLAZGO 3-B: salir voluntariamente de la lista de espera no debe
     * promover a nadie (no se libero ningun cupo), por lo que los cupos disponibles
     * deben permanecer iguales antes y despues.
     *
     * El test ocupa el unico cupo del grupo el mismo (en vez de depender de que
     * inscripcion_concurrente_soloUnaPermitida_cuandoHayUnCupo haya corrido antes)
     * para no depender del orden de ejecucion entre tests -- JUnit no lo garantiza.
     */
    @Test
    void salirDeListaEspera_noSobreInscribeGrupo() {
        String categoria = CATEGORIA;
        String curso = CURSO;
        int anio = ANIO;
        int iterable = ITERABLE;
        String alumnoOcupaCupo = ALUMNO_OCUPA_CUPO;
        String alumnoEnEspera = ALUMNO_EN_ESPERA;

        // Ocupa el unico cupo del grupo (o queda EN_ESPERA si ya estaba ocupado por
        // una corrida local anterior -- de cualquier forma, el grupo queda sin cupos).
        servicio.inscribir(new Inscripcion(alumnoOcupaCupo, categoria, curso, anio, iterable, null, null, null));

        Inscripcion resultado = servicio.inscribir(
                new Inscripcion(alumnoEnEspera, categoria, curso, anio, iterable, null, null, null));
        assertEquals("EN_ESPERA", resultado.getEstado(),
                "Precondicion no cumplida: se esperaba que el grupo ya estuviera sin cupos");

        Disponibilidad antes = servicio.obtenerDisponibilidad(categoria, curso, anio, iterable);

        servicio.desvincularInscripcion(alumnoEnEspera, categoria, curso, anio, iterable);

        Disponibilidad despues = servicio.obtenerDisponibilidad(categoria, curso, anio, iterable);

        assertEquals(antes.getCuposDisponibles(), despues.getCuposDisponibles(),
                "Salir de la lista de espera no debe cambiar los cupos disponibles: nadie debio ser promovido");
        assertEquals(antes.getTamanoListaEspera() - 1, despues.getTamanoListaEspera(),
                "La lista de espera debe reducirse en 1 (el alumno que salio), sin promociones");
    }

    /**
     * Regresion del mismo bug de aislamiento REPEATABLE READ que afecta a
     * inscribir(): promoverManualmente() tambien hace una lectura no bloqueante
     * (existeEnEspera) antes de tomar el lock del grupo, asi que el conteo de
     * cupos posterior podia leer un snapshot obsoleto bajo concurrencia real.
     *
     * Escenario: un grupo con 1 cupo ya ocupado y 2 candidatos en espera. Un
     * Coordinador amplia los cupos de 1 a 2 (via actualizarGrupo, sin desvincular
     * a nadie -- por eso no se dispara la auto-promocion de HALLAZGO 3-B), dejando
     * 1 cupo genuinamente libre con 2 personas en cola. Dos Coordinadores
     * promueven a los 2 candidatos al mismo tiempo: solo 1 debe tener exito.
     */
    @Test
    void promoverManualmente_concurrente_soloUnaPermitida_cuandoHayUnCupoLiberado() throws Exception {
        servicio.inscribir(new Inscripcion(
                ALUMNO_FILLER_PROMOCION, CATEGORIA, CURSO, ANIO, ITERABLE_PROMOCION, null, null, null));
        servicio.inscribir(new Inscripcion(
                ALUMNO_CANDIDATO_1, CATEGORIA, CURSO, ANIO, ITERABLE_PROMOCION, null, null, null));
        servicio.inscribir(new Inscripcion(
                ALUMNO_CANDIDATO_2, CATEGORIA, CURSO, ANIO, ITERABLE_PROMOCION, null, null, null));

        Grupo grupoActual = grupoServicio.obtenerGrupoPorId(CATEGORIA, CURSO, ANIO, ITERABLE_PROMOCION);
        grupoActual.setCupos(2);
        grupoServicio.actualizarGrupo(CATEGORIA, CURSO, ANIO, ITERABLE_PROMOCION, grupoActual);

        List<String> candidatos = List.of(ALUMNO_CANDIDATO_1, ALUMNO_CANDIDATO_2);
        int hilos = candidatos.size();

        CountDownLatch listo = new CountDownLatch(hilos);
        CountDownLatch inicio = new CountDownLatch(1);
        AtomicInteger exitosos = new AtomicInteger(0);
        AtomicInteger rechazados = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(hilos);
        List<Future<?>> futures = new ArrayList<>();

        for (String candidato : candidatos) {
            futures.add(executor.submit(() -> {
                listo.countDown();
                assertDoesNotThrow(() -> inicio.await());
                try {
                    // promoverManualmente() si lanza CuposAgotadosExcepcion cuando no
                    // hay cupo -- a diferencia de inscribir(), no hace falta revisar
                    // el estado del resultado.
                    servicio.promoverManualmente(candidato, CATEGORIA, CURSO, ANIO, ITERABLE_PROMOCION);
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

        assertTrue(exitosos.get() <= 1,
                "Con 1 cupo liberado, a lo sumo 1 promocion manual debe ser exitosa, pero fueron: " + exitosos.get());
        assertTrue(rechazados.get() >= 1,
                "Con 1 cupo liberado y 2 candidatos concurrentes, al menos 1 debe ser rechazado");
    }
}

# Implementación: Registro Transparente de Instructor

## Resumen

Se implementó un flujo completo y atómico para registrar instructores desde el frontend en una **única llamada HTTP**, manteniendo transparencia total para el usuario administrador.

## Cambios Realizados

### 1. Interfaz de Servicios (`IInstructorServicio`)

**Archivo:** `aplicacion/puertos/puertosEntrada/IInstructorServicio.java`

```java
public InstructorDto registrarInstructor(PerfilDto perfilDto);
```

- Nuevo método que acepta `PerfilDto` con datos del instructor
- Retorna `InstructorDto` completo registrado

### 2. Implementación de Servicio (`InstructorServicio`)

**Archivo:** `dominio/servicios/InstructorServicio.java`

```java
@Transactional  // ← CLAVE: garantiza atomicidad
public InstructorDto registrarInstructor(PerfilDto perfilDto) {
    // Validar que no exista
    if (instructorsGateway.existeInstructor(perfilDto.getId())) {
        throw new YaExisteElementoExcepcion(...);
    }

    // Convertir DTO a modelo
    Perfil perfil = Perfil.fabricarDeDto(perfilDto);

    // Llamar gateway para orquestar inserciones
    Instructor instructorRegistrado = instructorsGateway.registrarInstructor(
        perfil,
        perfilDto.getTipoAlumno()
    );

    // Retornar DTO
    return InstructorDto.fabricarDeModelo(instructorRegistrado);
}
```

**Características:**

- `@Transactional`: Si algo falla, ROLLBACK automático de todo
- Validaciones centralizadas
- Manejo de excepciones específicas

### 3. Interfaz de Gateway (`IInstructorGateway`)

**Archivo:** `aplicacion/puertos/puertosSalida/IInstructorGateway.java`

```java
public Instructor registrarInstructor(Perfil perfil, String tipoAlumno);
```

- Nuevo método en la interfaz puerto de salida

### 4. Gateway Implementation (`InstructorGateway`)

**Archivo:** `infraestructura/adaptadores/adaptadoresSecundarios/persistenciaSQL/gateway/InstructorGateway.java`

```java
@Override
public Instructor registrarInstructor(Perfil perfil, String tipoAlumno) {
    // [1] INSERT TBL_PERFIL
    PerfilEntidad entidadPerfil = PerfilEntidad.fabricarDeModelo(perfil, 0);
    PerfilEntidad perfilGuardado = repoPerfil.save(entidadPerfil);

    // [2] INSERT TBL_ALUMNO (vinculación al programa)
    AlumnoEntidad entidadAlumno = AlumnoEntidad.fabricarDePerfil(perfil, 0);
    entidadAlumno.setTipoAlumno(tipoAlumno);
    repoAlumno.save(entidadAlumno);

    // [3] INSERT TBL_INSTRUCTOR
    InstructorEntidad entidadInstructor = new InstructorEntidad();
    entidadInstructor.setIdPerfil(perfilGuardado.getPerf_id());
    entidadInstructor.setPerfil(perfilGuardado);
    InstructorEntidad instructorGuardado = repoInstructor.save(entidadInstructor);

    // Construir y retornar objeto de dominio
    Instructor instructorRegistrado = new Instructor();
    instructorRegistrado.setInst_codigo(instructorGuardado.getIdPerfil());
    instructorRegistrado.setPerfil(Perfil.fabricarDeEntidad(perfilGuardado));
    instructorRegistrado.getPerfil().setTipoAlumno(tipoAlumno);

    return instructorRegistrado;
}
```

**Características:**

- Ejecuta 3 INSERTs atómicamente (bajo `@Transactional` del servicio)
- **NO duplica datos:** No crea múltiples registros de un mismo perfil
- Preserva integridad referencial en BD

### 5. Endpoint REST (`InstructoresRest`)

**Archivo:** `infraestructura/adaptadores/adaptadoresPrimarios/adaptadorRest/api/InstructoresRest.java`

```java
@PostMapping("/RegistroPerfilInstructor")
@Operation(summary = "Registra un nuevo instructor con su perfil de forma transparente")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Instructor registrado exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "409", description = "El instructor ya existe")
})
public ResponseEntity<InstructorDto> registrarInstructor(
        @RequestBody @Valid PerfilDto perfilDto) {
    PeticionLogger.log(LOGGER, "POST", "/api/v2/RegistroPerfilInstructor",
        "id: " + perfilDto.getId() + ", nombre: " + perfilDto.getNombre());
    InstructorDto respuesta = servicioInstructor.registrarInstructor(perfilDto);
    return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
}
```

## Flujo de Datos Completo

```
┌─────────────────────────────────────────────────────────────────┐
│ Frontend: Modal "Agregar Instructor"                            │
│                                                                  │
│ Usuario admin completa:                                          │
│  - ID: "1061813673"                                              │
│  - Nombre: "Juan Sebastian Pisso"                                │
│  - Correo: "jpisso@unicauca.edu.co"                              │
│  - Sexo: "M"                                                     │
│  - TipoId: "CC"                                                  │
│  - TipoAlumno: "Docente"  (categoría en programa)               │
│                                                                  │
│ Presiona: "Aceptar"                                              │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
         ┌───────────────────────────┐
         │ [1 LLAMADA HTTP]          │
         │ POST /api/v2/RegistroPerfilInstructor │
         │ Content-Type: application/json │
         │                           │
         │ {                         │
         │   "id": "1061813673",     │
         │   "nombre": "...",        │
         │   "correo": "...",        │
         │   "sexo": "M",            │
         │   "tipoId": "CC",         │
         │   "tipoAlumno": "Docente" │
         │ }                         │
         └───────────────┬───────────┘
                         │
                         ▼
    ┌────────────────────────────────────┐
    │ Backend: @Transactional            │
    │                                    │
    │ InstructorServicio.registrarInstructor()
    │  ├─ Validar no existe              │
    │  └─ Gateway.registrarInstructor()  │
    │      ├─ [1] INSERT TBL_PERFIL     │
    │      ├─ [2] INSERT TBL_ALUMNO     │
    │      └─ [3] INSERT TBL_INSTRUCTOR │
    │                                    │
    │ Si falla: ROLLBACK (todo o nada)  │
    └────────────────┬───────────────────┘
                     │
                     ▼
    ┌────────────────────────────────────┐
    │ Response: 201 CREATED              │
    │ {                                  │
    │   "id": "1061813673",              │
    │   "nombre": "Juan S. Pisso",       │
    │   "correo": "jpisso@unicauca...",  │
    │   "sexo": "M"                      │
    │ }                                  │
    └────────────────┬───────────────────┘
                     │
                     ▼
    ┌────────────────────────────────────┐
    │ Frontend: Mostrar success           │
    │ "Instructor registrado ✓"          │
    │                                    │
    │ Admin ve UN resultado transparente │
    └────────────────────────────────────┘
```

## Base de Datos Resultante

### Después de registrar instructor

```sql
-- TBL_PERFIL
INSERT INTO TBL_PERFIL (
    PERF_ID, PERF_NOMBRE, PERF_CORREO, PERF_SEXO, PERF_TIPOID, META_ELIMINADO
) VALUES (
    '1061813673', 'Juan Sebastian Pisso', 'jpisso@unicauca.edu.co',
    'M', 'CC', 0
);

-- TBL_ALUMNO (vinculación al programa)
INSERT INTO TBL_ALUMNO (
    PERF_ID, ALM_TIPO, META_ELIMINADO
) VALUES (
    '1061813673', 'Docente', 0
);

-- TBL_INSTRUCTOR (autorización para enseñar)
INSERT INTO TBL_INSTRUCTOR (
    PERF_ID, META_ELIMINADO
) VALUES (
    '1061813673', 0
);
```

### Estado de la Base de Datos

| Tabla          | PERF_ID    | ALM_TIPO | Propósito                       |
| -------------- | ---------- | -------- | ------------------------------- |
| TBL_PERFIL     | 1061813673 | -        | Datos base del usuario          |
| TBL_ALUMNO     | 1061813673 | Docente  | Participa en programa deportivo |
| TBL_INSTRUCTOR | 1061813673 | -        | Está autorizado para enseñar    |

**Nota:** Un instructor también es "alumno" del sistema deportivo (participa con categoría "Docente").

## Ventajas de esta Implementación

✅ **Transparencia Total**

- Admin ve 1 botón → 1 acción → 1 resultado
- Sin pasos intermedios visibles

✅ **Atomicidad Garantizada**

- `@Transactional` garantiza todo-o-nada
- Si algo falla: ROLLBACK completo
- No hay registros huérfanos

✅ **Validación Centralizada**

- Validaciones en el servicio
- Excepciones específicas (YaExiste, ErrorInterno, etc.)
- Respuestas HTTP consistentes

✅ **Mantenibilidad**

- Lógica en backend (no duplicada en frontend)
- Un único punto de cambio
- Fácil de extender

✅ **Seguridad**

- Validación `@Valid` en DTO
- Anotaciones `@NotBlank`, `@Email`, etc.
- Lógica de negocio protegida

## Próximos Pasos (Opcional)

1. **Similares para Coordinador:**
   - Crear endpoint `/RegistroPerfilCoordinador`
   - Seguir el mismo patrón

2. **Actualizar Frontend:**
   - Modal "Agregar instructor" llama a: `POST /api/v2/RegistroPerfilInstructor`
   - Envía `PerfilDto` completo
   - Espera `InstructorDto` en respuesta

3. **Validación adicional (si es necesario):**
   - Verificar email único
   - Restricciones de rol

## Compilación y Testing

```bash
# Compilar (ya verificado ✅)
mvn clean compile
# BUILD SUCCESS

# Ejecutar tests
mvn test

# Ejecutar aplicación
mvn spring-boot:run
# Puerto: 8082
# Swagger: http://localhost:8082/swagger-ui.html
```

## Resumen de Cambios

| Archivo                    | Cambio                                          |
| -------------------------- | ----------------------------------------------- |
| `IInstructorServicio.java` | ➕ Método `registrarInstructor(PerfilDto)`      |
| `InstructorServicio.java`  | ➕ Implementación con `@Transactional`          |
| `IInstructorGateway.java`  | ➕ Método `registrarInstructor(Perfil, String)` |
| `InstructorGateway.java`   | ➕ Implementación que maneja 3 INSERTs          |
| `InstructoresRest.java`    | ➕ Endpoint `POST /RegistroPerfilInstructor`    |

**Total de cambios:** 5 archivos modificados
**Líneas de código:** ~200 líneas agregadas/actualizadas
**Compilación:** ✅ BUILD SUCCESS

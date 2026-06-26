# Instrucciones para Agentes de IA - DeporteParaTodos Backend

## Arquitectura del Proyecto

Este es un backend Spring Boot 3.2.3 con arquitectura **hexagonal** aplicada a nivel monolítico. La refactorización en curso busca **reducir acoplamiento**, mejorar mantenibilidad y facilitar futuras extensiones.

### Estructura de Capas

```
dominio/
  ├── modelo/          # Entidades de dominio (Alumno, Curso, Grupo, etc.)
  └── servicios/       # Lógica de negocio pura (ej: AlumnoServicio)
aplicacion/puertos/
  ├── puertosEntrada/  # Interfaces de servicios (ej: IAlumnoServicio)
  └── puertosSalida/   # Interfaces gateway para persistencia (ej: IAlumnoGateway)
infraestructura/
  ├── adaptadores/
  │   ├── adaptadoresPrimarios/  # REST controllers (entradas)
  │   └── adaptadoresSecundarios/ # SQL persistence, gateways (salidas)
  ├── mappers/         # Configuración de ModelMapper
  ├── controladorExcepciones/ # Manejo centralizado de errores
  └── logs/           # Logging utilities
```

### Decisiones de Diseño Clave

1. **Evitar relaciones complejas de entidades en Java**: Las relaciones OneToMany/ManyToMany se manejan explícitamente en SQL. Los servicios construyen objetos de dominio a partir de resultados raw SQL cuando es necesario.
   - Ejemplo: `obtenerAlumnosGrupo()` ejecuta consulta SQL y construye `List<Alumno>` manualmente
   - Esto evita ciclos infinitos de serialización y facilita mantenimiento

2. **Servicios independientes**: Cada servicio (AlumnoServicio, CursoServicio, etc.) es lo más autónomo posible, aunque requiere más código que una arquitectura fuertemente acoplada.

## Flujo de Datos (Ejemplo: Alumno)

1. **REST Controller** (`AlumnoRest`) → recibe HTTP request, valida DTOs
2. **IAlumnoServicio** (puerto entrada) → interface pública del servicio
3. **AlumnoServicio** (dominio) → contiene lógica de negocio
4. **IAlumnoGateway** (puerto salida) → interface de persistencia
5. **AlumnoGateway** (infraestructura) → implementa queries, convierte entidades ↔ modelos
6. **AlumnoEntidad** (JPA) → mapeo ORM a tabla `tbl_alumno`

## Patrones y Convenciones

### DTOs y Mapeo

- **DTOs** en `infraestructura/adaptadores/adaptadoresPrimarios/DTOs/` con método estático `fabricarDeModelo()`:
  ```java
  public static AlumnoDto fabricarDeModelo(Alumno modelo) {
      AlumnoDto dto = new AlumnoDto();
      dto.setNombre(modelo.getPerfil().getNombre());
      // ... mapping manual
      return dto;
  }
  ```
- Usar `@Qualifier("modelMapperGenerico")` para ModelMapper genérico definido en `Mapper.java`
- Para DTOs complejos con transformaciones especiales, crear beans adicionales en `Mapper.java`

### Servicios y Gateways

- **Servicios** en `dominio/servicios/` implementan lógica de negocio y validaciones
- **Gateways** en `infraestructura/adaptadores/adaptadoresSecundarios/persistenciaSQL/gateway/` implementan interfaces de `aplicacion/puertos/puertosSalida/`
- Siempre inyectar gateway vía `@Autowired private IAlumnoGateway alumnoGateway`
- Los gateways manejan la conversión ModelMapper → Objeto de dominio

### Excepciones Personalizadas

Lanzar excepciones especializadas desde servicios:

- `ListadoVacioExcepcion` - cuando listado está vacío
- `NoExisteExcepcion` - cuando entidad no existe
- `YaExisteElementoExcepcion` - cuando intenta duplicar
- Ubicadas en: `infraestructura/controladorExcepciones/excepciones/`
- ControladorExcepciones captura y convierte a ResponseEntity

### Validación y Anotaciones

- Usar `@Valid` en parámetros de controladores REST
- Validar con anotaciones: `@NotBlank`, `@NotNull` en DTOs
- Lombok simplifica entidades: `@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor`

### Logging

- Inyectar logger: `private static final Logger LOGGER = LoggerFactory.getLogger(MiClase.class)`
- Usar `PeticionLogger` para logs de peticiones HTTP

## Configuración Base de Datos

- **BBDD**: Oracle (jdbc:oracle:thin:@localhost:1521:xe)
- **Credenciales de desarrollo**: usuario=TEST, password=test (en `application.properties`)
- **Multipart**: Habilitado para imágenes (máx 10MB)
- **JPA DDL**: `ddl-auto=none` - migraciones manuales

## Construcción y Ejecución

```powershell
# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar aplicación
mvn spring-boot:run

# Puerto por defecto: 8082
# Swagger OpenAPI: http://localhost:8082/swagger-ui.html
```

## Puntos de Extensión

### Agregar Nueva Entidad (ej: Evento)

1. Crear `Evento.java` en `dominio/modelo/`
2. Crear `EventoEntidad.java` en `infraestructura/adaptadores/adaptadoresSecundarios/persistenciaSQL/entidades/`
3. Crear `IEventoGateway.java` en `aplicacion/puertos/puertosSalida/`
4. Crear `EventoGateway.java` en `infraestructura/adaptadores/adaptadoresSecundarios/persistenciaSQL/gateway/`
5. Crear `IEventoServicio.java` en `aplicacion/puertos/puertosEntrada/`
6. Crear `EventoServicio.java` en `dominio/servicios/`
7. Crear `EventoRest.java` en `infraestructura/adaptadores/adaptadoresPrimarios/adaptadorRest/api/`
8. Crear DTOs en `infraestructura/adaptadores/adaptadoresPrimarios/adaptadorRest/DTOs/`

## Dependencias Críticas

- **Spring Boot 3.2.3** - Framework base
- **Spring Data JPA** - ORM con Hibernate
- **ModelMapper 3.0.0** - Mapeo automático (preferir manual en gateways)
- **Lombok** - Reduce boilerplate en entidades
- **Validation (Jakarta)** - Validación de datos
- **SpringDoc OpenAPI 2.3.0** - Documentación Swagger
- **Oracle JDBC 19.8.0.0** - Driver Oracle

## Notas Importantes

- **No crear relaciones directas en Java**: Si necesitas datos de múltiples entidades, consulta SQL con JOIN
- **Mapeo manual en gateways**: Mejor control y evita problemas de relaciones circulares
- **Escalabilidad**: Esta arquitectura prepara para migrar servicios a microservicios sin cambios externos
- **Colaboración**: El proyecto busca mantener colaboración continua en desarrollo

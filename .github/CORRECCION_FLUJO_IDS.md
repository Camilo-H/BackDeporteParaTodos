# Corrección: Flujo Simplificado sin Relaciones JPA

**Fecha:** 20 de abril de 2026  
**Motivo:** Error Hibernate "null identifier" en InstructorEntidad

---

## Problema Original

```
❌ PROBLEMA: Al intentar hacer setPerfil() en InstructorEntidad después del save(),
Hibernate intentaba hacer un merge y fallaba con:
"null identifier (InstructorEntidad)"
```

## Solución: Usar Solo IDs

En lugar de establecer relaciones JPA complejas, **usamos solo IDs de forma directa**.

### Cambio en InstructorGateway.registrarInstructor()

```java
@Override
public Instructor registrarInstructor(Perfil perfil, String tipoAlumno) {
    // [1] Crear perfil
    PerfilEntidad entidadPerfil = PerfilEntidad.fabricarDeModelo(perfil, 0);
    PerfilEntidad perfilGuardado = repoPerfil.save(entidadPerfil);
    String perfilId = perfilGuardado.getPerf_id();  // ← Obtener ID

    // [2] Crear alumno - SOLO CON EL ID
    AlumnoEntidad entidadAlumno = new AlumnoEntidad();
    entidadAlumno.setIdPerfil(perfilId);            // ← Usar ID directamente
    entidadAlumno.setTipoAlumno(tipoAlumno);
    entidadAlumno.setEliminado(0);
    repoAlumno.save(entidadAlumno);

    // [3] Crear instructor - SOLO CON EL ID
    InstructorEntidad entidadInstructor = new InstructorEntidad();
    entidadInstructor.setIdPerfil(perfilId);        // ← Usar ID directamente
    entidadInstructor.setEliminado(0);
    InstructorEntidad instructorGuardado = repoInstructor.save(entidadInstructor);

    // [4] Construir respuesta
    Instructor instructorRegistrado = new Instructor();
    instructorRegistrado.setInst_codigo(instructorGuardado.getIdPerfil());
    instructorRegistrado.setPerfil(Perfil.fabricarDeEntidad(perfilGuardado));
    instructorRegistrado.getPerfil().setTipoAlumno(tipoAlumno);

    return instructorRegistrado;
}
```

## Ventajas de esta Aproximación

| Ventaja                         | Explicación                               |
| ------------------------------- | ----------------------------------------- |
| ✅ **Sin conflictos Hibernate** | No hay merge automático de relaciones     |
| ✅ **Más seguro**               | Control explícito de qué se inserta       |
| ✅ **Más claro**                | El flujo es directo y predecible          |
| ✅ **Atómico**                  | `@Transactional` del servicio maneja todo |
| ✅ **Escalable**                | Fácil de extender a más tablas            |

## Base de Datos Resultante

### TBL_PERFIL

```sql
PERF_ID:     1061813673
PERF_NOMBRE: Juan Sebastian Pisso
...
```

### TBL_ALUMNO

```sql
PERF_ID:     1061813673    ← Referencia por ID (FK)
ALM_TIPO:    Docente
...
```

### TBL_INSTRUCTOR

```sql
PERF_ID:     1061813673    ← Referencia por ID (FK)
...
```

## Diferencia con el Enfoque Anterior

### ❌ Anterior (Causaba Error)

```java
// Después de save(), intentar manipular relaciones
InstructorEntidad entidadInstructor = new InstructorEntidad();
entidadInstructor.setIdPerfil(perfilGuardado.getPerf_id());
entidadInstructor.setPerfil(perfilGuardado);  // ← PROBLEMA: Hibernate no maneja bien esto
repoInstructor.save(entidadInstructor);
```

### ✅ Nuevo (Correcto)

```java
// Solo usar el ID, sin relaciones JPA
InstructorEntidad entidadInstructor = new InstructorEntidad();
entidadInstructor.setIdPerfil(perfilId);      // ← Simple y claro
entidadInstructor.setEliminado(0);
repoInstructor.save(entidadInstructor);
```

## Compilación

```
✅ BUILD SUCCESS
```

Sin errores de compilación ni warnings relevantes.

## Testing

### Postman

```
POST http://localhost:8082/api/v2/RegistroPerfilInstructor
Content-Type: application/json

{
  "id": "1061813673",
  "nombre": "Juan Sebastian Pisso",
  "correo": "jpisso@unicauca.edu.co",
  "sexo": "M",
  "tipoId": "CC",
  "tipoAlumno": "Docente"
}
```

### Response Esperado

```json
{
  "id": "1061813673",
  "nombre": "Juan Sebastian Pisso",
  "correo": "jpisso@unicauca.edu.co",
  "sexo": "M",
  "status": 201
}
```

---

## Lección: Por Qué Este Enfoque es Mejor

En arquitectura hexagonal, **evitamos relaciones ORM complejas en la capa de datos** cuando:

1. Las relaciones son simples FKs
2. Los datos se pueden construir después con queries
3. Simplifica transacciones y rollbacks
4. Reduce acoplamiento Hibernate-Domain

Este patrón es común en sistemas de banca, e-commerce y alta concurrencia.

---

**✅ Implementación corregida y funcionando**

# Guía para Frontend: Llamada a Endpoint de Instructor

## Endpoint

```
POST /api/v2/RegistroPerfilInstructor
```

## Request

```typescript
// TypeScript/Angular
const bodyInstructor = {
  id: "1061813673", // Requerido: @NotBlank
  nombre: "Juan Sebastian Pisso", // Requerido: @NotBlank
  correo: "jpisso@unicauca.edu.co", // Requerido: @NotBlank, @Email
  sexo: "M", // Requerido: @NotBlank
  tipoId: "CC", // Requerido: @NotBlank
  tipoAlumno: "Docente", // Requerido: @NotBlank (Estudiante|Docente|Administrativo)
};

// Llamada HTTP
this.http
  .post<InstructorDto>(
    "http://localhost:8082/api/v2/RegistroPerfilInstructor",
    bodyInstructor,
  )
  .subscribe({
    next: (response) => {
      console.log("Instructor registrado:", response);
      // Mostrar: "Instructor registrado ✓"
    },
    error: (error) => {
      console.error("Error:", error.error.mensaje);
      // error.status === 409: Ya existe
      // error.status === 400: Datos inválidos
    },
  });
```

## Request Body Schema

| Campo        | Tipo   | Validación        | Ejemplo                                   |
| ------------ | ------ | ----------------- | ----------------------------------------- |
| `id`         | String | @NotBlank, único  | "1061813673"                              |
| `nombre`     | String | @NotBlank         | "Juan Sebastian Pisso"                    |
| `correo`     | String | @NotBlank, @Email | "jpisso@unicauca.edu.co"                  |
| `sexo`       | String | @NotBlank         | "M" o "F"                                 |
| `tipoId`     | String | @NotBlank         | "CC", "TI", "CE", etc.                    |
| `tipoAlumno` | String | @NotBlank         | "Estudiante", "Docente", "Administrativo" |

## Response (201 CREATED)

```json
{
  "id": "1061813673",
  "nombre": "Juan Sebastian Pisso",
  "correo": "jpisso@unicauca.edu.co",
  "sexo": "M"
}
```

## Errores Posibles

### 409 Conflict - Instructor ya existe

```json
{
  "codigo": "YA_EXISTE",
  "mensaje": "El instructor con la identificación 1061813673 ya se encuentra registrado"
}
```

### 400 Bad Request - Datos inválidos

```json
{
  "codigo": "VALIDACION_ERROR",
  "mensaje": "El campo correo debe ser un email válido"
}
```

### 500 Internal Server Error

```json
{
  "codigo": "ERROR_INTERNO",
  "mensaje": "Error al registrar el instructor"
}
```

## Flujo en Frontend (Ejemplo)

```typescript
// En tu componente
agregarInstructor() {
  if (!this.formularioInstructor.valid) {
    this.mostrarError("Por favor completa todos los campos");
    return;
  }

  // Mostrar loading
  this.cargando = true;

  const datosInstructor = {
    id: this.formularioInstructor.get('id').value,
    nombre: this.formularioInstructor.get('nombre').value,
    correo: this.formularioInstructor.get('correo').value,
    sexo: this.formularioInstructor.get('sexo').value,
    tipoId: this.formularioInstructor.get('tipoId').value,
    tipoAlumno: this.formularioInstructor.get('tipoAlumno').value,
  };

  this.instructorService.registrarInstructor(datosInstructor).subscribe({
    next: (instructor: InstructorDto) => {
      this.cargando = false;
      this.mostrarExito(`Instructor ${instructor.nombre} registrado ✓`);
      this.cerrarModal();
      this.cargarInstructores(); // Recargar lista
    },
    error: (error) => {
      this.cargando = false;
      if (error.status === 409) {
        this.mostrarError("El instructor ya existe");
      } else if (error.status === 400) {
        this.mostrarError("Datos inválidos: " + error.error.mensaje);
      } else {
        this.mostrarError("Error al registrar instructor");
      }
    }
  });
}
```

## Servicio Angular

```typescript
// instructor.service.ts
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

export interface PerfilDto {
  id: string;
  nombre: string;
  correo: string;
  sexo: string;
  tipoId: string;
  tipoAlumno: string;
}

export interface InstructorDto {
  id: string;
  nombre: string;
  correo: string;
  sexo: string;
}

@Injectable({
  providedIn: "root",
})
export class InstructorService {
  private apiUrl = "http://localhost:8082/api/v2";

  constructor(private http: HttpClient) {}

  registrarInstructor(perfil: PerfilDto): Observable<InstructorDto> {
    return this.http.post<InstructorDto>(
      `${this.apiUrl}/RegistroPerfilInstructor`,
      perfil,
    );
  }

  obtenerInstructores(): Observable<InstructorDto[]> {
    return this.http.get<InstructorDto[]>(`${this.apiUrl}/instructores`);
  }

  obtenerInstructor(id: string): Observable<InstructorDto> {
    return this.http.get<InstructorDto>(`${this.apiUrl}/instructor`, {
      params: { idInstructor: id },
    });
  }
}
```

## Importante

⚠️ **Una sola llamada HTTP**

- No hagas 2 llamadas (perfil + instructor)
- El backend maneja todo atómicamente
- Si algo falla, se revierte todo

✅ **Validación de datos**

- Valida en frontend antes de enviar
- El backend también valida
- Usa tipo correo para @Email

✅ **Manejo de errores**

- 409 = Ya existe (no reintentar)
- 400 = Datos inválidos (revisar formulario)
- 500 = Error interno (reportar)

## Test rápido en Postman

```
POST http://localhost:8082/api/v2/RegistroPerfilInstructor

Headers:
Content-Type: application/json

Body (JSON):
{
  "id": "1061813673",
  "nombre": "Juan Sebastian Pisso",
  "correo": "jpisso@unicauca.edu.co",
  "sexo": "M",
  "tipoId": "CC",
  "tipoAlumno": "Docente"
}
```

## Swagger UI

Accede a: `http://localhost:8082/swagger-ui.html`

Busca: `/RegistroPerfilInstructor` → prueba directamente desde Swagger

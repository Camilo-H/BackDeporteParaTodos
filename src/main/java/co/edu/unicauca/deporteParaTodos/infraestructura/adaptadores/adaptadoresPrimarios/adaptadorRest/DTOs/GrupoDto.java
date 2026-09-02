package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GrupoDto {
    @NotBlank(message = "{grupo.categoria.blank}")
    private String categoria;

    @NotBlank(message = "{grupo.curso.blank}")
    private String curso;

    private int anio;

    private int iterable;

    @NotNull(message = "el identificador de imagen no puede estar vacio")
    @PositiveOrZero(message = "El id de imagen debe ser un numero natural")
    private Integer imagenGrupo;

    @NotNull(message = "los cupos no pueden estar vacios")
    @Positive(message = "la cantidad de cupos debe ser mayor que cero")
    private Integer cupos;

    private String idInstructor;

    @PastOrPresent(message = "la fecha de creacion no puede ser periodo futuro")
    @NotNull(message = "la fecha de creacion no puede estar vacia")
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCreacion;

    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFinalizacion;

    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInscripcionApertura;

    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaIncripcionCierre;

    private Integer periodo;
}

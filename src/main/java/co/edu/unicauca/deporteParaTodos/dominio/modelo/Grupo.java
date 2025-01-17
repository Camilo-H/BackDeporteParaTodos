package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Grupo {

    private String nombre;

    private int anio;

    private int iterable;

    private Curso curso;

    private Imagen imagen;

    private int cupos;

    private String estado;

    private Date fechaCreacion;

    private Date fechaFinalizacion;

    private List<Horario> horarios;

    private List<Inscripcion> inscripciones;

}

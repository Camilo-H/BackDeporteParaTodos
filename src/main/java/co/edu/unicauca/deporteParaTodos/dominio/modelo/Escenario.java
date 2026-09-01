package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Escenario {

    private Integer id;
    private String nombre;
    private String descripcion;
    private int numTribunas;
    private boolean disponible;
    private Integer eliminado;

}

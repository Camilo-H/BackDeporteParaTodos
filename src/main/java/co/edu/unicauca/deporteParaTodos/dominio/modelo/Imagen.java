package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Imagen {
    
    private Integer id;

    private String nombre;

    private String tipoArchivo;

    private Long longitud;

    private byte[] datos;
}

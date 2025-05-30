package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {
    
    private String titulo;
    private String descripcion;
    private int imagenId;

    public static CategoriaDto fabricarDeModelo(Categoria modelo){
        try{
            CategoriaDto dto = new CategoriaDto();
            dto.setTitulo(modelo.getTitulo());
            dto.setDescripcion(modelo.getDescripcion());
            dto.setImagenId(modelo.getImagen());
            return dto;
        }catch(Exception e){
            return null;
        }
    }
}

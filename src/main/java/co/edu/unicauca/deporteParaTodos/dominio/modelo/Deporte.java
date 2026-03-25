package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.DeporteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Deporte {
    
    private String nombre;

    public static Deporte fabricarDeDto(DeporteDto dto){
        try{
            Deporte fabricado = new Deporte();
            fabricado.setNombre(dto.getNombre());
            return fabricado;
        }catch(Exception e){
            return null;
        }
    }
}

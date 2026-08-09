package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.EscenarioDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.EscenarioEntidad;
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

    public static Escenario fabricarDeEntidad(EscenarioEntidad entidad) {
        try {
            Escenario escenario = new Escenario();
            escenario.setId(entidad.getId());
            escenario.setNombre(entidad.getNombre());
            escenario.setDescripcion(entidad.getDescripcion());
            escenario.setNumTribunas(entidad.getNumTribunas());
            escenario.setDisponible(entidad.getDisponible() == 1);
            escenario.setEliminado(entidad.getEliminado());
            return escenario;
        } catch (Exception e) {
            return null;
        }
    }

    public static Escenario fabricarDeDto(EscenarioDto dto) {
        try {
            Escenario escenario = new Escenario();
            escenario.setId(dto.getId());
            escenario.setNombre(dto.getNombre());
            escenario.setDescripcion(dto.getDescripcion());
            escenario.setNumTribunas(dto.getNumTribunas() != null ? dto.getNumTribunas() : 0);
            escenario.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
            escenario.setEliminado(0);
            return escenario;
        } catch (Exception e) {
            return null;
        }
    }
}

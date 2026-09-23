package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Disponibilidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DisponibilidadDto {

    private int cuposTotales;
    private int cuposDisponibles;
    private int tamanoListaEspera;

    public static DisponibilidadDto fabricarDeModelo(Disponibilidad modelo) {
        return new DisponibilidadDto(
                modelo.getCuposTotales(),
                modelo.getCuposDisponibles(),
                modelo.getTamanoListaEspera());
    }
}

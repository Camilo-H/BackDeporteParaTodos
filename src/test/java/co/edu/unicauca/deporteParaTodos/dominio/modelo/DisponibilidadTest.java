package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisponibilidadTest {

    @Test
    void constructorSinArgumentosYSetters_asignanValoresCorrectamente() {
        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setCuposTotales(10);
        disponibilidad.setCuposDisponibles(4);
        disponibilidad.setTamanoListaEspera(3);

        assertEquals(10, disponibilidad.getCuposTotales());
        assertEquals(4, disponibilidad.getCuposDisponibles());
        assertEquals(3, disponibilidad.getTamanoListaEspera());
    }
}

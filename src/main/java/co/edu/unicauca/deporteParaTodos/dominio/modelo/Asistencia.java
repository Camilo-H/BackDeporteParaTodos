package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asistencia {
    private String idPerfil;
    private Integer clsCodigo;

    public static Asistencia fabricarDeEntidad(co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AsistenciaEntidad entidad) {
        try {
            Asistencia modelo = new Asistencia();
            modelo.setIdPerfil(entidad.getPerfilId());
            modelo.setClsCodigo(entidad.getClaseCodigo());
            return modelo;
        } catch (Exception e) {
            return null;
        }
    }
}

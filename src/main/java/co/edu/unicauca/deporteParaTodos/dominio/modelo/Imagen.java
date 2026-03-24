package co.edu.unicauca.deporteParaTodos.dominio.modelo;

import java.util.Base64;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
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

    public static Imagen fabricaFromImagenDto(ImagenDto dto) {
        try {
            Imagen modelo = new Imagen();
            modelo.setId(dto.getId());
            modelo.setNombre(dto.getNombre());
            modelo.setTipoArchivo(dto.getTipoArchivo());

            if (dto.getDatosMultipartFile() != null && !dto.getDatosMultipartFile().isEmpty()) {
                modelo.setDatos(dto.getDatosMultipartFile().getBytes());
                modelo.setLongitud(dto.getDatosMultipartFile().getSize());
            } else if (dto.getDatosBase64() != null && !dto.getDatosBase64().isBlank()) {
                byte[] datos = Base64.getDecoder().decode(dto.getDatosBase64());
                modelo.setDatos(datos);
                modelo.setLongitud((long) datos.length);
            }

            return modelo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

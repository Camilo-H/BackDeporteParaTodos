package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO;

import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class V2ImagenDTO {
    
    private Integer id;

    
    private String nombre;

    
    private String tipoArchivo;

    
    private Long longitud;
    
    private MultipartFile datosMultipartFile;

    private String datosBase64;

    public static V2ImagenDTO fabricaFromImagenEntidad(ImagenEntidad entidad){
        try{
            V2ImagenDTO dto = new V2ImagenDTO();
            dto.setId(entidad.getId());
            dto.setNombre(entidad.getNombre());
            dto.setLongitud(entidad.getLongitud());
            dto.setTipoArchivo(entidad.getTipoArchivo());
            dto.setDatosBase64(Base64.getEncoder().encodeToString(entidad.getDatos()));
            return dto;
        }catch(Exception e){
            return null;
        }
    }
}

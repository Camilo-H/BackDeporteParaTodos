package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.comunes.ImagenDTO;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ArchivoNoConvertibleExcepcion;

public class MapperImagen {

    public static ImagenEntidad imagenToentidad(Imagen imagen){
        ImagenEntidad entidad = new ImagenEntidad();
        entidad.setNombre(imagen.getNombre());
        entidad.setLongitud(imagen.getLongitud());
        entidad.setTipoArchivo(imagen.getTipoArchivo());
        entidad.setDatos(imagen.getDatos());
        return entidad;
    }

    public static Imagen entidadToImagen(ImagenEntidad entidad){
        Imagen imagen = new Imagen();
        imagen.setId(entidad.getId());
        imagen.setNombre(entidad.getNombre());
        imagen.setTipoArchivo(entidad.getTipoArchivo());
        imagen.setLongitud(entidad.getLongitud());
        imagen.setDatos(entidad.getDatos());
        return imagen;
    }

    /***
     * Convierte manualmente un objeto multiparfile a tipo ImagenDTO, omitiendo el id en el destino.
     * @param archivo 
     * @return objeto de tipo ImagenDTO con los datos del archivo recibido.
     */
    public static ImagenDTO multiparfileToImagenDTO(MultipartFile archivo){
        //validacion de contenido
        if(
            archivo==null || 
            archivo.isEmpty() || 
            archivo.getSize()==0){
            throw new ArchivoNoConvertibleExcepcion("Archivo vacio");
        }
        //validacion de nombre
        if(
            !(
                archivo.getContentType().equals("image/jpeg") ||
                archivo.getContentType().equals("image/png") ||
                archivo.getContentType().equals("image/webp")
            )
        ){
            throw new ArchivoNoConvertibleExcepcion(archivo.getContentType()+ " es un tipo de archivo no permitido, solo se permiten image/jpeg, image/png, image/webp");
        }
        //validacion de nombre
        if(archivo.getOriginalFilename()==null || archivo.getOriginalFilename()==""){
            throw new ArchivoNoConvertibleExcepcion("el archivo no posee un nombre");
        }
        //TODO: validacion de extension, de momento no lo veo necesario pues ya valido el tipo, sin embargo dejo como recordatorio ante algun imprevisto
        ImagenDTO dto = new ImagenDTO();
        dto.setNombre(archivo.getOriginalFilename());
        dto.setTipoArchivo(archivo.getContentType());
        dto.setLongitud(archivo.getSize());
        try{
            dto.setDatos(archivo.getBytes());
        }catch(Exception ex){
            throw new ArchivoNoConvertibleExcepcion("ex.getMessage().toString()");
        }
        return dto;
    }
}

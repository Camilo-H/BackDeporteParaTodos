package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import java.io.IOException;
import java.util.Base64;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;

public final class ImagenMapper {

    private ImagenMapper() {}

    public static Imagen toDominio(ImagenEntidad entidad) {
        if (entidad == null)
            throw new NoProcesableEntidadException("ImagenEntidad no puede ser nula");
        Imagen modelo = new Imagen();
        modelo.setId(entidad.getId());
        modelo.setNombre(entidad.getNombre());
        modelo.setTipoArchivo(entidad.getTipoArchivo());
        modelo.setLongitud(entidad.getLongitud());
        modelo.setDatos(entidad.getDatos());
        return modelo;
    }

    public static ImagenEntidad toEntidad(Imagen modelo) {
        if (modelo == null)
            throw new NoProcesableEntidadException("Imagen no puede ser nula");
        ImagenEntidad entidad = new ImagenEntidad();
        entidad.setId(modelo.getId());
        entidad.setNombre(modelo.getNombre());
        entidad.setTipoArchivo(modelo.getTipoArchivo());
        entidad.setLongitud(modelo.getLongitud());
        entidad.setDatos(modelo.getDatos());
        return entidad;
    }

    public static ImagenDto toDto(Imagen modelo) {
        if (modelo == null)
            throw new NoProcesableEntidadException("Imagen no puede ser nula");
        ImagenDto dto = new ImagenDto();
        dto.setId(modelo.getId());
        dto.setNombre(modelo.getNombre());
        dto.setTipoArchivo(modelo.getTipoArchivo());
        dto.setLongitud(modelo.getLongitud());
        if (modelo.getDatos() != null) {
            dto.setDatosBase64(Base64.getEncoder().encodeToString(modelo.getDatos()));
        }
        return dto;
    }

    public static Imagen fromDto(ImagenDto dto) {
        if (dto == null)
            throw new NoProcesableEntidadException("ImagenDto no puede ser nulo");
        Imagen modelo = new Imagen();
        modelo.setId(dto.getId());
        modelo.setNombre(dto.getNombre());
        modelo.setTipoArchivo(dto.getTipoArchivo());
        try {
            if (dto.getDatosMultipartFile() != null && !dto.getDatosMultipartFile().isEmpty()) {
                modelo.setDatos(dto.getDatosMultipartFile().getBytes());
                modelo.setLongitud(dto.getDatosMultipartFile().getSize());
            } else if (dto.getDatosBase64() != null && !dto.getDatosBase64().isBlank()) {
                byte[] datos = Base64.getDecoder().decode(dto.getDatosBase64());
                modelo.setDatos(datos);
                modelo.setLongitud((long) datos.length);
            }
        } catch (IOException e) {
            throw new NoProcesableEntidadException("No se pudo leer el archivo multipart: " + e.getMessage());
        }
        return modelo;
    }
}

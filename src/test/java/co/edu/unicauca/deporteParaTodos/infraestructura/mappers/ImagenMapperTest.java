package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoProcesableEntidadException;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Imagen;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ImagenDto;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ImagenEntidad;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class ImagenMapperTest {

    private static final Integer ID = 1;
    private static final String NOMBRE = "foto.jpg";
    private static final String TIPO = "image/jpeg";
    private static final byte[] DATOS = {10, 20, 30};

    private ImagenEntidad entidadBase() {
        ImagenEntidad e = new ImagenEntidad();
        e.setId(ID);
        e.setNombre(NOMBRE);
        e.setTipoArchivo(TIPO);
        e.setLongitud((long) DATOS.length);
        e.setDatos(DATOS);
        return e;
    }

    private Imagen modeloBase() {
        return new Imagen(ID, NOMBRE, TIPO, (long) DATOS.length, DATOS);
    }

    // ── toDominio ─────────────────────────────────────────────────────────────

    @Test
    void toDominio_entidadValida_mapeaTodosLosCampos() {
        Imagen resultado = ImagenMapper.toDominio(entidadBase());

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals(NOMBRE, resultado.getNombre());
        assertEquals(TIPO, resultado.getTipoArchivo());
        assertEquals((long) DATOS.length, resultado.getLongitud());
        assertArrayEquals(DATOS, resultado.getDatos());
    }

    @Test
    void toDominio_entidadNula_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> ImagenMapper.toDominio(null));
    }

    // ── toEntidad ─────────────────────────────────────────────────────────────

    @Test
    void toEntidad_modeloValido_mapeaTodosLosCamposIncluyendoId() {
        ImagenEntidad resultado = ImagenMapper.toEntidad(modeloBase());

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals(NOMBRE, resultado.getNombre());
        assertEquals(TIPO, resultado.getTipoArchivo());
        assertEquals((long) DATOS.length, resultado.getLongitud());
        assertArrayEquals(DATOS, resultado.getDatos());
    }

    @Test
    void toEntidad_modeloNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> ImagenMapper.toEntidad(null));
    }

    // ── toDto ─────────────────────────────────────────────────────────────────

    @Test
    void toDto_modeloConDatos_convierteABase64() {
        ImagenDto resultado = ImagenMapper.toDto(modeloBase());

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals(NOMBRE, resultado.getNombre());
        assertEquals(TIPO, resultado.getTipoArchivo());
        String base64Esperado = Base64.getEncoder().encodeToString(DATOS);
        assertEquals(base64Esperado, resultado.getDatosBase64());
    }

    @Test
    void toDto_datosNulos_datosBase64Nulo() {
        Imagen modelo = new Imagen(ID, NOMBRE, TIPO, 0L, null);
        ImagenDto resultado = ImagenMapper.toDto(modelo);

        assertNotNull(resultado);
        assertNull(resultado.getDatosBase64());
    }

    @Test
    void toDto_modeloNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> ImagenMapper.toDto(null));
    }

    // ── fromDto ───────────────────────────────────────────────────────────────

    @Test
    void fromDto_conMultipartFile_leeBytesYLongitud() throws Exception {
        MockMultipartFile archivo = new MockMultipartFile(
                "datosMultipartFile", NOMBRE, TIPO, DATOS);
        ImagenDto dto = new ImagenDto();
        dto.setId(ID);
        dto.setNombre(NOMBRE);
        dto.setTipoArchivo(TIPO);
        dto.setDatosMultipartFile(archivo);

        Imagen resultado = ImagenMapper.fromDto(dto);

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertArrayEquals(DATOS, resultado.getDatos());
        assertEquals((long) DATOS.length, resultado.getLongitud());
    }

    @Test
    void fromDto_conBase64_decodificaCorrectamente() {
        String base64 = Base64.getEncoder().encodeToString(DATOS);
        ImagenDto dto = new ImagenDto();
        dto.setId(ID);
        dto.setNombre(NOMBRE);
        dto.setDatosBase64(base64);

        Imagen resultado = ImagenMapper.fromDto(dto);

        assertNotNull(resultado);
        assertArrayEquals(DATOS, resultado.getDatos());
        assertEquals((long) DATOS.length, resultado.getLongitud());
    }

    @Test
    void fromDto_sinDatos_datosYLongitudNulos() {
        ImagenDto dto = new ImagenDto();
        dto.setId(ID);
        dto.setNombre(NOMBRE);

        Imagen resultado = ImagenMapper.fromDto(dto);

        assertNotNull(resultado);
        assertNull(resultado.getDatos());
        assertNull(resultado.getLongitud());
    }

    @Test
    void fromDto_dtoNulo_lanzaNoProcesableEntidadException() {
        assertThrows(NoProcesableEntidadException.class, () -> ImagenMapper.fromDto(null));
    }

    @Test
    void fromDto_base64_longitudCalculadaCorrectamente() {
        byte[] datosGrandes = new byte[100];
        String base64 = Base64.getEncoder().encodeToString(datosGrandes);
        ImagenDto dto = new ImagenDto();
        dto.setDatosBase64(base64);

        Imagen resultado = ImagenMapper.fromDto(dto);

        assertEquals(100L, resultado.getLongitud());
    }
}

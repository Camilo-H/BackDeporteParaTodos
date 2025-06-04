package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CodigoError {

        ERROR_GENERICO("GC-0001", "ERROR GENERICO"),
        LISTADO_VACIO("GC-0002", "El listado a retornar se encuentra vacio"),
        NO_EXISTE("GC-0003","No se encuentra registrado el elemento"),
        ARCHIVO_NO_CONVERTIBLE("GC-0004","El archivo no puede ser transformado"),
        INSERCION_FALLIDA("GC-0005", "El elemento no pudo ser insertado en la base de datos"),
        NO_IMPLEMENTADO("GC-0006", "El recurso no ha sido implementado aun"),
        YA_EXISTE("GC-0007", "Ya existe es mismo registro en el sistema"),
        NO_CONVERTIBLE("GC-0008", "Datos no son compatibles, no pueden ser transformados"),
        ERROR_INTERNO("GC-0009", "No se ha logrado completar la peticion"),
        DEPENDICEA_FALLIDA("GC-0010", "El recurso necesita de una dependencia que no se encuentra en el sistema"),
        ;

        private final String codigo;
        private final String llaveMensaje;
}
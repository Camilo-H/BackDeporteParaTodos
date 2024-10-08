package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CodigoError {

        ERROR_GENERICO("GC-0001", "ERROR GENERICO"),
        LISTADO_VACIO("GC-0002", "El listado a retornar se encuentra vacio"),
        NO_EXISTE("GC-0003","No se encuentra registrado el elemento"),
        ARCHIVO_NO_CONVERTIBLE("GC-0004","El archivo no puede ser transformado");
        
        private final String codigo;
        private final String llaveMensaje;
}
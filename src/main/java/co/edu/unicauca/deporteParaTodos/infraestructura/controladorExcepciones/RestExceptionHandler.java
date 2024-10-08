package co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ArchivoNoConvertibleExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.CodigoError;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.ErrorUtils;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.formatoError.Error;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

        /***
         * Captura las excepciones generadas por los argumentos en los endpoint al no coincidir con los constraint establecidos.
         * Los constraint se encuentran denotados en los tipos de datos DTO, mediante jakarta validation
         * Para su uso en los endpoints debe establecese la notacion @Validated a nivel de clase y la notacion @Valid a nivel de metodo
         * @param ex excepcion a capturar
         * @return response entity con la lista de errores o reglas infringidas en los argumentos
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
                System.out.println("Retornando respuesta con los errores identificados");
                Map<String, String> errores = new HashMap<>();
                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String campo = ((FieldError) error).getField();
                        String mensajeDeError = error.getDefaultMessage();
                        errores.put(campo, mensajeDeError);
                });

                return new ResponseEntity<Map<String, String>>(errores, HttpStatus.BAD_REQUEST);
        }

        /**
         * Captura exception cuando no se encuentran elementos en un listado
         * para su uso lance una exception de tipo ListadoVacioException
         * @param req
         * @param ex excepcion a capturar
         * @return
         */
        @ExceptionHandler(ListadoVacioExcepcion.class)
        @ResponseBody
        public ResponseEntity<Error> GenericException(final HttpServletRequest req,final ListadoVacioExcepcion ex) {
                final Error error = ErrorUtils.crearError(
                        CodigoError.LISTADO_VACIO.getCodigo(), 
                        String.format("%s, %s", CodigoError.LISTADO_VACIO.getLlaveMensaje(),ex.getMessage()), 
                        HttpStatus.OK.value())
                        .setUrl(req.getRequestURL().toString())
                        .setMetodo(req.getMethod());

                return new ResponseEntity<Error>(error, HttpStatus.OK);
        }

        @ExceptionHandler(NoExisteExcepcion.class)
        public ResponseEntity<Error> GenericException(final HttpServletRequest req, final NoExisteExcepcion ex){
                final Error error = ErrorUtils.crearError(
                        ex.getCodigo(),
                        ex.getLlaveMensaje(),
                        HttpStatus.NOT_FOUND.value())
                        .setUrl(req.getRequestURL().toString())
                        .setMetodo(req.getMethod());
                return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
        }

        /**
         * Captura excepcion de tipo Archivo no convertible
         * @param req
         * @param ex excepcion a capturar
         * @return ResponseEntity con reporte de error generado.
         */
        @ExceptionHandler(ArchivoNoConvertibleExcepcion.class)
        public ResponseEntity<Error> GenericException(final HttpServletRequest req, final ArchivoNoConvertibleExcepcion ex){
                final Error error = ErrorUtils.crearError(
                        ex.getCodigo(),
                        String.format("%s, %s", ex.getLlaveMensaje(), ex.getMessage()),
                        HttpStatus.BAD_REQUEST.value())
                        .setUrl(req.getRequestURL().toString())
                        .setMetodo(req.getMethod());
                return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
        }

}

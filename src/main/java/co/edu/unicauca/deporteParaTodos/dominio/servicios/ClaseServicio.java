package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IClaseServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IClaseGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Clase;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.ClaseDto;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ErrorInternoException;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.NoExisteExcepcion;

@Service
public class ClaseServicio implements IClaseServicio {

    @Autowired
    private IClaseGateway claseGateway;

    @Override
    public List<ClaseDto> obtenerClasesGrupo(String categoria, String curso, Integer anio, Integer iterable) {
        List<Clase> listado = claseGateway.obtenerClasesGrupo(categoria, curso, anio, iterable);
        if (listado.isEmpty()) {
            throw new ListadoVacioExcepcion("No existen registros");
        }
        List<ClaseDto> listaDtos = new ArrayList<>();
        listado.forEach(modelo -> {
            ClaseDto dto = ClaseDto.fabricarDeModelo(modelo);
            listaDtos.add(dto);
        });
        return listaDtos;
    }

    @Override
    public ClaseDto insertarClase(ClaseDto datoClase) {
        Clase modelo = Clase.fabricarDeDto(datoClase);
        if (modelo == null) {
            throw new ErrorInternoException();
        }
        Clase claseInsertada = claseGateway.insertarClase(modelo);
        ClaseDto respuesta = ClaseDto.fabricarDeModelo(claseInsertada);
        if (respuesta == null) {
            throw new ErrorInternoException();
        }
        return respuesta;
    }

    @Override
    public ClaseDto eliminarClase(int id) {
        if (!claseGateway.existeClase(id)) {
            throw new NoExisteExcepcion();
        }
        Clase claseEliminada = claseGateway.eliminarClase(id);
        ClaseDto respuesta = ClaseDto.fabricarDeModelo(claseEliminada);
        if (respuesta == null) {
            throw new ErrorInternoException();
        }
        return respuesta;
    }

}

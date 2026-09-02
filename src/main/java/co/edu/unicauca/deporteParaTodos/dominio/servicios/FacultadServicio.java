package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IFacultadServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IFacultadGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Facultad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.FacultadDto;
import co.edu.unicauca.deporteParaTodos.dominio.excepciones.ListadoVacioExcepcion;

@Service
public class FacultadServicio implements IFacultadServicio {

    @Autowired
    private IFacultadGateway facultadGateway;

    @Override
    public List<FacultadDto> obtenerFacultades() {
        List<Facultad> facultades = facultadGateway.obtenerFacultades();
        if (facultades.isEmpty()) {
            throw new ListadoVacioExcepcion("No hay facultades registradas");
        }
        List<FacultadDto> listaDtos = new ArrayList<>();
        facultades.forEach(modelo -> {
            FacultadDto dto = FacultadDto.fabricarDeModelo(modelo);
            listaDtos.add(dto);
        });
        return listaDtos;
    }

}

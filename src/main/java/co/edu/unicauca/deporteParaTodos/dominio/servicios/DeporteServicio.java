package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IDeporteServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.baseDatos.IDeporteRepositorio;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.DeporteEntidad;

@Service
public class DeporteServicio implements IDeporteServicio{
    
    @Autowired
    private IDeporteRepositorio repoDeporte;

    @Override
    public Iterable<DeporteEntidad> obtenerDeportes() {
        return repoDeporte.findAll();
    }
}

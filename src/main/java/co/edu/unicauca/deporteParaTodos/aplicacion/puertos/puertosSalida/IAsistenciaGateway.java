package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;

public interface IAsistenciaGateway {

    public boolean existeAsistencia(String perfId, int clsId);
    
    public List<Asistencia> obtenerAsistencias();

    public List<Asistencia> obtenerAtencionesPorClase(Integer claseId);

    public Optional<Asistencia> obtenerAsistencia(String perfId, int clsId);

    public Asistencia InsertarAsistencia(Asistencia datosAsistencia);

    public Asistencia eliminarAsitencia(String perfId, int clsId);

}

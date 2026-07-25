package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTOs.AtencionDto;

public interface IAsistenciaServicio {

    public List<Asistencia> obtenerAsistencias();

    public List<AtencionDto> obtenerAtencionesPorClase(Integer claseId);

    public void registrarAtencionesPorClase(List<AtencionDto> atenciones, Integer claseId);

    public Asistencia obtenerAsistencia(String perfId, int clsId);

    public Asistencia InsertarAsistencia(Asistencia datosAsistencia);

    public Asistencia eliminarAsitencia(String perfId, int clsId);

    public Asistencia eliminarAsistencia(String perfId, Long clsCodigo);
}

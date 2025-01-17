package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;

public interface IAsistenciaServicio {

    public List<Asistencia> obtenerAsistencias();

    public Asistencia obtenerAsistencia(String perfId, int clsId);

    public Asistencia InsertarAsistencia(Asistencia datosAsistencia);

    public Asistencia eliminarAsitencia(String perfId, int clsId);
}

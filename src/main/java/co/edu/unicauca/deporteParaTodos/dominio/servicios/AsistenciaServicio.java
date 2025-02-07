package co.edu.unicauca.deporteParaTodos.dominio.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada.IAsistenciaServicio;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAsistenciaGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.ListadoVacioExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.YaExisteElementoExcepcion;

@Service
public class AsistenciaServicio implements IAsistenciaServicio {

    @Autowired
    private IAsistenciaGateway asistenciaGateway;

    @Override
    public List<Asistencia> obtenerAsistencias() {
        List<Asistencia> asistencias = asistenciaGateway.obtenerAsistencias();
        if (asistencias.isEmpty()) {
            throw new ListadoVacioExcepcion("No hay registro de asistencias");
        }
        return asistencias;
    }

    @Override
    public Asistencia obtenerAsistencia(String perfId, int clsId) {
        return asistenciaGateway.obtenerAsistencia(perfId, clsId).orElseThrow(() -> new NoExisteExcepcion());

    }

    @Override
    public Asistencia InsertarAsistencia(Asistencia datosAsistencia) {
        if (asistenciaGateway.existeAsistencia(datosAsistencia.getIdPerfil(), datosAsistencia.getClsCodigo())) {
            throw new YaExisteElementoExcepcion("Ya existe el elemento");
        }
        return asistenciaGateway.InsertarAsistencia(datosAsistencia);
    }

    @Override
    public Asistencia eliminarAsitencia(String perfId, int clsId) {
        if (asistenciaGateway.existeAsistencia(perfId, clsId)) {
            return asistenciaGateway.eliminarAsitencia(perfId, clsId);
        }
        throw new NoExisteExcepcion();
    }

}

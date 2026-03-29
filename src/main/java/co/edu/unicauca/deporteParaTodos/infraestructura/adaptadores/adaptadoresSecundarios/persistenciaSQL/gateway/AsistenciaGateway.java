package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IAsistenciaGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Asistencia;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.AsistenciaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ids.AsistenciaId;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IAsistenciaRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class AsistenciaGateway implements IAsistenciaGateway {

    @Autowired
    private IAsistenciaRepositorio repoAsistencia;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existeAsistencia(String perfId, int clsId) {
        AsistenciaId idAsistencia = new AsistenciaId();
        idAsistencia.setPerfilId(perfId);
        idAsistencia.setClaseCodigo(clsId);
        return repoAsistencia.existsById(idAsistencia);
    }

    @Override
    public List<Asistencia> obtenerAsistencias() {
        return null;
    }

    @Override
    public List<Asistencia> obtenerAtencionesPorClase(Integer claseId) {
        List<AsistenciaEntidad> entidades = repoAsistencia.findByClaseCodigo(claseId);
        List<Asistencia> lista = new ArrayList<>();
        entidades.forEach(entidad -> {
            Asistencia modelo = Asistencia.fabricarDeEntidad(entidad);
            if (modelo != null) {
                lista.add(modelo);
            }
        });
        return lista;
    }

    @Override
    public Optional<Asistencia> obtenerAsistencia(String perfId, int clsId) {
        AsistenciaId idAsistencia = new AsistenciaId();
        idAsistencia.setPerfilId(perfId);
        idAsistencia.setClaseCodigo(clsId);
        Optional<AsistenciaEntidad> existente = repoAsistencia.findById(idAsistencia);
        return existente.map(Asistencia::fabricarDeEntidad);
    }

    @Override
    public Asistencia InsertarAsistencia(Asistencia datosAsistencia) {
        AsistenciaEntidad asisEntidad = new AsistenciaEntidad();
        asisEntidad.setPerfilId(datosAsistencia.getIdPerfil());
        asisEntidad.setClaseCodigo(datosAsistencia.getClsCodigo());
        asisEntidad.setEliminado(0);
        AsistenciaEntidad insertada = repoAsistencia.save(asisEntidad);
        return Asistencia.fabricarDeEntidad(insertada);
    }

    @Override
    public Asistencia eliminarAsitencia(String perfId, int clsId) {
        AsistenciaId idAsistencia = new AsistenciaId();
        idAsistencia.setPerfilId(perfId);
        idAsistencia.setClaseCodigo(clsId);
        Optional<AsistenciaEntidad> asisExistente = repoAsistencia.findById(idAsistencia);
        if (asisExistente.isPresent()) {
            AsistenciaEntidad entidad = asisExistente.get();
            repoAsistencia.delete(entidad);
            return Asistencia.fabricarDeEntidad(entidad);
        }
        throw new NoExisteExcepcion();
    }

}

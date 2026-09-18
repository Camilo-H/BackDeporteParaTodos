package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IDeporteGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.DeporteEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IDeporteRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.mappers.DeporteMapper;

@Service
public class DeporteGateway implements IDeporteGateway {

    private final IDeporteRepositorio repoDeporte;

    public DeporteGateway(IDeporteRepositorio repoDeporte) {
        this.repoDeporte = repoDeporte;
    }

    @Override
    public List<Deporte> listaDeportes() {
        List<Deporte> listaDeportes = new ArrayList<>();
        repoDeporte.findAll().forEach(entidad -> listaDeportes.add(DeporteMapper.toDominio(entidad)));
        return listaDeportes;
    }

    @Override
    public boolean existeDeporte(String nombreDeporte) {
        return repoDeporte.existsById(nombreDeporte);
    }

    @Override
    public Deporte insertarDeporte(Deporte datosDeporte) {
        DeporteEntidad entidad = DeporteMapper.toEntidad(datosDeporte);
        DeporteEntidad entidadGuardada = repoDeporte.save(entidad);
        return DeporteMapper.toDominio(entidadGuardada);
    }

    @Override
    public Deporte obtenerDeportePorId(String nombreDeporte) {
        Optional<DeporteEntidad> entidadOpt = repoDeporte.findById(nombreDeporte);
        if (entidadOpt.isPresent()) {
            return DeporteMapper.toDominio(entidadOpt.get());
        }
        return null;
    }

    @Override
    public Deporte actualizarDeporte(Deporte datosDeporte) {
        if (!repoDeporte.existsById(datosDeporte.getNombre())) {
            return null;
        }
        DeporteEntidad entidad = DeporteMapper.toEntidad(datosDeporte);
        DeporteEntidad entidadActualizada = repoDeporte.save(entidad);
        return DeporteMapper.toDominio(entidadActualizada);
    }

    @Override
    public Deporte eliminarDeporte(String nombreDeporte) {
        Optional<DeporteEntidad> entidadOpt = repoDeporte.findById(nombreDeporte);
        if (entidadOpt.isPresent()) {
            Deporte deporte = DeporteMapper.toDominio(entidadOpt.get());
            repoDeporte.deleteById(nombreDeporte);
            return deporte;
        }
        return null;
    }
}

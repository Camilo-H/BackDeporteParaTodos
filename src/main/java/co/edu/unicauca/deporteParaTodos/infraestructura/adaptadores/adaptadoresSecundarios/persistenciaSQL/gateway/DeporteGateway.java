package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IDeporteGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Deporte;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.DeporteEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IDeporteRepositorio;

@Service
public class DeporteGateway implements IDeporteGateway {

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IDeporteRepositorio repoDeporte;

    @Override
    public List<Deporte> listaDeportes() {
        // TODO Auto-generated method stub
        Iterable<DeporteEntidad> deporte = repoDeporte.findAll();
        List<Deporte> listaDeportes = new ArrayList<>();
        listaDeportes = mapper.map(deporte, new TypeToken<List<Deporte>>(){}.getType());
        return listaDeportes;
    }

    @Override
    public boolean existeDeporte(String nombreDeporte) {
        // TODO Auto-generated method stub
        return repoDeporte.existsById(nombreDeporte);
    }

    @Override
    public Deporte insertarDeporte(Deporte datosDeporte) {
        // TODO Auto-generated method stub
        DeporteEntidad entidad = mapper.map(datosDeporte, DeporteEntidad.class);
        DeporteEntidad entidadGuardada = repoDeporte.save(entidad);
        return mapper.map(entidadGuardada, Deporte.class);
    }

    @Override
    public Deporte obtenerDeportePorId(String nombreDeporte) {
        // TODO Auto-generated method stub
        Optional<DeporteEntidad> entidadOpt = repoDeporte.findById(nombreDeporte);
        if (entidadOpt.isPresent()) {
            return mapper.map(entidadOpt.get(), Deporte.class);
        }
        return null;
    }

    @Override
    public Deporte actualizarDeporte(Deporte datosDeporte) {
        // TODO Auto-generated method stub
        if (!repoDeporte.existsById(datosDeporte.getNombre())) {
            return null;
        }
        DeporteEntidad entidad = mapper.map(datosDeporte, DeporteEntidad.class);
        DeporteEntidad entidadActualizada = repoDeporte.save(entidad);
        return mapper.map(entidadActualizada, Deporte.class);
    }

    @Override
    public Deporte eliminarDeporte(String nombreDeporte) {
        // TODO Auto-generated method stub
        Optional<DeporteEntidad> entidadOpt = repoDeporte.findById(nombreDeporte);
        if (entidadOpt.isPresent()) {
            Deporte deporte = mapper.map(entidadOpt.get(), Deporte.class);
            repoDeporte.deleteById(nombreDeporte);
            return deporte;
        }
        return null;
    }
    
}

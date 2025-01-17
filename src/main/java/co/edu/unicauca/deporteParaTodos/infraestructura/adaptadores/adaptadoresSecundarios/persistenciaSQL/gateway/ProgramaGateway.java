package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida.IProgramaGateway;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Programa;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades.ProgramaEntidad;
import co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.repositorios.IProgramaRepositorio;
import co.edu.unicauca.deporteParaTodos.infraestructura.controladorExcepciones.excepciones.NoExisteExcepcion;

@Service
public class ProgramaGateway implements IProgramaGateway {

    @Autowired
    private IProgramaRepositorio repoPrograma;

    @Qualifier("modelMapperGenerico")
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean existePrograma(String nombrePrograma) {
        return repoPrograma.existsById(nombrePrograma);
    }

    @Override
    public List<Programa> obtenerProgramas() {
        Iterable<ProgramaEntidad> entidades = repoPrograma.findAll();
        List<Programa> programas = new ArrayList<>();
        programas = mapper.map(entidades, new TypeToken<List<Programa>>() {
        }.getType());
        return programas;
    }

    @Override
    public Optional<Programa> obtenerPrograma(String nombrePrograma) {
        Optional<ProgramaEntidad> programaExistente = repoPrograma.findById(nombrePrograma);
        return programaExistente.map(programaEntidad -> mapper.map(programaEntidad, Programa.class));
    }

    @Override
    public Programa insertarPrograma(Programa datosPrograma) {
        ProgramaEntidad entidad = mapper.map(datosPrograma, ProgramaEntidad.class);
        ProgramaEntidad insertado = repoPrograma.save(entidad);
        return mapper.map(insertado, Programa.class);
    }

    @Override
    public Programa actualizarPrograma(Programa datosPrograma) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarPrograma'");
    }

    @Override
    public Programa eliminarPrograma(String nombrePrograma) {
        Optional<ProgramaEntidad> entidadExistente = repoPrograma.findById(nombrePrograma);
        if (entidadExistente != null) {
            ProgramaEntidad entidad = entidadExistente.get();
            repoPrograma.delete(entidad);
            return mapper.map(entidad, Programa.class);
        }
        throw new NoExisteExcepcion();
    }

}

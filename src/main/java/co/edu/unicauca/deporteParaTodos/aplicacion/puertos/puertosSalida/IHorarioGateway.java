package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosSalida;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;

public interface IHorarioGateway {
    
    public boolean existeHorario(int id);
    
    public List<Horario> obtenerHorarios();

    public Optional<Horario> obtenerHorario (int id);

    public Horario insertatarHorario(Horario datosHorario);

    public Horario actualizarHorario(int id, Horario datosHorario); 

    public Horario eliminarHorario (int id);
}

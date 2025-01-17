package co.edu.unicauca.deporteParaTodos.aplicacion.puertos.puertosEntrada;

import java.util.List;
import co.edu.unicauca.deporteParaTodos.dominio.modelo.Horario;

public interface IHorarioServicio {

    public List<Horario> obtenerHorarios();

    public Horario obtenerHorario (int id);

    public Horario insertatarHoratio(Horario datosHorario);

    public Horario actualizarHorario(int id, Horario datosHorario); 

    public Horario eliminarHorario (int id);
}

package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.v2DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class V2Estadistica {
    ///titulo de la estadistica
    private String leyenda1;
    private String leyenda2;
    private String leyenda3;
    private String leyenda4;
    ///cantidad de clases
    private double clases;
    ///cantidad de horas
    private double horas;
    ///minutos excedentes de horas
    private double minutos;
    ///duracion total en minutos
    private double duracion;

    public static V2Estadistica fromObjectCategorias(Object[] objects){
        V2Estadistica estadistica = new V2Estadistica();
        try{
            estadistica.setLeyenda1(objects[0].toString());
            estadistica.setClases(Double.parseDouble(objects[1].toString()));
            estadistica.setHoras(Double.parseDouble(objects[2].toString()));
            estadistica.setMinutos(Double.parseDouble(objects[3].toString()));
            estadistica.setDuracion(Double.parseDouble(objects[4].toString()));
            return estadistica;
        }catch(Exception e){
            return null;
        }
    }

    public static V2Estadistica fromObjectCursos(Object[] objects){
        V2Estadistica estadistica = new V2Estadistica();
        try{
            estadistica.setLeyenda1(objects[0].toString());
            estadistica.setLeyenda2(objects[1].toString());
            estadistica.setClases(Double.parseDouble(objects[2].toString()));
            estadistica.setHoras(Double.parseDouble(objects[3].toString()));
            estadistica.setMinutos(Double.parseDouble(objects[4].toString()));
            estadistica.setDuracion(Double.parseDouble(objects[5].toString()));
            return estadistica;
        }catch(Exception e){
            return null;
        }
    }

    public static V2Estadistica fromObjectGrupos(Object[] objects){
        V2Estadistica estadistica = new V2Estadistica();
        try{
            estadistica.setLeyenda1(objects[0].toString());
            estadistica.setLeyenda2(objects[1].toString());
            estadistica.setLeyenda3(objects[2].toString());
            estadistica.setLeyenda4(objects[3].toString());
            estadistica.setClases(Double.parseDouble(objects[4].toString()));
            estadistica.setHoras(Double.parseDouble(objects[5].toString()));
            estadistica.setMinutos(Double.parseDouble(objects[6].toString()));
            estadistica.setDuracion(Double.parseDouble(objects[7].toString()));
            return estadistica;
        }catch(Exception e){
            return null;
        }
    }
}

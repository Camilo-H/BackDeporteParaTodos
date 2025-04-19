package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresPrimarios.adaptadorRest.DTO.dtoProcedimientos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class alumnoDTO {
    private Integer eliminadoestado;
    private String id;
    private String codigo; 
    private String tipo;
    private String nombre;
    private String correo;
    private String sexo;
    private String tipoid;
    private Integer imagen;
   
    public alumnoDTO(Integer eliminadoestado, String id, String codigo, String tipo,
                     String nombre, String correo, String sexo, String tipoid, Integer imagen) {
        this.eliminadoestado = eliminadoestado;
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.correo = correo;
        this.sexo = sexo;
        this.tipoid = tipoid;
        this.imagen = imagen;
                     }

    //Metodo fabrica estatica
    public static alumnoDTO fromObjectSQL(Object[] objects){
        alumnoDTO alumno = new alumnoDTO();
            try{
                alumno.setEliminadoestado(Integer.parseInt(objects[0].toString()));
                alumno.setId(objects[1].toString());
                alumno.setCodigo(objects[2].toString());
                alumno.setTipo(objects[3].toString());
                alumno.setNombre(objects[4].toString());
                alumno.setCorreo(objects[5].toString());
                alumno.setSexo(objects[6].toString());
                alumno.setTipoid(objects[7].toString());
                if(objects[8]!=null){
                    alumno.setImagen(Integer.parseInt(objects[8].toString()));
                }
                return alumno;
            }catch (Exception e){
                return null;
            }
    }
}

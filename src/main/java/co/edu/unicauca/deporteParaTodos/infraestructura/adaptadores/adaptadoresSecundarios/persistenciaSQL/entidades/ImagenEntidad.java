package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name="TBL_IMAGEN")
public class ImagenEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidad_imagen")
    @SequenceGenerator(name = "seq_entidad_imagen", sequenceName = "SEQ_ID_IMAGEN", allocationSize = 1)
    @Column(name="IMG_id")
    private Integer id;

    @Column(name="IMG_NOMBRE")
    private String nombre;

    @Column(name="IMG_TIPO_ARCHIVO")
    private String tipoArchivo;

    @Column(name="IMG_LONGITUD")
    private Long longitud;

    @Column(name="IMG_DATOS")
    private byte[] datos;

    //Este lado de la relacion no tiene la columna foranea, esta entidad es foranea
    //por tanto se llama al otro extremo de la relacion, con el nombre del objeto "objImagen"
    @OneToOne(mappedBy = "objImagen")
    private CursoEntidad curso;
}

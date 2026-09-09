package co.edu.unicauca.deporteParaTodos.infraestructura.adaptadores.adaptadoresSecundarios.persistenciaSQL.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "TBL_IMAGEN")
public class ImagenEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMG_ID")
    private Integer id;

    @Column(name = "IMG_NOMBRE")
    private String nombre;

    @Column(name = "IMG_TIPO_ARCHIVO")
    private String tipoArchivo;

    @Column(name = "IMG_LONGITUD")
    private Long longitud;

    @Column(name = "IMG_DATOS")
    @Lob
    private byte[] datos;

    @Column(name = "META_ELIMINADO")
    private int eliminado;

}

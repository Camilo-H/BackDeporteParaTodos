package co.edu.unicauca.proyecto.models;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor

@Entity
@Table (name = "tbl_BandejaEntrada")
public class BandejaEntradaEntity {
    @Id
    @Column(name = "bd_id")
    private long bd_id;

}

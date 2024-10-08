package co.edu.unicauca.deporteParaTodos.infraestructura.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * Definir en la presente configuracion los mappers a travez de modelMapper
 */
@Configuration
public class Mapper {

    // agregue un bean para usar un mapeo personalizado
    // para especificar el bean a usar en el destino, agrege la notacion Qualifier donde el argumento es el valor de la propiedad name del bean

    //bean de proposito general, uso en mapeos donde tipos de datos y estructura sean directamente coincidentes
    @Bean(name = "modelMapperGenerico")
    public ModelMapper mapperGenerico() {
        return new ModelMapper();
    }
}

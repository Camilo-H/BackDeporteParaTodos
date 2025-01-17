package co.edu.unicauca.AccesConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir todas las rutas del backend
        registry.addMapping("/**")
                .allowedOrigins("*") // Cambia esto por la URL de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permitir todas las cabeceras
                .allowCredentials(true); // Si estás usando autenticación con cookies o tokens
    }
}

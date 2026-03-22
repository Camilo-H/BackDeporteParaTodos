package co.edu.unicauca.deporteParaTodos.infraestructura.logs;

import org.slf4j.Logger;

public final class PeticionLogger {

    private PeticionLogger() {
    }

    public static void log(Logger logger, String metodo, String direccion, Object datosEntrantes) {
        logger.info(
                "\n=============================\nPeticion: {}\nDireccion: {}\nDatos entrantes: {}\n=============================",
                metodo,
                direccion,
                datosEntrantes);
    }
}

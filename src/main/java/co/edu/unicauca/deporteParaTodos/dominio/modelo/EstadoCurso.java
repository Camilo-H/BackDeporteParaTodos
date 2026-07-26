package co.edu.unicauca.deporteParaTodos.dominio.modelo;

public enum EstadoCurso {
    ACTIVO,
    INACTIVO,
    // TODO [DEUDA TÉCNICA]: CERRADO es código muerto. fabricarDeEntidad solo produce
    // ACTIVO o INACTIVO (mapea eliminado=0/1); nunca se persiste en BD; y si se envía
    // como prmEstado en PATCH /api/v2/curso/estado, CursoGateway.cambiarEstadoCurso lo
    // trata como "no es INACTIVO" → setEliminado(0), rehabilitando el curso silenciosamente.
    // Corregir en sprint de deuda técnica: eliminar el valor o añadir mapeo correcto.
    CERRADO
}

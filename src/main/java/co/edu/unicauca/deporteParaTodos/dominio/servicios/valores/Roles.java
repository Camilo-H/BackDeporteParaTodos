package co.edu.unicauca.deporteParaTodos.dominio.servicios.valores;

/***
 * proporciona el estandar para cumplir con restricciones sobre el rol en la base de datos
 */
public enum Roles {
    ADMINISTRADOR("Coordinador"),
    ALUMNO("Alumno"),
    INSTRUCTOR("Instructor");

    private final String valor;

    Roles(String valor) {
        this.valor = valor;
    }

    public String getValor(){
        return valor;
    }
}

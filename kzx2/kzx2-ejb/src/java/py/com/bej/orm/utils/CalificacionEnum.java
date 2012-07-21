/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

/**
 *
 * @author Diego_M
 */
public enum CalificacionEnum {

    JUDICIALIZADO(0, "Judicializado"),
    INSUFICIENTE(1, "Insuficiente"),
    ACEPTABLE(2, "Aceptable"),
    BUENO(3, "Bueno"),
    MUY_BUENO(4, "Muy Bueno"),
    EXCELENTE(5, "Excelente");
    private int valor;
    private String descripcion;

    private CalificacionEnum(int valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}

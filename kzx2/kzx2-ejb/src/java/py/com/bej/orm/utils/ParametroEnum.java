/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

/**
 *
 * @author Diego_M
 */
public enum ParametroEnum {

    AUMENTO1(1.21, "Aumento para el precio base"),
    AUMENTO2(1.18, "Aumento para el precio de costo"),
    VARIABLE(50000.00, "Variable de aumento por insumos");
    private Double valor;
    private String descripcion;

    private ParametroEnum(Double valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    /**
     * @return the valor
     */
    public Double getValor() {
        return valor;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}

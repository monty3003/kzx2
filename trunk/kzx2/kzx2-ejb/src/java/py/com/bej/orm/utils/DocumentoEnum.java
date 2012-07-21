/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

/**
 *
 * @author Diego_M
 */
public enum DocumentoEnum {

    TODOS("A", "Valido para todos los documentos"),
    CONTADO("CO", "Valido para venta al contado"),
    CREDITO("CR", "Valido para ventas a credito"),
    CUOTA_CORRIDA("RR", "Valido para ventas a credito en cuotas corridas"),
    ENTREGA_INICIAL("EN", "Valido para las ventas a credito con entrega inicial"),
    GARANTE("GT", "Valido para las ventas a credito que tengan garante"),
    PAGARE("PA", "Clausulas para los pagares"),
    RESPONSABILIDAD_CIVIL("RC", "Valido para la responsabilidad civil");
    private String codigo;
    private String descripcion;

    private DocumentoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

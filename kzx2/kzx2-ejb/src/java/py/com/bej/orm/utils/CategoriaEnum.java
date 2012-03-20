/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

/**
 *
 * @author Diego_M
 */
public enum CategoriaEnum {

    PERSONA_DESDE(10, "Cliente Desde"),
    PERSONA_HASTA(29, "Cliente Hasta"),
    CLIENTE_PF(10, "Cliente Persona Fisica"),
    CLIENTE_PJ(15, "Cliente Persona Juridica"),
    PROVEEDOR(20, "Proveedor"),
    COMPRA_DESDE(40, "Compra Desde"),
    COMPRA_HASTA(45, "Compra Hasta"),
    VENTA_DESDE(50, "Venta Desde"),
    VENTA_HASTA(55, "Venta Hasta"),
    COMPRA_MCO(40, "Compra de Moto al Contado"),
    COMPRA_MCR(45, "Compra de Moto a Credito"),
    VENTA_MCO(50, "Venta de Moto al Contado"),
    VENTA_MCR(55, "Venta de Moto a Credito"),
    PLAN_DESDE(60, "Plan Desde"),
    S_FRANCES(60, "Sistema Frances"),
    S_PER(62, "Personalizado"),
    S_ALE(65, "Sistema Aleman"),
    S_ESP(69, "Especial"),
    PLAN_HASTA(69, "Plan Hasta"),
    FACTURA_COMPRA_DESDE(70, "Factura de Compra desde"),
    FACTURA_COMPRA_HASTA(75, "Factura de Compra hasta"),
    FACTURA_VENTA_DESDE(76, "Factura de Venta desde"),
    FACTURA_VENTA_HASTA(79, "Factura de Venta hasta"),
    FACTURA_DESDE(70, "Factura desde"),
    FACTURA_HASTA(70, "Factura hasta"),
    FACTURA_COMPRA_MCO(70, "Factura de Compra de Moto al Contado"),
    FACTURA_COMPRA_MCR(75, "Factura de Compra de Moto a Credito"),
    FACTURA_VENTA_MCO(76, "Factura de Venta de Moto al Contado"),
    FACTURA_VENTA_MCR(79, "Factura de Venta de Moto a Credito"),
    ABIERTO(101, "Abierto"),
    CERRADO(102, "Cerrado");
    private int symbol;
    private String label;

    private CategoriaEnum(int symbol, String label) {
        this.symbol = symbol;
        this.label = label;
    }

    /**
     * @return the symbol
     */
    public int getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
}

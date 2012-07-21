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

    COMPRADO(0, "Comprado"),
    RECIBIDO(1, "Recibido"),
    GUARDADO(2, "Guardado"),
    BLOQUEADO(3, "Bloqueado"),
    DISPONIBLE(4, "Disponible"),
    VENDIDO_SIN_ENTREGAR(5, "Vendido sin entregar"),
    VENDIDO_ENTREGADO(6, "Vendido Entregado"),
    VENDIDO_DEVUELTO(7, "Vendido y devuelto"),
    COMPRADO_DEVUELTO(8, "Comprado y Devuelto"),
    VENDIDO_SIN_DATOS(9, "Vendido sin datos"),
    PERSONA_DESDE(10, "Cliente Desde"),
    PERSONA_HASTA(29, "Cliente Hasta"),
    CLIENTE_PF(10, "Cliente Persona Fisica"),
    CLIENTE_PJ(15, "Cliente Persona Juridica"),
    PROVEEDOR(20, "Proveedor"),
    COMPRA_DESDE(40, "Compra Desde"),
    COMPRA_HASTA(45, "Compra Hasta"),
    VENTA_DESDE(50, "Venta Desde"),
    VENTA_HASTA(59, "Venta Hasta"),
    COMPRA_MCO(40, "Compra de Moto al Contado"),
    COMPRA_MCR(45, "Compra de Moto a Credito"),
    VENTA_MCO(50, "Venta de Moto al Contado"),
    VENTA_MCR_CORRIDAS(56, "Venta de Moto a Credito en Cuotas Corridas"),
    VENTA_MCR_ENTREGA(57, "Venta de Moto a Credito con Entrega Inicial"),
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
    DEVOLUCION_DESDE(80, "Devolucion Desde"),
    DEVOLUCION_HASTA(85, "Devolucion Hasta"),
    DEVOLUCION_SIMPLE(80, "Devolucion Simple"),
    CANJE_DESDE(90, "Canje Desde"),
    CANJE_HASTA(95, "Canje Hasta"),
    CANJE_SIMPLE(90, "Canje Simple"),
    ABIERTO(101, "Abierto"),
    EN_MORA(102, "En Mora"),
    CERRADO(103, "Cerrado"),
    CANCELADO(104, "Cancelado"),
    EN_PROCESO_JUDICIAL(105, "En Proceso Judicial"),
    REFINANCIADO(106, "Refinanciado"),
    PAGO_CUOTA(110, "Pago Cuota"),
    PAGO_PARCIAL_CUOTA(111, "Pago Parcial Cuot."),
    DESCUENTO(112, "Descuento"),
    PAGO_CANCELACION(115, "Pago Cancelacion"),
    PAGO_INTERES_MORATORIO(120, "Interes Moratorio"),
    PAGO_HONORARIOS_PROFESIONALES(121, "Honorarios Prof.");
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

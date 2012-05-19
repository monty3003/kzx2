/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

/**
 *
 * @author Diego_M
 */
public enum ConfiguracionEnum {

    PAG_DESDE("0", "Paginacion desde"),
    PAG_MAX("10", "Paginacion desde"),
    DATE_PATTERN_CORTO("dd/MM/yyyy", "Formato de Fecha corta"),
    DATE_PATTERN_MES("MM/yyyy", "Formato de Fecha solo mes y año"),
    DATETIME_PATTERN("dd/MM/yyyy - HH:mm:s", "Formato de Fecha y Hora"),
    MONEDA_DECIMALES("0", "Lugares decimales de la moneda"),
    INDICE_REDONDEO("-3", "Indice de Redondeo en calculos de precios"),
    MONEDA_PATTERN("#,###,##0", "Formato de moneda local"),
    NUMBER_PATTERN("#,###,##0.00", "Formato de numero en general"),
    PROPIETARIO("1275758", "Documento del propietario del negocio"),
    LIMITE_MINIMO_PAGARE("150000", "Limite minimo del monto por Pagare"),
    LIMITE_MAXIMO_PAGARE("5000000", "Limite maximo del monto por Pagare"),
    DESCUENTO_CANCELACION("0.02", "Porcentaje del Descuento por cuota cancelada");
    private String symbol;
    private String label;

    private ConfiguracionEnum(String symbol, String label) {
        this.symbol = symbol;
        this.label = label;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
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

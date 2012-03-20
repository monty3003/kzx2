/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Diego_M
 */
public class PlanWrapper {

    private Short numeroCuota;
    private List<BigDecimal> listaMontoCuotas;

    public PlanWrapper(Short numeroCuota, List<BigDecimal> listaMontoCuotas) {
        this.numeroCuota = numeroCuota;
        this.listaMontoCuotas = listaMontoCuotas;
    }

    /**
     * @return the numeroCuota
     */
    public Short getNumeroCuota() {
        return numeroCuota;
    }

    /**
     * @param numeroCuota the numeroCuota to set
     */
    public void setNumeroCuota(Short numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    /**
     * @return the listaMontoCuotas
     */
    public List<BigDecimal> getListaMontoCuotas() {
        return listaMontoCuotas;
    }

    /**
     * @param listaMontoCuotas the listaMontoCuotas to set
     */
    public void setListaMontoCuotas(List<BigDecimal> listaMontoCuotas) {
        this.listaMontoCuotas = listaMontoCuotas;
    }
}

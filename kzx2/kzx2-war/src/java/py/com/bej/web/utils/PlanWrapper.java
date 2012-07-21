/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.bej.orm.utils.Conversor;

/**
 *
 * @author Diego_M
 */
public class PlanWrapper {

    private Short numeroCuota;
    private List<BigDecimal> listaMontoCuotas;
    private String cuotaCorrida;
    private String entrega1;
    private String entrega2;
    private String entrega3;
    private String entrega4;
    private String entrega5;
    private String entrega6;
    private String entrega7;
    private String entrega8;
    private String entrega9;
    private String entrega10;
    private String entrega11;
    private String entrega12;
    private String entrega13;
    private String entrega14;
    private String entrega15;
    private String entrega16;
    private String entrega17;
    private String entrega18;
    private String entrega19;
    private String entrega20;

    public PlanWrapper(Short numeroCuota, List<BigDecimal> listaMontoCuotas) {
        this.numeroCuota = numeroCuota;
        this.listaMontoCuotas = listaMontoCuotas;
        try {
            if (listaMontoCuotas.get(0) != null) {
                cuotaCorrida = Conversor.numberToStringPattern(listaMontoCuotas.get(0));
            }
            if (listaMontoCuotas.get(1) != null) {
                entrega1 = Conversor.numberToStringPattern(listaMontoCuotas.get(1));
            }
            if (listaMontoCuotas.get(2) != null) {
                entrega2 = Conversor.numberToStringPattern(listaMontoCuotas.get(2));
            }
            if (listaMontoCuotas.get(3) != null) {
                entrega3 = Conversor.numberToStringPattern(listaMontoCuotas.get(3));
            }
            if (listaMontoCuotas.get(4) != null) {
                entrega4 = Conversor.numberToStringPattern(listaMontoCuotas.get(4));
            }
            if (listaMontoCuotas.get(5) != null) {
                entrega5 = Conversor.numberToStringPattern(listaMontoCuotas.get(5));
            }
            if (listaMontoCuotas.get(6) != null) {
                entrega6 = Conversor.numberToStringPattern(listaMontoCuotas.get(6));
            }
            if (listaMontoCuotas.get(7) != null) {
                entrega7 = Conversor.numberToStringPattern(listaMontoCuotas.get(7));
            }
            if (listaMontoCuotas.get(8) != null) {
                entrega8 = Conversor.numberToStringPattern(listaMontoCuotas.get(8));
            }
            if (listaMontoCuotas.get(9) != null) {
                entrega9 = Conversor.numberToStringPattern(listaMontoCuotas.get(9));
            }
            if (listaMontoCuotas.get(10) != null) {
                entrega10 = Conversor.numberToStringPattern(listaMontoCuotas.get(10));
            }
            if (listaMontoCuotas.get(11) != null) {
                entrega11 = Conversor.numberToStringPattern(listaMontoCuotas.get(11));
            }
            if (listaMontoCuotas.get(12) != null) {
                entrega12 = Conversor.numberToStringPattern(listaMontoCuotas.get(12));
            }
            if (listaMontoCuotas.get(13) != null) {
                entrega13 = Conversor.numberToStringPattern(listaMontoCuotas.get(13));
            }
            if (listaMontoCuotas.get(14) != null) {
                entrega14 = Conversor.numberToStringPattern(listaMontoCuotas.get(14));
            }
            if (listaMontoCuotas.get(15) != null) {
                entrega15 = Conversor.numberToStringPattern(listaMontoCuotas.get(15));
            }
            if (listaMontoCuotas.get(16) != null) {
                entrega16 = Conversor.numberToStringPattern(listaMontoCuotas.get(16));
            }
            if (listaMontoCuotas.get(17) != null) {
                entrega17 = Conversor.numberToStringPattern(listaMontoCuotas.get(17));
            }
            if (listaMontoCuotas.get(18) != null) {
                entrega18 = Conversor.numberToStringPattern(listaMontoCuotas.get(18));
            }
            if (listaMontoCuotas.get(19) != null) {
                entrega19 = Conversor.numberToStringPattern(listaMontoCuotas.get(19));
            }
            if (listaMontoCuotas.get(20) != null) {
                entrega20 = Conversor.numberToStringPattern(listaMontoCuotas.get(20));
            }
        } catch (Exception e) {
            Logger.getLogger(PlanWrapper.class.getName()).log(Level.INFO, "Fin del plan", e);
        }
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

    public String getCuotaCorrida() {
        return cuotaCorrida;
    }

    public void setCuotaCorrida(String cuotaCorrida) {
        this.cuotaCorrida = cuotaCorrida;
    }

    public String getEntrega1() {
        return entrega1;
    }

    public void setEntrega1(String entrega1) {
        this.entrega1 = entrega1;
    }

    public String getEntrega10() {
        return entrega10;
    }

    public void setEntrega10(String entrega10) {
        this.entrega10 = entrega10;
    }

    public String getEntrega11() {
        return entrega11;
    }

    public void setEntrega11(String entrega11) {
        this.entrega11 = entrega11;
    }

    public String getEntrega12() {
        return entrega12;
    }

    public void setEntrega12(String entrega12) {
        this.entrega12 = entrega12;
    }

    public String getEntrega2() {
        return entrega2;
    }

    public void setEntrega2(String entrega2) {
        this.entrega2 = entrega2;
    }

    public String getEntrega3() {
        return entrega3;
    }

    public void setEntrega3(String entrega3) {
        this.entrega3 = entrega3;
    }

    public String getEntrega4() {
        return entrega4;
    }

    public void setEntrega4(String entrega4) {
        this.entrega4 = entrega4;
    }

    public String getEntrega5() {
        return entrega5;
    }

    public void setEntrega5(String entrega5) {
        this.entrega5 = entrega5;
    }

    public String getEntrega6() {
        return entrega6;
    }

    public void setEntrega6(String entrega6) {
        this.entrega6 = entrega6;
    }

    public String getEntrega7() {
        return entrega7;
    }

    public void setEntrega7(String entrega7) {
        this.entrega7 = entrega7;
    }

    public String getEntrega8() {
        return entrega8;
    }

    public void setEntrega8(String entrega8) {
        this.entrega8 = entrega8;
    }

    public String getEntrega9() {
        return entrega9;
    }

    public void setEntrega9(String entrega9) {
        this.entrega9 = entrega9;
    }

    public String getEntrega13() {
        return entrega13;
    }

    public void setEntrega13(String entrega13) {
        this.entrega13 = entrega13;
    }

    public String getEntrega14() {
        return entrega14;
    }

    public void setEntrega14(String entrega14) {
        this.entrega14 = entrega14;
    }

    public String getEntrega15() {
        return entrega15;
    }

    public void setEntrega15(String entrega15) {
        this.entrega15 = entrega15;
    }

    public String getEntrega16() {
        return entrega16;
    }

    public void setEntrega16(String entrega16) {
        this.entrega16 = entrega16;
    }

    public String getEntrega17() {
        return entrega17;
    }

    public void setEntrega17(String entrega17) {
        this.entrega17 = entrega17;
    }

    public String getEntrega18() {
        return entrega18;
    }

    public void setEntrega18(String entrega18) {
        this.entrega18 = entrega18;
    }

    public String getEntrega19() {
        return entrega19;
    }

    public void setEntrega19(String entrega19) {
        this.entrega19 = entrega19;
    }

    public String getEntrega20() {
        return entrega20;
    }

    public void setEntrega20(String entrega20) {
        this.entrega20 = entrega20;
    }

    public List<BigDecimal> getListaMontoCuotas() {
        return listaMontoCuotas;
    }

    public void setListaMontoCuotas(List<BigDecimal> listaMontoCuotas) {
        this.listaMontoCuotas = listaMontoCuotas;
    }
}

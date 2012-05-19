/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

/**
 *
 * @author Diego_M
 */
public class DetalleWrapper {

    private String codigo;
    private String concepto;
    private String debe;
    private String haber;

    public DetalleWrapper() {
    }

    public DetalleWrapper(String codigo, String concepto, String debe, String haber) {
        this.codigo = codigo;
        this.concepto = concepto;
        this.debe = debe;
        this.haber = haber;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the debe
     */
    public String getDebe() {
        return debe;
    }

    /**
     * @param debe the debe to set
     */
    public void setDebe(String debe) {
        this.debe = debe;
    }

    /**
     * @return the haber
     */
    public String getHaber() {
        return haber;
    }

    /**
     * @param haber the haber to set
     */
    public void setHaber(String haber) {
        this.haber = haber;
    }
}

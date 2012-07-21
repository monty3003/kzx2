/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

/**
 *
 * @author Diego_M
 */
public class GrillaCreditoCabeceraWrapper {

    private Integer mes;
    private Integer anho;
    private String etiqueta;
    private String longitud;

    public GrillaCreditoCabeceraWrapper(Integer mes, Integer anho, String etiqueta, String longitud) {
        this.mes = mes;
        this.anho = anho;
        this.etiqueta = etiqueta;
        this.longitud = longitud;
    }

    /**
     * @return the mes
     */
    public Integer getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    /**
     * @return the anho
     */
    public Integer getAnho() {
        return anho;
    }

    /**
     * @param anho the anho to set
     */
    public void setAnho(Integer anho) {
        this.anho = anho;
    }

    /**
     * @return the etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}

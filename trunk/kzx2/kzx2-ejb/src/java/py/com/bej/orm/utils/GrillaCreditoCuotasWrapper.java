/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Diego_M
 */
public class GrillaCreditoCuotasWrapper implements Serializable {

    private Integer mes;
    private Integer anho;
    private String etiqueta;
    private BigDecimal importe;
    private Boolean pagoParcial;
    private String colorEstado;
    private Boolean deHoy;
    private BigDecimal interesMoratorio;
    private BigDecimal totalAPagar;
    private Integer credito;
    private Date fechaVencimiento;
    private static final long serialVersionUID = 1436345L;

    public GrillaCreditoCuotasWrapper() {
    }

    public GrillaCreditoCuotasWrapper(Integer mes, Integer anho, String etiqueta, BigDecimal importe, Boolean pagoParcial, String colorEstado, Boolean deHoy, BigDecimal interesMoratorio, BigDecimal totalAPagar, Integer credito, Date fechaVencimiento) {
        this.mes = mes;
        this.anho = anho;
        this.etiqueta = etiqueta;
        this.importe = importe;
        this.pagoParcial = pagoParcial;
        this.colorEstado = colorEstado;
        this.deHoy = deHoy;
        this.interesMoratorio = interesMoratorio;
        this.totalAPagar = totalAPagar;
        this.credito = credito;
        this.fechaVencimiento = fechaVencimiento;
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

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the pagoParcial
     */
    public Boolean getPagoParcial() {
        return pagoParcial;
    }

    /**
     * @param pagoParcial the pagoParcial to set
     */
    public void setPagoParcial(Boolean pagoParcial) {
        this.pagoParcial = pagoParcial;
    }

    public String getColorEstado() {
        return colorEstado;
    }

    public void setColorEstado(String colorEstado) {
        this.colorEstado = colorEstado;
    }

    public Boolean getDeHoy() {
        return deHoy;
    }

    public void setDeHoy(Boolean deHoy) {
        this.deHoy = deHoy;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    public BigDecimal getInteresMoratorio() {
        return interesMoratorio;
    }

    public void setInteresMoratorio(BigDecimal interesMoratorio) {
        this.interesMoratorio = interesMoratorio;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}

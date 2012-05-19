/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;
import py.com.bej.orm.utils.Conversor;
import py.com.bej.orm.utils.ConversorDeNumeroALetra;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "financiacion", catalog = "bej")
@XmlRootElement
public class Financiacion extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "credito", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Credito credito;
    @Column(name = "numero_cuota", nullable = false)
    private short numeroCuota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capital", nullable = false, precision = 11, scale = 2)
    private BigDecimal capital;
    @Column(name = "interes", nullable = false, precision = 11, scale = 2)
    private BigDecimal interes;
    @Column(name = "cuota_neta", nullable = false, precision = 11, scale = 2)
    private BigDecimal cuotaNeta;
    @Column(name = "total_a_pagar", nullable = false, precision = 11, scale = 2)
    private BigDecimal totalAPagar;
    @Column(name = "ajuste_redondeo", nullable = false, precision = 11, scale = 2)
    private BigDecimal ajusteRedondeo;
    @Column(name = "fecha_vencimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "cancelado", length = 1, nullable = false)
    private Character cancelado;
    @Column(name = "interes_mora", precision = 11, scale = 2)
    private BigDecimal interesMora;
    @Column(name = "total_pagado", precision = 11, scale = 2, nullable = true)
    private BigDecimal totalPagado;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @Transient
    private String totalAPagarString;
    @Transient
    private String totalAPagarEnLetras;
    @Transient
    private String nombreTitular;
    @Transient
    private String documentoTitular;
    @Transient
    private String domicilioTitular;
    @Transient
    private String nombreGarante;
    @Transient
    private String documentoGarante;
    @Transient
    private String domicilioGarante;
    @Transient
    private String fechaVencimientoString;
    @Transient
    private String descripcionPagare;
    @Transient
    private String condicionesPagare;
    @Transient
    private Integer pagoAsignado;

    public Financiacion() {
    }

    public Financiacion(Integer id, Credito credito, short numeroCuota, BigDecimal capital, BigDecimal interes, BigDecimal cuotaNeta, BigDecimal totalAPagar, BigDecimal ajusteRedondeo, Date fechaPago, Character cancelado, Date fechaVencimiento, BigDecimal interesMora, BigDecimal totalPagado, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.credito = credito;
        this.numeroCuota = numeroCuota;
        this.capital = capital;
        this.interes = interes;
        this.cuotaNeta = cuotaNeta;
        this.totalAPagar = totalAPagar;
        this.ajusteRedondeo = ajusteRedondeo;
        this.fechaPago = fechaPago;
        this.cancelado = cancelado;
        this.fechaVencimiento = fechaVencimiento;
        this.interesMora = interesMora;
        this.totalPagado = totalPagado;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public short getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(short numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
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

    public Character getCancelado() {
        return cancelado;
    }

    public void setCancelado(Character cancelado) {
        this.cancelado = cancelado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getInteresMora() {
        return interesMora;
    }

    public void setInteresMora(BigDecimal interesMora) {
        this.interesMora = interesMora;
    }

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Financiacion)) {
            return false;
        }
        Financiacion other = (Financiacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Financiacion[ id=" + id + " ]";
    }

    @Override
    public void setActivo(Character activo) {
        this.activo = activo;
    }

    @Override
    public Character getActivo() {
        return this.activo;
    }

    @Override
    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    @Override
    public Date getUltimaModificacion() {
        return this.ultimaModificacion;
    }

    @Override
    public String getlabel() {
        return this.id + " " + this.credito.getlabel();
    }

    /**
     * @return the cuotaNeta
     */
    public BigDecimal getCuotaNeta() {
        return cuotaNeta;
    }

    /**
     * @param cuotaNeta the cuotaNeta to set
     */
    public void setCuotaNeta(BigDecimal cuotaNeta) {
        this.cuotaNeta = cuotaNeta;
    }

    /**
     * @return the ajusteRedondeo
     */
    public BigDecimal getAjusteRedondeo() {
        return ajusteRedondeo;
    }

    /**
     * @param ajusteRedondeo the ajusteRedondeo to set
     */
    public void setAjusteRedondeo(BigDecimal ajusteRedondeo) {
        this.ajusteRedondeo = ajusteRedondeo;
    }

    /**
     * @return the totalAPagarString
     */
    public String getTotalAPagarString() {
        if (totalAPagarString == null) {
            totalAPagarString = Conversor.numberToStringPattern(totalAPagar);
        }
        return totalAPagarString;
    }

    /**
     * @return the totalAPagarEnLetras
     */
    public String getTotalAPagarEnLetras() {
        if (totalAPagarEnLetras == null) {
            totalAPagarEnLetras = new ConversorDeNumeroALetra().getStringOfCurrency(totalAPagar.floatValue());
        }
        return totalAPagarEnLetras;
    }

    /**
     * @return the nombreTitular
     */
    public String getNombreTitular() {
        return nombreTitular;
    }

    /**
     * @param nombreTitular the nombreTitular to set
     */
    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    /**
     * @return the documentoTitular
     */
    public String getDocumentoTitular() {
        return documentoTitular;
    }

    /**
     * @param documentoTitular the documentoTitular to set
     */
    public void setDocumentoTitular(String documentoTitular) {
        this.documentoTitular = documentoTitular;
    }

    /**
     * @return the domicilioTitular
     */
    public String getDomicilioTitular() {
        return domicilioTitular;
    }

    /**
     * @param domicilioTitular the domicilioTitular to set
     */
    public void setDomicilioTitular(String domicilioTitular) {
        this.domicilioTitular = domicilioTitular;
    }

    /**
     * @return the nombreGarante
     */
    public String getNombreGarante() {
        return nombreGarante;
    }

    /**
     * @param nombreGarante the nombreGarante to set
     */
    public void setNombreGarante(String nombreGarante) {
        this.nombreGarante = nombreGarante;
    }

    /**
     * @return the documentoGarante
     */
    public String getDocumentoGarante() {
        return documentoGarante;
    }

    /**
     * @param documentoGarante the documentoGarante to set
     */
    public void setDocumentoGarante(String documentoGarante) {
        this.documentoGarante = documentoGarante;
    }

    /**
     * @return the domicilioGarante
     */
    public String getDomicilioGarante() {
        return domicilioGarante;
    }

    /**
     * @param domicilioGarante the domicilioGarante to set
     */
    public void setDomicilioGarante(String domicilioGarante) {
        this.domicilioGarante = domicilioGarante;
    }

    public String getFechaVencimientoString() {
        if (fechaVencimientoString == null) {
            if (fechaVencimiento != null) {
                fechaVencimientoString = Conversor.deDateToString(fechaVencimiento, "d 'de' MMMMM 'de' yyyy");
            } else {
                fechaVencimientoString = "";
            }
        }
        return fechaVencimientoString;
    }

    public String getDescripcionPagare() {
        return descripcionPagare;
    }

    public void setDescripcionPagare(String descripcionPagare) {
        this.descripcionPagare = descripcionPagare;
    }

    /**
     * @return the condicionesPagare
     */
    public String getCondicionesPagare() {
        return condicionesPagare;
    }

    /**
     * @param condicionesPagare the condicionesPagare to set
     */
    public void setCondicionesPagare(String condicionesPagare) {
        this.condicionesPagare = condicionesPagare;
    }

    public Integer getPagoAsignado() {
        return pagoAsignado;
    }

    public void setPagoAsignado(Integer pagoAsignado) {
        this.pagoAsignado = pagoAsignado;
    }
}

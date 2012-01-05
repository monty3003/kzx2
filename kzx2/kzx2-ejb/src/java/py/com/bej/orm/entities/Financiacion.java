/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Financiacion", catalog = "bejdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Financiacion.findAll", query = "SELECT f FROM Financiacion f"),
    @NamedQuery(name = "Financiacion.findById", query = "SELECT f FROM Financiacion f WHERE f.id = :id"),
    @NamedQuery(name = "Financiacion.findByCredito", query = "SELECT f FROM Financiacion f WHERE f.credito = :credito"),
    @NamedQuery(name = "Financiacion.findByNumeroCuota", query = "SELECT f FROM Financiacion f WHERE f.numeroCuota = :numeroCuota"),
    @NamedQuery(name = "Financiacion.findByCapital", query = "SELECT f FROM Financiacion f WHERE f.capital = :capital"),
    @NamedQuery(name = "Financiacion.findByInteres", query = "SELECT f FROM Financiacion f WHERE f.interes = :interes"),
    @NamedQuery(name = "Financiacion.findByTotalAPagar", query = "SELECT f FROM Financiacion f WHERE f.totalAPagar = :totalAPagar"),
    @NamedQuery(name = "Financiacion.findByFechaVencimiento", query = "SELECT f FROM Financiacion f WHERE f.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "Financiacion.findByFechaPago", query = "SELECT f FROM Financiacion f WHERE f.fechaPago = :fechaPago"),
    @NamedQuery(name = "Financiacion.findByInteresMora", query = "SELECT f FROM Financiacion f WHERE f.interesMora = :interesMora"),
    @NamedQuery(name = "Financiacion.findByTotalPagado", query = "SELECT f FROM Financiacion f WHERE f.totalPagado = :totalPagado")})
public class Financiacion implements Serializable, WithId<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "credito", referencedColumnName = "id", insertable = false, updatable = true)
    @ManyToOne
    private Credito credito;
    @Basic(optional = false)
    @Column(name = "numero_cuota", nullable = false)
    private short numeroCuota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "capital", nullable = false, precision = 11, scale = 2)
    private BigDecimal capital;
    @Basic(optional = false)
    @Column(name = "interes", nullable = false, precision = 11, scale = 2)
    private BigDecimal interes;
    @Basic(optional = false)
    @Column(name = "cuota_neta", nullable = false, precision = 11, scale = 2)
    private BigDecimal cuotaNeta;
    @Basic(optional = false)
    @Column(name = "total_a_pagar", nullable = false, precision = 11, scale = 2)
    private BigDecimal totalAPagar;
    @Basic(optional = false)
    @Column(name = "ajuste_redondeo", nullable = false, precision = 11, scale = 2)
    private BigDecimal ajusteRedondeo;
    @Basic(optional = false)
    @Column(name = "fecha_vencimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "interes_mora", precision = 11, scale = 2)
    private BigDecimal interesMora;
    @Column(name = "total_pagado", precision = 11, scale = 2)
    private BigDecimal totalPagado;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "financiacion")
    private List<Pago> pagos;

    public Financiacion() {
    }

    public Financiacion(Integer id, Credito credito, short numeroCuota, BigDecimal capital, BigDecimal interes, BigDecimal cuotaNeta, BigDecimal totalAPagar, BigDecimal ajusteRedondeo, Date fechaPago, BigDecimal interesMora, BigDecimal totalPagado, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.credito = credito;
        this.numeroCuota = numeroCuota;
        this.capital = capital;
        this.interes = interes;
        this.cuotaNeta = cuotaNeta;
        this.totalAPagar = totalAPagar;
        this.ajusteRedondeo = ajusteRedondeo;
        this.fechaPago = fechaPago;
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
     * @return the pagos
     */
    public List<Pago> getPagos() {
        return pagos;
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
}

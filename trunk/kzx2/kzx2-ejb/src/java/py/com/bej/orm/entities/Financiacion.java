/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.bej.orm.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
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

/**
 *
 * @author diego
 */
@Entity
@Table(name = "Financiacion")
public class Financiacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "credito", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Credito credito;
    @Basic(optional = false)
    @Column(name = "numero_cuota")
    private short numeroCuota;
    @Basic(optional = false)
    @Column(name = "capital")
    private BigDecimal capital;
    @Basic(optional = false)
    @Column(name = "interes")
    private BigDecimal interes;
    @Basic(optional = false)
    @Column(name = "total_a_pagar")
    private BigDecimal totalAPagar;
    @Basic(optional = false)
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "interes_mora")
    private BigDecimal interesMora;
    @Column(name = "total_pagado")
    private BigDecimal totalPagado;

    public Financiacion() {
    }

    public Financiacion(Integer id) {
        this.id = id;
    }

    public Financiacion(Integer id, Credito credito, short numeroCuota, BigDecimal capital, BigDecimal interes, BigDecimal totalAPagar, Date fechaVencimiento) {
        this.id = id;
        this.credito = credito;
        this.numeroCuota = numeroCuota;
        this.capital = capital;
        this.interes = interes;
        this.totalAPagar = totalAPagar;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getId() {
        return id;
    }

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
        return "py.com.bej.orm.entities.Financiacion[id=" + id + "]";
    }

}

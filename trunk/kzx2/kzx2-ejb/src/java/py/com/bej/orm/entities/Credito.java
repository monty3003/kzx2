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
@Table(name = "credito")
public class Credito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "categoria", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "transaccion", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Transaccion transaccion;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "sistema_credito", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Categoria sistemaCredito;
    @Basic(optional = false)
    @Column(name = "tan")
    private float tan;
    @Basic(optional = false)
    @Column(name = "tae")
    private float tae;
    @Basic(optional = false)
    @Column(name = "capital")
    private BigDecimal capital;
    @Basic(optional = false)
    @Column(name = "amortizacion")
    private short amortizacion;
    @Basic(optional = false)
    @Column(name = "credito_total")
    private BigDecimal creditoTotal;
    @Basic(optional = false)
    @Column(name = "total_amortizado_pagado")
    private BigDecimal totalAmortizadoPagado;
    @Basic(optional = false)
    @Column(name = "total_intereses_pagado")
    private BigDecimal totalInteresesPagado;
    @Column(name = "total_intereses_pagado_multa")
    private BigDecimal totalInteresesPagadoMulta;
    @Column(name = "fecha_ultimo_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimoPago;
    @Column(name = "cuotas_atrasadas")
    private Short cuotasAtrasadas;
    @JoinColumn(name = "estado", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Categoria estado;

    public Credito() {
    }

    public Credito(Integer id) {
        this.id = id;
    }

    public Credito(Integer id, Categoria categoria, Transaccion transaccion, Date fechaInicio, Date fechaFin, Categoria sistemaCredito, float tan, float tae, BigDecimal capital, short amortizacion, BigDecimal creditoTotal, BigDecimal totalAmortizadoPagado, BigDecimal totalInteresesPagado, Categoria estado) {
        this.id = id;
        this.categoria = categoria;
        this.transaccion = transaccion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sistemaCredito = sistemaCredito;
        this.tan = tan;
        this.tae = tae;
        this.capital = capital;
        this.amortizacion = amortizacion;
        this.creditoTotal = creditoTotal;
        this.totalAmortizadoPagado = totalAmortizadoPagado;
        this.totalInteresesPagado = totalInteresesPagado;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Categoria getSistemaCredito() {
        return sistemaCredito;
    }

    public void setSistemaCredito(Categoria sistemaCredito) {
        this.sistemaCredito = sistemaCredito;
    }

    public float getTan() {
        return tan;
    }

    public void setTan(float tan) {
        this.tan = tan;
    }

    public float getTae() {
        return tae;
    }

    public void setTae(float tae) {
        this.tae = tae;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public short getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(short amortizacion) {
        this.amortizacion = amortizacion;
    }

    public BigDecimal getCreditoTotal() {
        return creditoTotal;
    }

    public void setCreditoTotal(BigDecimal creditoTotal) {
        this.creditoTotal = creditoTotal;
    }

    public BigDecimal getTotalAmortizadoPagado() {
        return totalAmortizadoPagado;
    }

    public void setTotalAmortizadoPagado(BigDecimal totalAmortizadoPagado) {
        this.totalAmortizadoPagado = totalAmortizadoPagado;
    }

    public BigDecimal getTotalInteresesPagado() {
        return totalInteresesPagado;
    }

    public void setTotalInteresesPagado(BigDecimal totalInteresesPagado) {
        this.totalInteresesPagado = totalInteresesPagado;
    }

    public BigDecimal getTotalInteresesPagadoMulta() {
        return totalInteresesPagadoMulta;
    }

    public void setTotalInteresesPagadoMulta(BigDecimal totalInteresesPagadoMulta) {
        this.totalInteresesPagadoMulta = totalInteresesPagadoMulta;
    }

    public Date getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(Date fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public Short getCuotasAtrasadas() {
        return cuotasAtrasadas;
    }

    public void setCuotasAtrasadas(Short cuotasAtrasadas) {
        this.cuotasAtrasadas = cuotasAtrasadas;
    }

    public Categoria getEstado() {
        return estado;
    }

    public void setEstado(Categoria estado) {
        this.estado = estado;
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
        if (!(object instanceof Credito)) {
            return false;
        }
        Credito other = (Credito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Credito[id=" + id + "]";
    }
}

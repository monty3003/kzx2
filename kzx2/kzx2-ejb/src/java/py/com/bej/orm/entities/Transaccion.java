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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import py.com.bej.orm.utils.Conversor;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "Transaccion")
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "codigo", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Categoria codigo;
    @Basic(optional = false)
    @Column(name = "comprobante")
    private String comprobante;
    @Basic(optional = false)
    @Column(name = "fechaOperacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    @Basic(optional = false)
    @Column(name = "fechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @JoinColumns({
        @JoinColumn(name = "vendedor", referencedColumnName = "documento", insertable = false, updatable = false),
        @JoinColumn(name = "vendedor", referencedColumnName = "id", insertable = true, updatable = true)})
    @ManyToOne(optional = false)
    private Persona vendedor;
    @JoinColumns({
        @JoinColumn(name = "comprador", referencedColumnName = "documento", insertable = false, updatable = false),
        @JoinColumn(name = "comprador", referencedColumnName = "id", insertable = true, updatable = true)})
    @ManyToOne(optional = false)
    private Persona comprador;
    @Basic(optional = false)
    @Column(name = "anulado")
    private Character anulado;
    @Basic(optional = false)
    @Column(name = "sub_total_exentas")
    private BigDecimal subTotalExentas;
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_10")
    private BigDecimal subTotalGravadas10;
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_5")
    private BigDecimal subTotalGravadas5;
    @Basic(optional = false)
    @Column(name = "sub_total")
    private BigDecimal subTotal;
    @Basic(optional = false)
    @Column(name = "total_iva5")
    private BigDecimal totalIva5;
    @Basic(optional = false)
    @Column(name = "total_iva10")
    private BigDecimal totalIva10;
    @Basic(optional = false)
    @Column(name = "total_iva")
    private BigDecimal totalIva;
    @Basic(optional = false)
    @Column(name = "descuento")
    private Float descuento;
    @Basic(optional = false)
    @Column(name = "total")
    private BigDecimal total;
    @Basic(optional = false)
    @Column(name = "total_descuento")
    private BigDecimal totalDescuento;
    @Basic(optional = false)
    @Column(name = "total_pagado")
    private BigDecimal totalPagado;
    @Basic(optional = false)
    @Column(name = "entrega_inicial")
    private BigDecimal entregaInicial;
    @Basic(optional = false)
    @Column(name = "cuotas")
    private Short cuotas;
    @Basic(optional = false)
    @Column(name = "monto_cuota_igual")
    private BigDecimal montoCuotaIgual;
    @Basic(optional = false)
    @Column(name = "saldado")
    private Character saldado;
    @Basic(optional = false)
    @Column(name = "cantidad_items")
    private Short cantidadItems;

    public Transaccion() {
    }

    public Transaccion(Integer id) {
        this.id = id;
    }

    public Transaccion(Integer id, Categoria codigo, String comprobante, Date fechaOperacion, Date fechaEntrega, Persona vendedor, Persona comprador, Character anulado, BigDecimal subTotalExentas, BigDecimal subTotalGravadas10, BigDecimal subTotalGravadas5, BigDecimal subTotal, BigDecimal totalIva5, BigDecimal totalIva10, BigDecimal totalIva, Float descuento, BigDecimal total, BigDecimal totalDescuento, BigDecimal totalPagado, BigDecimal entregaInicial, Short cuotas, BigDecimal montoCuotaIgual, Character saldado, Short cantidadItems) {
        this.id = id;
        this.codigo = codigo;
        this.comprobante = comprobante;
        this.fechaOperacion = fechaOperacion;
        this.fechaEntrega = fechaEntrega;
        this.vendedor = vendedor;
        this.comprador = comprador;
        this.anulado = anulado;
        this.subTotalExentas = subTotalExentas;
        this.subTotalGravadas10 = subTotalGravadas10;
        this.subTotalGravadas5 = subTotalGravadas5;
        this.subTotal = subTotal;
        this.totalIva5 = totalIva5;
        this.totalIva10 = totalIva10;
        this.totalIva = totalIva;
        this.descuento = descuento;
        this.total = total;
        this.totalDescuento = totalDescuento;
        this.totalPagado = totalPagado;
        this.entregaInicial = entregaInicial;
        this.cuotas = cuotas;
        this.montoCuotaIgual = montoCuotaIgual;
        this.saldado = saldado;
        this.cantidadItems = cantidadItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getCodigo() {
        return codigo;
    }

    public void setCodigo(Categoria codigo) {
        this.codigo = codigo;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaEntregaString() {
        return Conversor.deDateToString(fechaEntrega);
    }

    public void setFechaEntregaString(String fechaEntrega) {
        this.fechaEntrega = Conversor.deStringToDate(fechaEntrega);
    }

    public Persona getVendedor() {
        return vendedor;
    }

    public void setVendedor(Persona vendedor) {
        this.vendedor = vendedor;
    }

    public Persona getComprador() {
        return comprador;
    }

    public void setComprador(Persona comprador) {
        this.comprador = comprador;
    }

    public Character getAnulado() {
        return anulado;
    }

    public void setAnulado(Character anulado) {
        this.anulado = anulado;
    }

    public BigDecimal getSubTotalExentas() {
        return subTotalExentas;
    }

    public void setSubTotalExentas(BigDecimal subTotalExentas) {
        this.subTotalExentas = subTotalExentas;
    }

    public BigDecimal getSubTotalGravadas10() {
        return subTotalGravadas10;
    }

    public void setSubTotalGravadas10(BigDecimal subTotalGravadas10) {
        this.subTotalGravadas10 = subTotalGravadas10;
    }

    public BigDecimal getSubTotalGravadas5() {
        return subTotalGravadas5;
    }

    public void setSubTotalGravadas5(BigDecimal subTotalGravadas5) {
        this.subTotalGravadas5 = subTotalGravadas5;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalIva5() {
        return totalIva5;
    }

    public void setTotalIva5(BigDecimal totalIva5) {
        this.totalIva5 = totalIva5;
    }

    public BigDecimal getTotalIva10() {
        return totalIva10;
    }

    public void setTotalIva10(BigDecimal totalIva10) {
        this.totalIva10 = totalIva10;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    public BigDecimal getEntregaInicial() {
        return entregaInicial;
    }

    public void setEntregaInicial(BigDecimal entregaInicial) {
        this.entregaInicial = entregaInicial;
    }

    public Short getCuotas() {
        return cuotas;
    }

    public void setCuotas(Short cuotas) {
        this.cuotas = cuotas;
    }

    public BigDecimal getMontoCuotaIgual() {
        return montoCuotaIgual;
    }

    public void setMontoCuotaIgual(BigDecimal montoCuotaIgual) {
        this.montoCuotaIgual = montoCuotaIgual;
    }

    public Character getSaldado() {
        return saldado;
    }

    public void setSaldado(Character saldado) {
        this.saldado = saldado;
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
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Transaccion[id=" + id + "]";
    }

    /**
     * @return the cantidadItems
     */
    public Short getCantidadItems() {
        return cantidadItems;
    }

    /**
     * @param cantidadItems the cantidadItems to set
     */
    public void setCantidadItems(Short cantidadItems) {
        this.cantidadItems = cantidadItems;
    }
}

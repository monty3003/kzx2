/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "factura", catalog = "bej")
@XmlRootElement
public class Factura extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{7}$", message = "Ingrese un valor con el formato ###-###-#######")
    @Column(name = "numero", nullable = false, length = 45)
    private String numero;
    @Column(name = "valido_hasta", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date validoHasta;
    @JoinColumn(name = "categoria", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @Column(name = "sub_total_exentas", nullable = false)
    private BigDecimal subTotalExentas;
    @Column(name = "sub_total_gravadas_10", nullable = false)
    private BigDecimal subTotalGravadas10;
    @Column(name = "sub_total_gravadas_5", nullable = false)
    private BigDecimal subTotalGravadas5;
    @Column(name = "neto_sin_iva_5", nullable = false)
    private BigDecimal netoSinIva5;
    @Column(name = "neto_sin_iva_10", nullable = false)
    private BigDecimal netoSinIva10;
    @Column(name = "total_iva_5", nullable = false)
    private BigDecimal totalIva5;
    @Column(name = "total_iva_10", nullable = false)
    private BigDecimal totalIva10;
    @Column(name = "sub_total", nullable = false)
    private BigDecimal subTotal;
    @Column(name = "total_iva", nullable = false)
    private BigDecimal totalIva;
    @Column(name = "total_pagado", nullable = false)
    private BigDecimal totalPagado;
    @Column(name = "descuento", nullable = false)
    private float descuento;
    @Column(name = "saldado", nullable = false)
    private Character saldado;
    @Column(name = "activo", length = 1)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToOne(optional = false, mappedBy = "factura", cascade = CascadeType.ALL)
    private Transaccion transaccion;

    public Factura() {
        this.categoria = new Categoria();
    }

    public Factura(String numero) {
        this.numero = numero;
    }

    public Factura(Integer id, String numero, Transaccion transaccion, Date validoHasta, Categoria categoria, BigDecimal subTotalExentas, BigDecimal subTotalGravadas10, BigDecimal subTotalGravadas5, BigDecimal netoSinIva5, BigDecimal netoSinIva10, BigDecimal totalIva5, BigDecimal totalIva10, BigDecimal subTotal, BigDecimal totalIva, BigDecimal totalPagado, float descuento, Character saldado, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.numero = numero;
        this.transaccion = transaccion;
        this.validoHasta = validoHasta;
        this.categoria = categoria;
        this.subTotalExentas = subTotalExentas;
        this.subTotalGravadas10 = subTotalGravadas10;
        this.subTotalGravadas5 = subTotalGravadas5;
        this.netoSinIva5 = netoSinIva5;
        this.netoSinIva10 = netoSinIva10;
        this.totalIva5 = totalIva5;
        this.totalIva10 = totalIva10;
        this.subTotal = subTotal;
        this.totalIva = totalIva;
        this.totalPagado = totalPagado;
        this.descuento = descuento;
        this.saldado = saldado;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
    }

    public Date getValidoHasta() {
        return validoHasta;
    }

    public void setValidoHasta(Date validoHasta) {
        this.validoHasta = validoHasta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public Character getSaldado() {
        return saldado;
    }

    public void setSaldado(Character saldado) {
        this.saldado = saldado;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Factura[ id=" + id + " ]";
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return this.id;
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
        return this.getNumero() + " " + this.categoria.getDescripcion();
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the netoSinIva5
     */
    public BigDecimal getNetoSinIva5() {
        return netoSinIva5;
    }

    /**
     * @param netoSinIva5 the netoSinIva5 to set
     */
    public void setNetoSinIva5(BigDecimal netoSinIva5) {
        this.netoSinIva5 = netoSinIva5;
    }

    /**
     * @return the netoSinIva10
     */
    public BigDecimal getNetoSinIva10() {
        return netoSinIva10;
    }

    /**
     * @param netoSinIva10 the netoSinIva10 to set
     */
    public void setNetoSinIva10(BigDecimal netoSinIva10) {
        this.netoSinIva10 = netoSinIva10;
    }

    /**
     * @return the totalIva5
     */
    public BigDecimal getTotalIva5() {
        return totalIva5;
    }

    /**
     * @param totalIva5 the totalIva5 to set
     */
    public void setTotalIva5(BigDecimal totalIva5) {
        this.totalIva5 = totalIva5;
    }

    /**
     * @return the totalIva10
     */
    public BigDecimal getTotalIva10() {
        return totalIva10;
    }

    /**
     * @param totalIva10 the totalIva10 to set
     */
    public void setTotalIva10(BigDecimal totalIva10) {
        this.totalIva10 = totalIva10;
    }

    /**
     * @return the transaccion
     */
    public Transaccion getTransaccion() {
        return transaccion;
    }

    /**
     * @param transaccion the transaccion to set
     */
    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }
}

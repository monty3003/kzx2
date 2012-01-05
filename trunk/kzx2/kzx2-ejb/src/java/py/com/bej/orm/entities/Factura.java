/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "Factura", catalog = "bejdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findById", query = "SELECT f FROM Factura f WHERE f.facturaPK.id = :id"),
    @NamedQuery(name = "Factura.findByNumero", query = "SELECT f FROM Factura f WHERE f.facturaPK.numero = :numero"),
    @NamedQuery(name = "Factura.findByValidoHasta", query = "SELECT f FROM Factura f WHERE f.validoHasta = :validoHasta"),
    @NamedQuery(name = "Factura.findByCategoria", query = "SELECT f FROM Factura f WHERE f.categoria = :categoria"),
    @NamedQuery(name = "Factura.findBySubTotalExentas", query = "SELECT f FROM Factura f WHERE f.subTotalExentas = :subTotalExentas"),
    @NamedQuery(name = "Factura.findBySubTotalGravadas10", query = "SELECT f FROM Factura f WHERE f.subTotalGravadas10 = :subTotalGravadas10"),
    @NamedQuery(name = "Factura.findBySubTotalGravadas5", query = "SELECT f FROM Factura f WHERE f.subTotalGravadas5 = :subTotalGravadas5"),
    @NamedQuery(name = "Factura.findBySubTotal", query = "SELECT f FROM Factura f WHERE f.subTotal = :subTotal"),
    @NamedQuery(name = "Factura.findByTotalIva", query = "SELECT f FROM Factura f WHERE f.totalIva = :totalIva"),
    @NamedQuery(name = "Factura.findByTotalPagado", query = "SELECT f FROM Factura f WHERE f.totalPagado = :totalPagado"),
    @NamedQuery(name = "Factura.findByDescuento", query = "SELECT f FROM Factura f WHERE f.descuento = :descuento"),
    @NamedQuery(name = "Factura.findBySaldado", query = "SELECT f FROM Factura f WHERE f.saldado = :saldado")})
public class Factura implements Serializable, WithId<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic(optional = false)
    @Column(name = "numero", nullable = false, unique = true, length = 45)
    private String numero;
    @Basic(optional = false)
    @Column(name = "valido_hasta", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validoHasta;
    @Basic(optional = false)
    @JoinColumn(name = "categoria", referencedColumnName = "id", insertable = true, updatable = false)
    @ManyToOne
    private Categoria categoria;
    @Basic(optional = false)
    @Column(name = "sub_total_exentas", nullable = false)
    private long subTotalExentas;
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_10", nullable = false)
    private long subTotalGravadas10;
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_5", nullable = false)
    private long subTotalGravadas5;
    @Basic(optional = false)
    @Column(name = "sub_total", nullable = false)
    private long subTotal;
    @Basic(optional = false)
    @Column(name = "total_iva", nullable = false)
    private long totalIva;
    @Basic(optional = false)
    @Column(name = "total_pagado", nullable = false)
    private long totalPagado;
    @Basic(optional = false)
    @Column(name = "descuento", nullable = false)
    private float descuento;
    @Basic(optional = false)
    @Column(name = "saldado", nullable = false)
    private Character saldado;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "comprobante")
    private List<Transaccion> transaccions;

    public Factura() {
    }

    public Factura(int id, String numero, Date validoHasta, Categoria categoria, long subTotalExentas, long subTotalGravadas10, long subTotalGravadas5, long subTotal, long totalIva, long totalPagado, float descuento, Character saldado, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.numero = numero;
        this.validoHasta = validoHasta;
        this.categoria = categoria;
        this.subTotalExentas = subTotalExentas;
        this.subTotalGravadas10 = subTotalGravadas10;
        this.subTotalGravadas5 = subTotalGravadas5;
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

    public long getSubTotalExentas() {
        return subTotalExentas;
    }

    public void setSubTotalExentas(long subTotalExentas) {
        this.subTotalExentas = subTotalExentas;
    }

    public long getSubTotalGravadas10() {
        return subTotalGravadas10;
    }

    public void setSubTotalGravadas10(long subTotalGravadas10) {
        this.subTotalGravadas10 = subTotalGravadas10;
    }

    public long getSubTotalGravadas5() {
        return subTotalGravadas5;
    }

    public void setSubTotalGravadas5(long subTotalGravadas5) {
        this.subTotalGravadas5 = subTotalGravadas5;
    }

    public long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(long subTotal) {
        this.subTotal = subTotal;
    }

    public long getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(long totalIva) {
        this.totalIva = totalIva;
    }

    public long getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(long totalPagado) {
        this.totalPagado = totalPagado;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public char getSaldado() {
        return saldado;
    }

    public void setSaldado(char saldado) {
        this.saldado = saldado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Integer)) {
            return false;
        }
        Integer other = (Integer) object;
        if (this.id != other) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Factura[" + id + " ]";
    }

    @Override
    public void setId(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        return this.numero + " " + this.categoria.getDescripcion();
    }

    /**
     * @return the transaccions
     */
    public List<Transaccion> getTransaccions() {
        return transaccions;
    }
}

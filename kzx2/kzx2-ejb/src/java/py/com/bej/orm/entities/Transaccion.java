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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Transaccion", catalog = "bejdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findById", query = "SELECT t FROM Transaccion t WHERE t.id = :id"),
    @NamedQuery(name = "Transaccion.findByCodigo", query = "SELECT t FROM Transaccion t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Transaccion.findByComprobante", query = "SELECT t FROM Transaccion t WHERE t.comprobante = :comprobante"),
    @NamedQuery(name = "Transaccion.findByFechaOperacion", query = "SELECT t FROM Transaccion t WHERE t.fechaOperacion = :fechaOperacion"),
    @NamedQuery(name = "Transaccion.findByFechaEntrega", query = "SELECT t FROM Transaccion t WHERE t.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "Transaccion.findByVendedor", query = "SELECT t FROM Transaccion t WHERE t.vendedor = :vendedor"),
    @NamedQuery(name = "Transaccion.findByComprador", query = "SELECT t FROM Transaccion t WHERE t.comprador = :comprador"),
    @NamedQuery(name = "Transaccion.findByAnulado", query = "SELECT t FROM Transaccion t WHERE t.anulado = :anulado"),
    @NamedQuery(name = "Transaccion.findBySubTotalExentas", query = "SELECT t FROM Transaccion t WHERE t.subTotalExentas = :subTotalExentas"),
    @NamedQuery(name = "Transaccion.findBySubTotalGravadas10", query = "SELECT t FROM Transaccion t WHERE t.subTotalGravadas10 = :subTotalGravadas10"),
    @NamedQuery(name = "Transaccion.findBySubTotalGravadas5", query = "SELECT t FROM Transaccion t WHERE t.subTotalGravadas5 = :subTotalGravadas5"),
    @NamedQuery(name = "Transaccion.findBySubTotal", query = "SELECT t FROM Transaccion t WHERE t.subTotal = :subTotal"),
    @NamedQuery(name = "Transaccion.findByTotalIva5", query = "SELECT t FROM Transaccion t WHERE t.totalIva5 = :totalIva5"),
    @NamedQuery(name = "Transaccion.findByTotalIva10", query = "SELECT t FROM Transaccion t WHERE t.totalIva10 = :totalIva10"),
    @NamedQuery(name = "Transaccion.findByTotalIva", query = "SELECT t FROM Transaccion t WHERE t.totalIva = :totalIva"),
    @NamedQuery(name = "Transaccion.findByDescuento", query = "SELECT t FROM Transaccion t WHERE t.descuento = :descuento"),
    @NamedQuery(name = "Transaccion.findByTotal", query = "SELECT t FROM Transaccion t WHERE t.total = :total"),
    @NamedQuery(name = "Transaccion.findByTotalDescuento", query = "SELECT t FROM Transaccion t WHERE t.totalDescuento = :totalDescuento"),
    @NamedQuery(name = "Transaccion.findByTotalPagado", query = "SELECT t FROM Transaccion t WHERE t.totalPagado = :totalPagado"),
    @NamedQuery(name = "Transaccion.findByEntregaInicial", query = "SELECT t FROM Transaccion t WHERE t.entregaInicial = :entregaInicial"),
    @NamedQuery(name = "Transaccion.findByCuotas", query = "SELECT t FROM Transaccion t WHERE t.cuotas = :cuotas"),
    @NamedQuery(name = "Transaccion.findByMontoCuotaIgual", query = "SELECT t FROM Transaccion t WHERE t.montoCuotaIgual = :montoCuotaIgual"),
    @NamedQuery(name = "Transaccion.findBySaldado", query = "SELECT t FROM Transaccion t WHERE t.saldado = :saldado"),
    @NamedQuery(name = "Transaccion.findByCantidadItems", query = "SELECT t FROM Transaccion t WHERE t.cantidadItems = :cantidadItems")})
public class Transaccion implements Serializable, WithId<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @JoinColumn(name = "codigo", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne
    private Categoria codigo;
    @Basic(optional = false)
    @JoinColumn(name = "comprobante", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne
    private Factura comprobante;
    @Basic(optional = false)
    @Column(name = "fechaOperacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    @Basic(optional = false)
    @Column(name = "fechaEntrega", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Basic(optional = false)
    @Column(name = "vendedor", nullable = false)
    @ManyToOne
    private Persona vendedor;
    @Basic(optional = false)
    @Column(name = "comprador", nullable = false)
    @ManyToOne
    private Persona comprador;
    @Basic(optional = false)
    @Column(name = "anulado", nullable = false)
    private char anulado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "sub_total_exentas", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotalExentas;
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_10", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotalGravadas10;
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_5", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotalGravadas5;
    @Basic(optional = false)
    @Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;
    @Basic(optional = false)
    @Column(name = "total_iva5", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIva5;
    @Basic(optional = false)
    @Column(name = "total_iva10", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIva10;
    @Basic(optional = false)
    @Column(name = "total_iva", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIva;
    @Basic(optional = false)
    @Column(name = "descuento", nullable = false)
    private float descuento;
    @Basic(optional = false)
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @Basic(optional = false)
    @Column(name = "total_descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDescuento;
    @Basic(optional = false)
    @Column(name = "total_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPagado;
    @Basic(optional = false)
    @Column(name = "entrega_inicial", nullable = false, precision = 10, scale = 2)
    private BigDecimal entregaInicial;
    @Basic(optional = false)
    @Column(name = "cuotas", nullable = false)
    private short cuotas;
    @Basic(optional = false)
    @Column(name = "monto_cuota_igual", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoCuotaIgual;
    @Basic(optional = false)
    @Column(name = "saldado", nullable = false)
    private char saldado;
    @Basic(optional = false)
    @Column(name = "cantidad_items", nullable = false)
    private short cantidadItems;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "compra")
    private List<Motostock> motostocksCompra;
    @OneToMany(mappedBy = "venta")
    private List<Motostock> motostocksVenta;
    @Transient
    private String fechaOperacionString;
    @Transient
    private String fechaEntregaString;
    @OneToMany(mappedBy = "transaccion")
    private List<Credito> creditosTransaccion;

    public Transaccion() {
    }

    public Transaccion(Integer id) {
        this.id = id;
    }

    public Transaccion(Integer id, Categoria codigo, Factura comprobante, Date fechaOperacion, Date fechaEntrega, Persona vendedor, Persona comprador, char anulado, BigDecimal subTotalExentas, BigDecimal subTotalGravadas10, BigDecimal subTotalGravadas5, BigDecimal subTotal, BigDecimal totalIva5, BigDecimal totalIva10, BigDecimal totalIva, float descuento, BigDecimal total, BigDecimal totalDescuento, BigDecimal totalPagado, BigDecimal entregaInicial, short cuotas, BigDecimal montoCuotaIgual, char saldado, short cantidadItems) {
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getCodigo() {
        return codigo;
    }

    public void setCodigo(Categoria codigo) {
        this.codigo = codigo;
    }

    public Factura getComprobante() {
        return comprobante;
    }

    public void setComprobante(Factura comprobante) {
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

    public char getAnulado() {
        return anulado;
    }

    public void setAnulado(char anulado) {
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

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
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

    public short getCuotas() {
        return cuotas;
    }

    public void setCuotas(short cuotas) {
        this.cuotas = cuotas;
    }

    public BigDecimal getMontoCuotaIgual() {
        return montoCuotaIgual;
    }

    public void setMontoCuotaIgual(BigDecimal montoCuotaIgual) {
        this.montoCuotaIgual = montoCuotaIgual;
    }

    public char getSaldado() {
        return saldado;
    }

    public void setSaldado(char saldado) {
        this.saldado = saldado;
    }

    public short getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(short cantidadItems) {
        this.cantidadItems = cantidadItems;
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
        return "py.com.bej.orm.entities.Transaccion[ id=" + id + " ]";
    }

    /**
     * @return the motostocksCompra
     */
    public List<Motostock> getMotostocksCompra() {
        return motostocksCompra;
    }

    /**
     * @return the motostocksVenta
     */
    public List<Motostock> getMotostocksVenta() {
        return motostocksVenta;
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
        return this.codigo.getDescripcion() + " " + this.comprobante + " " + this.getFechaOperacionString();
    }

    /**
     * @return the fechaOperacionString
     */
    public String getFechaOperacionString() {
        return fechaOperacionString;
    }

    /**
     * @return the fechaEntregaString
     */
    public String getFechaEntregaString() {
        return fechaEntregaString;
    }

    /**
     * @return the creditosTransaccion
     */
    public List<Credito> getCreditosTransaccion() {
        return creditosTransaccion;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Transaccion", catalog = "bej")
@XmlRootElement
public class Transaccion extends WithId<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "codigo", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Categoria codigo;
    @JoinColumn(name = "factura", unique = true, referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Factura factura;
    @NotNull(message = "Ingrese una fecha de operacion")
    @Past(message = "Ingrese una fecha válida")
    @Basic(optional = false)
    @Column(name = "fechaOperacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    @NotNull(message = "Ingrese una fecha de entrega válida")
    @Basic(optional = false)
    @Column(name = "fechaEntrega", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @JoinColumn(name = "vendedor", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Persona vendedor;
    @JoinColumn(name = "comprador", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Persona comprador;
    @Basic(optional = false)
    @Column(name = "anulado", nullable = false)
    private Character anulado;
    @Min(value = 0, message = "El Sub Total Exentas es un monto muy bajo")
    @DecimalMin(value="0.00",message="Ingrese un valor")
    @Column(name = "sub_total_exentas", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotalExentas;
    @Min(value = 0, message = "El Sub Total Gravadas 10% es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_10", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotalGravadas10;
    @Min(value = 0, message = "El Sub Total Gravadas 5% es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "sub_total_gravadas_5", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotalGravadas5;
    @Min(value = 0)
    @Basic(optional = false)
    @Column(name = "neto_sin_iva_5", nullable = false)
    private BigDecimal netoSinIva5;
    @Min(value = 0)
    @Basic(optional = false)
    @Column(name = "neto_sin_iva_10", nullable = false)
    private BigDecimal netoSinIva10;
    @Min(value = 1000, message = "El Sub Total es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;
    @DecimalMin(value = "0.00", message = "El Sub Total IVA 5% es un monto muy bajo")
    @Column(name = "total_iva5", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIva5;
    @Min(value = 0, message = "El Sub Total IVA 10% es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "total_iva10", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIva10;
    @Min(value = 0, message = "El Total IVA es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "total_iva", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalIva;
    @Min(value = 0, message = "El Descuento es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "descuento", nullable = false)
    private Float descuento;
    @Min(value = 1000, message = "El Total es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @Min(value = 0, message = "El Sub Total Descuento es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "total_descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDescuento;
    @Min(value = 0, message = "El Total Pagado es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "total_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPagado;
    @Min(value = 0, message = "La entrega inicial es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "entrega_inicial", nullable = false, precision = 10, scale = 2)
    private BigDecimal entregaInicial;
    @Min(value = 0, message = "El Nro de cuotas es un numero negativo")
    @Basic(optional = false)
    @Column(name = "cuotas", nullable = false)
    private short cuotas;
    @Min(value = 0, message = "El monto de cada cuota es un monto muy bajo")
    @Basic(optional = false)
    @Column(name = "monto_cuota_igual", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoCuotaIgual;
    @Basic(optional = false)
    @Column(name = "saldado", nullable = false)
    private Character saldado;
    @Min(value = 1, message = "Seleccione por lo menos un item")
    @Basic(optional = false)
    @Column(name = "cantidad_items", nullable = false)
    private Short cantidadItems;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Motostock> motostocksCompra;
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Motostock> motostocksVenta;
    @Transient
    private String fechaOperacionString;
    @Transient
    private String fechaEntregaString;
    @Transient
    private Categoria codigoMax;
    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.ALL)
    private List<Credito> creditosTransaccion;

    public Transaccion() {
        this.codigo = new Categoria();
        this.factura = new Factura();
        this.comprador = new Persona();
        this.vendedor = new Persona();
        //Montos
        this.subTotalExentas = BigDecimal.ZERO;
        this.subTotalGravadas5 = BigDecimal.ZERO;
        this.subTotalGravadas10 = BigDecimal.ZERO;
        this.subTotal = BigDecimal.ZERO;
        this.netoSinIva5 = BigDecimal.ZERO;
        this.netoSinIva10 = BigDecimal.ZERO;
        this.descuento = new Float(0.00);
        this.totalDescuento = BigDecimal.ZERO;
        this.totalIva5 = BigDecimal.ZERO;
        this.totalIva10 = BigDecimal.ZERO;
        this.totalIva = BigDecimal.ZERO;
        this.totalPagado = BigDecimal.ZERO;
    }

    public Transaccion(Integer id) {
        this.id = id;
    }

    public Transaccion(Integer id, Categoria codigo, Date fechaOperacion, Date fechaEntrega, Persona vendedor, Persona comprador, Character anulado, BigDecimal subTotalExentas, BigDecimal subTotalGravadas10, BigDecimal subTotalGravadas5, BigDecimal subTotal, BigDecimal totalIva5, BigDecimal totalIva10, BigDecimal totalIva, Float descuento, BigDecimal total, BigDecimal totalDescuento, BigDecimal totalPagado, BigDecimal entregaInicial, short cuotas, BigDecimal montoCuotaIgual, Character saldado, short cantidadItems) {
        this.id = id;
        this.codigo = codigo;
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
        this.setCuotas((short) cuotas);
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

    public Short getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(Short cantidadItems) {
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
        return this.codigo.getDescripcion() + " " + this.getFactura().getNumero() + " " + this.getFechaOperacionString();
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

    /**
     * @param motostocksCompra the motostocksCompra to set
     */
    public void setMotostocksCompra(List<Motostock> motostocksCompra) {
        this.motostocksCompra = motostocksCompra;
    }

    /**
     * @param motostocksVenta the motostocksVenta to set
     */
    public void setMotostocksVenta(List<Motostock> motostocksVenta) {
        this.motostocksVenta = motostocksVenta;
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
     * @return the codigoMax
     */
    public Categoria getCodigoMax() {
        return codigoMax;
    }

    /**
     * @param codigoMax the codigoMax to set
     */
    public void setCodigoMax(Categoria codigoMax) {
        this.codigoMax = codigoMax;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(short cuotas) {
        this.cuotas = cuotas;
    }

    /**
     * @return the factura
     */
    public Factura getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}

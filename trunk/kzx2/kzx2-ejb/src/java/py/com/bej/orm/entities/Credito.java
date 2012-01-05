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
@Table(name = "credito", catalog = "bejdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credito.findAll", query = "SELECT c FROM Credito c"),
    @NamedQuery(name = "Credito.findById", query = "SELECT c FROM Credito c WHERE c.id = :id"),
    @NamedQuery(name = "Credito.findByCategoria", query = "SELECT c FROM Credito c WHERE c.categoria = :categoria"),
    @NamedQuery(name = "Credito.findByTransaccion", query = "SELECT c FROM Credito c WHERE c.transaccion = :transaccion"),
    @NamedQuery(name = "Credito.findByFechaInicio", query = "SELECT c FROM Credito c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Credito.findByFechaFin", query = "SELECT c FROM Credito c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "Credito.findBySistemaCredito", query = "SELECT c FROM Credito c WHERE c.sistemaCredito = :sistemaCredito"),
    @NamedQuery(name = "Credito.findByTan", query = "SELECT c FROM Credito c WHERE c.tan = :tan"),
    @NamedQuery(name = "Credito.findByTae", query = "SELECT c FROM Credito c WHERE c.tae = :tae"),
    @NamedQuery(name = "Credito.findByCapital", query = "SELECT c FROM Credito c WHERE c.capital = :capital"),
    @NamedQuery(name = "Credito.findByAmortizacion", query = "SELECT c FROM Credito c WHERE c.amortizacion = :amortizacion"),
    @NamedQuery(name = "Credito.findByTotalAmortizadoPagado", query = "SELECT c FROM Credito c WHERE c.totalAmortizadoPagado = :totalAmortizadoPagado"),
    @NamedQuery(name = "Credito.findByTotalInteresesPagado", query = "SELECT c FROM Credito c WHERE c.totalInteresesPagado = :totalInteresesPagado"),
    @NamedQuery(name = "Credito.findByTotalInteresesPagadoMulta", query = "SELECT c FROM Credito c WHERE c.totalInteresesPagadoMulta = :totalInteresesPagadoMulta"),
    @NamedQuery(name = "Credito.findByFechaUltimoPago", query = "SELECT c FROM Credito c WHERE c.fechaUltimoPago = :fechaUltimoPago"),
    @NamedQuery(name = "Credito.findByCuotasAtrasadas", query = "SELECT c FROM Credito c WHERE c.cuotasAtrasadas = :cuotasAtrasadas"),
    @NamedQuery(name = "Credito.findByEstado", query = "SELECT c FROM Credito c WHERE c.estado = :estado")})
public class Credito implements Serializable, WithId<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @JoinColumn(name = "categoria", referencedColumnName = "id", insertable = false, updatable = true)
    @ManyToOne
    private Categoria categoria;
    @Basic(optional = false)
    @JoinColumn(name = "transaccion", referencedColumnName = "id", insertable = false, updatable = true)
    @ManyToOne
    private Transaccion transaccion;
    @Basic(optional = false)
    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @JoinColumn(name = "sistema_credito", nullable = false)
    @ManyToOne
    private Categoria sistemaCredito;
    @Basic(optional = false)
    @Column(name = "tan", nullable = false)
    private float tan;
    @Basic(optional = false)
    @Column(name = "tae", nullable = false)
    private float tae;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "capital", nullable = false, precision = 11, scale = 2)
    private BigDecimal capital;
    @Basic(optional = false)
    @Column(name = "amortizacion", nullable = false)
    private short amortizacion;
    @Basic(optional = false)
    @Column(name = "credito_total", nullable = false)
    private BigDecimal creditoTotal;
    @Basic(optional = false)
    @Column(name = "total_amortizado_pagado", nullable = false, precision = 11, scale = 2)
    private BigDecimal totalAmortizadoPagado;
    @Basic(optional = false)
    @Column(name = "total_intereses_pagado", nullable = false, precision = 11, scale = 2)
    private BigDecimal totalInteresesPagado;
    @Column(name = "total_intereses_pagado_multa", precision = 11, scale = 2)
    private BigDecimal totalInteresesPagadoMulta;
    @Column(name = "fecha_ultimo_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimoPago;
    @Column(name = "cuotas_atrasadas")
    private Short cuotasAtrasadas;
    @Basic(optional = false)
    @JoinColumn(name = "estado", insertable = false, updatable = true)
    private Categoria estado;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "credito")
    private List<Financiacion> financiacions;

    public Credito() {
    }

    public Credito(Integer id) {
        this.id = id;
    }

    public Credito(Integer id, Categoria categoria, Transaccion transaccion, Date fechaInicio, Date fechaFin, Categoria sistemaCredito, float tan, float tae, BigDecimal capital, short amortizacion, BigDecimal creditoTotal, BigDecimal totalAmortizadoPagado, BigDecimal totalInteresesPagado, BigDecimal totalInteresesPagadoMulta, Date fechaUltimoPago, Short cuotasAtrasadas, Categoria estado, Character activo, Date ultimaModificacion) {
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
        this.totalInteresesPagadoMulta = totalInteresesPagadoMulta;
        this.fechaUltimoPago = fechaUltimoPago;
        this.cuotasAtrasadas = cuotasAtrasadas;
        this.estado = estado;
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
        return "py.com.bej.orm.entities.Credito[ id=" + id + " ]";
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
        return this.id + " " + this.categoria.getDescripcion();
    }

    /**
     * @return the financiacions
     */
    public List<Financiacion> getFinanciacions() {
        return financiacions;
    }

    /**
     * @return the creditoTotal
     */
    public BigDecimal getCreditoTotal() {
        return creditoTotal;
    }

    /**
     * @param creditoTotal the creditoTotal to set
     */
    public void setCreditoTotal(BigDecimal creditoTotal) {
        this.creditoTotal = creditoTotal;
    }
}

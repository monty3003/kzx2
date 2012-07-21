/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;
import py.com.bej.orm.utils.ConversorDeNumeroALetra;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "pago", catalog = "bej")
@XmlRootElement
public class Pago extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "credito", referencedColumnName = "id", insertable = false, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Credito credito;
    @Column(name = "n_documento", nullable = false)
    private String numeroDocumento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_pagado", nullable = false, precision = 11, scale = 2)
    private BigDecimal totalPagado;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @Column(name = "fechaCreacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "usuario_creacion", referencedColumnName = "id", insertable = true, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioCreacion;
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id", insertable = true, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioModificacion;
    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL)
    private List<DetallePago> detalle;
    @Transient
    private String totalPagadoString;
    @Transient
    private String fechaString;
    @Transient
    private String cliente;
    @Transient
    private String membrete;
    @Transient
    private String empresa;
    @Transient
    private String ruc;
    @Transient
    private String saldoActualString;

    public Pago() {
        this.credito = new Credito();
    }

    public Pago(Integer id) {
        this.id = id;
    }

    public Pago(Integer id, Date fecha, Credito credito, String numeroDocumento, BigDecimal totalPagado, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.fecha = fecha;
        this.credito = credito;
        this.numeroDocumento = numeroDocumento;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
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
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Pago[ id=" + id + "," + numeroDocumento + " ]";
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
        return this.id + " " + this.getCredito().getlabel();
    }

    public List<DetallePago> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetallePago> detalle) {
        this.detalle = detalle;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTotalPagadoString() {
        if (totalPagadoString == null) {
            totalPagadoString = new ConversorDeNumeroALetra().getStringOfCurrency(totalPagado.floatValue());
        }
        return totalPagadoString;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMembrete() {
        return membrete;
    }

    public void setMembrete(String membrete) {
        this.membrete = membrete;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getFechaString() {
        if (fechaString == null) {
            fechaString = Conversor.deDateToString(fecha, ConfiguracionEnum.DATETIME_PATTERN.getSymbol());
        }
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

    public String getSaldoActualString() {
        return saldoActualString;
    }

    public void setSaldoActualString(String saldoActualString) {
        this.saldoActualString = saldoActualString;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(Usuario usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Usuario getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Usuario usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
}

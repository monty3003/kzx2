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
import py.com.bej.orm.utils.Conversor;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "credito", catalog = "bej")
@XmlRootElement
public class Credito extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "categoria", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "transaccion", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Transaccion transaccion;
    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "sistema_credito", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Categoria sistemaCredito;
    @Column(name = "tan", nullable = false)
    private float tan;
    @Column(name = "tae", nullable = false)
    private float tae;
    @Column(name = "interes_moratorio", nullable = false)
    private float interesMoratorio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capital", nullable = false, precision = 11, scale = 2)
    private BigDecimal capital;
    @Column(name = "amortizacion", nullable = false)
    private short amortizacion;
    @Column(name = "credito_total", nullable = false)
    private BigDecimal creditoTotal;
    @JoinColumn(name = "garante", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private Persona garante;
    @Column(name = "total_amortizado_pagado", precision = 11, scale = 2)
    private BigDecimal totalAmortizadoPagado;
    @Column(name = "total_intereses_pagado", precision = 11, scale = 2)
    private BigDecimal totalInteresesPagado;
    @Column(name = "total_intereses_pagado_multa", precision = 11, scale = 2)
    private BigDecimal totalInteresesPagadoMulta;
    @Column(name = "fecha_ultimo_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimoPago;
    @Column(name = "cuotas_atrasadas")
    private Short cuotasAtrasadas;
    @JoinColumn(name = "estado", nullable = false)
    @ManyToOne(optional = false)
    private Categoria estado;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL)
    private List<Financiacion> financiacions;
    @Transient
    private BigDecimal saldoActual;
    @Transient
    private String fechaInicioString;
    @Transient
    private String tanString;
    @Transient
    private String taeString;
    @Transient
    private String interesMoratorioString;
    @Transient
    private Boolean selected;

    public Credito() {
        this.categoria = new Categoria();
        this.estado = new Categoria();
        this.sistemaCredito = new Categoria();
        this.transaccion = new Transaccion();
        this.garante = new Persona();
    }

    public Credito(Integer id) {
        this.id = id;
    }

    public Credito(Integer id, Categoria categoria, Transaccion transaccion, Date fechaInicio, Date fechaFin, Categoria sistemaCredito, float tan, float tae, float interesMoratorio, BigDecimal capital, short amortizacion, BigDecimal creditoTotal, BigDecimal totalAmortizadoPagado, BigDecimal totalInteresesPagado, BigDecimal totalInteresesPagadoMulta, Date fechaUltimoPago, Short cuotasAtrasadas, Categoria estado, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.categoria = categoria;
        this.transaccion = transaccion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sistemaCredito = sistemaCredito;
        this.tan = tan;
        this.tae = tae;
        this.interesMoratorio = interesMoratorio;
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

    /**
     * @param financiacions the financiacions to set
     */
    public void setFinanciacions(List<Financiacion> financiacions) {
        for (Financiacion fn : financiacions) {
            fn.setCredito(this);
        }
        this.financiacions = financiacions;
    }

    public Persona getGarante() {
        return garante;
    }

    public void setGarante(Persona garante) {
        this.garante = garante;
    }

    /**
     * @return the saldoActual
     */
    public BigDecimal getSaldoActual() {
        if (saldoActual == null) {
            saldoActual = creditoTotal.subtract(totalAmortizadoPagado.add(totalInteresesPagado));
        }
        return saldoActual;
    }

    public String getFechaInicioString() {
        if (fechaInicioString == null) {
            fechaInicioString = Conversor.deDateToString(fechaInicio, "EEEEE, d 'de' MMMMM   'de' yyyy");
        }
        return fechaInicioString;
    }

    /**
     * @return the interesMoratorio
     */
    public float getInteresMoratorio() {
        return interesMoratorio;
    }

    /**
     * @param interesMoratorio the interesMoratorio to set
     */
    public void setInteresMoratorio(float interesMoratorio) {
        this.interesMoratorio = interesMoratorio;
    }

    /**
     * @return the tanString
     */
    public String getTanString() {
        if (tanString == null) {
            tanString = Conversor.numberToStringPattern(this.tan * 100) + "%";
        }
        return tanString;
    }

    /**
     * @return the taeString
     */
    public String getTaeString() {
        if (taeString == null) {
            taeString = Conversor.numberToStringPattern(this.tae * 100) + "%";
        }
        return taeString;
    }

    public String getInteresMoratorioString() {
        if (interesMoratorioString == null) {
            interesMoratorioString = Conversor.numberToStringPattern(this.interesMoratorio * 100) + "%";
        }
        return interesMoratorioString;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
}

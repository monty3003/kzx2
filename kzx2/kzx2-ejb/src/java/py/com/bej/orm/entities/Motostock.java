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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "motostock", catalog = "bej")
@XmlRootElement
public class Motostock extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "id_anterior", nullable = true)
    private Integer idAnterior;
    @JoinColumn(name = "moto", referencedColumnName = "codigo", nullable = false)
    @ManyToOne(optional = false)
    private Moto moto;
    @Column(name = "motor", length = 25)
    private String motor;
    @Size(min = 6, max = 25, message = "Chasis: Ingrese un valor entre 6 y 25 letras")
    @Column(name = "chasis", nullable = false, length = 25)
    private String chasis;
    @JoinColumn(name = "compra", referencedColumnName = "id", insertable = true, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Transaccion compra;
    @JoinColumn(name = "venta", referencedColumnName = "id", updatable = true, nullable = true)
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private Transaccion venta;
    @Min(value = 10, message = "Precio: Ingrese un valor positivo")
    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;
    @Column(name = "precio_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioBase;
    @Column(name = "precio_contado", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioContado;
    @Column(name = "gravamen", nullable = false, scale = 2)
    private Float gravamen;
    @JoinColumn(name = "ubicacion", referencedColumnName = "id", insertable = false, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;
    @JoinColumn(name = "plan", referencedColumnName = "id", insertable = false, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Plan plan;
    @JoinColumn(name = "estado", referencedColumnName = "id", insertable = false, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Categoria estado;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @Transient
    private Boolean selected;

    public Motostock() {
        this.moto = new Moto();
        this.ubicacion = new Ubicacion();
        this.compra = new Transaccion();
        this.plan = new Plan();
    }

    public Motostock(Integer id) {
        this.id = id;
    }

    public Motostock(Integer id, Moto moto, String chasis, Transaccion compra, Transaccion venta, BigDecimal costo, BigDecimal precioBase, BigDecimal precioContado, Float gravamen, Ubicacion ubicacion, Character activo) {
        this.id = id;
        this.moto = moto;
        this.chasis = chasis;
        this.compra = compra;
        this.venta = venta;
        this.costo = costo;
        this.precioBase = precioBase;
        this.precioContado = precioContado;
        this.gravamen = gravamen;
        this.ubicacion = ubicacion;
        this.activo = activo;
    }

    public Motostock(Integer id, Moto moto, String motor, String chasis, Transaccion compra, Transaccion venta, BigDecimal costo, BigDecimal precioBase, BigDecimal precioContado, Float gravamen, Ubicacion ubicacion, Plan plan, Categoria estado, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.idAnterior = id;
        this.moto = moto;
        this.motor = motor;
        this.chasis = chasis;
        this.compra = compra;
        this.venta = venta;
        this.costo = costo;
        this.precioBase = precioBase;
        this.precioContado = precioContado;
        this.gravamen = gravamen;
        this.ubicacion = ubicacion;
        this.plan = plan;
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

    public Integer getIdAnterior() {
        return idAnterior;
    }

    public void setIdAnterior(Integer idAnterior) {
        this.idAnterior = idAnterior;
    }

    public String getMotor() {
        return motor != null ? motor.toUpperCase() : motor;
    }

    public void setMotor(String motor) {
        this.motor = motor != null ? motor.toUpperCase() : motor;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public BigDecimal getPrecioContado() {
        return precioContado;
    }

    public void setPrecioContado(BigDecimal precioContado) {
        this.precioContado = precioContado;
    }

    public Float getGravamen() {
        return gravamen;
    }

    public void setGravamen(Float gravamen) {
        this.gravamen = gravamen;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
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
        if (!(object instanceof Motostock)) {
            return false;
        }
        Motostock other = (Motostock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Motostock[ id=" + id + " ]";
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
        return "[" + this.getId() + "] " + this.chasis + " " + this.moto.getlabel();
    }

    /**
     * @return the moto
     */
    public Moto getMoto() {
        return moto;
    }

    /**
     * @param moto the moto to set
     */
    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    /**
     * @return the compra
     */
    public Transaccion getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(Transaccion compra) {
        this.compra = compra;
    }

    /**
     * @return the venta
     */
    public Transaccion getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Transaccion venta) {
        this.venta = venta;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Categoria getEstado() {
        return estado;
    }

    public void setEstado(Categoria estado) {
        this.estado = estado;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "Motostock", catalog = "bejdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Motostock.findAll", query = "SELECT m FROM Motostock m"),
    @NamedQuery(name = "Motostock.findById", query = "SELECT m FROM Motostock m WHERE m.id = :id"),
    @NamedQuery(name = "Motostock.findByMoto", query = "SELECT m FROM Motostock m WHERE m.moto = :moto"),
    @NamedQuery(name = "Motostock.findByMotor", query = "SELECT m FROM Motostock m WHERE m.motor = :motor"),
    @NamedQuery(name = "Motostock.findByChasis", query = "SELECT m FROM Motostock m WHERE m.chasis = :chasis"),
    @NamedQuery(name = "Motostock.findByCompra", query = "SELECT m FROM Motostock m WHERE m.compra = :compra"),
    @NamedQuery(name = "Motostock.findByVenta", query = "SELECT m FROM Motostock m WHERE m.venta = :venta"),
    @NamedQuery(name = "Motostock.findByCosto", query = "SELECT m FROM Motostock m WHERE m.costo = :costo"),
    @NamedQuery(name = "Motostock.findByPrecioVenta", query = "SELECT m FROM Motostock m WHERE m.precioVenta = :precioVenta"),
    @NamedQuery(name = "Motostock.findByUbicacion", query = "SELECT m FROM Motostock m WHERE m.ubicacion = :ubicacion")})
public class Motostock implements Serializable, WithId<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @JoinColumn(name = "moto", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne
    private Moto moto;
    @Column(name = "motor", length = 25)
    private String motor;
    @Basic(optional = false)
    @Column(name = "chasis", nullable = false, length = 25)
    private String chasis;
    @Basic(optional = false)
    @JoinColumn(name = "compra", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne
    private Transaccion compra;
    @JoinColumn(name = "venta", referencedColumnName = "id", nullable = true, insertable = true, updatable = true)
    @ManyToOne(optional = true)
    private Transaccion venta;
    @Basic(optional = false)
    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;
    @Basic(optional = false)
    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;
    @Basic(optional = false)
    @JoinColumn(name = "ubicacion", referencedColumnName = "id", insertable = false, updatable = true)
    @ManyToOne
    private Ubicacion ubicacion;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;

    public Motostock() {
    }

    public Motostock(Integer id) {
        this.id = id;
    }

    public Motostock(Integer id, Moto moto, String motor, String chasis, Transaccion compra, Transaccion venta, BigDecimal costo, BigDecimal precioVenta, Ubicacion ubicacion, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.moto = moto;
        this.motor = motor;
        this.chasis = chasis;
        this.compra = compra;
        this.venta = venta;
        this.costo = costo;
        this.precioVenta = precioVenta;
        this.ubicacion = ubicacion;
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

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
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

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
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
        return this.chasis + " " + this.moto.getlabel();
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
}

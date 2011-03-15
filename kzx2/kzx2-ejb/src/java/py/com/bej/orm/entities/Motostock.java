/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "Motostock")
public class Motostock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "moto", referencedColumnName = "codigo", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Moto moto;
    @Column(name = "motor")
    private String motor;
    @Basic(optional = false)
    @Column(name = "chasis")
    private String chasis;
    @JoinColumn(name = "compra", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Transaccion compra;
    @JoinColumn(name = "venta", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Transaccion venta;
    @Basic(optional = false)
    @Column(name = "costo")
    private BigDecimal costo;
    @Basic(optional = false)
    @Column(name = "precio_venta")
    private BigDecimal precioVenta;
    @JoinColumn(name = "ubicacion", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;

    public Motostock() {
    }

    public Motostock(Integer id) {
        this.id = id;
    }

    public Motostock(Integer id, Moto moto, String motor, String chasis, Transaccion compra, Transaccion venta, BigDecimal costo, BigDecimal precioVenta, Ubicacion ubicacion) {
        this.id = id;
        this.moto = moto;
        this.motor = motor;
        this.chasis = chasis;
        this.compra = compra;
        this.venta = venta;
        this.costo = costo;
        this.precioVenta = precioVenta;
        this.ubicacion = ubicacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
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

    public Transaccion getCompra() {
        return compra;
    }

    public void setCompra(Transaccion compra) {
        this.compra = compra;
    }

    public Transaccion getVenta() {
        return venta;
    }

    public void setVenta(Transaccion venta) {
        this.venta = venta;
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
        return "py.com.bej.orm.entities.Motostock[id=" + id + "]";
    }
}

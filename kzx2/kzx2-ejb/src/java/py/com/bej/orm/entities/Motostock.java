/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.bej.orm.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "motostock")
public class Motostock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "motor")
    private String motor;
    @Basic(optional = false)
    @Column(name = "chasis")
    private String chasis;
    @Basic(optional = false)
    @Column(name = "compra")
    private Integer compra;
    @Column(name = "venta")
    private Integer venta;
    @Basic(optional = false)
    @Column(name = "costoGuarani")
    private float costoGuarani;
    @Column(name = "ventaGuarani")
    private Float ventaGuarani;
    @Column(name = "cambioMoneda")
    private Float cambioMoneda;
    @Column(name = "costoDolar")
    private Float costoDolar;
    @Column(name = "ventaDolar")
    private Float ventaDolar;
    @Basic(optional = false)
    @Column(name = "vendido")
    private char vendido;
    @Basic(optional = false)
    @Column(name = "ubicacion")
    private Integer ubicacion;

    public Motostock() {
    }

    public Motostock(Integer id) {
        this.id = id;
    }

    public Motostock(Integer id, String codigo, String chasis, Integer compra, float costoGuarani, char vendido, Integer ubicacion) {
        this.id = id;
        this.codigo = codigo;
        this.chasis = chasis;
        this.compra = compra;
        this.costoGuarani = costoGuarani;
        this.vendido = vendido;
        this.ubicacion = ubicacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Integer getCompra() {
        return compra;
    }

    public void setCompra(Integer compra) {
        this.compra = compra;
    }

    public Integer getVenta() {
        return venta;
    }

    public void setVenta(Integer venta) {
        this.venta = venta;
    }

    public float getCostoGuarani() {
        return costoGuarani;
    }

    public void setCostoGuarani(float costoGuarani) {
        this.costoGuarani = costoGuarani;
    }

    public Float getVentaGuarani() {
        return ventaGuarani;
    }

    public void setVentaGuarani(Float ventaGuarani) {
        this.ventaGuarani = ventaGuarani;
    }

    public Float getCambioMoneda() {
        return cambioMoneda;
    }

    public void setCambioMoneda(Float cambioMoneda) {
        this.cambioMoneda = cambioMoneda;
    }

    public Float getCostoDolar() {
        return costoDolar;
    }

    public void setCostoDolar(Float costoDolar) {
        this.costoDolar = costoDolar;
    }

    public Float getVentaDolar() {
        return ventaDolar;
    }

    public void setVentaDolar(Float ventaDolar) {
        this.ventaDolar = ventaDolar;
    }

    public char getVendido() {
        return vendido;
    }

    public void setVendido(char vendido) {
        this.vendido = vendido;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
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

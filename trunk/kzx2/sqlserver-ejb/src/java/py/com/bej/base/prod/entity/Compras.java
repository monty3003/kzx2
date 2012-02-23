/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "COMPRAS", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compras.findAll", query = "SELECT c FROM Compras c"),
    @NamedQuery(name = "Compras.findByIdCompras", query = "SELECT c FROM Compras c WHERE c.idCompras = :idCompras"),
    @NamedQuery(name = "Compras.findByNumeroFactura", query = "SELECT c FROM Compras c WHERE c.numeroFactura = :numeroFactura"),
    @NamedQuery(name = "Compras.findByFecha", query = "SELECT c FROM Compras c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Compras.findByCodProveedor", query = "SELECT c FROM Compras c WHERE c.codProveedor = :codProveedor"),
    @NamedQuery(name = "Compras.findBySubTotal", query = "SELECT c FROM Compras c WHERE c.subTotal = :subTotal"),
    @NamedQuery(name = "Compras.findByIvaCf", query = "SELECT c FROM Compras c WHERE c.ivaCf = :ivaCf"),
    @NamedQuery(name = "Compras.findByMontoTotal", query = "SELECT c FROM Compras c WHERE c.montoTotal = :montoTotal")})
public class Compras implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdCompras", nullable = false)
    private Integer idCompras;
    @Column(name = "NumeroFactura")
    private Integer numeroFactura;
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 15)
    @Column(name = "CodProveedor", length = 15)
    private String codProveedor;
    @Column(name = "SUB_TOTAL")
    private Integer subTotal;
    @Column(name = "IVA_CF")
    private Integer ivaCf;
    @Column(name = "MontoTotal")
    private Integer montoTotal;

    public Compras() {
    }

    public Compras(Integer idCompras) {
        this.idCompras = idCompras;
    }

    public Integer getIdCompras() {
        return idCompras;
    }

    public void setIdCompras(Integer idCompras) {
        this.idCompras = idCompras;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getIvaCf() {
        return ivaCf;
    }

    public void setIvaCf(Integer ivaCf) {
        this.ivaCf = ivaCf;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompras != null ? idCompras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compras)) {
            return false;
        }
        Compras other = (Compras) object;
        if ((this.idCompras == null && other.idCompras != null) || (this.idCompras != null && !this.idCompras.equals(other.idCompras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Compras[ idCompras=" + idCompras + " ]";
    }
    
}

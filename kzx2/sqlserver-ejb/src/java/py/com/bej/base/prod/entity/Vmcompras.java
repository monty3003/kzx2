/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "VMCOMPRAS", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmcompras.findAll", query = "SELECT v FROM Vmcompras v"),
    @NamedQuery(name = "Vmcompras.findByIdCompras", query = "SELECT v FROM Vmcompras v WHERE v.idCompras = :idCompras"),
    @NamedQuery(name = "Vmcompras.findByNumeroFactura", query = "SELECT v FROM Vmcompras v WHERE v.numeroFactura = :numeroFactura"),
    @NamedQuery(name = "Vmcompras.findByFecha", query = "SELECT v FROM Vmcompras v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Vmcompras.findByCodProveedor", query = "SELECT v FROM Vmcompras v WHERE v.codProveedor = :codProveedor"),
    @NamedQuery(name = "Vmcompras.findBySubTotal", query = "SELECT v FROM Vmcompras v WHERE v.subTotal = :subTotal"),
    @NamedQuery(name = "Vmcompras.findByIvaCf", query = "SELECT v FROM Vmcompras v WHERE v.ivaCf = :ivaCf"),
    @NamedQuery(name = "Vmcompras.findByMontoTotal", query = "SELECT v FROM Vmcompras v WHERE v.montoTotal = :montoTotal"),
    @NamedQuery(name = "Vmcompras.findByGuardado", query = "SELECT v FROM Vmcompras v WHERE v.guardado = :guardado"),
    @NamedQuery(name = "Vmcompras.findByAnulado", query = "SELECT v FROM Vmcompras v WHERE v.anulado = :anulado"),
    @NamedQuery(name = "Vmcompras.findByEntrega", query = "SELECT v FROM Vmcompras v WHERE v.entrega = :entrega"),
    @NamedQuery(name = "Vmcompras.findByCuotas", query = "SELECT v FROM Vmcompras v WHERE v.cuotas = :cuotas"),
    @NamedQuery(name = "Vmcompras.findByPrimerVenc", query = "SELECT v FROM Vmcompras v WHERE v.primerVenc = :primerVenc")})
public class Vmcompras implements Serializable {

    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;
    @Column(name = "primer_venc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date primerVenc;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdCompras", nullable = false)
    private Integer idCompras;
    @Column(name = "NumeroFactura")
    private Integer numeroFactura;
    @Size(max = 15)
    @Column(name = "CodProveedor", length = 15)
    private String codProveedor;
    @Column(name = "SUB_TOTAL")
    private Integer subTotal;
    @Column(name = "IVA_CF")
    private Integer ivaCf;
    @Column(name = "MontoTotal")
    private Integer montoTotal;
    @Column(name = "guardado")
    private Boolean guardado;
    @Column(name = "anulado")
    private Boolean anulado;
    @Column(name = "entrega")
    private Boolean entrega;
    @Column(name = "cuotas")
    private Short cuotas;
    @OneToMany(mappedBy = "nComp")
    private Collection<Vmmotostock> vmmotostockCollection;

    public Vmcompras() {
    }

    public Vmcompras(Integer idCompras) {
        this.idCompras = idCompras;
    }

    public Vmcompras(Integer idCompras, byte[] sSMATimeStamp) {
        this.idCompras = idCompras;
        this.sSMATimeStamp = sSMATimeStamp;
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

    public Boolean getGuardado() {
        return guardado;
    }

    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    public Boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(Boolean anulado) {
        this.anulado = anulado;
    }

    public byte[] getSSMATimeStamp() {
        return sSMATimeStamp;
    }

    public void setSSMATimeStamp(byte[] sSMATimeStamp) {
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Boolean getEntrega() {
        return entrega;
    }

    public void setEntrega(Boolean entrega) {
        this.entrega = entrega;
    }

    public Short getCuotas() {
        return cuotas;
    }

    public void setCuotas(Short cuotas) {
        this.cuotas = cuotas;
    }

    public Date getPrimerVenc() {
        return primerVenc;
    }

    public void setPrimerVenc(Date primerVenc) {
        this.primerVenc = primerVenc;
    }

    @XmlTransient
    public Collection<Vmmotostock> getVmmotostockCollection() {
        return vmmotostockCollection;
    }

    public void setVmmotostockCollection(Collection<Vmmotostock> vmmotostockCollection) {
        this.vmmotostockCollection = vmmotostockCollection;
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
        if (!(object instanceof Vmcompras)) {
            return false;
        }
        Vmcompras other = (Vmcompras) object;
        if ((this.idCompras == null && other.idCompras != null) || (this.idCompras != null && !this.idCompras.equals(other.idCompras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmcompras[ idCompras=" + idCompras + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

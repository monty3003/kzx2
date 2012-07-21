/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "VMINTERESES", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmintereses.findAll", query = "SELECT v FROM Vmintereses v"),
    @NamedQuery(name = "Vmintereses.findByIDcobro", query = "SELECT v FROM Vmintereses v WHERE v.iDcobro = :iDcobro"),
    @NamedQuery(name = "Vmintereses.findByNumeroRecibocrobro", query = "SELECT v FROM Vmintereses v WHERE v.numeroRecibocrobro = :numeroRecibocrobro"),
    @NamedQuery(name = "Vmintereses.findByTransaccion", query = "SELECT v FROM Vmintereses v WHERE v.transaccion = :transaccion"),
    @NamedQuery(name = "Vmintereses.findByConceptocobro", query = "SELECT v FROM Vmintereses v WHERE v.conceptocobro = :conceptocobro"),
    @NamedQuery(name = "Vmintereses.findByMonto", query = "SELECT v FROM Vmintereses v WHERE v.monto = :monto"),
    @NamedQuery(name = "Vmintereses.findByGuardado", query = "SELECT v FROM Vmintereses v WHERE v.guardado = :guardado")})
public class Vmintereses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDcobro", nullable = false)
    private Integer iDcobro;
    @Column(name = "NumeroRecibocrobro")
    private Integer numeroRecibocrobro;
    @Size(max = 20)
    @Column(name = "TRANSACCION", length = 20)
    private String transaccion;
    @Size(max = 1073741823)
    @Column(name = "CONCEPTOCOBRO", length = 1073741823)
    private String conceptocobro;
    @Column(name = "MONTO")
    private Integer monto;
    @Column(name = "GUARDADO")
    private Boolean guardado;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;

    public Vmintereses() {
    }

    public Vmintereses(Integer iDcobro, Integer numeroRecibocrobro, String transaccion, String conceptocobro, Integer monto, Boolean guardado) {
        this.iDcobro = iDcobro;
        this.numeroRecibocrobro = numeroRecibocrobro;
        this.transaccion = transaccion;
        this.conceptocobro = conceptocobro;
        this.monto = monto;
        this.guardado = guardado;
    }

    public Vmintereses(Integer iDcobro) {
        this.iDcobro = iDcobro;
    }

    public Vmintereses(Integer iDcobro, byte[] sSMATimeStamp) {
        this.iDcobro = iDcobro;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Integer getIDcobro() {
        return iDcobro;
    }

    public void setIDcobro(Integer iDcobro) {
        this.iDcobro = iDcobro;
    }

    public Integer getNumeroRecibocrobro() {
        return numeroRecibocrobro;
    }

    public void setNumeroRecibocrobro(Integer numeroRecibocrobro) {
        this.numeroRecibocrobro = numeroRecibocrobro;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getConceptocobro() {
        return conceptocobro;
    }

    public void setConceptocobro(String conceptocobro) {
        this.conceptocobro = conceptocobro;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Boolean getGuardado() {
        return guardado;
    }

    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    public byte[] getSSMATimeStamp() {
        return sSMATimeStamp;
    }

    public void setSSMATimeStamp(byte[] sSMATimeStamp) {
        this.sSMATimeStamp = sSMATimeStamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDcobro != null ? iDcobro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vmintereses)) {
            return false;
        }
        Vmintereses other = (Vmintereses) object;
        if ((this.iDcobro == null && other.iDcobro != null) || (this.iDcobro != null && !this.iDcobro.equals(other.iDcobro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmintereses[ iDcobro=" + iDcobro + " ]";
    }
}

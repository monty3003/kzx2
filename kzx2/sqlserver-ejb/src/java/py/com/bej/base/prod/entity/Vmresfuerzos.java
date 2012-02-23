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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "VMRESFUERZOS", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmresfuerzos.findAll", query = "SELECT v FROM Vmresfuerzos v"),
    @NamedQuery(name = "Vmresfuerzos.findByIdResfuerzo", query = "SELECT v FROM Vmresfuerzos v WHERE v.idResfuerzo = :idResfuerzo"),
    @NamedQuery(name = "Vmresfuerzos.findByFecha", query = "SELECT v FROM Vmresfuerzos v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Vmresfuerzos.findByCodEmpleado", query = "SELECT v FROM Vmresfuerzos v WHERE v.codEmpleado = :codEmpleado"),
    @NamedQuery(name = "Vmresfuerzos.findByMontoResfuerzo", query = "SELECT v FROM Vmresfuerzos v WHERE v.montoResfuerzo = :montoResfuerzo"),
    @NamedQuery(name = "Vmresfuerzos.findByGuardado", query = "SELECT v FROM Vmresfuerzos v WHERE v.guardado = :guardado"),
    @NamedQuery(name = "Vmresfuerzos.findByAnulado", query = "SELECT v FROM Vmresfuerzos v WHERE v.anulado = :anulado")})
public class Vmresfuerzos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdResfuerzo", nullable = false)
    private Integer idResfuerzo;
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "CodEmpleado")
    private Integer codEmpleado;
    @Column(name = "MontoResfuerzo")
    private Integer montoResfuerzo;
    @Column(name = "Guardado")
    private Boolean guardado;
    @Column(name = "Anulado")
    private Boolean anulado;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;
    @JoinColumn(name = "IdVentas", referencedColumnName = "IdVenta")
    @ManyToOne
    private Vmventamotos idVentas;

    public Vmresfuerzos() {
    }

    public Vmresfuerzos(Integer idResfuerzo) {
        this.idResfuerzo = idResfuerzo;
    }

    public Vmresfuerzos(Integer idResfuerzo, byte[] sSMATimeStamp) {
        this.idResfuerzo = idResfuerzo;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Integer getIdResfuerzo() {
        return idResfuerzo;
    }

    public void setIdResfuerzo(Integer idResfuerzo) {
        this.idResfuerzo = idResfuerzo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(Integer codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public Integer getMontoResfuerzo() {
        return montoResfuerzo;
    }

    public void setMontoResfuerzo(Integer montoResfuerzo) {
        this.montoResfuerzo = montoResfuerzo;
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

    public Vmventamotos getIdVentas() {
        return idVentas;
    }

    public void setIdVentas(Vmventamotos idVentas) {
        this.idVentas = idVentas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResfuerzo != null ? idResfuerzo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vmresfuerzos)) {
            return false;
        }
        Vmresfuerzos other = (Vmresfuerzos) object;
        if ((this.idResfuerzo == null && other.idResfuerzo != null) || (this.idResfuerzo != null && !this.idResfuerzo.equals(other.idResfuerzo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmresfuerzos[ idResfuerzo=" + idResfuerzo + " ]";
    }
    
}

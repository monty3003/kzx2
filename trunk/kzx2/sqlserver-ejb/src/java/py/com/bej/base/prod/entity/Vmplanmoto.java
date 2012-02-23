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
@Table(name = "VMPLANMOTO", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmplanmoto.findAll", query = "SELECT v FROM Vmplanmoto v"),
    @NamedQuery(name = "Vmplanmoto.findByIdPlanMoto", query = "SELECT v FROM Vmplanmoto v WHERE v.idPlanMoto = :idPlanMoto"),
    @NamedQuery(name = "Vmplanmoto.findByCuotaNumero", query = "SELECT v FROM Vmplanmoto v WHERE v.cuotaNumero = :cuotaNumero"),
    @NamedQuery(name = "Vmplanmoto.findByMontoCuota", query = "SELECT v FROM Vmplanmoto v WHERE v.montoCuota = :montoCuota"),
    @NamedQuery(name = "Vmplanmoto.findByMontoInteresMensual", query = "SELECT v FROM Vmplanmoto v WHERE v.montoInteresMensual = :montoInteresMensual"),
    @NamedQuery(name = "Vmplanmoto.findByFechaVencimiento", query = "SELECT v FROM Vmplanmoto v WHERE v.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "Vmplanmoto.findByFechaPago", query = "SELECT v FROM Vmplanmoto v WHERE v.fechaPago = :fechaPago"),
    @NamedQuery(name = "Vmplanmoto.findByGuardado", query = "SELECT v FROM Vmplanmoto v WHERE v.guardado = :guardado"),
    @NamedQuery(name = "Vmplanmoto.findByAnulado", query = "SELECT v FROM Vmplanmoto v WHERE v.anulado = :anulado")})
public class Vmplanmoto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdPlanMoto", nullable = false)
    private Integer idPlanMoto;
    @Column(name = "CuotaNumero")
    private Integer cuotaNumero;
    @Column(name = "MontoCuota")
    private Integer montoCuota;
    @Column(name = "MontoInteresMensual")
    private Integer montoInteresMensual;
    @Column(name = "FechaVencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Column(name = "FechaPago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Column(name = "Guardado")
    private Boolean guardado;
    @Column(name = "Anulado")
    private Boolean anulado;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;
    @JoinColumn(name = "IdVenta", referencedColumnName = "IdVenta")
    @ManyToOne
    private Vmventamotos idVenta;

    public Vmplanmoto() {
    }

    public Vmplanmoto(Integer idPlanMoto) {
        this.idPlanMoto = idPlanMoto;
    }

    public Vmplanmoto(Integer idPlanMoto, byte[] sSMATimeStamp) {
        this.idPlanMoto = idPlanMoto;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Integer getIdPlanMoto() {
        return idPlanMoto;
    }

    public void setIdPlanMoto(Integer idPlanMoto) {
        this.idPlanMoto = idPlanMoto;
    }

    public Integer getCuotaNumero() {
        return cuotaNumero;
    }

    public void setCuotaNumero(Integer cuotaNumero) {
        this.cuotaNumero = cuotaNumero;
    }

    public Integer getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(Integer montoCuota) {
        this.montoCuota = montoCuota;
    }

    public Integer getMontoInteresMensual() {
        return montoInteresMensual;
    }

    public void setMontoInteresMensual(Integer montoInteresMensual) {
        this.montoInteresMensual = montoInteresMensual;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
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

    public Vmventamotos getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Vmventamotos idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanMoto != null ? idPlanMoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vmplanmoto)) {
            return false;
        }
        Vmplanmoto other = (Vmplanmoto) object;
        if ((this.idPlanMoto == null && other.idPlanMoto != null) || (this.idPlanMoto != null && !this.idPlanMoto.equals(other.idPlanMoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmplanmoto[ idPlanMoto=" + idPlanMoto + " ]";
    }
    
}

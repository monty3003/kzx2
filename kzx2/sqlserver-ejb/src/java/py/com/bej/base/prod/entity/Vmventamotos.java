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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "VMVENTAMOTOS", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmventamotos.findAll", query = "SELECT v FROM Vmventamotos v"),
    @NamedQuery(name = "Vmventamotos.findByIdVenta", query = "SELECT v FROM Vmventamotos v WHERE v.idVenta = :idVenta"),
    @NamedQuery(name = "Vmventamotos.findByIdTransaccion", query = "SELECT v FROM Vmventamotos v WHERE v.idTransaccion = :idTransaccion"),
    @NamedQuery(name = "Vmventamotos.findByCedulaRuc", query = "SELECT v FROM Vmventamotos v WHERE v.cedulaRuc = :cedulaRuc"),
    @NamedQuery(name = "Vmventamotos.findByCodigoEmpleado", query = "SELECT v FROM Vmventamotos v WHERE v.codigoEmpleado = :codigoEmpleado"),
    @NamedQuery(name = "Vmventamotos.findByFechaVenta", query = "SELECT v FROM Vmventamotos v WHERE v.fechaVenta = :fechaVenta"),
    @NamedQuery(name = "Vmventamotos.findByPlan", query = "SELECT v FROM Vmventamotos v WHERE v.plan = :plan"),
    @NamedQuery(name = "Vmventamotos.findByPrecioContado", query = "SELECT v FROM Vmventamotos v WHERE v.precioContado = :precioContado"),
    @NamedQuery(name = "Vmventamotos.findByPrecioMoto", query = "SELECT v FROM Vmventamotos v WHERE v.precioMoto = :precioMoto"),
    @NamedQuery(name = "Vmventamotos.findByEntregaMoto", query = "SELECT v FROM Vmventamotos v WHERE v.entregaMoto = :entregaMoto"),
    @NamedQuery(name = "Vmventamotos.findByCompResfuerzo", query = "SELECT v FROM Vmventamotos v WHERE v.compResfuerzo = :compResfuerzo"),
    @NamedQuery(name = "Vmventamotos.findByCompFecha", query = "SELECT v FROM Vmventamotos v WHERE v.compFecha = :compFecha"),
    @NamedQuery(name = "Vmventamotos.findByCompObservacion", query = "SELECT v FROM Vmventamotos v WHERE v.compObservacion = :compObservacion"),
    @NamedQuery(name = "Vmventamotos.findByConCompromiso", query = "SELECT v FROM Vmventamotos v WHERE v.conCompromiso = :conCompromiso"),
    @NamedQuery(name = "Vmventamotos.findByRefuerzo", query = "SELECT v FROM Vmventamotos v WHERE v.refuerzo = :refuerzo"),
    @NamedQuery(name = "Vmventamotos.findBySaldoMoto", query = "SELECT v FROM Vmventamotos v WHERE v.saldoMoto = :saldoMoto"),
    @NamedQuery(name = "Vmventamotos.findByNumeroCuotas", query = "SELECT v FROM Vmventamotos v WHERE v.numeroCuotas = :numeroCuotas"),
    @NamedQuery(name = "Vmventamotos.findByMontoCuotas", query = "SELECT v FROM Vmventamotos v WHERE v.montoCuotas = :montoCuotas"),
    @NamedQuery(name = "Vmventamotos.findByTotalPagos", query = "SELECT v FROM Vmventamotos v WHERE v.totalPagos = :totalPagos"),
    @NamedQuery(name = "Vmventamotos.findBySalAcMoto", query = "SELECT v FROM Vmventamotos v WHERE v.salAcMoto = :salAcMoto"),
    @NamedQuery(name = "Vmventamotos.findByIdCodeudor", query = "SELECT v FROM Vmventamotos v WHERE v.idCodeudor = :idCodeudor"),
    @NamedQuery(name = "Vmventamotos.findByObservacion", query = "SELECT v FROM Vmventamotos v WHERE v.observacion = :observacion"),
    @NamedQuery(name = "Vmventamotos.findByGuardado", query = "SELECT v FROM Vmventamotos v WHERE v.guardado = :guardado"),
    @NamedQuery(name = "Vmventamotos.findByAnulado", query = "SELECT v FROM Vmventamotos v WHERE v.anulado = :anulado"),
    @NamedQuery(name = "Vmventamotos.findByCancelado", query = "SELECT v FROM Vmventamotos v WHERE v.cancelado = :cancelado"),
    @NamedQuery(name = "Vmventamotos.findByMontoLetras", query = "SELECT v FROM Vmventamotos v WHERE v.montoLetras = :montoLetras"),
    @NamedQuery(name = "Vmventamotos.findByChapa", query = "SELECT v FROM Vmventamotos v WHERE v.chapa = :chapa"),
    @NamedQuery(name = "Vmventamotos.findByEntregado", query = "SELECT v FROM Vmventamotos v WHERE v.entregado = :entregado"),
    @NamedQuery(name = "Vmventamotos.findByFechaEntrega", query = "SELECT v FROM Vmventamotos v WHERE v.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "Vmventamotos.findByUbicacion", query = "SELECT v FROM Vmventamotos v WHERE v.ubicacion = :ubicacion")})
public class Vmventamotos implements Serializable {

    @Column(name = "FechaVenta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;
    @Column(name = "CompFecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date compFecha;
    @Column(name = "FechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;
    @OneToMany(mappedBy = "idVentas")
    private Collection<Vmpagomotos> vmpagomotosCollection;
    @OneToMany(mappedBy = "idVenta")
    private Collection<Vmplanmoto> vmplanmotoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdVenta", nullable = false)
    private Integer idVenta;
    @Column(name = "IdTransaccion")
    private Integer idTransaccion;
    @Size(max = 14)
    @Column(name = "Cedula_Ruc", length = 14)
    private String cedulaRuc;
    @Column(name = "CodigoEmpleado")
    private Integer codigoEmpleado;
    @Column(name = "Plan")
    private Integer plan;
    @Column(name = "PrecioContado")
    private Integer precioContado;
    @Column(name = "PrecioMoto")
    private Integer precioMoto;
    @Column(name = "EntregaMoto")
    private Integer entregaMoto;
    @Column(name = "CompResfuerzo")
    private Integer compResfuerzo;
    @Size(max = 50)
    @Column(name = "CompObservacion", length = 50)
    private String compObservacion;
    @Column(name = "ConCompromiso")
    private Boolean conCompromiso;
    @Column(name = "Refuerzo")
    private Integer refuerzo;
    @Column(name = "SaldoMoto")
    private Integer saldoMoto;
    @Column(name = "NumeroCuotas")
    private Integer numeroCuotas;
    @Column(name = "MontoCuotas")
    private Integer montoCuotas;
    @Column(name = "TotalPagos")
    private Integer totalPagos;
    @Column(name = "SalAcMoto")
    private Integer salAcMoto;
    @Column(name = "IdCodeudor")
    private Integer idCodeudor;
    @Size(max = 250)
    @Column(name = "Observacion", length = 250)
    private String observacion;
    @Column(name = "Guardado")
    private Boolean guardado;
    @Column(name = "Anulado")
    private Boolean anulado;
    @Column(name = "Cancelado")
    private Boolean cancelado;
    @Size(max = 1073741823)
    @Column(name = "MontoLetras", length = 1073741823)
    private String montoLetras;
    @Column(name = "Chapa")
    private Boolean chapa;
    @Column(name = "Entregado")
    private Boolean entregado;
    @Column(name = "ubicacion")
    private Integer ubicacion;
    @OneToMany(mappedBy = "idVentas")
    private Collection<Vmresfuerzos> vmresfuerzosCollection;
    @JoinColumn(name = "IdMoto", referencedColumnName = "IdMoto")
    @ManyToOne
    private Vmmotostock idMoto;

    public Vmventamotos() {
    }

    public Vmventamotos(Integer IdVenta, Boolean Anulado, Boolean Cancelado, String Cedula_Ruc, Boolean Chapa, Integer CodigoEmpleado,
            Date CompFecha, String CompObservacion, Integer CompResfuerzo, Boolean ConCompromiso, Integer EntregaMoto, Boolean Entregado,
            Date FechaEntrega, Date FechaVenta, Boolean Guardado, Integer IdCodeudor, Integer IdTransaccion, Integer MontoCuotas,
            String MontoLetras, Integer NumeroCuotas, String Observacion, Integer PrecioContado, Integer PrecioMoto, Integer Refuerzo,
            Integer SalAcMoto, Integer SaldoMoto, Integer TotalPagos, Integer ubicacion, Integer IdMoto) {
        this.fechaVenta = FechaVenta;
        this.compFecha = CompFecha;
        this.fechaEntrega = FechaEntrega;
        this.idVenta = IdVenta;
        this.idTransaccion = IdTransaccion;
        this.cedulaRuc = Cedula_Ruc;
        this.codigoEmpleado = CodigoEmpleado;
        this.plan = 1;
        this.precioContado = PrecioContado;
        this.precioMoto = PrecioMoto;
        this.entregaMoto = EntregaMoto;
        this.compResfuerzo = CompResfuerzo;
        this.compObservacion = CompObservacion;
        this.conCompromiso = ConCompromiso;
        this.refuerzo = Refuerzo;
        this.saldoMoto = SaldoMoto;
        this.numeroCuotas = NumeroCuotas;
        this.montoCuotas = MontoCuotas;
        this.totalPagos = TotalPagos;
        this.salAcMoto = SalAcMoto;
        this.idCodeudor = IdCodeudor;
        this.observacion = Observacion;
        this.guardado = Guardado;
        this.anulado = Anulado;
        this.cancelado = Cancelado;
        this.montoLetras = MontoLetras;
        this.chapa = Chapa;
        this.entregado = Entregado;
        this.ubicacion = ubicacion;
        this.idMoto = new Vmmotostock(IdMoto);
    }

    public Vmventamotos(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Vmventamotos(Integer idVenta, byte[] sSMATimeStamp) {
        this.idVenta = idVenta;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getCedulaRuc() {
        return cedulaRuc;
    }

    public void setCedulaRuc(String cedulaRuc) {
        this.cedulaRuc = cedulaRuc;
    }

    public Integer getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(Integer codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public Integer getPrecioContado() {
        return precioContado;
    }

    public void setPrecioContado(Integer precioContado) {
        this.precioContado = precioContado;
    }

    public Integer getPrecioMoto() {
        return precioMoto;
    }

    public void setPrecioMoto(Integer precioMoto) {
        this.precioMoto = precioMoto;
    }

    public Integer getEntregaMoto() {
        return entregaMoto;
    }

    public void setEntregaMoto(Integer entregaMoto) {
        this.entregaMoto = entregaMoto;
    }

    public Integer getCompResfuerzo() {
        return compResfuerzo;
    }

    public void setCompResfuerzo(Integer compResfuerzo) {
        this.compResfuerzo = compResfuerzo;
    }

    public String getCompObservacion() {
        return compObservacion;
    }

    public void setCompObservacion(String compObservacion) {
        this.compObservacion = compObservacion;
    }

    public Boolean getConCompromiso() {
        return conCompromiso;
    }

    public void setConCompromiso(Boolean conCompromiso) {
        this.conCompromiso = conCompromiso;
    }

    public Integer getRefuerzo() {
        return refuerzo;
    }

    public void setRefuerzo(Integer refuerzo) {
        this.refuerzo = refuerzo;
    }

    public Integer getSaldoMoto() {
        return saldoMoto;
    }

    public void setSaldoMoto(Integer saldoMoto) {
        this.saldoMoto = saldoMoto;
    }

    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public Integer getMontoCuotas() {
        return montoCuotas;
    }

    public void setMontoCuotas(Integer montoCuotas) {
        this.montoCuotas = montoCuotas;
    }

    public Integer getTotalPagos() {
        return totalPagos;
    }

    public void setTotalPagos(Integer totalPagos) {
        this.totalPagos = totalPagos;
    }

    public Integer getSalAcMoto() {
        return salAcMoto;
    }

    public void setSalAcMoto(Integer salAcMoto) {
        this.salAcMoto = salAcMoto;
    }

    public Integer getIdCodeudor() {
        return idCodeudor;
    }

    public void setIdCodeudor(Integer idCodeudor) {
        this.idCodeudor = idCodeudor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public String getMontoLetras() {
        return montoLetras;
    }

    public void setMontoLetras(String montoLetras) {
        this.montoLetras = montoLetras;
    }

    public Boolean getChapa() {
        return chapa;
    }

    public void setChapa(Boolean chapa) {
        this.chapa = chapa;
    }

    public Boolean getEntregado() {
        return entregado;
    }

    public void setEntregado(Boolean entregado) {
        this.entregado = entregado;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public byte[] getSSMATimeStamp() {
        return sSMATimeStamp;
    }

    public void setSSMATimeStamp(byte[] sSMATimeStamp) {
        this.sSMATimeStamp = sSMATimeStamp;
    }

    @XmlTransient
    public Collection<Vmresfuerzos> getVmresfuerzosCollection() {
        return vmresfuerzosCollection;
    }

    public void setVmresfuerzosCollection(Collection<Vmresfuerzos> vmresfuerzosCollection) {
        this.vmresfuerzosCollection = vmresfuerzosCollection;
    }

    public Vmmotostock getIdMoto() {
        return idMoto;
    }

    public void setIdMoto(Vmmotostock idMoto) {
        this.idMoto = idMoto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vmventamotos)) {
            return false;
        }
        Vmventamotos other = (Vmventamotos) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmventamotos[ idVenta=" + idVenta + " ]";
    }

    @XmlTransient
    public Collection<Vmplanmoto> getVmplanmotoCollection() {
        return vmplanmotoCollection;
    }

    public void setVmplanmotoCollection(Collection<Vmplanmoto> vmplanmotoCollection) {
        this.vmplanmotoCollection = vmplanmotoCollection;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getCompFecha() {
        return compFecha;
    }

    public void setCompFecha(Date compFecha) {
        this.compFecha = compFecha;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @XmlTransient
    public Collection<Vmpagomotos> getVmpagomotosCollection() {
        return vmpagomotosCollection;
    }

    public void setVmpagomotosCollection(Collection<Vmpagomotos> vmpagomotosCollection) {
        this.vmpagomotosCollection = vmpagomotosCollection;
    }
}

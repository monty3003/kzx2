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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "VMPAGOMOTOS", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmpagomotos.findAll", query = "SELECT v FROM Vmpagomotos v"),
    @NamedQuery(name = "Vmpagomotos.findByNumeroRecibo", query = "SELECT v FROM Vmpagomotos v WHERE v.numeroRecibo = :numeroRecibo"),
    @NamedQuery(name = "Vmpagomotos.findByCedulaRuc", query = "SELECT v FROM Vmpagomotos v WHERE v.cedulaRuc = :cedulaRuc"),
    @NamedQuery(name = "Vmpagomotos.findByCodEmpleado", query = "SELECT v FROM Vmpagomotos v WHERE v.codEmpleado = :codEmpleado"),
    @NamedQuery(name = "Vmpagomotos.findByFechaPago", query = "SELECT v FROM Vmpagomotos v WHERE v.fechaPago = :fechaPago"),
    @NamedQuery(name = "Vmpagomotos.findByHora", query = "SELECT v FROM Vmpagomotos v WHERE v.hora = :hora"),
    @NamedQuery(name = "Vmpagomotos.findByNumCuotas", query = "SELECT v FROM Vmpagomotos v WHERE v.numCuotas = :numCuotas"),
    @NamedQuery(name = "Vmpagomotos.findByUltimopago", query = "SELECT v FROM Vmpagomotos v WHERE v.ultimopago = :ultimopago"),
    @NamedQuery(name = "Vmpagomotos.findByMontoEntrega", query = "SELECT v FROM Vmpagomotos v WHERE v.montoEntrega = :montoEntrega"),
    @NamedQuery(name = "Vmpagomotos.findByMontosaldo", query = "SELECT v FROM Vmpagomotos v WHERE v.montosaldo = :montosaldo"),
    @NamedQuery(name = "Vmpagomotos.findBySaldomomento", query = "SELECT v FROM Vmpagomotos v WHERE v.saldomomento = :saldomomento"),
    @NamedQuery(name = "Vmpagomotos.findByEnletras", query = "SELECT v FROM Vmpagomotos v WHERE v.enletras = :enletras"),
    @NamedQuery(name = "Vmpagomotos.findByConcepto", query = "SELECT v FROM Vmpagomotos v WHERE v.concepto = :concepto"),
    @NamedQuery(name = "Vmpagomotos.findByGuardado", query = "SELECT v FROM Vmpagomotos v WHERE v.guardado = :guardado"),
    @NamedQuery(name = "Vmpagomotos.findByAnulado", query = "SELECT v FROM Vmpagomotos v WHERE v.anulado = :anulado")})
public class Vmpagomotos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NumeroRecibo", nullable = false)
    private Integer numeroRecibo;
    @Size(max = 14)
    @Column(name = "Cedula_Ruc", length = 14)
    private String cedulaRuc;
    @Column(name = "CodEmpleado")
    private Integer codEmpleado;
    @Column(name = "Fecha Pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Column(name = "Hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;
    @Size(max = 6)
    @Column(name = "NumCuotas", length = 6)
    private String numCuotas;
    @Column(name = "ULTIMOPAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimopago;
    @Column(name = "Monto Entrega")
    private Integer montoEntrega;
    @Column(name = "MONTOSALDO")
    private Integer montosaldo;
    @Column(name = "SALDOMOMENTO")
    private Integer saldomomento;
    @Size(max = 1073741823)
    @Column(name = "ENLETRAS", length = 1073741823)
    private String enletras;
    @Size(max = 1073741823)
    @Column(name = "Concepto", length = 1073741823)
    private String concepto;
    @Column(name = "GUARDADO")
    private Integer guardado;
    @Column(name = "ANULADO")
    private Boolean anulado;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;
    @JoinColumn(name = "IdVentas", referencedColumnName = "IdVenta")
    @ManyToOne
    private Vmventamotos idVentas;

    public Vmpagomotos() {
    }

    public Vmpagomotos(Integer numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public Vmpagomotos(Integer numeroRecibo, byte[] sSMATimeStamp) {
        this.numeroRecibo = numeroRecibo;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Vmpagomotos(Integer numeroRecibo, String cedulaRuc, Integer codEmpleado, Date fechaPago, Date hora, String numCuotas, Date ultimopago, Integer montoEntrega, Integer montosaldo, Integer saldomomento, String concepto, Integer guardado, Boolean anulado, Integer idVentas) {
        this.numeroRecibo = numeroRecibo;
        this.cedulaRuc = cedulaRuc;
        this.codEmpleado = codEmpleado;
        this.fechaPago = fechaPago;
        this.hora = hora;
        this.numCuotas = numCuotas;
        this.ultimopago = ultimopago;
        this.montoEntrega = montoEntrega;
        this.montosaldo = montosaldo;
        this.saldomomento = saldomomento;
        this.concepto = concepto;
        this.guardado = guardado;
        this.anulado = anulado;
        this.idVentas = new Vmventamotos(idVentas);
    }

    public Integer getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(Integer numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public String getCedulaRuc() {
        return cedulaRuc;
    }

    public void setCedulaRuc(String cedulaRuc) {
        this.cedulaRuc = cedulaRuc;
    }

    public Integer getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(Integer codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getNumCuotas() {
        return numCuotas;
    }

    public void setNumCuotas(String numCuotas) {
        this.numCuotas = numCuotas;
    }

    public Date getUltimopago() {
        return ultimopago;
    }

    public void setUltimopago(Date ultimopago) {
        this.ultimopago = ultimopago;
    }

    public Integer getMontoEntrega() {
        return montoEntrega;
    }

    public void setMontoEntrega(Integer montoEntrega) {
        this.montoEntrega = montoEntrega;
    }

    public Integer getMontosaldo() {
        return montosaldo;
    }

    public void setMontosaldo(Integer montosaldo) {
        this.montosaldo = montosaldo;
    }

    public Integer getSaldomomento() {
        return saldomomento;
    }

    public void setSaldomomento(Integer saldomomento) {
        this.saldomomento = saldomomento;
    }

    public String getEnletras() {
        return enletras;
    }

    public void setEnletras(String enletras) {
        this.enletras = enletras;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getGuardado() {
        return guardado;
    }

    public void setGuardado(Integer guardado) {
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
        hash += (numeroRecibo != null ? numeroRecibo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vmpagomotos)) {
            return false;
        }
        Vmpagomotos other = (Vmpagomotos) object;
        if ((this.numeroRecibo == null && other.numeroRecibo != null) || (this.numeroRecibo != null && !this.numeroRecibo.equals(other.numeroRecibo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmpagomotos[ numeroRecibo=" + numeroRecibo + " ]";
    }
}

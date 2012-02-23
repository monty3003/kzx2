/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "CLIENTES", catalog = "BDBEJ", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CodigoCliente"}),
    @UniqueConstraint(columnNames = {"Cedula_Ruc"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAllOrdenado", query = "SELECT c FROM Clientes c order by c.clientesPK.codigoCliente asc"),
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByCedulaRuc", query = "SELECT c FROM Clientes c WHERE c.clientesPK.cedulaRuc = :cedulaRuc"),
    @NamedQuery(name = "Clientes.findByCodigoCliente", query = "SELECT c FROM Clientes c WHERE c.clientesPK.codigoCliente = :codigoCliente"),
    @NamedQuery(name = "Clientes.findByNombreApellido", query = "SELECT c FROM Clientes c WHERE c.nombreApellido = :nombreApellido"),
    @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Clientes.findByTelefono", query = "SELECT c FROM Clientes c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Clientes.findByMovil", query = "SELECT c FROM Clientes c WHERE c.movil = :movil"),
    @NamedQuery(name = "Clientes.findByEmail", query = "SELECT c FROM Clientes c WHERE c.email = :email"),
    @NamedQuery(name = "Clientes.findByFechaIngreso", query = "SELECT c FROM Clientes c WHERE c.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Clientes.findByEstado", query = "SELECT c FROM Clientes c WHERE c.estado = :estado"),
    @NamedQuery(name = "Clientes.findByFechaSesantia", query = "SELECT c FROM Clientes c WHERE c.fechaSesantia = :fechaSesantia"),
    @NamedQuery(name = "Clientes.findByObservacion", query = "SELECT c FROM Clientes c WHERE c.observacion = :observacion"),
    @NamedQuery(name = "Clientes.findByDesafectado", query = "SELECT c FROM Clientes c WHERE c.desafectado = :desafectado"),
    @NamedQuery(name = "Clientes.findByGuardado", query = "SELECT c FROM Clientes c WHERE c.guardado = :guardado"),
    @NamedQuery(name = "Clientes.findByCategoria", query = "SELECT c FROM Clientes c WHERE c.categoria = :categoria")})
public class Clientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClientesPK clientesPK;
    @Size(max = 50)
    @Column(name = "NombreApellido", length = 50)
    private String nombreApellido;
    @Size(max = 50)
    @Column(name = "Direccion", length = 50)
    private String direccion;
    @Size(max = 11)
    @Column(name = "Telefono", length = 11)
    private String telefono;
    @Size(max = 13)
    @Column(name = "Movil", length = 13)
    private String movil;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 25)
    @Column(name = "Email", length = 25)
    private String email;
    @Column(name = "FechaIngreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Estado", nullable = false)
    private float estado;
    @Column(name = "fechaSesantia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSesantia;
    @Size(max = 1073741823)
    @Column(name = "Observacion", length = 1073741823)
    private String observacion;
    @Column(name = "Desafectado")
    private Boolean desafectado;
    @Column(name = "Guardado")
    private Boolean guardado;
    @Column(name = "CATEGORIA")
    private Integer categoria;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;

    public Clientes() {
    }

    public Clientes(ClientesPK clientesPK) {
        this.clientesPK = clientesPK;
    }

    public Clientes(ClientesPK clientesPK, float estado, byte[] sSMATimeStamp) {
        this.clientesPK = clientesPK;
        this.estado = estado;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Clientes(String cedulaRuc, int codigoCliente) {
        this.clientesPK = new ClientesPK(cedulaRuc, codigoCliente);
    }

    public ClientesPK getClientesPK() {
        return clientesPK;
    }

    public void setClientesPK(ClientesPK clientesPK) {
        this.clientesPK = clientesPK;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public float getEstado() {
        return estado;
    }

    public void setEstado(float estado) {
        this.estado = estado;
    }

    public Date getFechaSesantia() {
        return fechaSesantia;
    }

    public void setFechaSesantia(Date fechaSesantia) {
        this.fechaSesantia = fechaSesantia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getDesafectado() {
        return desafectado;
    }

    public void setDesafectado(Boolean desafectado) {
        this.desafectado = desafectado;
    }

    public Boolean getGuardado() {
        return guardado;
    }

    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
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
        hash += (clientesPK != null ? clientesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.clientesPK == null && other.clientesPK != null) || (this.clientesPK != null && !this.clientesPK.equals(other.clientesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Clientes[ clientesPK=" + clientesPK + " ]";
    }
    
}

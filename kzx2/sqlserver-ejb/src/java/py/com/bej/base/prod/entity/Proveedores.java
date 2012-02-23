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
@Table(name = "PROVEEDORES", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p"),
    @NamedQuery(name = "Proveedores.findByCodProveedor", query = "SELECT p FROM Proveedores p WHERE p.codProveedor = :codProveedor"),
    @NamedQuery(name = "Proveedores.findByNombre", query = "SELECT p FROM Proveedores p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proveedores.findByDireccion", query = "SELECT p FROM Proveedores p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Proveedores.findByCiudad", query = "SELECT p FROM Proveedores p WHERE p.ciudad = :ciudad"),
    @NamedQuery(name = "Proveedores.findByTelefono", query = "SELECT p FROM Proveedores p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Proveedores.findByTelefax", query = "SELECT p FROM Proveedores p WHERE p.telefax = :telefax"),
    @NamedQuery(name = "Proveedores.findByContacto1", query = "SELECT p FROM Proveedores p WHERE p.contacto1 = :contacto1"),
    @NamedQuery(name = "Proveedores.findByContacto2", query = "SELECT p FROM Proveedores p WHERE p.contacto2 = :contacto2"),
    @NamedQuery(name = "Proveedores.findByObservacion", query = "SELECT p FROM Proveedores p WHERE p.observacion = :observacion")})
public class Proveedores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "CodProveedor", nullable = false, length = 11)
    private String codProveedor;
    @Size(max = 50)
    @Column(name = "Nombre", length = 50)
    private String nombre;
    @Size(max = 50)
    @Column(name = "Direccion", length = 50)
    private String direccion;
    @Size(max = 20)
    @Column(name = "Ciudad", length = 20)
    private String ciudad;
    @Size(max = 15)
    @Column(name = "Telefono", length = 15)
    private String telefono;
    @Size(max = 15)
    @Column(name = "Telefax", length = 15)
    private String telefax;
    @Size(max = 30)
    @Column(name = "Contacto1", length = 30)
    private String contacto1;
    @Size(max = 30)
    @Column(name = "Contacto2", length = 30)
    private String contacto2;
    @Size(max = 1073741823)
    @Column(name = "Observacion", length = 1073741823)
    private String observacion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;

    public Proveedores() {
    }

    public Proveedores(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public Proveedores(String codProveedor, byte[] sSMATimeStamp) {
        this.codProveedor = codProveedor;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefax() {
        return telefax;
    }

    public void setTelefax(String telefax) {
        this.telefax = telefax;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        hash += (codProveedor != null ? codProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.codProveedor == null && other.codProveedor != null) || (this.codProveedor != null && !this.codProveedor.equals(other.codProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Proveedores[ codProveedor=" + codProveedor + " ]";
    }
    
}

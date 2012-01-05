/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Persona", catalog = "bejdb", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"documento"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findById", query = "SELECT p FROM Persona p WHERE p.personaPK.id = :id"),
    @NamedQuery(name = "Persona.findByDocumento", query = "SELECT p FROM Persona p WHERE p.personaPK.documento = :documento"),
    @NamedQuery(name = "Persona.findByFisica", query = "SELECT p FROM Persona p WHERE p.fisica = :fisica"),
    @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Persona.findByDireccion1", query = "SELECT p FROM Persona p WHERE p.direccion1 = :direccion1"),
    @NamedQuery(name = "Persona.findByDireccion2", query = "SELECT p FROM Persona p WHERE p.direccion2 = :direccion2"),
    @NamedQuery(name = "Persona.findByTelefonoFijo", query = "SELECT p FROM Persona p WHERE p.telefonoFijo = :telefonoFijo"),
    @NamedQuery(name = "Persona.findByTelefonoMovil", query = "SELECT p FROM Persona p WHERE p.telefonoMovil = :telefonoMovil"),
    @NamedQuery(name = "Persona.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email"),
    @NamedQuery(name = "Persona.findByFechaIngreso", query = "SELECT p FROM Persona p WHERE p.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Persona.findByRuc", query = "SELECT p FROM Persona p WHERE p.ruc = :ruc"),
    @NamedQuery(name = "Persona.findByContacto", query = "SELECT p FROM Persona p WHERE p.contacto = :contacto"),
    @NamedQuery(name = "Persona.findByFechaNacimiento", query = "SELECT p FROM Persona p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Persona.findByEstadoCivil", query = "SELECT p FROM Persona p WHERE p.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "Persona.findByProfesion", query = "SELECT p FROM Persona p WHERE p.profesion = :profesion"),
    @NamedQuery(name = "Persona.findByTratamiento", query = "SELECT p FROM Persona p WHERE p.tratamiento = :tratamiento"),
    @NamedQuery(name = "Persona.findBySexo", query = "SELECT p FROM Persona p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "Persona.findByHijos", query = "SELECT p FROM Persona p WHERE p.hijos = :hijos"),
    @NamedQuery(name = "Persona.findByHabilitado", query = "SELECT p FROM Persona p WHERE p.habilitado = :habilitado"),
    @NamedQuery(name = "Persona.findByCategoria", query = "SELECT p FROM Persona p WHERE p.categoria = :categoria")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic(optional = false)
    @Column(name = "documento", nullable = false,unique=true, length = 11)
    private String documento;
    @Basic(optional = false)
    @Column(name = "fisica", nullable = false)
    private Character fisica;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "direccion1", nullable = false, length = 50)
    private String direccion1;
    @Column(name = "direccion2", length = 50)
    private String direccion2;
    @Column(name = "telefono_fijo", length = 11)
    private String telefonoFijo;
    @Column(name = "telefono_movil", length = 13)
    private String telefonoMovil;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", length = 25)
    private String email;
    @Basic(optional = false)
    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Size(max = 10)
    @Column(name = "ruc", length = 10)
    private String ruc;
    @Size(max = 45)
    @Column(name = "contacto", length = 45)
    private String contacto;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "estado_civil", nullable = false)
    private Character estadoCivil;
    @Column(name = "profesion", length = 40)
    private String profesion;
    @Basic(optional = false)
    @Column(name = "tratamiento", nullable = false, length = 15)
    private String tratamiento;
    @Column(name = "sexo")
    private Character sexo;
    @Column(name = "hijos")
    private Short hijos;
    @Basic(optional = false)
    @Column(name = "habilitado", nullable = false)
    private Character habilitado;
    @Basic(optional = false)
    @JoinColumn(name = "categoria",referencedColumnName="id",insertable=false,updatable=true)
    @ManyToOne
    private Categoria categoria;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "fabricante")
    private List<Moto> motos;
    @OneToMany(mappedBy = "vendedor")
    private List<Transaccion> transaccionsVendedor;
    @OneToMany(mappedBy = "comprador")
    private List<Transaccion> transaccionsComprador;

    public Persona() {
    }

    public Persona(int id, String documento, Character fisica, String nombre, String direccion1, String direccion2, String telefonoFijo, String telefonoMovil, String email, Date fechaIngreso, String ruc, String contacto, Date fechaNacimiento, Character estadoCivil, String profesion, String tratamiento, Character sexo, Short hijos, Character habilitado, Categoria categoria, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.documento = documento;
        this.fisica = fisica;
        this.nombre = nombre;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.telefonoFijo = telefonoFijo;
        this.telefonoMovil = telefonoMovil;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.ruc = ruc;
        this.contacto = contacto;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
        this.profesion = profesion;
        this.tratamiento = tratamiento;
        this.sexo = sexo;
        this.hijos = hijos;
        this.habilitado = habilitado;
        this.categoria = categoria;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
    }

    public char getFisica() {
        return fisica;
    }

    public void setFisica(char fisica) {
        this.fisica = fisica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public char getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(char estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Short getHijos() {
        return hijos;
    }

    public void setHijos(Short hijos) {
        this.hijos = hijos;
    }

    public char getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(char habilitado) {
        this.setHabilitado((Character) habilitado);
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.setCategoria(categoria);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) getId();
        hash += (getDocumento() != null ? getDocumento().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Integer)) {
            return false;
        }
        Integer other = (Integer) object;
        if (this.getId() != other) {
            return false;
        }
        if ((documento == null && documento != null) || (documento != null && !this.documento.equals(other))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.PersonaPK[ id=" + getId() + ", documento=" + getDocumento() + " ]";
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(Character habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the activo
     */
    public Character getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Character activo) {
        this.activo = activo;
    }

    /**
     * @return the ultimaModificacion
     */
    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    /**
     * @param ultimaModificacion the ultimaModificacion to set
     */
    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    /**
     * @return the motos
     */
    public List<Moto> getMotos() {
        return motos;
    }

    /**
     * @return the transaccionsVendedor
     */
    public List<Transaccion> getTransaccionsVendedor() {
        return transaccionsVendedor;
    }

    /**
     * @return the transaccionsComprador
     */
    public List<Transaccion> getTransaccionsComprador() {
        return transaccionsComprador;
    }
    
}

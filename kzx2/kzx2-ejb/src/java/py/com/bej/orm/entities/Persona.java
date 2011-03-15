/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import py.com.bej.orm.utils.Conversor;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "Persona")
@NamedQueries({
    @NamedQuery(name = "Persona.findById", query = "SELECT p FROM Persona p WHERE p.personaPK.id = :id"),
    @NamedQuery(name = "Persona.findByDocumento", query = "SELECT p FROM Persona p WHERE p.personaPK.documento = :documento")
})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PersonaPK personaPK;
    @Basic(optional = false)
    @Column(name = "fisica")
    private Character fisica;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "direccion1")
    private String direccion1;
    @Column(name = "direccion2")
    private String direccion2;
    @Column(name = "telefono_fijo")
    private String telefonoFijo;
    @Column(name = "telefono_movil")
    private String telefonoMovil;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "contacto")
    private String contacto;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "estado_civil")
    private Character estadoCivil;
    @Column(name = "profesion")
    private String profesion;
    @Basic(optional = false)
    @Column(name = "tratamiento")
    private String tratamiento;
    @Column(name = "sexo")
    private Character sexo;
    @Column(name = "hijos")
    private Short hijos;
    @Basic(optional = false)
    @Column(name = "habilitado")
    private Character habilitado;
    @Basic(optional = false)
    @Column(name = "categoria")
    private Integer categoria;

    public Persona() {
    }

    public Persona(PersonaPK personaPK) {
        this.personaPK = personaPK;
    }

    public Persona(PersonaPK personaPK, Character fisica, String nombre, String direccion1, String direccion2, String telefonoFijo, String telefonoMovil, String email, Date fechaIngreso, String ruc, String contacto, Date fechaNacimiento, Character estadoCivil, String profesion, String tratamiento, Character sexo, Short hijos, Character habilitado, Integer categoria) {
        this.personaPK = personaPK;
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
    }

    

    public Persona(Integer id, String documento) {
        this.personaPK = new PersonaPK(id, documento);
    }

    public PersonaPK getPersonaPK() {
        return personaPK;
    }

    public void setPersonaPK(PersonaPK personaPK) {
        this.personaPK = personaPK;
    }

    public Character getFisica() {
        return fisica;
    }

    public void setFisica(Character fisica) {
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

    public String getFechaIngresoString() {
        return Conversor.deDateToString(this.fechaIngreso);
    }

    public void setFechaIngresoString(String fechaIngreso) {
        this.fechaIngreso = Conversor.deStringToDate(fechaIngreso);
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

    public String getFechaNacimientoString() {
        return Conversor.deDateToString(this.fechaNacimiento);
    }

    public void setFechaNacimientoString(String fechaNacimiento) {
        this.fechaNacimiento = Conversor.deStringToDate(fechaNacimiento);
    }

    public Character getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Character estadoCivil) {
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

    public Character getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Character habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personaPK != null ? personaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.personaPK == null && other.personaPK != null) || (this.personaPK != null && !this.personaPK.equals(other.personaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Persona[personaPK=" + personaPK + "]";
    }

    /**
     * @return the categoria
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }
}

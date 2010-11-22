/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.bej.orm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "PersonaFisica")
@NamedQueries({
    @NamedQuery(name = "PersonaFisica.findAll", query = "SELECT p FROM PersonaFisica p"),
    @NamedQuery(name = "PersonaFisica.findById", query = "SELECT p FROM PersonaFisica p WHERE p.id = :id"),
    @NamedQuery(name = "PersonaFisica.findByRuc", query = "SELECT p FROM PersonaFisica p WHERE p.ruc = :ruc"),
    @NamedQuery(name = "PersonaFisica.findByEdad", query = "SELECT p FROM PersonaFisica p WHERE p.edad = :edad"),
    @NamedQuery(name = "PersonaFisica.findByProfesion", query = "SELECT p FROM PersonaFisica p WHERE p.profesion = :profesion"),
    @NamedQuery(name = "PersonaFisica.findByEstadoCivil", query = "SELECT p FROM PersonaFisica p WHERE p.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "PersonaFisica.findByFechaNacimiento", query = "SELECT p FROM PersonaFisica p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "PersonaFisica.findByTratamiento", query = "SELECT p FROM PersonaFisica p WHERE p.tratamiento = :tratamiento")})
public class PersonaFisica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ruc")
    private String ruc;
    @Basic(optional = false)
    @Column(name = "edad")
    private int edad;
    @Column(name = "profesion")
    private String profesion;
    @Basic(optional = false)
    @Column(name = "estado_civil")
    private String estadoCivil;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "tratamiento")
    private String tratamiento;
    @Basic(optional = false)
    @Lob
    @Column(name = "sexo")
    private byte[] sexo;

    public PersonaFisica() {
    }

    public PersonaFisica(Integer id) {
        this.id = id;
    }

    public PersonaFisica(Integer id, String ruc, int edad, String estadoCivil, Date fechaNacimiento, String tratamiento, byte[] sexo) {
        this.id = id;
        this.ruc = ruc;
        this.edad = edad;
        this.estadoCivil = estadoCivil;
        this.fechaNacimiento = fechaNacimiento;
        this.tratamiento = tratamiento;
        this.sexo = sexo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public byte[] getSexo() {
        return sexo;
    }

    public void setSexo(byte[] sexo) {
        this.sexo = sexo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaFisica)) {
            return false;
        }
        PersonaFisica other = (PersonaFisica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.PersonaFisica[id=" + id + "]";
    }

}

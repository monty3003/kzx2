/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.bej.orm.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "PersonaJuridica")
@NamedQueries({
    @NamedQuery(name = "PersonaJuridica.findAll", query = "SELECT p FROM PersonaJuridica p"),
    @NamedQuery(name = "PersonaJuridica.findById", query = "SELECT p FROM PersonaJuridica p WHERE p.id = :id"),
    @NamedQuery(name = "PersonaJuridica.findByContacto", query = "SELECT p FROM PersonaJuridica p WHERE p.contacto = :contacto"),
    @NamedQuery(name = "PersonaJuridica.findByCategoria", query = "SELECT p FROM PersonaJuridica p WHERE p.categoria = :categoria")})
public class PersonaJuridica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "contacto")
    private String contacto;
    @Basic(optional = false)
    @Column(name = "categoria")
    private int categoria;

    public PersonaJuridica() {
    }

    public PersonaJuridica(Integer id) {
        this.id = id;
    }

    public PersonaJuridica(Integer id, int categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
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
        if (!(object instanceof PersonaJuridica)) {
            return false;
        }
        PersonaJuridica other = (PersonaJuridica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.PersonaJuridica[id=" + id + "]";
    }

}

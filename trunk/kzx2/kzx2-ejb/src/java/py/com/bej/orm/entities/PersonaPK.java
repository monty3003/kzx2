/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.bej.orm.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author diego
 */
@Embeddable
public class PersonaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "documento")
    private String documento;

    public PersonaPK() {
    }

    public PersonaPK(Integer id, String documento) {
        this.id = id;
        this.documento = documento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (documento != null ? documento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaPK)) {
            return false;
        }
        PersonaPK other = (PersonaPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.documento == null && other.documento != null) || (this.documento != null && !this.documento.equals(other.documento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.PersonaPK[id=" + id + ", documento=" + documento + "]";
    }

}

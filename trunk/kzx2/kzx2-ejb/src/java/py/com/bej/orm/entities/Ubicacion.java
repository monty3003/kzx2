/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Ubicacion", catalog = "bej")
@XmlRootElement
public class Ubicacion extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotEmpty(message = "Ingrese un valor")
    @Size(min = 4, max = 45, message = "Ingrese una descripcion valida")
    @Basic(optional = false)
    @Column(name = "descripcion", nullable = false, length = 45, unique = true)
    private String descripcion;
    @NotNull(message = "Ingrese un valor")
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "ubicacion", cascade = CascadeType.ALL)
    private List<Motostock> motostocks;

    public Ubicacion() {
    }

    public Ubicacion(Integer id) {
        this.id = id;
    }

    public Ubicacion(Integer id, String descripcion, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Ubicacion)) {
            return false;
        }
        Ubicacion other = (Ubicacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Ubicacion[ id=" + id + " ]";
    }

    @Override
    public String getlabel() {
        return this.descripcion;
    }

    /**
     * @return the activo
     */
    @Override
    public Character getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    @Override
    public void setActivo(Character activo) {
        this.activo = activo;
    }

    /**
     * @return the ultimaModificacion
     */
    @Override
    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    /**
     * @param ultimaModificacion the ultimaModificacion to set
     */
    @Override
    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    /**
     * @return the motostocks
     */
    public List<Motostock> getMotostocks() {
        return motostocks;
    }
}

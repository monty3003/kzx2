/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Categoria", catalog = "bej")
@XmlRootElement
public class Categoria extends WithId<Integer> {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 50)
    @NotEmpty(message = "Ingrese un valor")
    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Persona> personas;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Moto> motos;
    @OneToMany(mappedBy = "codigo", cascade = CascadeType.ALL)
    private List<Transaccion> transaccions;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Factura> facturas;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Credito> creditos;
    @OneToMany(mappedBy = "sistemaCredito", cascade = CascadeType.ALL)
    private List<Credito> creditosSitemaCredito;

    public Categoria() {
    }

    public Categoria(Integer id) {
        this.id = id;
    }

    public Categoria(Integer id, String descripcion, Character activo, Date ultimaModificacion) {
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
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Categoria[ id=" + id + " ]";
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
     * @return the personas
     */
    public List<Persona> getPersonas() {
        return personas;
    }

    /**
     * @return the motos
     */
    public List<Moto> getMotos() {
        return motos;
    }

    /**
     * @return the transaccions
     */
    public List<Transaccion> getTransaccions() {
        return transaccions;
    }

    /**
     * @return the facturas
     */
    public List<Factura> getFacturas() {
        return facturas;
    }

    /**
     * @return the creditos
     */
    public List<Credito> getCreditos() {
        return creditos;
    }

    /**
     * @return the creditosSitemaCredito
     */
    public List<Credito> getCreditosSitemaCredito() {
        return creditosSitemaCredito;
    }
}

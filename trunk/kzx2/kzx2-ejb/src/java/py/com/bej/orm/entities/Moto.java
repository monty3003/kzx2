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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Moto", catalog = "bejdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Moto.findAll", query = "SELECT m FROM Moto m"),
    @NamedQuery(name = "Moto.findByCodigo", query = "SELECT m FROM Moto m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "Moto.findByCodigoFabrica", query = "SELECT m FROM Moto m WHERE m.codigoFabrica = :codigoFabrica"),
    @NamedQuery(name = "Moto.findByMarca", query = "SELECT m FROM Moto m WHERE m.marca = :marca"),
    @NamedQuery(name = "Moto.findByModelo", query = "SELECT m FROM Moto m WHERE m.modelo = :modelo"),
    @NamedQuery(name = "Moto.findByColor", query = "SELECT m FROM Moto m WHERE m.color = :color"),
    @NamedQuery(name = "Moto.findByFabricante", query = "SELECT m FROM Moto m WHERE m.fabricante = :fabricante"),
    @NamedQuery(name = "Moto.findByCategoria", query = "SELECT m FROM Moto m WHERE m.categoria = :categoria")})
public class Moto implements Serializable, WithId<String> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false, length = 14)
    private String codigo;
    @Basic(optional = false)
    @Column(name = "codigo_fabrica", nullable = false, length = 20)
    private String codigoFabrica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "marca", nullable = false, length = 20)
    private String marca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modelo", nullable = false, length = 20)
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "color", nullable = false, length = 20)
    private String color;
    @Basic(optional = false)
    @JoinColumn(name = "fabricante", referencedColumnName = "id", insertable = false, updatable = true)
    @ManyToOne
    private Persona fabricante;
    @Basic(optional = false)
    @JoinColumn(name = "categoria", referencedColumnName = "id", insertable = false, updatable = true)
    @ManyToOne
    private Categoria categoria;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "moto")
    private List<Motostock> motostocks;

    public Moto() {
    }

    public Moto(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoFabrica() {
        return codigoFabrica;
    }

    public void setCodigoFabrica(String codigoFabrica) {
        this.codigoFabrica = codigoFabrica;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Persona getFabricante() {
        return fabricante;
    }

    public void setFabricante(Persona fabricante) {
        this.fabricante = fabricante;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Moto)) {
            return false;
        }
        Moto other = (Moto) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities.Moto[ codigo=" + codigo + " ]";
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

    @Override
    public void setId(String id) {
        this.codigo = id;
    }

    @Override
    public String getId() {
        return this.codigo;
    }

    @Override
    public String getlabel() {
        return this.marca + " " + this.modelo + " " + this.color;
    }

    /**
     * @return the motostocks
     */
    public List<Motostock> getMotostocks() {
        return motostocks;
    }
}
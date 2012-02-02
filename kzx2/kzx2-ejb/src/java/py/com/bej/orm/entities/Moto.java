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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "Moto", catalog = "bej")
@XmlRootElement
public class Moto extends WithId<String> {

    @Id
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false, length = 14)
    @NotNull(message = "Codigo: Ingrese un valor")
    @Size(min = 6, max = 20, message = "Codigo: Ingrese un valor entre 6 a 20 caracteres")
    private String codigo;
    @NotNull(message = "Codigo Fabrica: Ingrese un valor")
    @Size(min = 6, max = 20, message = "Codigo Fabrica: Ingrese de 6 a 20 caracteres")
    @Basic(optional = false)
    @Column(name = "codigo_fabrica", nullable = false, length = 20)
    private String codigoFabrica;
    @Basic(optional = false)
    @NotNull(message = "Marca: Ingrese un valor")
    @Size(min = 6, max = 20, message = "Marca: Ingrese de 6 a 20 caracteres")
    @Column(name = "marca", nullable = false, length = 20)
    private String marca;
    @Basic(optional = false)
    @NotNull(message = "Modelo: Ingrese un valor")
    @Column(name = "modelo", nullable = false, length = 20)
    private String modelo;
    @Basic(optional = false)
    @NotNull(message = "Color: Ingrese un valor")
    @Column(name = "color", nullable = false, length = 20)
    private String color;
    @JoinColumn(name = "fabricante", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Persona fabricante;
    @JoinColumn(name = "categoria", referencedColumnName = "id", insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @Column(name = "activo", length = 1)
    @Basic(optional = false)
    private Character activo;
    @Column(name = "ultimaModificacion")
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL)
    private List<Motostock> motostocks;

    public Moto() {
        this.categoria = new Categoria();
        this.fabricante = new Persona();
    }

    public Moto(String codigo, String codigoFabrica, String marca, String modelo, String color, Persona fabricante, Categoria categoria) {
        this.codigo = codigo;
        this.codigoFabrica = codigoFabrica;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.fabricante = fabricante;
        this.categoria = categoria;
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

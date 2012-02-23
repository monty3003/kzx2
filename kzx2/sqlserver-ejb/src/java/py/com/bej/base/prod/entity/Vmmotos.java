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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "VMMOTOS", catalog = "BDBEJ", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CodSistema"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmmotos.findAll", query = "SELECT v FROM Vmmotos v"),
    @NamedQuery(name = "Vmmotos.findByCodSistema", query = "SELECT v FROM Vmmotos v WHERE v.codSistema = :codSistema"),
    @NamedQuery(name = "Vmmotos.findByCodMoto", query = "SELECT v FROM Vmmotos v WHERE v.codMoto = :codMoto"),
    @NamedQuery(name = "Vmmotos.findByMarca", query = "SELECT v FROM Vmmotos v WHERE v.marca = :marca"),
    @NamedQuery(name = "Vmmotos.findByModelo", query = "SELECT v FROM Vmmotos v WHERE v.modelo = :modelo"),
    @NamedQuery(name = "Vmmotos.findByColor", query = "SELECT v FROM Vmmotos v WHERE v.color = :color"),
    @NamedQuery(name = "Vmmotos.findByPrecio", query = "SELECT v FROM Vmmotos v WHERE v.precio = :precio")})
public class Vmmotos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CodSistema", nullable = false)
    private int codSistema;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "COD_MOTO", nullable = false, length = 14)
    private String codMoto;
    @Size(max = 10)
    @Column(name = "MARCA", length = 10)
    private String marca;
    @Size(max = 20)
    @Column(name = "MODELO", length = 20)
    private String modelo;
    @Size(max = 20)
    @Column(name = "COLOR", length = 20)
    private String color;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO", precision = 53)
    private Double precio;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;

    public Vmmotos() {
    }

    public Vmmotos(String codMoto) {
        this.codMoto = codMoto;
    }

    public Vmmotos(String codMoto, int codSistema, byte[] sSMATimeStamp) {
        this.codMoto = codMoto;
        this.codSistema = codSistema;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public int getCodSistema() {
        return codSistema;
    }

    public void setCodSistema(int codSistema) {
        this.codSistema = codSistema;
    }

    public String getCodMoto() {
        return codMoto;
    }

    public void setCodMoto(String codMoto) {
        this.codMoto = codMoto;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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
        hash += (codMoto != null ? codMoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vmmotos)) {
            return false;
        }
        Vmmotos other = (Vmmotos) object;
        if ((this.codMoto == null && other.codMoto != null) || (this.codMoto != null && !this.codMoto.equals(other.codMoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmmotos[ codMoto=" + codMoto + " ]";
    }
    
}

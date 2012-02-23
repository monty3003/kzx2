/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "VMMOTOSTOCK", catalog = "BDBEJ", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vmmotostock.findAll", query = "SELECT v FROM Vmmotostock v"),
    @NamedQuery(name = "Vmmotostock.findByIdMoto", query = "SELECT v FROM Vmmotostock v WHERE v.idMoto = :idMoto"),
    @NamedQuery(name = "Vmmotostock.findByCodigoMoto", query = "SELECT v FROM Vmmotostock v WHERE v.codigoMoto = :codigoMoto"),
    @NamedQuery(name = "Vmmotostock.findByNumMotor", query = "SELECT v FROM Vmmotostock v WHERE v.numMotor = :numMotor"),
    @NamedQuery(name = "Vmmotostock.findByNumChasis", query = "SELECT v FROM Vmmotostock v WHERE v.numChasis = :numChasis"),
    @NamedQuery(name = "Vmmotostock.findByNVenta", query = "SELECT v FROM Vmmotostock v WHERE v.nVenta = :nVenta"),
    @NamedQuery(name = "Vmmotostock.findByCostoGuarani", query = "SELECT v FROM Vmmotostock v WHERE v.costoGuarani = :costoGuarani"),
    @NamedQuery(name = "Vmmotostock.findByVentaGuarani", query = "SELECT v FROM Vmmotostock v WHERE v.ventaGuarani = :ventaGuarani"),
    @NamedQuery(name = "Vmmotostock.findByCotizacionDolar", query = "SELECT v FROM Vmmotostock v WHERE v.cotizacionDolar = :cotizacionDolar"),
    @NamedQuery(name = "Vmmotostock.findByCostoDolar", query = "SELECT v FROM Vmmotostock v WHERE v.costoDolar = :costoDolar"),
    @NamedQuery(name = "Vmmotostock.findByVentaDolar", query = "SELECT v FROM Vmmotostock v WHERE v.ventaDolar = :ventaDolar"),
    @NamedQuery(name = "Vmmotostock.findByVendido", query = "SELECT v FROM Vmmotostock v WHERE v.vendido = :vendido"),
    @NamedQuery(name = "Vmmotostock.findByUbicacion2", query = "SELECT v FROM Vmmotostock v WHERE v.ubicacion2 = :ubicacion2")})
public class Vmmotostock implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "SSMA_TimeStamp", nullable = false)
    private byte[] sSMATimeStamp;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdMoto", nullable = false)
    private Integer idMoto;
    @Size(max = 20)
    @Column(name = "CodigoMoto", length = 20)
    private String codigoMoto;
    @Size(max = 25)
    @Column(name = "NumMotor", length = 25)
    private String numMotor;
    @Size(max = 25)
    @Column(name = "NumChasis", length = 25)
    private String numChasis;
    @Column(name = "N_VENTA")
    private Integer nVenta;
    @Column(name = "CostoGuarani")
    private Integer costoGuarani;
    @Column(name = "VentaGuarani")
    private Integer ventaGuarani;
    @Column(name = "CotizacionDolar")
    private Integer cotizacionDolar;
    @Column(name = "CostoDolar")
    private Integer costoDolar;
    @Column(name = "VentaDolar")
    private Integer ventaDolar;
    @Column(name = "Vendido")
    private Boolean vendido;
    @Size(max = 15)
    @Column(name = "Ubicacion2", length = 15)
    private String ubicacion2;
    @OneToMany(mappedBy = "idMoto")
    private Collection<Vmventamotos> vmventamotosCollection;
    @JoinColumn(name = "N_COMP", referencedColumnName = "IdCompras")
    @ManyToOne
    private Vmcompras nComp;

    public Vmmotostock() {
    }

    public Vmmotostock(Integer idMoto) {
        this.idMoto = idMoto;
    }

    public Vmmotostock(Integer idMoto, byte[] sSMATimeStamp) {
        this.idMoto = idMoto;
        this.sSMATimeStamp = sSMATimeStamp;
    }

    public Integer getIdMoto() {
        return idMoto;
    }

    public void setIdMoto(Integer idMoto) {
        this.idMoto = idMoto;
    }

    public String getCodigoMoto() {
        return codigoMoto;
    }

    public void setCodigoMoto(String codigoMoto) {
        this.codigoMoto = codigoMoto;
    }

    public String getNumMotor() {
        return numMotor;
    }

    public void setNumMotor(String numMotor) {
        this.numMotor = numMotor;
    }

    public String getNumChasis() {
        return numChasis;
    }

    public void setNumChasis(String numChasis) {
        this.numChasis = numChasis;
    }

    public Integer getNVenta() {
        return nVenta;
    }

    public void setNVenta(Integer nVenta) {
        this.nVenta = nVenta;
    }

    public Integer getCostoGuarani() {
        return costoGuarani;
    }

    public void setCostoGuarani(Integer costoGuarani) {
        this.costoGuarani = costoGuarani;
    }

    public Integer getVentaGuarani() {
        return ventaGuarani;
    }

    public void setVentaGuarani(Integer ventaGuarani) {
        this.ventaGuarani = ventaGuarani;
    }

    public Integer getCotizacionDolar() {
        return cotizacionDolar;
    }

    public void setCotizacionDolar(Integer cotizacionDolar) {
        this.cotizacionDolar = cotizacionDolar;
    }

    public Integer getCostoDolar() {
        return costoDolar;
    }

    public void setCostoDolar(Integer costoDolar) {
        this.costoDolar = costoDolar;
    }

    public Integer getVentaDolar() {
        return ventaDolar;
    }

    public void setVentaDolar(Integer ventaDolar) {
        this.ventaDolar = ventaDolar;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public String getUbicacion2() {
        return ubicacion2;
    }

    public void setUbicacion2(String ubicacion2) {
        this.ubicacion2 = ubicacion2;
    }

    public byte[] getSSMATimeStamp() {
        return sSMATimeStamp;
    }

    public void setSSMATimeStamp(byte[] sSMATimeStamp) {
        this.sSMATimeStamp = sSMATimeStamp;
    }

    @XmlTransient
    public Collection<Vmventamotos> getVmventamotosCollection() {
        return vmventamotosCollection;
    }

    public void setVmventamotosCollection(Collection<Vmventamotos> vmventamotosCollection) {
        this.vmventamotosCollection = vmventamotosCollection;
    }

    public Vmcompras getNComp() {
        return nComp;
    }

    public void setNComp(Vmcompras nComp) {
        this.nComp = nComp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMoto != null ? idMoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vmmotostock)) {
            return false;
        }
        Vmmotostock other = (Vmmotostock) object;
        if ((this.idMoto == null && other.idMoto != null) || (this.idMoto != null && !this.idMoto.equals(other.idMoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.Vmmotostock[ idMoto=" + idMoto + " ]";
    }
    
}

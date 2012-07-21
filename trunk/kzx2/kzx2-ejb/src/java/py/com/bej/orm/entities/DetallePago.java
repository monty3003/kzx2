/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "detalle_pago", catalog = "bej")
@XmlRootElement
public class DetallePago extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "codigo", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Categoria codigo;
    @JoinColumn(name = "pago", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Pago pago;
    @Column(name = "concepto", length = 50, nullable = true)
    private String concepto;
    @Column(name = "importe", nullable = false)
    private BigDecimal importe;
    @Column(name = "numero_cuota", nullable = false)
    private Short numeroCuota;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @Transient
    private Boolean seleccion;
    @Transient
    private Date vencimiento;
    @Transient
    private String claseColor;

    public DetallePago() {
    }

    public DetallePago(Categoria codigo, Pago pago, String concepto, BigDecimal importe, Short numeroCuota, Character activo, Date ultimaModificacion, Boolean seleccion, Date vencimiento, String claseColor) {
        this.codigo = codigo;
        this.pago = pago;
        this.concepto = concepto;
        this.importe = importe;
        this.numeroCuota = numeroCuota;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
        this.seleccion = seleccion;
        this.vencimiento = vencimiento;
        this.claseColor = claseColor;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setActivo(Character activo) {
        this.activo = activo;
    }

    @Override
    public Character getActivo() {
        return this.activo;
    }

    @Override
    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    @Override
    public Date getUltimaModificacion() {
        return this.ultimaModificacion;
    }

    @Override
    public String getlabel() {
        return this.id + " " + this.codigo.getDescripcion();
    }

    /**
     * @return the codigo
     */
    public Categoria getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Categoria codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the pago
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Boolean getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Boolean seleccion) {
        this.seleccion = seleccion;
    }

    public Short getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Short numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getClaseColor() {
        return claseColor;
    }

    public void setClaseColor(String claseColor) {
        this.claseColor = claseColor;
    }
}

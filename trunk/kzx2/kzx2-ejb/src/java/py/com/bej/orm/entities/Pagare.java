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
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;
import py.com.bej.orm.utils.Conversor;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "pagare", catalog = "bej")
@XmlRootElement
public class Pagare extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "credito", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Credito credito;
    @Min(value = 1, message = "Numero de Pagare: debe ser un numero positivo")
    @Column(name = "numero", nullable = false)
    private Short numero;
    @Column(name = "fechaVencimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Min(value = 1, message = "Monto del pagare: debe ser un valor positivo")
    @Column(name = "monto", nullable = false)
    private BigDecimal monto;
    @Column(name = "vencido", nullable = false)
    private Boolean vencido;
    @Column(name = "vencimiento_impreso", nullable = false)
    private Boolean vencimientoImpreso;
    @Column(name = "fecha_de_cancelacion", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaDeCancelacion;
    @JoinColumn(name = "usuario_creacion", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioCreacion;
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioModificacion;
    @Column(name = "activo", nullable = false)
    private Character activo;
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultima_modificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @Transient
    private String fechaVencimientoString;
    @Transient
    private String cliente;

    public Pagare() {
    }

    public Pagare(Integer id, Credito credito, Short numero, Date fechaVencimiento, BigDecimal monto, Boolean vencido, Boolean vencimientoImpreso, Date fechaDeCancelacion, Usuario usuarioCreacion, Usuario usuarioModificacion, Character activo, Date fechaCreacion, Date ultimaModificacion) {
        this.id = id;
        this.credito = credito;
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
        this.monto = monto;
        this.vencido = vencido;
        this.vencimientoImpreso = vencimientoImpreso;
        this.fechaDeCancelacion = fechaDeCancelacion;
        this.usuarioCreacion = usuarioCreacion;
        this.usuarioModificacion = usuarioModificacion;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.ultimaModificacion = ultimaModificacion;
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
        return "" + this.id + this.getFechaVencimiento();
    }

    /**
     * @return the credito
     */
    public Credito getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    /**
     * @return the numero
     */
    public Short getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Short numero) {
        this.numero = numero;
    }

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the vencido
     */
    public Boolean getVencido() {
        return vencido;
    }

    /**
     * @param vencido the vencido to set
     */
    public void setVencido(Boolean vencido) {
        this.vencido = vencido;
    }

    /**
     * @return the fechaDeCancelacion
     */
    public Date getFechaDeCancelacion() {
        return fechaDeCancelacion;
    }

    /**
     * @param fechaDeCancelacion the fechaDeCancelacion to set
     */
    public void setFechaDeCancelacion(Date fechaDeCancelacion) {
        this.fechaDeCancelacion = fechaDeCancelacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public Usuario getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(Usuario usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the usuarioModificacion
     */
    public Usuario getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     * @param usuarioModificacion the usuarioModificacion to set
     */
    public void setUsuarioModificacion(Usuario usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getVencimientoImpreso() {
        return vencimientoImpreso;
    }

    public void setVencimientoImpreso(Boolean vencimientoImpreso) {
        this.vencimientoImpreso = vencimientoImpreso;
    }

    public String getFechaVencimientoString() {
        if (fechaVencimientoString == null) {
            if (fechaVencimiento != null) {
                fechaVencimientoString = Conversor.deDateToString(fechaVencimiento, "d 'de' MMMMM 'de' yyyy");
            } else {
                fechaVencimientoString = "";
            }
        }
        return fechaVencimientoString;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}

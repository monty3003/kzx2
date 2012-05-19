/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "plan", catalog = "bej")
@XmlRootElement
public class Plan extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "categoria", referencedColumnName = "id", insertable = true, updatable = true, nullable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @NotNull(message = "Ingrese un nombre")
    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;
    @Min(value = 1000, message = "Ingrese un valor positivo")
    @Column(name = "monto_entrega_minimo", length = 11, nullable = false)
    private BigDecimal montoEntregaMinimo;
    @Min(value = 1000, message = "Ingrese un valor positivo")
    @Column(name = "monto_entrega_maximo", length = 11, nullable = false)
    private BigDecimal montoEntregaMaximo;
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Column(name = "porcentaje_monto_entrega", nullable = false)
    private Float porcentajeMontoEntrega;
    @Min(value = 1, message = "Ingrese un valor positivo")
    @Column(name = "financiacion_minima", nullable = false)
    private Short financiacionMinima;
    @Min(value = 1, message = "Ingrese un valor positivo")
    @Column(name = "financiacion_maxima", nullable = false)
    private Short financiacionMaxima;
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Column(name = "tan", nullable = false)
    private Float tan;
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Column(name = "tae", nullable = false)
    private Float tae;
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Column(name = "tasa_interes_moratorio", nullable = false)
    private Float tasaInteresMoratorio;
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Max(value = 90, message = "Valor fuera de rango")
    @Column(name = "dias_a_primer_vencimiento", length = 2, nullable = false)
    private Short diasAPrimerVencimiento;
    @Min(value = -5, message = "Valor fuera de rango")
    @Max(value = 5, message = "Valor fuera de rango")
    @Column(name = "indice_redondeo", length = 2, nullable = false)
    private Short indiceRedondeo;
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Column(name = "procentaje_descuento", nullable = false)
    private Float porcentajeDescuento;
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Column(name = "monto_descuento", length = 11, nullable = false)
    private BigDecimal montoDescuento;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "plan")
    private List<Motostock> motoStockList;

    public Plan() {
        this.categoria = new Categoria();
    }

    public Plan(Integer id) {
        this.id = id;
    }

    public Plan(Integer id, Categoria categoria, String nombre, Character activo) {
        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Plan(Integer id, Categoria categoria, String nombre, BigDecimal montoEntregaMinimo, BigDecimal montoEntregaMaximo, Float porcentajeMontoEntrega, Short financiacionMinima, Short financiacionMaxima, Float tan, Float tae, Float tasaInteresMoratorio, Short diasAPrimerVencimiento, Short indiceRedondeo, Float porcentajeDescuento, BigDecimal montoDescuento, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
        this.montoEntregaMinimo = montoEntregaMinimo;
        this.montoEntregaMaximo = montoEntregaMaximo;
        this.porcentajeMontoEntrega = porcentajeMontoEntrega;
        this.financiacionMinima = financiacionMinima;
        this.financiacionMaxima = financiacionMaxima;
        this.tan = tan;
        this.tae = tae;
        this.tasaInteresMoratorio = tasaInteresMoratorio;
        this.diasAPrimerVencimiento = diasAPrimerVencimiento;
        this.indiceRedondeo = indiceRedondeo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.montoDescuento = montoDescuento;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Short getDiasAPrimerVencimiento() {
        return diasAPrimerVencimiento;
    }

    public void setDiasAPrimerVencimiento(Short diasAPrimerVencimiento) {
        this.diasAPrimerVencimiento = diasAPrimerVencimiento;
    }

    public Short getFinanciacionMaxima() {
        return financiacionMaxima;
    }

    public void setFinanciacionMaxima(Short financiacionMaxima) {
        this.financiacionMaxima = financiacionMaxima;
    }

    public Short getFinanciacionMinima() {
        return financiacionMinima;
    }

    public void setFinanciacionMinima(Short financiacionMinima) {
        this.financiacionMinima = financiacionMinima;
    }

    public Short getIndiceRedondeo() {
        return indiceRedondeo;
    }

    public void setIndiceRedondeo(Short indiceRedondeo) {
        this.indiceRedondeo = indiceRedondeo;
    }

    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public BigDecimal getMontoEntregaMaximo() {
        return montoEntregaMaximo;
    }

    public void setMontoEntregaMaximo(BigDecimal montoEntregaMaximo) {
        this.montoEntregaMaximo = montoEntregaMaximo;
    }

    public BigDecimal getMontoEntregaMinimo() {
        return montoEntregaMinimo;
    }

    public void setMontoEntregaMinimo(BigDecimal montoEntregaMinimo) {
        this.montoEntregaMinimo = montoEntregaMinimo;
    }

    public List<Motostock> getMotoStockList() {
        return motoStockList;
    }

    public void setMotoStockList(List<Motostock> motoStockList) {
        this.motoStockList = motoStockList;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Float porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Float getPorcentajeMontoEntrega() {
        return porcentajeMontoEntrega;
    }

    public void setPorcentajeMontoEntrega(Float porcentajeMontoEntrega) {
        this.porcentajeMontoEntrega = porcentajeMontoEntrega;
    }

    public Float getTae() {
        return tae;
    }

    public void setTae(Float tae) {
        this.tae = tae;
    }

    public Float getTan() {
        return tan;
    }

    public void setTan(Float tan) {
        this.tan = tan;
    }

    public Float getTasaInteresMoratorio() {
        return tasaInteresMoratorio;
    }

    public void setTasaInteresMoratorio(Float tasaInteresMoratorio) {
        this.tasaInteresMoratorio = tasaInteresMoratorio;
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
        return this.getNombre();
    }
}
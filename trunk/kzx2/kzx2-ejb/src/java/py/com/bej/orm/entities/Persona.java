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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "persona", catalog = "bej")
@XmlRootElement
public class Persona extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(min = 6, max = 11, message = "Ingrese un valor entre 6 y 11 caracteres")
    @Column(name = "documento", nullable = false, unique = true, length = 11)
    private String documento;
    @Column(name = "fisica", nullable = false)
    private Character fisica;
    @Size(min = 5, max = 50, message = "Ingrese un nombre valido")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "direccion1", nullable = false, length = 100)
    private String direccion1;
    @Column(name = "direccion2", length = 100)
    private String direccion2;
    @Column(name = "telefono_fijo", length = 11)
    private String telefonoFijo;
    @Column(name = "telefono_movil", length = 13)
    private String telefonoMovil;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Ingrese una direccion de email valida")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", length = 25)
    private String email;
    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "ruc", length = 11)
    private String ruc;
    @Column(name = "contacto", length = 45)
    private String contacto;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "estado_civil", nullable = false)
    private Character estadoCivil;
    @Column(name = "profesion", length = 40)
    private String profesion;
    @Column(name = "tratamiento", nullable = false, length = 15)
    private String tratamiento;
    @Column(name = "sexo")
    private Character sexo;
    @Column(name = "hijos")
    private Short hijos;
    @Column(name = "habilitado", nullable = false)
    private Character habilitado;
    @JoinColumn(name = "categoria", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL)
    private List<Moto> motos;
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private List<Transaccion> transaccionsVendedor;
    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL)
    private List<Transaccion> transaccionsComprador;
    @OneToMany(mappedBy = "garante", cascade = CascadeType.ALL)
    private List<Credito> creditos;
    @Transient
    private String fechaIngresoString;
    @Transient
    private String fechaNacimientoString;

    public Persona() {
        this.categoria = new Categoria();
    }

    public Persona(String documento, Character fisica, String nombre, String direccion1, String telefonoMovil, Categoria categoria) {
        this.documento = documento;
        this.fisica = fisica;
        this.nombre = nombre;
        this.direccion1 = direccion1;
        this.telefonoMovil = telefonoMovil;
        this.categoria = categoria;
    }

    public Persona(Integer id) {
        this.id = id;
    }

    public Persona(String id) {
        this.id = new Integer(id);
    }

    public Persona(Integer id, String documento) {
        this.id = id;
        this.documento = documento;
    }

    public Persona(Integer id, String documento, Character fisica, String nombre, String direccion1, String direccion2, String telefonoFijo, String telefonoMovil, String email, Date fechaIngreso, String ruc, String contacto, Date fechaNacimiento, Character estadoCivil, String profesion, String tratamiento, Character sexo, Short hijos, Character habilitado, Categoria categoria, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.documento = documento;
        this.fisica = fisica;
        this.nombre = nombre;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.telefonoFijo = telefonoFijo;
        this.telefonoMovil = telefonoMovil;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.ruc = ruc;
        this.contacto = contacto;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
        this.profesion = profesion;
        this.tratamiento = tratamiento;
        this.sexo = sexo;
        this.hijos = hijos;
        this.habilitado = habilitado;
        this.categoria = categoria;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
    }

    public Character getFisica() {
        return fisica;
    }

    public void setFisica(Character fisica) {
        this.fisica = fisica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Character getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Character estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Short getHijos() {
        return hijos;
    }

    public void setHijos(Short hijos) {
        this.hijos = hijos;
    }

    public Character getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Character habilitado) {
        this.habilitado = habilitado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        hash += (getDocumento() != null ? getDocumento().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Integer)) {
            return false;
        }
        Integer other = (Integer) object;
        if (this.getId() != other) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.orm.entities[ id=" + getId() + ", documento=" + getDocumento() + " ]";
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
     * @return the motos
     */
    public List<Moto> getMotos() {
        return motos;
    }

    /**
     * @return the transaccionsVendedor
     */
    public List<Transaccion> getTransaccionsVendedor() {
        return transaccionsVendedor;
    }

    /**
     * @return the transaccionsComprador
     */
    public List<Transaccion> getTransaccionsComprador() {
        return transaccionsComprador;
    }

    /**
     * @return the fechaIngresoString
     */
    public String getFechaIngresoString() {
        if (fechaIngresoString == null) {
            fechaIngresoString = Conversor.deDateToString(fechaIngreso, ConfiguracionEnum.DATE_PATTERN_CORTO.getSymbol());
        }
        return fechaIngresoString;
    }

    /**
     * @return the fechaNacimientoString
     */
    public String getFechaNacimientoString() {
        if (fechaNacimientoString == null) {
            fechaNacimientoString = Conversor.deDateToString(fechaNacimiento, ConfiguracionEnum.DATE_PATTERN_CORTO.getSymbol());
        }
        return fechaNacimientoString;
    }

    @Override
    public String getlabel() {
        return this.nombre;
    }

    /**
     * @return the creditos
     */
    public List<Credito> getCreditos() {
        return creditos;
    }

    /**
     * @param creditos the creditos to set
     */
    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }
}

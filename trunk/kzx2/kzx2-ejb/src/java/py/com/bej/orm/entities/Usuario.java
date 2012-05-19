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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
@Entity
@Table(name = "usuario", catalog = "bej")
@XmlRootElement
public class Usuario extends WithId<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotNull(message = "Ingrese un valor")
    @Column(name = "documento", length = 10, nullable = false)
    private String documento;
    @NotNull(message = "Ingrese un valor")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    @NotNull(message = "Ingrese un valor")
    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;
    @JoinColumn(name = "rol", referencedColumnName = "id", insertable = true, updatable = true, nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Rol rol;
    @NotNull(message = "Ingrese un valor")
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Ingrese una direccion de email valida")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", length = 45, nullable = false)
    private String email;
    @Column(name = "ultimo_acceso", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcceso;
    @Column(name = "desvinculado_el", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date desvinculadoEl;
    @Column(name = "activo", length = 1, nullable = false)
    private Character activo;
    @Column(name = "ultimaModificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Password> passwords;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String documento, String nombre, String userName, Rol rol, String email, Date ultimoAcceso, Date desvinculadoEl, Character activo, Date ultimaModificacion) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.userName = userName;
        this.rol = rol;
        this.email = email;
        this.ultimoAcceso = ultimoAcceso;
        this.desvinculadoEl = desvinculadoEl;
        this.activo = activo;
        this.ultimaModificacion = ultimaModificacion;
    }

    public Date getDesvinculadoEl() {
        return desvinculadoEl;
    }

    public void setDesvinculadoEl(Date desvinculadoEl) {
        this.desvinculadoEl = desvinculadoEl;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return this.nombre;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }
}

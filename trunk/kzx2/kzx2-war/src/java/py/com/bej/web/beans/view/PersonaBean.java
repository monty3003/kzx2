/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.PersonaPK;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class PersonaBean extends AbstractPageBean {

    @EJB
    private PersonaFacade facade;
    private PersonaPK pk;
    private String id;
    private Boolean valido;
    private DateFormat df;
    private Calendar ahora;
    private List<Categoria> listaCategorias;
    private List<SelectItem> listaCategoria;
    //Persona
    private Character fisica;
    private String documento;
    private String ruc;
    private String nombre;
    private String direccion1;
    private String direccion2;
    private String telefonoFijo;
    private String telefonoMovil;
    private String email;
    private String fechaIngreso;
    private String contacto;
    private String profesion;
    private Character estadoCivil;
    private String fechaNacimiento;
    private String tratamiento;
    private Character sexo;
    private Short hijos;
    private Character habilitado;
    private Integer categoria;

    /** Creates a new instance of PersonaBean */
    public PersonaBean() {
    }

    /**
     * @return the facade
     */
    public PersonaFacade getFacade() {
        if (this.facade == null) {
            this.facade = new PersonaFacade();
        }
        return facade;
    }

    @Override
    void deEntity() {
        setId(facade.getEntity().getPersonaPK().getId().toString());
        setDocumento(facade.getEntity().getPersonaPK().getDocumento());
        setPk(facade.getEntity().getPersonaPK());
        setNombre(facade.getEntity().getNombre());
        setDireccion1(facade.getEntity().getDireccion1());
        setDireccion2(facade.getEntity().getDireccion2());
        setTelefonoFijo(facade.getEntity().getTelefonoFijo());
        setTelefonoMovil(facade.getEntity().getTelefonoMovil());
        setEmail(facade.getEntity().getEmail());
        setFechaIngreso(facade.getEntity().getFechaIngresoString());
        setRuc(facade.getEntity().getRuc());
        setContacto(facade.getEntity().getContacto());
        setProfesion(facade.getEntity().getProfesion());
        setEstadoCivil(facade.getEntity().getEstadoCivil());
        setFechaNacimiento(facade.getEntity().getFechaNacimientoString());
        setTratamiento(facade.getEntity().getTratamiento());
        setFisica(facade.getEntity().getFisica());
        setSexo(facade.getEntity().getSexo());
        setHijos(facade.getEntity().getHijos());
        setHabilitado(facade.getEntity().getHabilitado());
        setCategoria(facade.getEntity().getCategoria());
    }

    @Override
    void deCampos() {
        if (id != null && !id.trim().equals("")) {
            facade.getEntity().getPersonaPK().setId(new Integer(getId()));
        } else {
            facade.getEntity().getPersonaPK().setId(null);
        }
        if (getFisica() != null && (getFisica().equals('S') || getFisica().equals('N'))) {
            facade.getEntity().setFisica(fisica);
        } else {
            facade.getEntity().setFisica(null);
        }
        if (documento != null && !documento.trim().equals("")) {
            facade.getEntity().getPersonaPK().setDocumento(documento.trim());
        } else {
            facade.getEntity().getPersonaPK().setDocumento(null);
        }
        if (getNombre() != null && !nombre.trim().equals("")) {
            facade.getEntity().setNombre((getNombre().trim()));
        } else {
            facade.getEntity().setNombre(null);
        }
        if (getDireccion1() != null && !direccion1.trim().equals("")) {
            facade.getEntity().setDireccion1((direccion1.trim()));
        } else {
            facade.getEntity().setDireccion1(null);
        }
        if (getDireccion2() != null && !direccion2.trim().equals("")) {
            facade.getEntity().setDireccion2((direccion2.trim()));
        } else {
            facade.getEntity().setDireccion2(null);
        }
        if (getTelefonoFijo() != null && !telefonoFijo.trim().equals("")) {
            facade.getEntity().setTelefonoFijo((telefonoFijo.trim()));
        } else {
            facade.getEntity().setTelefonoFijo(null);
        }
        if (getTelefonoMovil() != null && !telefonoMovil.trim().equals("")) {
            facade.getEntity().setTelefonoMovil((telefonoMovil.trim()));
        } else {
            facade.getEntity().setTelefonoMovil(null);
        }
        if (getEmail() != null && !email.trim().equals("")) {
            facade.getEntity().setEmail((getEmail().trim().toLowerCase()));
        } else {
            facade.getEntity().setEmail(null);
        }
        if (getFechaIngreso() != null && !fechaIngreso.trim().equals("")) {
            facade.getEntity().setFechaIngresoString(fechaIngreso);
        } else {
            facade.getEntity().setFechaIngreso(null);
        }
        if (getRuc() != null && !ruc.trim().equals("")) {
            facade.getEntity().setRuc(getRuc());
        } else {
            facade.getEntity().setRuc(null);
        }
        if (getContacto() != null && !contacto.trim().equals("")) {
            setContacto(getContacto().trim());
        } else {
            facade.getEntity().setContacto(null);
        }
        if (getProfesion() != null && !profesion.trim().equals("")) {
            facade.getEntity().setProfesion(getProfesion().trim());
        } else {
            facade.getEntity().setProfesion(null);
        }
        if (hijos != null && hijos >= 0) {
            facade.getEntity().setHijos(hijos);
        } else {
            facade.getEntity().setHijos(null);
        }
        if (getEstadoCivil() != null) {
            facade.getEntity().setEstadoCivil(getEstadoCivil());
        } else {
            facade.getEntity().setEstadoCivil(null);
        }
        if (fechaNacimiento != null && !fechaNacimiento.trim().equals("")) {
            facade.getEntity().setFechaNacimientoString(getFechaNacimiento());
        } else {
            facade.getEntity().setFechaNacimiento(null);
        }
        if (getTratamiento() != null && !tratamiento.trim().equals("")) {
            facade.getEntity().setTratamiento(getTratamiento());
        } else {
            facade.getEntity().setTratamiento(null);
        }
        if (getSexo() != null) {
            facade.getEntity().setSexo(sexo);
        } else {
            facade.getEntity().setSexo(null);
        }
        if (getHabilitado() != null && (getHabilitado().equals('S') || getHabilitado().equals('N'))) {
            facade.getEntity().setHabilitado(habilitado);
        } else {
            facade.getEntity().setHabilitado(null);
        }
        if (getCategoria() != null && !categoria.equals(-1)) {
            facade.getEntity().setCategoria(categoria);
        } else {
            facade.getEntity().setCategoria(null);
        }
    }

    @Override
    void limpiarCampos() {
        facade.setEntity(new Persona(new PersonaPK()));
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setId(null);
        setDocumento(null);
        setPk(new PersonaPK());
        setNombre(null);
        setDireccion1(null);
        setDireccion2(null);
        setTelefonoFijo(null);
        setTelefonoMovil(null);
        setEmail(null);
        setFechaIngreso(null);
        setRuc(null);
        setContacto(null);
        setProfesion(null);
        setEstadoCivil(null);
        setFechaNacimiento(null);
        setTratamiento(null);
        setSexo(null);
        setFisica(null);
        setHabilitado(null);
        setHijos(null);
        setCategoria(-1);
        setNav("listapersonas");
    }

    @Override
    public String listar() {
        setNav("listapersonas");
        setCategoria(null);
        setDesde(0);
        setMax(10);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return getNav();
    }

    @Override
    List filtrar() {
        facade.setEntity(new Persona(new PersonaPK()));
        deCampos();
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategorias = new CategoriaFacade().findBetween(20, 30);
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }
    }

    @Override
    public String buscar() {
        facade.setEntity(new Persona(new PersonaPK()));
        deCampos();
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        facade.setEntity(null);
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaCategorias = new CategoriaFacade().findBetween(20, 30);
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }
        return "crearpersona";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        setId((String) request.getParameter("radio"));
        if (getId() != null) {
            pk = new PersonaPK(new Integer(
                    id.substring(0, id.indexOf(":"))), id.substring(id.indexOf(":") + 1, id.length()));
            try {
                facade.setEntity(facade.find(pk));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            deEntity();
            listaCategoria = new ArrayList<SelectItem>();
            obtenerListas();
            return "modificarpersona";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    boolean validarNuevo() {
        if (getFisica().equals('X')) {
            setErrorMessage("frm:fisica", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (getDocumento().trim().equals("")) {
            setErrorMessage("frm:documento", "Ingrese un valor");
            res = false;
        } else {
            Persona existe = null;
            existe = getFacade().findByDocumento(getDocumento().trim());
            if (existe != null) {
                if (facade.getEntity() != null && existe.getPersonaPK().getId().equals(facade.getEntity().getPersonaPK().getId())) {
                    existe = null;
                }
            }
            if (existe != null) {
                setErrorMessage("frm:documento", "Ya existe una persona con este documento.");
                res = false;
            }
        }
        if (getNombre().trim().equals("")) {
            setErrorMessage("frm:nombre", "Ingrese un nombre");
            res = false;
        }
        if (getDireccion1().trim().equals("")) {
            setErrorMessage("frm:direccion1", "Ingrese una Dirección");
            res = false;
        }
        if (getTelefonoFijo().trim().equals("") && getTelefonoMovil().trim().equals("")) {
            setErrorMessage("frm:telefonoFijo", "Ingrese por lo menos un Número de teléfono");
            res = false;
        }
        if (getTratamiento().trim().equals("X")) {
            String component = getFisica().equals('S') ? "frm:tratamiento" : "frm:tratamiento2";
            setErrorMessage(component, "Seleccione un valor");
            res = false;
        }
        if (getFisica().equals('N')) {
            //Persona Juridica
            if (getContacto().trim().equals("")) {
                setErrorMessage("frm:contacto", "Ingrese un Contacto");
                res = false;
            }
        } else if (getFisica().equals('S')) {
            //Persona Fisica
            if (getSexo().equals('X')) {
                setErrorMessage("frm:sexo", "Seleccione un valor");
                res = false;
            }
            if (getEstadoCivil().equals('X')) {
                setErrorMessage("frm:estadoCivil", "Seleccione un valor");
                res = false;
            }
            if (getProfesion().trim().equals("")) {
                setErrorMessage("frm:profesion", "Ingrese un valor");
                res = false;
            }
            if (getHijos() == null || getHijos() > 10 || getHijos() < 0) {
                setErrorMessage("frm:hijos", "Ingrese un valor");
                res = false;
            } else {
                setHijos(hijos);
            }
            if (getCategoria() == null || getCategoria() == -1) {
                setErrorMessage("frm:categoria", "Seleccione un valor");
                res = false;
            } else {
                setCategoria(getCategoria());
            }
            if (getFechaNacimiento().trim().equals("")) {
                setErrorMessage("frm:fechaNacimiento", "Ingrese una fecha de nacimiento");
                res = false;
            } else {
                Date fecha = null;
                df = new SimpleDateFormat("dd/MM/yyyy");
                ahora = GregorianCalendar.getInstance();
                try {
                    fecha = df.parse(getFechaNacimiento());
                    Calendar fechaIntroducida = GregorianCalendar.getInstance();
                    fechaIntroducida.setTime(fecha);
                    int e = (ahora.get(GregorianCalendar.YEAR) - fechaIntroducida.get(GregorianCalendar.YEAR));
                    if (e == 18) {
                        if (ahora.get(GregorianCalendar.DAY_OF_YEAR) < fechaIntroducida.get(GregorianCalendar.DAY_OF_YEAR)) {
                            setErrorMessage("frm:fechaNacimiento", "El cliente debe tener 18 años cumplidos");
                            res = false;
                        }
                    } else if (e < 18) {
                        setErrorMessage("frm:fechaNacimiento", "El cliente debe tener 18 años cumplidos");
                        res = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    setErrorMessage("frm:fechaNacimiento", "Ingrese una fecha con el formato dd/MM/yyyy");
                    res = false;
                }
            }
        }
        return res;
    }

    @Override
    public String guardarNuevo() {
        boolean validado = validarNuevo();
        if (validado) {
            facade.setEntity(new Persona(new PersonaPK()));
            deCampos();
            facade.getEntity().setCategoria(categoria);
            facade.getEntity().setHabilitado('S');
            facade.getEntity().setFechaIngreso(new Date());
            if (fisica.equals('S')) {
                facade.getEntity().setEstadoCivil(estadoCivil);
            } else {
                facade.getEntity().setEstadoCivil('X');
            }
            facade.create();
            setInfoMessage(null, facade.ex1);
        } else {
            return null;
        }
        limpiarCampos();
        return listar();
    }

    @Override
    public String guardarModificar() {
        boolean validado = validarNuevo();
        if (validado) {
            deCampos();
            facade.guardar();
            setInfoMessage(null, facade.ex2);
            limpiarCampos();
            return listar();
        } else {
            return null;
        }
    }

    @Override
    public String cancelar() {
        limpiarCampos();
        return listar();
    }

    @Override
    public String anterior() {
        setLista(getFacade().anterior());
        return getNav();
    }

    @Override
    public String siguiente() {
        setLista(getFacade().siguiente());
        return getNav();
    }

    @Override
    public String todos() {
        limpiarCampos();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Integer[]{0, 10});
        getFacade().setOrden(new Orden("id", false));
        setValido((Boolean) true);
        deCampos();
        getFacade().setRango(new Integer[]{0, 10});
        filtrar();
        return getNav();
    }

    @Override
    boolean validarModificar() {
        if (facade.getEntity().getFisica().equals('X')) {
            setErrorMessage("frm:fisica", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (facade.getEntity().getPersonaPK().getDocumento().trim().equals("")) {
            setErrorMessage("frm:documento", "Ingrese un valor");
            res = false;
        }
        if (facade.getEntity().getNombre().trim().equals("")) {
            setErrorMessage("frm:nombre", "Ingrese un nombre");
            res = false;
        }
        if (facade.getEntity().getDireccion1().trim().equals("")) {
            setErrorMessage("frm:direccion1", "Ingrese una Dirección");
            res = false;
        }
        if (facade.getEntity().getTelefonoFijo().trim().equals("") && facade.getEntity().getTelefonoMovil().trim().equals("")) {
            setErrorMessage("frm:telefonoFijo", "Ingrese por lo menos un Número de teléfono");
            res = false;
        }
        if (facade.getEntity().getTratamiento().trim().equals("X")) {
            String component = facade.getEntity().getFisica().equals('S') ? "frm:tratamiento" : "frm:tratamiento2";
            setErrorMessage(component, "Seleccione un valor");
            res = false;
        }
        if (facade.getEntity().getFisica().equals('N')) {
            //Persona Juridica
            facade.getEntity().setFisica(fisica);
            facade.getEntity().setRuc(documento);
            if (facade.getEntity().getContacto().trim().equals("")) {
                setErrorMessage("frm:contacto", "Ingrese un Contacto");
                res = false;
            }
        } else if (facade.getEntity().getFisica().equals('S')) {
            //Persona Fisica
            facade.getEntity().setFisica(fisica);
            if (facade.getEntity().getSexo().equals('X')) {
                setErrorMessage("frm:sexo", "Seleccione un valor");
                res = false;
            }
            if (facade.getEntity().getEstadoCivil().equals('X')) {
                setErrorMessage("frm:estadoCivil", "Seleccione un valor");
                res = false;
            }
            if (facade.getEntity().getProfesion().trim().equals("")) {
                setErrorMessage("frm:profesion", "Ingrese un valor");
                res = false;
            }
            if (getHijos() == null || getHijos() > 10 || getHijos() < 0) {
                setErrorMessage("frm:hijos", "Ingrese un valor");
                res = false;
            } else {
                facade.getEntity().setHijos(hijos);
            }
            if (getFechaNacimiento().trim().equals("")) {
                setErrorMessage("frm:fechaNacimiento", "Ingrese una fecha de nacimiento");
                res = false;
            } else {
                Date fecha = null;
                df = new SimpleDateFormat("dd/MM/yyyy");
                ahora = GregorianCalendar.getInstance();
                try {
                    fecha = df.parse(getFechaNacimiento());
                    Calendar fechaIntroducida = GregorianCalendar.getInstance();
                    fechaIntroducida.setTime(fecha);
                    int e = (ahora.get(GregorianCalendar.YEAR) - fechaIntroducida.get(GregorianCalendar.YEAR));
                    if (e == 18) {
                        if (ahora.get(GregorianCalendar.DAY_OF_YEAR) < fechaIntroducida.get(GregorianCalendar.DAY_OF_YEAR)) {
                            setErrorMessage("frm:fechaNacimiento", "El cliente debe tener 18 años cumplidos");
                            res = false;
                        }
                    } else if (e < 18) {
                        setErrorMessage("frm:fechaNacimiento", "El cliente debe tener 18 años cumplidos");
                        res = false;
                    }
                    facade.getEntity().setFechaNacimiento(fecha);
                } catch (Exception e) {
                    e.printStackTrace();
                    setErrorMessage("frm:fechaNacimiento", "Ingrese una fecha con el formato dd/MM/yyyy");
                    res = false;
                }
            }
        }
        return res;
    }

    /**
     * @return the categoria
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the pk
     */
    public PersonaPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(PersonaPK pk) {
        this.pk = pk;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the valido
     */
    public Boolean getValido() {
        return valido;
    }

    /**
     * @param valido the valido to set
     */
    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    /**
     * @return the fisica
     */
    public Character getFisica() {
        return fisica;
    }

    /**
     * @param fisica the fisica to set
     */
    public void setFisica(Character fisica) {
        this.fisica = fisica;
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
     * @return the ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the direccion1
     */
    public String getDireccion1() {
        return direccion1;
    }

    /**
     * @param direccion1 the direccion1 to set
     */
    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    /**
     * @return the direccion2
     */
    public String getDireccion2() {
        return direccion2;
    }

    /**
     * @param direccion2 the direccion2 to set
     */
    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    /**
     * @return the telefonoFijo
     */
    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    /**
     * @param telefonoFijo the telefonoFijo to set
     */
    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    /**
     * @return the telefonoMovil
     */
    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    /**
     * @param telefonoMovil the telefonoMovil to set
     */
    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fechaIngreso
     */
    public String getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the contacto
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * @return the profesion
     */
    public String getProfesion() {
        return profesion;
    }

    /**
     * @param profesion the profesion to set
     */
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    /**
     * @return the estadoCivil
     */
    public Character getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(Character estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the tratamiento
     */
    public String getTratamiento() {
        return tratamiento;
    }

    /**
     * @param tratamiento the tratamiento to set
     */
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    /**
     * @return the sexo
     */
    public Character getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the hijos
     */
    public Short getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(Short hijos) {
        this.hijos = hijos;
    }

    /**
     * @return the habilitado
     */
    public Character getHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(Character habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * @return the listaCategoria
     */
    public List<SelectItem> getListaCategoria() {
        return listaCategoria;
    }

    /**
     * @param listaCategoria the listaCategoria to set
     */
    public void setListaCategoria(List<SelectItem> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class PersonaBean extends AbstractPageBean<Persona> {

    @EJB
    private PersonaFacade facade;
    private Persona persona;
    //Lista
    private List<SelectItem> listaCategoria;
    //Campos de busqueda
    private String id;
    private String documento;
    private String nombre;
    private Integer categoria;
    private String direccion;
    private String telefonoMovil;
    private Character estado;
    private Character fisica;
    private Boolean habilitado;

    /** Creates a new instance of PersonaBean */
    public PersonaBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        this.id = null;
        this.fisica = null;
        this.documento = null;
        this.categoria = null;
        this.nombre = null;
        this.direccion = null;
        this.telefonoMovil = null;
        this.estado = null;
        this.habilitado = null;
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
    public String listar() {
        limpiarCampos();
        setNav("persona");
        setDesde(0);
        setMax(10);
        if (facade.getOrden() == null) {
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
        getFacade().setEntity(new Persona(documento, fisica, nombre, direccion, telefonoMovil, new Categoria(categoria)));
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategoria = JsfUtils.getSelectItems(
                new CategoriaFacade().findBetween(
                CategoriaEnum.PERSONA_DESDE.getSymbol(), CategoriaEnum.PERSONA_HASTA.getSymbol()), !getModificar());
    }

    @Override
    public String buscar() {
        getFacade().setEntity(new Persona(documento, fisica, nombre, direccion, telefonoMovil, new Categoria(categoria)));
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        setPersona(new Persona());
        obtenerListas();
        return "persona";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.id = request.getParameter("radio");
        if (this.id != null) {
            try {
                setPersona(facade.find(Integer.valueOf(id)));
                setHabilitado(getPersona().getHabilitado().equals('S') ? Boolean.TRUE : Boolean.FALSE);
                setActivo(getPersona().getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(PersonaBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            return "persona";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            getFacade().setEntity(persona);
            if (getModificar()) {
                getFacade().getEntity().setHabilitado(habilitado ? 'S' : 'N');
                getFacade().getEntity().setActivo(getActivo() ? 'S' : 'N');
                getFacade().guardar();
                setInfoMessage(null, facade.ex2);
            } else {
                getFacade().getEntity().setFechaIngreso(new Date());
                getFacade().getEntity().setHabilitado('S');
                getFacade().create();
                setInfoMessage(null, getFacade().ex1);
            }
        } else {
            return null;
        }
        return this.listar();
    }

    @Override
    public String cancelar() {
        setPersona(new Persona());
        getFacade().setEntity(getPersona());
        return this.listar();
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
        setPersona(new Persona());
        getFacade().setEntity(persona);
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Integer[]{0, 10});
        getFacade().setOrden(new Orden("id", false));
        this.setValido((Boolean) true);
        getFacade().setEntity(getPersona());
        getFacade().setRango(new Integer[]{0, 10});
        this.filtrar();
        return getNav();
    }

    @Override
    boolean validar() {
        if (persona.getFisica().equals('X')) {
            setErrorMessage("frm:fisica", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (persona.getDocumento().trim().equals("")) {
            setErrorMessage("frm:documento", "Ingrese un valor");
            res = false;
        }
        if (persona.getNombre().trim().equals("")) {
            setErrorMessage("frm:nombre", "Ingrese un nombre");
            res = false;
        }
        if (persona.getDireccion1().trim().equals("")) {
            setErrorMessage("frm:direccion1", "Ingrese una Dirección");
            res = false;
        }
        if (persona.getTelefonoFijo().trim().equals("") && persona.getTelefonoMovil().trim().equals("")) {
            setErrorMessage("frm:telefonoFijo", "Ingrese por lo menos un Número de teléfono");
            res = false;
        }
        if (persona.getTratamiento().trim().equals("X")) {
            String component = persona.getFisica().equals('S') ? "frm:tratamiento" : "frm:tratamiento2";
            setErrorMessage(component, "Seleccione un valor");
            res = false;
        }
        if (persona.getFisica().equals('N')) {
            //Persona Juridica
            persona.setRuc(documento);
            if (persona.getContacto().trim().equals("")) {
                setErrorMessage("frm:contacto", "Ingrese un Contacto");
                res = false;
            }
        } else if (persona.getFisica().equals('S')) {
            //Persona Fisica
            if (persona.getSexo().equals('X')) {
                setErrorMessage("frm:sexo", "Seleccione un valor");
                res = false;
            }
            if (persona.getEstadoCivil().equals('X')) {
                setErrorMessage("frm:estadoCivil", "Seleccione un valor");
                res = false;
            }
            if (persona.getProfesion().trim().equals("")) {
                setErrorMessage("frm:profesion", "Ingrese un valor");
                res = false;
            }
            if (persona.getHijos() == null || persona.getHijos() > 10 || persona.getHijos() < 0) {
                setErrorMessage("frm:hijos", "Ingrese un valor");
                res = false;
            }
            if (persona.getFechaNacimiento() == null) {
                setErrorMessage("frm:fechaNacimiento", "Ingrese una fecha de nacimiento");
                res = false;
            } else {
                Calendar ahora = GregorianCalendar.getInstance();
                try {
                    Calendar fechaIntroducida = GregorianCalendar.getInstance();
                    fechaIntroducida.setTime(persona.getFechaNacimiento());
                    int anhoNacimiento = fechaIntroducida.get(GregorianCalendar.YEAR);
                    if (anhoNacimiento < 1900) {
                        fechaIntroducida.set(Calendar.YEAR, 1900 + anhoNacimiento);
                    }
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
                    persona.setFechaNacimiento(fechaIntroducida.getTime());
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

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
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
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
     * @return the estado
     */
    public Character getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Character estado) {
        this.estado = estado;
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
     * @return the habilitado
     */
    public Boolean getHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
}

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
import javax.faces.application.FacesMessage;
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

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class PersonaBean {

    @EJB
    private PersonaFacade facade;
    private PersonaPK pk;
    private Persona c;
    private List<Persona> lista;
    private Integer desde;
    private Integer max;
    private Integer total;
    private String nav = "listapersonas";
    private String id;
    private Boolean valido;
    private DateFormat df;
    private Calendar ahora;
    private CategoriaFacade cf;
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
    private String edad;
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

    private void deEntity() {
        this.setId(c.getPersonaPK().getId().toString());
        this.setDocumento(c.getPersonaPK().getDocumento());
        this.setPk(c.getPersonaPK());
        this.setNombre(getC().getNombre());
        this.setDireccion1(getC().getDireccion1());
        this.setDireccion2(getC().getDireccion2());
        this.setTelefonoFijo(getC().getTelefonoFijo());
        this.setTelefonoMovil(getC().getTelefonoMovil());
        this.setEmail(getC().getEmail());
        this.setFechaIngreso(getC().getFechaIngresoString());
        this.setRuc(getC().getRuc());
        this.setContacto(getC().getContacto());
        this.setProfesion(c.getProfesion());
        this.setEstadoCivil(c.getEstadoCivil());
        this.setFechaNacimiento(c.getFechaNacimientoString());
        this.setTratamiento(c.getTratamiento());
        this.setFisica(c.getFisica());
        this.setSexo(c.getSexo());
        this.setHijos(c.getHijos());
        this.setHabilitado(c.getHabilitado());
        this.setCategoria(c.getCategoria());
    }

    private void deCampos() {
        setC(new Persona(pk));
        if (this.id != null && !this.id.trim().equals("")) {
            this.getC().getPersonaPK().setId(new Integer(getId()));
        } else {
            this.getC().getPersonaPK().setId(null);
        }
        if (this.getFisica() != null && (this.getFisica().equals('S') || this.getFisica().equals('N'))) {
            this.c.setFisica(fisica);
        } else {
            this.c.setFisica(null);
        }
        if (this.documento != null && !this.documento.trim().equals("")) {
            this.getC().getPersonaPK().setDocumento(documento);
        } else {
            this.getC().getPersonaPK().setDocumento(null);
        }
        if (this.getNombre() != null && !this.nombre.trim().equals("")) {
            this.getC().setNombre((getNombre()));
        } else {
            this.getC().setNombre(null);
        }
        if (this.getDireccion1() != null && !this.direccion1.trim().equals("")) {
            this.getC().setDireccion1((direccion1));
        } else {
            this.getC().setDireccion1(null);
        }
        if (this.getDireccion2() != null && !this.direccion2.trim().equals("")) {
            this.getC().setDireccion2((direccion2));
        } else {
            this.getC().setDireccion2(null);
        }
        if (this.getTelefonoFijo() != null && !this.telefonoFijo.trim().equals("")) {
            this.getC().setTelefonoFijo((telefonoFijo));
        } else {
            this.getC().setTelefonoFijo(null);
        }
        if (this.getTelefonoMovil() != null && !this.telefonoMovil.trim().equals("")) {
            this.getC().setTelefonoMovil((telefonoMovil));
        } else {
            this.getC().setTelefonoMovil(null);
        }
        if (this.getEmail() != null && !this.email.trim().equals("")) {
            this.getC().setEmail((getEmail()));
        } else {
            this.getC().setEmail(null);
        }
        if (this.getFechaIngreso() != null && !this.fechaIngreso.trim().equals("")) {
            this.getC().setFechaIngresoString(fechaIngreso);
        } else {
            this.getC().setFechaIngreso(null);
        }
        if (this.getRuc() != null && !this.ruc.trim().equals("")) {
            this.getC().setRuc(getRuc());
        } else {
            this.getC().setRuc(null);
        }
        if (this.getContacto() != null && !this.contacto.trim().equals("")) {
            this.getC().setContacto(getContacto());
        } else {
            this.getC().setContacto(null);
        }
        if (this.getProfesion() != null && !this.profesion.trim().equals("")) {
            this.c.setProfesion(getProfesion());
        } else {
            this.c.setProfesion(null);
        }
        if (this.getEstadoCivil() != null) {
            this.c.setEstadoCivil(getEstadoCivil());
        } else {
            this.c.setEstadoCivil(null);
        }
        if (this.getFechaNacimiento() != null && !this.fechaNacimiento.trim().equals("")) {
            this.c.setFechaNacimientoString(getFechaNacimiento());
        } else {
            this.c.setFechaNacimiento(null);
        }
        if (this.getTratamiento() != null && !this.tratamiento.trim().equals("")) {
            this.c.setTratamiento(getTratamiento());
        } else {
            this.c.setTratamiento(null);
        }
        if (this.getSexo() != null) {
            this.c.setSexo(sexo);
        } else {
            this.c.setSexo(null);
        }
        if (this.getHabilitado() != null && (this.getHabilitado().equals('S') || this.getHabilitado().equals('N'))) {
            this.c.setHabilitado(habilitado);
        } else {
            this.c.setHabilitado(null);
        }
        if (this.getCategoria() != null && !this.categoria.equals(-1)) {
            this.getC().getPersonaPK().setId(new Integer(getId()));
        } else {
            this.getC().getPersonaPK().setId(null);
        }
    }

    private void limpiarCampos() {
        this.setId(null);
        this.setDocumento(null);
        this.setPk(new PersonaPK());
        this.setNombre(null);
        this.setDireccion1(null);
        this.setDireccion2(null);
        this.setTelefonoFijo(null);
        this.setTelefonoMovil(null);
        this.setEmail(null);
        this.setFechaIngreso(null);
        this.setRuc(null);
        this.setContacto(null);
        this.setProfesion(null);
        this.setEstadoCivil(null);
        this.setFechaNacimiento(null);
        this.setTratamiento(null);
        this.setSexo(null);
        this.setFisica(null);
        this.setHabilitado(null);
        this.setHijos(null);
        this.setCategoria(-1);
    }

    public String listar() {
        limpiarCampos();
        deCampos();
        this.setDesde(new Integer(0));
        this.setMax(new Integer(10));
        this.setValido((Boolean) true);
        this.filtrar();
        if (this.getLista().isEmpty()) {
            setErrorMessage(null, facade.r0);
        }
        return nav;
    }

    public List<Persona> filtrar() {
        deCampos();
        setLista(new ArrayList<Persona>());
        int[] range = {this.getDesde(), this.getMax()};
        setLista(facade.findRange());
        return getLista();
    }

    public String buscar() {
        setDesde((Integer) 0);
        setMax((Integer) 10);
        getFacade().setContador(null);
        this.filtrar();
        if (this.getLista().isEmpty()) {
            setErrorMessage(null, facade.c0);
        }
        return nav;
    }

    public String nuevo() {
        setC(new Persona(new PersonaPK()));
        getC().setFisica('X');
        getC().setTratamiento("X");
        getC().setEstadoCivil('X');
        this.fechaNacimiento = "";
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

    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.setId((String) request.getParameter("radio"));
        if (this.getId() != null) {
            pk = new PersonaPK(new Integer(
                    this.id.substring(0, this.id.indexOf(":"))), this.id.substring(this.id.indexOf(":") + 1, this.id.length()));
            try {
                this.setC(facade.find(pk));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            deEntity();
            listaCategoria = new ArrayList<SelectItem>();
            listaCategorias = new CategoriaFacade().findBetween(20, 30);
            if (!listaCategorias.isEmpty()) {
                Iterator<Categoria> it = listaCategorias.iterator();
                do {
                    Categoria x = it.next();
                    listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
                } while (it.hasNext());

            }
            return "modificarpersona";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    public String guardarNuevo() {
        boolean validado = validarNuevo();
        if (validado) {
            this.c.setHabilitado('S');
            this.c.setFechaIngreso(new Date());
            //boolean exito = facade.create();
//            if (exito) {
//                setInfoMessage(null, facade.ex1);
//                return this.listar();
//            } else {
//                FacesContext.getCurrentInstance().addMessage("frm:id", new FacesMessage("Id ya existe"));
//                return null;
//            }
//        } else {
//            return null;
//        }
        }
        return null;
    }

    public boolean validarNuevo() {
        if (this.c.getFisica().equals('X')) {
            setErrorMessage("frm:fisica", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (this.c.getPersonaPK().getDocumento().trim().equals("")) {
            setErrorMessage("frm:documento", "Ingrese un valor");
            res = false;
        }
        if (this.c.getNombre().trim().equals("")) {
            setErrorMessage("frm:nombre", "Ingrese un nombre");
            res = false;
        }
        if (this.c.getDireccion1().trim().equals("")) {
            setErrorMessage("frm:direccion1", "Ingrese una Dirección");
            res = false;
        }
        if (this.c.getTelefonoFijo().trim().equals("") && this.c.getTelefonoMovil().trim().equals("")) {
            setErrorMessage("frm:telefonoFijo", "Ingrese por lo menos un Número de teléfono");
            res = false;
        }
        if (this.c.getTratamiento().trim().equals("X")) {
            String component = c.getFisica().equals('S') ? "frm:tratamiento" : "frm:tratamiento2";
            setErrorMessage(component, "Seleccione un valor");
            res = false;
        }
        if (this.c.getFisica().equals('N')) {
            //Persona Juridica
            this.c.setRuc(documento);
            if (this.c.getContacto().trim().equals("")) {
                setErrorMessage("frm:contacto", "Ingrese un Contacto");
                res = false;
            }
        } else if (this.c.getFisica().equals('S')) {
            //Persona Fisica
            if (this.c.getSexo().equals('X')) {
                setErrorMessage("frm:sexo", "Seleccione un valor");
                res = false;
            }
            if (this.c.getEstadoCivil().equals('X')) {
                setErrorMessage("frm:estadoCivil", "Seleccione un valor");
                res = false;
            }
            if (this.c.getProfesion().trim().equals("")) {
                setErrorMessage("frm:profesion", "Ingrese un valor");
                res = false;
            }
            if (this.getHijos() == null || this.getHijos() > 10 || this.getHijos() < 0) {
                setErrorMessage("frm:hijos", "Ingrese un valor");
                res = false;
            } else {
                this.c.setHijos(hijos);
            }
            if (this.getCategoria() == null || this.getCategoria() == -1) {
                setErrorMessage("frm:categoria", "Seleccione un valor");
                res = false;
            } else {
                this.c.setCategoria(getCategoria());
            }
            if (this.getFechaNacimiento().trim().equals("")) {
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
                    this.c.setFechaNacimiento(fecha);
                } catch (Exception e) {
                    e.printStackTrace();
                    setErrorMessage("frm:fechaNacimiento", "Ingrese una fecha con el formato dd/MM/yyyy");
                    res = false;
                }
            }
        }
        return res;
    }

    public boolean validarModificar() {
        if (this.c.getFisica().equals('X')) {
            setErrorMessage("frm:fisica", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (this.c.getPersonaPK().getDocumento().trim().equals("")) {
            setErrorMessage("frm:documento", "Ingrese un valor");
            res = false;
        }
        if (this.c.getNombre().trim().equals("")) {
            setErrorMessage("frm:nombre", "Ingrese un nombre");
            res = false;
        }
        if (this.c.getDireccion1().trim().equals("")) {
            setErrorMessage("frm:direccion1", "Ingrese una Dirección");
            res = false;
        }
        if (this.c.getTelefonoFijo().trim().equals("") && this.c.getTelefonoMovil().trim().equals("")) {
            setErrorMessage("frm:telefonoFijo", "Ingrese por lo menos un Número de teléfono");
            res = false;
        }
        if (this.c.getTratamiento().trim().equals("X")) {
            String component = c.getFisica().equals('S') ? "frm:tratamiento" : "frm:tratamiento2";
            setErrorMessage(component, "Seleccione un valor");
            res = false;
        }
        if (this.c.getFisica().equals('N')) {
            //Persona Juridica
            this.c.setFisica(fisica);
            this.c.setRuc(documento);
            if (this.c.getContacto().trim().equals("")) {
                setErrorMessage("frm:contacto", "Ingrese un Contacto");
                res = false;
            }
        } else if (this.c.getFisica().equals('S')) {
            //Persona Fisica
            this.c.setFisica(fisica);
            if (this.c.getSexo().equals('X')) {
                setErrorMessage("frm:sexo", "Seleccione un valor");
                res = false;
            }
            if (this.c.getEstadoCivil().equals('X')) {
                setErrorMessage("frm:estadoCivil", "Seleccione un valor");
                res = false;
            }
            if (this.c.getProfesion().trim().equals("")) {
                setErrorMessage("frm:profesion", "Ingrese un valor");
                res = false;
            }
            if (this.getHijos() == null || this.getHijos() > 10 || this.getHijos() < 0) {
                setErrorMessage("frm:hijos", "Ingrese un valor");
                res = false;
            } else {
                this.c.setHijos(hijos);
            }
            if (this.getFechaNacimiento().trim().equals("")) {
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
                    this.c.setFechaNacimiento(fecha);
                } catch (Exception e) {
                    e.printStackTrace();
                    setErrorMessage("frm:fechaNacimiento", "Ingrese una fecha con el formato dd/MM/yyyy");
                    res = false;
                }
            }
        }
        return res;
    }

    public String guardarModificar() {
        boolean validado = validarModificar();
        if (validado) {
            //facade.guardar(getC());
            setInfoMessage(null, facade.ex2);
            return this.listar();
        } else {
            return null;
        }
    }

    public String cancelar() {
        return this.listar();
    }

    public String todos() {
        limpiarCampos();
        //facade.setCol(null);
        this.setValido((Boolean) true);
        deCampos();
        setDesde((Integer) 0);
        setMax((Integer) 10);
        this.filtrar();
        return nav;
    }

    public String anterior() {
        desde -= max;
        if (desde < 10) {
            desde = 0;
        }
        int[] range = {desde, max};
        //this.lista = getFacade().anterior(range, getC());

        return nav;
    }

    public String siguiente() {
        desde += max;
        if (desde > this.total) {
            desde = this.total - 1;
        } else {
            int[] range = {desde, max};
            //this.lista = getFacade().siguiente(range, getC());
        }
        return nav;
    }

    public Integer getUltimoItem() {
        deCampos();
        // PersonaFacade.c = getC();
        int[] range = {getDesde(), getMax()};
        return null;//getFacade().getUltimoItem(range);
    }

    /**
     * @return the lista
     */
    public List<Persona> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<Persona> lista) {
        this.lista = lista;
    }

    /**
     * @return the desde
     */
    public Integer getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    /**
     * @return the max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        //this.total = getFacade().count();
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the facade
     */
    public PersonaFacade getFacade() {
        return facade;
    }

    /**
     * @return the c
     */
    public Persona getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(Persona c) {
        this.c = c;
    }

    protected void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    protected void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
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

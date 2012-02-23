/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.ArrayList;
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
import py.com.bej.base.prod.entity.Clientes;
import py.com.bej.base.prod.entity.Proveedores;
import py.com.bej.base.prod.session.ClientesProduccionFacade;
import py.com.bej.base.prod.session.ProveedoresProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class PersonaBean extends AbstractPageBean<Persona> {

    @EJB
    private ProveedoresProduccionFacade proveedoresProduccionFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private ClientesProduccionFacade clientesProduccionFacade;
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

    public CategoriaFacade getCategoriaFacade() {
        if (this.categoriaFacade == null) {
            this.categoriaFacade = new CategoriaFacade();
        }
        return categoriaFacade;
    }

    public ClientesProduccionFacade getClientesProduccionFacade() {
        if (this.clientesProduccionFacade == null) {
            this.clientesProduccionFacade = new ClientesProduccionFacade();
        }
        return clientesProduccionFacade;
    }

    public String importar() {

        return "importarPersona";
    }

    public String migrarClientes() throws Exception {
        String res = null;
        int total = 0;
        //Produccion
        clientesProduccionFacade = new ClientesProduccionFacade();
        List<Clientes> listaClientesEnProduccion = null;
        //Desarrollo
        Categoria categoriaProduccion = null;
        //MIGRAR
        listaClientesEnProduccion = clientesProduccionFacade.buscarOrdenado();
        if (!listaClientesEnProduccion.isEmpty()) {
            Persona p = null;
            List<Persona> lista = new ArrayList<Persona>();
            String documentoProduccion = null;
            String nombreProduccion = null;
            String direccion1 = null;
            String telefonoFijo = null;
            String telefonoMovilProduccion = null;
            String email = null;
            Date fechaIngreso;
            categoriaProduccion = getCategoriaFacade().find(new Integer(CategoriaEnum.CLIENTE_PF.getSymbol()));

            for (Clientes c : listaClientesEnProduccion) {
                //Omitir propietario
                if (c.getClientesPK().getCedulaRuc().trim().equals(ConfiguracionEnum.PROPIETARIO.getSymbol())) {
                    continue;
                }
                //Documento
                if (c.getClientesPK().getCedulaRuc().trim().length() < 6) {
                    documentoProduccion = c.getClientesPK().getCedulaRuc().trim() + "99999";
                } else if (c.getClientesPK().getCedulaRuc().trim().length() > 11) {
                    documentoProduccion = c.getClientesPK().getCedulaRuc().trim().substring(0, 10);
                } else {
                    documentoProduccion = c.getClientesPK().getCedulaRuc().trim();
                }
                //Nombre
                if (c.getNombreApellido() == null) {
                    nombreProduccion = "Sin Nombre";
                } else if (c.getNombreApellido().trim().length() < 5) {
                    nombreProduccion = c.getNombreApellido().trim() + "AAAAA";
                } else if (c.getNombreApellido().trim().length() > 50) {
                    nombreProduccion = c.getNombreApellido().trim().substring(0, 49);
                } else {
                    nombreProduccion = c.getNombreApellido().trim();
                }
                //Direccion
                if (c.getDireccion() == null) {
                    direccion1 = "Sin Direccion registrada";
                } else if (c.getDireccion().trim().length() < 1) {
                    direccion1 = c.getDireccion().trim() + "A";
                } else if (c.getDireccion().trim().length() > 50) {
                    direccion1 = c.getDireccion().trim().substring(0, 49);
                } else {
                    direccion1 = c.getDireccion().trim();
                }

                //Telefono Fijo
                if (c.getTelefono() == null) {
                    telefonoFijo = "000000";
                } else if (c.getTelefono().trim().length() > 11) {
                    telefonoFijo = c.getTelefono().trim().substring(0, 10);
                } else {
                    telefonoFijo = c.getTelefono().trim();
                }
                //Telefono Movil
                if (c.getMovil() == null) {
                    telefonoMovilProduccion = "000000";
                } else if (c.getMovil().trim().length() > 13) {
                    telefonoMovilProduccion = c.getMovil().trim().substring(0, 12);
                } else {
                    telefonoMovilProduccion = c.getMovil().trim();
                }
                //Email
                if (c.getEmail() == null) {
                    email = "mail@mail.com";
                } else {
                    email = c.getEmail().trim();
                }
                //Fecha de Ingreso
                if (c.getFechaIngreso() == null) {
                    fechaIngreso = new Date();
                } else {
                    fechaIngreso = c.getFechaIngreso();
                }
                p = new Persona(c.getClientesPK().getCodigoCliente(), documentoProduccion, 'S', nombreProduccion, direccion1, null, telefonoFijo,
                        telefonoMovilProduccion, email, fechaIngreso, documentoProduccion, null, new Date(), 'S', null, "Sr.", 'H', Short.valueOf("0"), 'S', categoriaProduccion, 'S', new Date());
                LOGGER.log(Level.INFO, "Se va a agregar en la lista el registro con Id {0}", p.getId());
                lista.add(p);
                total++;
            }
            try {
                total = getFacade().cargaMasiva(lista);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        res = "Total de registros importados: " + total;
        return todos();
    }

    public String migrarProveedores() {
        //Proveedores
        int total = 0;
        List<Persona> lista = null;
        Categoria categoriaProduccion = null;
        Persona p;
        //Produccion
        proveedoresProduccionFacade = new ProveedoresProduccionFacade();
        List<Proveedores> listaProveedores = new ArrayList<Proveedores>();
        //Desarrollo
        String codProveedorProduccion = null;
        String tratamientoProveedorProduccion = null;
        String nombreProveedorProduccion = null;
        String direccion1ProveedorProduccion = null;
        String telefonoProveedorProduccion = null;
        String emailProveedorProduccion = null;
        lista = new ArrayList<Persona>();
        categoriaProduccion = getCategoriaFacade().find(new Integer(CategoriaEnum.PROVEEDOR.getSymbol()));
        //Buscar
        listaProveedores = proveedoresProduccionFacade.findAll();
        if (!listaProveedores.isEmpty()) {
            for (Proveedores pr : listaProveedores) {
                if (pr.getCodProveedor().trim().equals("ALEA634530T")) {
                    codProveedorProduccion = "80002740-0";
                    tratamientoProveedorProduccion = "S.A.";
                    nombreProveedorProduccion = "ALEX S.A.";
                    direccion1ProveedorProduccion = "America y Calle 2 Bo. Aeropuerto - Luque";
                    telefonoProveedorProduccion = "021 645900";
                    emailProveedorProduccion = "alexa@alexsa.com.py";
                } else if (pr.getCodProveedor().trim().equals("CHAA9583400")) {
                    codProveedorProduccion = "80013744-2";
                    tratamientoProveedorProduccion = "S.A.E.C.A";
                    nombreProveedorProduccion = "CHACOMER S.A.E.C.A.";
                    direccion1ProveedorProduccion = "Av. Eusebio Ayala 3321 c/ Av. Rca. Argentina - Asuncion";
                    telefonoProveedorProduccion = "021 5180000";
                    emailProveedorProduccion = "chacomer@chacomer.com.py";
                } else if (pr.getCodProveedor().trim().equals("MFEB998270M")) {
                    codProveedorProduccion = "80020496-4";
                    tratamientoProveedorProduccion = "S.A.C.I.";
                    nombreProveedorProduccion = "Metalurgica Fernandez S.A.C.I.";
                    direccion1ProveedorProduccion = "Ruta 1 Km. 16,5 - Capiata";
                    telefonoProveedorProduccion = "208074";
                    emailProveedorProduccion = "mail@mail.com";
                } else if (pr.getCodProveedor().trim().equals("REIB796460N")) {
                    codProveedorProduccion = "80001307-7";
                    tratamientoProveedorProduccion = "S.R.L.";
                    nombreProveedorProduccion = "Reimpex S.R.L.";
                    direccion1ProveedorProduccion = "RI 4 Curupayty Nº 268 c/ Av. Boggiani - Asuncion";
                    telefonoProveedorProduccion = "021 602460";
                    emailProveedorProduccion = "mail@mail.com";
                } else {
                    codProveedorProduccion = pr.getCodProveedor().trim();
                    tratamientoProveedorProduccion = "S.A.";
                    nombreProveedorProduccion = pr.getNombre().trim();
                    direccion1ProveedorProduccion = pr.getDireccion().trim();
                    telefonoProveedorProduccion = pr.getTelefono().trim();
                    emailProveedorProduccion = "mail@mail.com";
                }
                p = new Persona(0, codProveedorProduccion, 'N', nombreProveedorProduccion, direccion1ProveedorProduccion, null, telefonoProveedorProduccion,
                        null, emailProveedorProduccion, new Date(), codProveedorProduccion, null, new Date(), 'X', null, tratamientoProveedorProduccion, null, null, 'S', categoriaProduccion, 'S', new Date());
                LOGGER.log(Level.INFO, "Se va a agregar en la lista el proveedor con documento {0}", p.getDocumento());
                lista.add(p);
            }
            try {
                total = getFacade().cargaMasiva(lista);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        }
        setInfoMessage(null, "Total de registros importados: " + total);

        return todos();
    }

    @Override
    public String listar() {
        limpiarCampos();
        setNav("persona");
        LoginBean.getInstance().setUbicacion("Personas");
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
        if (documento != null && documento.trim().equals("")) {
            documento = null;
        }
        if (fisica != null && fisica.equals('X')) {
            fisica = null;
        }
        if (nombre != null && nombre.trim().equals("")) {
            nombre = null;
        }
        if (direccion != null && direccion.trim().equals("")) {
            direccion = null;
        }
        if (telefonoMovil != null && telefonoMovil.trim().equals("")) {
            telefonoMovil = null;
        }
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
        LoginBean.getInstance().setUbicacion("Agregar Persona");
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        setPersona(new Persona());
        obtenerListas();
        return "persona";
    }

    @Override
    public String modificar() {
        LoginBean.getInstance().setUbicacion("Modificar Persona");
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

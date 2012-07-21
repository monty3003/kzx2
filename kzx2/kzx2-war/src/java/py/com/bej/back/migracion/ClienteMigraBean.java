/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.back.migracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class ClienteMigraBean implements Serializable {

    @EJB
    private ProveedoresProduccionFacade proveedoresProduccionFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private ClientesProduccionFacade clientesProduccionFacade;
    @EJB
    private PersonaFacade facade;
    public final static Logger LOGGER = Logger.getLogger(ClienteMigraBean.class.getName());

    /** Creates a new instance of ClienteMigraBean */
    public ClienteMigraBean() {
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
                    if (telefonoFijo.startsWith("098")
                            || telefonoFijo.startsWith("097")
                            || telefonoFijo.startsWith("096")
                            || telefonoFijo.startsWith("099")) {
                        c.setMovil(telefonoFijo);
                    } else {
                        telefonoMovilProduccion = "000000";
                    }
                } else if (c.getMovil().trim().length() > 13) {
                    telefonoMovilProduccion = c.getMovil().trim().substring(0, 12);
                } else {
                    if (c.getMovil().trim().contains(" ") || c.getMovil().trim().contains("-")) {
                        String[] cadenaLimpia = c.getMovil().split(" ");
                        telefonoMovilProduccion = "";
                        for (String s : cadenaLimpia) {
                            telefonoMovilProduccion = telefonoMovilProduccion + s;
                        }
                        if (telefonoMovilProduccion.contains("-")) {
                            telefonoMovilProduccion = telefonoMovilProduccion.substring(telefonoMovilProduccion.indexOf("-", telefonoMovilProduccion.indexOf("-")));
                        }
                    } else {
                        telefonoMovilProduccion = c.getMovil().trim();
                    }
                    LOGGER.log(Level.FINE, "{0};{1};{2}", new Object[]{c.getNombreApellido(), telefonoFijo, telefonoMovilProduccion});
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
                LOGGER.log(Level.FINE, "Se va a agregar en la lista el registro con Id {0}", p.getId());
                lista.add(p);
                total++;
            }
            try {
                total = getFacade().cargaMasiva(lista);
            } catch (Exception ex) {
                Logger.getLogger(ClienteMigraBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setInfoMessage(null, "Total de registros importados: " + total);
        return "persona";
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
                    telefonoProveedorProduccion = "021645900";
                    emailProveedorProduccion = "alexa@alexsa.com.py";
                } else if (pr.getCodProveedor().trim().equals("CHAA9583400")) {
                    codProveedorProduccion = "80013744-2";
                    tratamientoProveedorProduccion = "S.A.E.C.A";
                    nombreProveedorProduccion = "CHACOMER S.A.E.C.A.";
                    direccion1ProveedorProduccion = "Av. Eusebio Ayala 3321 c/ Av. Rca. Argentina - Asuncion";
                    telefonoProveedorProduccion = "0215180000";
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
                    telefonoProveedorProduccion = "021602460";
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
                LOGGER.log(Level.FINE, "Se va a agregar en la lista el proveedor con documento {0}", p.getDocumento());
                lista.add(p);
            }
            try {
                total = getFacade().cargaMasiva(lista);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        }
        setInfoMessage(null, "Total de registros de Proveedores importados: " + total);

        return "persona";
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.entities.Ubicacion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.session.UbicacionFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class VentaMotosBean extends AbstractPageBean<Transaccion> {

    /** Creates a new instance of VentaMotosBean */
    public VentaMotosBean() {
    }
    @EJB
    private MotoFacade motoFacade;
    @EJB
    private UbicacionFacade ubicacionFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private MotostockFacade motostockFacade;
    @EJB
    private TransaccionFacade facade;
    private Transaccion venta;
    private String id;
    private DateFormat formatFechaHora;
    private NumberFormat formatNumero;
    private List<Categoria> listaCategorias;
    private List<SelectItem> listaCategoria;
    private List<Persona> listaClientes;
    private List<SelectItem> listaCliente;
    private List<SelectItem> listaGarante;
    private List<Motostock> listaMotos;
    private List<SelectItem> listaMoto;
    private List<Ubicacion> listaUbicaciones;
    private List<SelectItem> listaUbicacion;
    private Motostock motoVendida;
    @Digits(fraction = 0, integer = 20, message = "Ingrese un valor positivo")
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo igual o mayor a 1")
    @Max(value = 99, message = "Ingrese un valor igual o menor a 99")
    private Short cantidad;
    //Transaccion de Venta
    private String nMoto;
    @NotNull(message = "Ingrese un valor")
    private Integer codigo;
    @Size(min = 5, max = 45, message = "Ingrese un valor con el formato ###-###-######")
    @NotNull(message = "Ingrese un valor con el formato ###-###-######")
    @Pattern(regexp = "###-###-#####", message = "Ingrese un valor con el formato ###-###-######")
    private String comprobante;
    @NotNull(message = "Seleccione una fecha")
    private String fechaOperacion;
    @NotNull(message = "Seleccione una fecha")
    private String fechaEntrega;
//    @NotNull(message="Seleccione un valor")
//    @Min(value = 1, message = "Seleccione un valor")
    private Integer vendedor;
    @NotNull(message = "Seleccione un valor")
    @Min(value = 1, message = "Seleccione un valor")
    private Integer comprador;
    private Character anulado;
    @Digits(fraction = 0, integer = 7, message = "Ingrese un valor positivo")
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo igual o mayor a 1")
    @Max(value = Long.MAX_VALUE, message = "Ingrese un monto real")
    private String subTotalExentas;
    @Digits(fraction = 0, integer = 7, message = "Ingrese un valor positivo")
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo igual o mayor a 1")
    @Max(value = Long.MAX_VALUE, message = "Ingrese un monto real")
    private String subTotalGravadas10;
    private String subTotalGravadas5;
    @Digits(fraction = 0, integer = 7, message = "Ingrese un valor positivo")
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo igual o mayor a 1")
    @Max(value = Long.MAX_VALUE, message = "Ingrese un monto real")
    private String subtotal;
    private String totalIva5;
    @Digits(fraction = 0, integer = 7, message = "Ingrese un valor positivo")
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo igual o mayor a 1")
    @Max(value = Long.MAX_VALUE, message = "Ingrese un monto real")
    private String totalIva10;
    @Digits(fraction = 0, integer = 7, message = "Ingrese un valor positivo")
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo igual o mayor a 1")
    @Max(value = Long.MAX_VALUE, message = "Ingrese un monto real")
    private String totalIva;
    @Digits(fraction = 0, integer = 7, message = "Ingrese un valor positivo")
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo igual o mayor a 1")
    @Max(value = Long.MAX_VALUE, message = "Ingrese un monto real")
    private String toTal;
    private String totalDescuento;
    private String totalPagado;
    private String entregaInicial;
    private String cuotas;
    private String cantidadItems;
    private String montoCuotaIgual;
    private String descuento;
    private Character saldado;
    private Boolean usarFechaAhora = false;
    private Boolean usarFechaEntregaAhora = false;
    //Calculos Ajax
    private BigDecimal subTotalExentasX;
    private BigDecimal subTotalGravadas10X;
    private BigDecimal subTotalGravadas5X;
    private BigDecimal subtotalX;
    private BigDecimal totalIva5X;
    private BigDecimal totalIva10X;
    private BigDecimal totalIvaX;
    private BigDecimal toTalX;
    private BigDecimal descuentoX;
    private BigDecimal totalDescuentoX;
    private BigDecimal totalPagadoX;
    //Cliente
    private Integer clienteId;
    private Character clienteFisica;
    private String clienteDocumento;
    private String clienteRuc;
    private String clienteNombre;
    private String clienteDireccion1;
    private String clienteDireccion2;
    private String clienteTelefonoFijo;
    private String clienteTelefonoMovil;
    private String clienteEmail;
    private String clienteFechaIngreso;
    @Size(min = 5, max = 45, message = "Ingrese un nombre con longitud entre 5 y 45 caracteres")
    private String clienteContacto;
    private String clienteProfesion;
    private Character clienteEstadoCivil;
    private String clienteFechaNacimiento;
    private String clienteTratamiento;
    private Character clienteSexo;
    private Short clienteHijos;
    private Character clienteHabilitado;
    private Integer clienteCategoria;
    //Garante
    private Integer garanteId;
    private String garanteDocumento;
    private String garanteRuc;
    private String garanteNombre;
    private String garanteDireccion1;
    private String garanteDireccion2;
    private String garanteTelefonoFijo;
    private String garanteTelefonoMovil;
    private String garanteEmail;
    private String garanteFechaIngreso;
    private String garanteContacto;
    private String garanteProfesion;
    private Character garanteEstadoCivil;
    private String garanteFechaNacimiento;
    private String garanteTratamiento;
    private Character garanteSexo;
    private Short garanteHijos;
    private Character garanteHabilitado;
    private Integer garanteCategoria;
    //Credito
    private Integer garante;

    /**
     * @return the facade
     */
    public TransaccionFacade getFacade() {
        if (this.facade == null) {
            this.facade = new TransaccionFacade();
        }
        return facade;
    }

    /**
     * @return the facade
     */
    public CategoriaFacade getCategoriaFacade() {
        if (this.categoriaFacade == null) {
            this.categoriaFacade = new CategoriaFacade();
        }
        return categoriaFacade;
    }

    /**
     * @return the facade
     */
    public MotostockFacade getMotostockFacade() {
        if (this.motostockFacade == null) {
            this.motostockFacade = new MotostockFacade();
        }
        return motostockFacade;
    }

    /**
     * @return the facade
     */
    public PersonaFacade getPersonaFacade() {
        if (this.personaFacade == null) {
            this.personaFacade = new PersonaFacade();
        }
        return personaFacade;
    }

    /**
     * @return the facade
     */
    public UbicacionFacade getUbicacionFacade() {
        if (this.ubicacionFacade == null) {
            this.ubicacionFacade = new UbicacionFacade();
        }
        return ubicacionFacade;
    }

    /**
     * @return the facade
     */
    public MotoFacade getMotoFacade() {
        if (this.motoFacade == null) {
            this.motoFacade = new MotoFacade();
        }
        return motoFacade;
    }

    @Override
    public String listar() {
        setNav("listaventamotos");
        setDesde(0);
        setMax(10);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaCliente = new ArrayList<SelectItem>();
        getListaCliente().add(new SelectItem(-1, "-SELECCIONAR-"));
        listaGarante = new ArrayList<SelectItem>();
        getListaGarante().add(new SelectItem(-1, "-SELECCIONAR-"));
        listaUbicacion = new ArrayList<SelectItem>();
        listaUbicacion.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem('X', "-SELECCIONAR-"));
        obtenerListas();
        return getNav();
    }

    @Override
    List filtrar() {
        facade.setEntity(new Transaccion());
        getFacade().setEntity(venta);
        facade.getEntity().setCodigo(new Categoria(11));
//        facade.getEntity().setCodigoMax(20);
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategorias = getCategoriaFacade().findBetween(20, 29);
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }

        listaClientes = getPersonaFacade().findBetween(10, 19);
        if (!listaClientes.isEmpty()) {
            Iterator<Persona> it = listaClientes.iterator();
            do {
                Persona x = it.next();
                getListaCliente().add(new SelectItem(x.getId(), x.getNombre()));
            } while (it.hasNext());

        }

        listaUbicaciones = getUbicacionFacade().findAll();
        if (!listaUbicaciones.isEmpty()) {
            Iterator<Ubicacion> it = listaUbicaciones.iterator();
            do {
                Ubicacion x = it.next();
                listaUbicacion.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }
    }

    @Override
    public String buscar() {
        facade.setEntity(new Transaccion());
        getFacade().setEntity(venta);
        getFacade().setContador(null);
//        getFacade().getEntity().setCodigoMax(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        venta = new Transaccion(); getFacade().setEntity(venta);
        facade.setEntity(new Transaccion());
        facade.getEntity().setCodigo(new Categoria());
        facade.getEntity().setComprador(new Persona());
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaCliente = new ArrayList<SelectItem>();
        listaCliente.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaCliente.add(new SelectItem(0, "cargar nuevo..."));
        listaUbicacion = new ArrayList<SelectItem>();
        listaUbicacion.add(new SelectItem("-1", "-SELECCIONAR-"));
        obtenerListas();
        listaMotos = getMotostockFacade().findAll();
        if (!listaMotos.isEmpty()) {
            Iterator<Motostock> it = listaMotos.iterator();
            do {
                Motostock x = it.next();
                if (x.getVenta() == null) {
                    listaMoto.add(new SelectItem(x.getId(), x.getId() + "-" + x.getMoto().getModelo()));
                }
            } while (it.hasNext());
        }
        vendedor = 6;
        return "vendermotos";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        setId((String) request.getParameter("radio"));
        if (getId() != null) {
            try {
               venta = facade.find(new Integer(getId()));
            } catch (Exception e) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
                        listaCategoria = new ArrayList<SelectItem>();
            listaCliente = new ArrayList<SelectItem>();
            obtenerListas();
            return "modificarcompramoto";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            sumarResultados();
            getFacade().setEntity(venta);
            facade.getEntity().setSaldado('N');
            facade.getEntity().setAnulado('N');
            setInfoMessage(null, getFacade().ex1);
            venta = new Transaccion(); getFacade().setEntity(venta);
            return this.listar();
        } else {
            return null;
        }
    }

    @Override
    public boolean validar() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        boolean res = true;
        if (getCodigo() == -1) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        if (getComprobante().trim().equals("")) {
            setErrorMessage("frm:comprobante", "Ingrese el número de la factura con el formato XXX-XXX-XXXXXXX");
            res = false;
        }
        if (this.getFechaOperacion().trim().equals("")) {
            setErrorMessage("frm:fechaOperacion", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
            res = false;
        } else {
            Date fecha = null;
            try {
                fecha = formatFechaHora.parse(this.getFechaOperacion().trim());
                getFacade().getEntity().setFechaOperacion(fecha);
            } catch (Exception e) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
                setErrorMessage("frm:fechaOperacion", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
                res = false;
            }
        }
        if (this.getFechaEntrega().trim().equals("")) {
            setErrorMessage("frm:fechaEntrega", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
            res = false;
        } else {
            Date fecha = null;
            try {
                fecha = formatFechaHora.parse(this.getFechaEntrega().trim());
                getFacade().getEntity().setFechaEntrega(fecha);
            } catch (Exception e) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
                setErrorMessage("frm:fechaEntrega", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
                res = false;
            }
        }
        if (this.vendedor == -1) {
            setErrorMessage("frm:vendedor", "Seleccione un valor");
            res = false;
        }
        if (this.getSubTotalExentas().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getSubTotalExentas().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.getSubTotalGravadas5().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getSubTotalGravadas5().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.getSubTotalGravadas10().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getSubTotalGravadas10().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.getTotalPagado().trim().equals("")) {
            setErrorMessage("frm:totalPagado", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getTotalPagado().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:totalPagado", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.getEntregaInicial().trim().equals("")) {
            setErrorMessage("frm:entregaInicial", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getEntregaInicial().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:entregaInicial", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.getCantidadItems().trim().equals("")) {
            setErrorMessage("frm:cantidadItems", "Ingrese un valor");
            res = false;
        } else {
            Short subt = null;
            try {
                subt = new Short(this.getCantidadItems().trim());
                if (subt < 0) {
                    setErrorMessage("frm:cantidadItems", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    @Override
    public String todos() {
        venta = new Transaccion(); getFacade().setEntity(venta);
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Integer[]{0, 10});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
    }

    @Override
    public String cancelar() {
        venta = new Transaccion(); getFacade().setEntity(venta);
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

    public boolean sumarResultados() {
        boolean res = true;
        try {
            subTotalExentasX = BigDecimal.valueOf(formatNumero.parse(this.subTotalExentas).longValue());
            subTotalGravadas5X = BigDecimal.valueOf(formatNumero.parse(this.subTotalGravadas5).longValue());
            totalIva5X = BigDecimal.valueOf(formatNumero.parse(this.totalIva5).longValue());
            totalIva10X = BigDecimal.valueOf(formatNumero.parse(this.totalIva10).longValue());
            subTotalGravadas10X = BigDecimal.valueOf(formatNumero.parse(this.subTotalGravadas10).longValue());
        } catch (Exception e) {
            Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
        }
        if (subTotalGravadas5X.longValue() > 0) {
            totalIva5X = subTotalGravadas5X.multiply(new BigDecimal(0.05)).setScale(0, RoundingMode.HALF_UP);
        } else {
            res = false;
        }
        if (subTotalGravadas10X.longValue() > 0) {
            totalIva10X = subTotalGravadas10X.multiply(new BigDecimal(0.1)).setScale(0, RoundingMode.HALF_UP);
        } else {
            res = false;
        }
        subtotalX = subTotalExentasX.add(subTotalGravadas10X);
        try {
            descuentoX = new BigDecimal(this.descuento);
        } catch (Exception e) {
            descuentoX = BigDecimal.ZERO;
            res = false;
            Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
        }
        if (descuentoX.longValue() > 0) {
            totalDescuentoX = subtotalX.multiply(descuentoX);
        } else {
            totalDescuentoX = BigDecimal.ZERO;
        }
        toTalX = subtotalX.subtract(totalDescuentoX);
        //Pasar Datos
        totalIva5 = totalIva5X.toString();
        totalIva10 = totalIva10X.toString();
        subtotal = subtotalX.toString();
        totalDescuento = BigDecimal.ZERO.toString();
        descuento = totalDescuento;
        toTal = toTalX.toString();
        return res;
    }

    /*
     *=====================================PERSONAS===========================================
     *
     */
    private void clienteDeEntity() {
//        clienteId = personaFacade.getEntity().getId().toString();
        clienteDocumento = personaFacade.getEntity().getDocumento();
//        clientePk=personaFacade.getEntity();
        clienteNombre = personaFacade.getEntity().getNombre();
        clienteDireccion1 = personaFacade.getEntity().getDireccion1();
        clienteDireccion2 = personaFacade.getEntity().getDireccion2();
        clienteTelefonoFijo = personaFacade.getEntity().getTelefonoFijo();
        clienteTelefonoMovil = personaFacade.getEntity().getTelefonoMovil();
        clienteEmail = personaFacade.getEntity().getEmail();
        clienteFechaIngreso = personaFacade.getEntity().getFechaIngresoString();
        clienteRuc = personaFacade.getEntity().getRuc();
        clienteContacto = personaFacade.getEntity().getContacto();
        clienteProfesion = personaFacade.getEntity().getProfesion();
        clienteEstadoCivil = personaFacade.getEntity().getEstadoCivil();
        clienteFechaNacimiento = personaFacade.getEntity().getFechaNacimientoString();
        clienteTratamiento = personaFacade.getEntity().getTratamiento();
//        clienteFisica=personaFacade.getEntity().getFisica();
        clienteSexo = personaFacade.getEntity().getSexo();
        clienteHijos = personaFacade.getEntity().getHijos();
        clienteHabilitado = personaFacade.getEntity().getHabilitado();
        clienteCategoria = personaFacade.getEntity().getCategoria().getId();
    }

    private void garanteDeEntity() {
//        garanteId = personaFacade.getEntity().getId().toString();
        garanteDocumento = personaFacade.getEntity().getDocumento();
//        garantePk=personaFacade.getEntity();
        garanteNombre = personaFacade.getEntity().getNombre();
        garanteDireccion1 = personaFacade.getEntity().getDireccion1();
        garanteDireccion2 = personaFacade.getEntity().getDireccion2();
        garanteTelefonoFijo = personaFacade.getEntity().getTelefonoFijo();
        garanteTelefonoMovil = personaFacade.getEntity().getTelefonoMovil();
        garanteEmail = personaFacade.getEntity().getEmail();
        garanteFechaIngreso = personaFacade.getEntity().getFechaIngresoString();
        garanteRuc = personaFacade.getEntity().getRuc();
        garanteContacto = personaFacade.getEntity().getContacto();
        garanteProfesion = personaFacade.getEntity().getProfesion();
        garanteEstadoCivil = personaFacade.getEntity().getEstadoCivil();
        garanteFechaNacimiento = personaFacade.getEntity().getFechaNacimientoString();
        garanteTratamiento = personaFacade.getEntity().getTratamiento();
//        garanteFisica=personaFacade.getEntity().getFisica();
        garanteSexo = personaFacade.getEntity().getSexo();
        garanteHijos = personaFacade.getEntity().getHijos();
        garanteHabilitado = personaFacade.getEntity().getHabilitado();
        garanteCategoria = personaFacade.getEntity().getCategoria().getId();
    }

    private void clienteDeCampos() {
        if (comprador != null && comprador != -1) {
            personaFacade.getEntity().setId(comprador);
        } else {
            personaFacade.getEntity().setId(null);
        }
        if (clienteFisica != null && (clienteFisica.equals('S') || clienteFisica.equals('N'))) {
            personaFacade.getEntity().setFisica(clienteFisica);
        } else {
            personaFacade.getEntity().setFisica(null);
        }
        if (clienteDocumento != null && !clienteDocumento.trim().equals("")) {
            personaFacade.getEntity().setDocumento(clienteDocumento.trim());
        } else {
            personaFacade.getEntity().setDocumento(null);
        }
        if (clienteNombre != null && !clienteNombre.trim().equals("")) {
            personaFacade.getEntity().setNombre((clienteNombre.trim()));
        } else {
            personaFacade.getEntity().setNombre(null);
        }
        if (clienteDireccion1 != null && !clienteDireccion1.trim().equals("")) {
            personaFacade.getEntity().setDireccion1((clienteDireccion1.trim()));
        } else {
            personaFacade.getEntity().setDireccion1(null);
        }
        if (clienteDireccion2 != null && !clienteDireccion2.trim().equals("")) {
            personaFacade.getEntity().setDireccion2((clienteDireccion2.trim()));
        } else {
            personaFacade.getEntity().setDireccion2(null);
        }
        if (clienteTelefonoFijo != null && !clienteTelefonoFijo.trim().equals("")) {
            personaFacade.getEntity().setTelefonoFijo((clienteTelefonoFijo.trim()));
        } else {
            personaFacade.getEntity().setTelefonoFijo(null);
        }
        if (clienteTelefonoMovil != null && !clienteTelefonoMovil.trim().equals("")) {
            personaFacade.getEntity().setTelefonoMovil((clienteTelefonoMovil.trim()));
        } else {
            personaFacade.getEntity().setTelefonoMovil(null);
        }
        if (clienteEmail != null && !clienteEmail.trim().equals("")) {
            personaFacade.getEntity().setEmail((clienteEmail.trim().toLowerCase()));
        } else {
            personaFacade.getEntity().setEmail(null);
        }
//        if (clienteFechaIngreso != null && !clienteFechaIngreso.trim().equals("")) {
//            personaFacade.getEntity().setFechaIngresoString(clienteFechaIngreso);
//        } else {
//            personaFacade.getEntity().setFechaIngreso(null);
//        }
        if (clienteRuc != null && !clienteRuc.trim().equals("")) {
            personaFacade.getEntity().setRuc(clienteRuc);
        } else {
            personaFacade.getEntity().setRuc(null);
        }
        if (clienteContacto != null && !clienteContacto.trim().equals("")) {
            personaFacade.getEntity().setContacto(clienteContacto.trim());
        } else {
            personaFacade.getEntity().setContacto(null);
        }
        if (clienteProfesion != null && !clienteProfesion.trim().equals("")) {
            personaFacade.getEntity().setProfesion(clienteProfesion.trim());
        } else {
            personaFacade.getEntity().setProfesion(null);
        }
        if (clienteHijos != null && clienteHijos >= 0) {
            personaFacade.getEntity().setHijos(clienteHijos);
        } else {
            personaFacade.getEntity().setHijos(null);
        }
        if (clienteEstadoCivil != null) {
            personaFacade.getEntity().setEstadoCivil(clienteEstadoCivil);
        } else {
            personaFacade.getEntity().setEstadoCivil(null);
        }
//        if (clienteFechaNacimiento != null && !clienteFechaNacimiento.trim().equals("")) {
//            personaFacade.getEntity().setFechaNacimientoString(clienteFechaNacimiento);
//        } else {
//            personaFacade.getEntity().setFechaNacimiento(null);
//        }
        if (clienteTratamiento != null && !clienteTratamiento.trim().equals("")) {
            personaFacade.getEntity().setTratamiento(clienteTratamiento);
        } else {
            personaFacade.getEntity().setTratamiento(null);
        }
        if (clienteSexo != null) {
            personaFacade.getEntity().setSexo(clienteSexo);
        } else {
            personaFacade.getEntity().setSexo(null);
        }
        if (clienteHabilitado != null && (clienteHabilitado.equals('S') || clienteHabilitado.equals('N'))) {
            personaFacade.getEntity().setHabilitado(clienteHabilitado);
        } else {
            personaFacade.getEntity().setHabilitado(null);
        }
        if (clienteCategoria != null && !clienteCategoria.equals(-1)) {
            personaFacade.getEntity().setCategoria(new Categoria(clienteCategoria));
        } else {
            personaFacade.getEntity().setCategoria(null);
        }
    }

    private void garanteDeCampos() {
        if (garante != null && garante != -1) {
            personaFacade.getEntity().setId(garante);
        } else {
            personaFacade.getEntity().setId(null);
        }
        personaFacade.getEntity().setFisica('S');
        if (garanteDocumento != null && !garanteDocumento.trim().equals("")) {
            personaFacade.getEntity().setDocumento(garanteDocumento.trim());
        } else {
            personaFacade.getEntity().setDocumento(null);
        }
        if (garanteNombre != null && !garanteNombre.trim().equals("")) {
            personaFacade.getEntity().setNombre((garanteNombre.trim()));
        } else {
            personaFacade.getEntity().setNombre(null);
        }
        if (garanteDireccion1 != null && !garanteDireccion1.trim().equals("")) {
            personaFacade.getEntity().setDireccion1((garanteDireccion1.trim()));
        } else {
            personaFacade.getEntity().setDireccion1(null);
        }
        if (garanteDireccion2 != null && !garanteDireccion2.trim().equals("")) {
            personaFacade.getEntity().setDireccion2((garanteDireccion2.trim()));
        } else {
            personaFacade.getEntity().setDireccion2(null);
        }
        if (garanteTelefonoFijo != null && !garanteTelefonoFijo.trim().equals("")) {
            personaFacade.getEntity().setTelefonoFijo((garanteTelefonoFijo.trim()));
        } else {
            personaFacade.getEntity().setTelefonoFijo(null);
        }
        if (garanteTelefonoMovil != null && !garanteTelefonoMovil.trim().equals("")) {
            personaFacade.getEntity().setTelefonoMovil((garanteTelefonoMovil.trim()));
        } else {
            personaFacade.getEntity().setTelefonoMovil(null);
        }
        if (garanteEmail != null && !garanteEmail.trim().equals("")) {
            personaFacade.getEntity().setEmail((garanteEmail.trim().toLowerCase()));
        } else {
            personaFacade.getEntity().setEmail(null);
        }
//        if (garanteFechaIngreso != null && !garanteFechaIngreso.trim().equals("")) {
//            personaFacade.getEntity().setFechaIngresoString(garanteFechaIngreso);
//        } else {
//            personaFacade.getEntity().setFechaIngreso(null);
//        }
        if (garanteRuc != null && !garanteRuc.trim().equals("")) {
            personaFacade.getEntity().setRuc(garanteRuc);
        } else {
            personaFacade.getEntity().setRuc(null);
        }
        if (garanteContacto != null && !garanteContacto.trim().equals("")) {
            personaFacade.getEntity().setContacto(garanteContacto.trim());
        } else {
            personaFacade.getEntity().setContacto(null);
        }
        if (garanteProfesion != null && !garanteProfesion.trim().equals("")) {
            personaFacade.getEntity().setProfesion(garanteProfesion.trim());
        } else {
            personaFacade.getEntity().setProfesion(null);
        }
        if (garanteHijos != null && garanteHijos >= 0) {
            personaFacade.getEntity().setHijos(garanteHijos);
        } else {
            personaFacade.getEntity().setHijos(null);
        }
        if (garanteEstadoCivil != null) {
            personaFacade.getEntity().setEstadoCivil(garanteEstadoCivil);
        } else {
            personaFacade.getEntity().setEstadoCivil(null);
        }
//        if (garanteFechaNacimiento != null && !garanteFechaNacimiento.trim().equals("")) {
//            personaFacade.getEntity().setFechaNacimientoString(garanteFechaNacimiento);
//        } else {
//            personaFacade.getEntity().setFechaNacimiento(null);
//        }
        if (garanteTratamiento != null && !garanteTratamiento.trim().equals("")) {
            personaFacade.getEntity().setTratamiento(garanteTratamiento);
        } else {
            personaFacade.getEntity().setTratamiento(null);
        }
        if (garanteSexo != null) {
            personaFacade.getEntity().setSexo(garanteSexo);
        } else {
            personaFacade.getEntity().setSexo(null);
        }
        if (garanteHabilitado != null && (garanteHabilitado.equals('S') || garanteHabilitado.equals('N'))) {
            personaFacade.getEntity().setHabilitado(garanteHabilitado);
        } else {
            personaFacade.getEntity().setHabilitado(null);
        }
        if (garanteCategoria != null && !garanteCategoria.equals(-1)) {
            personaFacade.getEntity().setCategoria(new Categoria(garanteCategoria));
        } else {
            personaFacade.getEntity().setCategoria(null);
        }
    }

    void personaLimpiarCampos() {
        personaFacade.setEntity(new Persona());
        //Cliente
        clienteId = null;
        clienteFisica = null;
        clienteDocumento = null;
        clienteRuc = null;
        clienteNombre = null;
        clienteDireccion1 = null;
        clienteDireccion2 = null;
        clienteTelefonoFijo = null;
        clienteTelefonoMovil = null;
        clienteEmail = null;
        clienteFechaIngreso = null;
        clienteContacto = null;
        clienteProfesion = null;
        clienteEstadoCivil = null;
        clienteFechaNacimiento = null;
        clienteTratamiento = null;
        clienteSexo = null;
        clienteHijos = null;
        clienteHabilitado = null;
        clienteCategoria = -1;
        //Garante
        garanteId = null;
        garanteDocumento = null;
        garanteRuc = null;
        garanteNombre = null;
        garanteDireccion1 = null;
        garanteDireccion2 = null;
        garanteTelefonoFijo = null;
        garanteTelefonoMovil = null;
        garanteEmail = null;
        garanteFechaIngreso = null;
        garanteContacto = null;
        garanteProfesion = null;
        garanteEstadoCivil = null;
        garanteFechaNacimiento = null;
        garanteTratamiento = null;
        garanteSexo = null;
        garanteHijos = null;
        garanteHabilitado = null;
        garanteCategoria = -1;

        setNav("listapersonas");
    }

    @Override
    protected void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    @Override
    protected void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
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
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the fechaOperacion
     */
    public String getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion the fechaOperacion to set
     */
    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @return the fechaEntrega
     */
    public String getFechaEntrega() {
        if (usarFechaEntregaAhora) {
            fechaEntrega = formatFechaHora.format(new Date());
            usarFechaEntregaAhora = false;
        }
        return fechaEntrega;
    }

    /**
     * @param fechaEntrega the fechaEntrega to set
     */
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * @return the vendedor
     */
    public Integer getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(Integer vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @return the comprador
     */
    public Integer getComprador() {
        return comprador;
    }

    /**
     * @param comprador the comprador to set
     */
    public void setComprador(Integer comprador) {
        this.comprador = comprador;
    }

    /**
     * @return the anulado
     */
    public Character getAnulado() {
        return anulado;
    }

    /**
     * @param anulado the anulado to set
     */
    public void setAnulado(Character anulado) {
        this.anulado = anulado;
    }

    /**
     * @return the subTotalExentas
     */
    public String getSubTotalExentas() {
        Number n = null;
        try {
            n = new BigDecimal(subTotalExentas.trim());
            subTotalExentas = formatNumero.format(n.doubleValue());
        } finally {
            return subTotalExentas;
        }
    }

    /**
     * @param subTotalExentas the subTotalExentas to set
     */
    public void setSubTotalExentas(String subTotalExentas) {
        this.subTotalExentas = subTotalExentas;
    }

    /**
     * @return the subTotalGravadas10
     */
    public String getSubTotalGravadas10() {
        Number n = null;
        try {
            n = new BigDecimal(subTotalGravadas10.trim());
            subTotalGravadas10 = formatNumero.format(n.doubleValue());
        } finally {
            return subTotalGravadas10;
        }
    }

    /**
     * @param subTotalGravadas10 the subTotalGravadas10 to set
     */
    public void setSubTotalGravadas10(String subTotalGravadas10) {
        this.subTotalGravadas10 = subTotalGravadas10;
    }

    /**
     * @return the subTotalGravadas5
     */
    public String getSubTotalGravadas5() {
        Number n = null;
        try {
            n = new BigDecimal(subTotalGravadas5.trim());
            subTotalGravadas5 = formatNumero.format(n.doubleValue());
        } finally {
            return subTotalGravadas5;
        }
    }

    /**
     * @param subTotalGravadas5 the subTotalGravadas5 to set
     */
    public void setSubTotalGravadas5(String subTotalGravadas5) {
        this.subTotalGravadas5 = subTotalGravadas5;
    }

    /**
     * @return the subtotal
     */
    public String getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the totalIva
     */
    public String getTotalIva() {
        return totalIva;
    }

    /**
     * @param totalIva the totalIva to set
     */
    public void setTotalIva(String totalIva) {
        this.totalIva = totalIva;
    }

    /**
     * @return the totalPagado
     */
    public String getTotalPagado() {
        Number n = null;
        try {
            n = new BigDecimal(totalPagado.trim());
            totalPagado = formatNumero.format(n.doubleValue());
        } finally {
            return totalPagado;
        }
    }

    /**
     * @param totalPagado the totalPagado to set
     */
    public void setTotalPagado(String totalPagado) {
        this.totalPagado = totalPagado;
    }

    /**
     * @return the descuento
     */
    public String getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the saldado
     */
    public Character getSaldado() {
        return saldado;
    }

    /**
     * @param saldado the saldado to set
     */
    public void setSaldado(Character saldado) {
        this.saldado = saldado;
    }

    /**
     * @return the comprobante
     */
    public String getComprobante() {
        return comprobante;
    }

    /**
     * @param comprobante the comprobante to set
     */
    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    /**
     * @return the cuotas
     */
    public String getCuotas() {
        return cuotas;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(String cuotas) {
        this.cuotas = cuotas;
    }

    /**
     * @return the montoCuotaIgual
     */
    public String getMontoCuotaIgual() {
        Number n = null;
        try {
            n = new BigDecimal(montoCuotaIgual.trim());
            montoCuotaIgual = formatNumero.format(n.doubleValue());
        } finally {
            return montoCuotaIgual;
        }
    }

    /**
     * @param montoCuotaIgual the montoCuotaIgual to set
     */
    public void setMontoCuotaIgual(String montoCuotaIgual) {
        this.montoCuotaIgual = montoCuotaIgual;
    }

    /**
     * @return the toTal
     */
    public String getToTal() {
        Number n = null;
        try {
            n = new BigDecimal(toTal.trim());
            toTal = formatNumero.format(n.doubleValue());
        } finally {
            return toTal;
        }
    }

    /**
     * @param toTal the toTal to set
     */
    public void setToTal(String toTal) {
        this.toTal = toTal;
    }

    /**
     * @return the totalDescuento
     */
    public String getTotalDescuento() {
        return totalDescuento;
    }

    /**
     * @param totalDescuento the totalDescuento to set
     */
    public void setTotalDescuento(String totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    /**
     * @return the entregaInicial
     */
    public String getEntregaInicial() {
        Number n = null;
        try {
            n = new BigDecimal(entregaInicial.trim());
            entregaInicial = formatNumero.format(n.doubleValue());
        } finally {
            return entregaInicial;
        }
    }

    /**
     * @param entregaInicial the entregaInicial to set
     */
    public void setEntregaInicial(String entregaInicial) {
        this.entregaInicial = entregaInicial;
    }

    /**
     * @return the totalIva5
     */
    public String getTotalIva5() {
        return totalIva5;
    }

    /**
     * @param totalIva5 the totalIva5 to set
     */
    public void setTotalIva5(String totalIva5) {
        this.totalIva5 = totalIva5;
    }

    /**
     * @return the totalIva10
     */
    public String getTotalIva10() {
        return totalIva10;
    }

    /**
     * @param totalIva10 the totalIva10 to set
     */
    public void setTotalIva10(String totalIva10) {
        this.totalIva10 = totalIva10;
    }

    /**
     * @return the listaClientees
     */
    public List<Persona> getlistaClientes() {
        return listaClientes;
    }

    /**
     * @param listaClientees the listaClientees to set
     */
    public void setlistaClientes(List<Persona> listaClientes) {
        this.listaClientes = listaClientes;
    }

    /**
     * @return the listaCliente
     */
    public List<SelectItem> getlistaCliente() {
        return getListaCliente();
    }

    /**
     * @param listaCliente the listaCliente to set
     */
    public void setlistaCliente(List<SelectItem> listaCliente) {
        this.listaCliente = listaCliente;
    }

    /**
     * @return the cantidadItems
     */
    public String getCantidadItems() {
        return cantidadItems;
    }

    /**
     * @param cantidadItems the cantidadItems to set
     */
    public void setCantidadItems(String cantidadItems) {
        this.cantidadItems = cantidadItems;
    }

    /**
     * @return the usarFechaAhora
     */
    public Boolean getUsarFechaAhora() {
        return usarFechaAhora;
    }

    /**
     * @param usarFechaAhora the usarFechaAhora to set
     */
    public void setUsarFechaAhora(Boolean usarFechaAhora) {
        this.usarFechaAhora = usarFechaAhora;
        setFechaOperacion(formatFechaHora.format(new Date()));

    }

    /**
     * @return the usarFechaEntregaAhora
     */
    public Boolean getUsarFechaEntregaAhora() {
        return usarFechaEntregaAhora;
    }

    /**
     * @param usarFechaEntregaAhora the usarFechaEntregaAhora to set
     */
    public void setUsarFechaEntregaAhora(Boolean usarFechaEntregaAhora) {
        this.usarFechaEntregaAhora = usarFechaEntregaAhora;
    }

    /**
     * @return the cantidad
     */
    public Short getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the listaUbicacion
     */
    public List<SelectItem> getListaUbicacion() {
        return listaUbicacion;
    }

    /**
     * @param listaUbicacion the listaUbicacion to set
     */
    public void setListaUbicacion(List<SelectItem> listaUbicacion) {
        this.listaUbicacion = listaUbicacion;
    }

    /**
     * @return the listaMoto
     */
    public List<SelectItem> getListaMoto() {
        return listaMoto;
    }

    /**
     * @return the nMoto
     */
    public String getnMoto() {
        return nMoto;
    }

    /**
     * @param nMoto the nMoto to set
     */
    public void setnMoto(String nMoto) {
        this.nMoto = nMoto;
    }

    /**
     * @return the listaCliente
     */
    public List<SelectItem> getListaCliente() {
        return listaCliente;
    }

    /**
     * @return the listaGarante
     */
    public List<SelectItem> getListaGarante() {
        return listaGarante;
    }

    /**
     * @return the clienteDocumento
     */
    public String getClienteDocumento() {
        return clienteDocumento;
    }

    /**
     * @param clienteDocumento the clienteDocumento to set
     */
    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    /**
     * @return the clienteRuc
     */
    public String getClienteRuc() {
        return clienteRuc;
    }

    /**
     * @param clienteRuc the clienteRuc to set
     */
    public void setClienteRuc(String clienteRuc) {
        this.clienteRuc = clienteRuc;
    }

    /**
     * @return the clienteNombre
     */
    public String getClienteNombre() {
        return clienteNombre;
    }

    /**
     * @param clienteNombre the clienteNombre to set
     */
    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    /**
     * @return the clienteDireccion1
     */
    public String getClienteDireccion1() {
        return clienteDireccion1;
    }

    /**
     * @param clienteDireccion1 the clienteDireccion1 to set
     */
    public void setClienteDireccion1(String clienteDireccion1) {
        this.clienteDireccion1 = clienteDireccion1;
    }

    /**
     * @return the clienteDireccion2
     */
    public String getClienteDireccion2() {
        return clienteDireccion2;
    }

    /**
     * @param clienteDireccion2 the clienteDireccion2 to set
     */
    public void setClienteDireccion2(String clienteDireccion2) {
        this.clienteDireccion2 = clienteDireccion2;
    }

    /**
     * @return the clienteTelefonoFijo
     */
    public String getClienteTelefonoFijo() {
        return clienteTelefonoFijo;
    }

    /**
     * @param clienteTelefonoFijo the clienteTelefonoFijo to set
     */
    public void setClienteTelefonoFijo(String clienteTelefonoFijo) {
        this.clienteTelefonoFijo = clienteTelefonoFijo;
    }

    /**
     * @return the clienteTelefonoMovil
     */
    public String getClienteTelefonoMovil() {
        return clienteTelefonoMovil;
    }

    /**
     * @param clienteTelefonoMovil the clienteTelefonoMovil to set
     */
    public void setClienteTelefonoMovil(String clienteTelefonoMovil) {
        this.clienteTelefonoMovil = clienteTelefonoMovil;
    }

    /**
     * @return the ClienteEmail
     */
    public String getClienteEmail() {
        return clienteEmail;
    }

    /**
     * @param ClienteEmail the ClienteEmail to set
     */
    public void setClienteEmail(String ClienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    /**
     * @return the clienteContacto
     */
    public String getClienteContacto() {
        return clienteContacto;
    }

    /**
     * @param clienteContacto the clienteContacto to set
     */
    public void setClienteContacto(String clienteContacto) {
        this.clienteContacto = clienteContacto;
    }

    /**
     * @return the clienteProfesion
     */
    public String getClienteProfesion() {
        return clienteProfesion;
    }

    /**
     * @param clienteProfesion the clienteProfesion to set
     */
    public void setClienteProfesion(String clienteProfesion) {
        this.clienteProfesion = clienteProfesion;
    }

    /**
     * @return the clienteEstadoCivil
     */
    public Character getClienteEstadoCivil() {
        return clienteEstadoCivil;
    }

    /**
     * @param clienteEstadoCivil the clienteEstadoCivil to set
     */
    public void setClienteEstadoCivil(Character clienteEstadoCivil) {
        this.clienteEstadoCivil = clienteEstadoCivil;
    }

    /**
     * @return the clienteFechaNacimiento
     */
    public String getClienteFechaNacimiento() {
        return clienteFechaNacimiento;
    }

    /**
     * @param clienteFechaNacimiento the clienteFechaNacimiento to set
     */
    public void setClienteFechaNacimiento(String clienteFechaNacimiento) {
        this.clienteFechaNacimiento = clienteFechaNacimiento;
    }

    /**
     * @return the clienteTratamiento
     */
    public String getClienteTratamiento() {
        return clienteTratamiento;
    }

    /**
     * @param clienteTratamiento the clienteTratamiento to set
     */
    public void setClienteTratamiento(String clienteTratamiento) {
        this.clienteTratamiento = clienteTratamiento;
    }

    /**
     * @return the clienteSexo
     */
    public Character getClienteSexo() {
        return clienteSexo;
    }

    /**
     * @param clienteSexo the clienteSexo to set
     */
    public void setClienteSexo(Character clienteSexo) {
        this.clienteSexo = clienteSexo;
    }

    /**
     * @return the clienteHijos
     */
    public Short getClienteHijos() {
        return clienteHijos;
    }

    /**
     * @param clienteHijos the clienteHijos to set
     */
    public void setClienteHijos(Short clienteHijos) {
        this.clienteHijos = clienteHijos;
    }

    /**
     * @return the clienteCategoria
     */
    public Integer getClienteCategoria() {
        return clienteCategoria;
    }

    /**
     * @param clienteCategoria the clienteCategoria to set
     */
    public void setClienteCategoria(Integer clienteCategoria) {
        this.clienteCategoria = clienteCategoria;
    }

    /**
     * @return the garanteDocumento
     */
    public String getGaranteDocumento() {
        return garanteDocumento;
    }

    /**
     * @param garanteDocumento the garanteDocumento to set
     */
    public void setGaranteDocumento(String garanteDocumento) {
        this.garanteDocumento = garanteDocumento;
    }

    /**
     * @return the garanteRuc
     */
    public String getGaranteRuc() {
        return garanteRuc;
    }

    /**
     * @param garanteRuc the garanteRuc to set
     */
    public void setGaranteRuc(String garanteRuc) {
        this.garanteRuc = garanteRuc;
    }

    /**
     * @return the garanteNombre
     */
    public String getGaranteNombre() {
        return garanteNombre;
    }

    /**
     * @param garanteNombre the garanteNombre to set
     */
    public void setGaranteNombre(String garanteNombre) {
        this.garanteNombre = garanteNombre;
    }

    /**
     * @return the garanteDireccion1
     */
    public String getGaranteDireccion1() {
        return garanteDireccion1;
    }

    /**
     * @param garanteDireccion1 the garanteDireccion1 to set
     */
    public void setGaranteDireccion1(String garanteDireccion1) {
        this.garanteDireccion1 = garanteDireccion1;
    }

    /**
     * @return the garanteDireccion2
     */
    public String getGaranteDireccion2() {
        return garanteDireccion2;
    }

    /**
     * @param garanteDireccion2 the garanteDireccion2 to set
     */
    public void setGaranteDireccion2(String garanteDireccion2) {
        this.garanteDireccion2 = garanteDireccion2;
    }

    /**
     * @return the garanteTelefonoFijo
     */
    public String getGaranteTelefonoFijo() {
        return garanteTelefonoFijo;
    }

    /**
     * @param garanteTelefonoFijo the garanteTelefonoFijo to set
     */
    public void setGaranteTelefonoFijo(String garanteTelefonoFijo) {
        this.garanteTelefonoFijo = garanteTelefonoFijo;
    }

    /**
     * @return the garanteTelefonoMovil
     */
    public String getGaranteTelefonoMovil() {
        return garanteTelefonoMovil;
    }

    /**
     * @param garanteTelefonoMovil the garanteTelefonoMovil to set
     */
    public void setGaranteTelefonoMovil(String garanteTelefonoMovil) {
        this.garanteTelefonoMovil = garanteTelefonoMovil;
    }

    /**
     * @return the garanteEmail
     */
    public String getGaranteEmail() {
        return garanteEmail;
    }

    /**
     * @param garanteEmail the garanteEmail to set
     */
    public void setGaranteEmail(String garanteEmail) {
        this.garanteEmail = garanteEmail;
    }

    /**
     * @return the garanteContacto
     */
    public String getGaranteContacto() {
        return garanteContacto;
    }

    /**
     * @param garanteContacto the garanteContacto to set
     */
    public void setGaranteContacto(String garanteContacto) {
        this.garanteContacto = garanteContacto;
    }

    /**
     * @return the garanteProfesion
     */
    public String getGaranteProfesion() {
        return garanteProfesion;
    }

    /**
     * @param garanteProfesion the garanteProfesion to set
     */
    public void setGaranteProfesion(String garanteProfesion) {
        this.garanteProfesion = garanteProfesion;
    }

    /**
     * @return the garanteEstadoCivil
     */
    public Character getGaranteEstadoCivil() {
        return garanteEstadoCivil;
    }

    /**
     * @param garanteEstadoCivil the garanteEstadoCivil to set
     */
    public void setGaranteEstadoCivil(Character garanteEstadoCivil) {
        this.garanteEstadoCivil = garanteEstadoCivil;
    }

    /**
     * @return the garanteFechaNacimiento
     */
    public String getGaranteFechaNacimiento() {
        return garanteFechaNacimiento;
    }

    /**
     * @param garanteFechaNacimiento the garanteFechaNacimiento to set
     */
    public void setGaranteFechaNacimiento(String garanteFechaNacimiento) {
        this.garanteFechaNacimiento = garanteFechaNacimiento;
    }

    /**
     * @return the garanteTratamiento
     */
    public String getGaranteTratamiento() {
        return garanteTratamiento;
    }

    /**
     * @param garanteTratamiento the garanteTratamiento to set
     */
    public void setGaranteTratamiento(String garanteTratamiento) {
        this.garanteTratamiento = garanteTratamiento;
    }

    /**
     * @return the garanteSexo
     */
    public Character getGaranteSexo() {
        return garanteSexo;
    }

    /**
     * @param garanteSexo the garanteSexo to set
     */
    public void setGaranteSexo(Character garanteSexo) {
        this.garanteSexo = garanteSexo;
    }

    /**
     * @return the garanteHijos
     */
    public Short getGaranteHijos() {
        return garanteHijos;
    }

    /**
     * @param garanteHijos the garanteHijos to set
     */
    public void setGaranteHijos(Short garanteHijos) {
        this.garanteHijos = garanteHijos;
    }

    /**
     * @return the garante
     */
    public Integer getGarante() {
        return garante;
    }

    /**
     * @param garante the garante to set
     */
    public void setGarante(Integer garante) {
        this.garante = garante;
    }

    /**
     * @return the clienteFisica
     */
    public Character getClienteFisica() {
        return clienteFisica;
    }

    /**
     * @param clienteFisica the clienteFisica to set
     */
    public void setClienteFisica(Character clienteFisica) {
        this.clienteFisica = clienteFisica;
    }

    /**
     * @return the clienteId
     */
    public Integer getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the garanteId
     */
    public Integer getGaranteId() {
        return garanteId;
    }

    /**
     * @param garanteId the garanteId to set
     */
    public void setGaranteId(Integer garanteId) {
        this.garanteId = garanteId;
    }

    /**
     * @return the venta
     */
    public Transaccion getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Transaccion venta) {
        this.venta = venta;
    }

    @Override
    void limpiarCampos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

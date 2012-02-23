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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
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
import py.com.bej.base.prod.entity.Vmpagomotos;
import py.com.bej.base.prod.entity.Vmplanmoto;
import py.com.bej.base.prod.entity.Vmventamotos;
import py.com.bej.base.prod.session.PlanMotosProduccionFacade;
import py.com.bej.base.prod.session.VentaMotosProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Factura;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.session.UbicacionFacade;
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
public class VentaMotosBean extends AbstractPageBean<Transaccion> {

    @EJB
    private PlanMotosProduccionFacade planMotosProduccionFacade;
    @EJB
    private VentaMotosProduccionFacade ventaMotosProduccionFacade;
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
    private List<SelectItem> listaCategoria;
    private List<SelectItem> listaCliente;
    private List<SelectItem> listaGarante;
    private List<SelectItem> listaMoto;
    private List<SelectItem> listaUbicacion;
    private Motostock motoVendida;
    //Campos de busqueda
    private String idFiltro;
    private String comprobanteFiltro;
    private Integer categoriaFiltro;
    private Integer compradorFiltro;
    private Character saldadoFiltro;
    private Character anuladoFiltro;
    private Character activoFiltro;
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
    private String total;
    private String totalDescuento;
    private String totalPagado;
    private String entregaInicial;
    private String cuotas;
    private String cantidadItems;
    private String montoCuotaIgual;
    private String descuento;
    private Boolean saldado;
    private Boolean anulado;
    private Boolean activo;
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
    private BigDecimal totalX;
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

    /** Creates a new instance of VentaMotosBean */
    public VentaMotosBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        //Campos de busqueda
        this.idFiltro = null;
        this.comprobanteFiltro = null;
        this.categoriaFiltro = null;
        this.compradorFiltro = null;
        this.saldadoFiltro = null;
        this.setAnuladoFiltro(null);
        //Transaccion de Compra
        this.cantidad = null;
        this.fechaOperacion = null;
        this.fechaEntrega = null;
        this.comprador = null;
        this.subTotalExentas = null;
        this.subTotalGravadas10 = null;
        this.subTotalGravadas5 = null;
        this.subtotal = null;
        this.totalIva5 = null;
        this.totalIva10 = null;
        this.totalIva = null;
        this.total = null;
        this.totalDescuento = null;
        this.totalPagado = null;
        this.entregaInicial = null;
        this.cuotas = null;
        this.cantidadItems = null;
        this.montoCuotaIgual = null;
        this.usarFechaAhora = false;
        this.usarFechaEntregaAhora = false;
        //Calculos Ajax
        this.subTotalGravadas10X = null;
        this.subTotalGravadas5X = null;
        this.subtotalX = null;
        this.totalX = null;
        this.descuentoX = null;
        this.totalDescuentoX = null;
    }

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
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        LoginBean.getInstance().setUbicacion("Venta de Motos");
        setDesde(0);
        setMax(10);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        obtenerListas();
        return getNav();
    }

    @Override
    List filtrar() {
        Transaccion t = new Transaccion();
        if (idFiltro != null && !idFiltro.trim().equals("")) {
            t.setId(Integer.valueOf(idFiltro));
        }
        if (comprobanteFiltro != null && !comprobanteFiltro.trim().equals("")) {
            t.setFactura(new Factura(comprobanteFiltro));
        }
        if (categoriaFiltro != null && !categoriaFiltro.equals(-1)) {
            t.setCodigo(new Categoria(categoriaFiltro));
        }
        if (compradorFiltro != null && !compradorFiltro.equals(-1)) {
            t.setComprador(new Persona(compradorFiltro));
        }
        if (saldadoFiltro != null && !saldadoFiltro.equals('X')) {
            t.setSaldado(saldadoFiltro);
        }
        if (anuladoFiltro != null && !anuladoFiltro.equals('X')) {
            t.setAnulado(anuladoFiltro);
        }
        if (activoFiltro != null && !activoFiltro.equals('X')) {
            t.setActivo(activoFiltro);
        }
        facade.setEntity(t);
        getFacade().getEntity().setCodigo(new Categoria(CategoriaEnum.VENTA_DESDE.getSymbol()));
        getFacade().getEntity().setCodigoMax(new Categoria(CategoriaEnum.VENTA_HASTA.getSymbol()));
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategoria = JsfUtils.getSelectItems(getCategoriaFacade().findBetween(CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol()), !getModificar());
        listaCliente = JsfUtils.getSelectItems(getPersonaFacade().findBetween(CategoriaEnum.CLIENTE_PF.getSymbol(), CategoriaEnum.CLIENTE_PJ.getSymbol()), !getModificar());
        listaUbicacion = JsfUtils.getSelectItems(getUbicacionFacade().findAll(), !getModificar());
    }

    @Override
    public String buscar() {
        getFacade().setEntity(venta);
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        LoginBean.getInstance().setUbicacion("Venta Nueva");
        limpiarCampos();
        venta = new Transaccion();
        obtenerListas();
        listaMoto = JsfUtils.getSelectItems(getMotoFacade().findAll(), !getModificar());
        vendedor = 1;
        return "comprarmotos";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        idFiltro = (String) request.getParameter("radio");
        if (idFiltro != null) {
            try {
                venta = facade.find(new Integer(idFiltro));
                anulado = venta.getAnulado().equals('S') ? Boolean.TRUE : Boolean.FALSE;
                saldado = venta.getSaldado().equals('S') ? Boolean.TRUE : Boolean.FALSE;
                activo = venta.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE;
            } catch (Exception e) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setModificar(Boolean.TRUE);
            setAgregar(Boolean.FALSE);
            obtenerListas();
            listaMoto = JsfUtils.getSelectItems(getMotoFacade().findAll(), getModificar());
            LoginBean.getInstance().setUbicacion("Modificar Venta");
            return "vendermotos";
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
            venta = new Transaccion();
            getFacade().setEntity(venta);
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

    public String importar() {

        return "importarVenta";
    }

    public String migrarVentas() {
        int totalMigrado = 0;
        //Venta
        Transaccion t;
        Integer idProduccion;
        Categoria codigoProduccion = null;
        Factura facturaProduccion = null;
        String numero;
        Categoria categoriaFactura;
        Date fechaOperacionProduccion;
        Date fechaEntregaProduccion;
        Persona compradorProduccion;
        Persona vendedorProduccion = getPersonaFacade().findByDocumento(ConfiguracionEnum.PROPIETARIO.getSymbol());
        Character anuladoProduccion;
        BigDecimal subTotalExentasProduccion = BigDecimal.ZERO;
        BigDecimal subTotalGravadas10Produccion = BigDecimal.ZERO;
        BigDecimal subTotalGravadas5Produccion = BigDecimal.ZERO;
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal totalIva5Produccion = BigDecimal.ZERO;
        BigDecimal totalIva10Produccion = BigDecimal.ZERO;
        BigDecimal totalIvaProduccion = BigDecimal.ZERO;
        Float descuentoProduccion = Float.valueOf("0.0");
        BigDecimal totalProduccion;
        BigDecimal totalDescuentoProduccion = BigDecimal.ZERO;
        BigDecimal totalPagadoProduccion;
        Short cuotasProduccion;
        Short cantidadItemsProduccion = 1;
        Character activo = 'S';
        Character saldadoProduccion;
        Date fechaUltimoPagoProduccion = new Date();
        Date ultimaModificacion = new Date();
        //Desarrollo
        List<Transaccion> lista = new ArrayList<Transaccion>();
        Credito credito = null;
        //Produccion
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        ventaMotosProduccionFacade = new VentaMotosProduccionFacade();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdCompraOrdenado();
        Financiacion financiacionProduccion;
        List<Vmpagomotos> pagosProduccion;
        if (!listaVentasProduccion.isEmpty()) {
            for (Vmventamotos c : listaVentasProduccion) {
                idProduccion = c.getIdVenta();
                LOGGER.log(Level.INFO, "==============================================================");
                LOGGER.log(Level.INFO, "La venta Nro. {0}", id);
                fechaOperacionProduccion = c.getFechaVenta() == null ? new Date() : c.getFechaVenta();
                fechaEntregaProduccion = c.getFechaEntrega() == null ? new Date() : c.getFechaEntrega();
                //Buscar Cliente
                String codCliente = null;
                if (c.getCedulaRuc() == null) {
                    codCliente = "1275758-6";
                } else {
                    codCliente = c.getCedulaRuc().trim();
                }
                //Montos
                if (c.getPrecioMoto() != null && c.getPrecioMoto() > 0) {
                    totalPagadoProduccion = BigDecimal.valueOf(c.getPrecioMoto());
                } else {
                    totalPagadoProduccion = BigDecimal.TEN;
                }
                BigDecimal entregaInicialProduccion = BigDecimal.valueOf(c.getEntregaMoto());
                totalProduccion = totalPagadoProduccion;
                subTotalGravadas10Produccion = totalPagadoProduccion.multiply(BigDecimal.valueOf(0.02)).setScale(Integer.valueOf(ConfiguracionEnum.MONEDA_DECIMALES.getSymbol()));
                subTotalExentasProduccion = totalPagadoProduccion.subtract(subTotalGravadas10Produccion);
                totalIva10Produccion = subTotalGravadas10Produccion.multiply(BigDecimal.valueOf(0.1));
                BigDecimal montoCuotaIgualProduccion = BigDecimal.ZERO;
                //Saldado
                if (c.getSalAcMoto() > 0) {
                    saldadoProduccion = 'N';
                } else {
                    saldadoProduccion = 'S';
                }
                //Factura
                facturaProduccion = new Factura();
                facturaProduccion.setSaldado(saldadoProduccion);
                facturaProduccion.setSubTotalExentas(subTotalExentasProduccion);
                facturaProduccion.setSubTotalGravadas10(subTotalGravadas10Produccion);
                facturaProduccion.setSubTotalGravadas5(subTotalGravadas5Produccion);
                facturaProduccion.setNetoSinIva5(totalIva5Produccion);
                facturaProduccion.setNetoSinIva10(subTotalGravadas10Produccion.subtract(totalIva10Produccion));
                facturaProduccion.setTotalIva5(totalIva5Produccion);
                facturaProduccion.setTotalIva10(totalIva10Produccion);
                facturaProduccion.setTotalIva(totalIva10Produccion);
                facturaProduccion.setTotalPagado(totalPagadoProduccion);
                facturaProduccion.setDescuento(descuentoProduccion);
                facturaProduccion.setValidoHasta(ultimaModificacion);
                facturaProduccion.setActivo('S');
                facturaProduccion.setUltimaModificacion(ultimaModificacion);
                //Contado o Credito
                if (c.getIdTransaccion() == 1) {
                    //Venta a Contado
                    categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCO.getSymbol());
                    facturaProduccion.setCategoria(categoriaFactura);
                    cuotasProduccion = 0;
                } else {
                    //Venta Credito
                    planMotosProduccionFacade = new PlanMotosProduccionFacade();
                    List<Vmplanmoto> listaCuotasProduccion = new ArrayList<Vmplanmoto>();
                    List<Pago> listaPagosProduccion = new ArrayList<Pago>();
                    montoCuotaIgualProduccion = BigDecimal.valueOf(c.getMontoCuotas());
                    BigDecimal capitalProduccion = BigDecimal.valueOf(c.getSaldoMoto());
                    cuotasProduccion = Short.valueOf("" + c.getNumeroCuotas());
                    BigDecimal creditoTotalProduccion = BigDecimal.valueOf(montoCuotaIgualProduccion.intValue() * cuotasProduccion);
                    List<Financiacion> listaCuotasDesarrollo = new ArrayList<Financiacion>();
                    categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCR.getSymbol());
                    facturaProduccion.setCategoria(categoriaFactura);
                    BigDecimal interes = BigDecimal.valueOf(0.26F);


                    credito = new Credito(null, new Categoria(CategoriaEnum.S_PER.getSymbol()), null,
                            c.getFechaVenta(), null, new Categoria(CategoriaEnum.S_ESP.getSymbol()), 0.0F, 0.0F,
                            capitalProduccion, cuotasProduccion, creditoTotalProduccion, BigDecimal.ZERO,
                            BigDecimal.ZERO, BigDecimal.ZERO, fechaUltimoPagoProduccion, new Short("0"),
                            new Categoria(CategoriaEnum.ABIERTO.getSymbol()), 'S', new Date());

                    //FINANCIACION
                    //Buscar Cuotas
                    listaCuotasProduccion = (List<Vmplanmoto>) c.getVmplanmotoCollection();
                    if (!listaCuotasProduccion.isEmpty()) {
                        BigDecimal totalPagadoHastaAhora = BigDecimal.ZERO;
                        pagosProduccion = new ArrayList<Vmpagomotos>();
                        for (Vmplanmoto p : listaCuotasProduccion) {
                            financiacionProduccion = new Financiacion(null, credito, Short.valueOf("" + p.getCuotaNumero()),
                                    credito.getCapital(), credito.getCapital().multiply(interes),
                                    BigDecimal.valueOf(c.getMontoCuotas()), credito.getCreditoTotal(),
                                    null, p.getFechaPago(), BigDecimal.valueOf(p.getMontoInteresMensual()),
                                    montoCuotaIgualProduccion, 'S', new Date());
                            totalPagadoHastaAhora = totalPagadoHastaAhora.add(montoCuotaIgualProduccion);
                            if (p.getCuotaNumero() == listaCuotasProduccion.size()) {
                                credito.setFechaFin(p.getFechaVencimiento());
                            }
                            //Buscar Pagos
                            pagosProduccion = (List<Vmpagomotos>) c.getVmpagomotosCollection();
                            if (!pagosProduccion.isEmpty()) {
                                Pago pg;
                                boolean esPagoParcial = false;
                                String cadenaParcial = "parcial";
                                for (Vmpagomotos pago : pagosProduccion) {
                                    if (pago.getConcepto().contains(cadenaParcial)) {
                                        esPagoParcial = true;
                                    }
                                    pg = new Pago(null, pago.getFechaPago(),
                                            financiacionProduccion, BigDecimal.valueOf(pago.getMontoEntrega()),
                                            esPagoParcial, pago.getAnulado() ? 'N' : 'S', new Date());
                                    listaPagosProduccion.add(pg);
                                }
                            }
                            financiacionProduccion.setPagos(listaPagosProduccion);
                            listaCuotasDesarrollo.add(financiacionProduccion);
                        }
                    }
                }

                compradorProduccion = getPersonaFacade().findByDocumento(codCliente);
                anuladoFiltro = c.getAnulado() ? 'S' : 'N';

                //Factura
                numero = "" + c.getIdVenta();
                if (numero != null && !numero.equals("null") && numero.length() < 7) {
                    switch (numero.length()) {
                        case 1:
                            numero = "000000" + numero;
                            break;
                        case 2:
                            numero = "00000" + numero;
                            break;
                        case 3:
                            numero = "0000" + numero;
                            break;
                        case 4:
                            numero = "000" + numero;
                            break;
                        case 5:
                            numero = "00" + numero;
                            break;
                        case 6:
                            numero = "0" + numero;
                            break;
                    }
                    numero = "001-001-" + numero;
                } else if (numero == null || numero.equals("null")) {
                    Random r = new Random(1L);
                    int randomNumber = r.nextInt();
                    String nro = "" + (1000000 + (randomNumber < 0 ? (randomNumber * -1) : randomNumber));
                    numero = "001-001-" + nro.substring(0, 7);
                } else if (numero.length() == 7) {
                    numero = "001-001-" + numero;
                } else {
                    numero = "001-001-" + numero.substring(0, 7);
                }
                //Numero de Factura
                facturaProduccion.setNumero(numero);
                //Fecha de Vencimiento
                Calendar ahora = GregorianCalendar.getInstance();
                ahora.add(Calendar.YEAR, 1);
                //Juntar todo
                t = new Transaccion(c.getIdVenta(), categoriaFactura, facturaProduccion, fechaOperacionProduccion, fechaEntregaProduccion, vendedorProduccion,
                        compradorProduccion, anuladoFiltro, subTotalExentasProduccion, subTotalGravadas10Produccion, subTotalGravadas5Produccion,
                        totalIva5Produccion, totalIva10Produccion, subTotal, totalIva5Produccion, totalIva10Produccion, totalIvaProduccion,
                        descuentoProduccion, totalProduccion, totalDescuentoProduccion, totalPagadoProduccion, entregaInicialProduccion,
                        cuotasProduccion, montoCuotaIgualProduccion, saldadoProduccion, cantidadItemsProduccion, activo, new Date());
                if (credito != null) {
                    t.setCreditosTransaccion(Arrays.asList(credito));
                }
            }
            try {
                totalMigrado = getFacade().cargaMasiva(lista);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
            //Asignar Venta a Motos
            Motostock motoDesarrollo;
            try {
                LOGGER.log(Level.INFO, "ASIGNACION DE VENTA A LAS MOTOS GUARDADAS");
                LOGGER.log(Level.INFO, "=============================================================");
                for (Vmventamotos ventaProduccion : listaVentasProduccion) {
                    motoDesarrollo = getMotostockFacade().findByNumeroAnterior(ventaProduccion.getIdMoto().getIdMoto());
                    LOGGER.log(Level.INFO, "La Moto es la Nro. {0}", motoDesarrollo.getId());
                    motoDesarrollo.setVenta(getFacade().findByIdAnterior(ventaProduccion.getIdVenta()));
                    LOGGER.log(Level.INFO, "Se la va a asiganr la venta Nro. {0}", motoDesarrollo.getVenta().getId());
                    getMotostockFacade().setEntity(motoDesarrollo);
                    getMotostockFacade().guardar();
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, "Excepcion al intentar asignar ventas al stock", ex);
            }
            setInfoMessage(null, "Total de registros importados: " + totalMigrado);
        }


        return "listacompramotos";
    }

    @Override
    public String todos() {
        limpiarCampos();
        venta = new Transaccion();
        getFacade().setEntity(venta);
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
        venta = new Transaccion();
        getFacade().setEntity(venta);
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
        totalX = subtotalX.subtract(totalDescuentoX);
        //Pasar Datos
        totalIva5 = totalIva5X.toString();
        totalIva10 = totalIva10X.toString();
        subtotal = subtotalX.toString();
        totalDescuento = BigDecimal.ZERO.toString();
        descuento = totalDescuento;
        total = totalX.toString();
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
    public Boolean getAnulado() {
        return anulado;
    }

    /**
     * @param anulado the anulado to set
     */
    public void setAnulado(Boolean anulado) {
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
    public Boolean getSaldado() {
        return saldado;
    }

    /**
     * @param saldado the saldado to set
     */
    public void setSaldado(Boolean saldado) {
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
            n = new BigDecimal(total.trim());
            total = formatNumero.format(n.doubleValue());
        } finally {
            return total;
        }
    }

    /**
     * @param toTal the toTal to set
     */
    public void setTotal(String total) {
        this.total = total;
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

    public Character getActivoFiltro() {
        return activoFiltro;
    }

    public void setActivoFiltro(Character activoFiltro) {
        this.activoFiltro = activoFiltro;
    }

    public Character getAnuladoFiltro() {
        return anuladoFiltro;
    }

    public void setAnuladoFiltro(Character anuladoFiltro) {
        this.anuladoFiltro = anuladoFiltro;
    }

    public Integer getCategoriaFiltro() {
        return categoriaFiltro;
    }

    public void setCategoriaFiltro(Integer categoriaFiltro) {
        this.categoriaFiltro = categoriaFiltro;
    }

    public String getComprobanteFiltro() {
        return comprobanteFiltro;
    }

    public void setComprobanteFiltro(String comprobanteFiltro) {
        this.comprobanteFiltro = comprobanteFiltro;
    }

    public Character getSaldadoFiltro() {
        return saldadoFiltro;
    }

    public void setSaldadoFiltro(Character saldadoFiltro) {
        this.saldadoFiltro = saldadoFiltro;
    }

    public Integer getCompradorFiltro() {
        return compradorFiltro;
    }

    public void setCompradorFiltro(Integer compradorFiltro) {
        this.compradorFiltro = compradorFiltro;
    }

    public String getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(String idFiltro) {
        this.idFiltro = idFiltro;
    }
}

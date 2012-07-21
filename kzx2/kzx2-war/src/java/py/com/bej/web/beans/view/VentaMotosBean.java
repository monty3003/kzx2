/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Factura;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Pagare;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PagareFacade;
import py.com.bej.orm.session.PagoFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.session.UbicacionFacade;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.GeneradorReporte;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class VentaMotosBean extends AbstractPageBean<Transaccion> {

    @EJB
    private PagareFacade pagareFacade;
    @EJB
    private FinanciacionFacade financiacionFacade;
    @EJB
    private CreditoFacade creditoFacade;
    @EJB
    private PagoFacade pagoFacade;
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
    private Credito credito;
    private String id;
    private List<SelectItem> listaCategoria;
    private List<SelectItem> listaCliente;
    private List<SelectItem> listaMoto;
    private Motostock motoVendida;
    private Integer idMotoVendida;
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
    private Integer vendedor;
    @NotNull(message = "Ingrese un valor")
    private BigDecimal precioUnitario;
    private Boolean saldado;
    private Boolean anulado;
    private Boolean activo;
    private Boolean usarFechaAhora = false;
    private Boolean usarFechaEntregaAhora = false;
    //Garante
    private Boolean conGarante;
    private Integer garanteId;
    private String garanteDocumento;
    private String garanteRuc;
    private String garanteNombre;
    private String garanteDireccion1;
    private String garanteDireccion2;
    private String garanteTelefonoFijo;
    private String garanteTelefonoMovil;
    private String garanteEmail;
    private String garanteContacto;
    private String garanteProfesion;
    private Character garanteEstadoCivil;
    private String garanteFechaNacimiento;
    private String garanteTratamiento;
    private Character garanteSexo;
    private Short garanteHijos;
    //Pagares
    private Boolean imprimirVencimientoEnPagare;
    private List<Pagare> pagares;
    private List<SelectItem> listaOpcionesPagare;
    private String opcionPagare;
    //Plan de Ventas
    private Short plan;
    private List<SelectItem> listaDePlanDeVenta;
    //Informe
    private List<Transaccion> listaInforme;
    private Date fechaDesde;
    private Date fechaHasta;
    private Boolean usarFechaDesdeAhora;
    private Boolean usarFechaHastaAhora;

    /** Creates a new instance of VentaMotosBean */
    public VentaMotosBean() {
    }

    @Override
    void limpiarCampos() {
        venta = null;
        credito = null;
        motoVendida = null;
        idMotoVendida = null;
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        //Campos de busqueda
        this.idFiltro = null;
        this.comprobanteFiltro = null;
        this.categoriaFiltro = null;
        this.compradorFiltro = null;
        this.saldadoFiltro = null;
        this.setAnuladoFiltro(null);
        //Transaccion de Compra
        this.cantidad = null;
        this.usarFechaAhora = false;
        this.usarFechaEntregaAhora = false;
        //Nueva Venta
        precioUnitario = null;
        motoVendida = null;
        imprimirVencimientoEnPagare = null;
        opcionPagare = null;
        plan = -2;
        listaDePlanDeVenta = null;
        listaInforme = null;
        fechaDesde = null;
        fechaHasta = null;
        usarFechaDesdeAhora = null;
        usarFechaHastaAhora = null;
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
    public FinanciacionFacade getFinanciacionFacade() {
        if (this.financiacionFacade == null) {
            this.financiacionFacade = new FinanciacionFacade();
        }
        return financiacionFacade;
    }

    /**
     * @return the facade
     */
    public PagareFacade getPagareFacade() {
        if (this.pagareFacade == null) {
            this.pagareFacade = new PagareFacade();
        }
        return pagareFacade;
    }

    public CreditoFacade getCreditoFacade() {
        if (creditoFacade == null) {
            creditoFacade = new CreditoFacade();
        }
        return creditoFacade;
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
    public PagoFacade getPagoFacade() {
        if (this.pagoFacade == null) {
            this.pagoFacade = new PagoFacade();
        }
        return pagoFacade;
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
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategoria = JsfUtils.getSelectItems(getCategoriaFacade().findBetween(CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol()), !getModificar());
        listaCliente = JsfUtils.getSelectItems(getPersonaFacade().findBetween(CategoriaEnum.CLIENTE_PF.getSymbol(), CategoriaEnum.CLIENTE_PJ.getSymbol()), !getModificar());
    }

    @Override
    public String buscar() {
        getFacade().setEntity(new Transaccion(
                (idFiltro != null && !idFiltro.trim().equals("")) ? Integer.valueOf(idFiltro) : null,
                (categoriaFiltro != null && categoriaFiltro > 0) ? new Categoria(categoriaFiltro) : null,
                (comprobanteFiltro != null && comprobanteFiltro.trim().equals("")) ? new Factura(comprobanteFiltro) : null,
                (compradorFiltro != null && compradorFiltro > 0) ? new Persona(compradorFiltro) : new Persona(),
                (anuladoFiltro != null && !anuladoFiltro.equals('X')) ? anuladoFiltro : null,
                (saldadoFiltro != null && !saldadoFiltro.equals('X')) ? saldadoFiltro : null,
                (activoFiltro != null && !activoFiltro.equals('X')) ? activoFiltro : null));
        getFacade().getEntity().setVendedor(new Persona());
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
        venta.setVendedor(getPersonaFacade().findByDocumento(ConfiguracionEnum.PROPIETARIO.getSymbol()));
        obtenerListas();
        credito = new Credito();
        return "vendermotos";
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
            guardarVenta();
            if (credito.getTransaccion().getCodigo().getId().equals(CategoriaEnum.VENTA_MCR_CORRIDAS.getSymbol())) {
                //Pagar la primera cuota
                Pago p = null;
                p = getPagoFacade().realizarPago(credito, LoginBean.getInstance().getUsuario(), venta.getMontoCuotaIgual());
                //Actualizar Cabecera
                credito = getCreditoFacade().consolidarCreditoPorPago(p.getCredito().getId());
            }
            return "resumenVenta";
        } else {
            return null;
        }
    }

    public String resumenVenta(Integer ventaId) {
        try {
            venta = facade.find(ventaId);
            motoVendida = getMotostockFacade().findByVenta(venta.getId());
            if (!venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
                credito = getCreditoFacade().findByTransaccion(venta.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "resumenVenta";
    }

    public String resumenVenta() {
        limpiarCampos();
        try {
            //recuperar la seleccion
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            idFiltro = (String) request.getParameter("radio");
            if (idFiltro != null) {
                venta = facade.find(new Integer(idFiltro));
                motoVendida = getMotostockFacade().findByVenta(venta.getId());
                if (!venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
                    credito = getCreditoFacade().findByTransaccion(venta.getId());
                }
            } else {
                setErrorMessage(null, facade.sel);
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "resumenVenta";
    }

    public String entregarMoto() {
        motoVendida.setEstado(new Categoria(CategoriaEnum.VENDIDO_ENTREGADO.getSymbol()));
        getMotostockFacade().setEntity(motoVendida);
        getMotostockFacade().guardar();
        venta.setFechaEntrega(new Date());
        getFacade().setEntity(venta);
        getFacade().guardar();
        setInfoMessage("frm:entrega", "Debe imprimir la Responsabilidad Civil inmediatamente.");
        return null;
    }

    public void imprimirDocumento() {
        new GeneradorReporte().generarDocumentosDeVenta(venta);
    }

    public void imprimirResponsabilidadCivil() {
        new GeneradorReporte().generarResposabilidadCivil(venta);
    }

    public void imprimirReciboInicial() {
        new GeneradorReporte().generarReciboInicial(venta);
    }

    public void imprimirPagare() {
        new GeneradorReporte().generarPagares(venta);
    }

    private void calcularOpcionesPagare() {
        listaOpcionesPagare = new ArrayList<SelectItem>();
        listaOpcionesPagare.add(new SelectItem("X", "-SELECCIONAR-"));
        if (venta.getCuotas() != null && venta.getCuotas() > 0) {
            listaOpcionesPagare.add(new SelectItem("0", venta.getCuotas() + " x " + Conversor.numberToStringPattern(venta.getMontoCuotaIgual())));
            BigDecimal limiteMinimo = new BigDecimal(ConfiguracionEnum.LIMITE_MINIMO_PAGARE.getSymbol());
            BigDecimal limiteMaximo = new BigDecimal(ConfiguracionEnum.LIMITE_MAXIMO_PAGARE.getSymbol());
            if (credito.getCreditoTotal() != null) {
                if (credito.getCreditoTotal().equals(credito.getCreditoTotal().max(limiteMaximo))) {
                    BigDecimal xmonto = credito.getCreditoTotal();
                    int numero = 2;
                    do {
                        xmonto = credito.getCreditoTotal().divide(BigDecimal.valueOf(numero), motoVendida.getPlan().getIndiceRedondeo(), RoundingMode.HALF_DOWN);
                        listaOpcionesPagare.add(new SelectItem("" + numero, numero + " x " + Conversor.numberToStringPattern(xmonto)));
                        numero++;
                    } while (xmonto.equals(xmonto.max(limiteMinimo)) && numero <= credito.getAmortizacion());
                } else {
                    listaOpcionesPagare.add(new SelectItem("1", "1 x " + Conversor.numberToStringPattern(credito.getCreditoTotal())));
                }
            }
        }
    }

    @Override
    public boolean validar() {
        boolean res = true;
        if (venta.getFechaOperacion() == null) {
            setErrorMessage("frm:fechaOperacion", "Ingrese un valor");
            res = false;
        }
        if (venta.getCodigo().getId() == -1) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        if (motoVendida.getMotor() == null || motoVendida.getMotor().trim().equals("")) {
            setErrorMessage("frm:motor", "Ingrese un valor");
            res = false;
        }
        //EDADES
        Calendar nacimiento = GregorianCalendar.getInstance();
        Calendar ahora = GregorianCalendar.getInstance();
        nacimiento.setTime(venta.getComprador().getFechaNacimiento());
        nacimiento.add(Calendar.YEAR, 18);
        if (nacimiento.after(ahora)) {
            setErrorMessage("frm:fechaNacimiento", "El comprador debe tener 18 años cumplidos");
            res = false;
        }
        if (!venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
            nacimiento.setTime(credito.getGarante().getFechaNacimiento());
            nacimiento.add(Calendar.YEAR, 18);
            if (nacimiento.after(ahora)) {
                setErrorMessage("frm:garanteFechaNacimiento", "El garante debe tener 18 años cumplidos");
                res = false;
            }
            if (imprimirVencimientoEnPagare == null) {
                setErrorMessage("frm:pagaresSinVencimiento", "Seleccione un valor");
                res = false;
            }
            if (opcionPagare == null || opcionPagare.equals("X")) {
                setErrorMessage("frm:opcionPagare", "Seleccione un valor");
                res = false;
            }
        } else {
            BigDecimal descuentoX = motoVendida.getPlan().getMontoDescuento().add(motoVendida.getPlan().getRangoNegociacionMaximo());
            if (venta.getTotalDescuento().compareTo(BigDecimal.ZERO) < 0) {
                if (venta.getTotalDescuento().compareTo(motoVendida.getPlan().getMontoDescuento()) > 0) {
                    setErrorMessage("frm:descuento", "Valor fuera del rango establecido");
                    res = false;
                }
            } else {
                if (venta.getTotalDescuento().compareTo(descuentoX) > 0) {
                    setErrorMessage("frm:descuento", "Valor fuera del rango establecido");
                    res = false;
                }
            }
        }
        return res;
    }

    private void guardarVenta() {
        //Cliente
        if (venta.getComprador().getFisica() == 'S') {
            venta.getComprador().setCategoria(getCategoriaFacade().find(CategoriaEnum.CLIENTE_PF.getSymbol()));
        } else {
            venta.getComprador().setCategoria(getCategoriaFacade().find(CategoriaEnum.CLIENTE_PJ.getSymbol()));
        }
        //Factura
        venta.setCodigo(getCategoriaFacade().find(venta.getCodigo().getId()));
        venta.getFactura().setCategoria(venta.getCodigo());
        if (!venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
            venta.getFactura().setSaldado('N');
        } else {
            venta.getFactura().setSaldado('S');
        }
        venta.getFactura().setActivo('S');
        venta.getFactura().setNetoSinIva5(BigDecimal.ZERO);
        venta.getFactura().setNetoSinIva10(venta.getSubTotalGravadas10());
        venta.getFactura().setSubTotalExentas(venta.getSubTotalExentas());
        venta.getFactura().setSubTotal(venta.getSubTotal());
        venta.getFactura().setSubTotalGravadas5(BigDecimal.ZERO);
        venta.getFactura().setSubTotalGravadas10(venta.getSubTotalGravadas10());
        venta.getFactura().setTotalIva(venta.getTotalIva());
        venta.getFactura().setTotalIva10(venta.getTotalIva());
        venta.getFactura().setTotalIva5(BigDecimal.ZERO);
        venta.getFactura().setTotalPagado(venta.getTotal());
        venta.getFactura().setDescuento(venta.getDescuento());
        venta.setCantidadItems((short) 1);
        venta.setActivo('N');
        venta.setAnulado('N');
        if (!venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
            venta.setSaldado('N');
        } else {
            venta.setSaldado('S');
            venta.setTotalPagado(venta.getTotal());
            venta.setEntregaInicial(venta.getTotalPagado());
            venta.setMontoCuotaIgual(BigDecimal.ZERO);
        }
        venta.setUsuarioCreacion(LoginBean.getInstance().getUsuario());
        venta.setFechaCreacion(new Date());
        venta.setUsuarioModificacion(LoginBean.getInstance().getUsuario());
        venta.setUltimaModificacion(new Date());
        venta.getFactura().setUltimaModificacion(venta.getUltimaModificacion());
        venta.getFactura().setTransaccion(venta);
        getFacade().guardarVenta(venta);
        motoVendida.setEstado(new Categoria(CategoriaEnum.VENDIDO_SIN_ENTREGAR.getSymbol()));
        motoVendida.setVenta(venta);
        getMotostockFacade().setEntity(motoVendida);
        getMotostockFacade().guardar();
        if (!venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
            try {
                //Credito
                if (conGarante) {
                    if (credito.getGarante().getId() == null) {
                        //Garante Nuevo
                        credito.getGarante().setFechaIngreso(new Date());
                        credito.getGarante().setEmail("mail@mail.com");
                        credito.getGarante().setHijos(Short.valueOf("0"));
                        credito.getGarante().setHabilitado('S');
                        credito.getGarante().setActivo('S');
                        credito.getGarante().setUltimaModificacion(new Date());
                        credito.getGarante().setFisica('S');
                    }
                    credito.getGarante().setCategoria(categoriaFacade.find(CategoriaEnum.CLIENTE_PF.getSymbol()));
                }
                getCreditoFacade().abrirCredito(venta, credito);
                venta.setTotal(credito.getCreditoTotal().add(venta.getEntregaInicial()));
                //Pagares
                List<Financiacion> cuotasFijas = getFinanciacionFacade().findByTransaccion(venta.getId());
                pagares = new ArrayList<Pagare>();
                short cantidadPagares = 0;
                if (opcionPagare.equals("0")) {
                    //Un pagare por cuota
                    cantidadPagares = credito.getAmortizacion();
                } else {
                    cantidadPagares = new Short(opcionPagare);
                }
                BigDecimal montoPorPagare = null;
                if (cantidadPagares == credito.getAmortizacion()) {
                    montoPorPagare = venta.getMontoCuotaIgual();
                    for (Financiacion f : cuotasFijas) {
                        pagares.add(new Pagare(null, credito, f.getNumeroCuota(), f.getFechaVencimiento(), montoPorPagare,
                                Boolean.FALSE, imprimirVencimientoEnPagare, null,
                                LoginBean.getInstance().getUsuario(), LoginBean.getInstance().getUsuario(), 'S', new Date(), new Date()));
                    }
                } else {
                    montoPorPagare = credito.getCreditoTotal().divide(BigDecimal.valueOf(cantidadPagares), RoundingMode.UP);
                    short c = 1;
                    short h = 1;
                    for (int x = 1; x <= cantidadPagares; x++) {
                        Date vencimiento = null;
                        BigDecimal conjunto = BigDecimal.ZERO;
                        for (Financiacion f : cuotasFijas) {
                            if (f.getNumeroCuota() == h) {
                                conjunto = conjunto.add(f.getTotalAPagar());
                                h++;
                            }
                            if (conjunto.compareTo(montoPorPagare) >= 0
                                    || f.getNumeroCuota() == credito.getAmortizacion()) {
                                vencimiento = f.getFechaVencimiento();
                                break;
                            }
                        }
                        if (vencimiento == null) {
                            vencimiento = credito.getFechaFin();
                        }
                        pagares.add(new Pagare(null, credito, c, vencimiento, montoPorPagare,
                                Boolean.FALSE, imprimirVencimientoEnPagare, null,
                                LoginBean.getInstance().getUsuario(), LoginBean.getInstance().getUsuario(), 'S', new Date(), new Date()));
                        c++;
                    }
                }
                for (Pagare p : pagares) {
                    getPagareFacade().create(p);
                }
            } catch (Exception ex) {
                Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void buscarComprador() {
        if (venta.getComprador().getDocumento() != null && !venta.getComprador().getDocumento().trim().equals("")) {
            Persona existeComprador = null;
            existeComprador = getPersonaFacade().findByDocumento(venta.getComprador().getDocumento());
            if (existeComprador != null) {
                setInfoMessage("frm:documento", "El cliente ya existe. Puede actualizar sus datos si es necesario");
                venta.setComprador(existeComprador);
            } else {
                venta.setComprador(new Persona(null, venta.getComprador().getDocumento()));
            }
        }
    }

    public void buscarGarante() {
        if (credito.getGarante().getDocumento() != null && !credito.getGarante().getDocumento().trim().equals("")) {
            Persona gar = null;
            gar = getPersonaFacade().findByDocumento(credito.getGarante().getDocumento());
            if (gar != null) {
                if (gar.getId().equals(venta.getComprador().getId())) {
                    setErrorMessage("frm:garanteDocumento", "El Cliente Titular no puede ser su Garante");
                } else {
                    setInfoMessage("frm:garanteDocumento", "El garante ya existe. Puede actualizar sus datos si es necesario");
                    credito.setGarante(gar);
                }
            }
        }
    }

    public void buscarMotoVendida() {
        if (idMotoVendida > 0) {
            motoVendida = getMotostockFacade().find(idMotoVendida);
            if (motoVendida != null && motoVendida.getVenta() != null) {
                setErrorMessage("frm:nMoto", "Esta Moto ya está vendida.");
                motoVendida = null;
            } else if (motoVendida.getActivo().equals('N') || motoVendida.getEstado().getId() > 4) {
                setErrorMessage("frm:nMoto", "Esta Moto no está disponible.");
                motoVendida = null;
            } else {
                if (motoVendida != null) {
                    if (motoVendida.getEstado().getId() < 2) {
                        setInfoMessage("frm:nMoto", "Esta Moto no tiene documentos guardados.");
                    }
                    venta.setEntregaInicial(null);
                    venta.setCuotas(Short.MIN_VALUE);
                    venta.setSubTotal(null);
                    venta.setCodigo(null);
                    obtenerListaDeTiposDeVenta();
                }
            }
        }
    }

    @Override
    public String todos() {
        limpiarCampos();
        venta = new Transaccion();
        getFacade().setEntity(venta);
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
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

    private void obtenerListaDeTiposDeVenta() {
        BigDecimal interesMensual;
        BigDecimal capitalMensual;
        listaDePlanDeVenta = new ArrayList<SelectItem>();
        listaDePlanDeVenta.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaDePlanDeVenta.add(new SelectItem(0, "Al Contado"));
        listaDePlanDeVenta.add(new SelectItem(1, "Con Entrega Inicial"));
        for (short c = motoVendida.getPlan().getCuotaCorridaDesde(); c <= motoVendida.getPlan().getCuotaCorridaHasta(); c++) {
            interesMensual = BigDecimal.valueOf((motoVendida.getPlan().getTan() * c) / (c >= 12 ? 12 : 1)).setScale(4, RoundingMode.HALF_DOWN);
            capitalMensual = motoVendida.getPrecioBase().divide(BigDecimal.valueOf(c), 4, RoundingMode.HALF_DOWN);
            BigDecimal netoConInteres = capitalMensual.multiply(interesMensual.add(BigDecimal.ONE));
            BigDecimal cuotaX = netoConInteres.setScale(motoVendida.getPlan().getIndiceRedondeo(), RoundingMode.HALF_UP);
            LOGGER.log(Level.INFO, "Plan {0} meses: El interes mensual es {1}, el Neto con interes es {2} y la cuota es {3}",
                    new Object[]{c, interesMensual, netoConInteres, cuotaX});
            listaDePlanDeVenta.add(new SelectItem(c, c + " Cuotas de " + Conversor.numberToStringPattern(cuotaX)));
        }

    }

    public void calcularPlanDeVentaCuotaCorrida() {
        venta.setEntregaInicial(null);
        venta.setMontoCuotaIgual(null);
        venta.setCuotas(null);
        calcularPlanDeVenta();
    }

    public void calcularPlanDeVenta() {
        conGarante = Boolean.TRUE;
        //Validar datos
        if (plan < 0) {
            //Error de seleccion
            setErrorMessage("frm:planDeVenta", "Seleccione un valor");
        } else if (plan == 0) {
            //Venta Contado
            venta.setCodigo(new Categoria(CategoriaEnum.VENTA_MCO.getSymbol()));
            venta.setCuotas(plan);
            venta.setEntregaInicial(motoVendida.getPrecioContado());
            venta.setDescuento(motoVendida.getPlan().getPorcentajeDescuento());
            venta.setTotalPagado(venta.getEntregaInicial());
        } else if (plan == 1) {
            //Venta con Entrega Inicial
            credito = new Credito();
            venta.setCodigo(new Categoria(CategoriaEnum.VENTA_MCR_ENTREGA.getSymbol()));
            if (venta.getEntregaInicial() == null) {
                venta.setEntregaInicial(motoVendida.getPlan().getMontoEntregaMinimo());
                venta.setDescuento(motoVendida.getPlan().getPorcentajeDescuento());
                venta.setTotalPagado(venta.getEntregaInicial());
            }
            if (venta.getCuotas() == null || venta.getCuotas() < 1) {
                venta.setCuotas(motoVendida.getPlan().getFinanciacionMaxima());
                credito.setAmortizacion(venta.getCuotas());
            }
            boolean esParaCalculoValido = true;
            do {
                if (venta.getEntregaInicial().compareTo(motoVendida.getPlan().getMontoEntregaMinimo()) != 0
                        && venta.getEntregaInicial().compareTo(motoVendida.getPlan().getMontoEntregaMaximo()) != 0) {
                    if (venta.getEntregaInicial().equals(venta.getEntregaInicial().min(motoVendida.getPlan().getMontoEntregaMinimo()))
                            || venta.getEntregaInicial().equals(venta.getEntregaInicial().max(motoVendida.getPlan().getMontoEntregaMaximo()))) {
                        //La entrega es muy baja
                        setErrorMessage("frm:entregaInicial", "Entrega Inicial fuera del rango permitido");
                        esParaCalculoValido = false;
                        continue;
                    }
                }
                if (venta.getEntregaInicial().compareTo(motoVendida.getPlan().getMontoEntregaMaximo()) == 0) {
                    //No hace falta Garante.
                    conGarante = Boolean.FALSE;
                    setInfoMessage("frm:incluirGarante", "Este plan no obliga a tener garante. De todas formas puede incluir uno.");
                }

                if (venta.getCuotas() < motoVendida.getPlan().getFinanciacionMinima()
                        || (venta.getCuotas() > motoVendida.getPlan().getFinanciacionMaxima())) {
                    setErrorMessage("frm:cuotas", "valor fuera del rango permitido");
                    esParaCalculoValido = false;
                    continue;
                } else {
                    credito.setTan(motoVendida.getPlan().getTan());
                    credito.setTae(motoVendida.getPlan().getTae());
                    credito.setCapital(motoVendida.getPrecioBase().subtract(venta.getEntregaInicial()));
                    BigDecimal interesAcumulado = BigDecimal.valueOf((credito.getTan() * venta.getCuotas()) / (venta.getCuotas() >= 12 ? 12 : 1)).setScale(4, RoundingMode.HALF_DOWN);
                    BigDecimal capitalMensual = credito.getCapital().divide(BigDecimal.valueOf(venta.getCuotas()), 4, RoundingMode.HALF_DOWN);
                    BigDecimal netoConInteres = capitalMensual.multiply(interesAcumulado.add(BigDecimal.ONE).setScale(4, RoundingMode.HALF_DOWN));
                    BigDecimal cuotaX = netoConInteres.setScale(motoVendida.getPlan().getIndiceRedondeo(), RoundingMode.HALF_UP);
                    venta.setMontoCuotaIgual(cuotaX);
                    credito.setCreditoTotal(venta.getMontoCuotaIgual().multiply(BigDecimal.valueOf(venta.getCuotas())));
                    if (venta.getMontoCuotaIgual().compareTo(new BigDecimal(ConfiguracionEnum.MONTO_MINIMO_CUOTA.getSymbol())) < 0) {
                        setErrorMessage("frm:montoCuotaIgual", "Cuota fuera del rango permitido");
                    }
                    break;
                }

            } while (esParaCalculoValido);
            calcularOpcionesPagare();
        } else {
            //Venta con cuotas corridas
            venta.setCodigo(new Categoria(CategoriaEnum.VENTA_MCR_CORRIDAS.getSymbol()));
            venta.setCuotas(plan);
            credito = new Credito();
            credito.setTan(motoVendida.getPlan().getTan());
            credito.setTae(motoVendida.getPlan().getTae());
            credito.setCapital(motoVendida.getPrecioBase());
            BigDecimal interesAcumulado = BigDecimal.valueOf((credito.getTan() * venta.getCuotas()) / (venta.getCuotas() >= 12 ? 12 : 1)).setScale(4, RoundingMode.HALF_DOWN);
            BigDecimal capitalMensual = credito.getCapital().divide(BigDecimal.valueOf(venta.getCuotas()), 4, RoundingMode.HALF_DOWN);
            BigDecimal netoConInteres = capitalMensual.multiply(interesAcumulado.add(BigDecimal.ONE).setScale(4, RoundingMode.HALF_DOWN));
            BigDecimal cuotaX = netoConInteres.setScale(motoVendida.getPlan().getIndiceRedondeo(), RoundingMode.HALF_UP);
            venta.setMontoCuotaIgual(cuotaX);
            credito.setCreditoTotal(cuotaX.multiply(BigDecimal.valueOf(venta.getCuotas())));
            credito.setAmortizacion(plan);
            venta.setCuotas(plan);
            venta.setEntregaInicial(BigDecimal.ZERO);
            venta.setDescuento(motoVendida.getPlan().getPorcentajeDescuento());
            venta.setTotalPagado(venta.getEntregaInicial());
            LOGGER.log(Level.INFO, "Plan {0} meses: El interes acumulado es {1}, el Neto con interes es {2} y la cuota es {3}",
                    new Object[]{plan, interesAcumulado, netoConInteres, cuotaX});

            calcularOpcionesPagare();
        }
        sumarResultados();
    }

    public void sumarResultados() {
        try {
            BigDecimal precioTotalDeLaVenta = null;
            BigDecimal gravamen = BigDecimal.valueOf(motoVendida.getGravamen());
            precioTotalDeLaVenta = motoVendida.getPrecioContado();
            venta.setTotalIva10(precioTotalDeLaVenta.multiply(gravamen).setScale(0, RoundingMode.HALF_UP));
            venta.setSubTotalGravadas10(venta.getTotalIva10().multiply(BigDecimal.TEN));
            venta.getFactura().setNetoSinIva10(venta.getSubTotalGravadas10().subtract(venta.getTotalIva10()));
            venta.setSubTotalExentas(precioTotalDeLaVenta.subtract(venta.getSubTotalGravadas10()));
            precioUnitario = precioTotalDeLaVenta.subtract(venta.getTotalIva10());
            venta.setSubTotal(precioTotalDeLaVenta);
            venta.setTotalDescuento(motoVendida.getPlan().getMontoDescuento());
            venta.setTotal(venta.getSubTotal().subtract(venta.getTotalDescuento()));
        } catch (Exception e) {
            Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String buscarVentasPorFecha() {
        Calendar calFechaDesde = GregorianCalendar.getInstance();
        Calendar calFechaHasta = GregorianCalendar.getInstance();
        Calendar xfechaDesde = null;
        Calendar xfechaHasta = null;
        calFechaDesde.setTime(fechaDesde);
        calFechaHasta.setTime(fechaHasta);
        xfechaDesde = new GregorianCalendar(calFechaDesde.get(Calendar.YEAR), calFechaDesde.get(Calendar.MONTH), calFechaDesde.get(Calendar.DAY_OF_MONTH), 00, 01);
        xfechaHasta = new GregorianCalendar(calFechaHasta.get(Calendar.YEAR), calFechaHasta.get(Calendar.MONTH), calFechaHasta.get(Calendar.DAY_OF_MONTH), 00, 01);
        try {
            listaInforme = getFacade().findByFechaYCodigo(CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol(), xfechaDesde.getTime(), xfechaHasta.getTime());
        } catch (Exception ex) {
            Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void imprimirInformeVentas() {
        new GeneradorReporte().generarInformeVentas(listaInforme, fechaDesde, fechaHasta);
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
        venta.setFechaOperacion(new Date());

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
        venta.setFechaEntrega(new Date());
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

    public Motostock getMotoVendida() {
        return motoVendida;
    }

    public void setMotoVendida(Motostock motoVendida) {
        this.motoVendida = motoVendida;
    }

    public Integer getIdMotoVendida() {
        return idMotoVendida;
    }

    public void setIdMotoVendida(Integer idMotoVendida) {
        this.idMotoVendida = idMotoVendida;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public List<SelectItem> getListaCliente() {
        return listaCliente;
    }

    public List<SelectItem> getListaMoto() {
        return listaMoto;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Boolean getImprimirVencimientoEnPagare() {
        return imprimirVencimientoEnPagare;
    }

    public void setImprimirVencimientoEnPagare(Boolean imprimirVencimientoEnPagare) {
        this.imprimirVencimientoEnPagare = imprimirVencimientoEnPagare;
        if (imprimirVencimientoEnPagare) {
            setInfoMessage("frm:vencimientoEnPagare", "Los pagares se imprmirán con fecha de Vencimiento.");
        }
    }

    public List<SelectItem> getListaOpcionesPagare() {
        return listaOpcionesPagare;
    }

    /**
     * @return the opcionPagare
     */
    public String getOpcionPagare() {
        return opcionPagare;
    }

    /**
     * @param opcionPagare the opcionPagare to set
     */
    public void setOpcionPagare(String opcionPagare) {
        this.opcionPagare = opcionPagare;
    }

    public List<SelectItem> getListaDePlanDeVenta() {
        return listaDePlanDeVenta;
    }

    public void setListaDePlanDeVenta(List<SelectItem> listaDePlanDeVenta) {
        this.listaDePlanDeVenta = listaDePlanDeVenta;
    }

    public Short getPlan() {
        return plan;
    }

    public void setPlan(Short plan) {
        this.plan = plan;
    }

    public Boolean getConGarante() {
        return conGarante;
    }

    public void setConGarante(Boolean conGarante) {
        this.conGarante = conGarante;
    }

    public List<Transaccion> getListaInforme() {
        return listaInforme;
    }

    public void setListaInforme(List<Transaccion> listaInforme) {
        this.listaInforme = listaInforme;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Boolean getUsarFechaDesdeAhora() {
        return usarFechaDesdeAhora;
    }

    public void setUsarFechaDesdeAhora(Boolean usarFechaDesdeAhora) {
        if (usarFechaDesdeAhora) {
            fechaDesde = new Date();
        }
        this.usarFechaDesdeAhora = usarFechaDesdeAhora;
    }

    public Boolean getUsarFechaHastaAhora() {
        return usarFechaHastaAhora;
    }

    public void setUsarFechaHastaAhora(Boolean usarFechaHastaAhora) {
        if (usarFechaHastaAhora) {
            fechaHasta = new Date();
        }
        this.usarFechaHastaAhora = usarFechaHastaAhora;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
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
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Factura;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FacturaFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.session.PlanFacade;
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
public class CompraMotosBean extends AbstractPageBean<Transaccion> {

    @EJB
    private FinanciacionFacade financiacionFacade;
    @EJB
    private PlanFacade planFacade;
    @EJB
    private FacturaFacade facturaFacade;
    @EJB
    private CreditoFacade creditoFacade;
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
    private Transaccion compra;
    private NumberFormat formatNumero;
    //listas de Seleccion
    private List<SelectItem> listaCategoria;
    private List<SelectItem> listaProveedor;
    private List<SelectItem> listaMoto;
    private List<SelectItem> listaUbicacion;
    private List<SelectItem> listaPlan;
    //Campos de busqueda
    private String idFiltro;
    private String idCategoria;
    private String comprobanteFiltro;
    private Integer categoriaFiltro;
    private Integer vendedorFiltro;
    private Boolean saldadoFiltro;
    private Boolean anuladoFiltro;
    private Boolean activoFiltro;
    //Transaccion de Compra
    private Short cantidad;
    private Date fechaOperacion;
    private Date fechaEntrega;
    private Integer comprador;
    private String subTotalExentas;
    private String subTotalGravadas10;
    private String subTotalGravadas5;
    private String subtotal;
    private String totalIva5;
    private String totalIva10;
    private String totalIva;
    private String total;
    private String totalDescuento;
    private String totalPagado;
    private String entregaInicial;
    private String cuotas;
    private String cantidadItems;
    private String montoCuotaIgual;
    private Boolean usarFechaAhora;
    private Boolean usarFechaEntregaAhora;
    private String gravamen;
    private List<Financiacion> listaCuotas;
    //Calculos Ajax
    private BigDecimal subTotalGravadas10X;
    private BigDecimal subTotalGravadas5X;
    private BigDecimal subtotalX;
    private BigDecimal totalX;
    private BigDecimal descuentoX;
    private BigDecimal totalDescuentoX;
    private Boolean esCredito;
    private Credito credito;

    /** Creates a new instance of CompraMotosBean */
    public CompraMotosBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        //Campos de busqueda
        this.idFiltro = null;
        this.comprobanteFiltro = null;
        this.categoriaFiltro = null;
        this.vendedorFiltro = null;
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

    public PlanFacade getPlanFacade() {
        if (planFacade == null) {
            planFacade = new PlanFacade();
        }
        return planFacade;
    }

    public FinanciacionFacade getFinanciacionFacade() {
        if (financiacionFacade == null) {
            financiacionFacade = new FinanciacionFacade();
        }
        return financiacionFacade;
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
     * @return the facturaFacade
     */
    public FacturaFacade getFacturaFacade() {
        if (this.facturaFacade == null) {
            this.facturaFacade = new FacturaFacade();
        }
        return facturaFacade;
    }

    /**
     * @return the creditoFacade
     */
    public CreditoFacade getCreditoFacade() {
        if (this.creditoFacade == null) {
            this.creditoFacade = new CreditoFacade();
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
        setNav("listacompramotos");
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        LoginBean.getInstance().setUbicacion("Compras de Motos");

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
        facade.setEntity(new Transaccion());
        getFacade().getEntity().setCodigo(new Categoria(CategoriaEnum.COMPRA_DESDE.getSymbol()));
        getFacade().getEntity().setCodigoMax(new Categoria(CategoriaEnum.COMPRA_HASTA.getSymbol()));
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategoria = JsfUtils.getSelectItems(getCategoriaFacade().findBetween(CategoriaEnum.COMPRA_DESDE.getSymbol(), CategoriaEnum.COMPRA_HASTA.getSymbol()), !getModificar());
        listaProveedor = JsfUtils.getSelectItems(getPersonaFacade().findByPersona(CategoriaEnum.PROVEEDOR.getSymbol()), !getModificar());
        listaUbicacion = JsfUtils.getSelectItems(getUbicacionFacade().findAll(), !getModificar());
        listaPlan = JsfUtils.getSelectItems(getPlanFacade().findAll(), !getModificar());
    }

    @Override
    public String buscar() {
        getFacade().setEntity(new Transaccion(
                (idFiltro != null && !idFiltro.trim().equals("")) ? Integer.valueOf(idFiltro) : null,
                (categoriaFiltro != null && categoriaFiltro > 0) ? new Categoria(categoriaFiltro) : null,
                (comprobanteFiltro != null && comprobanteFiltro.trim().equals("")) ? new Factura(comprobanteFiltro) : null,
                (comprador != null && comprador > 0) ? new Persona(comprador) : new Persona(),
                (anuladoFiltro) ? 'S' : 'N', (saldadoFiltro) ? 'S' : 'N', (activoFiltro != null && activoFiltro) ? 'S' : 'N'));
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
        LoginBean.getInstance().setUbicacion("Compra Nueva");
        limpiarCampos();
        compra = new Transaccion();
        obtenerListas();
        List<Moto> listaMotos = getMotoFacade().findAll();
        listaMoto = new ArrayList<SelectItem>();
        for (Moto m : listaMotos) {
            if (m.getActivo().equals('S')) {
                listaMoto.add(new SelectItem(m.getCodigo(), m.getMarca() + " " + m.getModelo() + " " + m.getColor()));
            }
        }
        comprador = 1;
        return "comprarmotos";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        idFiltro = (String) request.getParameter("radio");
        if (idFiltro != null) {
            try {
                compra = facade.find(new Integer(idFiltro));
                setAnuladoFiltro(compra.getAnulado().equals('S') ? Boolean.TRUE : Boolean.FALSE);
                setSaldadoFiltro(compra.getSaldado().equals('S') ? Boolean.TRUE : Boolean.FALSE);
                setActivoFiltro(compra.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(CompraMotosBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setModificar(Boolean.TRUE);
            setAgregar(Boolean.FALSE);
            obtenerListas();
            listaMoto = JsfUtils.getSelectItems(getMotoFacade().findAll(), getModificar());
            cargarMotosNuevas();
            LoginBean.getInstance().setUbicacion("Modificar Compra");
            return "comprarmotos";
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
            compra.setVendedor(getPersonaFacade().find(new Integer(idFiltro)));
            //Datos del comprobante
            Categoria tipoFactura = null;
            Boolean saldado = null;
            compra.setCodigo(new Categoria(new Integer(idCategoria)));
            if (compra.getCodigo().getId().equals(CategoriaEnum.COMPRA_MCO.getSymbol())) {
                tipoFactura = new Categoria(CategoriaEnum.FACTURA_COMPRA_MCO.getSymbol());
                compra.setEntregaInicial(compra.getTotalPagado());
                compra.setMontoCuotaIgual(BigDecimal.ZERO);
                compra.setCuotas(Short.valueOf("0"));
                saldado = Boolean.TRUE;
            } else {
                tipoFactura = new Categoria(CategoriaEnum.FACTURA_COMPRA_MCR.getSymbol());
                saldado = Boolean.FALSE;
            }
            compra.getFactura().setCategoria(tipoFactura);
            compra.getFactura().setSubTotalExentas(compra.getSubTotalExentas());
            compra.getFactura().setSubTotalGravadas5(compra.getSubTotalGravadas5());
            compra.getFactura().setSubTotalGravadas10(compra.getSubTotalGravadas10());
            compra.getFactura().setNetoSinIva5(compra.getNetoSinIva5());
            compra.getFactura().setNetoSinIva10(compra.getNetoSinIva10());
            compra.getFactura().setTotalIva5(compra.getTotalIva5());
            compra.getFactura().setTotalIva10(compra.getTotalIva10());
            compra.getFactura().setSubTotal(compra.getSubTotal());
            compra.getFactura().setTotalIva(compra.getTotalIva());
            compra.getFactura().setTotalPagado(compra.getTotalPagado());
            compra.getFactura().setActivo('S');
            compra.getFactura().setUltimaModificacion(new Date());
            compra.getFactura().setDescuento(compra.getDescuento() == null ? 0 : compra.getDescuento());
            compra.getFactura().setSaldado(saldado ? 'S' : 'N');
            compra.setUsuarioModificacion(LoginBean.getInstance().getUsuario());
            if (getModificar()) {
                compra.setAnulado(anuladoFiltro ? 'S' : 'N');
                compra.setSaldado(saldadoFiltro ? 'S' : 'N');
                compra.setActivo(activoFiltro ? 'S' : 'N');
                facade.setEntity(compra);
                facade.guardar();
                setInfoMessage(null, facade.ex2);
            } else {
                compra.setUsuarioCreacion((LoginBean.getInstance().getUsuario()));
                compra.setFechaCreacion(new Date());
                compra.setComprador(getPersonaFacade().find(new Integer(comprador)));
                compra.setAnulado('N');
                compra.setActivo('S');
                compra.setSaldado(saldado ? 'S' : 'N');
                compra.setUltimaModificacion(new Date());
                //Stock
                try {
                    Categoria estado = getCategoriaFacade().find(CategoriaEnum.RECIBIDO.getSymbol());
                    BigDecimal precio[] = new BigDecimal[2];
                    Float gravamenF = new Float(gravamen) / 100;
                    for (Motostock m : compra.getMotostocksCompra()) {
                        m.setActivo('S');
                        m.setCompra(compra);
                        m.setUltimaModificacion(new Date());
                        precio = MotostockBean.calcularPrecios(m.getCosto());
                        m.setPrecioBase(precio[0]);
                        m.setPrecioContado(precio[1]);
                        m.setEstado(estado);
                        m.setGravamen(gravamenF);
                    }
                    getFacade().setEntity(compra);
                    getFacade().guardar();

                    if (!compra.getCodigo().getId().equals(CategoriaEnum.COMPRA_MCO.getSymbol())) {
                        //Credito
                        if (compra.getCodigo().getId().equals(CategoriaEnum.COMPRA_MCR.getSymbol())) {
                            credito = new Credito();
                            credito.setGarante(null);
                            credito.setEstado(new Categoria(CategoriaEnum.ABIERTO.getSymbol()));
                            credito.setCapital(compra.getTotal().subtract(compra.getEntregaInicial(), MathContext.UNLIMITED));
                            credito.setCreditoTotal(credito.getCapital());
                            credito.setTan(1F);
                            credito.setTae(1F);
                            credito.setFechaInicio(compra.getFechaOperacion());
                            credito.setFechaFin(compra.getFechaOperacion());
                            credito.setAmortizacion(compra.getCuotas());
                            credito.setTotalAmortizadoPagado(BigDecimal.ZERO);
                            credito.setTotalDescuento(BigDecimal.ZERO);
                            credito.setTotalInteresesPagado(BigDecimal.ZERO);
                            credito.setTotalInteresesPagadoMulta(BigDecimal.ZERO);
                            getFacade().persist(compra);
                            credito.setTransaccion(compra);
                            credito.setUltimaModificacion(new Date());
                            credito.setActivo('S');
                            getCreditoFacade().setEntity(credito);
                            getCreditoFacade().persist(credito);
                            getCreditoFacade().abrirCredito(compra, credito);
                            compra.setTotal(credito.getCreditoTotal().add(compra.getEntregaInicial()));
                            getFacade().setEntity(compra);
                            getFacade().guardar();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CompraMotosBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                setNav("resumenCompra");
            }
            return this.todos();
        } else {
            return null;
        }
    }

    public String guardarCambiosEnLasCuotas() {
        getFinanciacionFacade().guardarCambiosEnCuotas(credito.getFinanciacions());
        return "listacompramotos";
    }

    public String resumenCompra(Integer compraId) {
        try {
            compra = facade.find(compraId);
            if (!compra.getCodigo().getId().equals(CategoriaEnum.COMPRA_MCO.getSymbol())) {

                credito = getCreditoFacade().findByTransaccion(compra.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(CompraMotosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "resumenCompra";
    }

    public String resumenCompra() {
        limpiarCampos();
        try {
            //recuperar la seleccion
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            idFiltro = (String) request.getParameter("radio");
            if (idFiltro != null) {
                compra = facade.find(new Integer(idFiltro));
                if (!compra.getCodigo().getId().equals(CategoriaEnum.COMPRA_MCO.getSymbol())) {
                    credito = getCreditoFacade().findByTransaccion(compra.getId());
                }
            } else {
                setErrorMessage(null, facade.sel);
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(CompraMotosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "resumenCompra";
    }

    @Override
    public boolean validar() {
        boolean res = true;
        if (idCategoria.equals("-1")) {
            setErrorMessage("frm:categoria", "Seleccione una categoria");
            res = false;
        }
        if (idFiltro == null || idFiltro.trim().equals("-1")) {
            setErrorMessage("frm:vendedor", "Seleccione un proveedor");
            res = false;
        }
        if (compra.getSubTotalExentas().equals(BigDecimal.ZERO)
                && compra.getSubTotalGravadas5().equals(BigDecimal.ZERO)
                && compra.getSubTotalGravadas10().equals(BigDecimal.ZERO)) {
            setErrorMessage("frm:subTotalGravadas10", "Sub Totales: Ingrese un monto");
            res = false;
        }
        if (compra.getTotalPagado().equals(BigDecimal.ZERO)) {
            setErrorMessage("frm:total", "Total: Ingrese un monto");
            res = false;
        }
        if (compra.getCantidadItems() == null || compra.getCantidadItems() < 1) {
            setErrorMessage("frm:cantidadItems", "Ingrese por lo menos un item");
            res = false;
        } else if (compra.getMotostocksCompra() == null || compra.getMotostocksCompra().isEmpty()) {
            for (int i = 0; i < compra.getMotostocksCompra().size(); i++) {
                if (compra.getMotostocksCompra().get(i).getChasis() == null
                        || compra.getMotostocksCompra().get(i).getChasis().trim().equals("")) {
                    setErrorMessage("frm:chasis", "Moto " + i++ + ":Ingrese un codigo de chasis");
                    res = false;
                    break;
                }
                if (compra.getMotostocksCompra().get(i).getUbicacion().getId() == null
                        || compra.getMotostocksCompra().get(i).getUbicacion().getId() < 0) {
                    setErrorMessage("frm:ubicacion", "Moto " + i++ + ": Seleccione una ubicacion");
                    res = false;
                    break;
                }
                if (compra.getMotostocksCompra().get(i).getPlan().getId() == null
                        || compra.getMotostocksCompra().get(i).getPlan().getId() < 0) {
                    setErrorMessage("frm:plan", "Moto " + i++ + ": Seleccione un plan");
                    res = false;
                    break;
                }
                if (compra.getMotostocksCompra().get(i).getMoto().getCodigo() == null
                        || compra.getMotostocksCompra().get(i).getMoto().getCodigo().trim().equals("X")) {
                    setErrorMessage("frm:modelo", "Moto " + i++ + ": Seleccione un modelo");
                    res = false;
                    break;
                }
            }
        }



        return res;
    }

    @Override
    public String todos() {
        limpiarCampos();
        compra = new Transaccion();
        getFacade().setEntity(compra);
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
        compra = new Transaccion();
        limpiarCampos();
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

    public String generarCredito() {

        return null;
    }

    public boolean sumarResultados() {
        boolean res = true;
        try {
            subTotalGravadas5X = compra.getSubTotalGravadas5();
            subTotalGravadas10X = compra.getSubTotalGravadas10();
            //Calculo del IVA 10%
            BigDecimal x10 = BigDecimal.TEN.divide(BigDecimal.valueOf(110), 8, RoundingMode.HALF_UP);
            compra.setNetoSinIva10(subTotalGravadas10X.multiply(x10));
            compra.setTotalIva10(subTotalGravadas10X.subtract(compra.getNetoSinIva10()).setScale(new Integer(ConfiguracionEnum.MONEDA_DECIMALES.getSymbol()), RoundingMode.HALF_UP));
            //Calculo del IVA 5%
            BigDecimal x5 = BigDecimal.TEN.divide(BigDecimal.valueOf(105), 8, RoundingMode.HALF_UP);
            compra.setNetoSinIva5(subTotalGravadas5X.multiply(x5));
            compra.setTotalIva5(subTotalGravadas5X.subtract(compra.getNetoSinIva5()).setScale(new Integer(ConfiguracionEnum.MONEDA_DECIMALES.getSymbol()), RoundingMode.HALF_UP));
            //Suma del IVA Total
            compra.setTotalIva(compra.getTotalIva10().add(compra.getTotalIva5()));
            //Suma del sub total
            subtotalX = compra.getSubTotalExentas().add(subTotalGravadas5X).add(subTotalGravadas10X);


        } catch (Exception e) {
            Logger.getLogger(CompraMotosBean.class.getName()).log(Level.SEVERE, null, e);
        }
        if (descuentoX != null) {
            compra.setDescuento(descuentoX.multiply(BigDecimal.valueOf(0.1)).floatValue());
        } else {
            compra.setDescuento(Float.valueOf(0));
        }
        if (compra.getDescuento().longValue() > 0) {
            totalDescuentoX = subtotalX.multiply(descuentoX);
        } else {
            totalDescuentoX = BigDecimal.ZERO;
        }
        totalX = subtotalX.subtract(totalDescuentoX);
        //Pasar Datos
        compra.setTotalDescuento(totalDescuentoX);
        compra.setSubTotal(subtotalX);
        compra.setTotal(totalX);
        return res;
    }

    public void cargarMotosNuevas() {
        if (compra.getCantidadItems() != null && compra.getCantidadItems() > 0) {
            if (getModificar()) {
                int cantidadExistente = compra.getMotostocksCompra().size();
                int cantidadSolicitada = compra.getCantidadItems() - cantidadExistente;
                if (cantidadSolicitada > 0) {
                    for (int i = 0; i < cantidadSolicitada; i++) {
                        compra.getMotostocksCompra().add(new Motostock());
                    }
                }
            } else {
                cantidad = null;
                try {
                    cantidad = compra.getCantidadItems();
                    ArrayList<Motostock> motosNuevas = new ArrayList<Motostock>();
                    for (int i = 0; i < cantidad; i++) {
                        motosNuevas.add(new Motostock());
                    }
                    compra.setMotostocksCompra(motosNuevas);
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        }
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
    public String getIdFiltro() {
        return idFiltro;
    }

    /**
     * @param id the id to set
     */
    public void setIdFiltro(String idFiltro) {
        this.idFiltro = idFiltro;
    }

    public List<SelectItem> getListaPlan() {
        return listaPlan;
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
    public Integer getCategoriaFiltro() {
        return categoriaFiltro;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCategoriaFiltro(Integer categoriaFiltro) {
        this.categoriaFiltro = categoriaFiltro;
    }

    /**
     * @return the fechaOperacion
     */
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion the fechaOperacion to set
     */
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @return the fechaEntrega
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * @param fechaEntrega the fechaEntrega to set
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * @return the vendedor
     */
    public Integer getVendedorFiltro() {
        return vendedorFiltro;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedorFiltro(Integer vendedorFiltro) {
        this.vendedorFiltro = vendedorFiltro;
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
    public Boolean getAnuladoFiltro() {
        return anuladoFiltro;
    }

    /**
     * @param anulado the anulado to set
     */
    public void setAnuladoFiltro(Boolean anuladoFiltro) {
        this.anuladoFiltro = anuladoFiltro;
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
     * @return the saldado
     */
    public Boolean getSaldadoFiltro() {
        return saldadoFiltro;
    }

    /**
     * @param saldado the saldado to set
     */
    public void setSaldadoFiltro(Boolean saldadoFiltro) {
        this.saldadoFiltro = saldadoFiltro;
    }

    /**
     * @return the comprobante
     */
    public String getComprobanteFiltro() {
        return comprobanteFiltro;
    }

    /**
     * @param comprobante the comprobante to set
     */
    public void setComprobanteFiltro(String comprobanteFiltro) {
        this.comprobanteFiltro = comprobanteFiltro;
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
     * @return the listaProveedor
     */
    public List<SelectItem> getListaProveedor() {
        return listaProveedor;
    }

    /**
     * @param listaProveedor the listaProveedor to set
     */
    public void setListaProveedor(List<SelectItem> listaProveedor) {
        this.listaProveedor = listaProveedor;
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
        if (usarFechaAhora) {
            compra.setFechaOperacion(new Date());
        }
        this.usarFechaAhora = usarFechaAhora;

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
        if (usarFechaEntregaAhora) {
            compra.setFechaEntrega(new Date());
        }
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
     * @return the compra
     */
    public Transaccion getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(Transaccion compra) {
        this.compra = compra;
    }

    /**
     * @return the activoFiltro
     */
    public Boolean getActivoFiltro() {
        return activoFiltro;
    }

    /**
     * @param activoFiltro the activoFiltro to set
     */
    public void setActivoFiltro(Boolean activoFiltro) {
        this.activoFiltro = activoFiltro;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        if (idCategoria.equals(String.valueOf(CategoriaEnum.COMPRA_MCR.getSymbol()))) {
            esCredito = Boolean.TRUE;
        } else {
            esCredito = Boolean.FALSE;
        }
        this.idCategoria = idCategoria;
    }

    public String getGravamen() {
        return gravamen;
    }

    public void setGravamen(String gravamen) {
        this.gravamen = gravamen;
    }

    public Boolean getEsCredito() {
        return esCredito;
    }

    public void setEsCredito(Boolean esCredito) {
        this.esCredito = esCredito;
    }

    public List<Financiacion> getListaCuotas() {
        return listaCuotas;
    }

    public void setListaCuotas(List<Financiacion> listaCuotas) {
        this.listaCuotas = listaCuotas;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }
}

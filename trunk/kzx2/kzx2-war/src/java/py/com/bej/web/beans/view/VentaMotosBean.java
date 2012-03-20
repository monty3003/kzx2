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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Factura;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FacturaFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PagoFacade;
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
    private FinanciacionFacade financiacionFacade;
    @EJB
    private CreditoFacade creditoFacade;
    @EJB
    private PagoFacade pagoFacade;
    @EJB
    private FacturaFacade facturaFacade;
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
    private DateFormat formatFechaHora;
    private NumberFormat formatNumero;
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

    /** Creates a new instance of VentaMotosBean */
    public VentaMotosBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
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
                (idFiltro != null && idFiltro.trim().equals("")) ? Integer.valueOf(idFiltro) : null,
                (categoriaFiltro != null && categoriaFiltro > 0) ? new Categoria(categoriaFiltro) : null,
                (comprobanteFiltro != null && comprobanteFiltro.trim().equals("")) ? new Factura(comprobanteFiltro) : null,
                (compradorFiltro != null && compradorFiltro > 0) ? new Persona(compradorFiltro) : null,
                (anuladoFiltro != null && !anuladoFiltro.equals('X')) ? anuladoFiltro : null,
                (saldadoFiltro != null && !saldadoFiltro.equals('X')) ? saldadoFiltro : null,
                (activoFiltro != null && !activoFiltro.equals('X')) ? activoFiltro : null));
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
            setNav("listaventamotos");
            return todos();
        } else {
            return null;
        }
    }

    @Override
    public boolean validar() {
        boolean res = true;
        if (venta.getCodigo().getId() == -1) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
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
        if (venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCR.getSymbol())) {
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
        venta.setActivo('S');
        venta.setAnulado('N');
        if (venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCR.getSymbol())) {
            venta.setSaldado('N');
        } else {
            venta.setSaldado('S');
        }
        venta.setUsuarioCreacion(LoginBean.getInstance().getUsuario());
        venta.setFechaCreacion(new Date());
        venta.setUsuarioModificacion(LoginBean.getInstance().getUsuario());
        venta.setUltimaModificacion(new Date());
        venta.getFactura().setUltimaModificacion(venta.getUltimaModificacion());
        venta.getFactura().setTransaccion(venta);
        getFacade().guardarVenta(venta);
        motoVendida.setVenta(venta);
        getMotostockFacade().setEntity(motoVendida);
        getMotostockFacade().guardar();
        if (venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCR.getSymbol())) {
            try {
                //Credito
                credito.getGarante().setCategoria(categoriaFacade.find(CategoriaEnum.CLIENTE_PF.getSymbol()));
                credito.setCategoria(motoVendida.getPlan().getCategoria());
                credito.setSistemaCredito(motoVendida.getPlan().getCategoria());
                getCreditoFacade().abrirCredito(venta, credito);
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
            } else {
                if (motoVendida != null) {
                    calcularPlanDeVenta();
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

    public void prepararPlanDePago() {
        if (venta.getCodigo().getId() == CategoriaEnum.VENTA_MCR.getSymbol()) {
            //Venta a Credito
            credito = new Credito();
        } else {
            credito = null;
        }
    }

    public void calcularPlanDeVenta() {
        //Validar datos
        if (venta.getCodigo().getId() == CategoriaEnum.VENTA_MCR.getSymbol()) {
            if (venta.getEntregaInicial() == null) {
                venta.setEntregaInicial(motoVendida.getPlan().getMontoEntregaMinimo());
                venta.setTotalPagado(venta.getEntregaInicial());
            }
            if (venta.getCuotas() == null || venta.getCuotas() < 1) {
                venta.setCuotas(motoVendida.getPlan().getFinanciacionMinima());
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
                if (venta.getCuotas() < motoVendida.getPlan().getFinanciacionMinima()
                        || (venta.getCuotas() > motoVendida.getPlan().getFinanciacionMaxima())) {
                    setErrorMessage("frm:montoCuotaIgual", "Cuota fuera del rango permitido");
                    esParaCalculoValido = false;
                    continue;
                } else {
                    BigDecimal tasaEfectiva = BigDecimal.valueOf(motoVendida.getPlan().getTan() * 12).divide(BigDecimal.valueOf(360), 8, RoundingMode.UP);
                    credito.setTan(motoVendida.getPlan().getTan());
                    credito.setTae(tasaEfectiva.floatValue());
                    credito.setCapital(motoVendida.getPrecioBase().subtract(venta.getEntregaInicial()));
                    BigDecimal netoSinInteres = credito.getCapital().divide(BigDecimal.valueOf(venta.getCuotas()), motoVendida.getPlan().getIndiceRedondeo(), RoundingMode.HALF_DOWN);
                    venta.setMontoCuotaIgual(netoSinInteres.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(credito.getTan()))).setScale(motoVendida.getPlan().getIndiceRedondeo(), RoundingMode.HALF_DOWN));
                    credito.setCreditoTotal(venta.getMontoCuotaIgual().multiply(BigDecimal.valueOf(credito.getAmortizacion())));

                    break;
                }
            } while (esParaCalculoValido);

        } else {
            venta.setTotalPagado(venta.getTotal());
        }
        sumarResultados();
    }

    public void sumarResultados() {
        try {
            BigDecimal precioTotalDeLaVenta = null;
            BigDecimal cien = new BigDecimal("100");
            BigDecimal cientoDiez = new BigDecimal("110");
            BigDecimal cientoDos = new BigDecimal("102");
            BigDecimal ochentaPorCiento = new BigDecimal("0.8");

            precioTotalDeLaVenta = motoVendida.getPrecioContado();
            precioUnitario = precioTotalDeLaVenta.multiply((cien.divide(cientoDos, 10, RoundingMode.HALF_UP)));
            venta.setSubTotalExentas(precioUnitario.multiply(ochentaPorCiento).setScale(0, RoundingMode.HALF_UP));
            venta.getFactura().setNetoSinIva10(precioUnitario.subtract(venta.getSubTotalExentas()));
            venta.setTotalIva10(venta.getFactura().getNetoSinIva10().multiply((BigDecimal.TEN.divide(cien, 1, RoundingMode.HALF_DOWN))).setScale(0, RoundingMode.HALF_UP));
            venta.setSubTotalGravadas10(venta.getFactura().getNetoSinIva10().add(venta.getTotalIva10()));
            venta.setSubTotal(precioTotalDeLaVenta);
            venta.setTotalDescuento(motoVendida.getPlan().getMontoDescuento());
            venta.setTotal(venta.getSubTotal().subtract(venta.getTotalDescuento()));
        } catch (Exception e) {
            Logger.getLogger(VentaMotosBean.class.getName()).log(Level.SEVERE, null, e);
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
}

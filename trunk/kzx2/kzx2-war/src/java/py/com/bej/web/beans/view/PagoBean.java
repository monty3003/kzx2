/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.DetallePagoFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.PagoFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.GeneradorReporte;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class PagoBean extends AbstractPageBean<Pago> {
    
    @EJB
    private DetallePagoFacade detallePagoFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private CreditoFacade creditoFacade;
    @EJB
    private PagoFacade facade;
    private Pago pago;
    private Credito creditoConsolidado;
    private Credito creditoResumen;
    private Integer numeroDocumentoFiltro;
    private Date fechaFiltro;
    private String clienteFiltro;
    private Character activoFiltro;
    private Boolean calcularPorMonto;
    private Boolean habilitarParaExcluir;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo")
    private BigDecimal montoSolicitado;
    private List<Credito> creditos;
    private List<Financiacion> listaFinanciacion;
    private List<Pago> listaPagos;
    private List<DetallePago> listaDetallePagos;
    private List<Persona> listaClientes;
    private Integer creditoSeleccionado;
    private Date fecha;
    private Boolean usarFechaAhora;
    private String moto;
    private String idFiltro;
    private String ventaFiltro;

    /** Creates a new instance of PagoBean */
    public PagoBean() {
        LoginBean.getInstance().setUbicacion("Pagos");
        setNav("cobrarCredito");
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        pago = new Pago();
        this.activoFiltro = 'X';
    }
    
    public PagoFacade getFacade() {
        if (facade == null) {
            facade = new PagoFacade();
        }
        return facade;
    }
    
    public DetallePagoFacade getDetallePagoFacade() {
        if (detallePagoFacade == null) {
            detallePagoFacade = new DetallePagoFacade();
        }
        return detallePagoFacade;
    }
    
    public PersonaFacade getPersonaFacade() {
        if (personaFacade == null) {
            personaFacade = new PersonaFacade();
        }
        return personaFacade;
    }
    
    public CreditoFacade getCreditoFacade() {
        if (creditoFacade == null) {
            creditoFacade = new CreditoFacade();
        }
        return creditoFacade;
    }
    
    @Override
    void limpiarCampos() {
    }
    
    @Override
    public String listar() {
        setNav("pago");
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        LoginBean.getInstance().setUbicacion("Pagos");
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("fecha", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        obtenerListas();
        return getNav();
    }
    
    public String cobrarCredito() {
        LoginBean.getInstance().setUbicacion("Cobrar Credito");
        limpiarCampos();
        setNav("cobrarCredito");
        return getNav();
    }
    
    public String obtenerCliente() {
        if (moto == null || moto.trim().equals("")) {
            listaClientes = getPersonaFacade().findByNombre(pago.getCliente());
        } else {
            Integer nMoto = null;
            nMoto = new Integer(moto);
            listaClientes = Arrays.asList(getPersonaFacade().findByNumeroMoto(nMoto));
        }
        return null;
    }
    
    public String obtenerCreditosPendientes() {
        try {
            creditos = getCreditoFacade().findByEstado(CategoriaEnum.ABIERTO.getSymbol());
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public String resumenDeCreditoAlDia(Integer credito) {
        calcularPorMonto = Boolean.FALSE;
        habilitarParaExcluir = pago.getCredito().getTransaccion().getIdAnterior() != null ? Boolean.TRUE : Boolean.FALSE;
        creditoConsolidado = getCreditoFacade().find(credito);
        pago = getFacade().generarPagoParaEstarAlDia(creditoConsolidado);
        calcularTotalAPagar();
        return "cobrarCredito";
    }
    
    public String seleccionarCliente() {
        //recuperar la seleccion
        pago.setCliente(clienteFiltro);
        return getNav();
    }
    
    public String seleccionarCredito() {
        //recuperar la seleccion
        try {
            pago.setCredito(getCreditoFacade().find(creditoSeleccionado));
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String seleccionarCreditoYObtenerLasListas() {
        //recuperar la seleccion
        try {
            creditoConsolidado = getCreditoFacade().find(creditoSeleccionado);
            obtenerFinanciacion();
            obtenerPagos();
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String consolidar() {
        BigDecimal contador = BigDecimal.ZERO;
        for (Financiacion f : listaFinanciacion) {
            if (f.getPagoAsignado() != null) {
                Pago existe = null;
                existe = getFacade().find(f.getPagoAsignado());
                if (existe != null) {
                    f.setCancelado('S');
                    f.setFechaPago(existe.getFecha());
                    f.setTotalPagado(f.getTotalAPagar());
                    contador = contador.add(f.getTotalPagado());
                    creditoConsolidado.setFechaUltimoPago(existe.getFecha());
                }
            }
        }
        if (contador.compareTo(creditoConsolidado.getCreditoTotal()) >= 0) {
            creditoConsolidado.setEstado(new Categoria(CategoriaEnum.CERRADO.getSymbol()));
        }
        creditoConsolidado.setFinanciacions(listaFinanciacion);
        getCreditoFacade().setEntity(creditoConsolidado);
        getCreditoFacade().guardar();
        return obtenerCreditosPendientes();
    }
    
    public String obtenerCreditosParaElCobro() {
        calcularPorMonto = Boolean.FALSE;
        pago.setDetalle(null);
        try {
            creditos = getCreditoFacade().findByClienteYEstado(pago.getCliente(), CategoriaEnum.ABIERTO.getSymbol());
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String calcularPonerseAlDia() {
        calcularPorMonto = Boolean.FALSE;
        habilitarParaExcluir = pago.getCredito().getTransaccion().getIdAnterior() != null ? Boolean.FALSE : Boolean.TRUE;
        pago = getFacade().generarPagoParaEstarAlDia(pago.getCredito());
        calcularTotalAPagar();
        return null;
    }
    
    public String calcularPorMontoAPagar() {
        calcularPorMonto = Boolean.TRUE;
        habilitarParaExcluir = pago.getCredito().getTransaccion().getIdAnterior() != null ? Boolean.FALSE : Boolean.TRUE;
        pago.setDetalle(null);
        return null;
    }
    
    public String calcularParaCancelar() {
        calcularPorMonto = Boolean.FALSE;
        habilitarParaExcluir = pago.getCredito().getTransaccion().getIdAnterior() != null ? Boolean.FALSE : Boolean.TRUE;
        pago = getFacade().generarPagoParaCancelarCredito(pago.getCredito());
        calcularTotalAPagar();
        return null;
    }
    
    public String calcularTeniendoMontoAPagar() {
        try {
            pago = getFacade().generarPagoPorMontoSolicitado(pago.getCredito(), montoSolicitado);
            calcularTotalAPagar();
        } catch (OperationNotSupportedException n) {
            setErrorMessage("frm:montoAPagar", n.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void calcularTotalAPagar() {
        pago.setTotalPagado(BigDecimal.ZERO);
        if (pago.getDetalle() != null && !pago.getDetalle().isEmpty()) {
            for (DetallePago dp : pago.getDetalle()) {
                if (dp.getSeleccion()) {
                    pago.setTotalPagado(pago.getTotalPagado().add(dp.getImporte()));
                }
            }
        }
        obtenerFinanciacion();
    }
    
    public Pago cobrar() {
        montoSolicitado = pago.getTotalPagado();
        //Realizar el pago
        Pago pagoRealizado = getFacade().realizarPago(pago.getCredito(), LoginBean.getInstance().getUsuario(), montoSolicitado);
        //Actualizar Cabecera
        creditoConsolidado = getCreditoFacade().consolidarCreditoPorPago(pagoRealizado.getCredito().getId());
        pagoRealizado.setSaldoActualString(Conversor.numberToStringPattern(creditoConsolidado.getSaldoActual()));
        return pagoRealizado;
    }
    
    private void obtenerFinanciacion() {
        listaFinanciacion = new FinanciacionFacade().findByCredito(creditoSeleccionado);
    }
    
    private void obtenerPagos() {
        listaPagos = getFacade().findPagoByCredito(creditoSeleccionado);
    }
    
    public String confirmarPagoYGenerarRecibo() {
        pago = cobrar();
        //Generar el recibo
        return ImprimirReciboBean.getInstance().imprimir(pago);
    }
    
    public String generarEstadoCuenta() {
        return ImprimirReciboBean.getInstance().imprimirEstadoCuenta(creditoResumen, listaFinanciacion);
    }
    
    public String calcularCuotasCobradas() {
        Calendar c = GregorianCalendar.getInstance();
        Calendar fechaDesde = null;
        Calendar fechaHasta = null;
        c.setTime(fecha);
        fechaDesde = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 00, 00);
        fechaHasta = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59);
        listaDetallePagos = getDetallePagoFacade().findByFecha(fechaDesde.getTime(), fechaHasta.getTime());
        return null;
    }
    
    public void imprimirPagos() {
        new GeneradorReporte().generarListaPagos(listaDetallePagos, fecha);
    }
    
    @Override
    List<Pago> filtrar() {
        if (idFiltro != null && !idFiltro.trim().equals("")) {
            pago.setId(new Integer(idFiltro));
        }
        if (ventaFiltro != null && !ventaFiltro.trim().equals("")) {
            pago.getCredito().getTransaccion().setId(new Integer(ventaFiltro));
        }
        if (activoFiltro != null && activoFiltro != 'X') {
            pago.setActivo(activoFiltro);
        }
        if (ventaFiltro != null && !ventaFiltro.trim().equals("")) {
            pago.getCredito().getTransaccion().setId(new Integer(ventaFiltro));
        }
        facade.setEntity(getPago() == null ? new Pago() : getPago());
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }
    
    @Override
    void obtenerListas() {
        return;
    }
    
    @Override
    public String buscar() {
        getFacade().setEntity(pago);
        getFacade().setContador(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return null;
    }
    
    @Override
    public String nuevo() {
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        pago = new Pago();
        obtenerListas();
        return null;
    }
    
    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer idFiltr = new Integer(request.getParameter("radio"));
        if (idFiltr != null) {
            try {
                pago = facade.find(idFiltr);
                setActivo(pago.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
        } else {
            setErrorMessage(null, facade.sel);
        }
        return null;
    }
    
    @Override
    boolean validar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String guardar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String cancelar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String anterior() {
        setLista(getFacade().anterior());
        return null;
    }
    
    @Override
    public String siguiente() {
        setLista(getFacade().siguiente());
        return null;
    }
    
    @Override
    public String todos() {
        limpiarCampos();
        pago = new Pago();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("id", false));
        filtrar();
        return null;
    }
    
    public Character getActivoFiltro() {
        return activoFiltro;
    }
    
    public void setActivoFiltro(Character activoFiltro) {
        this.activoFiltro = activoFiltro;
    }
    
    public String getClienteFiltro() {
        return clienteFiltro;
    }
    
    public void setClienteFiltro(String clienteFiltro) {
        this.clienteFiltro = clienteFiltro;
    }
    
    public Date getFechaFiltro() {
        return fechaFiltro;
    }
    
    public void setFechaFiltro(Date fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }
    
    public Integer getNumeroDocumentoFiltro() {
        return numeroDocumentoFiltro;
    }
    
    public void setNumeroDocumentoFiltro(Integer numeroDocumentoFiltro) {
        this.numeroDocumentoFiltro = numeroDocumentoFiltro;
    }
    
    public Pago getPago() {
        return pago;
    }
    
    public void setPago(Pago pago) {
        this.pago = pago;
    }
    
    public Boolean getCalcularPorMonto() {
        return calcularPorMonto;
    }
    
    public void setCalcularPorMonto(Boolean calcularPorMonto) {
        this.calcularPorMonto = calcularPorMonto;
    }
    
    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }
    
    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }
    
    public List<Credito> getCreditos() {
        return creditos;
    }
    
    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }
    
    public Integer getCreditoSeleccionado() {
        return creditoSeleccionado;
    }
    
    public void setCreditoSeleccionado(Integer creditoSeleccionado) {
        this.creditoSeleccionado = creditoSeleccionado;
    }
    
    public List<Financiacion> getListaFinanciacion() {
        return listaFinanciacion;
    }
    
    public List<Persona> getListaClientes() {
        return listaClientes;
    }
    
    public Credito getCreditoConsolidado() {
        return creditoConsolidado;
    }
    
    public void setCreditoConsolidado(Credito creditoConsolidado) {
        this.creditoConsolidado = creditoConsolidado;
    }
    
    public List<Pago> getListaPagos() {
        return listaPagos;
    }
    
    public void setListaPagos(List<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }
    
    public Credito getCreditoResumen() {
        return creditoResumen;
    }
    
    public void setCreditoResumen(Credito creditoResumen) {
        if (this.creditoResumen == null) {
            calcularPorMonto = Boolean.FALSE;
            creditoSeleccionado = creditoResumen.getId();
            obtenerFinanciacion();
            pago = getFacade().generarPagoParaEstarAlDia(creditoResumen);
            calcularTotalAPagar();
        }
        this.creditoResumen = creditoResumen;
    }
    
    public Boolean getHabilitarParaExcluir() {
        return habilitarParaExcluir;
    }
    
    public void setHabilitarParaExcluir(Boolean habilitarParaExcluir) {
        this.habilitarParaExcluir = habilitarParaExcluir;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Boolean getUsarFechaAhora() {
        return usarFechaAhora;
    }
    
    public void setUsarFechaAhora(Boolean usarFechaAhora) {
        if (usarFechaAhora) {
            fecha = new Date();
        }
        this.usarFechaAhora = usarFechaAhora;
    }
    
    public List<DetallePago> getListaDetallePagos() {
        return listaDetallePagos;
    }
    
    public void setListaDetallePagos(List<DetallePago> listaDetallePagos) {
        this.listaDetallePagos = listaDetallePagos;
    }
    
    public String getMoto() {
        return moto;
    }
    
    public void setMoto(String moto) {
        this.moto = moto;
    }
    
    public String getIdFiltro() {
        return idFiltro;
    }
    
    public void setIdFiltro(String idFiltro) {
        this.idFiltro = idFiltro;
    }
    
    public String getVentaFiltro() {
        return ventaFiltro;
    }
    
    public void setVentaFiltro(String ventaFiltro) {
        this.ventaFiltro = ventaFiltro;
    }
}

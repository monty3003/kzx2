/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.PagoFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.servlets.security.LoginBean;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class PagoBean extends AbstractPageBean<Pago> {

    @EJB
    private CreditoFacade creditoFacade;
    @EJB
    private PagoFacade facade;
    private Pago pago;
    private Integer numeroDocumentoFiltro;
    private Date fechaFiltro;
    private String clienteFiltro;
    private Boolean activoFiltro;
    private Boolean calcularPorMonto;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo")
    private BigDecimal montoSolicitado;
    private List<Credito> creditos;
    private Integer creditoSeleccionado;

    /** Creates a new instance of PagoBean */
    public PagoBean() {
    }

    public PagoFacade getFacade() {
        if (facade == null) {
            facade = new PagoFacade();
        }
        return facade;
    }

    public CreditoFacade getCreditoFacade() {
        if (creditoFacade == null) {
            creditoFacade = new CreditoFacade();
        }
        return creditoFacade;
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
                this.pago = new Pago();
        this.numeroDocumentoFiltro = null;
        this.fechaFiltro = null;
        this.clienteFiltro = null;
        this.activoFiltro = null;
        this.calcularPorMonto = null;
        this.montoSolicitado = null;
        this.creditos = null;
        this.creditoSeleccionado = null;
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

    public String seleccionarCredito() {
        //recuperar la seleccion
        try {
            pago.setCredito(getCreditoFacade().find(creditoSeleccionado));
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getNav();
    }

    public String obtenerCreditosParaElCobro() {
        try {
            creditos = getCreditoFacade().findByCliente(pago.getCliente());
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getNav();
    }

    public String calcularPonerseAlDia() {
        pago = getFacade().generarPagoParaEstarAlDia(pago.getCredito().getTransaccion().getComprador().getId());
        calcularTotalAPagar();
        return getNav();
    }

    public String calcularPorMontoAPagar() {
        calcularPorMonto = Boolean.TRUE;
        return getNav();
    }

    public String calcularParaCancelar() {
        pago = getFacade().generarPagoParaCancelarCredito(pago.getCredito().getTransaccion().getComprador().getId());
        calcularTotalAPagar();
        return getNav();
    }

    public void calcularTeniendoMontoAPagar() {
        try {
            pago = getFacade().generarPagoPorMontoSolicitado(pago.getCredito().getTransaccion().getComprador().getId(), montoSolicitado);
            calcularTotalAPagar();
        } catch (OperationNotSupportedException n) {
            setErrorMessage("frm:montoAPagar", n.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calcularTotalAPagar() {
        pago.setTotalPagado(BigDecimal.ZERO);
        for (DetallePago dp : pago.getDetalle()) {
            if (dp.getSeleccion()) {
                pago.setTotalPagado(pago.getTotalPagado().add(dp.getImporte()));
            }
        }
    }

    public void confirmarPagoYGenerarRecibo() {
        //Generar el recibo
    }

    @Override
    List<Pago> filtrar() {
                pago = new Pago(null, fechaFiltro, null, numeroDocumentoFiltro != null ? String.valueOf(numeroDocumentoFiltro) : null, null, null, null);
        if (activoFiltro != null) {
            pago.setActivo(activoFiltro ? 'S' : 'N');
        }
        if (clienteFiltro != null && !clienteFiltro.trim().equals("")) {
            pago.setCliente(clienteFiltro);
        }
        getFacade().setEntity(pago);
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
        pago = new Pago(null, fechaFiltro, null, numeroDocumentoFiltro != null ? String.valueOf(numeroDocumentoFiltro) : null, null, null, null);
        if (activoFiltro != null) {
            pago.setActivo(activoFiltro ? 'S' : 'N');
        }
        if (clienteFiltro != null && !clienteFiltro.trim().equals("")) {
            pago.setCliente(clienteFiltro);
        }
        getFacade().setEntity(pago);
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
        pago = new Pago();
        obtenerListas();
        return "pago";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer idFiltro = new Integer(request.getParameter("radio"));
        if (idFiltro != null) {
            try {
                pago = facade.find(idFiltro);
                setActivo(pago.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(PagoBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            return "pago";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
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
        setModificar(Boolean.FALSE);
        setAgregar(Boolean.FALSE);
        obtenerListas();
        pago = new Pago();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("fecha", false));
        this.filtrar();
        return getNav();
    }

    public Boolean getActivoFiltro() {
        return activoFiltro;
    }

    public void setActivoFiltro(Boolean activoFiltro) {
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
}

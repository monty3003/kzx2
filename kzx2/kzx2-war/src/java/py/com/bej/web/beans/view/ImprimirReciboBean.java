/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.PagoFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class ImprimirReciboBean implements Serializable {

    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private PagoFacade pagoFacade;
    private String monedaPattern = ConfiguracionEnum.MONEDA_PATTERN.getSymbol();
    private String fechaHoraPattern = ConfiguracionEnum.DATETIME_PATTERN.getSymbol();
    private String fechaCortaPattern = ConfiguracionEnum.DATE_PATTERN_CORTO.getSymbol();
    private Pago pago;
    private List<Pago> listaPagos;
    private Credito credito;
    private BigDecimal ponerseAlDia;
    private List<Financiacion> listaFinanciacion;
    private String fechaHoy;
    private String empresa;
    private String membrete;
    private String cliente;
    private String ruc;
    private Persona propietario;
    private static final long serialVersionUID = 143464353L;

    /** Creates a new instance of ImprimirReciboBean */
    public ImprimirReciboBean() {
        propietario = new PersonaFacade().findByDocumento(ConfiguracionEnum.PROPIETARIO.getSymbol());
    }

    public PagoFacade getPagoFacade() {
        if (pagoFacade == null) {
            pagoFacade = new PagoFacade();
        }
        return pagoFacade;
    }

    private void obtenerDatosParaDocumento(Persona c) {
        empresa = "<h3>BEJ MOTOS</h3><br/>de " + propietario.getNombre();
        ruc = propietario.getRuc();
        fechaHoy = Conversor.deDateToString(new Date(), ConfiguracionEnum.DATETIME_PATTERN.getSymbol());
        cliente = c.getNombre();
        membrete = "<b>Casa Central:</b> 14 de Mayo c/ Av. Cap. Caballero. Telefax: 0786 232126<br/><b>Sucursal:</b> Av. Irala Nº 9143 Barrio Loma Clavel. Telef: 0786 231903<br/>Pilar - Paraguay";
    }

    public String imprimir(Pago pago) {
        obtenerDatosParaDocumento(pago.getCredito().getTransaccion().getComprador());
        this.pago = pago;
        return "imprimirRecibo";
    }

    public String imprimirEstadoCuenta(Credito credito, List<Financiacion> listaFinanciacion) {
        obtenerDatosParaDocumento(credito.getTransaccion().getComprador());
        this.credito = credito;
        this.listaFinanciacion = listaFinanciacion;
        listaPagos = getPagoFacade().findPagoByCredito(credito.getId());
        return "imprimirEstadoCuenta";
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getFechaHoraPattern() {
        return fechaHoraPattern;
    }

    public String getMonedaPattern() {
        return monedaPattern;
    }

    public static ImprimirReciboBean getInstance() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "imprimirReciboBean") == null) {
            fc.getApplication().getELResolver().setValue(fc.getELContext(), new ImprimirReciboBean(), null, "imprimirReciboBean");
        }
        return (ImprimirReciboBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "imprimirReciboBean");
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public List<Financiacion> getListaFinanciacion() {
        return listaFinanciacion;
    }

    public void setListaFinanciacion(List<Financiacion> listaFinanciacion) {
        this.listaFinanciacion = listaFinanciacion;
    }

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public String getFechaCortaPattern() {
        return fechaCortaPattern;
    }

    public String getFechaHoy() {
        return fechaHoy;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getMembrete() {
        return membrete;
    }

    public String getRuc() {
        return ruc;
    }

    public BigDecimal getPonerseAlDia() {
        Pago p = getPagoFacade().generarPagoParaEstarAlDia(credito);
        ponerseAlDia = BigDecimal.ZERO;
        if (p.getDetalle() != null && !p.getDetalle().isEmpty()) {
            for (DetallePago dp : p.getDetalle()) {
                ponerseAlDia = ponerseAlDia.add(dp.getImporte());
            }
        }
        return ponerseAlDia;
    }
}

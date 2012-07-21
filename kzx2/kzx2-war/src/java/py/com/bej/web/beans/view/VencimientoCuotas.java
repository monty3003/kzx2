/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.PagoFacade;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;
import py.com.bej.orm.utils.GrillaCreditoCabeceraWrapper;
import py.com.bej.orm.utils.GrillaCreditoCuotasWrapper;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class VencimientoCuotas implements Serializable {

    @EJB
    private FinanciacionFacade financiacionFacade;
    private List<Financiacion> lista;
    private String numbrePattern = ConfiguracionEnum.MONEDA_PATTERN.getSymbol();
    private String datePattern = ConfiguracionEnum.DATE_PATTERN_CORTO.getSymbol();
    private Date fechaDesde;
    private Date fechaHasta;
    private Boolean usarFechaDesdeAhora = false;
    private Boolean usarFechaHastaAhora = false;
    private List<Credito> listaCredito;
    private List<GrillaCreditoCabeceraWrapper> listaDeCreditosCabecera;
    private List<GrillaCreditoCuotasWrapper> listaDeCreditosDetalle;
    private static final long serialVersionUID = 14566345L;

    /** Creates a new instance of VencimientoCuotas */
    public VencimientoCuotas() {
        financiacionFacade = new FinanciacionFacade();
    }

    public String calcularVencimiento() {
        Calendar vencimiento = new GregorianCalendar();
        financiacionFacade = new FinanciacionFacade();
        if (fechaHasta != null) {
            try {
                if (fechaDesde != null) {
                    if (fechaHasta.after(fechaDesde)) {
                        lista = financiacionFacade.buscarCuotasQueVencen(fechaDesde, fechaHasta, -1);
                    } else if (fechaHasta.equals(fechaDesde)) {
                        lista = financiacionFacade.buscarCuotasQueVencen(fechaDesde, fechaHasta, -1);
                    } else {
                        setErrorMessage("frm:fechaHasta", "Rango de fechas no válido");
                        return null;
                    }
                } else {
                    lista = financiacionFacade.buscarCuotasQueVencen(fechaDesde, fechaHasta, -1);
                }
            } catch (Exception ex) {
                Logger.getLogger(VencimientoCuotas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            setErrorMessage("frm:fechaHasta", "Este valor no puede ser nulo.");
            return null;
        }
        if (lista != null && !lista.isEmpty()) {
            int meses = 0;
            for (Financiacion f : lista) {
                meses = PagoFacade.calcularEstadoCuota(f);
                vencimiento.setTime(f.getFechaVencimiento());
                //Ponerle color al registro
                if (meses > 0) {
                    f.setCondicionesPagare("rojo");
                } else if (meses == 0) {
                    f.setCondicionesPagare("amarillo");
                } else {
                    f.setCondicionesPagare("verde");
                }

                if (!f.getCondicionesPagare().equals("verde")) {
                    //Calcular el interes
                    Float interes = f.getCredito().getInteresMoratorio() * meses;
                    f.setInteresMora(f.getTotalAPagar().multiply(BigDecimal.valueOf(interes)));

                }
            }
        }
        return null;
    }

    public String generarGrillaDeCreditos() {
        Date elPrimero = null;
        Date elUltimo = null;
        Calendar primeraFecha = GregorianCalendar.getInstance();
        Calendar ultimaFecha = GregorianCalendar.getInstance();
        Calendar aux;
        List<GrillaCreditoCuotasWrapper> listaCuotas;
        listaDeCreditosCabecera = new ArrayList<GrillaCreditoCabeceraWrapper>();
        listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(-1, -1, "Cliente", "25px"));
        listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(-1, -1, "Importe Cuota", "15px"));
        CreditoFacade crf = new CreditoFacade();
        financiacionFacade = new FinanciacionFacade();
        elPrimero = financiacionFacade.buscarLaCuotaPendientePorFechaVencimiento(Boolean.TRUE).getFechaVencimiento();
        elUltimo = financiacionFacade.buscarLaCuotaPendientePorFechaVencimiento(Boolean.FALSE).getFechaVencimiento();
        primeraFecha.setTime(elPrimero);
        ultimaFecha.setTime(elUltimo);
        listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(primeraFecha.get(Calendar.MONTH), primeraFecha.get(Calendar.YEAR), Conversor.deDateToString(elPrimero, "MM/yy"), "10px"));
        for (int anho = primeraFecha.get(Calendar.YEAR); anho <= ultimaFecha.get(Calendar.YEAR); anho++) {
            for (int mes = primeraFecha.get(Calendar.MONTH); mes <= ultimaFecha.get(Calendar.MONTH); mes++) {
                aux = new GregorianCalendar(mes, mes, anho);
                listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(mes, anho, Conversor.deDateToString(aux.getTime(), "MM/yy"), "10px"));
            }
        }
        try {
            listaCredito = crf.findByEstado(CategoriaEnum.ABIERTO.getSymbol());
        } catch (Exception ex) {
            Logger.getLogger(VencimientoCuotas.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listaCredito != null && !listaCredito.isEmpty()) {
            for (Credito c : listaCredito) {
                listaCuotas = new ArrayList<GrillaCreditoCuotasWrapper>();
                if (c.getFinanciacions() != null && !c.getFinanciacions().isEmpty()) {
                    for (Financiacion f : c.getFinanciacions()) {
                        primeraFecha.setTime(f.getFechaVencimiento());
                        for (GrillaCreditoCabeceraWrapper cabecera : listaDeCreditosCabecera) {
                            if (cabecera.getAnho() < -1) {
                                continue;
                            }
                            if (primeraFecha.get(Calendar.YEAR) == cabecera.getAnho() && primeraFecha.get(Calendar.MONTH) == cabecera.getMes()) {
                                String etiqueta = null;
                                String colorEstado = null;
                                Boolean esPagoParcial = false;
                                if (f.getCancelado().equals('S')) {
                                    etiqueta = Conversor.deDateToString(f.getFechaPago(), "dd") + "(" + Conversor.numberToStringPattern(f.getTotalPagado()) + ")";
                                    colorEstado = "azul";
                                    esPagoParcial = false;
                                } else if (f.getFechaPago() != null && f.getTotalPagado() != null) {
                                    etiqueta = Conversor.deDateToString(f.getFechaPago(), "dd") + "(" + Conversor.numberToStringPattern(f.getTotalPagado()) + ")";
                                    colorEstado = "amarillo";
                                    esPagoParcial = true;
                                } else {
                                    etiqueta = "(" + Conversor.numberToStringPattern(f.getTotalAPagar()) + ")";
                                    colorEstado = "verde";
                                    esPagoParcial = false;
                                }
                            } else {
                                listaCuotas.add(new GrillaCreditoCuotasWrapper());
                            }
                        }
                    }
                }
            }
        }
        return "grillaCreditos";

    }

    public List<Financiacion> getLista() {
        return lista;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public String getNumbrePattern() {
        return numbrePattern;
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
        this.usarFechaDesdeAhora = usarFechaDesdeAhora;
        this.fechaDesde = new Date();
    }

    public Boolean getUsarFechaHastaAhora() {
        return usarFechaHastaAhora;
    }

    public void setUsarFechaHastaAhora(Boolean usarFechaHastaAhora) {
        this.usarFechaHastaAhora = usarFechaHastaAhora;
        this.fechaHasta = new Date();
    }

    protected void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    public List<GrillaCreditoCabeceraWrapper> getListaDeCreditosCabecera() {
        return listaDeCreditosCabecera;
    }

    public void setListaDeCreditosCabecera(List<GrillaCreditoCabeceraWrapper> listaDeCreditosCabecera) {
        this.listaDeCreditosCabecera = listaDeCreditosCabecera;
    }

    public List<GrillaCreditoCuotasWrapper> getListaDeCreditosDetalle() {
        return listaDeCreditosDetalle;
    }

    public void setListaDeCreditosDetalle(List<GrillaCreditoCuotasWrapper> listaDeCreditosDetalle) {
        this.listaDeCreditosDetalle = listaDeCreditosDetalle;
    }
}

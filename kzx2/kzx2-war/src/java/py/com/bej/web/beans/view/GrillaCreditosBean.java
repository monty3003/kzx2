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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pago;
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
public class GrillaCreditosBean implements Serializable {

    @EJB
    private PagoFacade pagoFacade;
    @EJB
    private FinanciacionFacade financiacionFacade;
    private List<Credito> listaCredito;
    private List<GrillaCreditoCabeceraWrapper> listaDeCreditosCabecera;
    private String numberPattern = ConfiguracionEnum.MONEDA_PATTERN.getSymbol();
    private String fechaCortaPattern = ConfiguracionEnum.DATE_PATTERN_CORTO.getSymbol();
    private Integer periodo;
    private static final long serialVersionUID = 14566345L;

    public GrillaCreditosBean() {
    }

    public PagoFacade getPagoFacade() {
        if (pagoFacade == null) {
            pagoFacade = new PagoFacade();
        }
        return pagoFacade;
    }

    public FinanciacionFacade getFinanciacionFacade() {
        if (financiacionFacade == null) {
            financiacionFacade = new FinanciacionFacade();
        }
        return financiacionFacade;
    }

    public String verGrillaDeCreditos() {
        Date elPrimero = null;
        Date elUltimo = null;
        BigDecimal interesMoratorio = null;
        BigDecimal totalAPagar = null;
        Pago p;
        int meses;
        Calendar primeraFecha = GregorianCalendar.getInstance();
        Calendar ultimaFecha = GregorianCalendar.getInstance();
        Calendar ahora = GregorianCalendar.getInstance();
        Calendar aux;
        List<GrillaCreditoCuotasWrapper> listaCuotas;
        List<Financiacion> listaFinanciacion;
        listaDeCreditosCabecera = new ArrayList<GrillaCreditoCabeceraWrapper>();
        listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(-1, -1, "Cliente Titular del Crédito", "200px"));
        listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(-1, -1, "Monto", "4px"));
        CreditoFacade crf = new CreditoFacade();
        elPrimero = financiacionFacade.buscarLaCuotaPendientePorFechaVencimiento(Boolean.TRUE).getFechaVencimiento();
        elUltimo = financiacionFacade.buscarLaCuotaPendientePorFechaVencimiento(Boolean.FALSE).getFechaVencimiento();
        primeraFecha.setTime(elPrimero);
        ultimaFecha.setTime(elUltimo);
        int mesesParaHoy = 0;
        boolean sumarMes = true;
        // listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(primeraFecha.get(Calendar.MONTH), primeraFecha.get(Calendar.YEAR), Conversor.deDateToString(elPrimero, "MM/yy")));
        for (int anho = primeraFecha.get(Calendar.YEAR); anho <= ultimaFecha.get(Calendar.YEAR); anho++) {
            for (int mes = primeraFecha.get(Calendar.MONTH); mes <= 11; mes++) {
                aux = new GregorianCalendar(anho, mes, 1);
                listaDeCreditosCabecera.add(new GrillaCreditoCabeceraWrapper(mes, anho, Conversor.deDateToString(aux.getTime(), "MMM/yyyy"), "2px"));
                if (sumarMes) {
                    if (ahora.get(Calendar.YEAR) == anho && ahora.get(Calendar.MONTH) == mes) {
                        mesesParaHoy++;
                        sumarMes = false;
                        Logger.getLogger(GrillaCreditosBean.class.getName()).log(Level.INFO, "LA grilla tiene {0} meses para hoy", mesesParaHoy);
                    } else {
                        mesesParaHoy++;
                    }
                }
            }
        }
        Logger.getLogger(GrillaCreditosBean.class.getName()).log(Level.INFO, "La cabecera tiene {0} columnas", listaDeCreditosCabecera.size());
        try {
//            listaCredito = crf.findByEstadoYAnho(CategoriaEnum.ABIERTO.getSymbol(), 2010);
            listaCredito = crf.obtenerCreditosAbiertosPorPeriodoDeCuotas(periodo);
        } catch (Exception ex) {
            Logger.getLogger(VencimientoCuotas.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listaCredito != null && !listaCredito.isEmpty()) {
            for (Credito c : listaCredito) {
                listaCuotas = new ArrayList<GrillaCreditoCuotasWrapper>();
                listaFinanciacion = financiacionFacade.findByCredito(c.getId());
                if (listaFinanciacion != null && !listaFinanciacion.isEmpty()) {
                    int auxMes = 0;
                    for (GrillaCreditoCabeceraWrapper cabecera : listaDeCreditosCabecera) {
                        if (cabecera.getAnho() == -1) {
                            continue;
                        }
                        auxMes++;
                        GrillaCreditoCuotasWrapper cuota = new GrillaCreditoCuotasWrapper();
                        cuota.setColorEstado("#fff");
                        if (auxMes == mesesParaHoy) {
                            cuota.setDeHoy(true);
                        }
                        for (Financiacion f : listaFinanciacion) {
                            interesMoratorio = null;
                            totalAPagar = null;
                            primeraFecha.setTime(f.getFechaVencimiento());
                            if (f.getFechaPago() != null) {
                                ultimaFecha.setTime(f.getFechaPago());
                            }
                            if (primeraFecha.get(Calendar.YEAR) == cabecera.getAnho() && primeraFecha.get(Calendar.MONTH) == cabecera.getMes()) {
                                String etiqueta = null;
                                String colorEstado = null;
                                Boolean esPagoParcial = false;
                                Boolean deHoy = false;
                                etiqueta = Conversor.deDateToString(f.getFechaPago(), "dd/MMM");
                                //Marcar la celda si es de hoy
                                if (primeraFecha.get(Calendar.YEAR) == ahora.get(Calendar.YEAR)
                                        && primeraFecha.get(Calendar.MONTH) == ahora.get(Calendar.MONTH)) {
                                    deHoy = true;
                                }
                                if (f.getCancelado().equals('S')) {
                                    colorEstado = "#ccffff";
                                    esPagoParcial = false;
                                } else {
                                    if (f.getFechaPago() != null && f.getTotalPagado() != null) {
                                        colorEstado = "#FFFFCC";
                                        esPagoParcial = true;
                                    } else {
                                        totalAPagar = f.getTotalAPagar();
                                        if (deHoy) {
                                            etiqueta = "El " + Conversor.deDateToString(f.getFechaVencimiento(), "dd");
                                        }
                                        esPagoParcial = false;
                                        meses = PagoFacade.calcularEstadoCuota(f);
                                        if (meses <= 0) {
                                            if (f.getNumeroCuota() <= 6) {
                                                colorEstado = "#99ff99";
                                            } else if (f.getNumeroCuota() <= 12) {
                                                colorEstado = "#006633";
                                            } else if (f.getNumeroCuota() <= 25) {
                                                colorEstado = "#33ff66";
                                            }
                                            esPagoParcial = false;
                                        } else {
                                            if (f.getNumeroCuota() <= 12) {
                                                if (meses > 2) {
                                                    colorEstado = "#cc99ff";
                                                }
                                            } else {
                                                if (meses <= 2) {
                                                    colorEstado = "#ffcc00";
                                                } else if (meses == 3) {
                                                    colorEstado = "#ffcc66";
                                                } else if (meses >= 4) {
                                                    colorEstado = "#ff3333";
                                                }
                                            }
                                            try {
                                                p = getPagoFacade().generarElDetalleDelPago(f);
                                                if (p != null) {
                                                    for (DetallePago dp : p.getDetalle()) {
                                                        if (dp.getCodigo().getId().equals(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol())) {
                                                            interesMoratorio = dp.getImporte();
                                                            totalAPagar = f.getTotalAPagar().add(interesMoratorio);
                                                            etiqueta = Conversor.numberToStringPattern(totalAPagar);
                                                            break;
                                                        }

                                                    }
                                                }
                                            } catch (Exception ex) {
                                                Logger.getLogger(GrillaCreditosBean.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }
                                }
                                cuota = new GrillaCreditoCuotasWrapper(primeraFecha.get(Calendar.MONTH),
                                        primeraFecha.get(Calendar.YEAR), etiqueta,
                                        f.getTotalAPagar().subtract(f.getTotalPagado() == null ? BigDecimal.ZERO : f.getTotalPagado()),
                                        esPagoParcial, colorEstado, deHoy, interesMoratorio, totalAPagar,
                                        f.getCredito().getId(),
                                        f.getFechaVencimiento());
                            }
                        }
                        listaCuotas.add(cuota);
                    }
                }
                Logger.getLogger(GrillaCreditosBean.class.getName()).log(Level.FINE, "El Credito Nro {0} tiene {1} cuotas", new Object[]{c.getId(), listaCuotas.size()});
                c.setListaGrilla(listaCuotas);
            }
        }
        return null;
    }

    public List<GrillaCreditoCabeceraWrapper> getListaDeCreditosCabecera() {
        return listaDeCreditosCabecera;
    }

    public void setListaDeCreditosCabecera(List<GrillaCreditoCabeceraWrapper> listaDeCreditosCabecera) {
        this.listaDeCreditosCabecera = listaDeCreditosCabecera;
    }

    public List<Credito> getListaCredito() {
        return listaCredito;
    }

    public void setListaCredito(List<Credito> listaCredito) {
        this.listaCredito = listaCredito;
    }

    public String getFechaCortaPattern() {
        return fechaCortaPattern;
    }

    public String getNumberPattern() {
        return numberPattern;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
}

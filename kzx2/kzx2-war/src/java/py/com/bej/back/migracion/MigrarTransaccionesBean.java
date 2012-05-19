/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.back.migracion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.com.bej.base.prod.entity.Vmcompras;
import py.com.bej.base.prod.entity.Vmmotostock;
import py.com.bej.base.prod.entity.Vmpagomotos;
import py.com.bej.base.prod.entity.Vmplanmoto;
import py.com.bej.base.prod.entity.Vmresfuerzos;
import py.com.bej.base.prod.entity.Vmventamotos;
import py.com.bej.base.prod.session.ComprasProduccionFacade;
import py.com.bej.base.prod.session.PagoMotosProduccionFacade;
import py.com.bej.base.prod.session.PlanMotosProduccionFacade;
import py.com.bej.base.prod.session.VentaMotosProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Factura;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Pago;
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
import py.com.bej.web.servlets.security.LoginBean;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class MigrarTransaccionesBean implements Serializable {

    @EJB
    private FinanciacionFacade financiacionFacade;
    @EJB
    private PagoFacade pagoFacade;
    @EJB
    private PagoMotosProduccionFacade pagoMotosProduccionFacade;
    @EJB
    private PlanMotosProduccionFacade planMotosProduccionFacade;
    @EJB
    private VentaMotosProduccionFacade ventaMotosProduccionFacade;
    @EJB
    private ComprasProduccionFacade comprasProduccionFacade;
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
    private static final long serialVersionUID = 1L;
    public final static Logger LOGGER = Logger.getLogger(MigrarTransaccionesBean.class.getName());
    private Map<Short, BigDecimal> map;

    /** Creates a new instance of MigrarTransaccionesBean */
    public MigrarTransaccionesBean() {
    }

    /**
     * @return the facade
     */
    public PagoMotosProduccionFacade getPagoMotosProduccionFacade() {
        if (this.pagoMotosProduccionFacade == null) {
            this.pagoMotosProduccionFacade = new PagoMotosProduccionFacade();
        }
        return pagoMotosProduccionFacade;
    }

    /**
     * @return the facade
     */
    public PlanMotosProduccionFacade getPlanMotosProduccionFacade() {
        if (this.planMotosProduccionFacade == null) {
            this.planMotosProduccionFacade = new PlanMotosProduccionFacade();
        }
        return planMotosProduccionFacade;
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
    public FinanciacionFacade getFinanciacionFacade() {
        if (this.financiacionFacade == null) {
            this.financiacionFacade = new FinanciacionFacade();
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

    public String importarCompras() {

        return "importarCompra";
    }

    public String migrarCompras() {
        int totalMigrado = 0;
        //variables
        Transaccion t;
        Integer id;
        Categoria codigo;
        Factura factura;
        String numero;
        Categoria categoriaFactura;
        Date fechaOperacionProduccion;
        Date fechaEntregaProduccion;
        Persona vendedor;
        Persona compradorProduccion = getPersonaFacade().findByDocumento(ConfiguracionEnum.PROPIETARIO.getSymbol());
        Character anulado;
        BigDecimal subTotalExentasProduccion = BigDecimal.ZERO;
        BigDecimal subTotalGravadas10Produccion = BigDecimal.ZERO;
        BigDecimal subTotalGravadas5Produccion = BigDecimal.ZERO;
        BigDecimal subTotal;
        BigDecimal totalIva5Produccion = BigDecimal.ZERO;
        BigDecimal totalIva10Produccion = BigDecimal.ZERO;
        BigDecimal totalIvaProduccion;
        Float descuento = Float.valueOf("0.0");
        BigDecimal totalProduccion;
        BigDecimal totalDescuentoProduccion = BigDecimal.ZERO;
        BigDecimal totalPagadoProduccion;
        BigDecimal entregaInicialProduccion = BigDecimal.ZERO;
        Short cuotasProduccion;
        BigDecimal montoCuotaIgualProduccion = BigDecimal.ZERO;
        Short cantidadItemsProduccion = 1;
        Character activo = 'S';
        Date ultimaModificacion = new Date();
        //Desarrollo
        List<Transaccion> lista = new ArrayList<Transaccion>();
        List<Motostock> listaStock;
        //Produccion
        List<Vmcompras> listaComprasProduccion = new ArrayList<Vmcompras>();
        comprasProduccionFacade = new ComprasProduccionFacade();
        listaComprasProduccion = comprasProduccionFacade.findByIdCompraOrdenado();
        List<Vmmotostock> listaStockProduccion;
        if (!listaComprasProduccion.isEmpty()) {
            for (Vmcompras c : listaComprasProduccion) {
                id = c.getIdCompras();
                LOGGER.log(Level.INFO, "==============================================================");
                LOGGER.log(Level.INFO, "La venta Nro. {0}", id);
                fechaOperacionProduccion = c.getFecha() == null ? new Date() : c.getFecha();
                fechaEntregaProduccion = c.getFecha() == null ? new Date() : c.getFecha();
                //Buscar Proveedor con RUC nuevo
                String prov = null;
                if (c.getCodProveedor() == null) {
                    prov = "80001307-7";
                } else {
                    if (c.getCodProveedor().trim().equals("ALEA634530T")) {
                        prov = "80002740-0";
                    } else if (c.getCodProveedor().trim().equals("CHAA9583400")) {
                        prov = "80013744-2";
                    } else if (c.getCodProveedor().trim().equals("MFEB998270M")) {
                        prov = "80020496-4";
                    } else if (c.getCodProveedor().trim().equals("REIB796460N")) {
                        prov = "80001307-7";
                    } else {
                        prov = c.getCodProveedor().trim();
                    }
                }
                vendedor = getPersonaFacade().findByDocumento(prov);
                anulado = c.getAnulado() ? 'S' : 'N';
                if (c.getSubTotal() != null && c.getSubTotal() > 1000) {
                    subTotal = new BigDecimal(c.getSubTotal());
                } else {
                    subTotal = BigDecimal.valueOf(1000);
                }
                totalIvaProduccion = new BigDecimal(c.getIvaCf());
                if (c.getMontoTotal() != null && c.getMontoTotal() > 1000) {
                    totalProduccion = new BigDecimal(c.getMontoTotal());
                } else {
                    totalProduccion = BigDecimal.valueOf(1000);
                }
                totalPagadoProduccion = totalProduccion;
                cuotasProduccion = c.getCuotas() == null ? Short.valueOf("0") : c.getCuotas();
                if (c.getCuotas() != null && c.getCuotas() > 0) {
                    //Compra a Credito
                    codigo = getCategoriaFacade().find(CategoriaEnum.COMPRA_MCR.getSymbol());
                    categoriaFactura = getCategoriaFacade().find(CategoriaEnum.FACTURA_COMPRA_MCR.getSymbol());
                } else {
                    codigo = getCategoriaFacade().find(CategoriaEnum.COMPRA_MCO.getSymbol());
                    categoriaFactura = getCategoriaFacade().find(CategoriaEnum.FACTURA_COMPRA_MCO.getSymbol());
                }
                //Factura
                numero = "" + c.getNumeroFactura();
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

                //Fecha de Vencimiento
                Calendar ahora = GregorianCalendar.getInstance();
                ahora.add(Calendar.YEAR, 1);
                //Factura
                factura = new Factura(null, numero, null, ahora.getTime(), categoriaFactura,
                        subTotalExentasProduccion, subTotalGravadas10Produccion, subTotalGravadas5Produccion, totalIva5Produccion,
                        totalIva10Produccion, totalIva5Produccion, totalIva10Produccion, subTotal, totalIvaProduccion, totalPagadoProduccion,
                        descuento, anulado, activo, ultimaModificacion);
                //Juntar todo
                t = new Transaccion(id, codigo, factura, fechaOperacionProduccion, fechaEntregaProduccion, vendedor, compradorProduccion,
                        anulado, subTotalExentasProduccion, subTotalGravadas10Produccion, subTotalGravadas5Produccion, totalIva5Produccion, totalIva10Produccion,
                        subTotal, totalIva5Produccion, totalIva10Produccion, totalIvaProduccion, descuento, totalProduccion, totalDescuentoProduccion, totalPagadoProduccion,
                        entregaInicialProduccion, cuotasProduccion, montoCuotaIgualProduccion, anulado, cantidadItemsProduccion, activo, ultimaModificacion, new Date(), LoginBean.getInstance().getUsuario(), LoginBean.getInstance().getUsuario());
                getFacade().setEntity(t);
                getFacade().create();
                totalMigrado++;
                if (t.getIdAnterior().equals(27)) {
                    Transaccion tx = new Transaccion(28, codigo, factura, fechaOperacionProduccion, fechaEntregaProduccion, vendedor, compradorProduccion,
                            anulado, subTotalExentasProduccion, subTotalGravadas10Produccion, subTotalGravadas5Produccion, totalIva5Produccion, totalIva10Produccion,
                            subTotal, totalIva5Produccion, totalIva10Produccion, totalIvaProduccion, descuento, totalProduccion, totalDescuentoProduccion, totalPagadoProduccion,
                            entregaInicialProduccion, cuotasProduccion, montoCuotaIgualProduccion, 'S', cantidadItemsProduccion, 'N', ultimaModificacion, new Date(), LoginBean.getInstance().getUsuario(), LoginBean.getInstance().getUsuario());
                    getFacade().create(tx);
                    totalMigrado++;
                }

            }
//            try {
//                totalMigrado = getFacade().cargaMasiva(lista);
//            } catch (Exception ex) {
//                LOGGER.log(Level.SEVERE, ex.getMessage());
//            }
            setInfoMessage(null, "Total de registros importados: " + totalMigrado);
        }


        return "listacompramotos";
    }

    public String importarVentas() {
        return "importarVenta";
    }

    public String migrarVentas() {
        int totalMigrado = 0;
        Character anuladoFiltro;
        //Venta
        Transaccion t;
        Integer idProduccion;
        Categoria codigoDesarrollo = null;
        Factura facturaDesarrollo = null;
        String numero;
        Categoria categoriaFactura = null;
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
        Short cuotasProduccion = null;
        Short cantidadItemsProduccion = 1;
        Character activo;
        Character saldadoProduccion;
        Date fechaUltimoPagoProduccion = null;
        Date ultimaModificacion = new Date();
        //Desarrollo
        List<Financiacion> listaCuotasDesarrollo = new ArrayList<Financiacion>();
        Credito credito = null;
        List<Pago> listaPagosDesarrollo;
        BigDecimal montoCuota;
        Short cuotaNumero;
        Integer cantidadCuotas;
        BigDecimal interesTotal;
        BigDecimal interesMensual;
        BigDecimal factor;
        BigDecimal cien = BigDecimal.valueOf(100);
        BigDecimal montoCapital;
        BigDecimal montoInteres;
        //Produccion
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        ventaMotosProduccionFacade = new VentaMotosProduccionFacade();
        facturaFacade = new FacturaFacade();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdVentaOrdenado();
        Financiacion financiacionDesarrollo;
        if (!listaVentasProduccion.isEmpty()) {
            for (Vmventamotos c : listaVentasProduccion) {
                if (c.getIdVenta() == 389) {
                    int pararAqui = 0;
                }
                activo = 'S';
                idProduccion = c.getIdVenta();
                LOGGER.log(Level.INFO, "==============================================================");
                LOGGER.log(Level.INFO, "La venta Nro. {0}", c.getIdVenta());
                fechaOperacionProduccion = c.getFechaVenta() == null ? new Date() : c.getFechaVenta();
                fechaEntregaProduccion = c.getFechaEntrega() == null ? fechaOperacionProduccion : c.getFechaEntrega();
                listaPagosDesarrollo = new ArrayList<Pago>();
                //Buscar Cliente
                String codCliente = null;
                if (c.getCedulaRuc() == null) {
                    codCliente = ConfiguracionEnum.PROPIETARIO.getSymbol();
                } else {
                    codCliente = c.getCedulaRuc().trim();
                }
                //Montos
                if (c.getPrecioMoto() != null && c.getPrecioMoto() > 0) {
                    totalPagadoProduccion = BigDecimal.valueOf(c.getPrecioMoto());
                } else {
                    totalPagadoProduccion = BigDecimal.TEN;
                }
                BigDecimal entregaInicialProduccion = BigDecimal.ZERO;
                if (c.getEntregaMoto() != null) {
                    entregaInicialProduccion = BigDecimal.valueOf(c.getEntregaMoto());
                }
                totalProduccion = totalPagadoProduccion;
                subTotalGravadas10Produccion = totalPagadoProduccion.multiply(BigDecimal.valueOf(0.02)).setScale(Integer.valueOf(ConfiguracionEnum.MONEDA_DECIMALES.getSymbol()), RoundingMode.HALF_UP);
                subTotalExentasProduccion = totalPagadoProduccion.subtract(subTotalGravadas10Produccion);
                totalIva10Produccion = subTotalGravadas10Produccion.multiply(BigDecimal.valueOf(0.1));
                BigDecimal montoCuotaIgualProduccion = BigDecimal.ZERO;
                //Saldado
                if (c.getSalAcMoto() > 0) {
                    saldadoProduccion = 'N';
                } else {
                    saldadoProduccion = 'S';
                }
                //Entrega Inicial con refuerzo incluido
                if (c.getVmresfuerzosCollection() != null && !c.getVmresfuerzosCollection().isEmpty()) {
                    LOGGER.log(Level.INFO, "La venta {0} tiene {1} registros de Refuerzos", new Object[]{c.getIdVenta(), c.getVmresfuerzosCollection().size()});
                    for (Vmresfuerzos r : c.getVmresfuerzosCollection()) {
                        if (!r.getAnulado() && r.getGuardado()) {
                            LOGGER.log(Level.INFO, "Se va a sumar al monto total el refuerzo con el monto :{0}", r.getMontoResfuerzo());
                            totalPagadoProduccion.add(BigDecimal.valueOf(r.getMontoResfuerzo()));
                        }
                    }
                }
                //Factura
                facturaDesarrollo = new Factura();
                facturaDesarrollo.setSaldado(saldadoProduccion);
                facturaDesarrollo.setSubTotalExentas(subTotalExentasProduccion);
                facturaDesarrollo.setSubTotalGravadas10(subTotalGravadas10Produccion);
                facturaDesarrollo.setSubTotalGravadas5(subTotalGravadas5Produccion);
                facturaDesarrollo.setNetoSinIva5(totalIva5Produccion);
                facturaDesarrollo.setNetoSinIva10(subTotalGravadas10Produccion.subtract(totalIva10Produccion));
                facturaDesarrollo.setTotalIva5(totalIva5Produccion);
                facturaDesarrollo.setTotalIva10(totalIva10Produccion);
                facturaDesarrollo.setTotalIva(totalIva10Produccion);
                facturaDesarrollo.setSubTotal(totalPagadoProduccion);
                facturaDesarrollo.setTotalPagado(totalPagadoProduccion);
                facturaDesarrollo.setDescuento(descuentoProduccion);
                facturaDesarrollo.setValidoHasta(ultimaModificacion);
                facturaDesarrollo.setActivo('S');
                facturaDesarrollo.setUltimaModificacion(ultimaModificacion);
                //Contado o Credito
                if (c.getIdTransaccion() == 1 || c.getIdTransaccion() == 2) {
                    //Excepcion
                    if (c.getIdVenta() == 910) {
                        c.setIdTransaccion(1);
                        c.setEntregaMoto(3250000);
                    }
                    if (c.getIdTransaccion() == 1) {
                        //Venta a Contado
                        categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCO.getSymbol());
                        facturaDesarrollo.setCategoria(categoriaFactura);
                        cuotasProduccion = 0;
                        listaPagosDesarrollo.add(new Pago(null, fechaOperacionProduccion, null, String.valueOf(c.getIdTransaccion()), totalPagadoProduccion, 'S', new Date()));
                        LOGGER.info("Es Venta CONTADO");
                    } else {
                        //Venta Credito
                        List<Vmplanmoto> listaCuotasProduccion = new ArrayList<Vmplanmoto>();
                        if (c.getNumeroCuotas() != null && c.getNumeroCuotas() > 0) {
                            cuotasProduccion = Short.valueOf("" + c.getNumeroCuotas());
                        } else {
                            cuotasProduccion = Short.valueOf("" + c.getVmplanmotoCollection().size());
                        }
                        if (c.getMontoCuotas() != null && c.getMontoCuotas() > 0) {
                            montoCuotaIgualProduccion = BigDecimal.valueOf(c.getMontoCuotas());
                        } else {
                            if (c.getSaldoMoto() != null && c.getSaldoMoto() > 0) {
                                montoCuotaIgualProduccion = BigDecimal.valueOf(c.getSaldoMoto() / cuotasProduccion);
                            } else {
                                montoCuotaIgualProduccion = BigDecimal.valueOf((c.getPrecioMoto() - c.getEntregaMoto()) / cuotasProduccion);
                            }
                        }
                        BigDecimal capitalProduccion = BigDecimal.valueOf(c.getSaldoMoto());
                        BigDecimal creditoTotalProduccion = BigDecimal.valueOf(montoCuotaIgualProduccion.intValue() * cuotasProduccion);
                        listaCuotasDesarrollo = new ArrayList<Financiacion>();
                        categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCR.getSymbol());
                        facturaDesarrollo.setCategoria(categoriaFactura);
                        credito = new Credito(null, new Categoria(CategoriaEnum.S_PER.getSymbol()), null,
                                c.getFechaVenta(), c.getFechaVenta(), new Categoria(CategoriaEnum.S_ESP.getSymbol()), 0.0F, 0.0F, 2.5F,
                                capitalProduccion, cuotasProduccion, creditoTotalProduccion, BigDecimal.ZERO,
                                BigDecimal.ZERO, BigDecimal.ZERO, fechaUltimoPagoProduccion, new Short("0"),
                                new Categoria(CategoriaEnum.ABIERTO.getSymbol()), 'S', new Date());
                        if (c.getIdCodeudor() != null && c.getIdCodeudor() > 0) {
                            credito.setGarante(getPersonaFacade().findByDocumento(String.valueOf(c.getIdCodeudor())));
                        }
                        //Calcular montos
                        cantidadCuotas = c.getNumeroCuotas();
                        //Saltar errores
                        if (cantidadCuotas <= 0) {
                            cantidadCuotas = 2;
                            activo = 'N';
                        }
                        if (c.getPrecioContado() == null || c.getPrecioContado() <= 0) {
                            c.setPrecioContado(1000000);
                            activo = 'N';
                        }
                        montoCuota = montoCuotaIgualProduccion;
                        interesTotal = BigDecimal.valueOf(((c.getPrecioMoto() - c.getPrecioContado()) * 100) / c.getPrecioContado());
                        interesMensual = interesTotal.divide(BigDecimal.valueOf(cantidadCuotas), 4, RoundingMode.UP);
                        factor = cien.divide((interesMensual.add(cien)), 4, RoundingMode.UP);
                        montoCapital = montoCuota.multiply(factor);
                        montoInteres = montoCuota.subtract(montoCapital);
                        //FINANCIACION
                        //Buscar Cuotas
                        listaCuotasProduccion = getPlanMotosProduccionFacade().findByIdVenta(c.getIdVenta());
                        if (!listaCuotasProduccion.isEmpty()) {
                            LOGGER.log(Level.INFO, "Es Venta CREDITO y tiene {0} Cuotas", listaCuotasProduccion.size());
                            BigDecimal totalPagadoHastaAhora = BigDecimal.ZERO;

                            Date fechaVencimiento;
                            Character activoX;
                            Character cancelado = 'N';
                            for (int x = 0; x < listaCuotasProduccion.size(); x++) {
                                Vmplanmoto p = listaCuotasProduccion.get(x);
                                activoX = 'S';
                                if (p.getFechaVencimiento() != null) {
                                    fechaVencimiento = p.getFechaVencimiento();
                                } else {
                                    fechaVencimiento = new Date();
                                    activoX = 'N';
                                }
                                if (p.getCuotaNumero() != null) {
                                    cuotaNumero = Short.valueOf("" + p.getCuotaNumero());
                                } else {
                                    cuotaNumero = Short.MIN_VALUE;
                                    activoX = 'N';
                                }
                                if (p.getGuardado()) {
                                    if (p.getAnulado()) {
                                        activoX = 'N';
                                    }
//                                } else {
//                                    cancelado = 'S';
//                                }
                                }
                                financiacionDesarrollo = new Financiacion(null, credito, cuotaNumero,
                                        montoCapital, montoInteres,
                                        montoCuota, montoCuota,
                                        BigDecimal.ZERO, null, cancelado, fechaVencimiento, BigDecimal.valueOf(p.getMontoInteresMensual()),
                                        p.getFechaPago() != null ? BigDecimal.valueOf(p.getMontoCuota()) : null, activoX, new Date());
                                totalPagadoHastaAhora = totalPagadoHastaAhora.add(montoCuotaIgualProduccion);
                                if (credito.getFechaFin().before(fechaVencimiento)) {
                                    credito.setFechaFin(fechaVencimiento);
                                }
                                listaCuotasDesarrollo.add(financiacionDesarrollo);
                            }
                        }
                    }
                    compradorProduccion = getPersonaFacade().findByDocumento(codCliente);
                    if (compradorProduccion == null) {
                        compradorProduccion = getPersonaFacade().findByDocumento(ConfiguracionEnum.PROPIETARIO.getSymbol());
                        anuladoFiltro = 'S';
                    } else {
                        anuladoFiltro = c.getAnulado() ? 'S' : 'N';
                    }
                } else {
                    //Otras Transacciones
                    BigDecimal cientoDos = new BigDecimal("102");
                    BigDecimal ochentaPorCiento = new BigDecimal("0.8");
                    if (c.getIdTransaccion() == 7) {
                        //Devolucion
                        categoriaFactura = new Categoria(CategoriaEnum.DEVOLUCION_SIMPLE.getSymbol());
                    } else if (c.getIdTransaccion() == 8) {
                        //Canje
                        categoriaFactura = new Categoria(CategoriaEnum.CANJE_SIMPLE.getSymbol());
                    }
                    facturaDesarrollo.setCategoria(categoriaFactura);
                    fechaOperacionProduccion = c.getFechaVenta();
                    fechaEntregaProduccion = fechaOperacionProduccion;
                    Motostock m = null;
                    try {
                        m = getMotostockFacade().findByNumeroAnterior(c.getIdMoto().getIdMoto());
                    } catch (Exception ex) {
                        Logger.getLogger(MigrarTransaccionesBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    compradorProduccion = m.getMoto().getFabricante();
                    anuladoFiltro = 'S';
                    BigDecimal precio = m.getCosto().multiply(cien.divide(cientoDos, 10, RoundingMode.HALF_DOWN));
                    subTotalExentasProduccion = precio.multiply(ochentaPorCiento).setScale(0, RoundingMode.HALF_UP);
                    BigDecimal netoSinIva10 = precio.subtract(subTotalExentasProduccion);
                    totalIva10Produccion = netoSinIva10.multiply((BigDecimal.TEN.divide(cien, 1, RoundingMode.HALF_DOWN))).setScale(0, RoundingMode.HALF_UP);
                    totalIvaProduccion = totalIva10Produccion;
                    subTotalGravadas10Produccion = netoSinIva10.add(totalIva10Produccion);
                    subTotal = m.getCosto();
                    totalDescuentoProduccion = BigDecimal.ZERO;
                    descuentoProduccion = 0.0F;
                    totalProduccion = m.getCosto();
                    subTotalGravadas5Produccion = BigDecimal.ZERO;
                    totalIva5Produccion = BigDecimal.ZERO;
                    totalPagadoProduccion = m.getCosto();
                    entregaInicialProduccion = m.getCosto();
                    cuotasProduccion = Short.valueOf("0");
                    montoCuotaIgualProduccion = BigDecimal.ZERO;
                    saldadoProduccion = 'S';
                    cantidadItemsProduccion = Short.valueOf("1");
                    activo = 'N';
                    credito = null;
                }
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
                facturaDesarrollo.setNumero(numero);
                //Fecha de Vencimiento
                Calendar ahora = GregorianCalendar.getInstance();
                ahora.add(Calendar.YEAR, 1);
                //Juntar todo
                t = new Transaccion(c.getIdVenta(), categoriaFactura, facturaDesarrollo, fechaOperacionProduccion, fechaEntregaProduccion, vendedorProduccion,
                        compradorProduccion, anuladoFiltro, subTotalExentasProduccion, subTotalGravadas10Produccion, subTotalGravadas5Produccion,
                        totalIva5Produccion, totalIva10Produccion, subTotal, totalIva5Produccion, totalIva10Produccion, totalIvaProduccion,
                        descuentoProduccion, totalProduccion, totalDescuentoProduccion, totalPagadoProduccion, entregaInicialProduccion,
                        cuotasProduccion, montoCuotaIgualProduccion, saldadoProduccion, cantidadItemsProduccion, activo, new Date(), new Date(), LoginBean.getInstance().getUsuario(), LoginBean.getInstance().getUsuario());
                if (credito != null) {
                    if (credito.getFechaFin() == null) {
                        credito.setFechaFin(fechaOperacionProduccion);
                    }
//                    if (!listaPagosDesarrollo.isEmpty()) {
//                        t.setPagos(listaPagosDesarrollo);
//                    }
                    credito.setFinanciacions(listaCuotasDesarrollo);
                    t.setCreditosTransaccion(Arrays.asList(credito));
                }
                getFacade().create(t);
            }
//            try {
//                totalMigrado = getFacade().cargaMasiva(lista);
//            } catch (Exception ex) {
//                LOGGER.log(Level.SEVERE, ex.getMessage());
//            }
            //Asignar Venta a Motos
            Motostock motoDesarrollo;
            Transaccion ventaParaMoto;
            try {
                LOGGER.log(Level.INFO, "ASIGNACION DE VENTA A LAS MOTOS GUARDADAS");
                LOGGER.log(Level.INFO, "=============================================================");
                for (Vmventamotos ventaProduccion : listaVentasProduccion) {
                    LOGGER.log(Level.INFO, "La Venta Anterior es la Nro. {0}", ventaProduccion.getIdVenta());
                    ventaParaMoto = getFacade().findByNumeroAnterior(
                            ventaProduccion.getIdVenta(), CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol());
                    LOGGER.log(Level.INFO, "La Venta Actual es la Nro. {0}", ventaParaMoto.getId());
                    motoDesarrollo = getMotostockFacade().findByNumeroAnterior(ventaProduccion.getIdMoto().getIdMoto());
                    LOGGER.log(Level.INFO, "La Moto es la Nro. {0}", motoDesarrollo.getId());
                    motoDesarrollo.setVenta(ventaParaMoto);
                    motoDesarrollo.setActivo('S');
                    LOGGER.log(Level.INFO, "Se la va a asignar la venta Nro. {0}", motoDesarrollo.getVenta().getId());
                    getMotostockFacade().setEntity(motoDesarrollo);
                    getMotostockFacade().guardar();
                }
            } catch (Exception ex) {
                Logger.getLogger(MigrarTransaccionesBean.class.getName()).log(Level.SEVERE, "Excepcion al intentar asignar ventas al stock", ex);
            }//Desactivar los modelos que no tienen stock
            List<Moto> listaCompleta = getMotoFacade().findAll();
            for (Moto m : listaCompleta) {
                boolean hayStock = false;
                List<Motostock> listaDeStock = new ArrayList<Motostock>();
                if (m.getActivo() != null) {
                    if (m.getActivo().equals('S')) {
                        listaDeStock = m.getMotostocks();
//                        LOGGER.log(Level.WARNING, "El modelo {0} tiene {1} motos.", new Object[]{m.getCodigo(), listaDeStock.size()});
                        if (!listaDeStock.isEmpty()) {
                            for (Motostock ms : listaDeStock) {
                                if (ms.getVenta() == null) {
                                    hayStock = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                }
                if (hayStock) {
                    m.setActivo('S');
                } else {
                    m.setActivo('N');
                }
                getMotoFacade().setEntity(m);
                LOGGER.log(Level.INFO, "Se va a guardar la moto: {0}", m.getId());
                getMotoFacade().guardar();
            }

            setInfoMessage(null, "Total de registros importados: " + totalMigrado);
        }


        return "listaventamotos";
    }

    private void poblarMapaDeCuotas(Object key, Object value) throws Exception {
        Short k = null;
        BigDecimal v = BigDecimal.ZERO;
        k = new Short(String.valueOf(key));
        v = new BigDecimal(String.valueOf(value));
        map.put(k, v);
    }

    private String limpiarCadena(String valor) {
        if (valor.equals("4+")) {
            int r = 0;
        }
        String res = valor;
        int letra = -2;
        do {
            if (valor.contains("y")) {
                letra = valor.indexOf("y");
            } else if (valor.contains("/")) {
                letra = valor.indexOf("/");
            } else if (valor.contains(",")) {
                letra = valor.indexOf(",");
            } else if (valor.contains("+")) {
                letra = valor.indexOf("+");
            } else if (valor.contains(" ")) {
                letra = valor.indexOf(" ");
            } else {
                letra = -1;
            }
            switch (letra) {
                case 0:
                    valor = valor.substring(1, valor.length());
                    break;
                case 1:
                    valor = valor.substring(0, 1);
                    break;
                case 2:
                    valor = valor.substring(0, 2);
                    break;
                case 3:
                    valor = valor.substring(0, valor.length() - 1);
                    break;
            }
        } while (letra != -1);
        res = valor.trim();
        return res;
    }

    public String migrarPagosDeCuotas() {
        String res = null;
        int totalPagosImportados = 0;
        Transaccion t;
        List<Vmpagomotos> pagosProduccion;
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdVentaOrdenado();
        try {
            if (!listaVentasProduccion.isEmpty()) {
                boolean esPagoParcial;
                for (Vmventamotos c : listaVentasProduccion) {

                    //Buscar Transaccion
                    t = getFacade().findByNumeroAnterior(c.getIdVenta(), CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol());
                    Credito credito = null;
                    credito = getCreditoFacade().findByTransaccion(t.getId());
                    if (credito.getId() == 1732) {
                        int pararAqui = 0;
                    }
                    //Fecha de ultimo Pago
                    Date fechaUltimoPago = credito.getFechaInicio();
                    //Monto total pagado
                    BigDecimal montoTotalPagado = BigDecimal.ZERO;
                    //Monto de la cuota fija
                    Integer montoCuotaFija = c.getMontoCuotas();
                    //Buscar Pagos
                    pagosProduccion = getPagoMotosProduccionFacade().findByIdVenta(c.getIdVenta());
                    //Registrar Pagos
                    if (!pagosProduccion.isEmpty()) {
                        List<Pago> pagosDesarrollo = new ArrayList<Pago>();
                        String cadenaParcial = "parcial";
                        String cadenaIniciar = "Iniciar";
                        List<DetallePago> detalle = null;
                        Categoria pagoParcial = getCategoriaFacade().find(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol());
                        Categoria pagoTotal = getCategoriaFacade().find(CategoriaEnum.PAGO_CUOTA.getSymbol());
                        Categoria categoriaPago = pagoTotal;
                        for (Vmpagomotos pago : pagosProduccion) {
                            esPagoParcial = false;
                            if (pago.getGuardado() < 0) {
                                if (pago.getConcepto() != null && pago.getConcepto().contains(cadenaParcial)) {
                                    esPagoParcial = true;
                                }
                                if (pago.getMontoEntrega() == null || pago.getMontoEntrega() < 1
                                        || pago.getConcepto() == null || pago.getConcepto().contains(cadenaIniciar)) {
                                    continue;
                                }
                                Pago pg = new Pago(null, pago.getFechaPago(),
                                        credito, String.valueOf(pago.getNumeroRecibo()), BigDecimal.valueOf(pago.getMontoEntrega()),
                                        pago.getAnulado() ? 'N' : 'S', new Date());
                                if (pg.getFecha() == null) {
                                    pg.setFecha(new Date());
                                    pg.setActivo('N');
                                }
                                detalle = new ArrayList<DetallePago>();
                                if (pago.getNumCuotas() != null) {
                                    map = new HashMap<Short, BigDecimal>();
                                    //Es complejo
                                    if (pago.getNumCuotas().contains("/")
                                            || pago.getNumCuotas().contains("-")
                                            || pago.getNumCuotas().contains("+")
                                            || pago.getNumCuotas().contains(",")
                                            || pago.getNumCuotas().contains("'")
                                            || pago.getNumCuotas().contains(" ")) {
                                        StringTokenizer stBarra = new StringTokenizer(pago.getNumCuotas(), "/");
                                        String cuotas = null;
                                        cuotas = stBarra.nextToken();
                                        if (cuotas.contains("-")) {
                                            //Tiene varias cuotas adentro o es un pago parcial
                                            Integer montoDelPago = pago.getMontoEntrega();
                                            StringTokenizer stGuion = new StringTokenizer(cuotas, "-");
                                            if (stGuion.countTokens() > 1) {
                                                do {
                                                    esPagoParcial = Boolean.FALSE;
                                                    String valor = null;
                                                    try {
                                                        valor = stGuion.nextToken();
                                                        if (valor.contains("+")) {
                                                            esPagoParcial = Boolean.TRUE;
                                                            categoriaPago = pagoParcial;
                                                        }
                                                        valor = limpiarCadena(valor);
                                                        if (!esPagoParcial) {
                                                            poblarMapaDeCuotas(valor, montoCuotaFija);
                                                            montoDelPago = montoDelPago - montoCuotaFija;
                                                        } else {
                                                            poblarMapaDeCuotas(valor, montoDelPago);
                                                        }
                                                    } catch (NumberFormatException nfe) {
                                                        LOGGER.log(Level.INFO, "Excepcion al intentar convertir a numero", nfe);
                                                        LOGGER.log(Level.INFO, "La cadena es :{0}", valor);
                                                        if (valor.contains("+")) {
                                                            valor = valor.substring(1, valor.length() - 1);
                                                            poblarMapaDeCuotas(valor, montoDelPago);
                                                        }
                                                    } catch (Exception ex) {
                                                        LOGGER.log(Level.SEVERE, "Excepcion al intentar convertir a numero", ex);
                                                    }
                                                } while (stGuion.hasMoreElements());
                                            } else {
                                                //Cuota Simple. Puede ser Parcial
                                                String valor = stGuion.nextToken();
                                                try {
                                                    if (valor.contains("+")) {
                                                        esPagoParcial = Boolean.TRUE;
                                                        categoriaPago = pagoParcial;
                                                    }
                                                    valor = limpiarCadena(valor);
                                                    if (!esPagoParcial) {
                                                        poblarMapaDeCuotas(valor, montoCuotaFija);
                                                        montoDelPago = montoDelPago - montoCuotaFija;
                                                    } else {
                                                        poblarMapaDeCuotas(valor, montoDelPago);
                                                    }
                                                } catch (NumberFormatException nfe) {
                                                    LOGGER.log(Level.INFO, "Excepcion al intentar convertir a numero", nfe);
                                                    LOGGER.log(Level.INFO, "La cadena es :{0}", valor);
                                                    if (valor.contains("+")) {
                                                        valor = valor.substring(1, valor.length() - 1);
                                                    }
                                                } catch (Exception ex) {
                                                    LOGGER.log(Level.SEVERE, "Excepcion al intentar convertir a numero", ex);
                                                }
                                            }
                                        } else {
                                            //Cuota Simple Parcial
                                            Integer montoDelPago = pago.getMontoEntrega();
                                            String valor = cuotas;
                                            try {
                                                if (valor.contains("+")) {
                                                    esPagoParcial = Boolean.TRUE;
                                                    categoriaPago = pagoParcial;
                                                }
                                                valor = limpiarCadena(valor);
                                                if (valor.equals("")) {
                                                    int eee = 0;
                                                }
                                                if (!esPagoParcial) {
                                                    poblarMapaDeCuotas(valor, montoCuotaFija);
                                                    montoDelPago = montoDelPago - montoCuotaFija;
                                                } else {
                                                    poblarMapaDeCuotas(valor, montoDelPago);
                                                }
                                            } catch (NumberFormatException nfe) {
                                                LOGGER.log(Level.INFO, "Excepcion al intentar convertir a numero", nfe);
                                                LOGGER.log(Level.INFO, "La cadena es :{0}", valor);
                                                if (valor.contains("+")) {
                                                    valor = valor.substring(1, valor.length() - 1);
                                                }
                                            } catch (Exception ex) {
                                                LOGGER.log(Level.SEVERE, "Excepcion al intentar convertir a numero", ex);
                                            }
                                        }
                                    } else {
                                        //Cuota Simple y total
                                        if (pago.getNumCuotas().equals("160000")) {
                                            pago.setNumCuotas("13");
                                        }
                                        try {
                                            poblarMapaDeCuotas(pago.getNumCuotas().trim(), pago.getMontoEntrega());
                                        } catch (NumberFormatException nfe) {
                                            LOGGER.log(Level.INFO, "Excepcion al intentar convertir a numero", nfe);
                                            LOGGER.log(Level.INFO, "La cadena es :{0}", pago.getNumCuotas());
                                        } catch (Exception ex) {
                                            LOGGER.log(Level.SEVERE, "Excepcion al intentar convertir a numero", ex);
                                        }
                                    }
                                }
                                Iterator<Short> it = map.keySet().iterator();
                                Short cuota;
                                BigDecimal monto;
                                do {
                                    cuota = it.next();
                                    monto = map.get(cuota);
                                    if (monto.equals(BigDecimal.valueOf(montoCuotaFija))) {
                                        categoriaPago = pagoTotal;
                                    } else {
                                        categoriaPago = pagoParcial;
                                    }
                                    detalle.add(new DetallePago(categoriaPago, pg, pagoTotal.getDescripcion() + " " + cuota, monto, cuota, 'S', new Date(), Boolean.FALSE));
                                } while (it.hasNext());
                                for (DetallePago dt : detalle) {
                                    if (dt.getConcepto().length() > 50) {
                                        LOGGER.log(Level.INFO, "====Detalle: {0} Concepto:{1}", new Object[]{pg.getNumeroDocumento(), dt.getConcepto()});
                                    }
                                }
                                pg.setDetalle(detalle);
                                if (pago.getFechaPago() != null && pago.getFechaPago().after(fechaUltimoPago)) {
                                    fechaUltimoPago = pago.getFechaPago();
                                }
                                pagosDesarrollo.add(pg);
                            }
                        }
                        for (Pago px : pagosDesarrollo) {
                            montoTotalPagado = montoTotalPagado.add(px.getTotalPagado());
                        }
                        totalPagosImportados = getPagoFacade().cargaMasiva(pagosDesarrollo);
                        //Actualizar el credito
                        //Fecha ultimo Pago
                        credito.setFechaUltimoPago(fechaUltimoPago);
                        //Monto total pagado
                        if (credito.getCreditoTotal().equals(montoTotalPagado) || credito.getCreditoTotal().equals(credito.getCreditoTotal().min(montoTotalPagado))) {
                            BigDecimal totalAmortizacion = BigDecimal.ZERO;
                            BigDecimal totalInteres = BigDecimal.ZERO;
                            for (Financiacion f : credito.getFinanciacions()) {
                                if (f.getActivo() == 'S') {
                                    totalAmortizacion = totalAmortizacion.add(f.getCapital());
                                    totalInteres = totalInteres.add(f.getInteres());
                                    f.setCancelado('S');
                                }
                            }
                            credito.setTotalAmortizadoPagado(totalAmortizacion);
                            credito.setTotalInteresesPagado(totalInteres);
                            credito.setEstado(new Categoria(CategoriaEnum.CERRADO.getSymbol()));
                        } else {
                            credito.setTotalAmortizadoPagado(montoTotalPagado);
                        }
                        getCreditoFacade().setEntity(credito);
                        getCreditoFacade().guardar();
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        setInfoMessage(null, "Total de Pagos Importados : " + totalPagosImportados);
        return "listaventamotos";
    }

    public String migrarPagos() {
        String res = null;
        int totalPagosImportados = 0;
        Transaccion t;
        Credito credito = null;
        List<Vmpagomotos> pagosProduccion;
        List<Financiacion> listaCuotas;
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdVentaOrdenado();
        try {
            if (!listaVentasProduccion.isEmpty()) {
                for (Vmventamotos c : listaVentasProduccion) {
                    //Buscar Transaccion
                    t = getFacade().findByNumeroAnterior(c.getIdVenta(), CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol());
                    //Buscar Credito
                    credito = getCreditoFacade().findByTransaccion(t.getId());
                    //Buscar Financiacion
                    listaCuotas = getFinanciacionFacade().findByTransaccion(t.getId());
                    //Fecha de ultimo Pago
                    Date fechaUltimoPago = credito.getFechaInicio();
                    //Monto total pagado
                    BigDecimal montoTotalPagado = BigDecimal.ZERO;
                    //Monto de la cuota fija
                    Integer montoCuotaFija = c.getMontoCuotas();
                    //Buscar Pagos
                    pagosProduccion = getPagoMotosProduccionFacade().findByIdVenta(c.getIdVenta());
                    //Registrar Pagos
                    if (!pagosProduccion.isEmpty()) {
                        List<Pago> pagosDesarrollo = new ArrayList<Pago>();
                        List<DetallePago> detalle = null;
                        Categoria pagoParcial = getCategoriaFacade().find(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol());
                        Categoria pagoTotal = getCategoriaFacade().find(CategoriaEnum.PAGO_CUOTA.getSymbol());
                        Categoria categoriaPago = pagoTotal;
                        BigDecimal saldoParaSiguienteCuota = null;
                        for (Vmpagomotos pago : pagosProduccion) {
                            LOGGER.log(Level.INFO, "El pago es Nro:{0}", pago.getNumeroRecibo());
                            Short numeroCuota;
                            BigDecimal montoPagoDetalle;
                            BigDecimal faltaParaCancelarCuota;
                            detalle = new ArrayList<DetallePago>();
                            Pago pg = new Pago(null, pago.getFechaPago(),
                                    credito, String.valueOf(pago.getNumeroRecibo()), BigDecimal.valueOf(pago.getMontoEntrega()),
                                    pago.getAnulado() ? 'N' : 'S', new Date());
                            if (pg.getFecha() == null) {
                                pg.setFecha(new Date());
                                pg.setActivo('N');
                            }
                            //ANTES: SALDAR LO QUE QUEDO DEL PAGO ANTERIOR
                            if (saldoParaSiguienteCuota != null) {
                                do {
                                    boolean faltaCancelar = false;
                                    for (Financiacion f : listaCuotas) {
                                        if (saldoParaSiguienteCuota.compareTo(BigDecimal.ZERO) <= 0) {
                                            break;
                                        }
                                        if (f.getCancelado().equals('N')) {
                                            faltaCancelar = true;
                                            int dimensionDelSaldo = saldoParaSiguienteCuota.compareTo(BigDecimal.valueOf(montoCuotaFija));
                                            switch (dimensionDelSaldo) {
                                                case -1: {
                                                    //Pago Parcial de la Cuota. Se suma al pago actual
                                                    if (f.getTotalPagado() == null) {
                                                        f.setTotalPagado(saldoParaSiguienteCuota);
                                                    } else {
                                                        f.setTotalPagado(f.getTotalPagado().add(saldoParaSiguienteCuota));
                                                    }
                                                    f.setFechaPago(fechaUltimoPago);
                                                    getFinanciacionFacade().setEntity(f);
                                                    getFinanciacionFacade().guardar();
                                                    saldoParaSiguienteCuota = saldoParaSiguienteCuota.subtract(saldoParaSiguienteCuota);
                                                    break;
                                                }
                                                case 0: {
                                                    //Saldo da exacto para un pago total
                                                    f.setTotalPagado(saldoParaSiguienteCuota);
                                                    f.setFechaPago(fechaUltimoPago);
                                                    f.setCancelado('S');
                                                    getFinanciacionFacade().setEntity(f);
                                                    getFinanciacionFacade().guardar();
                                                    saldoParaSiguienteCuota = saldoParaSiguienteCuota.subtract(saldoParaSiguienteCuota);
                                                    break;
                                                }
                                                case 1: {
                                                    //Saldo da para varios pagos. 
                                                    f.setTotalPagado(f.getTotalAPagar());
                                                    f.setFechaPago(fechaUltimoPago);
                                                    f.setCancelado('S');
                                                    getFinanciacionFacade().setEntity(f);
                                                    getFinanciacionFacade().guardar();
                                                    saldoParaSiguienteCuota = saldoParaSiguienteCuota.subtract(f.getTotalAPagar());
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (!faltaCancelar) {
                                        break;
                                    }
                                } while (saldoParaSiguienteCuota.compareTo(BigDecimal.ZERO) > 0);
                            }
                            saldoParaSiguienteCuota = null;
                            // EL PAGO ES IDENTICO A UNA CUOTA
                            if (pago.getMontoEntrega().equals(montoCuotaFija)) {
                                categoriaPago = pagoTotal;
                                for (Financiacion f : listaCuotas) {
                                    if (f.getFechaPago() == null) {
                                        if (f.getCancelado().equals('N')) {
                                            // SE PROCEDE A CANCELAR LA CUOTA
                                            numeroCuota = f.getNumeroCuota();
                                            montoPagoDetalle = BigDecimal.valueOf(pago.getMontoEntrega());
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE));
                                            f.setCancelado('S');
                                            f.setFechaPago(pago.getFechaPago());
                                            f.setTotalPagado(montoPagoDetalle);
                                            getFinanciacionFacade().setEntity(f);
                                            getFinanciacionFacade().guardar();
                                            pg.setDetalle(detalle);
                                            pagosDesarrollo.add(pg);
                                            break;
                                        }
                                        break;
                                    }
                                }
                            } else if (pago.getMontoEntrega() < montoCuotaFija) {
                                //EL PAGO ES MENOR A LA CUOTA
                                categoriaPago = pagoParcial;
                                for (Financiacion f : listaCuotas) {
                                    if (f.getCancelado().equals('N')) {
                                        // SE PROCEDE A PAGAR PARCIALMENTE LA CUOTA
                                        if (f.getTotalPagado() == null) {
                                            f.setTotalPagado(BigDecimal.ZERO);
                                        }
                                        faltaParaCancelarCuota = BigDecimal.valueOf(montoCuotaFija).subtract(f.getTotalPagado());
                                        numeroCuota = f.getNumeroCuota();
                                        montoPagoDetalle = BigDecimal.valueOf(pago.getMontoEntrega());
                                        //Calcular si el pago salda la cuota
                                        int diferenciaDeSaldo = montoPagoDetalle.compareTo(faltaParaCancelarCuota);
                                        switch (diferenciaDeSaldo) {
                                            case -1: {
                                                //El pago es insuficiente para saldar la cuota. Se registra un pago parcial.
                                                categoriaPago = pagoParcial;
                                                detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE));
                                                f.setCancelado('N');
                                                f.setFechaPago(pago.getFechaPago());
                                                f.setTotalPagado(f.getTotalPagado().add(montoPagoDetalle));
                                                getFinanciacionFacade().setEntity(f);
                                                getFinanciacionFacade().guardar();
                                                pg.setDetalle(detalle);
                                                pagosDesarrollo.add(pg);
                                                break;
                                            }
                                            case 0: {
                                                //El pago salda la cuota. Se procede a cancelar la cuota.
                                                categoriaPago = pagoTotal;
                                                detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE));
                                                f.setCancelado('S');
                                                f.setFechaPago(pago.getFechaPago());
                                                f.setTotalPagado(f.getTotalPagado().add(montoPagoDetalle));
                                                getFinanciacionFacade().setEntity(f);
                                                getFinanciacionFacade().guardar();
                                                pg.setDetalle(detalle);
                                                pagosDesarrollo.add(pg);
                                                break;
                                            }
                                            case 1: {
                                                //El pago supera el saldo de la cuota. Se procedera a cancelar la cuota y realizar un nuevo pago
                                                //parcial para la siguiente cuota.
                                                categoriaPago = pagoTotal;
                                                saldoParaSiguienteCuota = montoPagoDetalle.subtract(faltaParaCancelarCuota);
                                                fechaUltimoPago = pago.getFechaPago();
                                                detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, faltaParaCancelarCuota, numeroCuota, 'S', new Date(), Boolean.FALSE));
                                                detalle.add(new DetallePago(pagoParcial, pg, pagoParcial.getDescripcion() + " " + (numeroCuota + 1), saldoParaSiguienteCuota, Short.valueOf("" + (numeroCuota + 1)), 'S', new Date(), Boolean.FALSE));
                                                f.setCancelado('S');
                                                f.setFechaPago(pago.getFechaPago());
                                                f.setTotalPagado(f.getTotalPagado().add(montoPagoDetalle));
                                                getFinanciacionFacade().setEntity(f);
                                                getFinanciacionFacade().guardar();
                                                pg.setDetalle(detalle);
                                                pagosDesarrollo.add(pg);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            } else if (pago.getMontoEntrega() > montoCuotaFija) {
                                //EL PAGO ES MAYOR AL DE LA CUOTA
                                categoriaPago = pagoParcial;
                                BigDecimal saldoDelPago = BigDecimal.valueOf(pago.getMontoEntrega());
                                montoPagoDetalle = BigDecimal.valueOf(pago.getMontoEntrega());
                                numeroCuota = null;
                                for (Financiacion f : listaCuotas) {
                                    if (f.getCancelado().equals('N')) {
                                        numeroCuota = f.getNumeroCuota();
                                        if (f.getFechaPago() != null) {
                                            // SE PROCEDE A PAGAR PARCIALMENTE LA CUOTA
                                            faltaParaCancelarCuota = BigDecimal.valueOf(montoCuotaFija).subtract(f.getTotalPagado());
                                            categoriaPago = pagoParcial;
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, faltaParaCancelarCuota, numeroCuota, 'S', new Date(), Boolean.FALSE));
                                            f.setCancelado('S');
                                            f.setFechaPago(pago.getFechaPago());
                                            f.setTotalPagado(f.getTotalPagado().add(faltaParaCancelarCuota));
                                            getFinanciacionFacade().setEntity(f);
                                            getFinanciacionFacade().guardar();
                                            //Restar del pago, lo que se uso
                                            saldoDelPago = saldoDelPago.subtract(faltaParaCancelarCuota);
                                        } else {
                                            // SE PROCEDE A CANCELAR LA CUOTA
                                            numeroCuota = f.getNumeroCuota();
                                            categoriaPago = pagoTotal;
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, f.getTotalAPagar(), numeroCuota, 'S', new Date(), Boolean.FALSE));
                                            f.setCancelado('S');
                                            f.setFechaPago(pago.getFechaPago());
                                            f.setTotalPagado(f.getTotalAPagar());
                                            getFinanciacionFacade().setEntity(f);
                                            getFinanciacionFacade().guardar();
                                            //Restar del pago, lo que se uso
                                            saldoDelPago = saldoDelPago.subtract(f.getTotalAPagar());
                                        }
                                        saldoParaSiguienteCuota = saldoDelPago;
                                        fechaUltimoPago = pago.getFechaPago();
                                        do {
                                            int saldoDisponible = saldoDelPago.compareTo(f.getTotalAPagar());
                                            numeroCuota++;
                                            BigDecimal saldoParaUtilizar = BigDecimal.ZERO;
                                            switch (saldoDisponible) {
                                                case -1: {
                                                    //Da para un pago parcial
                                                    categoriaPago = pagoParcial;
                                                    saldoParaUtilizar = saldoDelPago;
                                                    break;
                                                }
                                                case 0: {
                                                    //Da para un ultimo pago total
                                                    categoriaPago = pagoTotal;
                                                    saldoParaUtilizar = f.getTotalAPagar();
                                                    break;

                                                }
                                                case 1: {
                                                    //Da para mas pagos
                                                    categoriaPago = pagoTotal;
                                                    saldoParaUtilizar = f.getTotalAPagar();
                                                    break;
                                                }
                                            }
                                            LOGGER.log(Level.INFO, "El numero de cuota es {0}", numeroCuota);
                                            LOGGER.log(Level.INFO, "El monto de la cuota es {0}", saldoParaUtilizar);
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, saldoParaUtilizar, numeroCuota, 'S', new Date(), Boolean.FALSE));
                                            saldoDelPago = saldoDelPago.subtract(saldoParaUtilizar);
                                        } while (saldoDelPago.compareTo(BigDecimal.ZERO) > 0);
                                        pg.setDetalle(detalle);
                                        pagosDesarrollo.add(pg);
                                        break;
                                    }
                                }
                            }
                        }
                        for (Pago px : pagosDesarrollo) {
                            montoTotalPagado = montoTotalPagado.add(px.getTotalPagado());
                        }
                        totalPagosImportados = getPagoFacade().cargaMasiva(pagosDesarrollo);
                        //Actualizar el credito
                        //Fecha ultimo Pago
                        credito.setFechaUltimoPago(fechaUltimoPago);
                        //Monto total pagado
                        if (credito.getCreditoTotal().equals(montoTotalPagado) || credito.getCreditoTotal().equals(credito.getCreditoTotal().min(montoTotalPagado))) {
                            BigDecimal totalAmortizacion = BigDecimal.ZERO;
                            BigDecimal totalInteres = BigDecimal.ZERO;
                            for (Financiacion f : credito.getFinanciacions()) {
                                if (f.getActivo() == 'S') {
                                    totalAmortizacion = totalAmortizacion.add(f.getCapital());
                                    totalInteres = totalInteres.add(f.getInteres());
                                    f.setCancelado('S');
                                }
                            }
                            credito.setTotalAmortizadoPagado(totalAmortizacion);
                            credito.setTotalInteresesPagado(totalInteres);
                            credito.setEstado(new Categoria(CategoriaEnum.CERRADO.getSymbol()));
                        } else {
                            credito.setTotalAmortizadoPagado(montoTotalPagado);
                        }
                        getCreditoFacade().setEntity(credito);
                        getCreditoFacade().guardar();
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        setInfoMessage(null, "Total de Pagos Importados : " + totalPagosImportados);
        return "listaventamotos";
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}

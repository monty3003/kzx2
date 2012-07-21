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
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import py.com.bej.base.prod.entity.Vmcompras;
import py.com.bej.base.prod.entity.Vmintereses;
import py.com.bej.base.prod.entity.Vmmotostock;
import py.com.bej.base.prod.entity.Vmpagomotos;
import py.com.bej.base.prod.entity.Vmplanmoto;
import py.com.bej.base.prod.entity.Vmresfuerzos;
import py.com.bej.base.prod.entity.Vmventamotos;
import py.com.bej.base.prod.session.ComprasProduccionFacade;
import py.com.bej.base.prod.session.PagoMotosProduccionFacade;
import py.com.bej.base.prod.session.PlanMotosProduccionFacade;
import py.com.bej.base.prod.session.VentaMotosProduccionFacade;
import py.com.bej.base.prod.session.VminteresesProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Factura;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Pagare;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FacturaFacade;
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
import py.com.bej.web.servlets.security.LoginBean;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class MigrarTransaccionesBean implements Serializable {

    @EJB
    private PagareFacade pagareFacade;
    @EJB
    private VminteresesProduccionFacade vminteresesProduccionFacade;
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

    /** Creates a new instance of MigrarTransaccionesBean */
    public MigrarTransaccionesBean() {
    }

    public PagareFacade getPagareFacade() {
        if (pagareFacade == null) {
            pagareFacade = new PagareFacade();
        }
        return pagareFacade;
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
                LOGGER.log(Level.FINE, "==============================================================");
                LOGGER.log(Level.FINE, "La venta Nro. {0}", id);
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
        Factura facturaDesarrollo = null;
        String numero;
        Categoria categoriaFactura = null;
        Date fechaOperacionProduccion;
        Date fechaEntregaProduccion;
        Persona compradorProduccion;
        Persona vendedorProduccion = getPersonaFacade().findByDocumento(ConfiguracionEnum.PROPIETARIO.getSymbol());
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
        Categoria estado;
        Character cancelado;
        Character saldadoProduccion;
        Date fechaUltimoPagoProduccion = null;
        Date ultimaModificacion = new Date();
        //Desarrollo
        List<Financiacion> listaCuotasDesarrollo = new ArrayList<Financiacion>();
        Credito credito = null;
        Pagare pagare = null;
        List<Pago> listaPagosDesarrollo;
        BigDecimal montoCuota;
        Short cuotaNumero;
        Integer cantidadCuotas;
        BigDecimal cien = BigDecimal.valueOf(100);
        BigDecimal cientoVeinte = BigDecimal.valueOf(120);
        BigDecimal montoCapital;
        BigDecimal montoInteres;
        BigDecimal ajusteRedondeo;
        //Produccion
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        ventaMotosProduccionFacade = new VentaMotosProduccionFacade();
        facturaFacade = new FacturaFacade();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdVentaOrdenado();
        Financiacion financiacionDesarrollo;
        if (!listaVentasProduccion.isEmpty()) {
            for (Vmventamotos c : listaVentasProduccion) {
                credito = null;
                if (c.getAnulado()) {
                    activo = 'N';
                    estado = new Categoria(CategoriaEnum.CERRADO.getSymbol());
                } else {
                    activo = 'S';
                    estado = new Categoria(CategoriaEnum.ABIERTO.getSymbol());
                }
                if (c.getCancelado()) {
                    cancelado = 'S';
                    estado = new Categoria(CategoriaEnum.CANCELADO.getSymbol());
                } else {
                    cancelado = 'N';
                }
                LOGGER.log(Level.FINE, "==============================================================");
                LOGGER.log(Level.FINE, "La venta en produccion es Nro. {0}", c.getIdVenta());
                fechaOperacionProduccion = c.getFechaVenta() == null ? new Date() : c.getFechaVenta();
                fechaEntregaProduccion = c.getFechaEntrega() == null ? fechaOperacionProduccion : c.getFechaEntrega();
                listaPagosDesarrollo = new ArrayList<Pago>();
                //Buscar Cliente
                String codCliente = null;
                if (c.getCedulaRuc() == null) {
                    codCliente = ConfiguracionEnum.PROPIETARIO.getSymbol();
                    activo = 'N';
                } else {
                    codCliente = c.getCedulaRuc().trim();
                }
                //Montos
                if (c.getPrecioMoto() != null && c.getPrecioMoto() > 0) {
                    totalPagadoProduccion = BigDecimal.valueOf(c.getPrecioMoto());
                } else {
                    totalPagadoProduccion = BigDecimal.TEN;
                    activo = 'N';
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
                    LOGGER.log(Level.FINE, "La venta {0} tiene {1} registros de Refuerzos", new Object[]{c.getIdVenta(), c.getVmresfuerzosCollection().size()});
                    for (Vmresfuerzos r : c.getVmresfuerzosCollection()) {
                        if (!r.getAnulado() && r.getGuardado()) {
                            LOGGER.log(Level.FINE, "Se va a sumar al monto total el refuerzo con el monto :{0}", r.getMontoResfuerzo());
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
                facturaDesarrollo.setActivo(activo);
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
                        LOGGER.fine("Es Venta CONTADO");
                    } else {
                        LOGGER.fine("Es Venta CREDITO");
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
                        if (c.getEntregaMoto().equals(c.getMontoCuotas())) {
                            //Cuota Corrida
                            LOGGER.fine("LA VENTA ES CON CUOTAS CORRIDAS");
                            categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCR_CORRIDAS.getSymbol());
                        } else {
                            //Con Entrega Inicial
                            categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCR_ENTREGA.getSymbol());
                        }
                        facturaDesarrollo.setCategoria(categoriaFactura);
                        credito = new Credito(null, null, c.getFechaVenta(), c.getFechaVenta(), 0.26F, 8.9F, 0.02F,
                                capitalProduccion, cuotasProduccion, creditoTotalProduccion, BigDecimal.ZERO,
                                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, fechaUltimoPagoProduccion, new Short("0"),
                                estado, activo, new Date());
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
                        montoCapital = montoCuota.multiply(cien.divide(cientoVeinte, 6, RoundingMode.UP));
                        montoInteres = montoCuota.subtract(montoCapital);
                        //FINANCIACION
                        //Buscar Cuotas
                        listaCuotasProduccion = getPlanMotosProduccionFacade().findByIdVenta(c.getIdVenta());
                        if (!listaCuotasProduccion.isEmpty()) {
                            LOGGER.log(Level.FINE, "Es Venta CREDITO y tiene {0} Cuotas", listaCuotasProduccion.size());
                            BigDecimal totalPagadoHastaAhora = BigDecimal.ZERO;

                            Date fechaVencimiento;
                            Character activoX;
                            for (int x = 0; x < listaCuotasProduccion.size(); x++) {
                                Vmplanmoto p = listaCuotasProduccion.get(x);
                                activoX = credito.getActivo();
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
                                        BigDecimal.ZERO, null, 'N', fechaVencimiento, BigDecimal.valueOf(p.getMontoInteresMensual()),
                                        BigDecimal.ZERO, p.getFechaPago() != null ? BigDecimal.valueOf(p.getMontoCuota()) : null, activoX, new Date());
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
                        cuotasProduccion, montoCuotaIgualProduccion, cancelado, cantidadItemsProduccion, activo, new Date(), new Date(), LoginBean.getInstance().getUsuario(), LoginBean.getInstance().getUsuario());
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
                if (!t.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
                    boolean vencido = false;
                    Date fechaDeCancelacion = null;
                    //Pagare
                    pagare = new Pagare(null, credito, Short.valueOf("1"), credito.getFechaFin(), credito.getCreditoTotal(),
                            vencido, Boolean.FALSE, fechaDeCancelacion,
                            LoginBean.getInstance().getUsuario(), LoginBean.getInstance().getUsuario(), 'S', new Date(), new Date());
                    getPagareFacade().setEntity(pagare);
                    getPagareFacade().guardar();
                }
                totalMigrado++;
                LOGGER.log(Level.FINE, "La venta Desarrollo queda con Nro. {0}.", t.getId());
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
                LOGGER.log(Level.FINE, "ASIGNACION DE VENTA A LAS MOTOS GUARDADAS");
                LOGGER.log(Level.FINE, "=============================================================");
                for (Vmventamotos ventaProduccion : listaVentasProduccion) {
                    LOGGER.log(Level.FINE, "La Venta Anterior es la Nro. {0}", ventaProduccion.getIdVenta());
                    ventaParaMoto = getFacade().findByNumeroAnterior(
                            ventaProduccion.getIdVenta(), CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol());
                    LOGGER.log(Level.FINE, "La Venta Actual es la Nro. {0}", ventaParaMoto.getId());
                    LOGGER.log(Level.FINE, "El Nro. anterior de la moto es {0}", ventaProduccion.getIdMoto().getIdMoto());
                    motoDesarrollo = getMotostockFacade().find(ventaProduccion.getIdMoto().getIdMoto());
                    if (motoDesarrollo != null) {
                        LOGGER.log(Level.FINE, "La Moto es la Nro. {0}", motoDesarrollo.getId());
                        motoDesarrollo.setEstado(new Categoria(CategoriaEnum.VENDIDO_ENTREGADO.getSymbol()));
                        motoDesarrollo.setVenta(ventaParaMoto);
                        motoDesarrollo.setActivo('S');
                        LOGGER.log(Level.FINE, "Se la va a asignar la venta Nro. {0}", motoDesarrollo.getVenta().getId());
                        getMotostockFacade().setEntity(motoDesarrollo);
                        getMotostockFacade().guardar();
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(MigrarTransaccionesBean.class.getName()).log(Level.SEVERE, "Excepcion al intentar asignar ventas al stock", ex);
            }//Desactivar los modelos que no tienen stock
            List<Moto> listaCompleta = getMotoFacade().findAll();
            for (Moto m : listaCompleta) {
                if (m.getActivo() == null || m.getActivo().equals('N')) {
                    LOGGER.log(Level.FINE, "La moto {0} tiene como valor ACTIVO = {1}. Se omite su modificacion.", new Object[]{m.getCodigo(), m.getActivo()});
                    continue;
                } else {
                    boolean hayStock = false;
                    List<Motostock> listaDeStock = new ArrayList<Motostock>();
                    if (m.getActivo() != null) {
                        if (m.getActivo().equals('S')) {
                            listaDeStock = m.getMotostocks();
                            LOGGER.log(Level.FINE, "El codigo {0} tiene {1} motos.", new Object[]{m.getCodigo(), listaDeStock.size()});
                            if (!listaDeStock.isEmpty()) {
                                for (Motostock ms : listaDeStock) {
                                    LOGGER.log(Level.FINE, "El stock es el {0}", ms.getId());
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
                    LOGGER.log(Level.FINE, "CODIGO :{0}", m.getCodigo());
                    LOGGER.log(Level.FINE, "CODIGO FABRICA:{0}", m.getCodigoFabrica());
                    LOGGER.log(Level.FINE, "CATEGORIA:{0}", m.getCategoria());
                    LOGGER.log(Level.FINE, "FABRICANTE:{0}", m.getFabricante());
                    LOGGER.log(Level.FINE, "MARCA :{0}", m.getMarca());
                    LOGGER.log(Level.FINE, "MODELO:{0}", m.getModelo());
                    LOGGER.log(Level.FINE, "COLOR:{0}", m.getColor());
                    LOGGER.log(Level.FINE, "ACTIVO:{0}", m.getActivo());
                    LOGGER.log(Level.FINE, "ULTIMA MODIFICACION:{0}", m.getUltimaModificacion());

                    getMotoFacade().setEntity(m);
                    LOGGER.log(Level.FINE, "Se va a guardar la moto: {0}", m.getId());
                    getMotoFacade().guardar();
                }
            }

            setInfoMessage(null, "Total de registros importados: " + totalMigrado);
        }


        return "listaventamotos";
    }

    public String migrarPagos() {
        int totalPagosImportados = 0;
        int totalCreditosAbiertos = 0;
        Transaccion t;
        Credito credito = null;
        List<Vmpagomotos> pagosProduccion;
        List<Financiacion> listaCuotas;
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdVentaCreditoOrdenado();
        try {
            if (!listaVentasProduccion.isEmpty()) {
                for (Vmventamotos c : listaVentasProduccion) {
                    LOGGER.log(Level.FINE, "La venta es {0} y es de categoria {1} y es Anulado {2}.", new Object[]{c.getIdVenta(), c.getIdTransaccion(), c.getAnulado()});
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
                        Categoria pagoInteresMoratorio = getCategoriaFacade().find(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol());
                        Categoria descuento = getCategoriaFacade().find(CategoriaEnum.DESCUENTO.getSymbol());
                        Categoria pagoHonorariosProfesionales = getCategoriaFacade().find(CategoriaEnum.PAGO_HONORARIOS_PROFESIONALES.getSymbol());
                        List<Vmintereses> listaDeOtrosPagos;
                        vminteresesProduccionFacade = new VminteresesProduccionFacade();
                        Categoria categoriaPago = pagoTotal;
                        BigDecimal saldoParaSiguienteCuota = null;
                        for (Vmpagomotos pago : pagosProduccion) {
                            if (pago.getNumeroRecibo() == 17015) {
                                int pararAquiAhora = 0;
                            }
                            LOGGER.log(Level.FINE, "El pago es Nro:{0}", pago.getNumeroRecibo());
                            listaDeOtrosPagos = null;
                            if (pago.getNumeroRecibo() != null && pago.getNumeroRecibo() > 0) {
                                listaDeOtrosPagos = vminteresesProduccionFacade.findByNroRecibo(pago.getNumeroRecibo());
                                if (listaDeOtrosPagos != null && !listaDeOtrosPagos.isEmpty()) {
                                    LOGGER.log(Level.FINE, "El pago{0} tiene {1} registros en otros pagos", new Object[]{pago.getNumeroRecibo(), listaDeOtrosPagos.size()});
                                }
                            }
                            Short numeroCuota;
                            BigDecimal montoPagoDetalle;
                            BigDecimal faltaParaCancelarCuota;
                            detalle = new ArrayList<DetallePago>();
                            Pago pg = new Pago(null, pago.getFechaPago(),
                                    credito, String.valueOf(pago.getNumeroRecibo()), BigDecimal.valueOf(pago.getMontoEntrega()),
                                    pago.getAnulado() ? 'N' : 'S', new Date());
                            pg.setUsuarioCreacion(LoginBean.getInstance().getUsuario());
                            pg.setUsuarioModificacion(LoginBean.getInstance().getUsuario());
                            pg.setFechaCreacion(new Date());
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
                                    //CONTROLAR QUE NO HAYA UN PAGO PARCIAL
                                    if (f.getTotalPagado() != null) {
                                        if (f.getFechaPago() != null && f.getCancelado().equals('N')) {
                                            //PAGO PARCIAL
                                            faltaParaCancelarCuota = BigDecimal.valueOf(montoCuotaFija).subtract(f.getTotalPagado());
                                            numeroCuota = f.getNumeroCuota();
                                            montoPagoDetalle = BigDecimal.valueOf(pago.getMontoEntrega());
                                            //Calcular si el pago salda la cuota
                                            int diferenciaDeSaldo = montoPagoDetalle.compareTo(faltaParaCancelarCuota);
                                            switch (diferenciaDeSaldo) {
                                                case -1: {
                                                    //El pago es insuficiente para saldar la cuota. Se registra un pago parcial.
                                                    categoriaPago = pagoParcial;
                                                    detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
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
                                                    detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
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
                                                    detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, faltaParaCancelarCuota, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
                                                    detalle.add(new DetallePago(pagoParcial, pg, pagoParcial.getDescripcion() + " " + (numeroCuota + 1), saldoParaSiguienteCuota, Short.valueOf("" + (numeroCuota + 1)), 'S', new Date(), Boolean.FALSE, null, null));
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
                                    } else if (f.getFechaPago() == null) {
                                        if (f.getCancelado().equals('N')) {
                                            // SE PROCEDE A CANCELAR LA CUOTA
                                            numeroCuota = f.getNumeroCuota();
                                            montoPagoDetalle = BigDecimal.valueOf(pago.getMontoEntrega());
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
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
                                                detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
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
                                                detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, montoPagoDetalle, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
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
                                                detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, faltaParaCancelarCuota, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
                                                detalle.add(new DetallePago(pagoParcial, pg, pagoParcial.getDescripcion() + " " + (numeroCuota + 1), saldoParaSiguienteCuota, Short.valueOf("" + (numeroCuota + 1)), 'S', new Date(), Boolean.FALSE, null, null));
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
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, faltaParaCancelarCuota, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
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
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, f.getTotalAPagar(), numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
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
                                            if (numeroCuota > credito.getAmortizacion()) {
                                                //Ya no hay cuotas disponibles. Se guardara el pago como interes moratorio
                                                LOGGER.log(Level.FINE, "El pago {0} para el credito {1} supera el monto de las cuotas. Se guardara como pago de interes moratorio.",
                                                        new Object[]{pg.getNumeroDocumento(), credito.getId()});
                                                detalle.add(new DetallePago(getCategoriaFacade().find(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol()),
                                                        pg, categoriaPago.getDescripcion(), saldoDelPago, Short.valueOf("" + (numeroCuota - 1)),
                                                        'S', new Date(), Boolean.FALSE, null, null));
                                                break;
                                            }
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
                                            LOGGER.log(Level.FINE, "El numero de cuota es {0}", numeroCuota);
                                            LOGGER.log(Level.FINE, "El monto de la cuota es {0}", saldoParaUtilizar);
                                            detalle.add(new DetallePago(categoriaPago, pg, categoriaPago.getDescripcion() + " " + numeroCuota, saldoParaUtilizar, numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
                                            saldoDelPago = saldoDelPago.subtract(saldoParaUtilizar);
                                        } while (saldoDelPago.compareTo(BigDecimal.ZERO) > 0);
                                        //Registrar otros pagos
                                        for (Vmintereses otros : listaDeOtrosPagos) {
                                            if (otros.getMonto() != 0) {
                                                if (otros.getTransaccion() != null) {
                                                    if (otros.getTransaccion().trim().toLowerCase().contains("honorarios")) {
                                                        detalle.add(new DetallePago(pagoHonorariosProfesionales, pg, pagoHonorariosProfesionales.getDescripcion(), BigDecimal.valueOf(200000), numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
                                                    } else if (otros.getTransaccion().trim().toLowerCase().contains("descuento")) {
                                                        if (otros.getMonto() > 0) {
                                                            otros.setMonto(-1 * otros.getMonto());
                                                        }
                                                        detalle.add(new DetallePago(descuento, pg, descuento.getDescripcion(), BigDecimal.valueOf(otros.getMonto()), numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
                                                    } else {
                                                        detalle.add(new DetallePago(pagoInteresMoratorio, pg, pagoInteresMoratorio.getDescripcion(), BigDecimal.valueOf(otros.getMonto()), numeroCuota, 'S', new Date(), Boolean.FALSE, null, null));
                                                    }
                                                    pg.setTotalPagado(pg.getTotalPagado().add(BigDecimal.valueOf(otros.getMonto())));
                                                }
                                            }
                                        }
                                        pg.setDetalle(detalle);
                                        pagosDesarrollo.add(pg);
                                        break;
                                    }
                                }
                            }
                        }
                        //SI QUEDO ALGO EN SALDO: SALDAR LO QUE QUEDO DEL PAGO ANTERIOR
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
                        for (Pago px : pagosDesarrollo) {
                            montoTotalPagado = montoTotalPagado.add(px.getTotalPagado());
                        }
                        totalPagosImportados += getPagoFacade().cargaMasiva(pagosDesarrollo);
                        //Actualizar el credito
                        //Fecha ultimo Pago
                        credito.setFechaUltimoPago(fechaUltimoPago);
                        //Monto total pagado
                        BigDecimal totalAmortizacion = BigDecimal.ZERO;
                        BigDecimal totalInteres = BigDecimal.ZERO;
                        BigDecimal totalDescuento = BigDecimal.ZERO;
                        BigDecimal totalInteresMoratorio = BigDecimal.ZERO;
                        boolean todoCancelado = true;
                        for (Financiacion f : credito.getFinanciacions()) {
                            if (f.getActivo() == 'S') {
                                f = getFinanciacionFacade().cancelarCuotaConPagoAsignado(f);
                                if (f.getTotalPagado() != null) {
                                    totalAmortizacion = totalAmortizacion.add(f.getCapital());
                                    totalInteres = totalInteres.add(f.getInteres());
                                    totalDescuento = totalDescuento.add(f.getDescuento());
                                    totalInteresMoratorio = totalInteresMoratorio.add(f.getInteresMora());
                                    if (f.getFechaPago() != null && f.getFechaPago().after(credito.getFechaUltimoPago())) {
                                        credito.setFechaUltimoPago(f.getFechaPago());
                                    }
                                }
                                if (f.getCancelado() == 'N') {
                                    todoCancelado = false;
                                }
                            }
                        }
                        credito.setTotalAmortizadoPagado(totalAmortizacion);
                        credito.setTotalInteresesPagado(totalInteres);
                        credito.setTotalDescuento(totalDescuento);
                        credito.setTotalInteresesPagadoMulta(totalInteresMoratorio);
                        if (todoCancelado) {
                            credito.setEstado(new Categoria(CategoriaEnum.CANCELADO.getSymbol()));
                        } else {
                            credito.setEstado(new Categoria(CategoriaEnum.ABIERTO.getSymbol()));
                            LOGGER.log(Level.FINE, "El credito {0} no se ha cancelado.", credito.getId());
                            totalCreditosAbiertos++;
                        }

                        boolean vencido = false;
                        Date fechaDeCancelacion = null;
                        for (Pagare p : credito.getPagares()) {
                            if (p.getActivo().equals('S')) {
                                if (credito.getFechaUltimoPago() != null) {
                                    if (credito.getEstado().getId().equals(CategoriaEnum.CANCELADO.getSymbol())) {
                                        if (credito.getFechaFin().before(credito.getFechaUltimoPago())) {
                                            vencido = true;
                                        }
                                        fechaDeCancelacion = credito.getFechaUltimoPago();
                                    } else if (credito.getFechaFin().before(credito.getUltimaModificacion())) {
                                        vencido = true;
                                    }
                                    p.setFechaDeCancelacion(fechaDeCancelacion);
                                    p.setVencido(vencido);
                                }
                            }
                        }
                        getCreditoFacade().setEntity(credito);
                        getCreditoFacade().guardar();
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        setInfoMessage(null, "Total de Pagos Importados : " + totalPagosImportados + " Total de creditos abiertos: " + totalCreditosAbiertos);
        return "listaventamotos";
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}

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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import py.com.bej.base.prod.entity.Vmcompras;
import py.com.bej.base.prod.entity.Vmmotostock;
import py.com.bej.base.prod.entity.Vmpagomotos;
import py.com.bej.base.prod.entity.Vmplanmoto;
import py.com.bej.base.prod.entity.Vmventamotos;
import py.com.bej.base.prod.session.ComprasProduccionFacade;
import py.com.bej.base.prod.session.PagoMotosProduccionFacade;
import py.com.bej.base.prod.session.PlanMotosProduccionFacade;
import py.com.bej.base.prod.session.VentaMotosProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Factura;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FacturaFacade;
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
        Categoria categoriaFactura;
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
        Short cuotasProduccion;
        Short cantidadItemsProduccion = 1;
        Character activo = 'S';
        Character saldadoProduccion;
        Date fechaUltimoPagoProduccion = new Date();
        Date ultimaModificacion = new Date();
        //Desarrollo
        List<Financiacion> listaCuotasDesarrollo = new ArrayList<Financiacion>();
        Credito credito = null;
        List<Pago> listaPagosDesarrollo;
        //Produccion
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        ventaMotosProduccionFacade = new VentaMotosProduccionFacade();
        facturaFacade = new FacturaFacade();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdVentaOrdenado();
        Financiacion financiacionDesarrollo;
        if (!listaVentasProduccion.isEmpty()) {
            for (Vmventamotos c : listaVentasProduccion) {
                idProduccion = c.getIdVenta();
                LOGGER.log(Level.INFO, "==============================================================");
                LOGGER.log(Level.INFO, "La venta Nro. {0}", c.getIdVenta());
                fechaOperacionProduccion = c.getFechaVenta() == null ? new Date() : c.getFechaVenta();
                fechaEntregaProduccion = c.getFechaEntrega() == null ? new Date() : c.getFechaEntrega();
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
                if (c.getIdTransaccion() == 1) {
                    //Venta a Contado
                    categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCO.getSymbol());
                    facturaDesarrollo.setCategoria(categoriaFactura);
                    cuotasProduccion = 0;
                    listaPagosDesarrollo.add(new Pago(null, fechaOperacionProduccion, null, totalPagadoProduccion, Boolean.FALSE, 'S', new Date()));
                    LOGGER.info("Es Venta CONTADO");
                } else {
                    //Venta Credito
                    List<Vmplanmoto> listaCuotasProduccion = new ArrayList<Vmplanmoto>();
                    montoCuotaIgualProduccion = c.getMontoCuotas() < 0 ? BigDecimal.ZERO : BigDecimal.valueOf(c.getMontoCuotas());
                    BigDecimal capitalProduccion = BigDecimal.valueOf(c.getSaldoMoto());
                    cuotasProduccion = Short.valueOf("" + c.getNumeroCuotas());
                    BigDecimal creditoTotalProduccion = BigDecimal.valueOf(montoCuotaIgualProduccion.intValue() * cuotasProduccion);
                    listaCuotasDesarrollo = new ArrayList<Financiacion>();
                    categoriaFactura = new Categoria(CategoriaEnum.VENTA_MCR.getSymbol());
                    facturaDesarrollo.setCategoria(categoriaFactura);
                    BigDecimal interes = BigDecimal.valueOf(0.26F);

                    credito = new Credito(null, new Categoria(CategoriaEnum.S_PER.getSymbol()), null,
                            c.getFechaVenta(), null, new Categoria(CategoriaEnum.S_ESP.getSymbol()), 0.0F, 0.0F,
                            capitalProduccion, cuotasProduccion, creditoTotalProduccion, BigDecimal.ZERO,
                            BigDecimal.ZERO, BigDecimal.ZERO, fechaUltimoPagoProduccion, new Short("0"),
                            new Categoria(CategoriaEnum.ABIERTO.getSymbol()), 'S', new Date());
                    if (c.getIdCodeudor() != null && c.getIdCodeudor() > 0) {
                        credito.setGarante(getPersonaFacade().findByDocumento(String.valueOf(c.getIdCodeudor())));
                    }

                    //FINANCIACION
                    //Buscar Cuotas
                    listaCuotasProduccion = getPlanMotosProduccionFacade().findByIdVenta(c.getIdVenta());
                    if (!listaCuotasProduccion.isEmpty()) {
                        LOGGER.log(Level.INFO, "Es Venta CREDITO y tiene {0} Cuotas", listaCuotasProduccion.size());
                        BigDecimal totalPagadoHastaAhora = BigDecimal.ZERO;
                        BigDecimal montoCuota;
                        Short cuotaNumero;
                        Date fechaVencimiento;
                        Character activoX;
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
                            if (p.getMontoCuota() != null) {
                                montoCuota = BigDecimal.valueOf(p.getMontoCuota());
                            } else {
                                montoCuota = BigDecimal.ZERO;
                                activoX = 'N';
                            }
                            financiacionDesarrollo = new Financiacion(null, credito, cuotaNumero,
                                    credito.getCapital(), credito.getCapital().multiply(interes),
                                    montoCuota, credito.getCreditoTotal(),
                                    BigDecimal.ZERO, p.getFechaPago(), fechaVencimiento, BigDecimal.valueOf(p.getMontoInteresMensual()),
                                    montoCuotaIgualProduccion, activoX, new Date());
                            totalPagadoHastaAhora = totalPagadoHastaAhora.add(montoCuotaIgualProduccion);
                            if (x == (listaCuotasProduccion.size() - 1)) {
                                credito.setFechaFin(fechaOperacionProduccion);
                                credito.setActivo('N');
                            }
                            Object[] params = new Object[4];
                            params[0] = financiacionDesarrollo.getNumeroCuota();
                            params[1] = financiacionDesarrollo.getCuotaNeta();
                            params[2] = financiacionDesarrollo.getFechaVencimiento();

//                            LOGGER.log(Level.INFO, "{0}__________{1}__________{2}", params);
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
            }
            setInfoMessage(null, "Total de registros importados: " + totalMigrado);
        }


        return "listaventamotos";
    }

    public String migrarPagos() {
        String res = null;
        int totalPagosImportados = 0;
        Transaccion t;
        List<Vmpagomotos> pagosProduccion;
        List<Vmventamotos> listaVentasProduccion = new ArrayList<Vmventamotos>();
        listaVentasProduccion = ventaMotosProduccionFacade.findByIdVentaOrdenado();
        try {
            if (!listaVentasProduccion.isEmpty()) {
                for (Vmventamotos c : listaVentasProduccion) {

                    //Buscar Transaccion
                    t = getFacade().findByNumeroAnterior(c.getIdVenta(), CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol());
                    Credito credito = null;
                    if (!t.getCreditosTransaccion().isEmpty()) {
                        credito = t.getCreditosTransaccion().get(t.getCreditosTransaccion().size() - 1);
                    }
                    //Buscar Pagos
                    pagosProduccion = getPagoMotosProduccionFacade().findByIdVenta(c.getIdVenta());
                    //Registrar Pagos
                    if (!pagosProduccion.isEmpty()) {
                        List<Pago> pagosDesarrollo = new ArrayList<Pago>();
                        boolean esPagoParcial = false;
                        String cadenaParcial = "parcial";
                        String cadenaIniciar = "Iniciar";
                        for (Vmpagomotos pago : pagosProduccion) {
                            if (pago.getConcepto() != null && pago.getConcepto().contains(cadenaParcial)) {
                                esPagoParcial = true;
                            }
                            if (pago.getMontoEntrega() == null || pago.getMontoEntrega() < 1
                                    || pago.getConcepto() == null || pago.getConcepto().contains(cadenaIniciar)) {
                                continue;
                            }
                            Pago pg = new Pago(null, pago.getFechaPago(),
                                    credito, BigDecimal.valueOf(pago.getMontoEntrega()),
                                    esPagoParcial, pago.getAnulado() ? 'N' : 'S', new Date());
                            if (pg.getFecha() == null) {
                                pg.setFecha(new Date());
                                pg.setActivo('N');
                            }
                            pagosDesarrollo.add(pg);
                        }
                        totalPagosImportados = getPagoFacade().cargaMasiva(pagosDesarrollo);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MigrarTransaccionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        setInfoMessage(null, "Total de Pagos Importados : " + totalPagosImportados);
        return "listaventamotos";
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}

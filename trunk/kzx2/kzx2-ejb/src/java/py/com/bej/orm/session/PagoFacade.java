/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.OperationNotSupportedException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Usuario;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;

/**
 *
 * @author diego
 */
@Stateless
public class PagoFacade extends AbstractFacade<Pago> {

    @EJB
    private FinanciacionFacade financiacionFacade;
    private final static Logger LOGGER = Logger.getLogger(PagoFacade.class.getName());

    public PagoFacade() {
        super(Pago.class);
    }

    public FinanciacionFacade getFinanciacionFacade() {
        if (financiacionFacade == null) {
            financiacionFacade = new FinanciacionFacade();
        }
        return financiacionFacade;
    }

    private Pago generarElDetalleDePago(List<Financiacion> cuotasPendientes, BigDecimal importeMaximo) throws Exception {
        Pago res = new Pago();
        BigDecimal importeAcumulado = importeMaximo;
        List<DetallePago> detalle = new ArrayList<DetallePago>();
        BigDecimal porcentajeDescuento = new BigDecimal(ConfiguracionEnum.DESCUENTO_CANCELACION.getSymbol());
        if (!cuotasPendientes.isEmpty()) {
            DetallePago d;
            String colorEstado = null;
            BigDecimal importeCuota;
            BigDecimal aux = BigDecimal.ZERO;
            BigDecimal interesMoratorio;
            BigDecimal porcentajeInteresMoratorio;
            BigDecimal porcentajeInteresMoratorioAcumulado;
            int mesesAtraso = 0;
            boolean pagoParcial = false;
            List<DetallePago> dp = null;
            for (Financiacion f : cuotasPendientes) {
                mesesAtraso = calcularEstadoCuota(f);
                dp = new ArrayList<DetallePago>();
                //Ponerle color al registro
                if (mesesAtraso > 0) {
                    colorEstado = "rojo";
                } else if (mesesAtraso == 0) {
                    colorEstado = "amarillo";
                } else {
                    colorEstado = "verde";
                }
                if (f.getFechaPago() != null && f.getTotalPagado() != null) {
                    //ya tiene un pago parcial
                    pagoParcial = true;
                    importeCuota = f.getCuotaNeta().subtract(f.getTotalPagado());
                } else {
                    importeCuota = f.getTotalAPagar();
                    pagoParcial = false;
                }
                if (mesesAtraso > 0) {
                    //Calcular Interes Moratorio
                    porcentajeInteresMoratorio = BigDecimal.valueOf(f.getCredito().getInteresMoratorio());
                    porcentajeInteresMoratorioAcumulado = porcentajeInteresMoratorio.multiply(BigDecimal.valueOf(mesesAtraso));
                    interesMoratorio = importeCuota.multiply(porcentajeInteresMoratorioAcumulado);
                    interesMoratorio = interesMoratorio.setScale(0, RoundingMode.HALF_DOWN);
                    d = new DetallePago(new Categoria(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol()), res,
                            CategoriaEnum.PAGO_INTERES_MORATORIO.getLabel() + " " + f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                            interesMoratorio, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE, f.getFechaVencimiento(), colorEstado);
                    dp.add(d);
                }
                if (pagoParcial) {
                    d = new DetallePago(new Categoria(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol()), res,
                            CategoriaEnum.PAGO_PARCIAL_CUOTA.getLabel() + " " + f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                            importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE, f.getFechaVencimiento(), colorEstado);
                } else {
                    d = new DetallePago(new Categoria(CategoriaEnum.PAGO_CUOTA.getSymbol()), res,
                            CategoriaEnum.PAGO_CUOTA.getLabel() + " " + f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                            importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE, f.getFechaVencimiento(), colorEstado);
                }
                dp.add(d);
                DetallePago descuentoProcesado = null;
                if (mesesAtraso < -1) {
                    //Proceder al Descuento
                    //Calcular descuento
                    BigDecimal descuento = BigDecimal.ZERO;
                    descuento.setScale(-2, RoundingMode.HALF_DOWN);
                    int mesesDescuento = -(mesesAtraso + 1);
                    descuento = f.getTotalAPagar().multiply(porcentajeDescuento.multiply(BigDecimal.valueOf(mesesDescuento)));
                    //Restarle al descuento el ajuste de redondeo
                    descuento = descuento.add(f.getAjusteRedondeo());
                    //Si el descuento es mayor al interes, descontar solo el interes.
                    int pasaInteres = descuento.compareTo(f.getInteres());
                    if (pasaInteres == 1) {
                        descuento = f.getInteres();
                    }
                    descuento = descuento.negate();
                    descuentoProcesado = new DetallePago(new Categoria(CategoriaEnum.DESCUENTO.getSymbol()), res,
                            CategoriaEnum.DESCUENTO.getLabel(), descuento, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE, f.getFechaVencimiento(), colorEstado);
                }
                if (!dp.isEmpty()) {
                    for (DetallePago det : dp) {
                        //Calculo Auxiliar para guardar el monto antes de ser restado
                        aux = importeAcumulado;
                        if (importeMaximo != null) {//Si el proceso esta definido por el monto maximo permitido
                            if (descuentoProcesado != null) {
                                importeAcumulado = importeAcumulado.subtract(det.getImporte().add(descuentoProcesado.getImporte()));
                            } else {
                                importeAcumulado = importeAcumulado.subtract(det.getImporte());
                            }

                            if (importeAcumulado.compareTo(BigDecimal.ZERO) < 0) {
                                if (det.getCodigo().getId().equals(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol())) {
                                    if (detalle.isEmpty()) {
                                        // Ni siquiera cubre el monto del primer interes moratorio.
                                        throw new OperationNotSupportedException("Para realizar un pago debe por lo menos cancelar el interes acumulado.");
                                    }
                                } else if (det.getCodigo().getId().equals(CategoriaEnum.PAGO_CUOTA.getSymbol())) {
                                    //Pago Parcial
                                    d = new DetallePago(new Categoria(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol()), res,
                                            CategoriaEnum.PAGO_PARCIAL_CUOTA.getLabel() + " " + f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                                            aux, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE, f.getFechaVencimiento(), colorEstado);
                                    detalle.add(d);
                                }
                                res.setDetalle(detalle);
                                return res;
                            }
                        }
                        //Guardar el detalle
                        detalle.add(det);
                        if (descuentoProcesado != null) {
                            detalle.add(descuentoProcesado);
                        }
                        if (importeMaximo != null) {//Si el proceso esta definido por el monto maximo permitido
                            if (importeAcumulado.compareTo(BigDecimal.ZERO) == 0) {
                                break;
                            }
                        }
                    }
                }
                if (importeMaximo != null) {//Si el proceso esta definido por el monto maximo permitido
                    if (importeAcumulado.compareTo(BigDecimal.ZERO) == 0) {
                        break;
                    }
                }
            }
            res.setDetalle(detalle);
        }
        return res;
    }

    public Pago generarElDetalleDelPago(Financiacion f) throws Exception {
        return this.generarElDetalleDePago(Arrays.asList(f), null);
    }

    public static int calcularEstadoCuota(Financiacion f) {
        /*Estado de la cuota
        [-1] - Cuota Atrasada
        [ 0] - Hoy es el dia de vencimiento
        [ 1] - Cuota Adelantada
        [ 2] - Cuota adelantada con descuento
         */
        Calendar c = GregorianCalendar.getInstance();
        Calendar fechaVencimiento = GregorianCalendar.getInstance();
        int mesesAtraso = 0;
        int diasDeGracia = Integer.valueOf(ConfiguracionEnum.DIAS_DE_GRACIA.getSymbol());
        fechaVencimiento.setTime(f.getFechaVencimiento());
        //Fechas
        int anhoHoy = c.get(Calendar.YEAR);
        int mesHoy = c.get(Calendar.MONTH);
        int diaHoy = c.get(Calendar.DAY_OF_MONTH);
        int anhoVencimiento = fechaVencimiento.get(Calendar.YEAR);
        int mesVencimiento = fechaVencimiento.get(Calendar.MONTH);
        int diaVencimiento = fechaVencimiento.get(Calendar.DAY_OF_MONTH);
        //Ubicar el anho
        int diferenciaDeAnho = anhoHoy - anhoVencimiento;
        //Ubicar el mes
        int diferenciaDeMes = mesHoy - mesVencimiento;
        //Ubicar el dia
        int diferenciaDelDia = diaHoy - diaVencimiento;
        //Agregar o sacar meses
        if (diferenciaDeAnho != 0) {
            //calcular la diferencia en meses
            mesesAtraso = diferenciaDeAnho * 12;
        }
        if (diferenciaDelDia > diasDeGracia) {
            if (mesesAtraso == 0 && diferenciaDeMes == 0) {
                mesesAtraso++;
            }
        }
        mesesAtraso = mesesAtraso + diferenciaDeMes;
        LOGGER.log(Level.FINE, "La cuota {0} tiene {1} anhos de diferencia, {2} meses de diferencia, {3} dias de diferencia. TOTAL: {4}", new Object[]{f.getNumeroCuota(), diferenciaDeAnho, diferenciaDeMes, diferenciaDelDia, mesesAtraso});

        return mesesAtraso;
    }

    public Pago generarPagoParaEstarAlDia(Credito credito) {
        Pago res = new Pago();
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, 1);
        //Buscar Cuotas Pendientes
        List<Financiacion> cuotasPendientes = new ArrayList<Financiacion>();
        try {
            cuotasPendientes = getFinanciacionFacade().buscarCuotasPendientesPorCliente(credito.getId(), c.getTime());
            res = generarElDetalleDePago(cuotasPendientes, null);
            res.setCredito(credito);
        } catch (Exception ex) {
            Logger.getLogger(PagoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Pago generarPagoParaCancelarCredito(Credito credito) {
        Pago res = new Pago();
        //Buscar Cuotas Pendientes
        List<Financiacion> cuotasPendientes = new ArrayList<Financiacion>();
        cuotasPendientes = new FinanciacionFacade().buscarCuotasPendientesPorClienteyCredito(
                credito.getTransaccion().getComprador().getId(),
                credito.getId());
        try {
            res = generarElDetalleDePago(cuotasPendientes, null);
            res.setCredito(credito);
        } catch (Exception ex) {
            Logger.getLogger(PagoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Pago generarPagoPorMontoSolicitado(Credito credito, BigDecimal montoSolicitado) throws Exception {
        Pago res = null;
        //Buscar Cuotas Pendientes
        List<Financiacion> cuotasPendientes = new ArrayList<Financiacion>();
        cuotasPendientes = new FinanciacionFacade().buscarCuotasPendientesPorCredito(credito.getId());
        try {
            res = generarElDetalleDePago(cuotasPendientes, montoSolicitado);
            res.setCredito(credito);
        } catch (Exception ex) {
            Logger.getLogger(PagoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;

    }

    public Pago realizarPago(Credito credito, Usuario usuario, BigDecimal importe) {
        Pago res = null;
        //Buscar Cuotas Pendientes
        FinanciacionFacade ff = new FinanciacionFacade();
        List<Financiacion> cuotasPendientes = new ArrayList<Financiacion>();
        cuotasPendientes = ff.buscarCuotasPendientesPorCredito(credito.getId());
        try {
            res = generarElDetalleDePago(cuotasPendientes, importe);
            res.setCredito(credito);
            res.setTotalPagado(importe);
            res.setFecha(new Date());
            res.setActivo('S');
            for (DetallePago dp : res.getDetalle()) {
                if (dp.getSeleccion()) {
                    for (Financiacion f : cuotasPendientes) {
                        if (dp.getNumeroCuota() == f.getNumeroCuota()) {
                            if (dp.getCodigo().getId().equals(CategoriaEnum.PAGO_CUOTA.getSymbol())) {
                                //Pago total de la cuota.
                                f.setTotalPagado(dp.getImporte());
                                f.setCancelado('S');
                                f.setFechaPago(res.getFecha());
                                ff.setEntity(f);
                                ff.guardar();
                                break;
                            } else if (dp.getCodigo().getId().equals(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol())) {
                                //Pago PARCIAL de la cuota.
                                if (f.getTotalPagado() != null) {
                                    f.setTotalPagado(f.getTotalPagado().add(dp.getImporte()));
                                } else {
                                    f.setTotalPagado(dp.getImporte());
                                }
                                if (f.getTotalPagado().compareTo(f.getTotalAPagar()) >= 0) {
                                    f.setCancelado('S');
                                } else {
                                    f.setCancelado('N');
                                }
                                f.setFechaPago(res.getFecha());
                                ff.setEntity(f);
                                ff.guardar();
                                break;
                            } else if (dp.getCodigo().getId().equals(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol())) {
                                //Pago INTERES MORATORIO de la cuota.
                                f.setInteresMora(dp.getImporte());
                                if (f.getInteresMora() != null) {
                                    f.setInteresMora(f.getInteresMora().add(dp.getImporte()));
                                } else {
                                    f.setInteresMora(dp.getImporte());
                                }
                                f.setTotalPagado(f.getTotalAPagar().add(dp.getImporte()));
                                ff.setEntity(f);
                                ff.guardar();
                                break;
                            } else if (dp.getCodigo().getId().equals(CategoriaEnum.DESCUENTO.getSymbol())) {
                                //Pago DESCUENTO de la cuota.
                                f.setDescuento(f.getDescuento().add(dp.getImporte()));
                                f.setTotalPagado(f.getTotalAPagar().add(dp.getImporte()));
                                ff.setEntity(f);
                                ff.guardar();
                                break;
                            }
                        }
                    }
                }
            }
            //Guardar
            res.setUsuarioCreacion(usuario);
            res.setUsuarioModificacion(usuario);
            res.setFechaCreacion(new Date());
            res.setUltimaModificacion(new Date());
            res.setNumeroDocumento("");
            setEntity(res);
            create();
            getEm().flush();
            res.setNumeroDocumento(String.valueOf(res.getId()));
            setEntity(res);
            guardar();
        } catch (Exception ex) {
            Logger.getLogger(PagoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public List<Pago> buscarPagosDesdeHasta(Date desde, Date hasta) {
        List<Pago> res = new ArrayList<Pago>();
        inicio();
        cq.where(cb.between(r.get("fecha"), desde, hasta));
        cq.orderBy(cb.asc(r.get("fecha")));
        TypedQuery q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Pago> findPagoByCredito(Integer credito) {
        List<Pago> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("credito").get("id"), credito), cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("fecha")));
        TypedQuery<Pago> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Pago> findRange() {
        inicio();
        List<Predicate> criteria = predicarCriteria();
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        if (getOrden().getColumna() != null && getOrden().getAsc() != null) {
            if (getOrden().getColumna().equals("cliente")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get("credito").get("transaccion").get("comprador").get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get("credito").get("transaccion").get("comprador").get("nombre")));
                }
            } else if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Pago> q = setearConsulta();
        if (getContador() == null) {
            cq.select(cq.from(getEntityClass()));
            cq.select(cb.count(r.get("id")));
            TypedQuery<Integer> q1 = setearConsulta();
            setContador(Long.parseLong("" + q1.getSingleResult()));
        }
        q.setMaxResults(getRango()[1].intValue());
        q.setFirstResult(getRango()[0].intValue());
        setDesde(getRango()[0]);
        setUltimo(getRango()[0] + getRango()[1] > getContador() ? getContador() : getRango()[0] + getRango()[1]);
        return q.getResultList();
    }

    @Override
    public List<Pago> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0L;
        }
        return findRange();
    }

    @Override
    public List<Pago> siguiente() {
        getRango()[0] += getRango()[1];
        if (getRango()[0] > getContador()) {
            getRango()[0] = getContador() - 1;
        }
        return findRange();
    }

    @Override
    public void guardar() {
        try {
            getEm().merge(getEntity());
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                LOGGER.log(Level.SEVERE, "Constraint Descriptor :", cv.getConstraintDescriptor());
                LOGGER.log(Level.SEVERE, "Invalid Value :", cv.getInvalidValue());
                LOGGER.log(Level.SEVERE, "Root Bean :", cv.getRootBean());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Ocurrio una excepcion al intentar guardar el registro", ex);

        }
    }

    @Override
    public List<Predicate> predicarCriteria() {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (getEntity().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (getEntity().getFecha() != null) {
            ParameterExpression<Date> p =
                    cb.parameter(Date.class, "fecha");
            criteria.add(cb.equal(r.get("fecha"), p));
        }
        if (getEntity().getNumeroDocumento() != null && !getEntity().getNumeroDocumento().trim().equals("")) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "numeroDocumento");
            criteria.add(cb.equal(r.get("numeroDocumento"), p));
        }
        if (getEntity().getCliente() != null && !getEntity().getCliente().trim().equals("")) {
            criteria.add(cb.like(cb.lower(
                    r.get("credito").get("transaccion").get("comprador").get("nombre")), "%"
                    + getEntity().getCliente() + "%"));
        }
        if (getEntity().getCredito().getTransaccion().getId() != null) {
            criteria.add(cb.equal(
                    r.get("credito").get("transaccion").get("id"), getEntity().getCredito().getTransaccion().getId()));
        }
        if (getEntity().getActivo() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "activo");
            criteria.add(cb.equal(r.get("activo"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery setearConsulta() {
        TypedQuery q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getFecha() != null) {
            q.setParameter("fecha", getEntity().getFecha());
        }
        if (getEntity().getNumeroDocumento() != null) {
            q.setParameter("numeroDocumento", getEntity().getNumeroDocumento());
        }
        if (getEntity().getCliente() != null && !getEntity().getCliente().trim().equals("")) {
            q.setParameter("credito.transaccion.comprador.nombre", getEntity().getCliente());
        }
        if (getEntity().getTotalPagado() != null) {
            q.setParameter("totalPagado", getEntity().getTotalPagado());
        }
        if (getEntity().getActivo() != null) {
            q.setParameter("activo", getEntity().getActivo());
        }
        return q;
    }
}

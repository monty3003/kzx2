/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.OperationNotSupportedException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;

/**
 *
 * @author diego
 */
@Stateless
public class PagoFacade extends AbstractFacade<Pago> {

    public PagoFacade() {
        super(Pago.class);
    }

    public Pago generarPagoParaEstarAlDia(Integer cliente) {
        Pago res = new Pago();
        List<DetallePago> detalle = new ArrayList<DetallePago>();
        //Buscar Cuotas Pendientes
        List<Financiacion> cuotasPendientes = new ArrayList<Financiacion>();
        cuotasPendientes = new FinanciacionFacade().buscarCuotasPendientesPorCliente(cliente, new Date());
        if (!cuotasPendientes.isEmpty()) {
            //Hay cuotas vencidas. Generar Interes moratorio
            DetallePago d;
            BigDecimal importeCuota;
            BigDecimal interesMoratorio;
            BigDecimal porcentajeInteresMoratorio;
            BigDecimal porcentajeInteresMoratorioAcumulado;
            int mesesAtraso = 0;
            int anhosAtraso = 0;
            Calendar c = GregorianCalendar.getInstance();
            Calendar fechaVencimiento = GregorianCalendar.getInstance();
            for (Financiacion f : cuotasPendientes) {
                importeCuota = BigDecimal.ZERO;
                interesMoratorio = BigDecimal.ZERO;
                //Fechas
                fechaVencimiento.setTime(f.getFechaVencimiento());
                anhosAtraso = c.get(Calendar.YEAR) - fechaVencimiento.get(Calendar.YEAR);
                mesesAtraso = c.get(Calendar.MONTH) - fechaVencimiento.get(Calendar.MONTH);
                if (anhosAtraso > 0) {
                    //Ya pasaron anhos 
                    mesesAtraso = mesesAtraso + (anhosAtraso * 12);
                }
                porcentajeInteresMoratorio = BigDecimal.valueOf(f.getCredito().getInteresMoratorio());
                porcentajeInteresMoratorioAcumulado = porcentajeInteresMoratorio.multiply(BigDecimal.valueOf(mesesAtraso));
                boolean pagoParcial = false;
                if (f.getFechaPago() != null && f.getTotalPagado() != null) {
                    //ya tiene un pago parcial
                    pagoParcial = true;
                    importeCuota = f.getCuotaNeta().subtract(f.getTotalPagado());
                } else {
                    importeCuota = f.getTotalAPagar();
                }
                interesMoratorio = importeCuota.multiply(porcentajeInteresMoratorioAcumulado);
                d = new DetallePago(new Categoria(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol()), res,
                        f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                        interesMoratorio, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                detalle.add(d);
                if (pagoParcial) {
                    d = new DetallePago(new Categoria(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol()), res,
                            f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                            importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                } else {
                    d = new DetallePago(new Categoria(CategoriaEnum.PAGO_CUOTA.getSymbol()), res,
                            f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                            importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                }
                detalle.add(d);
            }
        } else {
            cuotasPendientes = new FinanciacionFacade().buscarProximaCuotaAVencer(cliente, new Date());
            if (!cuotasPendientes.isEmpty()) {
                for (Financiacion f : cuotasPendientes) {
                    detalle.add(new DetallePago(new Categoria(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol()), res,
                            f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                            f.getTotalAPagar(), f.getNumeroCuota(), 'S', new Date(), Boolean.FALSE));
                }
            }
        }
        res.setDetalle(detalle);
        return res;
    }

    public Pago generarPagoParaCancelarCredito(Integer cliente) {
        Pago res = new Pago();
        List<DetallePago> detalle = new ArrayList<DetallePago>();
        BigDecimal porcentajeDescuento = new BigDecimal(ConfiguracionEnum.DESCUENTO_CANCELACION.getSymbol());
        //Buscar Cuotas Pendientes
        List<Financiacion> cuotasPendientes = new ArrayList<Financiacion>();
        cuotasPendientes = new FinanciacionFacade().buscarCuotasPendientesPorCliente(cliente);
        if (!cuotasPendientes.isEmpty()) {
            for (Financiacion cuotaPendiente : cuotasPendientes) {
                if (cuotaPendiente.getFechaVencimiento().before(new Date())) {
                    //Hay cuotas vencidas. Generar Interes moratorio
                    DetallePago d;
                    BigDecimal importeCuota;
                    BigDecimal interesMoratorio;
                    BigDecimal porcentajeInteresMoratorio;
                    BigDecimal porcentajeInteresMoratorioAcumulado;
                    int mesesAtraso = 0;
                    int anhosAtraso = 0;
                    Calendar c = GregorianCalendar.getInstance();
                    Calendar fechaVencimiento = GregorianCalendar.getInstance();
                    for (Financiacion f : cuotasPendientes) {
                        importeCuota = BigDecimal.ZERO;
                        interesMoratorio = BigDecimal.ZERO;
                        //Fechas
                        fechaVencimiento.setTime(f.getFechaVencimiento());
                        anhosAtraso = c.get(Calendar.YEAR) - fechaVencimiento.get(Calendar.YEAR);
                        mesesAtraso = c.get(Calendar.MONTH) - fechaVencimiento.get(Calendar.MONTH);
                        if (anhosAtraso > 0) {
                            //Ya pasaron anhos 
                            mesesAtraso = mesesAtraso + (anhosAtraso * 12);
                        }
                        porcentajeInteresMoratorio = BigDecimal.valueOf(f.getCredito().getInteresMoratorio());
                        porcentajeInteresMoratorioAcumulado = porcentajeInteresMoratorio.multiply(BigDecimal.valueOf(mesesAtraso));
                        boolean pagoParcial = false;
                        if (f.getFechaPago() != null && f.getTotalPagado() != null) {
                            //ya tiene un pago parcial
                            pagoParcial = true;
                            importeCuota = f.getCuotaNeta().subtract(f.getTotalPagado());
                        } else {
                            importeCuota = f.getTotalAPagar();
                        }
                        interesMoratorio = importeCuota.multiply(porcentajeInteresMoratorioAcumulado);
                        d = new DetallePago(new Categoria(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol()), res,
                                f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                                interesMoratorio, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                        detalle.add(d);
                        if (pagoParcial) {
                            d = new DetallePago(new Categoria(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol()), res,
                                    f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                                    importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                        } else {
                            d = new DetallePago(new Categoria(CategoriaEnum.PAGO_CUOTA.getSymbol()), res,
                                    f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                                    importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                        }
                        detalle.add(d);
                    }
                } else {
                    //Proceder al Descuento
                    cuotaPendiente.setTotalAPagar(cuotaPendiente.getTotalAPagar().subtract(cuotaPendiente.getTotalAPagar().multiply(porcentajeDescuento)));
                    detalle.add(new DetallePago(new Categoria(CategoriaEnum.PAGO_CUOTA.getSymbol()), res,
                            cuotaPendiente.getNumeroCuota() + "/" + cuotaPendiente.getCredito().getAmortizacion(),
                            cuotaPendiente.getTotalAPagar(), cuotaPendiente.getNumeroCuota(), 'S', new Date(), Boolean.TRUE));
                }
            }
        }
        res.setDetalle(detalle);
        return res;
    }

    public Pago generarPagoPorMontoSolicitado(Integer cliente, BigDecimal montoSolicitado) throws Exception {
        Pago res = null;
        List<DetallePago> detalle = new ArrayList<DetallePago>();
        BigDecimal montoAcumulado = BigDecimal.ZERO;
        BigDecimal montoSolicitadoSaldo = montoSolicitado;
        //Buscar Cuotas Pendientes
        List<Financiacion> cuotasPendientes = new ArrayList<Financiacion>();
        cuotasPendientes = new FinanciacionFacade().buscarCuotasPendientesPorCliente(cliente);
        if (!cuotasPendientes.isEmpty()) {
            for (Financiacion cuotaPendiente : cuotasPendientes) {
                if (cuotaPendiente.getFechaVencimiento().before(new Date())) {
                    //Hay cuotas vencidas. Generar Interes moratorio
                    DetallePago d;
                    BigDecimal importeCuota;
                    BigDecimal interesMoratorio;
                    BigDecimal porcentajeInteresMoratorio;
                    BigDecimal porcentajeInteresMoratorioAcumulado;
                    int mesesAtraso = 0;
                    int anhosAtraso = 0;
                    Calendar c = GregorianCalendar.getInstance();
                    Calendar fechaVencimiento = GregorianCalendar.getInstance();
                    for (Financiacion f : cuotasPendientes) {
                        importeCuota = BigDecimal.ZERO;
                        interesMoratorio = BigDecimal.ZERO;
                        //Fechas
                        fechaVencimiento.setTime(f.getFechaVencimiento());
                        anhosAtraso = c.get(Calendar.YEAR) - fechaVencimiento.get(Calendar.YEAR);
                        mesesAtraso = c.get(Calendar.MONTH) - fechaVencimiento.get(Calendar.MONTH);
                        if (anhosAtraso > 0) {
                            //Ya pasaron anhos 
                            mesesAtraso = mesesAtraso + (anhosAtraso * 12);
                        }
                        porcentajeInteresMoratorio = BigDecimal.valueOf(f.getCredito().getInteresMoratorio());
                        porcentajeInteresMoratorioAcumulado = porcentajeInteresMoratorio.multiply(BigDecimal.valueOf(mesesAtraso));
                        boolean pagoParcial = false;
                        if (f.getFechaPago() != null && f.getTotalPagado() != null) {
                            //ya tiene un pago parcial
                            pagoParcial = true;
                            importeCuota = f.getCuotaNeta().subtract(f.getTotalPagado());
                        } else {
                            importeCuota = f.getTotalAPagar();
                        }
                        interesMoratorio = importeCuota.multiply(porcentajeInteresMoratorioAcumulado);
                        d = new DetallePago(new Categoria(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol()), res,
                                f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                                interesMoratorio, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                        montoAcumulado = montoAcumulado.add(interesMoratorio);
                        montoSolicitadoSaldo = montoSolicitadoSaldo.subtract(importeCuota);
                        if (montoAcumulado.equals(montoAcumulado.min(montoSolicitado))) {
                            detalle.add(d);
                        } else {
                            throw new OperationNotSupportedException("Para realizar un pago debe por lo menos cancelar el interes acumulado.");
                        }
                        if (pagoParcial) {
                            d = new DetallePago(new Categoria(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol()), res,
                                    f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                                    importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                        } else {
                            d = new DetallePago(new Categoria(CategoriaEnum.PAGO_CUOTA.getSymbol()), res,
                                    f.getNumeroCuota() + "/" + f.getCredito().getAmortizacion(),
                                    importeCuota, f.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                        }
                        montoAcumulado = montoAcumulado.add(importeCuota);
                        if (montoAcumulado.equals(montoAcumulado.max(montoSolicitado))) {
                            if (pagoParcial) {
                                d.setImporte(importeCuota);
                            }
                        }
                        detalle.add(d);
                    }
                } else {
                    //Proceder al Descuento
                    DetallePago d = new DetallePago(new Categoria(CategoriaEnum.PAGO_CUOTA.getSymbol()), res,
                            cuotaPendiente.getNumeroCuota() + "/" + cuotaPendiente.getCredito().getAmortizacion(),
                            cuotaPendiente.getTotalAPagar(), cuotaPendiente.getNumeroCuota(), 'S', new Date(), Boolean.TRUE);
                    montoAcumulado.add(cuotaPendiente.getTotalAPagar());
                    if (montoAcumulado.equals(montoAcumulado.min(montoSolicitado))) {
                        detalle.add(d);
                    }
                }
            }
        }
        if (res != null) {
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
        throw new UnsupportedOperationException("Not supported yet.");
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

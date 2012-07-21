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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.DetallePago;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.utils.CategoriaEnum;

/**
 *
 * @author diego
 */
@Stateless
public class FinanciacionFacade extends AbstractFacade<Financiacion> {

    @EJB
    private DetallePagoFacade detallePagoFacade;
    private final static Logger LOGGER = Logger.getLogger(FinanciacionFacade.class.getName());

    public FinanciacionFacade() {
        super(Financiacion.class);
    }

    public DetallePagoFacade getDetallePagoFacade() {
        if (detallePagoFacade == null) {
            detallePagoFacade = new DetallePagoFacade();
        }
        return detallePagoFacade;
    }

    public List<Financiacion> findByTransaccion(Integer id) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("credito").get("transaccion").get("id"), id), cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public Long findCantidadByTransaccion(Integer id) {
        Long res = null;
        inicio();
        cq.select(cb.count(r.get("id")));
        cq.where(cb.and(cb.equal(r.get("credito").get("transaccion").get("id"), id), cb.equal(r.get("activo"), 'S')));
        TypedQuery<Long> q = getEm().createQuery(cq);
        res = (Long) q.getSingleResult();
        return res;
    }

    public List<Financiacion> findByCredito(Integer id) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("credito").get("id"), id), cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Financiacion> buscarCuotasProximasAVencer(int codigoTransaccionDesde, int codigoTransaccionHasta) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(cb.greaterThanOrEqualTo(r.get("credito").get("transaccion").get("codigo").get("id"), codigoTransaccionDesde),
                cb.lessThanOrEqualTo(r.get("credito").get("transaccion").get("codigo").get("id"), codigoTransaccionHasta),
                cb.equal(r.get("cancelado"), 'N'),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("fechaVencimiento")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        q.setMaxResults(10);
        res = q.getResultList();
        return res;
    }

    public List<Financiacion> buscarCuotasPendientesPorCliente(Integer credito, Date fechaLimite) throws Exception {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("id"), credito),
                cb.equal(r.get("cancelado"), 'N'),
                cb.and(cb.or(cb.lessThan(r.get("fechaVencimiento"), fechaLimite), cb.equal(r.get("fechaVencimiento"), fechaLimite))),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        if (res == null || res.isEmpty()) {
            res = Arrays.asList(buscarProximaCuotaAVencer(credito));
        }
        return res;
    }

    public Financiacion buscarProximaCuotaAVencerPorCliente(Integer cliente, Date fechaLimite) throws Exception {
        Financiacion res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("transaccion").get("comprador").get("id"), cliente),
                cb.equal(r.get("cancelado"), 'N'),
                cb.and(cb.or(cb.greaterThan(r.get("fechaVencimiento"), fechaLimite), cb.equal(r.get("fechaVencimiento"), fechaLimite))),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        q.setMaxResults(1);
        res = q.getSingleResult();
        return res;
    }

    public List<Financiacion> buscarCuotasPendientesPorCredito(Integer credito) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("id"), credito),
                cb.equal(r.get("cancelado"), 'N'),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Financiacion> buscarCuotasPendientesPorClienteyCredito(Integer cliente, Integer credito) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("id"), credito),
                cb.equal(r.get("cancelado"), 'N'),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public Financiacion buscarProximaCuotaAVencer(Integer credito) throws Exception {
        Financiacion res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("id"), credito),
                cb.equal(r.get("cancelado"), 'N'),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        q.setFirstResult(0);
        q.setMaxResults(1);
        res = q.getSingleResult();
        return res;
    }

    public Financiacion cancelarCuotaConPagoAsignado(Financiacion f) {
        Pago pago = null;
        List<DetallePago> detallePago = null;
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalPagado = BigDecimal.ZERO;
        if (f.getPagoAsignado() != null) {
            pago = new PagoFacade().find(f.getPagoAsignado());
            detallePago = pago.getDetalle();
        } else {
            detallePago = getDetallePagoFacade().findByNroCuota(f);
            if (detallePago != null && !detallePago.isEmpty()) {
                pago = detallePago.get(0).getPago();
            } else {
                return f;
            }
        }
        if (detallePago != null && !detallePago.isEmpty()) {
            for (DetallePago dp : detallePago) {
                if (dp.getNumeroCuota() == f.getNumeroCuota()) {
                    totalPagado = totalPagado.add(dp.getImporte());
                    if (dp.getCodigo().getId().equals(CategoriaEnum.PAGO_INTERES_MORATORIO.getSymbol())) {
                        f.setInteresMora(f.getInteresMora().add(dp.getImporte()));
                    } else if (dp.getCodigo().getId().equals(CategoriaEnum.DESCUENTO.getSymbol())) {
                        f.setDescuento(f.getDescuento().add(dp.getImporte()));
                    } else if (dp.getCodigo().getId().equals(CategoriaEnum.PAGO_CUOTA.getSymbol())
                            || dp.getCodigo().getId().equals(CategoriaEnum.PAGO_PARCIAL_CUOTA.getSymbol())) {
                        f.setTotalPagado(f.getTotalPagado().add(dp.getImporte()));
                        total = total.add(dp.getImporte());
                    }
                }
            }
            f.setTotalPagado(totalPagado);
            f.setFechaPago(pago.getFecha());
            if (f.getTotalAPagar().compareTo(total) == 0) {
                f.setCancelado('S');
            } else {
                f.setCancelado('N');
            }
        }
        return f;
    }

    public List<Financiacion> buscarCuotasQueVencen(Date fechaDesde, Date fechaHasta, int maxResults) throws Exception {
        List<Financiacion> res = null;
        inicio();
        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(r.get("cancelado"), 'N'));
        criteria.add(cb.equal(r.get("activo"), 'S'));
        if (fechaDesde != null) {
            criteria.add(cb.between(r.get("fechaVencimiento"), fechaDesde, fechaHasta));
        } else {
            criteria.add(cb.lessThanOrEqualTo(r.get("fechaVencimiento"), fechaHasta));
        }
        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        cq.orderBy(cb.desc(r.get("fechaVencimiento")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        res = q.getResultList();
        return res;
    }

    public List<Financiacion> buscarCuotasPorEstado(Date fechaDesde, Date fechaHasta, Character cancelado) {
        List<Financiacion> res = null;
        inicio();
        List<Predicate> criteria = new ArrayList<Predicate>();
        criteria.add(cb.equal(r.get("cancelado"), cancelado));
        criteria.add(cb.equal(r.get("activo"), 'S'));
        if (fechaHasta != null) {
            criteria.add(cb.between(r.get("fechaPago"), fechaDesde, fechaHasta));
        } else {
            criteria.add(cb.lessThanOrEqualTo(r.get("fechaPago"), fechaDesde));
        }
        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        cq.orderBy(cb.desc(r.get("fechaPago")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public void cancelarCuotas() throws Exception {
        List<Financiacion> cuotas = findByTransaccion(Integer.SIZE);
    }

    @Override
    public List<Financiacion> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Financiacion> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Financiacion> siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void guardarCambiosEnCuotas(List<Financiacion> listaCuotas) {
        for (Financiacion f : listaCuotas) {
            setEntity(f);
            guardar();
        }
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypedQuery<Financiacion> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Financiacion> crearCuotas(Credito credito) {
        List<Financiacion> res = new ArrayList<Financiacion>();
        //FERIADOS
        Calendar unoEnero = new GregorianCalendar(2012, Calendar.JANUARY, 1);
        Calendar unoMayo = new GregorianCalendar(2012, Calendar.MAY, 1);
        Calendar navidad = new GregorianCalendar(2012, Calendar.DECEMBER, 25);
        Motostock motoVendida = null;
        try {
            motoVendida = new MotostockFacade().findByVenta(credito.getTransaccion().getId());
        } catch (Exception ex) {
            Logger.getLogger(FinanciacionFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        int indiceRedondeo = motoVendida.getPlan().getIndiceRedondeo();
        BigDecimal saldoCapital = credito.getCapital();
        int contarCuotas = credito.getAmortizacion();
        BigDecimal cuotaCapital = saldoCapital.divide(BigDecimal.valueOf(contarCuotas), 0, RoundingMode.HALF_DOWN);
        BigDecimal interesAcumulado = BigDecimal.valueOf((credito.getTan() * contarCuotas) / (contarCuotas >= 12 ? 12 : 1)).setScale(4, RoundingMode.HALF_DOWN);
        BigDecimal cuotaInteres = cuotaCapital.multiply(interesAcumulado).setScale(0, RoundingMode.HALF_DOWN);
        BigDecimal cuotaNeta = cuotaCapital.add(cuotaInteres);
        BigDecimal totalAPagar = cuotaCapital.add(cuotaInteres).setScale(indiceRedondeo, RoundingMode.HALF_UP);
        BigDecimal ajusteRedondeo = totalAPagar.subtract(cuotaNeta);
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTime(credito.getFechaInicio());
        if (credito.getTransaccion().getCodigo().getId().equals(CategoriaEnum.VENTA_MCR_CORRIDAS.getSymbol())) {
            //Ajustar para que la primera cuota corrida actue como entrega inicial
            fecha.add(Calendar.DAY_OF_MONTH, -30);
        }
        int dia_vencimiento = fecha.get(Calendar.DAY_OF_MONTH);
        for (short i = 0; i < credito.getAmortizacion(); i++) {
            Financiacion f = new Financiacion();
            f.setCancelado('N');
            f.setInteresMora(BigDecimal.ZERO);
            f.setDescuento(BigDecimal.ZERO);
            f.setCredito(credito);
            f.setNumeroCuota((short) (i + 1));
            f.setCuotaNeta(cuotaNeta);
            f.setTotalAPagar(totalAPagar);
            f.setAjusteRedondeo(ajusteRedondeo);
            fecha.add(Calendar.DAY_OF_MONTH, 30);
            int diferencia = dia_vencimiento - fecha.get(Calendar.DAY_OF_MONTH);
            fecha.add(Calendar.DAY_OF_MONTH, diferencia);
            if (fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                fecha.add(Calendar.DAY_OF_MONTH, -1);
            }
            if (fecha.get(Calendar.DAY_OF_MONTH) == unoEnero.get(Calendar.DAY_OF_MONTH)
                    || fecha.get(Calendar.DAY_OF_MONTH) == unoMayo.get(Calendar.DAY_OF_MONTH)
                    || fecha.get(Calendar.DAY_OF_MONTH) == navidad.get(Calendar.DAY_OF_MONTH)) {
                fecha.add(Calendar.DAY_OF_MONTH, -2);
            }
            f.setFechaVencimiento(fecha.getTime());
            f.setCapital(cuotaCapital);
            f.setInteres(cuotaInteres);
            f.setAjusteRedondeo(ajusteRedondeo);
            f.setActivo('S');
            f.setUltimaModificacion(new Date());
            Logger.getLogger(FinanciacionFacade.class.getName()).log(Level.INFO, "_{0}______________{1}_______{2}______{3}_____{4}_____{5}",
                    new Object[]{f.getNumeroCuota(), f.getFechaVencimiento(), f.getCapital(), f.getInteres(), f.getAjusteRedondeo(), f.getTotalAPagar()});
            res.add(f);
        }
        return res;
    }

    public Financiacion buscarLaCuotaPendienteMasAntigua() {
        Financiacion res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("activo"), 'S'),
                cb.equal(r.get("cancelado"), 'N')));
        cq.orderBy(cb.asc(r.get("fechaVencimiento")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        q.setFirstResult(0);
        q.setMaxResults(1);
        res = q.getSingleResult();
        return res;

    }

    public Financiacion buscarLaCuotaPendientePorFechaVencimiento(Boolean asc) {
        Financiacion res = null;
//        Calendar c = new GregorianCalendar(2010, 0, 1);
        inicio();
//        cq.where(cb.and(cb.equal(r.get("activo"), 'S'),
//                cb.equal(r.get("cancelado"), 'N'),
//                cb.greaterThanOrEqualTo(r.get("fechaVencimiento"), c.getTime())));
        cq.where(cb.and(cb.equal(r.get("activo"), 'S'),
                cb.equal(r.get("cancelado"), 'N')));
        if (asc) {
            cq.orderBy(cb.asc(r.get("fechaVencimiento")));
        } else {
            cq.orderBy(cb.desc(r.get("fechaVencimiento")));
        }
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        q.setFirstResult(0);
        q.setMaxResults(1);
        res = q.getSingleResult();
        return res;

    }
}

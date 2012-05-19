/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.utils.CategoriaEnum;

/**
 *
 * @author diego
 */
@Stateless
public class FinanciacionFacade extends AbstractFacade<Financiacion> {

    private final static Logger LOGGER = Logger.getLogger(FinanciacionFacade.class.getName());

    public FinanciacionFacade() {
        super(Financiacion.class);
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

    public List<Financiacion> buscarCuotasPendientesPorCliente(Integer cliente, Date fechaLimite) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("transaccion").get("comprador").get("id"), cliente),
                cb.equal(r.get("cancelado"), 'N'),
                cb.and(cb.or(cb.lessThan(r.get("fechaVencimiento"), fechaLimite), cb.equal(r.get("fechaVencimiento"), fechaLimite))),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Financiacion> buscarCuotasPendientesPorCliente(Integer cliente) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("transaccion").get("comprador").get("id"), cliente),
                cb.equal(r.get("cancelado"), 'N'),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Financiacion> buscarProximaCuotaAVencer(Integer cliente, Date fechaLimite) {
        List<Financiacion> res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("credito").get("transaccion").get("comprador").get("id"), cliente),
                cb.equal(r.get("cancelado"), 'N'),
                cb.and(cb.greaterThan(r.get("fechaVencimiento"), fechaLimite)),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numeroCuota")));
        TypedQuery<Financiacion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public Financiacion cancelarCuotaConPagoAsignado(Financiacion f) {
        Pago pago = null;
        pago = new PagoFacade().find(f.getPagoAsignado());
        if (pago != null) {
            f.setTotalPagado(pago.getTotalPagado());
            f.setFechaPago(pago.getFecha());
            f.setCancelado('S');
        }
        return f;
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

        BigDecimal saldoCapital = credito.getCapital();
        int contarCuotas = credito.getAmortizacion();
        BigDecimal saldoFinal = saldoCapital;
        BigDecimal interesAnualEfectivo = new BigDecimal(credito.getTae());
        BigDecimal cuotaCapital = saldoCapital.divide(BigDecimal.valueOf(contarCuotas), 0, RoundingMode.HALF_DOWN);
        BigDecimal interesAnualNeto = new BigDecimal(credito.getTan());
        BigDecimal cuotaInteres = cuotaCapital.multiply(interesAnualNeto).setScale(0, RoundingMode.HALF_DOWN);
        BigDecimal cuotaNeta = cuotaCapital.add(cuotaInteres);
        BigDecimal totalAPagar = credito.getTransaccion().getMontoCuotaIgual();
        BigDecimal ajusteRedondeo = totalAPagar.subtract(cuotaNeta);
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTime(credito.getFechaInicio());
        int dia_vencimiento = fecha.get(Calendar.DAY_OF_MONTH);
        BigDecimal cuotaExacta;
        for (short i = 0; i < credito.getAmortizacion(); i++) {
            Financiacion f = new Financiacion();
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
            if (credito.getCategoria().getId().equals(CategoriaEnum.S_ALE.getSymbol())) {
                f.setCapital(credito.getCapital());
                f.setInteres(BigDecimal.ZERO);
            } else if (credito.getSistemaCredito().getId().equals(CategoriaEnum.S_FRANCES.getSymbol())) {
                BigDecimal x = BigDecimal.ONE.add(interesAnualEfectivo);
                BigDecimal y = x.pow(contarCuotas, MathContext.DECIMAL64);
                BigDecimal z = y.subtract(BigDecimal.ONE);
                BigDecimal aux = z.divide(interesAnualEfectivo, 8, RoundingMode.HALF_UP);
                BigDecimal result = saldoFinal.divide(aux, 8, RoundingMode.HALF_UP);
                f.setCapital(result);
                f.setInteres(cuotaNeta.subtract(result));
                saldoFinal = saldoFinal.subtract(result);
                contarCuotas--;
            } else {
                f.setCapital(cuotaCapital);
                f.setInteres(cuotaInteres);
                f.setAjusteRedondeo(ajusteRedondeo);
            }
            f.setActivo('S');
            f.setUltimaModificacion(new Date());
            Logger.getLogger(FinanciacionFacade.class.getName()).log(Level.INFO, "_{0}______________{1}_______{2}______{3}_____{4}_____{5}",
                    new Object[]{f.getNumeroCuota(), f.getFechaVencimiento(), f.getCapital(), f.getInteres(), f.getAjusteRedondeo(), f.getTotalAPagar()});
            res.add(f);
        }
        return res;
    }
}

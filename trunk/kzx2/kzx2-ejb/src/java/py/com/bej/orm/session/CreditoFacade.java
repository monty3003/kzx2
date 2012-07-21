/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.utils.CategoriaEnum;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Calificacion;
import py.com.bej.orm.utils.CalificacionEnum;

/**
 *
 * @author diego
 */
@Stateless
public class CreditoFacade extends AbstractFacade<Credito> {

    @EJB
    private CalificacionFacade calificacionFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private FinanciacionFacade financiacionFacade;

    public CreditoFacade() {
        super(Credito.class);
    }

    public List<Credito> findByCliente(String documento) throws Exception {
        List<Credito> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("transaccion").get("comprador").get("documento"), documento), cb.equal(r.get("activo"), "S")));
        TypedQuery<Credito> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Credito> findByClienteYEstado(String documento, Integer estado) throws Exception {
        List<Credito> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("transaccion").get("comprador").get("documento"), documento),
                cb.equal(r.get("activo"), "S"), cb.equal(r.get("estado").get("id"), estado)));
        TypedQuery<Credito> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public Credito findByTransaccion(Integer transaccion) throws Exception {
        Credito res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("transaccion").get("id"), transaccion)));
        TypedQuery<Credito> q = getEm().createQuery(cq);
        res = q.getSingleResult();
        return res;
    }

    public List<Credito> findByEstado(Integer estado) throws Exception {
        List<Credito> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("estado").get("id"), estado),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("fechaInicio")));
        TypedQuery<Credito> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Credito> findByEstadoYAnho(Integer estado, Integer anhoInicio) throws Exception {
        Calendar c = new GregorianCalendar(anhoInicio, 0, 1);
        List<Credito> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("estado").get("id"), estado),
                cb.equal(r.get("activo"), 'S'),
                cb.greaterThanOrEqualTo(r.get("fechaInicio"), c.getTime())));
        cq.orderBy(cb.asc(r.get("fechaInicio")));
        TypedQuery<Credito> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Credito> obtenerCreditosAbiertosPorPeriodoDeCuotas(int periodo) {
        Calendar ahora = new GregorianCalendar();
        Calendar vencimiento = new GregorianCalendar(ahora.get(Calendar.YEAR), ahora.get(Calendar.MONTH), ahora.get(Calendar.DAY_OF_MONTH));
        List<Credito> res = null;
        String sql = "select f.credito from Financiacion f where f.cancelado = 'N' and f.activo = 'S'";
        switch (periodo) {
            case 1: {
                sql = sql + " and f.numeroCuota <=6";
                break;
            }
            case 2: {
                sql = sql + " and f.numeroCuota >6 and f.numeroCuota <=12";
                break;
            }
            case 3: {
                sql = sql + " and f.numeroCuota >12 and f.numeroCuota <=18";
                break;
            }
            case 4: {
                sql = sql + " and f.numeroCuota >18 and f.numeroCuota <=25";
                break;
            }
            case 5: {
                vencimiento.add(Calendar.MONTH, -3);
                sql = sql + " and f.fechaVencimiento =:fechaVencimiento";
                break;
            }
            case 6: {
                vencimiento.add(Calendar.MONTH, -3);
                sql = sql + " and f.fechaVencimiento <:fechaVencimiento";
                break;
            }
        }
        sql = sql + " and f.credito.activo = 'S' and f.credito.estado.id =:estadoCredito group by f.credito order by f.fechaVencimiento asc";
        Query q = getEm().createQuery(sql);
        if (periodo > 4) {
            q.setParameter("fechaVencimiento", vencimiento.getTime());
        }
        q.setParameter("estadoCredito", CategoriaEnum.ABIERTO.getSymbol());
        res = q.getResultList();
        return res;
    }

    public Credito procesarEstadoCredito(Integer id) {
        Credito res = find(id);
        //Buscar Financiacion
        List<Financiacion> cuotas = res.getFinanciacions();
        if (!cuotas.isEmpty()) {
            BigDecimal interesMoratorio = BigDecimal.ZERO;
            int meses = 0;
            int calificacion = 0;
            short cuotasAtrasadas = 0;
            boolean cancelado = true;
            for (Financiacion f : cuotas) {
                if (f.getCancelado() == 'N') {
                    cuotasAtrasadas++;
                    meses = PagoFacade.calcularEstadoCuota(f);
                    interesMoratorio = BigDecimal.ZERO;
                    cancelado = false;
                    if (meses > 2) {
                        calificacion = CalificacionEnum.INSUFICIENTE.getValor();
                    } else if (meses > 1) {
                        calificacion = calificacion > CalificacionEnum.ACEPTABLE.getValor() ? CalificacionEnum.ACEPTABLE.getValor() : calificacion;
                    } else if (meses == 1) {
                        calificacion = calificacion > CalificacionEnum.BUENO.getValor() ? CalificacionEnum.BUENO.getValor() : calificacion;
                    } else if (meses == 0) {
                        calificacion = calificacion > CalificacionEnum.MUY_BUENO.getValor() ? CalificacionEnum.MUY_BUENO.getValor() : calificacion;
                        cuotasAtrasadas--;
                    } else {
                        calificacion = calificacion > CalificacionEnum.EXCELENTE.getValor() ? CalificacionEnum.EXCELENTE.getValor() : calificacion;
                        cuotasAtrasadas--;
                    }
                    if (meses > 0) {
                        interesMoratorio = BigDecimal.valueOf((res.getInteresMoratorio() * meses) + 1).setScale(4, RoundingMode.UP);
                        f.setInteresMora(f.getTotalAPagar().multiply(interesMoratorio).setScale(0));
                    }
                }
            }
            if (cancelado) {
                res.setEstado(new Categoria(CategoriaEnum.CANCELADO.getSymbol()));
            } else if (calificacion < 4) {
                //Credito en mora
                if (res.getEstado().getId() < CategoriaEnum.EN_MORA.getSymbol()) {
                    res.setEstado(new Categoria(CategoriaEnum.EN_MORA.getSymbol()));
                }
            }
            //Actualizar todo
            res.setCuotasAtrasadas(cuotasAtrasadas);
            res.getTransaccion().getComprador().getCalificacion().setCalificacion(calificacion);
            setEntity(res);
            guardar();
            //Generar la calificacion
            calificacionFacade = new CalificacionFacade();
            Calificacion c = null;
            c = calificacionFacade.find(res.getTransaccion().getComprador());
            if (c != null) {
                c.setCalificacion(calificacion);
                calificacionFacade.setEntity(c);
                calificacionFacade.guardar();
            } else {
                c = new Calificacion(res.getTransaccion().getComprador(), calificacion, 'S', new Date());
                calificacionFacade.setEntity(c);
                calificacionFacade.create();
            }
        }
        return res;
    }

    public CategoriaFacade getCategoriaFacade() {
        if (categoriaFacade == null) {
            categoriaFacade = new CategoriaFacade();
        }
        return categoriaFacade;
    }

    public FinanciacionFacade getFinanciacionFacade() {
        if (financiacionFacade == null) {
            financiacionFacade = new FinanciacionFacade();
        }
        return financiacionFacade;
    }

    @Override
    public List<Credito> findRange() {
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
            if (getOrden().getColumna().equals("estado")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("descripcion")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("descripcion")));
                }
            } else if (getOrden().getColumna().equals("cliente")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get("transaccion").get("comprador").get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get("transaccion").get("comprador").get("nombre")));
                }
            } else if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Credito> q = setearConsulta();
        if (getContador() == null) {
            if (getEntity().getId() != null) {
                setContador(Long.parseLong("1"));
            } else {
                cq.select(cq.from(getEntityClass()));
                cq.select(cb.count(r.get("id")));
                TypedQuery<Integer> q1 = setearConsulta();
                setContador(Long.parseLong("" + q1.getSingleResult()));
            }
        }
        q.setMaxResults(getRango()[1].intValue());
        q.setFirstResult(getRango()[0].intValue());
        setDesde(getRango()[0]);
        setUltimo(getRango()[0] + getRango()[1] > getContador() ? getContador() : getRango()[0] + getRango()[1]);
        return q.getResultList();
    }

    @Override
    public List<Credito> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0L;
        }
        return findRange();
    }

    @Override
    public List<Credito> siguiente() {
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
            Logger.getLogger(CreditoFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                Logger.getLogger(MotostockFacade.class.getName()).log(Level.SEVERE, "{0},{1},{2}",
                        new Object[]{cv.getConstraintDescriptor(), cv.getMessageTemplate(), cv.getMessage()});
            }
        } catch (Exception ex) {
            Logger.getLogger(CreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Predicate> predicarCriteria() {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (getEntity().getId() != null) {
            criteria.add(cb.equal(r.get("id"), getEntity().getId()));
        }
        if (getEntity().getCliente() != null && !getEntity().getCliente().trim().equals("")) {
            criteria.add(cb.like(cb.lower(
                    r.get("transaccion").get("comprador").get("nombre")), "%"
                    + getEntity().getCliente().toLowerCase() + "%"));
        }
        if (getEntity().getEstado() != null && getEntity().getEstado().getId() != null && getEntity().getEstado().getId() > -1) {
            criteria.add(cb.equal(r.get("estado").get("id"), getEntity().getEstado().getId()));
        }
        if (getEntity().getTransaccion() != null && getEntity().getTransaccion().getId() != null && getEntity().getTransaccion().getId() > -1) {
            criteria.add(cb.equal(r.get("transaccion").get("id"), getEntity().getTransaccion().getId()));
        }
        if (getEntity().getCuotasAtrasadas() != null) {
            criteria.add(cb.equal(r.get("cuotasAtrasadas"), getEntity().getCuotasAtrasadas()));
        }
        if (getEntity().getActivo() != null && !getEntity().getActivo().equals('X')) {
            criteria.add(cb.and(cb.equal(r.get("activo"), getEntity().getActivo())));
        }
        if (getEntity().getFechaUltimoPago() != null) {
            criteria.add(cb.and(cb.equal(r.get("fechaUltimoPago"), getEntity().getFechaUltimoPago())));
        }
        if (!getEntity().getIncluirCreditoDeCompras()) {
            criteria.add(cb.and(cb.notEqual(r.get("transaccion").get("codigo").get("id"), CategoriaEnum.COMPRA_MCR.getSymbol())));
        }

        return criteria;

    }

    @Override
    public TypedQuery setearConsulta() {
        TypedQuery<Credito> res = getEm().createQuery(cq);
        return res;
    }

    public void abrirCredito(Transaccion t, Credito cr) throws Exception {
        if (cr.getGarante() != null) {
            cr.getGarante().setUltimaModificacion(new Date());
            if (cr.getGarante().getId() != null) {
                getEm().merge(cr.getGarante());
            } else {
                cr.getGarante().setActivo('S');
                cr.getGarante().setHabilitado('S');
                getEm().persist(cr.getGarante());
            }
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(t.getFechaOperacion());
        cr.setActivo('S');
        cr.setAmortizacion(t.getCuotas());
        cal.add(Calendar.DAY_OF_MONTH, t.getDiasAPrimerVencimiento());
        cr.setFechaInicio(cal.getTime());
        cr.setEstado(new Categoria(CategoriaEnum.ABIERTO.getSymbol()));
        cal.add(Calendar.MONTH, cr.getAmortizacion());
        Date fin = cal.getTime();
        cr.setFechaFin(fin);
        cr.setTransaccion(t);
        cr.setTotalInteresesPagado(BigDecimal.ZERO);
        cr.setTotalAmortizadoPagado(BigDecimal.ZERO);

        //Generar Cuotas
        cr.setFinanciacions(getFinanciacionFacade().crearCuotas(cr));
        setEntity(cr);
        create();
    }

    public Credito consolidarCreditoPorPago(Integer credito) {
        Credito res = null;
        BigDecimal totalAmortizadoPagado = BigDecimal.ZERO;
        BigDecimal totalInteresPagado = BigDecimal.ZERO;
        BigDecimal interesPagadoMulta = BigDecimal.ZERO;
        Date ultimoPago;
        Calendar ahora = GregorianCalendar.getInstance();
        Calendar fechaHoy = new GregorianCalendar(ahora.get(Calendar.YEAR), ahora.get(Calendar.MONTH), ahora.get(Calendar.DAY_OF_MONTH));
        Short cantidadCuotasPendientes = 0;
        res = find(credito);
        ultimoPago = res.getFechaUltimoPago();
        cantidadCuotasPendientes = res.getCuotasAtrasadas();
        boolean cancelado = true;
        for (Financiacion f : res.getFinanciacions()) {
            if (f.getActivo().equals('S')) {
                if (f.getTotalPagado() != null && f.getFechaPago() != null) {
                    totalAmortizadoPagado = totalAmortizadoPagado.add(f.getCapital());
                    if (f.getCancelado().equals('S')) {
                        //Cuota Cancelada
                        totalInteresPagado = totalInteresPagado.add(f.getInteres());
                        interesPagadoMulta = interesPagadoMulta.add(f.getInteresMora());
                    } else if (f.getFechaVencimiento().after(f.getFechaPago())) {
                        totalAmortizadoPagado = f.getTotalPagado();
                    }
                    if (ultimoPago != null) {
                        if (f.getFechaPago().after(ultimoPago)) {
                            ultimoPago = f.getFechaPago();
                        }
                    } else {
                        ultimoPago = f.getFechaPago();
                    }
                } else if (f.getFechaVencimiento().before(fechaHoy.getTime())) {
                    cantidadCuotasPendientes++;
                }
            }
            if (f.getCancelado().equals('N')) {
                cancelado = false;
            }
        }
        res.setTotalAmortizadoPagado(totalAmortizadoPagado);
        res.setTotalInteresesPagado(totalInteresPagado);
        res.setTotalInteresesPagadoMulta(interesPagadoMulta);
        res.setFechaUltimoPago(ultimoPago);
        res.setCuotasAtrasadas(cantidadCuotasPendientes);
        if (cancelado) {
            res.setEstado(new Categoria(CategoriaEnum.CANCELADO.getSymbol()));
        } else {
            if (cantidadCuotasPendientes > 0) {
                if (!res.getEstado().getId().equals(CategoriaEnum.EN_PROCESO_JUDICIAL.getSymbol())
                        && !res.getEstado().getId().equals(CategoriaEnum.CERRADO.getSymbol())) {
                    res.setEstado(new Categoria(CategoriaEnum.EN_MORA.getSymbol()));
                }
            } else {
                if (!res.getEstado().getId().equals(CategoriaEnum.EN_PROCESO_JUDICIAL.getSymbol())
                        && !res.getEstado().getId().equals(CategoriaEnum.CERRADO.getSymbol())) {
                    res.setEstado(new Categoria(CategoriaEnum.ABIERTO.getSymbol()));
                }
            }
        }
        res.setEstado(cancelado ? new Categoria(CategoriaEnum.CERRADO.getSymbol()) : new Categoria(CategoriaEnum.ABIERTO.getSymbol()));
        //Guardar
        setEntity(res);
        guardar();
        return res;
    }
}

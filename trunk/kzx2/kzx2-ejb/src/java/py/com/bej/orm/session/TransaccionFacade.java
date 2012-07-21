/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.utils.CategoriaEnum;

/**
 *
 * @author diego
 */
@Stateless
public class TransaccionFacade extends AbstractFacade<Transaccion> {

    @EJB
    private PlanFacade planFacade;
    @EJB
    private CreditoFacade creditoFacade;
    @EJB
    private MotostockFacade motostockFacade;
    private final static Logger LOGGER = Logger.getLogger(MotostockFacade.class.getName());

    public TransaccionFacade() {
        super(Transaccion.class);
    }

    public PlanFacade getPlanFacade() {
        if (planFacade == null) {
            planFacade = new PlanFacade();
        }
        return planFacade;
    }

    public CreditoFacade getCreditoFacade() {
        if (creditoFacade == null) {
            creditoFacade = new CreditoFacade();
        }
        return creditoFacade;
    }

    public List<Transaccion> findByFechaYCodigo(Integer codigoDesde, Integer codigoHasta, Date fechaDesde, Date fechaaHasta) throws Exception {
        List<Transaccion> res = null;
        inicio();
        cq.where(cb.and(
                cb.greaterThanOrEqualTo(r.get("codigo").get("id"), codigoDesde),
                cb.lessThanOrEqualTo(r.get("codigo").get("id"), codigoHasta),
                cb.between(r.get("fechaOperacion"), fechaDesde, fechaaHasta),
                cb.equal(r.get("activo"), 'S')));
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        res = q.getResultList();
        if (res != null && !res.isEmpty()) {
            for (Transaccion t : res) {
                t.setMotoVendida(t.getMotostocksVenta().get(0));
                if (t.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
                    t.setPrecioTotal(t.getTotalPagado());
                } else {
                    t.setPrecioTotal(t.getEntregaInicial().add(BigDecimal.valueOf(t.getCuotas() * t.getMontoCuotaIgual().doubleValue())));
                }
            }
        }
        return res;
    }

    public List<Transaccion> findUltimasVentas() {
        List<Transaccion> res = null;
        inicio();
        cq.where(cb.and(
                cb.greaterThanOrEqualTo(r.get("codigo").get("id"), CategoriaEnum.VENTA_DESDE.getSymbol()),
                cb.lessThanOrEqualTo(r.get("codigo").get("id"), CategoriaEnum.VENTA_HASTA.getSymbol()),
                cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.desc(r.get("fechaOperacion")));
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        q.setMaxResults(4);
        res = q.getResultList();
        return res;
    }

    public MotostockFacade getMotostockFacade() {
        if (motostockFacade == null) {
            motostockFacade = new MotostockFacade();
        }
        return motostockFacade;
    }

    @Override
    public List<Transaccion> findRange() {
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
            if (getOrden().getColumna().equals("codigo")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("descripcion")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("descripcion")));
                }
            } else if (getOrden().getColumna().equals("vendedor")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("nombre")));
                }
            } else if (getOrden().getColumna().equals("comprador")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("nombre")));
                }
            } else {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
                }
            }
        }
        TypedQuery<Transaccion> q = setearConsulta();
        if (getContador() == null) {
            cq.select(cq.from(getEntityClass()));
            cq.select(cb.count(r.get("id")));
            TypedQuery<Integer> q1 = setearConsulta();
            setContador(Long.parseLong("" + q1.getSingleResult()));
        }
        q.setMaxResults(getRango()[1].intValue());
        q.setFirstResult(getRango()[0].intValue());
        setUltimo(getRango()[0] + getRango()[1] > getContador() ? getContador() : getRango()[0] + getRango()[1]);
        return q.getResultList();
    }

    @Override
    public List<Transaccion> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0L;
        }
        return findRange();
    }

    @Override
    public List<Transaccion> siguiente() {
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

    public void guardarVenta(Transaccion t) {
        //Verificar si el cliente esta actualizado
        t.getComprador().setUltimaModificacion(new Date());
        try {
            if (t.getComprador().getId() != null) {
                getEm().merge(t.getComprador());
            } else {
                t.getComprador().setActivo('S');
                t.getComprador().setHabilitado('S');
                t.getComprador().setFechaIngreso(new Date());
                getEm().persist(t.getComprador());

            }
            getEm().persist(t);
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

    public List<Transaccion> findByTransaccion(Integer categoria) {
        inicio();
        List<Predicate> criteria = new ArrayList<Predicate>();
        ParameterExpression<Integer> p1 =
                cb.parameter(Integer.class, "categoria");
        ParameterExpression<Character> p2 =
                cb.parameter(Character.class, "habilitado");
        List<Transaccion> res = new ArrayList<Transaccion>();
        criteria.add(cb.equal(r.get("categoria"), p1));
        criteria.add(cb.equal(r.get("habilitado"), p2));
        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        cq.orderBy(cb.desc(r.get("id")));
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        q.setParameter("categoria", categoria);
        q.setParameter("habilitado", 'S');
        res = q.getResultList();
        return res;
    }

    public List<Transaccion> findByTransaccion(Integer inicio, Integer fin) {
        List<Transaccion> res = new ArrayList<Transaccion>();
        inicio();
        cq.where(cb.greaterThanOrEqualTo(r.get("codigo").get("id"), inicio));
        cq.where(cb.and(cb.lessThanOrEqualTo(r.get("codigo").get("id"), fin)));
        cq.orderBy(cb.desc(r.get("id")));
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Predicate> predicarCriteria() {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (getEntity().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (getEntity().getCodigo() != null) {
            ParameterExpression<Integer> p1 =
                    cb.parameter(Integer.class, "codigo");
            if (getEntity().getCodigoMax() != null) {
                ParameterExpression<Integer> p2 =
                        cb.parameter(Integer.class, "codigoMax");
                criteria.add(cb.between(r.get("codigo").get("id"), p1, p2));
            }
        }
//        if (getEntity().getComprobante().getNumero() != null) {
//            criteria.add(cb.like(cb.lower(
//                    r.get("comprobante").get("numero")), "%"
//                    + getEntity().getComprobante().getNumero().toLowerCase() + "%"));
//        }
        if (getEntity().getComprador().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "comprador");
            criteria.add(cb.equal(r.get("comprador").get("id"), p));
        }
        if (getEntity().getVendedor().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "vendedor");
            criteria.add(cb.equal(r.get("vendedor").get("id"), p));
        }
        if (getEntity().getAnulado() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "anulado");
            criteria.add(cb.equal(r.get("anulado"), p));
        }
        if (getEntity().getSaldado() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "saldado");
            criteria.add(cb.equal(r.get("saldado"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery setearConsulta() {
        TypedQuery q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getCodigo() != null) {
            q.setParameter("codigo", getEntity().getCodigo().getId());
        }
        if (getEntity().getCodigoMax() != null) {
            q.setParameter("codigoMax", getEntity().getCodigoMax().getId());
        }
//        if (getEntity().getComprobante() != null) {
//            q.setParameter("comprobante", getEntity().getComprobante());
//        }
        if (getEntity().getVendedor() != null) {
            q.setParameter("vendedor", getEntity().getVendedor().getId());
        }
        if (getEntity().getComprador() != null) {
            q.setParameter("comprador", getEntity().getComprador().getId());
        }
        if (getEntity().getAnulado() != null) {
            q.setParameter("anulado", getEntity().getAnulado());
        }
        if (getEntity().getSaldado() != null) {
            q.setParameter("saldado", getEntity().getSaldado());
        }
        return q;
    }

    public Transaccion findByNumeroAnterior(Integer idAnterior, Integer categoriaDesde, Integer categoriaHasta) throws Exception {
        Transaccion res = null;
        inicio();
        cq.where(cb.and(
                cb.equal(r.get("idAnterior"), idAnterior),
                cb.greaterThanOrEqualTo(r.get("codigo").get("id"), categoriaDesde),
                cb.lessThanOrEqualTo(r.get("codigo").get("id"), categoriaHasta)));
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        res = q.getSingleResult();
        return res;
    }

    public Transaccion findByNumeroAnterior(Integer idAnterior, Integer categoria) throws Exception {
        Transaccion res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("idAnterior"), idAnterior),
                cb.notEqual(r.get("codigo").get("id"), categoria)));
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        res = q.getSingleResult();
        return res;
    }
}

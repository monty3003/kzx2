/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.transaction.UserTransaction;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Transaccion;

/**
 *
 * @author diego
 */
@Stateless
public class TransaccionFacade extends AbstractFacade<Transaccion> {

    @EJB
    private MotostockFacade motostockFacade;

    public TransaccionFacade() {
        super(Transaccion.class);
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
            } else if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Transaccion> q = setearConsulta();
        if (getContador() == null) {
            setContador(q.getResultList().size());
        }
        q.setMaxResults(getRango()[1]);
        q.setFirstResult(getRango()[0]);
        setUltimo(getRango()[0] + getRango()[1] > getContador() ? getContador() : getRango()[0] + getRango()[1]);
        return q.getResultList();
    }

    @Override
    public List<Transaccion> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0;
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int guardarCompra(List<Motostock> motos) {
        int res = 0;
        try {
            motostockFacade = new MotostockFacade();
            getEm().persist(getEntity());
            getEm().flush();
            for (Motostock m : motos) {
                m.setPrecioVenta(BigDecimal.ZERO);
                m.setCompra(getEntity());
                motostockFacade.persist(m);
                res++;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        } finally {
            return res;
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
        cq.where(cb.between(r.get("codigo").get("id"), inicio, fin));
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
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "codigo");
            criteria.add(cb.equal(r.get("codigo").get("id"), p));
        }
        if (getEntity().getComprobante() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("comprobante")), "%"
                    + getEntity().getComprobante().toLowerCase() + "%"));
        }
        if (getEntity().getComprador() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "comprador");
            criteria.add(cb.equal(r.get("comprador").get("personaPK").get("id"), p));
        }
        if (getEntity().getVendedor() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "vendedor");
            criteria.add(cb.equal(r.get("vendedor").get("personaPK").get("id"), p));
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
    public TypedQuery<Transaccion> setearConsulta() {
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getCodigo() != null) {
            q.setParameter("codigo", getEntity().getCodigo().getId());
        }
        if (getEntity().getComprobante() != null) {
            q.setParameter("comprobante", getEntity().getComprobante());
        }
        if (getEntity().getVendedor() != null) {
            q.setParameter("vendedor", getEntity().getVendedor().getPersonaPK().getId());
        }
        if (getEntity().getComprador() != null) {
            q.setParameter("comprador", getEntity().getComprador().getPersonaPK().getId());
        }
        if (getEntity().getAnulado() != null) {
            q.setParameter("anulado", getEntity().getAnulado());
        }
        if (getEntity().getSaldado() != null) {
            q.setParameter("saldado", getEntity().getSaldado());
        }
        return q;
    }
}

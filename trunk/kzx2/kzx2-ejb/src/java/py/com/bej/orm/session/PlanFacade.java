/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Plan;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class PlanFacade extends AbstractFacade<Plan> {

    public PlanFacade() {
        super(Plan.class);
    }

    @Override
    public List<Plan> findRange() {
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
            if (getOrden().getColumna().equals("categoria")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("descripcion")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("descripcion")));
                }
            } else {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
                }
            }
        }
        TypedQuery<Plan> q = setearConsulta();
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
    public List<Plan> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0L;
        }
        return findRange();
    }

    @Override
    public List<Plan> siguiente() {
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

    @Override
    public List<Predicate> predicarCriteria() {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (getEntity().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (getEntity().getCategoria().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "categoria");
            criteria.add(cb.equal(r.get("categoria").get("id"), p));
        }
        if (getEntity().getNombre() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("nombre")), "%"
                    + getEntity().getNombre().toLowerCase() + "%"));
        }
        if (getEntity().getActivo() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "activo");
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
        if (getEntity().getCategoria().getId() != null) {
            q.setParameter("categoria", getEntity().getCategoria().getId());
        }
        if (getEntity().getNombre() != null) {
            q.setParameter("nombre", getEntity().getNombre());
        }
        if (getEntity().getActivo() != null) {
            q.setParameter("activo", getEntity().getActivo());
        }
        return q;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

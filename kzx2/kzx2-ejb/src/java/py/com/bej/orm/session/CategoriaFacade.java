/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Categoria;

/**
 *
 * @author diego
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> {

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public List<Categoria> findRange() {
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
            if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Categoria> q = setearConsulta();
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

    public List<Categoria> findBetween(Integer inicio, Integer fin) {
        List<Categoria> res = new ArrayList<Categoria>();
        inicio();
        cq.where(cb.and(cb.greaterThanOrEqualTo(r.get("id"), inicio), cb.lessThanOrEqualTo(r.get("id"), fin)));
        TypedQuery<Categoria> q = getEm().createQuery(cq);
        res = q.getResultList();
        return new ArrayList<Categoria>(res);
    }

    @Override
    public List<Categoria> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 0) {
            getRango()[0] = 0L;
        }
        return findRange();
    }

    @Override
    public List<Categoria> siguiente() {
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
        if (getEntity().getDescripcion() != null && !getEntity().getDescripcion().trim().equals("")) {
            criteria.add(cb.like(cb.lower(
                    r.get("descripcion")), "%"
                    + getEntity().getDescripcion().toLowerCase() + "%"));
        }
        return criteria;
    }

    @Override
    public TypedQuery setearConsulta() {
        TypedQuery q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getDescripcion() != null) {
            q.setParameter("descripcion", getEntity().getDescripcion());
        }
        return q;
    }
}

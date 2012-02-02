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
import py.com.bej.orm.entities.Factura;

/**
 *
 * @author Diego_M
 */
@Stateless
public class FacturaFacade extends AbstractFacade<Factura> {

    public FacturaFacade() {
        super(Factura.class);
    }

    @Override
    public List<Factura> findRange() {
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
            } else if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Factura> q = setearConsulta();
        if (getContador() == null) {
            setContador(q.getResultList().size());
        }
        q.setMaxResults(getRango()[1]);
        q.setFirstResult(getRango()[0]);
        setDesde(getRango()[0]);
        setUltimo(getRango()[0] + getRango()[1] > getContador() ? getContador() : getRango()[0] + getRango()[1]);
        return q.getResultList();
    }

    @Override
    public List<Factura> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0;
        }
        return findRange();
    }

    @Override
    public List<Factura> siguiente() {
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
        if (getEntity().getNumero() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("numero")), "%"
                    + getEntity().getNumero().toLowerCase() + "%"));
        }
        if (getEntity().getSaldado() != ' ') {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "saldado");
            criteria.add(cb.equal(r.get("saldado"), p));
        }
        if (getEntity().getCategoria().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "categoria.id");
            criteria.add(cb.equal(r.get("categoria").get("id"), p));
        }

        return criteria;
    }

    @Override
    public TypedQuery<Factura> setearConsulta() {
        TypedQuery<Factura> q = getEm().createQuery(cq);
        if (getEntity().getNumero() != null) {
            q.setParameter("numero", getEntity().getNumero());
        }
        if (getEntity().getSaldado() != null) {
            q.setParameter("saldado", getEntity().getSaldado());
        }
        if (getEntity().getCategoria() != null) {
            q.setParameter("categoria", getEntity().getCategoria().getId());
        }
        return q;
    }
}

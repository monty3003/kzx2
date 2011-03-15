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
import py.com.bej.orm.entities.Moto;

/**
 *
 * @author diego
 */
@Stateless
public class MotoFacade extends AbstractFacade<Moto> {

    public MotoFacade() {
        super(Moto.class);
    }

    @Override
    public List<Moto> findRange() {
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
            } else if (getOrden().getColumna().equals("fabricante")) {
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
        TypedQuery<Moto> q = setearConsulta();
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
    public List<Moto> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0;
        }
        return findRange();
    }

    @Override
    public List<Moto> siguiente() {
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
        if (getEntity().getCodigo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("codigo")), "%"
                    + getEntity().getCodigo().toLowerCase() + "%"));
        }
        if (getEntity().getCodigoFabrica() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("codigoFabrica")), "%"
                    + getEntity().getCodigoFabrica().toLowerCase() + "%"));
        }
        if (getEntity().getMarca() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("marca")), "%"
                    + getEntity().getMarca().toLowerCase() + "%"));
        }
        if (getEntity().getModelo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("modelo")), "%"
                    + getEntity().getModelo().toLowerCase() + "%"));
        }
        if (getEntity().getColor() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("color")), "%"
                    + getEntity().getColor().toLowerCase() + "%"));
        }
        if (getEntity().getFabricante() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "fabricante");
            criteria.add(cb.equal(r.get("fabricante").get("personaPK").get("id"), p));
        }
        if (getEntity().getCategoria() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "categoria");
            criteria.add(cb.equal(r.get("categoria").get("id"), p));
        }

        return criteria;
    }

    @Override
    public TypedQuery<Moto> setearConsulta() {
        TypedQuery<Moto> q = getEm().createQuery(cq);
        if (getEntity().getCodigo() != null) {
            q.setParameter("codigo", getEntity().getCodigo());
        }
        if (getEntity().getCodigoFabrica() != null) {
            q.setParameter("codigoFabrica", getEntity().getCodigoFabrica());
        }
        if (getEntity().getMarca() != null) {
            q.setParameter("marca", getEntity().getMarca());
        }
        if (getEntity().getModelo() != null) {
            q.setParameter("modelo", getEntity().getModelo());
        }
        if (getEntity().getColor() != null) {
            q.setParameter("color", getEntity().getColor());
        }
        if (getEntity().getFabricante() != null) {
            q.setParameter("fabricante", getEntity().getFabricante().getPersonaPK().getId());
        }
        if (getEntity().getCategoria() != null) {
            q.setParameter("categoria", getEntity().getCategoria().getId());
        }
        return q;
    }
}

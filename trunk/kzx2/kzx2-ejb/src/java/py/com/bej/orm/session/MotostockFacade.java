/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Motostock;

/**
 *
 * @author diego
 */
@Stateless
public class MotostockFacade extends AbstractFacade<Motostock> {

    public MotostockFacade() {
        super(Motostock.class);
    }

    @Override
    public List<Motostock> findRange() {
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
            if (getOrden().getColumna().equals("moto")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("codigo")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("codigo")));
                }
            } else if (getOrden().getColumna().equals("compra")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("id")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("id")));
                }
            } else if (getOrden().getColumna().equals("venta")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("id")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("id")));
                }
            } else if (getOrden().getColumna().equals("ubicacion")) {
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
        TypedQuery<Motostock> q = setearConsulta();
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
    public List<Motostock> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0;
        }
        return findRange();
    }

    @Override
    public List<Motostock> siguiente() {
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
        if (getEntity().getMoto() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "moto");
            criteria.add(cb.equal(r.get("moto").get("codigo"), p));
        }
        if (getEntity().getMotor() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("motor")), "%"
                    + getEntity().getMotor().toLowerCase() + "%"));
        }
        if (getEntity().getChasis() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("chasis")), "%"
                    + getEntity().getChasis().toLowerCase() + "%"));
        }
        if (getEntity().getCompra() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "compra");
            criteria.add(cb.equal(r.get("compra").get("id"), p));
        }
        if (getEntity().getVenta() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "venta");
            criteria.add(cb.equal(r.get("venta").get("id"), p));
        }
        if (getEntity().getPrecioVenta() != null) {
            ParameterExpression<BigDecimal> p =
                    cb.parameter(BigDecimal.class, "precioVenta");
            criteria.add(cb.equal(r.get("precioVenta"), p));
        }
        if (getEntity().getUbicacion() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "ubicacion");
            criteria.add(cb.equal(r.get("ubicacion").get("id"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery<Motostock> setearConsulta() {
        TypedQuery<Motostock> q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getMoto() != null) {
            q.setParameter("moto", getEntity().getMoto().getCodigo());
        }
        if (getEntity().getMotor() != null) {
            q.setParameter("motor", getEntity().getMotor());
        }
        if (getEntity().getChasis() != null) {
            q.setParameter("chasis", getEntity().getChasis());
        }
         if (getEntity().getCompra() != null) {
            q.setParameter("compra", getEntity().getCompra().getId());
        }
         if (getEntity().getVenta() != null) {
            q.setParameter("venta", getEntity().getVenta().getId());
        }
        if (getEntity().getPrecioVenta() != null) {
            q.setParameter("precioVenta", getEntity().getPrecioVenta());
        }
        if (getEntity().getUbicacion() != null) {
            q.setParameter("ubicacion", getEntity().getUbicacion().getId());
        }
        return q;
    }

    public List<Motostock> findByCompra(Integer compra) {
        inicio();
        ParameterExpression<Integer> p =
                cb.parameter(Integer.class, "compra");
        List<Motostock> res = new ArrayList<Motostock>();
        cq.where(cb.and(cb.equal(r.get("compra").get("id"), p)));
        TypedQuery<Motostock> q = getEm().createQuery(cq);
        q.setParameter("compra", compra);
        res = q.getResultList();
        return res;
    }
}

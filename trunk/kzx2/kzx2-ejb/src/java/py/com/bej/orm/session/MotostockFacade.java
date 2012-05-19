/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Plan;

/**
 *
 * @author diego
 */
@Stateless
public class MotostockFacade extends AbstractFacade<Motostock> {

    public MotostockFacade() {
        super(Motostock.class);
    }

    public Motostock findByVenta(Integer transaccion) throws Exception {
        Motostock res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("venta").get("id"), transaccion), cb.equal(r.get("activo"), "S")));
        TypedQuery<Motostock> q = getEm().createQuery(cq);
        res = q.getSingleResult();
        return res;
    }

    public Motostock findByNumeroAnterior(Integer idAnterior) throws Exception {
        Motostock res = null;
        inicio();
        cq.where(cb.equal(r.get("idAnterior"), idAnterior));
        TypedQuery<Motostock> q = getEm().createQuery(cq);
        res = q.getSingleResult();
        return res;
    }

    public List<Motostock> findByModelo(String modelo) throws Exception {
        List<Motostock> res = null;
        inicio();
        cq.where(cb.and(cb.isNull(r.get("venta")), cb.equal(r.get("moto").get("modelo"), modelo), cb.equal(r.get("activo"), 'S')));
        TypedQuery<Motostock> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public List<Motostock> findStockDisponible() {
        List<Motostock> res = null;
        inicio();
        cq.where(cb.and(cb.isNull(r.get("venta")), cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.desc(r.get("id")));
        TypedQuery<Motostock> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
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
    public List<Motostock> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0L;
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
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(MotostockFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                Logger.getLogger(MotostockFacade.class.getName()).log(Level.SEVERE, "{0},{1},{2}",
                        new Object[]{cv.getConstraintDescriptor(), cv.getMessageTemplate(), cv.getMessage()});
            }
        } catch (Exception ex) {
            Logger.getLogger(MotostockFacade.class.getName()).log(Level.SEVERE, null, ex);
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
        if (getEntity().getMoto().getCodigo() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "moto");
            criteria.add(cb.equal(r.get("moto").get("codigo"), p));
        }
        if (getEntity().getMotor() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("motor")), "%"
                    + getEntity().getMotor().toLowerCase() + "%"));
        }
        if (getEntity().getChasis() != null && !getEntity().getChasis().trim().equals("")) {
            criteria.add(cb.like(cb.lower(
                    r.get("chasis")), "%"
                    + getEntity().getChasis().toLowerCase() + "%"));
        }
        if (getEntity().getCompra().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "compra");
            criteria.add(cb.equal(r.get("compra").get("id"), p));
        }
        if (getEntity().getVenta().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "venta");
            criteria.add(cb.equal(r.get("venta").get("id"), p));
        }
        if (getEntity().getPrecioBase() != null) {
            ParameterExpression<BigDecimal> p =
                    cb.parameter(BigDecimal.class, "precioBase");
            criteria.add(cb.equal(r.get("precioBase"), p));
        }
        if (getEntity().getPrecioContado() != null) {
            ParameterExpression<BigDecimal> p =
                    cb.parameter(BigDecimal.class, "precioContado");
            criteria.add(cb.equal(r.get("precioContado"), p));
        }
        if (getEntity().getUbicacion().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "ubicacion");
            criteria.add(cb.equal(r.get("ubicacion").get("id"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery setearConsulta() {
        TypedQuery q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getMoto().getCodigo() != null) {
            q.setParameter("moto", getEntity().getMoto().getCodigo());
        }
        if (getEntity().getMotor() != null) {
            q.setParameter("motor", getEntity().getMotor());
        }
        if (getEntity().getChasis() != null && !getEntity().getChasis().trim().equals("")) {
            q.setParameter("chasis", getEntity().getChasis());
        }
        if (getEntity().getCompra().getId() != null) {
            q.setParameter("compra", getEntity().getCompra().getId());
        }
        if (getEntity().getVenta().getId() != null) {
            q.setParameter("venta", getEntity().getVenta().getId());
        }
        if (getEntity().getPrecioBase() != null) {
            q.setParameter("precioBase", getEntity().getPrecioBase());
        }
        if (getEntity().getPrecioContado() != null) {
            q.setParameter("precioContado", getEntity().getPrecioContado());
        }
        if (getEntity().getUbicacion().getId() != null) {
            q.setParameter("ubicacion", getEntity().getUbicacion().getId());
        }
        return q;
    }

    public TypedQuery<Integer> setearConsultaContador() {
        TypedQuery<Integer> q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getMoto() != null) {
            q.setParameter("moto", getEntity().getMoto().getCodigo());
        }
        if (getEntity().getMotor() != null) {
            q.setParameter("motor", getEntity().getMotor());
        }
        if (getEntity().getChasis() != null && !getEntity().getChasis().trim().equals("")) {
            q.setParameter("chasis", getEntity().getChasis());
        }
        if (getEntity().getCompra() != null) {
            q.setParameter("compra", getEntity().getCompra().getId());
        }
        if (getEntity().getVenta() != null) {
            q.setParameter("venta", getEntity().getVenta().getId());
        }
        if (getEntity().getPrecioBase() != null) {
            q.setParameter("precioBase", getEntity().getPrecioBase());
        }
        if (getEntity().getPrecioContado() != null) {
            q.setParameter("precioContado", getEntity().getPrecioContado());
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

    public void asignarPlan(List<Motostock> motostock, Plan plan) throws Exception {
        for (Motostock m : motostock) {
            m.setPlan(plan);
            getEm().merge(m);
        }
    }

    public int asignarPrecioGrupal(String modelo, BigDecimal precioCosto, BigDecimal precioContado, BigDecimal precioBase) {
        int res = 0;
        Query q = getEm().createQuery("UPDATE Motostock m SET m.costo =:costo , m.precioContado =:precioContado , m.precioBase =:precioBase"
                + " where m.venta is null and m.activo = 'S' and m.moto.modelo =:modelo");
        q.setParameter("costo", precioCosto);
        q.setParameter("precioContado", precioContado);
        q.setParameter("precioBase", precioBase);
        q.setParameter("modelo", modelo);
        res = q.executeUpdate();
        return res;
    }
}

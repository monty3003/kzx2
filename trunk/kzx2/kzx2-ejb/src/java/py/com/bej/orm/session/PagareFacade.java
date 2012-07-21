/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Pagare;

/**
 *
 * @author Diego_M
 */
@Stateless
public class PagareFacade extends AbstractFacade<Pagare> {

    public PagareFacade() {
        super(Pagare.class);
    }

    public List<Pagare> findPagaresProximosAVencer() {
        List<Pagare> res = null;
        inicio();
        cq.where(cb.and(cb.isNull(r.get("fechaDeCancelacion")),
                cb.equal(r.get("vencido"), Boolean.FALSE)),
                cb.equal(r.get("activo"), 'S'));
        cq.orderBy(cb.asc(r.get("fechaVencimiento")));
        TypedQuery<Pagare> q = getEm().createQuery(cq);
        q.setMaxResults(10);
        res = q.getResultList();
        return res;
    }

    public List<Pagare> findByTransaccion(Integer id) {
        List<Pagare> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("Pagare").get("transaccion").get("id"), id), cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numero")));
        TypedQuery<Pagare> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Pagare> findRange() {
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
            if (getOrden().getColumna().equals("credito")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("id")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("id")));
                }
            } else if (getOrden().getColumna().equals("cliente")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get("credito").get("transaccion").get("comprador").get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get("credito").get("transaccion").get("comprador").get("nombre")));
                }
            } else if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Pagare> q = setearConsulta();
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
    public List<Pagare> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Pagare> siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void guardar() {
        getEm().persist(getEntity());
    }

    @Override
    public List<Predicate> predicarCriteria() {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (getEntity().getId() != null) {
            criteria.add(cb.equal(r.get("id"), getEntity().getId()));
        }
        if (getEntity().getCliente() != null && !getEntity().getCliente().trim().equals("")) {
            criteria.add(cb.like(cb.lower(
                    r.get("credito").get("transaccion").get("comprador").get("nombre")), "%"
                    + getEntity().getCliente().toLowerCase() + "%"));
        }
        if (getEntity().getCredito() != null && getEntity().getCredito().getId() != null && getEntity().getCredito().getId() > -1) {
            criteria.add(cb.equal(r.get("credito").get("id"), getEntity().getCredito().getId()));
        }
        if (getEntity().getNumero() != null) {
            criteria.add(cb.equal(r.get("numero"), getEntity().getNumero()));
        }
        if (getEntity().getFechaVencimiento() != null) {
            criteria.add(cb.equal(r.get("fechaVencimiento"), getEntity().getFechaVencimiento()));
        }
        if (getEntity().getVencido() != null) {
            criteria.add(cb.equal(r.get("vencido"), getEntity().getVencido()));
        }
        if (getEntity().getVencimientoImpreso() != null) {
            criteria.add(cb.equal(r.get("vencimientoImpreso"), getEntity().getVencimientoImpreso()));
        }
        if (getEntity().getFechaDeCancelacion() != null) {
            criteria.add(cb.equal(r.get("fechaDeCancelacion"), getEntity().getFechaDeCancelacion()));
        }
        return criteria;
    }

    @Override
    public TypedQuery setearConsulta() {
        TypedQuery<Pagare> res = getEm().createQuery(cq);
        return res;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

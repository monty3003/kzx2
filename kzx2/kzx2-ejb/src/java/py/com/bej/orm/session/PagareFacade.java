/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

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

    public List<Pagare> findByTransaccion(Integer id) {
        List<Pagare> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("credito").get("transaccion").get("id"), id), cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("numero")));
        TypedQuery<Pagare> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Pagare> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Predicate> predicarCriteria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypedQuery setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

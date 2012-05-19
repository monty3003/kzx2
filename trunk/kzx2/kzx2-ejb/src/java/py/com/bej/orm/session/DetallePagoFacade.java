/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.DetallePago;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class DetallePagoFacade extends AbstractFacade<DetallePago> {

    public DetallePagoFacade() {
        super(DetallePago.class);
    }

    public List<DetallePago> findByIdPago(Integer idPago) {
        List<DetallePago> res = null;
        inicio();
        cq.where(cb.equal(r.get("pago").get("id"), idPago));
        cq.orderBy(cb.asc(r.get("id")));
        TypedQuery<DetallePago> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<DetallePago> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DetallePago> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DetallePago> siguiente() {
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

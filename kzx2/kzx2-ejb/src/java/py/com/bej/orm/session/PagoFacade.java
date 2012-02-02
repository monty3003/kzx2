/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.bej.orm.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Pago;

/**
 *
 * @author diego
 */
@Stateless
public class PagoFacade extends AbstractFacade<Pago> {
    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoFacade() {
        super(Pago.class);
    }

    @Override
    public List<Pago> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Pago> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Pago> siguiente() {
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
    public TypedQuery<Pago> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

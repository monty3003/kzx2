/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.base.prod.entity.Vmintereses;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class VminteresesProduccionFacade extends AbstractFacade<Vmintereses> {

    public VminteresesProduccionFacade() {
        super(Vmintereses.class);
    }

    public List<Vmintereses> findByNroRecibo(int nroRecibo) {
        List<Vmintereses> res = null;
        Query q = getEm().createQuery("select"
                + " new py.com.bej.base.prod.entity.Vmintereses(v.iDcobro, v.numeroRecibocrobro, v.transaccion, v.conceptocobro, v.monto, v.guardado)"
                + " from Vmintereses v where v.numeroRecibocrobro =:nroRecibo and v.monto <>0").setParameter("nroRecibo", nroRecibo);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Vmintereses> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmintereses> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmintereses> siguiente() {
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
    public TypedQuery<Vmintereses> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

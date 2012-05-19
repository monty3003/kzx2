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
import py.com.bej.base.prod.entity.Vmresfuerzos;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class RefuerzoProduccionFacade extends AbstractFacade<Vmresfuerzos> {

    public RefuerzoProduccionFacade() {
        super(Vmresfuerzos.class);
    }

    public List<Vmresfuerzos> findByNroVenta(Integer idVenta) throws Exception {
        List<Vmresfuerzos> res = null;
        Query q = getEm().createQuery("select r from Vmresfuerzos r where r.idVentas.idVenta =?");
        q.setParameter(1, idVenta);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Vmresfuerzos> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmresfuerzos> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmresfuerzos> siguiente() {
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
    public TypedQuery<Vmresfuerzos> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

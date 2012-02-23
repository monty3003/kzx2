/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.base.prod.entity.Vmmotostock;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class MotostockProduccionFacade extends AbstractFacade<Vmmotostock> {

    public MotostockProduccionFacade() {
        super(Vmmotostock.class);
    }

    public List<Vmmotostock> findByIdMotoOrdenado() {
        List<Vmmotostock> res = null;
        res = getEm().createQuery("select v from Vmmotostock v order by v.idMoto asc").getResultList();
        return res;
    }

    @Override
    public List<Vmmotostock> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmmotostock> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmmotostock> siguiente() {
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
    public TypedQuery<Vmmotostock> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

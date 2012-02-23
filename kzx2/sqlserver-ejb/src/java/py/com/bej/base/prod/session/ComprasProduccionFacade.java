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
import py.com.bej.base.prod.entity.Vmcompras;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class ComprasProduccionFacade extends AbstractFacade<Vmcompras> {

    public ComprasProduccionFacade() {
        super(Vmcompras.class);
    }

    public List<Vmcompras> findByIdCompraOrdenado() {
        return getEm().createQuery("select c from Vmcompras c order by c.idCompras asc").getResultList();
    }

    @Override
    public List<Vmcompras> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmcompras> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmcompras> siguiente() {
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
    public TypedQuery<Vmcompras> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

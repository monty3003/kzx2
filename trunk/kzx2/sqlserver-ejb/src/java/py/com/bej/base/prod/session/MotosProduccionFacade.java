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
import py.com.bej.base.prod.entity.Vmmotos;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class MotosProduccionFacade extends AbstractFacade<Vmmotos> {

    public MotosProduccionFacade() {
        super(Vmmotos.class);
    }

    @Override
    public List<Vmmotos> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmmotos> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmmotos> siguiente() {
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
    public TypedQuery<Vmmotos> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

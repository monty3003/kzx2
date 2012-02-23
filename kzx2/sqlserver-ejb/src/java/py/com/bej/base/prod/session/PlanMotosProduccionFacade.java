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
import py.com.bej.base.prod.entity.Vmplanmoto;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class PlanMotosProduccionFacade extends AbstractFacade<Vmplanmoto> {

    public PlanMotosProduccionFacade() {
        super(Vmplanmoto.class);
    }

    @Override
    public List<Vmplanmoto> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmplanmoto> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmplanmoto> siguiente() {
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
    public TypedQuery<Vmplanmoto> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

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
import py.com.bej.orm.entities.Rol;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class RolFacade extends AbstractFacade<Rol> {

    public RolFacade() {
        super(Rol.class);
    }

    @Override
    public List<Rol> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Rol> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Rol> siguiente() {
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
    public TypedQuery<Rol> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

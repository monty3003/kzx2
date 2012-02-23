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
import py.com.bej.base.prod.entity.Proveedores;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class ProveedoresProduccionFacade extends AbstractFacade<Proveedores> {

    public ProveedoresProduccionFacade() {
        super(Proveedores.class);
    }

    @Override
    public List<Proveedores> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Proveedores> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Proveedores> siguiente() {
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
    public TypedQuery<Proveedores> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

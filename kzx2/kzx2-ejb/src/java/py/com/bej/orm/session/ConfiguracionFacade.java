/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Configuracion;

/**
 *
 * @author Diego_M
 */
@Singleton
@LocalBean
public class ConfiguracionFacade extends AbstractFacade<Configuracion> {

    public ConfiguracionFacade() {
        super(Configuracion.class);
    }

    @Override
    public List<Configuracion> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Configuracion> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Configuracion> siguiente() {
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

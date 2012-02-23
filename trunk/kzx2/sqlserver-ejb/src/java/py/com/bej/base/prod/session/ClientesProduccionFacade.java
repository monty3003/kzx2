/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.base.prod.entity.Clientes;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class ClientesProduccionFacade extends AbstractFacade<Clientes> {

    public ClientesProduccionFacade() {
        super(Clientes.class);
    }

    public List<Clientes> buscarOrdenado() {
        List<Clientes> res = new ArrayList<Clientes>();
        res = getEm().createNamedQuery("Clientes.findAllOrdenado").getResultList();
        return res;
    }

    @Override
    public List<Clientes> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Clientes> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Clientes> siguiente() {
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
    public TypedQuery<Clientes> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

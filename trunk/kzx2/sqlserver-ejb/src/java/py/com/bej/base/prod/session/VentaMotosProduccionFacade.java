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
import py.com.bej.base.prod.entity.Vmventamotos;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class VentaMotosProduccionFacade extends AbstractFacade<Vmventamotos> {

    public VentaMotosProduccionFacade() {
        super(Vmventamotos.class);
    }

    public List<Vmventamotos> findByIdCompraOrdenado() {
        return getEm().createQuery("select c from Vmventamotos c order by c.idVenta asc").getResultList();
    }

    @Override
    public List<Vmventamotos> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmventamotos> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmventamotos> siguiente() {
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
    public TypedQuery<Vmventamotos> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

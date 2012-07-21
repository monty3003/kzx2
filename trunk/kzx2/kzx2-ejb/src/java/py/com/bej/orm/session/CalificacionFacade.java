/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Calificacion;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class CalificacionFacade extends AbstractFacade<Calificacion> {

    public CalificacionFacade() {
        super(Calificacion.class);
    }

    @Override
    public List<Calificacion> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Calificacion> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Calificacion> siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void guardar() {
        try {
            getEntity().setUltimaModificacion(new Date());
            getEm().merge(getEntity());
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(CalificacionFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                Logger.getLogger(CalificacionFacade.class.getName()).log(Level.SEVERE, "{0},{1},{2}",
                        new Object[]{cv.getConstraintDescriptor(), cv.getMessageTemplate(), cv.getMessage()});
            }
        } catch (Exception ex) {
            Logger.getLogger(CalificacionFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Password;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class PasswordFacade extends AbstractFacade<Password> {

    public PasswordFacade() {
        super(Password.class);
    }

    public List<Password> findByUsuario(Integer idUsuario) {
        List<Password> res = null;
        inicio();
        cq.where(cb.equal(r.get("usuario").get("id"), idUsuario));
        TypedQuery q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Password> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Password> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Password> siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void guardar() {
        try {
            getEm().merge(getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Predicate> predicarCriteria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypedQuery<Password> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

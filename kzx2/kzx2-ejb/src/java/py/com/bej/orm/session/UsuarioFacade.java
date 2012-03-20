/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Usuario;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class UsuarioFacade extends AbstractFacade<Usuario> {

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario findByUserName(String userName) throws Exception {
        inicio();
        ParameterExpression<String> p1 =
                cb.parameter(String.class, "userName");
        Usuario res = null;
        cq.where(cb.equal(r.get("userName"), p1));
        TypedQuery<Usuario> q = getEm().createQuery(cq);
        q.setParameter("userName", userName);
        try {
            res = q.getSingleResult();

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    @Override
    public List<Usuario> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Usuario> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Usuario> siguiente() {
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
    public TypedQuery<Usuario> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

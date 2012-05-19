/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Documento;

/**
 *
 * @author Diego_M
 */
@Stateless
public class DocumentoFacade extends AbstractFacade<Documento> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentoFacade() {
        super(Documento.class);
    }

    public List<Documento> findByConjunto(String... conjunto) {
        List<Documento> res = null;
        inicio();
        Predicate[] restriccions = new Predicate[conjunto.length];
        for (int i = 0; i < conjunto.length; i++) {
            restriccions[i] = cb.equal(r.get("conjunto"), conjunto[i]);
        }
        cq.where(cb.or(restriccions), cb.and(cb.equal(r.get("activo"), 'S')));
        cq.orderBy(cb.asc(r.get("orden")));
        TypedQuery q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public List<Documento> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Documento> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Documento> siguiente() {
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
}

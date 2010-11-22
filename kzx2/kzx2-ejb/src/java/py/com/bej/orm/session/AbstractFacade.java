/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
public abstract class AbstractFacade<T>{

    private Class<T> entityClass;
    public CriteriaBuilder cb;
    public CriteriaQuery cq;
    public Root r;
    public EntityType et;
    public Orden orden;
    public String col;
    public Boolean asc;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEm();

    public void create(T entity) {
        getEm().persist(entity);
    }

    public void edit(T entity) {
        getEm().merge(entity);
    }

    public void remove(T entity) {
        getEm().remove(getEm().merge(entity));
    }

    public T find(Object id) {
        return getEm().find(getEntityClass(), id);
    }

    public List<T> findAll() {
        cq = getEm().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        return getEm().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        cq = getEm().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        javax.persistence.Query q = getEm().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        cq = getEm().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(getEntityClass());
        cq.select(getEm().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEm().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public abstract Integer totalFiltrado(T entity);

    public abstract List<T> anterior(int[] range, T entity);

    public abstract List<T> siguiente(int[] range, T entity);

    public abstract Integer getContador(T entity);

    public abstract Integer getUltimoItem(int[] range);

    public abstract void setCol(String col);

    public abstract String getCol();

    public abstract void setAsc(Boolean asc);

    public abstract Boolean getAsc();

    /**
     * @return the entityClass
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * @param entityClass the entityClass to set
     */
    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
}

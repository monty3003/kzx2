/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.session;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

/**
 *
 * @author diego
 */
public abstract class AbstractFacade<T> {

    @PersistenceContext(unitName = "sqlserver-ejbPU")
    private EntityManager em;
    private Class<T> entityClass;
    private T entity;
    public CriteriaBuilder cb;
    public CriteriaQuery cq;
    public Root r;
    public EntityType et;
    private Integer contador;
    private Integer desde;
    private Integer ultimo;
    private Integer[] rango;
    public String c0 = "No se encontraron coincidencias";
    public String r0 = "No se encontraron coincidencias";
    public String ex1 = "El registro se ha guardado con éxito";
    public String ex2 = "El registro se ha modificado con éxito";
    public String ex3 = "El registro se ha eliminado con éxito";
    public String sel = "Seleccione un registro para modificar";

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlserver-ejbPU");
            em = emf.createEntityManager();
        }
        return em;
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

    public abstract List<T> findRange();

    public abstract List<T> anterior();

    public abstract List<T> siguiente();

    public abstract void guardar();

    public abstract List<Predicate> predicarCriteria();

    public abstract TypedQuery<T> setearConsulta();

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
        this.setEntityClass(entityClass);
    }

    public void create() {
        getEm().persist(entity);
    }

    public void inicio() {
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<T>) cb.createQuery(entityClass);
        r = (Root<T>) cq.from(entityClass);
    }

    /**
     * @return the contador
     */
    public Integer getContador() {
        return contador;
    }

    /**
     * @param contador the contador to set
     */
    public void setContador(Integer contador) {
        this.contador = contador;
    }

    /**
     * @return the rango
     */
    public Integer[] getRango() {
        return rango;
    }

    /**
     * @param rango the rango to set
     */
    public void setRango(Integer[] rango) {
        this.rango = rango;
    }

    /**
     * @return the desde
     */
    public Integer getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    /**
     * @return the ultimo
     */
    public Integer getUltimo() {
        return ultimo;
    }

    /**
     * @param ultimo the ultimo to set
     */
    public void setUltimo(Integer ultimo) {
        this.ultimo = ultimo;
    }

    /**
     * @return the entity
     */
    public T getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(T entity) {
        this.entity = entity;
    }

    public void persist(Object object) {
        getEm().persist(object);
    }
}

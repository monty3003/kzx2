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
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.interfaces.WithId;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
public abstract class AbstractFacade<T extends WithId> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;
    private Class<T> entityClass;
    private T entity;
    public CriteriaBuilder cb;
    public CriteriaQuery cq;
    public Root r;
    public EntityType et;
    private Orden orden;
    private Long contador;
    private Long desde;
    private Long ultimo;
    private Long[] rango;
    public String c0 = "No se encontraron coincidencias";
    public String r0 = "No se encontraron coincidencias";
    public String ex1 = "El registro se ha guardado con éxito";
    public String ex2 = "El registro se ha modificado con éxito";
    public String ex3 = "El registro se ha eliminado con éxito";
    public String sel = "Seleccione un registro para modificar";
    private final static Logger LOGGER = Logger.getLogger(AbstractFacade.class.getName());

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.orden = new Orden(entityClass.getDeclaredFields()[0].getName(), Boolean.FALSE);
    }

    protected EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("kzx2-ejbPU");
            em = emf.createEntityManager();
        }
        return em;
    }

    public void edit(T entity) {
        try {
            entity.setUltimaModificacion(new Date());
            getEm().merge(entity);
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                LOGGER.log(Level.SEVERE, "Constraint Descriptor :", cv.getConstraintDescriptor());
                LOGGER.log(Level.SEVERE, "Invalid Value :", cv.getInvalidValue());
                LOGGER.log(Level.SEVERE, "Root Bean :", cv.getRootBean());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Ocurrio una excepcion al intentar guardar el registro", ex);

        }
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

    public abstract TypedQuery setearConsulta();

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
        try {
            entity.setActivo('S');
            entity.setUltimaModificacion(new Date());
            getEm().persist(entity);
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                LOGGER.log(Level.SEVERE, "Constraint Descriptor :", cv.getConstraintDescriptor());
                LOGGER.log(Level.SEVERE, "Invalid Value :", cv.getInvalidValue());
                LOGGER.log(Level.SEVERE, "Root Bean :", cv.getRootBean());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Ocurrio una excepcion al intentar guardar el registro", ex);

        }
    }

    public void create(T tx) {
        try {
            getEm().persist(tx);
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                LOGGER.log(Level.SEVERE, "Constraint Descriptor :", cv.getConstraintDescriptor());
                LOGGER.log(Level.SEVERE, "Invalid Value :", cv.getInvalidValue());
                LOGGER.log(Level.SEVERE, "Root Bean :", cv.getRootBean());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Ocurrio una excepcion al intentar guardar el registro", ex);

        }
    }

    public int cargaMasiva(List<T> lista) throws Exception {
        int res = 0;
        try {
            for (T e : lista) {
                LOGGER.log(Level.FINE, "Se va a guardar el registro {0}", e.toString());
                getEm().persist(e);
                res++;
            }
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> listaDeErrores = cve.getConstraintViolations();
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : listaDeErrores) {
                LOGGER.log(Level.SEVERE, "Constraint Descriptor :", cv.getConstraintDescriptor());
                LOGGER.log(Level.SEVERE, "Invalid Value :", cv.getInvalidValue());
                LOGGER.log(Level.SEVERE, "Root Bean :", cv.getRootBean());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Ocurrio una excepcion al intentar guardar el registro", ex);

        }
        return res;
    }

    public void inicio() {
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<T>) cb.createQuery(entityClass);
        r = (Root<T>) cq.from(entityClass);
    }

    /**
     * @return the contador
     */
    public Long getContador() {
        return contador;
    }

    /**
     * @param contador the contador to set
     */
    public void setContador(Long contador) {
        this.contador = contador;
    }

    /**
     * @return the rango
     */
    public Long[] getRango() {
        return rango;
    }

    /**
     * @param rango the rango to set
     */
    public void setRango(Long[] rango) {
        this.rango = rango;
    }

    /**
     * @return the desde
     */
    public Long getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(Long desde) {
        this.desde = desde;
    }

    /**
     * @return the ultimo
     */
    public Long getUltimo() {
        return ultimo;
    }

    /**
     * @param ultimo the ultimo to set
     */
    public void setUltimo(Long ultimo) {
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

    /**
     * @return the orden
     */
    public Orden getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public void persist(Object object) {
        getEm().persist(object);
    }
}

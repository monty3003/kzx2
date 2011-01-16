/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import py.com.bej.orm.entities.Categoria;

/**
 *
 * @author diego
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;
    public static Categoria c = new Categoria();
    private Integer contador;

    @Override
    protected EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("kzx2-ejbPU");
            em = emf.createEntityManager();
        }
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public List<Categoria> findRange(int[] range, Categoria c) {
        inicio();
        List<Predicate> criteria = predicarCriteria(c);
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        if (col != null && asc != null) {
            if (col.equals("id")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("id")));
                } else {
                    cq.orderBy(cb.desc(r.get("id")));
                }
            } else if (col.equals("descripcion")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("descripcion")));
                } else {
                    cq.orderBy(cb.desc(r.get("descripcion")));
                }
            }
        }
        TypedQuery<Categoria> q = setearConsulta(c);
        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return q.getResultList();

    }

    public List<Categoria> findBetween(Integer inicio, Integer fin) {
        List<Categoria> res = new ArrayList<Categoria>();
        inicio();
        cq.where(cb.between(r.get("id"), inicio, fin));
        TypedQuery<Categoria> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    @Override
    public int count() {
        inicio();
        Root<Categoria> rt = cq.from(Categoria.class);
        cq.select(getEm().getCriteriaBuilder().count(rt));
        Query q = getEm().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public void inicio() {
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<Categoria>) cb.createQuery(c.getClass());
        r = (Root<Categoria>) cq.from(c.getClass());
        et = r.getModel();
        this.orden = null;
    }

    @Override
    public List<Categoria> anterior(int[] range, Categoria entity) {
        range[0] = -range[1];
        if (range[0] < 0) {
            range[0] = 0;
        }
        return this.findRange(range, entity);
    }

    @Override
    public Integer getContador(Categoria entity) {
        this.contador = this.totalFiltrado(entity);
        return this.contador;
    }

    @Override
    public List<Categoria> siguiente(int[] range, Categoria entity) {
        c = entity;
        if (range[0] + range[1] < getContador(c)) {
            range[0] = range[0] + range[1];
        }

        return this.findRange(range, entity);
    }

    @Override
    public Integer totalFiltrado(Categoria entity) {
        inicio();
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (entity.getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (entity.getDescripcion() != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "descripcion");
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("descripcion", String.class))), "%"
                    + entity.getDescripcion().toLowerCase() + "%"));
        }
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        TypedQuery<Categoria> q = getEm().createQuery(cq);
        if (entity.getId() != null) {
            q.setParameter("id", entity.getId());
        }
        if (entity.getDescripcion() != null) {
            q.setParameter("descripcion", entity.getDescripcion());
        }
        return q.getResultList().size();
    }

    @Override
    public Integer getUltimoItem(int[] range) {
        this.contador = getContador(c);
        return range[0] + range[1] > this.contador ? this.contador : range[0] + range[1];
    }

    @Override
    public boolean create(Categoria c) {
        Categoria aux = null;
        try {
            aux = em.find(Categoria.class, c.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (aux != null) {
                return false;
            } else {
                em.persist(c);
                return true;
            }
        }
    }

    @Override
    public void guardar(Categoria c) {
        try {
            em.merge(c);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the col
     */
    @Override
    public String getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    @Override
    public void setCol(String col) {
        this.col = col;
    }

    /**
     * @return the asc
     */
    @Override
    public Boolean getAsc() {
        return asc;
    }

    /**
     * @param asc the asc to set
     */
    @Override
    public void setAsc(Boolean asc) {
        if (this.asc == asc) {
            this.asc = !asc;
        } else {
            this.asc = asc;
        }
    }

    @Override
    public List<Predicate> predicarCriteria(Categoria c) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (c.getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (c.getDescripcion() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("descripcion", String.class))), "%"
                    + c.getDescripcion().toLowerCase() + "%"));
        }
        return criteria;
    }

    @Override
    public TypedQuery<Categoria> setearConsulta(Categoria c) {
        TypedQuery<Categoria> q = getEm().createQuery(cq);
        if (c.getId() != null) {
            q.setParameter("id", c.getId());
        }
        if (c.getDescripcion() != null) {
            q.setParameter("descripcion", c.getDescripcion());
        }
        return q;
    }
}

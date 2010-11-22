/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import py.com.bej.orm.entities.Categoria;

/**
 *
 * @author diego
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria>{

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;
    public static Categoria c = new Categoria();
    private Integer contador;
    private Integer id;
    private String descripcion;

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
    public List<Categoria> findRange(int[] range) {
        inicio();
        if (id != null) {
            cq.where(cb.equal(r.get("id"), id));
        }
        if (descripcion != null && !descripcion.trim().equals("")) {
            cq.where(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("descripcion", String.class))), "%"
                    + descripcion.toLowerCase() + "%"));
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
        TypedQuery<Categoria> q = getEm().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
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
        if(et == null){
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<Categoria>) cb.createQuery(c.getClass());
        r = (Root<Categoria>) cq.from(c.getClass());
        et = r.getModel();
        }
        this.orden = null;
    }

    @Override
    public List<Categoria> anterior(int[] range, Categoria entity) {
        c = entity;
        range[0] = -range[1];
        if (range[0] < 0) {
            range[0] = 0;
        }
        return this.findRange(range);
    }

    @Override
    public Integer getContador(Categoria entity) {
        if (this.contador == null) {
            this.contador = this.totalFiltrado(entity);
        }
        return this.contador;
    }

    @Override
    public List<Categoria> siguiente(int[] range, Categoria entity) {
        c = entity;
        if (range[0] + range[1] < getContador(c)) {
            range[0] = range[0] + range[1];
        }

        return this.findRange(range);
    }

    @Override
    public Integer totalFiltrado(Categoria entity) {
        inicio();
        if (c.getId() != null) {
            cq.where(cb.equal(r.get("id"), c.getId()));
        }
        if (c.getDescripcion() != null && !c.getDescripcion().trim().equals("")) {
            cq.where(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("descripcion", String.class))), "%"
                    + c.getDescripcion().toLowerCase() + "%"));
        }
        TypedQuery<Categoria> q = getEm().createQuery(cq);
        return q.getResultList().size();
    }

    @Override
    public Integer getUltimoItem(int[] range) {
        this.contador = getContador(c);
        return range[0] + range[1] > this.contador ? this.contador : range[0] + range[1];
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

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}

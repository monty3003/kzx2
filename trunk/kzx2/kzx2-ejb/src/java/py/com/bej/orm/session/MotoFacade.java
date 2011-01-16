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
import py.com.bej.orm.entities.Moto;

/**
 *
 * @author diego
 */
@Stateless
public class MotoFacade extends AbstractFacade<Moto> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;
    public static Moto c = new Moto();
    private Integer contador;

    @Override
    protected EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("kzx2-ejbPU");
            em = emf.createEntityManager();
        }
        return em;
    }

    public MotoFacade() {
        super(Moto.class);
    }

    @Override
    public List<Moto> findRange(int[] range, Moto c) {
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
            if (col.equals("codigo")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("codigo")));
                } else {
                    cq.orderBy(cb.desc(r.get("codigo")));
                }
            } else if (col.equals("codigoFabrica")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("codigoFabrica")));
                } else {
                    cq.orderBy(cb.desc(r.get("codigoFabrica")));
                }
            } else if (col.equals("marca")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("marca")));
                } else {
                    cq.orderBy(cb.desc(r.get("marca")));
                }
            } else if (col.equals("modelo")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("modelo")));
                } else {
                    cq.orderBy(cb.desc(r.get("modelo")));
                }
            } else if (col.equals("color")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("color")));
                } else {
                    cq.orderBy(cb.desc(r.get("color")));
                }
            } else if (col.equals("fabricante")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("fabricante")));
                } else {
                    cq.orderBy(cb.desc(r.get("fabricante")));
                }
            }
        }
        TypedQuery<Moto> q = setearConsulta(c);

        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return q.getResultList();

    }

    @Override
    public int count() {
        inicio();
        Root<Moto> rt = cq.from(Moto.class);
        cq.select(getEm().getCriteriaBuilder().count(rt));
        Query q = getEm().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public void inicio() {
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<Moto>) cb.createQuery(c.getClass());
        r = (Root<Moto>) cq.from(c.getClass());
        et = r.getModel();
        this.orden = null;
    }

    @Override
    public List<Moto> anterior(int[] range, Moto entity) {
        range[0] = -range[1];
        if (range[0] < 0) {
            range[0] = 0;
        }
        return this.findRange(range, entity);
    }

    @Override
    public Integer getContador(Moto entity) {
        this.contador = this.totalFiltrado(entity);
        return this.contador;
    }

    @Override
    public List<Moto> siguiente(int[] range, Moto entity) {
        c = entity;
        if (range[0] + range[1] < getContador(c)) {
            range[0] = range[0] + range[1];
        }

        return this.findRange(range, entity);
    }

    @Override
    public Integer totalFiltrado(Moto entity) {
        inicio();
        List<Predicate> criteria = predicarCriteria(c);
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        TypedQuery<Moto> q = setearConsulta(c);
        return q.getResultList().size();
    }

    @Override
    public Integer getUltimoItem(int[] range) {
        this.contador = getContador(c);
        return range[0] + range[1] > this.contador ? this.contador : range[0] + range[1];
    }

    @Override
    public boolean create(Moto c) {
        Moto aux = null;
        boolean res = true;
        try {
            aux = em.find(Moto.class, c.getCodigo());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (aux != null) {
                if (aux.getCodigo().equals(c.getCodigo())) {
                    res = false;
                }
            } else {
                em.persist(c);
                res = true;
            }
            return res;
        }

    }

    @Override
    public void guardar(Moto c) {
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
    public List<Predicate> predicarCriteria(Moto c) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (c.getCodigo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("codigo", String.class))), "%"
                    + c.getCodigo().toLowerCase() + "%"));
        }
        if (c.getCodigoFabrica() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("codigoFabrica", String.class))), "%"
                    + c.getCodigoFabrica().toLowerCase() + "%"));
        }
        if (c.getMarca() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("marca", String.class))), "%"
                    + c.getMarca().toLowerCase() + "%"));
        }
        if (c.getModelo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("modelo", String.class))), "%"
                    + c.getModelo().toLowerCase() + "%"));
        }
        if (c.getColor() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("color", String.class))), "%"
                    + c.getColor().toLowerCase() + "%"));
        }
        if (c.getFabricante() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("fabricante"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery<Moto> setearConsulta(Moto c) {
        TypedQuery<Moto> q = getEm().createQuery(cq);
        if (c.getCodigo() != null) {
            q.setParameter("codigo", c.getCodigo());
        }
        if (c.getCodigoFabrica() != null) {
            q.setParameter("codigoFabrica", c.getCodigoFabrica());
        }
        if (c.getMarca() != null) {
            q.setParameter("marca", c.getMarca());
        }
        if (c.getModelo() != null) {
            q.setParameter("modelo", c.getModelo());
        }
        if (c.getColor() != null) {
            q.setParameter("color", c.getColor());
        }
        if (c.getFabricante() != null) {
            q.setParameter("fabricante", c.getFabricante());
        }
        return q;
    }
}

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
import py.com.bej.orm.entities.Motostock;

/**
 *
 * @author diego
 */
@Stateless
public class MotostockFacade extends AbstractFacade<Motostock> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;
    public static Motostock c = new Motostock();
    private Integer contador;

    @Override
    protected EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("kzx2-ejbPU");
            em = emf.createEntityManager();
        }
        return em;
    }

    public MotostockFacade() {
        super(Motostock.class);
    }

    @Override
    public List<Motostock> findRange(int[] range, Motostock c) {
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
        TypedQuery<Motostock> q = setearConsulta(c);

        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return q.getResultList();

    }

    @Override
    public int count() {
        inicio();
        Root<Motostock> rt = cq.from(Motostock.class);
        cq.select(getEm().getCriteriaBuilder().count(rt));
        Query q = getEm().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public void inicio() {
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<Motostock>) cb.createQuery(c.getClass());
        r = (Root<Motostock>) cq.from(c.getClass());
        et = r.getModel();
        this.orden = null;
    }

    @Override
    public List<Motostock> anterior(int[] range, Motostock entity) {
        range[0] = -range[1];
        if (range[0] < 0) {
            range[0] = 0;
        }
        return this.findRange(range, entity);
    }

    @Override
    public Integer getContador(Motostock entity) {
        this.contador = this.totalFiltrado(entity);
        return this.contador;
    }

    @Override
    public List<Motostock> siguiente(int[] range, Motostock entity) {
        c = entity;
        if (range[0] + range[1] < getContador(c)) {
            range[0] = range[0] + range[1];
        }

        return this.findRange(range, entity);
    }

    @Override
    public Integer totalFiltrado(Motostock entity) {
        inicio();
        List<Predicate> criteria = predicarCriteria(c);
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        TypedQuery<Motostock> q = setearConsulta(c);
        return q.getResultList().size();
    }

    @Override
    public Integer getUltimoItem(int[] range) {
        this.contador = getContador(c);
        return range[0] + range[1] > this.contador ? this.contador : range[0] + range[1];
    }

    @Override
    public boolean create(Motostock c) {
        Motostock aux = null;
        boolean res = true;
        try {
            aux = em.find(Motostock.class, c.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (aux != null) {
                if (aux.getId().equals(c.getId())) {
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
    public void guardar(Motostock c) {
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
    public List<Predicate> predicarCriteria(Motostock c) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (c.getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (c.getMoto() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("moto").get(et.getSingularAttribute("codigo", String.class))), "%"
                    + c.getMoto().getCodigo().toLowerCase() + "%"));
        }
        if (c.getMotor() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("motor", String.class))), "%"
                    + c.getMotor().toLowerCase() + "%"));
        }
        if (c.getChasis() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("chasis", String.class))), "%"
                    + c.getChasis().toLowerCase() + "%"));
        }
        if (c.getCompra() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "compra");
            criteria.add(cb.equal(r.get("compra").get("id"), p));
        }
        if (c.getVenta() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "venta");
            criteria.add(cb.equal(r.get("venta").get("id"), p));
        }
        if (c.getUbicacion() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "ubicacion");
            criteria.add(cb.equal(r.get("ubicacion").get("id"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery<Motostock> setearConsulta(Motostock c) {
        TypedQuery<Motostock> q = getEm().createQuery(cq);
        if (c.getId() != null) {
            q.setParameter("id", c.getId());
        }
        if (c.getMoto() != null) {
            q.setParameter("moto", c.getMoto().getCodigo());
        }
        if (c.getMotor() != null) {
            q.setParameter("motor", c.getMotor());
        }
        if (c.getChasis() != null) {
            q.setParameter("chasis", c.getChasis());
        }
        if (c.getCompra() != null) {
            q.setParameter("compra", c.getCompra().getId());
        }
        if (c.getVenta() != null) {
            q.setParameter("venta", c.getVenta().getId());
        }
        if (c.getUbicacion() != null) {
            q.setParameter("ubicacion", c.getUbicacion().getId());
        }
        return q;
    }
}

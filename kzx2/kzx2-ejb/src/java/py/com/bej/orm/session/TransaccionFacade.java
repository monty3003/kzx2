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
import py.com.bej.orm.entities.Transaccion;

/**
 *
 * @author diego
 */
@Stateless
public class TransaccionFacade extends AbstractFacade<Transaccion> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;
    public static Transaccion c = new Transaccion();
    private Integer contador;

    @Override
    protected EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("kzx2-ejbPU");
            em = emf.createEntityManager();
        }
        return em;
    }

    public TransaccionFacade() {
        super(Transaccion.class);
    }

    @Override
    public List<Transaccion> findRange(int[] range, Transaccion c) {
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
            if (col.equals("vendedor")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get(col).get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get(col).get("nombre")));
                }
            } else {
                if (asc) {
                    cq.orderBy(cb.asc(r.get(col)));
                } else {
                    cq.orderBy(cb.desc(r.get(col)));
                }
            }
        }
        TypedQuery<Transaccion> q = setearConsulta(c);
        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return q.getResultList();

    }

    @Override
    public int count() {
        inicio();
        Root<Transaccion> rt = cq.from(Transaccion.class);
        cq.select(getEm().getCriteriaBuilder().count(rt));
        Query q = getEm().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public void inicio() {
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<Transaccion>) cb.createQuery(c.getClass());
        r = (Root<Transaccion>) cq.from(c.getClass());
        et = r.getModel();
        this.orden = null;
    }

    @Override
    public List<Transaccion> anterior(int[] range, Transaccion entity) {
        range[0] = -range[1];
        if (range[0] < 0) {
            range[0] = 0;
        }
        return this.findRange(range, entity);
    }

    @Override
    public Integer getContador(Transaccion entity) {
        this.contador = this.totalFiltrado(entity);
        return this.contador;
    }

    @Override
    public List<Transaccion> siguiente(int[] range, Transaccion entity) {
        c = entity;
        if (range[0] + range[1] < getContador(c)) {
            range[0] = range[0] + range[1];
        }

        return this.findRange(range, entity);
    }

    @Override
    public Integer totalFiltrado(Transaccion entity) {
        inicio();
        List<Predicate> criteria = predicarCriteria(entity);
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        TypedQuery<Transaccion> q = setearConsulta(entity);
        return q.getResultList().size();
    }

    @Override
    public Integer getUltimoItem(int[] range) {
        this.contador = getContador(c);
        return range[0] + range[1] > this.contador ? this.contador : range[0] + range[1];
    }

    @Override
    public boolean create(Transaccion c) {
        boolean res = true;
        em.persist(c);
        res = true;
        return res;
    }

    @Override
    public void guardar(Transaccion c) {
        try {
            em.merge(c);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Transaccion> findByTransaccion(Integer categoria) {
        inicio();
        List<Predicate> criteria = new ArrayList<Predicate>();
        ParameterExpression<Integer> p1 =
                cb.parameter(Integer.class, "categoria");
        ParameterExpression<Character> p2 =
                cb.parameter(Character.class, "habilitado");
        List<Transaccion> res = new ArrayList<Transaccion>();
        criteria.add(cb.equal(r.get("categoria"), p1));
        criteria.add(cb.equal(r.get("habilitado"), p2));
        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        q.setParameter("categoria", categoria);
        q.setParameter("habilitado", 'S');
        res = q.getResultList();
        return res;
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
    public List<Predicate> predicarCriteria(Transaccion c) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (c.getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (c.getCodigo() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "codigo");
            criteria.add(cb.equal(r.get("codigo").get("id"), p));
        }
        if (c.getComprobante() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("comprobante")), "%"
                    + c.getComprobante().toLowerCase() + "%"));
        }
        if (c.getComprador() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "comprador");
            criteria.add(cb.equal(r.get("comprador").get("personaPK").get("id"), p));
        }
        if (c.getVendedor() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "vendedor");
            criteria.add(cb.equal(r.get("vendedor").get("personaPK").get("id"), p));
        }
        if (c.getAnulado() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "anulado");
            criteria.add(cb.equal(r.get("anulado"), p));
        }
        if (c.getSaldado() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "saldado");
            criteria.add(cb.equal(r.get("saldado"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery<Transaccion> setearConsulta(Transaccion c) {
        TypedQuery<Transaccion> q = getEm().createQuery(cq);
        if (c.getId() != null) {
            q.setParameter("id", c.getId());
        }
        if (c.getCodigo() != null) {
            q.setParameter("codigo", c.getCodigo().getId());
        }
        if (c.getComprobante() != null) {
            q.setParameter("comprobante", c.getComprobante());
        }
        if (c.getVendedor() != null) {
            q.setParameter("vendedor", c.getVendedor().getPersonaPK().getId());
        }
        if (c.getComprador() != null) {
            q.setParameter("comprador", c.getComprador().getPersonaPK().getId());
        }
        if (c.getAnulado() != null) {
            q.setParameter("anulado", c.getAnulado());
        }
        if (c.getSaldado() != null) {
            q.setParameter("saldado", c.getSaldado());
        }
        return q;
    }
}

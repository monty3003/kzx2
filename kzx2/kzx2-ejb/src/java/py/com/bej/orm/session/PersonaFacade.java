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
import py.com.bej.orm.entities.Persona;

/**
 *
 * @author diego
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;
    public static Persona c = new Persona();
    private Integer contador;

    @Override
    protected EntityManager getEm() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("kzx2-ejbPU");
            em = emf.createEntityManager();
        }
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }

    public Persona findById(Integer id) {
        Query q = getEm().createNamedQuery("Persona.findById");
        q.setParameter("id", id);
        return (Persona) q.getSingleResult();
    }

    @Override
    public List<Persona> findRange(int[] range, Persona c) {
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
            } else if (col.equals("documento")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("personaPK").get("documento")));
                } else {
                    cq.orderBy(cb.desc(r.get("personaPK").get("documento")));
                }

            } else if (col.equals("nombre")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get("nombre")));
                }
            } else if (col.equals("direccion1")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("direccion1")));
                } else {
                    cq.orderBy(cb.desc(r.get("direccion1")));
                }
            } else if (col.equals("direccion2")) {
                if (asc) {
                    cq.orderBy(cb.asc(r.get("direccion2")));
                } else {
                    cq.orderBy(cb.desc(r.get("direccion2")));
                }
            }
        }
        TypedQuery<Persona> q = setearConsulta(c);
        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return q.getResultList();

    }

    @Override
    public int count() {
        inicio();
        Root<Persona> rt = cq.from(Persona.class);
        cq.select(getEm().getCriteriaBuilder().count(rt));
        Query q = getEm().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public void inicio() {
        cb = getEm().getCriteriaBuilder();
        cq = (CriteriaQuery<Persona>) cb.createQuery(c.getClass());
        r = (Root<Persona>) cq.from(c.getClass());
        et = r.getModel();
        this.orden = null;
    }

    @Override
    public List<Persona> anterior(int[] range, Persona entity) {
        range[0] = -range[1];
        if (range[0] < 0) {
            range[0] = 0;
        }
        return this.findRange(range, entity);
    }

    @Override
    public Integer getContador(Persona entity) {
        this.contador = this.totalFiltrado(entity);
        return this.contador;
    }

    @Override
    public List<Persona> siguiente(int[] range, Persona entity) {
        c = entity;
        if (range[0] + range[1] < getContador(c)) {
            range[0] = range[0] + range[1];
        }

        return this.findRange(range, entity);
    }

    @Override
    public Integer totalFiltrado(Persona entity) {
        inicio();
        List<Predicate> criteria = predicarCriteria(entity);
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        TypedQuery<Persona> q = setearConsulta(entity);
        return q.getResultList().size();
    }

    @Override
    public Integer getUltimoItem(int[] range) {
        this.contador = getContador(c);
        return range[0] + range[1] > this.contador ? this.contador : range[0] + range[1];
    }

    @Override
    public boolean create(Persona c) {
        Persona aux = null;
        boolean res = true;
        try {
            aux = em.find(Persona.class, c.getPersonaPK());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (aux != null) {
                if (aux.getPersonaPK().getDocumento().equals(c.getPersonaPK().getDocumento())) {
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
    public void guardar(Persona c) {
        try {
            em.merge(c);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Persona> findByPersona(Integer categoria) {
        inicio();
        List<Predicate> criteria = new ArrayList<Predicate>();
        ParameterExpression<Integer> p1 =
                cb.parameter(Integer.class, "categoria");
        ParameterExpression<Character> p2 =
                cb.parameter(Character.class, "habilitado");
        List<Persona> res = new ArrayList<Persona>();
        criteria.add(cb.equal(r.get("categoria"), p1));
        criteria.add(cb.equal(r.get("habilitado"), p2));
        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        TypedQuery<Persona> q = getEm().createQuery(cq);
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
    public List<Predicate> predicarCriteria(Persona c) {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (c.getPersonaPK().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("personaPK").get("id"), p));
        }
        if (c.getPersonaPK().getDocumento() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("personaPK").get("documento")), "%"
                    + c.getPersonaPK().getDocumento() + "%"));
        }
        if (c.getNombre() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("nombre", String.class))), "%"
                    + c.getNombre().toLowerCase() + "%"));
        }
        if (c.getDireccion1() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("direccion1", String.class))), "%"
                    + c.getDireccion1().toLowerCase() + "%"));
        }
        if (c.getDireccion2() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("direccion2", String.class))), "%"
                    + c.getDireccion2().toLowerCase() + "%"));
        }
        if (c.getTelefonoFijo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("telefonoFijo", String.class))), "%"
                    + c.getTelefonoFijo().toLowerCase() + "%"));
        }
        if (c.getTelefonoMovil() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get(et.getSingularAttribute("telefonoMovil", String.class))), "%"
                    + c.getTelefonoMovil().toLowerCase() + "%"));
        }
        if (c.getHabilitado() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "habilitado");
            criteria.add(cb.equal(r.get("habilitado"), p));
        }
        if (c.getFisica() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "fisica");
            criteria.add(cb.equal(r.get("fisica"), p));
        }
        if (c.getCategoria() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "categoria");
            criteria.add(cb.equal(r.get("categoria"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery<Persona> setearConsulta(Persona c) {
        TypedQuery<Persona> q = getEm().createQuery(cq);
        if (c.getPersonaPK().getId() != null) {
            q.setParameter("id", c.getPersonaPK().getId());
        }
        if (c.getPersonaPK().getDocumento() != null) {
            q.setParameter("documento", c.getPersonaPK().getDocumento());
        }
        if (c.getNombre() != null) {
            q.setParameter("nombre", c.getNombre());
        }
        if (c.getDireccion1() != null) {
            q.setParameter("direccion1", c.getDireccion1());
        }
        if (c.getDireccion2() != null) {
            q.setParameter("direccion2", c.getDireccion2());
        }
        if (c.getTelefonoFijo() != null) {
            q.setParameter("telefonoFijo", c.getTelefonoFijo());
        }
        if (c.getTelefonoMovil() != null) {
            q.setParameter("telefonoMovil", c.getTelefonoMovil());
        }
        if (c.getHabilitado() != null) {
            q.setParameter("habilitado", c.getHabilitado());
        }
        if (c.getFisica() != null) {
            q.setParameter("fisica", c.getFisica());
        }
        if (c.getCategoria() != null) {
            q.setParameter("categoria", c.getCategoria());
        }
        return q;
    }
}

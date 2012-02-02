/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Persona;

/**
 *
 * @author diego
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> {

    public PersonaFacade() {
        super(Persona.class);
    }

    @Override
    public List<Persona> findRange() {
        inicio();
        List<Predicate> criteria = predicarCriteria();
        if (!criteria.isEmpty()) {
            if (criteria.size() == 1) {
                cq.where(criteria.get(0));
            } else {
                cq.where(cb.and(criteria.toArray(new Predicate[0])));
            }
        }
        if (getOrden().getColumna() != null && getOrden().getAsc() != null) {
            if (getOrden().getColumna().equals("categoria")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("id")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("id")));
                }
            } else if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Persona> q = setearConsulta();
        if (getContador() == null) {
            setContador(q.getResultList().size());
        }
        q.setMaxResults(getRango()[1]);
        q.setFirstResult(getRango()[0]);
        setDesde(getRango()[0]);
        setUltimo(getRango()[0] + getRango()[1] > getContador() ? getContador() : getRango()[0] + getRango()[1]);
        return q.getResultList();
    }

    @Override
    public List<Persona> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0;
        }
        return findRange();
    }

    @Override
    public List<Persona> siguiente() {
        getRango()[0] += getRango()[1];
        if (getRango()[0] > getContador()) {
            getRango()[0] = getContador() - 1;
        }
        return findRange();
    }

    @Override
    public void guardar() {
        try {
            getEm().merge(getEntity());
        } catch (Exception ex) {
            Logger.getLogger(PersonaFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Persona> findByPersona(Integer categoria) {
        inicio();
        List<Predicate> criteria = new ArrayList<Predicate>();
        ParameterExpression<Integer> p1 =
                cb.parameter(Integer.class, "categoria.id");
        ParameterExpression<Character> p2 =
                cb.parameter(Character.class, "habilitado");
        List<Persona> res = new ArrayList<Persona>();
        criteria.add(cb.equal(r.get("categoria").get("id"), p1));
        criteria.add(cb.equal(r.get("habilitado"), p2));
        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        TypedQuery<Persona> q = getEm().createQuery(cq);
        q.setParameter("categoria.id", categoria);
        q.setParameter("habilitado", 'S');
        res = q.getResultList();
        return res;
    }

    public List<Persona> findBetween(Integer inicio, Integer fin) {
        List<Persona> res = new ArrayList<Persona>();
        inicio();
        cq.where(cb.between(r.get("categoria.id"), inicio, fin));
        TypedQuery<Persona> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public Persona findById() {
        inicio();
        ParameterExpression<Integer> p1 =
                cb.parameter(Integer.class, "id");
        Persona res = null;
        cq.where(cb.equal(r.get("id"), p1));
        TypedQuery<Persona> q = getEm().createQuery(cq);
        q.setParameter("id", getEntity().getId());
        try {
            res = q.getSingleResult();
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public Persona findByDocumento(String documento) {
        inicio();
        ParameterExpression<String> p1 =
                cb.parameter(String.class, "documento");
        Persona res = null;
        cq.where(cb.equal(r.get("documento"), p1));
        TypedQuery<Persona> q = getEm().createQuery(cq);
        q.setParameter("documento", documento);
        try {
            res = q.getSingleResult();

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    /**
     * @return the col
     */
    @Override
    public List<Predicate> predicarCriteria() {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (getEntity().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "id");
            criteria.add(cb.equal(r.get("id"), p));
        }
        if (getEntity().getDocumento() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("documento")), "%"
                    + getEntity().getDocumento() + "%"));
        }
        if (getEntity().getNombre() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("nombre")), "%"
                    + getEntity().getNombre() + "%"));
        }
        if (getEntity().getDireccion1() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("direccion1")), "%"
                    + getEntity().getDireccion1() + "%"));
        }
        if (getEntity().getDireccion2() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("direccion2")), "%"
                    + getEntity().getDireccion2() + "%"));
        }
        if (getEntity().getTelefonoFijo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("telefonoFijo")), "%"
                    + getEntity().getTelefonoFijo() + "%"));
        }
        if (getEntity().getTelefonoMovil() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("telefonoMovil")), "%"
                    + getEntity().getTelefonoMovil() + "%"));
        }
        if (getEntity().getHabilitado() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "habilitado");
            criteria.add(cb.equal(r.get("habilitado"), p));
        }
        if (getEntity().getFisica() != null) {
            ParameterExpression<Character> p =
                    cb.parameter(Character.class, "fisica");
            criteria.add(cb.equal(r.get("fisica"), p));
        }
        if (getEntity().getCategoria().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "categoria.id");
            criteria.add(cb.equal(r.get("categoria").get("id"), p));
        }
        return criteria;
    }

    @Override
    public TypedQuery<Persona> setearConsulta() {
        TypedQuery<Persona> q = getEm().createQuery(cq);
        if (getEntity().getId() != null) {
            q.setParameter("id", getEntity().getId());
        }
        if (getEntity().getDocumento() != null) {
            q.setParameter("documento", getEntity().getDocumento());
        }
        if (getEntity().getNombre() != null) {
            q.setParameter("nombre", getEntity().getNombre());
        }
        if (getEntity().getDireccion1() != null) {
            q.setParameter("direccion1", getEntity().getDireccion1());
        }
        if (getEntity().getDireccion2() != null) {
            q.setParameter("direccion2", getEntity().getDireccion2());
        }
        if (getEntity().getTelefonoFijo() != null) {
            q.setParameter("telefonoFijo", getEntity().getTelefonoFijo());
        }
        if (getEntity().getTelefonoMovil() != null) {
            q.setParameter("telefonoMovil", getEntity().getTelefonoMovil());
        }
        if (getEntity().getHabilitado() != null) {
            q.setParameter("habilitado", getEntity().getHabilitado());
        }
        if (getEntity().getFisica() != null) {
            q.setParameter("fisica", getEntity().getFisica());
        }
        if (getEntity().getCategoria() != null) {
            q.setParameter("categoria.id", getEntity().getCategoria());
        }
        return q;
    }
}

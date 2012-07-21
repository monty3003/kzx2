/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.orm.entities.Moto;

/**
 *
 * @author diego
 */
@Stateless
public class MotoFacade extends AbstractFacade<Moto> {

    public MotoFacade() {
        super(Moto.class);
    }

    @Override
    public List<Moto> findRange() {
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
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("descripcion")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("descripcion")));
                }
            } else if (getOrden().getColumna().equals("fabricante")) {
                if (getOrden().getAsc()) {
                    cq.orderBy(cb.asc(r.get(getOrden().getColumna()).get("nombre")));
                } else {
                    cq.orderBy(cb.desc(r.get(getOrden().getColumna()).get("nombre")));
                }
            } else if (getOrden().getAsc()) {
                cq.orderBy(cb.asc(r.get(getOrden().getColumna())));
            } else {
                cq.orderBy(cb.desc(r.get(getOrden().getColumna())));
            }
        }
        TypedQuery<Moto> q = setearConsulta();
        if (getContador() == null) {
            cq.select(cq.from(getEntityClass()));
            cq.select(cb.count(r.get("codigo")));
            TypedQuery<Integer> q1 = setearConsulta();
            setContador(Long.parseLong("" + q1.getSingleResult()));
        }
        q.setMaxResults(getRango()[1].intValue());
        q.setFirstResult(getRango()[0].intValue());
        setDesde(getRango()[0]);
        setUltimo(getRango()[0] + getRango()[1] > getContador() ? getContador() : getRango()[0] + getRango()[1]);
        return q.getResultList();
    }

    @Override
    public List<Moto> anterior() {
        getRango()[0] -= getRango()[1];
        if (getRango()[0] < 10) {
            getRango()[0] = 0L;
        }
        return findRange();
    }

    @Override
    public List<Moto> siguiente() {
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
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Constraint Descriptor :", cv.getConstraintDescriptor());
                Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Invalid Value :", cv.getInvalidValue());
                Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Root Bean :", cv.getRootBean());
            }
        } catch (Exception ex) {
            Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Excepcion al intentar modificar los datos de la Moto", ex);
        }
    }

    @Override
    public List<Predicate> predicarCriteria() {
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (getEntity().getCodigo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("codigo")), "%"
                    + getEntity().getCodigo().toLowerCase() + "%"));
        }
        if (getEntity().getCodigoFabrica() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("codigoFabrica")), "%"
                    + getEntity().getCodigoFabrica().toLowerCase() + "%"));
        }
        if (getEntity().getMarca() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("marca")), "%"
                    + getEntity().getMarca().toLowerCase() + "%"));
        }
        if (getEntity().getModelo() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("modelo")), "%"
                    + getEntity().getModelo().toLowerCase() + "%"));
        }
        if (getEntity().getColor() != null) {
            criteria.add(cb.like(cb.lower(
                    r.get("color")), "%"
                    + getEntity().getColor().toLowerCase() + "%"));
        }
        if (getEntity().getFabricante().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "fabricante.id");
            criteria.add(cb.equal(r.get("fabricante").get("id"), p));
        }
        if (getEntity().getCategoria().getId() != null) {
            ParameterExpression<Integer> p =
                    cb.parameter(Integer.class, "categoria.id");
            criteria.add(cb.equal(r.get("categoria").get("id"), p));
        }

        return criteria;
    }

    @Override
    public TypedQuery setearConsulta() {
        TypedQuery q = getEm().createQuery(cq);
        if (getEntity().getCodigo() != null) {
            q.setParameter("codigo", getEntity().getCodigo());
        }
        if (getEntity().getCodigoFabrica() != null) {
            q.setParameter("codigoFabrica", getEntity().getCodigoFabrica());
        }
        if (getEntity().getMarca() != null) {
            q.setParameter("marca", getEntity().getMarca());
        }
        if (getEntity().getModelo() != null) {
            q.setParameter("modelo", getEntity().getModelo());
        }
        if (getEntity().getColor() != null) {
            q.setParameter("color", getEntity().getColor());
        }
        if (getEntity().getFabricante() != null) {
            q.setParameter("fabricante.id", getEntity().getFabricante().getId());
        }
        if (getEntity().getCategoria() != null) {
            q.setParameter("categoria.id", getEntity().getCategoria().getId());
        }
        return q;
    }

    public List<Moto> findGrupoByModelo() {
        List<Moto> res = null;
        List<Object[]> resultado = getEm().createQuery(
                "select m.codigo,"
                + "m.marca,"
                + "m.modelo,"
                + "m.activo,"
                + "count(m.codigo)"
                + " from Moto m"
                + " where m.activo = 'S'"
                + " group by m.modelo"
                + " order by m.marca"
                + " desc, m.modelo asc").getResultList();
        if (!resultado.isEmpty()) {
            res = new ArrayList<Moto>();
            Moto m;
            for (Object[] o : resultado) {
                m = new Moto(String.valueOf(o[0]), null, String.valueOf(o[1]), String.valueOf(o[2]), null, null, null);
                Character activo = (Character) o[3];
                m.setActivo(activo);
                res.add(m);
            }
        }
        return res;
    }
}

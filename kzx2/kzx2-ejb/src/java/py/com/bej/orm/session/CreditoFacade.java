/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Transaccion;

/**
 *
 * @author diego
 */
@Stateless
public class CreditoFacade extends AbstractFacade<Credito> {

    @EJB
    private FinanciacionFacade financiacionFacade;
    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CreditoFacade() {
        super(Credito.class);
    }

    @Override
    public List<Credito> findRange() {
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
        TypedQuery<Credito> q = setearConsulta();
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
    public List<Credito> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Credito> siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Predicate> predicarCriteria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypedQuery<Credito> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void abrirCredito(Transaccion t) {
        CategoriaFacade cat = new CategoriaFacade();
        Credito c = new Credito();
        c.setTransaccion(t);
        c.setAmortizacion(t.getCuotas());
        c.setTan((float) 0.25);
        c.setTae(c.getTan() / c.getAmortizacion());
        c.setCategoria(cat.find(60));
        c.setEstado(cat.find(0));
        c.setFechaInicio(t.getFechaOperacion());
        c.setFechaFin(new Date());
        c.setSistemaCredito(cat.find(44));
        c.setCreditoTotal(t.getEntregaInicial().add(t.getMontoCuotaIgual().multiply(new BigDecimal(t.getCuotas()))));
        c.setCapital(t.getTotalPagado().subtract(t.getEntregaInicial()));
        c.setTotalInteresesPagado(BigDecimal.ZERO);
        c.setTotalAmortizadoPagado(BigDecimal.ZERO);
        setEntity(c);
        getEm().persist(getEntity());
        getEm().flush();
        financiacionFacade = new FinanciacionFacade();
        financiacionFacade.crearCuotas(getEntity());
    }
}

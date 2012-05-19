/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.utils.CategoriaEnum;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author diego
 */
@Stateless
public class CreditoFacade extends AbstractFacade<Credito> {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private FinanciacionFacade financiacionFacade;

    public CreditoFacade() {
        super(Credito.class);
    }

    public List<Credito> findByCliente(String documento) throws Exception {
        List<Credito> res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("transaccion").get("comprador").get("documento"), documento), cb.equal(r.get("activo"), "S")));
        TypedQuery<Credito> q = getEm().createQuery(cq);
        res = q.getResultList();
        return res;
    }

    public Credito findByTransaccion(Integer transaccion) throws Exception {
        Credito res = null;
        inicio();
        cq.where(cb.and(cb.equal(r.get("transaccion").get("id"), transaccion), cb.equal(r.get("activo"), "S")));
        TypedQuery<Credito> q = getEm().createQuery(cq);
        res = q.getSingleResult();
        return res;
    }

    public Credito procesarEstadoCredito(Integer id) {
        Credito res = find(id);
        //Buscar Financiacion
        List<Financiacion> cuotas = res.getFinanciacions();
        if (!cuotas.isEmpty()) {
            BigDecimal importeCuota;
            BigDecimal interesMoratorio;
            BigDecimal porcentajeInteresMoratorio;
            BigDecimal porcentajeInteresMoratorioAcumulado;
            int mesesAtraso = 0;
            int anhosAtraso = 0;
            short cuotasAtrasadas = 0;
            Calendar c = GregorianCalendar.getInstance();
            Calendar fechaVencimiento = GregorianCalendar.getInstance();
            for (Financiacion f : cuotas) {
                if (f.getCancelado() == 'N') {
                    Date ahora = new Date();
                    if (f.getFechaVencimiento().before(ahora)) {
                        cuotasAtrasadas++;
                        importeCuota = BigDecimal.ZERO;
                        interesMoratorio = BigDecimal.ZERO;
                        //Fechas
                        fechaVencimiento.setTime(f.getFechaVencimiento());
                        anhosAtraso = c.get(Calendar.YEAR) - fechaVencimiento.get(Calendar.YEAR);
                        mesesAtraso = c.get(Calendar.MONTH) - fechaVencimiento.get(Calendar.MONTH);
                        if (anhosAtraso > 0) {
                            //Ya pasaron anhos 
                            mesesAtraso = mesesAtraso + (anhosAtraso * 12);
                        }
                        porcentajeInteresMoratorio = BigDecimal.valueOf(f.getCredito().getInteresMoratorio());
                        porcentajeInteresMoratorioAcumulado = porcentajeInteresMoratorio.multiply(BigDecimal.valueOf(mesesAtraso));
                        if (f.getFechaPago() != null && f.getTotalPagado() != null) {
                            //ya tiene un pago parcial
                            importeCuota = f.getCuotaNeta().subtract(f.getTotalPagado());
                        } else {
                            importeCuota = f.getTotalAPagar();
                        }
                        interesMoratorio = importeCuota.multiply(porcentajeInteresMoratorioAcumulado);
                        f.setInteresMora(interesMoratorio);
                        f.setCuotaNeta(importeCuota);
                        BigDecimal sinRedondear = importeCuota.add(interesMoratorio);
                        f.setTotalAPagar(sinRedondear.setScale(-2, RoundingMode.HALF_DOWN));
                        f.setAjusteRedondeo(f.getTotalAPagar().subtract(sinRedondear));
                        f.setUltimaModificacion(ahora);
                        //Actualizar Cabecera
                        res.setCuotasAtrasadas(cuotasAtrasadas);
                    }
                }
            }
            //Actualizar todo
            setEntity(res);
            guardar();
        }
        return res;
    }

    public CategoriaFacade getCategoriaFacade() {
        if (categoriaFacade == null) {
            categoriaFacade = new CategoriaFacade();
        }
        return categoriaFacade;
    }

    public FinanciacionFacade getFinanciacionFacade() {
        if (financiacionFacade == null) {
            financiacionFacade = new FinanciacionFacade();
        }
        return financiacionFacade;
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
            cq.select(cq.from(getEntityClass()));
            cq.select(cb.count(r.get("id")));
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
    public List<Credito> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Credito> siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void guardar() {
        try {
            getEm().merge(getEntity());
        } catch (ConstraintViolationException cve) {
            Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
            Logger.getLogger(CreditoFacade.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
            for (ConstraintViolation cv : lista) {
                Logger.getLogger(MotostockFacade.class.getName()).log(Level.SEVERE, "{0},{1},{2}",
                        new Object[]{cv.getConstraintDescriptor(), cv.getMessageTemplate(), cv.getMessage()});
            }
        } catch (Exception ex) {
            Logger.getLogger(CreditoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Predicate> predicarCriteria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypedQuery setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void abrirCredito(Transaccion t, Credito cr) throws Exception {
        cr.getGarante().setUltimaModificacion(new Date());
        if (cr.getGarante().getId() != null) {
            getEm().merge(cr.getGarante());
        } else {
            cr.getGarante().setActivo('S');
            cr.getGarante().setHabilitado('S');
            getEm().persist(cr.getGarante());
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(t.getFechaOperacion());
        cr.setActivo('S');
        cr.setAmortizacion(t.getCuotas());
        cal.add(Calendar.DAY_OF_MONTH, t.getDiasAPrimerVencimiento());
        cr.setFechaInicio(cal.getTime());
        cr.setEstado(new Categoria(CategoriaEnum.ABIERTO.getSymbol()));
        cal.add(Calendar.MONTH, cr.getAmortizacion());
        Date fin = cal.getTime();
        cr.setFechaFin(fin);
        cr.setTransaccion(t);
        cr.setTotalInteresesPagado(BigDecimal.ZERO);
        cr.setTotalAmortizadoPagado(BigDecimal.ZERO);

        //Generar Cuotas
        cr.setFinanciacions(getFinanciacionFacade().crearCuotas(cr));

        setEntity(cr);
        create();
    }
}

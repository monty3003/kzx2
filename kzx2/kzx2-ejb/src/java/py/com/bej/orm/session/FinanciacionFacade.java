/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.session;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Financiacion;

/**
 *
 * @author diego
 */
@Stateless
public class FinanciacionFacade extends AbstractFacade<Financiacion> {

    @PersistenceContext(unitName = "kzx2-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public FinanciacionFacade() {
        super(Financiacion.class);
    }

    @Override
    public List<Financiacion> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Financiacion> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Financiacion> siguiente() {
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
    public TypedQuery<Financiacion> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void crearCuotas(Credito credito) {
        BigDecimal saldoCapital = credito.getCapital();
        int contarCuotas = credito.getAmortizacion();
        BigDecimal saldoFinal = saldoCapital;
        BigDecimal interesAnualEfectivo = new BigDecimal(credito.getTae());
        BigDecimal interesAnualNeto = new BigDecimal(credito.getTan());
        BigDecimal cuotaNeta = credito.getTransaccion().getMontoCuotaIgual();
        //credito.getCapital().multiply(interesAnualNeto.divide(new BigDecimal(12), 4, RoundingMode.HALF_UP)).add(credito.getCapital());
        BigDecimal totalAPagar = cuotaNeta.setScale(-2, RoundingMode.HALF_DOWN);
        BigDecimal ajusteRedondeo = totalAPagar.subtract(cuotaNeta);
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTime(credito.getFechaInicio());
        for (short i = 0; i <= credito.getAmortizacion(); i++) {
            Financiacion f = new Financiacion();
            f.setCredito(credito);
            f.setNumeroCuota((short) (i + 1));
            f.setCuotaNeta(cuotaNeta);
            f.setTotalAPagar(totalAPagar);
            f.setAjusteRedondeo(ajusteRedondeo);
            fecha.add(Calendar.DAY_OF_MONTH, 30);
            f.setFechaVencimiento(fecha.getTime());
            if (credito.getCategoria().getId().equals(40)) {
                f.setCapital(credito.getCapital());
            } else {
                if (credito.getSistemaCredito().getId().equals(42)) {
                    BigDecimal x = BigDecimal.ONE.add(interesAnualEfectivo);
                    BigDecimal y = x.pow(contarCuotas, MathContext.DECIMAL64);
                    BigDecimal z = y.subtract(BigDecimal.ONE);
                    BigDecimal aux = z.divide(interesAnualEfectivo, 8, RoundingMode.HALF_UP);
                    BigDecimal res = saldoFinal.divide(aux, 8, RoundingMode.HALF_UP);
                    f.setCapital(res);
                    f.setInteres(cuotaNeta.subtract(res));
                    saldoFinal = saldoFinal.subtract(res);
                    contarCuotas--;
                }
            }
            getEm().persist(f);
        }

    }
}

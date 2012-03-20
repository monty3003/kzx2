/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.base.prod.entity.Vmplanmoto;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class PlanMotosProduccionFacade extends AbstractFacade<Vmplanmoto> {

    public PlanMotosProduccionFacade() {
        super(Vmplanmoto.class);
    }

    public List<Vmplanmoto> findByIdVenta(Integer idVenta) {
        List<Vmplanmoto> res = new ArrayList<Vmplanmoto>();
        List<Object[]> resx;
        Query q = getEm().createNativeQuery("SELECT IdPlanMoto,CuotaNumero,MontoCuota,MontoInteresMensual,fechaVencimiento,"
                + "FechaPago,Guardado,Anulado,IdVenta"
                + " from BDBEJ.dbo.Vmplanmoto where idVenta =? order by CuotaNumero ASC");
        q.setParameter(1, idVenta);
        resx = q.getResultList();
        if (resx != null && !resx.isEmpty()) {

            for (Object[] reg : resx) {
                res.add(new Vmplanmoto(reg[0] == null ? null : (Integer) reg[0],
                        reg[1] == null ? null : (Integer) reg[1],
                        reg[2] == null ? null : (Integer) reg[2],
                        reg[3] == null ? null : (Integer) reg[3],
                        reg[4] == null ? null : (Date) reg[4],
                        reg[5] == null ? null : (Date) reg[5],
                        reg[6] == null ? null : (Boolean) reg[6],
                        reg[7] == null ? null : (Boolean) reg[7],
                        reg[8] == null ? null : (Integer) reg[8]));
            }
        }
        return res;
    }

    @Override
    public List<Vmplanmoto> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmplanmoto> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmplanmoto> siguiente() {
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
    public TypedQuery<Vmplanmoto> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.base.prod.entity.Vmpagomotos;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class PagoMotosProduccionFacade extends AbstractFacade<Vmpagomotos> {

    public PagoMotosProduccionFacade() {
        super(Vmpagomotos.class);
    }

    public List<Vmpagomotos> findByIdVenta(Integer idVenta) {
        List<Vmpagomotos> res = new ArrayList<Vmpagomotos>();
        List<Object[]> resx;
        resx = getEm().createNativeQuery("select NumeroRecibo,Cedula_Ruc,CodEmpleado,\"Fecha Pago\","
                + "Hora,NumCuotas,ULTIMOPAGO,\"Monto Entrega\",MONTOSALDO,SALDOMOMENTO,Concepto,"
                + "GUARDADO,ANULADO,IdVentas"
                + " from BDBEJ.dbo.Vmpagomotos "
                + "where \"Monto Entrega\">0"
                + " and \"Fecha Pago\" IS NOT NULL"
                + " and Anulado=0"
                + " and idVentas=?"
                + " order by \"Fecha Pago\" asc, NumeroRecibo ASC").setParameter(1, idVenta).getResultList();
        if (resx != null && !resx.isEmpty()) {

            for (Object[] reg : resx) {
                res.add(new Vmpagomotos(reg[0] == null ? null : (Integer) reg[0],
                        reg[1] == null ? null : (String) reg[1],
                        reg[2] == null ? null : (Integer) reg[2],
                        reg[3] == null ? null : (Date) reg[3],
                        reg[4] == null ? null : (Date) reg[4],
                        reg[5] == null ? null : (String) reg[5],
                        reg[6] == null ? null : (Date) reg[6],
                        reg[7] == null ? null : (Integer) reg[7],
                        reg[8] == null ? null : (Integer) reg[8],
                        reg[9] == null ? null : (Integer) reg[9],
                        reg[10] == null ? null : (String) reg[10],
                        reg[11] == null ? null : (Integer) reg[11],
                        reg[12] == null ? null : (Boolean) reg[12],
                        reg[13] == null ? null : (Integer) reg[13]));
            }
        }
        return res;
    }

    @Override
    public List<Vmpagomotos> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmpagomotos> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmpagomotos> siguiente() {
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
    public TypedQuery<Vmpagomotos> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

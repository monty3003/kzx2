/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import py.com.bej.base.prod.entity.Vmventamotos;

/**
 *
 * @author Diego_M
 */
@Stateless
@LocalBean
public class VentaMotosProduccionFacade extends AbstractFacade<Vmventamotos> {

    public VentaMotosProduccionFacade() {
        super(Vmventamotos.class);
    }

    public List<Vmventamotos> findByIdVentaOrdenado() {
        return getEm().createQuery("select new py.com.bej.base.prod.entity.Vmventamotos("
                + " v.idVenta, v.anulado, v.cancelado, v.cedulaRuc, v.chapa, v.codigoEmpleado,"
                + " v.compFecha, v.compObservacion, v.compResfuerzo, v.conCompromiso, v.entregaMoto, v.entregado,"
                + " v.fechaEntrega, v.fechaVenta, v.guardado, v.idCodeudor, v.idTransaccion, v.montoCuotas,"
                + " v.montoLetras, v.numeroCuotas, v.observacion, v.precioContado, v.precioMoto, v.refuerzo,"
                + " v.salAcMoto, v.saldoMoto, v.totalPagos, v.ubicacion, v.idMoto.idMoto)"
                + " from Vmventamotos v "
                + " where v.idMoto.idMoto IS NOT NULL and v.idVenta != 176"
                + " order by v.idVenta asc").getResultList();
//        return getEm().createNativeQuery("SELECT IdVenta, Anulado, Cancelado, Cedula_Ruc, Chapa, CodigoEmpleado,"
//                + " CompFecha, CompObservacion, CompResfuerzo, ConCompromiso, EntregaMoto, Entregado,"
//                + " FechaEntrega, FechaVenta, Guardado, IdCodeudor, IdTransaccion, MontoCuotas,"
//                + " MontoLetras, NumeroCuotas, Observacion, PrecioContado, PrecioMoto, Refuerzo,"
//                + " SSMA_TimeStamp, SalAcMoto, SaldoMoto, TotalPagos, ubicacion, IdMoto"
//                + " FROM BDBEJ.dbo.VMVENTAMOTOS ORDER BY IdVenta ASC").getResultList();
    }

    @Override
    public List<Vmventamotos> findRange() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmventamotos> anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vmventamotos> siguiente() {
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
    public TypedQuery<Vmventamotos> setearConsulta() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

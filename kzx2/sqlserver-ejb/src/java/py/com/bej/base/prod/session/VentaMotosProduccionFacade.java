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
                + " where v.idMoto.idMoto IS NOT NULL and v.idVenta != 176 and (v.idTransaccion = 1 or v.idTransaccion = 2)"
                + " order by v.idVenta asc").getResultList();
    }

    public Vmventamotos findByIdVentaOrdenado(Integer nVenta) throws Exception {
        return (Vmventamotos) getEm().createQuery("select new py.com.bej.base.prod.entity.Vmventamotos("
                + " v.idVenta, v.anulado, v.cancelado, v.cedulaRuc, v.chapa, v.codigoEmpleado,"
                + " v.compFecha, v.compObservacion, v.compResfuerzo, v.conCompromiso, v.entregaMoto, v.entregado,"
                + " v.fechaEntrega, v.fechaVenta, v.guardado, v.idCodeudor, v.idTransaccion, v.montoCuotas,"
                + " v.montoLetras, v.numeroCuotas, v.observacion, v.precioContado, v.precioMoto, v.refuerzo,"
                + " v.salAcMoto, v.saldoMoto, v.totalPagos, v.ubicacion, v.idMoto.idMoto)"
                + " from Vmventamotos v where v.idVenta =:idVenta"
                + " order by v.idVenta asc").setParameter("idVenta", nVenta).getSingleResult();
    }

    public List<Vmventamotos> findByIdVentaCreditoOrdenado() {
                return getEm().createQuery("select new py.com.bej.base.prod.entity.Vmventamotos("
                + " v.idVenta, v.anulado, v.cancelado, v.cedulaRuc, v.chapa, v.codigoEmpleado,"
                + " v.compFecha, v.compObservacion, v.compResfuerzo, v.conCompromiso, v.entregaMoto, v.entregado,"
                + " v.fechaEntrega, v.fechaVenta, v.guardado, v.idCodeudor, v.idTransaccion, v.montoCuotas,"
                + " v.montoLetras, v.numeroCuotas, v.observacion, v.precioContado, v.precioMoto, v.refuerzo,"
                + " v.salAcMoto, v.saldoMoto, v.totalPagos, v.ubicacion, v.idMoto.idMoto)"
                + " from Vmventamotos v "
                + " where v.idMoto.idMoto IS NOT NULL"
                + " and v.idVenta != 176"
                + " and v.idVenta != 910"
                + " and v.idTransaccion = 2"
                + " and v.anulado <> true"
                + " order by v.idVenta asc").getResultList();
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

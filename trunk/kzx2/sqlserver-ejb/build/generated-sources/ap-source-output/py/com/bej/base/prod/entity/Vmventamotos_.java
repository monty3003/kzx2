package py.com.bej.base.prod.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.bej.base.prod.entity.Vmmotostock;
import py.com.bej.base.prod.entity.Vmpagomotos;
import py.com.bej.base.prod.entity.Vmplanmoto;
import py.com.bej.base.prod.entity.Vmresfuerzos;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-23T09:46:16")
@StaticMetamodel(Vmventamotos.class)
public class Vmventamotos_ { 

    public static volatile SingularAttribute<Vmventamotos, String> compObservacion;
    public static volatile SingularAttribute<Vmventamotos, Vmmotostock> idMoto;
    public static volatile SingularAttribute<Vmventamotos, Boolean> anulado;
    public static volatile SingularAttribute<Vmventamotos, Date> fechaVenta;
    public static volatile SingularAttribute<Vmventamotos, Date> compFecha;
    public static volatile SingularAttribute<Vmventamotos, Integer> idCodeudor;
    public static volatile CollectionAttribute<Vmventamotos, Vmpagomotos> vmpagomotosCollection;
    public static volatile SingularAttribute<Vmventamotos, String> observacion;
    public static volatile SingularAttribute<Vmventamotos, Integer> totalPagos;
    public static volatile CollectionAttribute<Vmventamotos, Vmplanmoto> vmplanmotoCollection;
    public static volatile SingularAttribute<Vmventamotos, Boolean> entregado;
    public static volatile SingularAttribute<Vmventamotos, Integer> ubicacion;
    public static volatile SingularAttribute<Vmventamotos, Boolean> guardado;
    public static volatile SingularAttribute<Vmventamotos, Integer> refuerzo;
    public static volatile SingularAttribute<Vmventamotos, String> cedulaRuc;
    public static volatile SingularAttribute<Vmventamotos, Integer> montoCuotas;
    public static volatile SingularAttribute<Vmventamotos, Integer> precioMoto;
    public static volatile SingularAttribute<Vmventamotos, Integer> idTransaccion;
    public static volatile SingularAttribute<Vmventamotos, String> montoLetras;
    public static volatile SingularAttribute<Vmventamotos, byte[]> sSMATimeStamp;
    public static volatile SingularAttribute<Vmventamotos, Integer> numeroCuotas;
    public static volatile CollectionAttribute<Vmventamotos, Vmresfuerzos> vmresfuerzosCollection;
    public static volatile SingularAttribute<Vmventamotos, Integer> codigoEmpleado;
    public static volatile SingularAttribute<Vmventamotos, Boolean> chapa;
    public static volatile SingularAttribute<Vmventamotos, Integer> salAcMoto;
    public static volatile SingularAttribute<Vmventamotos, Integer> idVenta;
    public static volatile SingularAttribute<Vmventamotos, Integer> plan;
    public static volatile SingularAttribute<Vmventamotos, Integer> precioContado;
    public static volatile SingularAttribute<Vmventamotos, Integer> entregaMoto;
    public static volatile SingularAttribute<Vmventamotos, Date> fechaEntrega;
    public static volatile SingularAttribute<Vmventamotos, Boolean> conCompromiso;
    public static volatile SingularAttribute<Vmventamotos, Integer> compResfuerzo;
    public static volatile SingularAttribute<Vmventamotos, Integer> saldoMoto;
    public static volatile SingularAttribute<Vmventamotos, Boolean> cancelado;

}
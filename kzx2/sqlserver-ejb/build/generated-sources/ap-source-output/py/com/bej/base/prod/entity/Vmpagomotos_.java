package py.com.bej.base.prod.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.bej.base.prod.entity.Vmventamotos;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-23T09:46:16")
@StaticMetamodel(Vmpagomotos.class)
public class Vmpagomotos_ { 

    public static volatile SingularAttribute<Vmpagomotos, Date> ultimopago;
    public static volatile SingularAttribute<Vmpagomotos, String> concepto;
    public static volatile SingularAttribute<Vmpagomotos, Integer> saldomomento;
    public static volatile SingularAttribute<Vmpagomotos, String> cedulaRuc;
    public static volatile SingularAttribute<Vmpagomotos, Integer> codEmpleado;
    public static volatile SingularAttribute<Vmpagomotos, String> numCuotas;
    public static volatile SingularAttribute<Vmpagomotos, Boolean> anulado;
    public static volatile SingularAttribute<Vmpagomotos, byte[]> sSMATimeStamp;
    public static volatile SingularAttribute<Vmpagomotos, Vmventamotos> idVentas;
    public static volatile SingularAttribute<Vmpagomotos, Date> hora;
    public static volatile SingularAttribute<Vmpagomotos, Integer> montoEntrega;
    public static volatile SingularAttribute<Vmpagomotos, String> enletras;
    public static volatile SingularAttribute<Vmpagomotos, Date> fechaPago;
    public static volatile SingularAttribute<Vmpagomotos, Integer> numeroRecibo;
    public static volatile SingularAttribute<Vmpagomotos, Integer> montosaldo;
    public static volatile SingularAttribute<Vmpagomotos, Integer> guardado;

}
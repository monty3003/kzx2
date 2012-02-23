package py.com.bej.base.prod.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.bej.base.prod.entity.Vmventamotos;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-23T09:46:16")
@StaticMetamodel(Vmplanmoto.class)
public class Vmplanmoto_ { 

    public static volatile SingularAttribute<Vmplanmoto, Vmventamotos> idVenta;
    public static volatile SingularAttribute<Vmplanmoto, Integer> montoInteresMensual;
    public static volatile SingularAttribute<Vmplanmoto, Date> fechaVencimiento;
    public static volatile SingularAttribute<Vmplanmoto, Boolean> anulado;
    public static volatile SingularAttribute<Vmplanmoto, Integer> idPlanMoto;
    public static volatile SingularAttribute<Vmplanmoto, byte[]> sSMATimeStamp;
    public static volatile SingularAttribute<Vmplanmoto, Integer> cuotaNumero;
    public static volatile SingularAttribute<Vmplanmoto, Date> fechaPago;
    public static volatile SingularAttribute<Vmplanmoto, Integer> montoCuota;
    public static volatile SingularAttribute<Vmplanmoto, Boolean> guardado;

}
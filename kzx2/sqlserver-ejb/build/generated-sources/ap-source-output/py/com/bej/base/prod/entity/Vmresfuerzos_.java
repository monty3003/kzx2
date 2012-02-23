package py.com.bej.base.prod.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.bej.base.prod.entity.Vmventamotos;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-16T17:57:39")
@StaticMetamodel(Vmresfuerzos.class)
public class Vmresfuerzos_ { 

    public static volatile SingularAttribute<Vmresfuerzos, Integer> idResfuerzo;
    public static volatile SingularAttribute<Vmresfuerzos, Vmventamotos> idVentas;
    public static volatile SingularAttribute<Vmresfuerzos, Date> fecha;
    public static volatile SingularAttribute<Vmresfuerzos, Integer> codEmpleado;
    public static volatile SingularAttribute<Vmresfuerzos, Boolean> anulado;
    public static volatile SingularAttribute<Vmresfuerzos, Integer> montoResfuerzo;
    public static volatile SingularAttribute<Vmresfuerzos, byte[]> sSMATimeStamp;
    public static volatile SingularAttribute<Vmresfuerzos, Boolean> guardado;

}
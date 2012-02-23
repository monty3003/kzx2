package py.com.bej.base.prod.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.bej.base.prod.entity.Vmcompras;
import py.com.bej.base.prod.entity.Vmventamotos;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-23T09:46:16")
@StaticMetamodel(Vmmotostock.class)
public class Vmmotostock_ { 

    public static volatile SingularAttribute<Vmmotostock, String> codigoMoto;
    public static volatile SingularAttribute<Vmmotostock, Integer> costoDolar;
    public static volatile SingularAttribute<Vmmotostock, Vmcompras> nComp;
    public static volatile SingularAttribute<Vmmotostock, String> ubicacion2;
    public static volatile SingularAttribute<Vmmotostock, Integer> idMoto;
    public static volatile SingularAttribute<Vmmotostock, String> numChasis;
    public static volatile CollectionAttribute<Vmmotostock, Vmventamotos> vmventamotosCollection;
    public static volatile SingularAttribute<Vmmotostock, Integer> costoGuarani;
    public static volatile SingularAttribute<Vmmotostock, Boolean> vendido;
    public static volatile SingularAttribute<Vmmotostock, String> numMotor;
    public static volatile SingularAttribute<Vmmotostock, Integer> nVenta;
    public static volatile SingularAttribute<Vmmotostock, byte[]> sSMATimeStamp;
    public static volatile SingularAttribute<Vmmotostock, Integer> ventaGuarani;
    public static volatile SingularAttribute<Vmmotostock, Integer> cotizacionDolar;
    public static volatile SingularAttribute<Vmmotostock, Integer> ventaDolar;

}
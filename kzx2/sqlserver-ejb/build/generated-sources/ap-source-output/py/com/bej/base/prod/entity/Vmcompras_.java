package py.com.bej.base.prod.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.bej.base.prod.entity.Vmmotostock;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-23T09:46:16")
@StaticMetamodel(Vmcompras.class)
public class Vmcompras_ { 

    public static volatile SingularAttribute<Vmcompras, Date> primerVenc;
    public static volatile SingularAttribute<Vmcompras, Date> fecha;
    public static volatile SingularAttribute<Vmcompras, Integer> numeroFactura;
    public static volatile SingularAttribute<Vmcompras, Boolean> anulado;
    public static volatile SingularAttribute<Vmcompras, byte[]> sSMATimeStamp;
    public static volatile SingularAttribute<Vmcompras, Boolean> entrega;
    public static volatile SingularAttribute<Vmcompras, Short> cuotas;
    public static volatile SingularAttribute<Vmcompras, Integer> subTotal;
    public static volatile SingularAttribute<Vmcompras, Integer> ivaCf;
    public static volatile CollectionAttribute<Vmcompras, Vmmotostock> vmmotostockCollection;
    public static volatile SingularAttribute<Vmcompras, String> codProveedor;
    public static volatile SingularAttribute<Vmcompras, Integer> idCompras;
    public static volatile SingularAttribute<Vmcompras, Boolean> guardado;
    public static volatile SingularAttribute<Vmcompras, Integer> montoTotal;

}
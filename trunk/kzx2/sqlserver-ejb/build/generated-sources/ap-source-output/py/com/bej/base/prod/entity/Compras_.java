package py.com.bej.base.prod.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-16T17:57:39")
@StaticMetamodel(Compras.class)
public class Compras_ { 

    public static volatile SingularAttribute<Compras, Date> fecha;
    public static volatile SingularAttribute<Compras, Integer> numeroFactura;
    public static volatile SingularAttribute<Compras, Integer> subTotal;
    public static volatile SingularAttribute<Compras, Integer> ivaCf;
    public static volatile SingularAttribute<Compras, String> codProveedor;
    public static volatile SingularAttribute<Compras, Integer> idCompras;
    public static volatile SingularAttribute<Compras, Integer> montoTotal;

}
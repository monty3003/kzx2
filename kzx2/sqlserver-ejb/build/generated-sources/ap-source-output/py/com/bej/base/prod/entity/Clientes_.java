package py.com.bej.base.prod.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.bej.base.prod.entity.ClientesPK;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-16T17:57:39")
@StaticMetamodel(Clientes.class)
public class Clientes_ { 

    public static volatile SingularAttribute<Clientes, String> direccion;
    public static volatile SingularAttribute<Clientes, String> telefono;
    public static volatile SingularAttribute<Clientes, Date> fechaIngreso;
    public static volatile SingularAttribute<Clientes, byte[]> sSMATimeStamp;
    public static volatile SingularAttribute<Clientes, Date> fechaSesantia;
    public static volatile SingularAttribute<Clientes, String> movil;
    public static volatile SingularAttribute<Clientes, Integer> categoria;
    public static volatile SingularAttribute<Clientes, ClientesPK> clientesPK;
    public static volatile SingularAttribute<Clientes, String> observacion;
    public static volatile SingularAttribute<Clientes, Float> estado;
    public static volatile SingularAttribute<Clientes, Boolean> desafectado;
    public static volatile SingularAttribute<Clientes, String> email;
    public static volatile SingularAttribute<Clientes, String> nombreApellido;
    public static volatile SingularAttribute<Clientes, Boolean> guardado;

}
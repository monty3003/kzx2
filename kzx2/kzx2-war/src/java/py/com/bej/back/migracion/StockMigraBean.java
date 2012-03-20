/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.back.migracion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import py.com.bej.base.prod.entity.Vmmotostock;
import py.com.bej.base.prod.session.MotostockProduccionFacade;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Plan;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.entities.Ubicacion;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.session.UbicacionFacade;
import py.com.bej.web.beans.view.MotostockBean;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class StockMigraBean implements Serializable {

    @EJB
    private MotostockProduccionFacade motostockProduccionFacade;
    @EJB
    private UbicacionFacade ubicacionFacade;
    @EJB
    private MotoFacade motoFacade;
    @EJB
    private TransaccionFacade transaccionFacade;
    @EJB
    private MotostockFacade facade;
    private static final long serialVersionUID = 1L;
    public final static Logger LOGGER = Logger.getLogger(StockMigraBean.class.getName());

    /** Creates a new instance of StockMigraBean */
    public StockMigraBean() {
    }

    public MotostockFacade getFacade() {
        return facade;
    }

    public MotoFacade getMotoFacade() {
        return motoFacade;
    }

    public MotostockProduccionFacade getMotostockProduccionFacade() {
        return motostockProduccionFacade;
    }

    public TransaccionFacade getTransaccionFacade() {
        return transaccionFacade;
    }

    public UbicacionFacade getUbicacionFacade() {
        return ubicacionFacade;
    }

    public String importar() {

        return "importarStock";
    }

    public String migrarStock() throws Exception {
        int total = 0;
        motostockProduccionFacade = new MotostockProduccionFacade();
        List<Vmmotostock> listaStockProduccion = null;
        listaStockProduccion = motostockProduccionFacade.findByIdMotoOrdenado();
        if (listaStockProduccion != null && !listaStockProduccion.isEmpty()) {
            LOGGER.log(Level.INFO, "Hay {0} motos", listaStockProduccion.size());
            Random r = new Random(100000000L);
            int contador = 1;
            try {
                for (Vmmotostock vmmotostock : listaStockProduccion) {
                    if (!vmmotostock.getIdMoto().equals(contador)) {
                        LOGGER.log(Level.INFO, "Se va a agregar una moto en el lugar {0}", contador);
                        Motostock m = new Motostock(null, new Moto("HPDLX65R"), String.valueOf(r.nextInt()), String.valueOf(r.nextInt()), new Transaccion(1), null, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(10), new Ubicacion(1), new Plan(2), 'N', new Date());
                        getFacade().create(m);
                        contador++;
                    }
                    LOGGER.log(Level.INFO, "     La moto nro {0}", vmmotostock.getIdMoto());
                    Motostock mst = new Motostock();
                    if (vmmotostock.getIdMoto() != null && vmmotostock.getIdMoto() > 0) {
                        mst.setIdAnterior(vmmotostock.getIdMoto());
                    } else {
                        mst.setIdAnterior(9999999);
                    }
                    if (vmmotostock.getCodigoMoto() != null
                            && !vmmotostock.getCodigoMoto().trim().equals("")
                            && !vmmotostock.getCodigoMoto().trim().equals("FD´`'OKYO MDXR'")) {
                        mst.setMoto(getMotoFacade().find(vmmotostock.getCodigoMoto().trim()));
                    } else {
                        mst.setMoto(getMotoFacade().find("HPDLX65R"));
                    }
                    if (vmmotostock.getNumMotor() != null) {
                        mst.setMotor(vmmotostock.getNumMotor());
                    }
                    if (vmmotostock.getNumChasis() != null) {
                        mst.setChasis(vmmotostock.getNumChasis());
                    } else {
                        mst.setChasis("" + r.nextInt());
                    }
                    if (vmmotostock.getUbicacion2() != null && !vmmotostock.getUbicacion2().trim().equals("")) {
                        if (vmmotostock.getUbicacion2().trim().equalsIgnoreCase("DEPOSITO CTRAL.")) {
                            mst.setUbicacion(getUbicacionFacade().findByDescripcion("DEPOSITO CENTRAL"));
                        } else {
                            mst.setUbicacion(getUbicacionFacade().findByDescripcion(vmmotostock.getUbicacion2().trim()));
                        }
                    } else {
                        mst.setUbicacion(new Ubicacion(1));
                    }
                    if (vmmotostock.getNComp() == null) {
                        mst.setCompra(new Transaccion(1));
                    } else {
                        mst.setCompra(getTransaccionFacade().find(vmmotostock.getNComp().getIdCompras()));
                    }
                    if (vmmotostock.getCostoGuarani() != null) {
                        BigDecimal precioCosto = BigDecimal.valueOf(vmmotostock.getCostoGuarani());
                        if (precioCosto.doubleValue() < 10) {
                            precioCosto = BigDecimal.valueOf(10);
                        }
                        mst.setCosto(precioCosto);
                        BigDecimal[] precio = MotostockBean.calcularPrecios(mst.getCosto());
                        mst.setPrecioBase(precio[0]);
                        mst.setPrecioContado(precio[1]);
                    } else {
                        mst.setCosto(BigDecimal.valueOf(10));
                        mst.setPrecioBase(BigDecimal.valueOf(10));
                        mst.setPrecioContado(BigDecimal.valueOf(10));
                    }
                    mst.setPlan(new Plan(2));
                    if (vmmotostock.getVendido()) {
                        mst.setActivo('N');
                    } else {
                        mst.setActivo('S');
                    }
                    mst.setUltimaModificacion(new Date());
                    getFacade().create(mst);
                    contador++;
                }
            } catch (Exception ex) {
                Logger.getLogger(StockMigraBean.class.getName()).log(Level.SEVERE, "Ocurrio una excepcion al intentar migrar un registro del stock", ex);
            }
        }
        setInfoMessage(null, "Total de registros importados: " + total);
        return "motostock";
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}

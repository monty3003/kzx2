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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import py.com.bej.base.prod.entity.Vmmotostock;
import py.com.bej.base.prod.entity.Vmventamotos;
import py.com.bej.base.prod.session.MotostockProduccionFacade;
import py.com.bej.base.prod.session.VentaMotosProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Plan;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.entities.Ubicacion;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.session.UbicacionFacade;
import py.com.bej.orm.utils.CategoriaEnum;
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

    public String migrarStock() {
        int total = 0;
        Float cuarenta = new Float(0.04F);
        Float veinte = new Float(0.02F);
        motostockProduccionFacade = new MotostockProduccionFacade();
        List<Vmmotostock> listaStockProduccion = null;
        listaStockProduccion = motostockProduccionFacade.findByIdMotoOrdenado();
        if (listaStockProduccion != null && !listaStockProduccion.isEmpty()) {
            LOGGER.log(Level.FINE, "Hay {0} motos", listaStockProduccion.size());
            Random r = new Random(100000000L);
            int contador = 1;
            Vmventamotos venta = null;
            try {
                for (Vmmotostock vmmotostock : listaStockProduccion) {
                    if (!vmmotostock.getIdMoto().equals(contador)) {
                        LOGGER.log(Level.FINE, "Se va a agregar una moto en el lugar {0}", contador);
                        Motostock m = new Motostock(null, new Moto("HPDLX65R"), String.valueOf(r.nextInt()), String.valueOf(r.nextInt()), new Transaccion(1), null, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(10), new Float(0.02F), new Ubicacion(1), new Plan(2), new Categoria(CategoriaEnum.VENDIDO_ENTREGADO.getSymbol()), 'N', new Date());
                        getFacade().create(m);
                        contador++;
                    }
                    LOGGER.log(Level.FINE, "     La moto nro {0}", vmmotostock.getIdMoto());
                    Motostock mst = new Motostock();
                    if (vmmotostock.getIdMoto() != null && vmmotostock.getIdMoto() > 0) {
                        mst.setIdAnterior(vmmotostock.getIdMoto());
                    } else {
                        mst.setIdAnterior(9999999);
                    }
                    //Gravamen. Hay un aporcion de 4598 a 4708 que tiene gravamen = 40%. El resto es 20%

                    if (mst.getIdAnterior() < 4598 || mst.getIdAnterior() > 4708) {
                        mst.setGravamen(veinte);
                    } else {
                        mst.setGravamen(cuarenta);
                        LOGGER.log(Level.FINE, "La moto Nro {0} esta con el gravamen de 40%", mst.getIdAnterior());
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
                        if (vmmotostock.getUbicacion2().trim().equals("DEPOSITO CTRAL.")) {
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
                    mst.setActivo('S');
                    if (vmmotostock.getVendido()) {
                        if (vmmotostock.getNVenta() == null) {
                            mst.setEstado(new Categoria(CategoriaEnum.VENDIDO_SIN_DATOS.getSymbol()));
                            mst.setActivo('N');
                        } else if (vmmotostock.getNVenta() == 0) {
                            mst.setEstado(new Categoria(CategoriaEnum.VENDIDO_SIN_DATOS.getSymbol()));
                            mst.setActivo('N');
                        } else {
                            venta = new VentaMotosProduccionFacade().findByIdVentaOrdenado(vmmotostock.getNVenta());
                            if (venta.getIdTransaccion() > 2) {
                                mst.setEstado(new Categoria(CategoriaEnum.COMPRADO_DEVUELTO.getSymbol()));
                            } else {
                                mst.setEstado(new Categoria(CategoriaEnum.VENDIDO_ENTREGADO.getSymbol()));
                                mst.setActivo('S');
                            }
                        }
                    } else {
                        if (vmmotostock.getNVenta() == null) {
                            mst.setEstado(new Categoria(CategoriaEnum.VENDIDO_SIN_DATOS.getSymbol()));
                            mst.setActivo('N');
                        } else if (vmmotostock.getNVenta() == 0) {
                            mst.setEstado(new Categoria(CategoriaEnum.GUARDADO.getSymbol()));
                            mst.setActivo('S');
                        } else {
                            venta = new VentaMotosProduccionFacade().findByIdVentaOrdenado(vmmotostock.getNVenta());
                            if (venta.getIdTransaccion() > 2) {
                                mst.setEstado(new Categoria(CategoriaEnum.COMPRADO_DEVUELTO.getSymbol()));
                            } else {
                                mst.setEstado(new Categoria(CategoriaEnum.VENDIDO_SIN_DATOS.getSymbol()));
                                mst.setActivo('S');
                            }
                        }
                    }
                    mst.setUltimaModificacion(new Date());
                    getFacade().create(mst);
                    contador++;
                }

            } catch (ConstraintViolationException cve) {
                Set<ConstraintViolation<?>> lista = cve.getConstraintViolations();
                Logger.getLogger(StockMigraBean.class.getName()).log(Level.SEVERE, "Excepcion de tipo Constraint Violation.", cve);
                for (ConstraintViolation cv : lista) {
                    Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Constraint Descriptor :", cv.getConstraintDescriptor());
                    Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Invalid Value :", cv.getInvalidValue());
                    Logger.getLogger(MotoFacade.class.getName()).log(Level.SEVERE, "Root Bean :", cv.getRootBean());
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

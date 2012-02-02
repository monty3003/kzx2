/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.entities.Ubicacion;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.session.UbicacionFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class MotostockBean extends AbstractPageBean<Motostock> {

    @EJB
    private UbicacionFacade ubicacionFacade;
    @EJB
    private MotoFacade motoFacade;
    @EJB
    private TransaccionFacade transaccionFacade;
    @EJB
    private MotostockFacade facade;
    private Motostock stock;
    private List<Moto> listaMotos;
    private List<SelectItem> listaMoto;
    private List<Transaccion> listaCompras;
    private List<SelectItem> listaCompra;
    private List<Transaccion> listaVentas;
    private List<SelectItem> listaVenta;
    private List<Ubicacion> listaUbicaciones;
    private List<SelectItem> listaUbicacion;
    //Motostock
    private String id;
    private String moto;
    private String motor;
    private String chasis;
    private Integer compra;
    private Integer venta;
    private String costo;
    private String precioVenta;
    private Integer ubicacion;

    /** Creates a new instance of MotostockBean */
    public MotostockBean() {
    }

    @Override
    public String listar() {
        stock = new Motostock();
        getFacade().setEntity(stock);
        setNav("listamotostock");
        setDesde(0);
        setMax(10);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem("X", "-SELECCIONAR-"));
        listaCompra = new ArrayList<SelectItem>();
        listaCompra.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaVenta = new ArrayList<SelectItem>();
        getListaVenta().add(new SelectItem("-1", "-SELECCIONAR-"));
        listaUbicacion = new ArrayList<SelectItem>();
        getListaUbicacion().add(new SelectItem("-1", "-SELECCIONAR-"));
        obtenerListas();
        listaVentas = getTransaccionFacade().findByTransaccion(40, 49);
        if (!listaVentas.isEmpty()) {
            Iterator<Transaccion> it = listaVentas.iterator();
            do {
                Transaccion x = it.next();
                listaVenta.add(new SelectItem(x.getId(), x.getId() + " - " + x.getCodigo().getDescripcion()));
            } while (it.hasNext());

        }
        setModificar(false);
        return getNav();
    }

    @Override
    List<Motostock> filtrar() {
        facade.setEntity(new Motostock());
        getFacade().setEntity(stock);
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaMotos = getMotoFacade().findAll();
        if (!listaMotos.isEmpty()) {
            Iterator<Moto> it = listaMotos.iterator();
            do {
                Moto x = it.next();
                listaMoto.add(new SelectItem(x.getCodigo(), x.getCodigo() + " - " + x.getMarca() + " - " + x.getModelo() + " - " + x.getColor()));
            } while (it.hasNext());

        }
        listaCompras = getTransaccionFacade().findByTransaccion(30, 39);
        if (!listaCompras.isEmpty()) {
            Iterator<Transaccion> it = listaCompras.iterator();
            do {
                Transaccion x = it.next();
                listaCompra.add(new SelectItem(x.getId(), x.getId() + " - " + x.getCodigo().getDescripcion()));
            } while (it.hasNext());

        }
        listaUbicaciones = getUbicacionFacade().findAll();
        if (!listaUbicaciones.isEmpty()) {
            Iterator<Ubicacion> it = listaUbicaciones.iterator();
            do {
                Ubicacion x = it.next();
                listaUbicacion.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }
    }

    @Override
    public String buscar() {
        if (id != null && !id.trim().equals("")) {
            Integer x = null;
            try {
                x = new Integer(id.trim());
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage("frm:id", "Ingrese un número válido");
                return null;
            }
        }
        facade.setEntity(new Motostock());
        getFacade().setEntity(stock);
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        stock = new Motostock();
        getFacade().setEntity(stock);
        setFormatNumero(NumberFormat.getInstance(Locale.US));
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem("X", "-SELECCIONAR-"));
        listaCompra = new ArrayList<SelectItem>();
        listaCompra.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaUbicacion = new ArrayList<SelectItem>();
        listaUbicacion.add(new SelectItem(-1, "-SELECCIONAR-"));
        obtenerListas();
        getFacade().setEntity(new Motostock());
        return "cargarstock";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String xid = null;
        xid = ((String) request.getParameter("radio"));
        if (xid != null) {
            try {
                getFacade().find(new Integer(xid));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            listaMoto = new ArrayList<SelectItem>();
            listaCompra = new ArrayList<SelectItem>();
            listaVenta = new ArrayList<SelectItem>();
            listaUbicacion = new ArrayList<SelectItem>();
            obtenerListas();
            setModificar(true);
            return "modificarstock";
        } else {
            setErrorMessage(null, getFacade().sel);
            return null;
        }
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            getFacade().setEntity(stock);
            getFacade().create();
            //Actualizar datos de la compra
            Transaccion t = getTransaccionFacade().find(getFacade().getEntity().getCompra().getId());
            t.setCantidadItems(new Short("" + (t.getCantidadItems().intValue() + 1)));
            getTransaccionFacade().setEntity(t);
            getTransaccionFacade().guardar();
            setInfoMessage(null, getFacade().ex1);
            return this.listar();
        } else {
            FacesContext.getCurrentInstance().addMessage("frm:id", new FacesMessage("Id ya existe"));
            return null;
        }
    }

    @Override
    boolean validar() {
        setFormatNumero(NumberFormat.getInstance(Locale.US));
        if (this.moto.trim().equals("X")) {
            setErrorMessage("frm:moto", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (this.chasis.trim().equals("")) {
            setErrorMessage("frm:chasis", "Ingrese un valor");
            res = false;
        }
        if (this.compra.equals(-1)) {
            setErrorMessage("frm:compra", "Seleccione un valor");
            res = false;
        }
//        if (this.venta.equals(-1)) {
//            setErrorMessage("frm:venta", "Seleccione un valor");
//            res = false;
//        }
        if (this.costo.trim().equals("")) {
            setErrorMessage("frm:costo", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = getFormatNumero().parse(this.costo.trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:costo", "Ingrese un valor positivo");
                    res = false;
                } else {
                    getFacade().getEntity().setCosto(new BigDecimal(subt.longValue()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.precioVenta.trim().equals("")) {
            setErrorMessage("frm:precioVenta", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = getFormatNumero().parse(this.precioVenta.trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:precioVenta", "Ingrese un valor positivo");
                    res = false;
                } else {
                    getFacade().getEntity().setPrecioVenta(new BigDecimal(subt.longValue()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.ubicacion.equals(-1)) {
            setErrorMessage("frm:ubicacion", "Seleccione un valor");
            res = false;
        }
        return res;
    }

    public void buscarModelo() {
        getFacade().getEntity().setMoto(getMotoFacade().find(this.moto));
    }

    @Override
    public String cancelar() {
        return this.listar();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the moto
     */
    public String getMoto() {
        return moto;
    }

    /**
     * @param moto the moto to set
     */
    public void setMoto(String moto) {
        this.moto = moto;
    }

    /**
     * @return the motor
     */
    public String getMotor() {
        if (motor != null) {
            return motor.toUpperCase();
        }
        return motor;
    }

    /**
     * @param motor the motor to set
     */
    public void setMotor(String motor) {
        this.motor = motor;
    }

    /**
     * @return the chasis
     */
    public String getChasis() {
        if (chasis != null) {
            return chasis.toUpperCase();
        }
        return chasis;
    }

    /**
     * @param chasis the chasis to set
     */
    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    /**
     * @return the compra
     */
    public Integer getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(Integer compra) {
        this.compra = compra;
    }

    /**
     * @return the venta
     */
    public Integer getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Integer venta) {
        this.venta = venta;
    }

    /**
     * @return the costo
     */
    public String getCosto() {
        Number n = null;
        if (costo != null) {
            try {
                n = new BigDecimal(costo.trim());
                costo = getFormatNumero().format(n.doubleValue());
            } finally {
                return costo;
            }
        }
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(String costo) {
        this.costo = costo;
    }

    /**
     * @return the precioVenta
     */
    public String getPrecioVenta() {
        Number n = null;
        if (precioVenta != null) {
            try {
                n = new BigDecimal(precioVenta.trim());
                precioVenta = getFormatNumero().format(n.doubleValue());
            } finally {
                return precioVenta;
            }
        }
        return precioVenta;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * @return the ubicacion
     */
    public Integer getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String anterior() {
        setLista(getFacade().anterior());
        return getNav();
    }

    @Override
    public String siguiente() {
        setLista(getFacade().siguiente());
        return getNav();
    }

    @Override
    public String todos() {
        stock = new Motostock();
        getFacade().setEntity(stock);
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Integer[]{0, 10});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
    }

    /**
     * @return the facade
     */
    public MotostockFacade getFacade() {
        if (this.facade == null) {
            this.facade = new MotostockFacade();
        }
        return facade;
    }

    /**
     * @return the listaVenta
     */
    public List<SelectItem> getListaVenta() {
        return listaVenta;
    }

    /**
     * @return the listaUbicacion
     */
    public List<SelectItem> getListaUbicacion() {
        return listaUbicacion;
    }

    /**
     * @return the ubicacionFacade
     */
    private UbicacionFacade getUbicacionFacade() {
        if (this.ubicacionFacade == null) {
            this.ubicacionFacade = new UbicacionFacade();
        }
        return ubicacionFacade;
    }

    /**
     * @return the motoFacade
     */
    private MotoFacade getMotoFacade() {
        if (this.motoFacade == null) {
            this.motoFacade = new MotoFacade();
        }
        return motoFacade;
    }

    /**
     * @return the transaccionFacade
     */
    private TransaccionFacade getTransaccionFacade() {
        if (this.transaccionFacade == null) {
            this.transaccionFacade = new TransaccionFacade();
        }
        return transaccionFacade;
    }

    /**
     * @return the listaCompra
     */
    public List<SelectItem> getListaCompra() {
        return listaCompra;
    }

    /**
     * @return the listaCompra
     */
    public List<SelectItem> getListaMoto() {
        return listaMoto;
    }

    /**
     * @return the stock
     */
    public Motostock getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Motostock stock) {
        this.stock = stock;
    }

    @Override
    void limpiarCampos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

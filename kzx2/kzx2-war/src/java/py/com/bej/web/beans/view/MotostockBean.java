/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Orden;
import py.com.bej.orm.utils.ParametroEnum;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.JsfUtils;

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
    private List<SelectItem> listaMoto;
    private List<SelectItem> listaCompra;
    private List<SelectItem> listaVenta;
    private List<SelectItem> listaUbicacion;
    //Motostock
    private Integer idFiltro;
    private String motoFiltro;
    private String chasisFiltro;
    private String compraFiltro;
    private String ventaFiltro;
    private String ubicacionFiltro;
    private Character activoFiltro;
    //Calculos
    private BigDecimal precioBase;
    private BigDecimal precioContado;
    private BigDecimal precioCosto;

    /** Creates a new instance of MotostockBean */
    public MotostockBean() {
    }
    
    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        this.idFiltro = null;
        this.motoFiltro = null;
        this.chasisFiltro = null;
        this.compraFiltro = null;
        this.ventaFiltro = null;
        this.ubicacionFiltro = null;
        this.activoFiltro = null;
        this.precioBase = null;
        this.precioContado = null;
        this.precioCosto = null;
    }
    
    public static BigDecimal[] calcularPrecios(BigDecimal costo) {
        Integer redondeo = Integer.valueOf(ConfiguracionEnum.INDICE_REDONDEO.getSymbol());
        BigDecimal multiplicador1 = BigDecimal.valueOf(ParametroEnum.AUMENTO1.getValor());
        BigDecimal multiplicador2 = BigDecimal.valueOf(ParametroEnum.AUMENTO2.getValor());
        BigDecimal resultado;
        BigDecimal res[] = new BigDecimal[2];
        if (costo != null && costo.doubleValue() > 0) {
            resultado = costo.multiply(multiplicador1).add(BigDecimal.valueOf(ParametroEnum.VARIABLE.getValor()));
            res[0] = resultado.setScale(redondeo, RoundingMode.HALF_UP);
            resultado = costo.multiply(multiplicador2).add(BigDecimal.valueOf(ParametroEnum.VARIABLE.getValor()));
            res[1] = resultado.setScale(redondeo, RoundingMode.HALF_UP);
        }
        return res;
    }
    
    public String asignarPrecioGrupal() {
        LoginBean.getInstance().setUbicacion("Asignar Precios a un Grupo de Motos");
        motoFiltro = null;
        precioCosto = null;
        precioContado = null;
        precioBase = null;
        obtenerListaMoto();
        return "asignarPrecioGrupal";
    }
    
    public void calcularPrecioGrupal() {
        BigDecimal[] precios = calcularPrecios(precioCosto);
        precioBase = precios[0];
        precioContado = precios[1];
    }
    
    @Override
    public String listar() {
        setNav("motostock");
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        LoginBean.getInstance().setUbicacion("Stock de Motos");
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        obtenerListas();
        return getNav();
    }
    
    @Override
    List<Motostock> filtrar() {
        Moto moto = (motoFiltro == null || motoFiltro.trim().equals("X")) ? new Moto() : new Moto(motoFiltro);
        Transaccion compra = (compraFiltro == null || compraFiltro.equals("-1")) ? new Transaccion() : new Transaccion(Integer.valueOf(compraFiltro));
        Transaccion venta = (ventaFiltro == null || ventaFiltro.equals("-1")) ? new Transaccion() : new Transaccion(Integer.valueOf(ventaFiltro));
        Ubicacion ubicacion = (ubicacionFiltro == null || ubicacionFiltro.equals("-1")) ? new Ubicacion() : new Ubicacion(Integer.valueOf(ubicacionFiltro));
        getFacade().setEntity(
                new Motostock(idFiltro, moto,
                (chasisFiltro != null && !chasisFiltro.trim().equals("") ? chasisFiltro : null),
                compra, venta, null, null, null, ubicacion,
                (activoFiltro != null && activoFiltro.equals('X')) ? null : activoFiltro));
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }
    
    @Override
    void obtenerListas() {
        listaMoto = JsfUtils.getSelectItems(getMotoFacade().findAll(), !getModificar());
        listaCompra = JsfUtils.getSelectItems(
                getTransaccionFacade().findByTransaccion(CategoriaEnum.COMPRA_DESDE.getSymbol(), CategoriaEnum.COMPRA_HASTA.getSymbol()), !getModificar());
        listaUbicacion = JsfUtils.getSelectItems(getUbicacionFacade().findAll(), !getModificar());
        listaVenta = JsfUtils.getSelectItems(getTransaccionFacade().findByTransaccion(CategoriaEnum.VENTA_DESDE.getSymbol(), CategoriaEnum.VENTA_HASTA.getSymbol()), !getModificar());
    }
    
    private void obtenerListaMoto() {
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem("X", "-SELECCIONAR-"));
        List<Moto> listaMotos = new ArrayList<Moto>();
        listaMotos = getMotoFacade().findGrupoByModelo();
        if (!listaMotos.isEmpty()) {
            for (Moto m : listaMotos) {
                listaMoto.add(new SelectItem(m.getModelo(), m.getMarca() + " " + m.getModelo()));
            }
        }
    }
    
    @Override
    public String buscar() {
        Moto moto = (motoFiltro == null || motoFiltro.trim().equals("X")) ? new Moto() : new Moto(motoFiltro);
        Transaccion compra = (compraFiltro == null || compraFiltro.equals("-1")) ? new Transaccion() : new Transaccion(Integer.valueOf(compraFiltro));
        Transaccion venta = (ventaFiltro == null || ventaFiltro.equals("-1")) ? new Transaccion() : new Transaccion(Integer.valueOf(ventaFiltro));
        Ubicacion ubicacion = (ubicacionFiltro == null || ubicacionFiltro.equals("-1")) ? new Ubicacion() : new Ubicacion(Integer.valueOf(ubicacionFiltro));
        getFacade().setEntity(
                new Motostock(idFiltro, moto,
                (chasisFiltro != null && !chasisFiltro.trim().equals("") ? chasisFiltro : null),
                compra, venta, null, null, null, ubicacion,
                activoFiltro.equals('X') ? null : activoFiltro));
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }
    
    @Override
    public String nuevo() {
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        stock = new Motostock();
        obtenerListas();
        return "motostock";
    }
    
    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        idFiltro = new Integer(request.getParameter("radio"));
        if (idFiltro != null) {
            try {
                stock = facade.find(idFiltro);
                setActivo(stock.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
                compraFiltro = String.valueOf(stock.getCompra().getId());
            } catch (Exception e) {
                Logger.getLogger(UbicacionBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            return "motostock";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }
    
    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            BigDecimal precio[] = null;
            precio = calcularPrecios(stock.getCosto());
            stock.setPrecioBase(precio[0]);
            stock.setPrecioContado(precio[1]);
            stock.setCompra(new Transaccion(Integer.valueOf(compraFiltro)));
            stock.setUbicacion(new Ubicacion(Integer.valueOf(ubicacionFiltro)));
            getFacade().setEntity(stock);
            if (getModificar()) {
                getFacade().getEntity().setActivo(getActivo() ? 'S' : 'N');
                getFacade().getEntity().setVenta(ventaFiltro != null ? new Transaccion(Integer.valueOf(ventaFiltro)) : null);
                getFacade().guardar();
                setInfoMessage(null, facade.ex2);
            } else {
                getFacade().create();
                setInfoMessage(null, getFacade().ex1);
            }
        } else {
            return null;
        }
        return todos();
    }
    
    public String guardarPrecioGrupal() {
        int totalAfectado = 0;
        totalAfectado = getFacade().asignarPrecioGrupal(motoFiltro, precioCosto, precioContado, precioBase);
        setInfoMessage(null, "Total de motos afectadas con los nuevos precios: " + totalAfectado);
        return listar();
        
    }
    
    public void buscarModelo() {
        if (motoFiltro != null && !motoFiltro.equals("X")) {
            stock.setMoto(getMotoFacade().find(motoFiltro));
        }
    }
    
    @Override
    public String cancelar() {
        stock = new Motostock();
        getFacade().setEntity(stock);
        return this.listar();
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
        limpiarCampos();
        setModificar(Boolean.FALSE);
        setAgregar(Boolean.FALSE);
        obtenerListas();
        stock = new Motostock();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
    }
    
    @Override
    boolean validar() {
        setFormatNumero(NumberFormat.getInstance(Locale.US));
        if (stock.getMoto().getCodigo().equals("X")) {
            setErrorMessage("frm:moto", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (stock.getChasis().trim().equals("")) {
            setErrorMessage("frm:chasis", "Ingrese un valor");
            res = false;
        }
        if (compraFiltro.equals("-1")) {
            setErrorMessage("frm:compra", "Seleccione un valor");
            res = false;
        }
//        if (this.venta.equals(-1)) {
//            setErrorMessage("frm:venta", "Seleccione un valor");
//            res = false;
//        }
        if (stock.getCosto() == null || stock.getCosto().doubleValue() < 1) {
            setErrorMessage("frm:costo", "Ingrese un valor");
            res = false;
        }
        if (ubicacionFiltro.equals("-1")) {
            setErrorMessage("frm:ubicacion", "Seleccione un valor");
            res = false;
        }
        return res;
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
    
    public Character getActivoFiltro() {
        return activoFiltro;
    }
    
    public void setActivoFiltro(Character activoFiltro) {
        this.activoFiltro = activoFiltro;
    }
    
    public String getChasisFiltro() {
        return chasisFiltro;
    }
    
    public void setChasisFiltro(String chasisFiltro) {
        this.chasisFiltro = chasisFiltro;
    }
    
    public String getCompraFiltro() {
        return compraFiltro;
    }
    
    public void setCompraFiltro(String compraFiltro) {
        this.compraFiltro = compraFiltro;
    }
    
    public Integer getIdFiltro() {
        return idFiltro;
    }
    
    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }
    
    public String getMotoFiltro() {
        return motoFiltro;
    }
    
    public void setMotoFiltro(String motoFiltro) {
        this.motoFiltro = motoFiltro;
    }
    
    public String getUbicacionFiltro() {
        return ubicacionFiltro;
    }
    
    public void setUbicacionFiltro(String ubicacionFiltro) {
        this.ubicacionFiltro = ubicacionFiltro;
    }
    
    public String getVentaFiltro() {
        return ventaFiltro;
    }
    
    public void setVentaFiltro(String ventaFiltro) {
        this.ventaFiltro = ventaFiltro;
    }
    
    public BigDecimal getPrecioBase() {
        return precioBase;
    }
    
    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }
    
    public BigDecimal getPrecioContado() {
        return precioContado;
    }
    
    public void setPrecioContado(BigDecimal precioContado) {
        this.precioContado = precioContado;
    }
    
    public BigDecimal getPrecioCosto() {
        return precioCosto;
    }
    
    public void setPrecioCosto(BigDecimal precioCosto) {
        this.precioCosto = precioCosto;
    }
}

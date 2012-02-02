/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.PagoFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class CreditoBean extends AbstractPageBean<Credito> {

    @EJB
    private TransaccionFacade transaccionFacade;
    @EJB
    private PagoFacade pagoFacade;
    @EJB
    private FinanciacionFacade financiacionFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private CreditoFacade facade;
    private Credito credito;
    private ArrayList<SelectItem> listaCategoria;
    private ArrayList<SelectItem> listaTransaccion;
    private ArrayList<SelectItem> listaSistemaCredito;
    private ArrayList<SelectItem> listaEstado;
    private ArrayList<SelectItem> listaProveedor;
    private List<Categoria> listaCategorias;
    private List<Persona> listaProveedores;
    private List<Transaccion> listaTransacciones;
    private List<Financiacion> listaCuotas;
    private Integer categoria;
    private Integer transaccion;
    private String fechaInicio;
    private String fechaFin;
    private Integer sistemaCredito;
    private String tan;
    private String tae;
    private String capital;
    private String amortizacion;
    private String creditoTotal;
    private String totalAmortizadoPagado;
    private String totalInteresesPagado;
    private String totalInteresesPagadoMulta;
    private String fechaUltimoPago;
    private String cuotasAtrasadas;
    private Integer estado;
    private SimpleDateFormat df;
    private NumberFormat nf;

    /** Creates a new instance of CreditoBean */
    public CreditoBean() {
    }

    /**
     * @return the transaccionFacade
     */
    public TransaccionFacade getTransaccionFacade() {
        return (transaccionFacade == null ? new TransaccionFacade() : transaccionFacade);
    }

    /**
     * @return the pagoFacade
     */
    public PagoFacade getPagoFacade() {
        return (pagoFacade == null ? new PagoFacade() : pagoFacade);
    }

    /**
     * @return the financiacionFacade
     */
    public FinanciacionFacade getFinanciacionFacade() {
        return (financiacionFacade == null ? new FinanciacionFacade() : financiacionFacade);
    }

    /**
     * @return the categoriaFacade
     */
    public CategoriaFacade getCategoriaFacade() {
        return (categoriaFacade == null ? new CategoriaFacade() : categoriaFacade);
    }

    /**
     * @return the creditoFacade
     */
    public CreditoFacade getFacade() {
        return (facade == null ? new CreditoFacade() : facade);
    }

    @Override
    public String listar() {
        setNav("listacreditos");
        setDesde(0);
        setMax(10);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaTransaccion = new ArrayList<SelectItem>();
        listaTransaccion.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaSistemaCredito = new ArrayList<SelectItem>();
        listaSistemaCredito.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaEstado = new ArrayList<SelectItem>();
        listaEstado.add(new SelectItem(-1, "-SELECCIONAR-"));
        obtenerListas();
        return getNav();
    }

    @Override
    List filtrar() {
        facade.setEntity(getCredito());

        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategorias = getCategoriaFacade().findAll();
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                if (x.getId() >= 60 && x.getId() <= 69) {
                    listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
                    continue;
                } else if (x.getId() >= 5 && x.getId() <= 10) {
                    listaSistemaCredito.add(new SelectItem(x.getId(), x.getDescripcion()));
                    continue;
                } else if (x.getId() >= 0 && x.getId() <= 4) {
                    listaEstado.add(new SelectItem(x.getId(), x.getDescripcion()));
                    continue;
                }
            } while (it.hasNext());
        }
        listaTransacciones = getTransaccionFacade().findByTransaccion(40, 59);
        if (!listaTransacciones.isEmpty()) {
            Iterator<Transaccion> it = listaTransacciones.iterator();
            do {
                Transaccion x = it.next();
                listaTransaccion.add(new SelectItem(x.getId(), x.getId() + "-" + x.getFechaOperacion().toString()));
            } while (it.hasNext());
        }
    }

    @Override
    public String buscar() {
        facade.setEntity(new Credito());
        getFacade().setEntity(credito);
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String id = (String) request.getParameter("radio");
        if (id != null) {
            try {
                credito = facade.find(new Integer(id));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            listaCategoria = new ArrayList<SelectItem>();
            listaProveedor = new ArrayList<SelectItem>();
            obtenerListas();
            return "modificarcompramoto";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    boolean validar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String cancelar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String todos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the listaCategoria
     */
    public ArrayList<SelectItem> getListaCategoria() {
        return listaCategoria;
    }

    /**
     * @return the listaTransaccion
     */
    public ArrayList<SelectItem> getListaTransaccion() {
        return listaTransaccion;
    }

    /**
     * @return the listaSistemaCredito
     */
    public ArrayList<SelectItem> getListaSistemaCredito() {
        return listaSistemaCredito;
    }

    /**
     * @return the listaEstados
     */
    public ArrayList<SelectItem> getListaEstados() {
        return listaEstado;
    }

    /**
     * @return the listaCuotas
     */
    public List<Financiacion> getListaCuotas() {
        return listaCuotas;
    }

    /**
     * @return the categoria
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the transaccion
     */
    public Integer getTransaccion() {
        return transaccion;
    }

    /**
     * @param transaccion the transaccion to set
     */
    public void setTransaccion(Integer transaccion) {
        this.transaccion = transaccion;
    }

    /**
     * @return the fechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the sistemaCredito
     */
    public Integer getSistemaCredito() {
        return sistemaCredito;
    }

    /**
     * @param sistemaCredito the sistemaCredito to set
     */
    public void setSistemaCredito(Integer sistemaCredito) {
        this.sistemaCredito = sistemaCredito;
    }

    /**
     * @return the tan
     */
    public String getTan() {
        return tan;
    }

    /**
     * @param tan the tan to set
     */
    public void setTan(String tan) {
        this.tan = tan;
    }

    /**
     * @return the tae
     */
    public String getTae() {
        return tae;
    }

    /**
     * @param tae the tae to set
     */
    public void setTae(String tae) {
        this.tae = tae;
    }

    /**
     * @return the capital
     */
    public String getCapital() {
        return capital;
    }

    /**
     * @param capital the capital to set
     */
    public void setCapital(String capital) {
        this.capital = capital;
    }

    /**
     * @return the amortizacion
     */
    public String getAmortizacion() {
        return amortizacion;
    }

    /**
     * @param amortizacion the amortizacion to set
     */
    public void setAmortizacion(String amortizacion) {
        this.amortizacion = amortizacion;
    }

    /**
     * @return the creditoTotal
     */
    public String getCreditoTotal() {
        return creditoTotal;
    }

    /**
     * @param creditoTotal the creditoTotal to set
     */
    public void setCreditoTotal(String creditoTotal) {
        this.creditoTotal = creditoTotal;
    }

    /**
     * @return the totalAmortizadoPagado
     */
    public String getTotalAmortizadoPagado() {
        return totalAmortizadoPagado;
    }

    /**
     * @param totalAmortizadoPagado the totalAmortizadoPagado to set
     */
    public void setTotalAmortizadoPagado(String totalAmortizadoPagado) {
        this.totalAmortizadoPagado = totalAmortizadoPagado;
    }

    /**
     * @return the totalInteresesPagado
     */
    public String getTotalInteresesPagado() {
        return totalInteresesPagado;
    }

    /**
     * @param totalInteresesPagado the totalInteresesPagado to set
     */
    public void setTotalInteresesPagado(String totalInteresesPagado) {
        this.totalInteresesPagado = totalInteresesPagado;
    }

    /**
     * @return the totalInteresesPagadoMulta
     */
    public String getTotalInteresesPagadoMulta() {
        return totalInteresesPagadoMulta;
    }

    /**
     * @param totalInteresesPagadoMulta the totalInteresesPagadoMulta to set
     */
    public void setTotalInteresesPagadoMulta(String totalInteresesPagadoMulta) {
        this.totalInteresesPagadoMulta = totalInteresesPagadoMulta;
    }

    /**
     * @return the fechaUltimoPago
     */
    public String getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    /**
     * @param fechaUltimoPago the fechaUltimoPago to set
     */
    public void setFechaUltimoPago(String fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    /**
     * @return the cuotasAtrasadas
     */
    public String getCuotasAtrasadas() {
        return cuotasAtrasadas;
    }

    /**
     * @param cuotasAtrasadas the cuotasAtrasadas to set
     */
    public void setCuotasAtrasadas(String cuotasAtrasadas) {
        this.cuotasAtrasadas = cuotasAtrasadas;
    }

    /**
     * @return the estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /**
     * @return the listaProveedor
     */
    public ArrayList<SelectItem> getListaProveedor() {
        return listaProveedor;
    }

    /**
     * @return the credito
     */
    public Credito getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    @Override
    public String guardar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void limpiarCampos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

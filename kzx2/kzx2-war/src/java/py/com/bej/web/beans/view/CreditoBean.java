/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.PagoFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author diego
 */
@ManagedBean
@ViewScoped
public class CreditoBean extends AbstractPageBean<Credito> {

    @EJB
    private PersonaFacade personaFacade;
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
    private Credito creditoResumen;
    private ArrayList<SelectItem> listaCategoria;
    private ArrayList<SelectItem> listaTransaccion;
    private ArrayList<SelectItem> listaSistemaCredito;
    private ArrayList<SelectItem> listaEstado;
    private ArrayList<SelectItem> listaGarante;
    private List<Categoria> listaCategorias;
    private List<Transaccion> listaTransacciones;
    private List<Financiacion> listaCuotas;
    private List<Pago> listaPagos;
    private Integer transaccion;
    private String fechaInicio;
    private String fechaFin;
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
    //Filtros de Busqueda
    private String idFiltro;
    private String ventaFiltro;
    private String cuotasAtrasadasFiltro;
    private Character activoFiltro;

    /** Creates a new instance of CreditoBean */
    public CreditoBean() {
        transaccionFacade = new TransaccionFacade();
        categoriaFacade = new CategoriaFacade();
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaTransaccion = new ArrayList<SelectItem>();
        listaTransaccion.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaSistemaCredito = new ArrayList<SelectItem>();
        listaSistemaCredito.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaEstado = new ArrayList<SelectItem>();
        listaEstado.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaCategorias = categoriaFacade.findAll();
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                if (x.getId() >= 60 && x.getId() <= 69) {
                    listaCategoria.add(new SelectItem("" + x.getId(), x.getDescripcion()));
                    continue;
                } else if (x.getId() >= 60 && x.getId() <= 69) {
                    listaSistemaCredito.add(new SelectItem("" + x.getId(), x.getDescripcion()));
                    continue;
                } else if (x.getId() >= 101 && x.getId() <= 106) {
                    listaEstado.add(new SelectItem("" + x.getId(), x.getDescripcion()));
                    continue;
                }
            } while (it.hasNext());
        }
        listaTransacciones = transaccionFacade.findByTransaccion(40, 59);
        if (!listaTransacciones.isEmpty()) {
            Iterator<Transaccion> it = listaTransacciones.iterator();
            do {
                Transaccion x = it.next();
                listaTransaccion.add(new SelectItem(x.getId(), x.getId() + "-" + Conversor.deDateToString(x.getFechaOperacion(), getFechaCortaPattern())));
            } while (it.hasNext());
        }
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        credito = new Credito();
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
     * @return the pagoFacade
     */
    public PersonaFacade getPersonaFacade() {
        return (personaFacade == null ? new PersonaFacade() : personaFacade);
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
        setModificar(Boolean.FALSE);
        setAgregar(Boolean.FALSE);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return null;
    }

    @Override
    List filtrar() {
        if (idFiltro != null && !idFiltro.trim().equals("")) {
            credito.setId(new Integer(idFiltro));
        }
        if (ventaFiltro != null && !ventaFiltro.trim().equals("")) {
            credito.getTransaccion().setId(new Integer(ventaFiltro));
        }
        if (cuotasAtrasadasFiltro != null && !cuotasAtrasadasFiltro.trim().equals("")) {
            credito.setCuotasAtrasadas(Short.valueOf(cuotasAtrasadas));
        }
        if (estado != null && estado > -1) {
            credito.setEstado(new Categoria(estado));
        }
        if (activoFiltro != null && activoFiltro != 'X') {
            credito.setActivo(activoFiltro);
        }
        credito.setIncluirCreditoDeCompras(Boolean.FALSE);
        facade.setEntity(getCredito() == null ? new Credito() : getCredito());
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        //Garante. Puede ser cualquiera menos los inactivos y el titular
        listaGarante = (ArrayList<SelectItem>) JsfUtils.getSelectItems(getPersonaFacade().findGarantes(credito.getTransaccion().getComprador().getId()), true);
    }

    @Override
    public String buscar() {
        getFacade().setEntity(credito);
        getFacade().setContador(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return null;
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
                if (credito.getGarante() == null) {
                    credito.setGarante(new Persona());
                }
                setActivo(credito.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
                estado = credito.getEstado().getId();
                transaccion = credito.getTransaccion().getId();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Excepcion al intentar obetener el registro.", e);
                return null;
            }
            obtenerListas();
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            return null;
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    boolean validar() {
        boolean res = true;
        if (estado < 0) {
            setErrorMessage("frm:estado", "Seleccione un valor");
            res = false;
        } else {
            credito.setEstado(new Categoria(estado));
        }
        return res;
    }

    @Override
    public String cancelar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String anterior() {
        setLista(getFacade().anterior());
        return null;
    }

    @Override
    public String siguiente() {
        setLista(getFacade().siguiente());
        return null;
    }

    @Override
    public String todos() {
        limpiarCampos();
        credito = new Credito();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("id", false));
        filtrar();
        return null;
    }

    private void obtenerFinanciacion() {
        listaCuotas = getFinanciacionFacade().findByCredito(creditoResumen.getId());
    }

    private void obtenerPagos() {
        listaPagos = getPagoFacade().findPagoByCredito(creditoResumen.getId());
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
        boolean validado = validar();
        if (validado) {
            getFacade().setEntity(credito);
            if (getModificar()) {
                getFacade().getEntity().setGarante(getFacade().getEntity().getGarante().getId() == -1 ? null : new Persona(getFacade().getEntity().getGarante().getId()));
                getFacade().getEntity().setActivo(getActivo() ? 'S' : 'N');
                getFacade().guardar();
                limpiarCampos();
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

    @Override
    void limpiarCampos() {
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        idFiltro = null;
        estado = null;
        setActivo(null);
    }

    public ArrayList<SelectItem> getListaGarante() {
        return listaGarante;
    }

    public String getCuotasAtrasadasFiltro() {
        return cuotasAtrasadasFiltro;
    }

    public void setCuotasAtrasadasFiltro(String cuotasAtrasadasFiltro) {
        this.cuotasAtrasadasFiltro = cuotasAtrasadasFiltro;
    }

    public String getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(String idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getVentaFiltro() {
        return ventaFiltro;
    }

    public void setVentaFiltro(String ventaFiltro) {
        this.ventaFiltro = ventaFiltro;
    }

    public Character getActivoFiltro() {
        return activoFiltro;
    }

    public void setActivoFiltro(Character activoFiltro) {
        this.activoFiltro = activoFiltro;
    }

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public Credito getCreditoResumen() {
        return creditoResumen;
    }

    public void setCreditoResumen(Credito creditoResumen) {
        this.creditoResumen = creditoResumen;
        obtenerFinanciacion();
        obtenerPagos();
    }
}

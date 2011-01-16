/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.session.TransaccionFacade;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class CompraMotosBean {

    @EJB
    private TransaccionFacade facade;
    private Transaccion c;
    private List<Transaccion> lista;
    private Integer desde;
    private Integer max;
    private Integer total;
    private String nav = "listacompramotos";
    private String id;
    private Boolean valido;
    private DateFormat formatFechaHora;
    private Calendar ahora;
    private List<Categoria> listaCategorias;
    private List<SelectItem> listaCategoria;
    private List<Persona> listaProveedores;
    private List<SelectItem> listaProveedor;
    //Transaccion de Compra
    private Integer codigo;
    private String comprobante;
    private String fechaOperacion;
    private String fechaEntrega;
    private Integer vendedor;
    private Integer comprador;
    private Character anulado;
    private String subTotalExentas;
    private String subTotalGravadas10;
    private String subTotalGravadas5;
    private String subtotal;
    private String totalIva5;
    private String totalIva10;
    private String totalIva;
    private String toTal;
    private String totalDescuento;
    private String totalPagado;
    private String entregaInicial;
    private String cuotas;
    private String cantidadItems;
    private String montoCuotaIgual;
    private String descuento;
    private Character saldado;
    //Calculos Ajax
    private BigDecimal subTotalExentasX;
    private BigDecimal subTotalGravadas10X;
    private BigDecimal subTotalGravadas5X;
    private BigDecimal subtotalX;
    private BigDecimal totalIva5X;
    private BigDecimal totalIva10X;
    private BigDecimal totalIvaX;
    private BigDecimal toTalX;
    private BigDecimal descuentoX;
    private BigDecimal totalDescuentoX;
    private BigDecimal totalPagadoX;

    /** Creates a new instance of CompraMotosBean */
    public CompraMotosBean() {
    }

    private void deEntity() {
        this.codigo = getC().getCodigo().getId();
        this.comprobante = getC().getComprobante();
        this.fechaOperacion = formatFechaHora.format(getC().getFechaOperacion());
        this.fechaEntrega = formatFechaHora.format(getC().getFechaEntrega());
        this.comprador = getC().getComprador().getPersonaPK().getId();
        this.vendedor = getC().getVendedor().getPersonaPK().getId();
        this.anulado = getC().getAnulado();
        this.subTotalExentas = getC().getSubTotalExentas().toString();
        this.subTotalGravadas10 = getC().getSubTotalGravadas10().toString();
        this.subTotalGravadas5 = getC().getSubTotalGravadas5().toString();
        this.subtotal = getC().getSubTotal().toString();
        this.totalIva5 = getC().getTotalIva5().toString();
        this.totalIva10 = getC().getTotalIva10().toString();
        this.totalIva = getC().getTotalIva().toString();
        this.descuento = getC().getDescuento().toString();
        this.toTal = getC().getTotal().toString();
        this.totalDescuento = getC().getTotalDescuento().toString();
        this.totalPagado = getC().getTotalPagado().toString();
        this.entregaInicial = getC().getEntregaInicial().toString();
        this.cuotas = getC().getCuotas().toString();
        this.montoCuotaIgual = getC().getMontoCuotaIgual().toString();
        this.saldado = getC().getSaldado();
        this.cantidadItems = getC().getCantidadItems().toString();
    }

    private void deCampos() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        try {
            if (getFechaOperacion() != null) {
                this.c.setFechaOperacion(formatFechaHora.parse(getFechaOperacion()));
            }
            if (this.fechaEntrega != null && !this.fechaEntrega.trim().equals("")) {
                this.c.setFechaEntrega(formatFechaHora.parse(getFechaEntrega()));
            }
            this.c.setSubTotalExentas(new BigDecimal(getSubTotalExentas()));
            this.c.setSubTotalGravadas10(new BigDecimal(getSubTotalGravadas10()));
            this.c.setSubTotalGravadas5(new BigDecimal(getSubTotalGravadas5()));
            this.c.setSubTotal(new BigDecimal(getSubtotal()));
            this.c.setTotal(new BigDecimal(getToTal()));
            this.c.setTotalDescuento(new BigDecimal(getTotalDescuento()));
            this.c.setTotalIva5(new BigDecimal(getTotalIva5()));
            this.c.setTotalIva10(new BigDecimal(getTotalIva10()));
            this.c.setTotalIva(new BigDecimal(getTotalIva()));
            this.c.setTotalPagado(new BigDecimal(getTotalPagado()));
            this.c.setEntregaInicial(new BigDecimal(getEntregaInicial()));
            this.c.setMontoCuotaIgual(new BigDecimal(getMontoCuotaIgual()));
            if (getCuotas() != null) {
                this.c.setCuotas(new Short(getCuotas()));
            }
            if (getCantidadItems() != null) {
                this.c.setCantidadItems(new Short(getCantidadItems()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        this.id = null;
        this.codigo = null;
        this.comprobante = null;
        this.fechaEntrega = null;
        this.comprador = null;
        this.vendedor = null;
        this.anulado = null;
        this.subTotalExentas = BigDecimal.ZERO.toString();
        this.subTotalGravadas10 = BigDecimal.ZERO.toString();
        this.subTotalGravadas5 = BigDecimal.ZERO.toString();
        this.subtotal = BigDecimal.ZERO.toString();
        this.totalIva = BigDecimal.ZERO.toString();
        this.totalIva5 = BigDecimal.ZERO.toString();
        this.totalIva10 = BigDecimal.ZERO.toString();
        this.descuento = null;
        this.toTal = BigDecimal.ZERO.toString();
        this.totalDescuento = BigDecimal.ZERO.toString();
        this.totalPagado = BigDecimal.ZERO.toString();
        this.entregaInicial = BigDecimal.ZERO.toString();
        this.cuotas = null;
        this.montoCuotaIgual = BigDecimal.ZERO.toString();
        this.saldado = null;
        this.cantidadItems = null;
    }

    public String listar() {
        this.c = new Transaccion();
        limpiarCampos();
        deCampos();
        this.setDesde(new Integer(0));
        this.setMax(new Integer(10));
        this.setValido((Boolean) true);
        this.filtrar();
        if (this.getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaProveedor = new ArrayList<SelectItem>();
        listaProveedor.add(new SelectItem(-1, "-SELECCIONAR-"));
        obtenerListas();
        return nav;
    }

    public List<Transaccion> filtrar() {
        deCampos();
        setLista(new ArrayList<Transaccion>());
        int[] range = {this.getDesde(), this.getMax()};
        setLista(getFacade().findRange(range, this.getC()));
        return getLista();
    }

    private void obtenerListas() {
        listaCategorias = new CategoriaFacade().findBetween(31, 39);
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }
        listaProveedores = new PersonaFacade().findByPersona(20);
        if (!listaProveedores.isEmpty()) {
            Iterator<Persona> it = listaProveedores.iterator();
            do {
                Persona x = it.next();
                listaProveedor.add(new SelectItem(x.getPersonaPK().getId(), x.getNombre()));
            } while (it.hasNext());

        }
    }

    public String buscar() {
        setDesde((Integer) 0);
        setMax((Integer) 10);
        this.filtrar();
        if (this.getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return nav;
    }

    public String nuevo() {
        setC(new Transaccion());
        limpiarCampos();
        getC().setCodigo(new Categoria(-1));
        getC().setAnulado('N');
        getC().setSaldado('X');
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaProveedor = new ArrayList<SelectItem>();
        listaProveedor.add(new SelectItem(-1, "-SELECCIONAR-"));
        obtenerListas();
        return "comprarmotos";
    }

    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.setId((String) request.getParameter("radio"));
        if (this.getId() != null) {
            try {
                this.setC(getFacade().find(new Integer(getId())));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            deEntity();
            setListaCategoria(new ArrayList<SelectItem>());
            setListaProveedor(new ArrayList<SelectItem>());
            obtenerListas();
            return "modificarcompramoto";
        } else {
            setErrorMessage(null, getFacade().sel);
            return null;
        }
    }

    public String guardarNuevo() {
        boolean validado = validarNuevo();
        if (validado) {
            deCampos();
            boolean exito = getFacade().create(getC());
            if (exito) {
                setInfoMessage(null, getFacade().ex1);
                return this.listar();
            } else {
                FacesContext.getCurrentInstance().addMessage("frm:id", new FacesMessage("Id ya existe"));
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean validarNuevo() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        if (this.c.getCodigo().getId() == -1) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            return false;
        }
        boolean res = true;
        if (this.c.getComprobante().trim().equals("")) {
            setErrorMessage("frm:comprobante", "Ingrese el número de la factura con el formato XXX-XXX-XXXXXXX");
            res = false;
        }
        if (this.getFechaOperacion().trim().equals("")) {
            setErrorMessage("frm:fechaOperacion", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
            res = false;
        } else {
            Date fecha = null;
            try {
                fecha = formatFechaHora.parse(this.getFechaOperacion().trim());
                getC().setFechaOperacion(fecha);
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage("frm:fechaOperacion", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
                res = false;
            }
        }
        if (this.getFechaEntrega().trim().equals("")) {
            setErrorMessage("frm:fechaEntrega", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
            res = false;
        } else {
            Date fecha = null;
            try {
                fecha = formatFechaHora.parse(this.getFechaEntrega().trim());
                getC().setFechaEntrega(fecha);
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage("frm:fechaEntrega", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
                res = false;
            }
        }
        if (this.vendedor == -1) {
            setErrorMessage("frm:vendedor", "Seleccione un valor");
            res = false;
        } else {
            this.c.setVendedor(new PersonaFacade().findById(this.vendedor));
        }
        if (this.getSubTotalExentas().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getSubTotalExentas().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setSubTotalExentas(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getSubTotalGravadas5().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getSubTotalGravadas5().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setSubTotalGravadas5(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getSubTotalGravadas10().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getSubTotalGravadas10().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setSubTotalGravadas10(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getTotalPagado().trim().equals("")) {
            setErrorMessage("frm:totalPagado", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getTotalPagado().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:totalPagado", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setTotalPagado(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getEntregaInicial().trim().equals("")) {
            setErrorMessage("frm:entregaInicial", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getEntregaInicial().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:entregaInicial", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setEntregaInicial(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getCantidadItems().trim().equals("")) {
            setErrorMessage("frm:cantidadItems", "Ingrese un valor");
            res = false;
        } else {
            Short subt = null;
            try {
                subt = new Short(this.getCantidadItems().trim());
                if (subt < 0) {
                    setErrorMessage("frm:cantidadItems", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setCantidadItems(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.c.setComprador(new PersonaFacade().findById(6));
        this.c.setDescuento(Float.MIN_VALUE);
        return res;
    }

    public boolean validarModificar() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        boolean res = true;
        if (this.c.getComprobante().trim().equals("")) {
            setErrorMessage("frm:comprobante", "Ingrese el número de la factura con el formato XXX-XXX-XXXXXXX");
            return false;
        }
        if (this.getFechaOperacion().trim().equals("")) {
            setErrorMessage("frm:fechaOperacion", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
            res = false;
        } else {
            Date fecha = null;
            try {
                fecha = formatFechaHora.parse(this.getFechaOperacion().trim());
                getC().setFechaOperacion(fecha);
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage("frm:fechaOperacion", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
                res = false;
            }
        }
        if (this.getFechaEntrega().trim().equals("")) {
            setErrorMessage("frm:fechaEntrega", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
            res = false;
        } else {
            Date fecha = null;
            try {
                fecha = formatFechaHora.parse(this.getFechaEntrega().trim());
                getC().setFechaEntrega(fecha);
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage("frm:fechaEntrega", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
                res = false;
            }
        }

        if (this.getSubTotalExentas().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getSubTotalExentas().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setSubTotalExentas(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getSubTotalGravadas5().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getSubTotalGravadas5().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setSubTotalGravadas5(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getSubTotalGravadas10().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getSubTotalGravadas10().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setSubTotalGravadas10(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getTotalPagado().trim().equals("")) {
            setErrorMessage("frm:totalPagado", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getTotalPagado().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:totalPagado", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setTotalPagado(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getEntregaInicial().trim().equals("")) {
            setErrorMessage("frm:entregaInicial", "Ingrese un valor");
            res = false;
        } else {
            BigDecimal subt = null;
            try {
                subt = new BigDecimal(this.getEntregaInicial().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:entregaInicial", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.c.setEntregaInicial(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getCantidadItems().trim().equals("")) {
            setErrorMessage("frm:cantidadItems", "Ingrese un valor");
            res = false;
        } else {
            Short subt = null;
            try {
                subt = new Short(this.getCantidadItems().trim());
                if (subt < 0) {
                    setErrorMessage("frm:cantidadItems", "Ingrese un valor positivo");
                    res = false;
                } else {
                    this.getC().setCantidadItems(subt);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.c.setVendedor(new PersonaFacade().findById(this.vendedor));
        return res;
    }

    public String guardarModificar() {
        boolean validado = validarModificar();
        if (validado) {
            getFacade().guardar(getC());
            setInfoMessage(null, getFacade().ex2);
            return this.listar();
        } else {
            return null;
        }
    }

    public String cancelar() {
        return this.listar();
    }

    public String todos() {
        limpiarCampos();
        getFacade().setCol(null);
        this.setValido((Boolean) true);
        deCampos();
        setDesde((Integer) 0);
        setMax((Integer) 10);
        this.filtrar();
        return nav;
    }

    public String anterior() {
        setDesde((Integer) (getDesde() - getMax()));
        int[] range = {getDesde(), getMax()};
        this.setLista(getFacade().anterior(range, getC()));
        return nav;
    }

    public String siguiente() {
        setDesde((Integer) (getDesde() + getMax()));
        int[] range = {getDesde(), getMax()};
        this.setLista(getFacade().siguiente(range, getC()));
        return nav;
    }

    public Integer getUltimoItem() {
        deCampos();
        TransaccionFacade.c = getC();
        int[] range = {getDesde(), getMax()};
        return getFacade().getUltimoItem(range);
    }

    public boolean sumarResultados() {
        boolean res = true;
        subTotalExentasX = new BigDecimal(this.subTotalExentas);
        subTotalGravadas5X = new BigDecimal(this.subTotalGravadas5);
        totalIva5X = new BigDecimal(this.totalIva5);
        totalIva10X = new BigDecimal(this.totalIva10);
        subTotalGravadas10X = new BigDecimal(this.subTotalGravadas10);
        if (subTotalGravadas5X.longValue() > 0) {
            totalIva5X = subTotalGravadas5X.multiply(new BigDecimal(0.05)).setScale(0, RoundingMode.HALF_UP);
        } else {
            res = false;
        }
        if (subTotalGravadas10X.longValue() > 0) {
            totalIva10X = subTotalGravadas10X.multiply(new BigDecimal(0.1)).setScale(0, RoundingMode.HALF_UP);
        } else {
            res = false;
        }
        subtotalX = subTotalExentasX.add(subTotalGravadas10X);
        try {
            descuentoX = new BigDecimal(this.descuento);
        } catch (Exception e) {
            descuentoX = BigDecimal.ZERO;
            res = false;
            e.printStackTrace();
        }
//        if (descuentoX.longValue() > 0) {
//            totalDescuentoX = subtotalX.multiply(descuentoX);
//        } else {
//            totalDescuentoX = BigDecimal.ZERO;
//        }
//        toTalX = subtotalX.subtract(totalDescuentoX);
        //Pasar Datos
        totalIva5 = totalIva5X.toString();
        totalIva10 = totalIva10X.toString();
        subtotal = subtotalX.toString();
        totalDescuento = BigDecimal.ZERO.toString();
        toTal = toTalX.toString();
        return res;
    }

    /**
     * @return the desde
     */
    public Integer getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    /**
     * @return the max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        this.total = getFacade().count();
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    protected void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    protected void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
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
     * @return the valido
     */
    public Boolean getValido() {
        return valido;
    }

    /**
     * @param valido the valido to set
     */
    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    /**
     * @return the listaCategoria
     */
    public List<SelectItem> getListaCategoria() {
        return listaCategoria;
    }

    /**
     * @param listaCategoria the listaCategoria to set
     */
    public void setListaCategoria(List<SelectItem> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the fechaOperacion
     */
    public String getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion the fechaOperacion to set
     */
    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @return the fechaEntrega
     */
    public String getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * @param fechaEntrega the fechaEntrega to set
     */
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * @return the vendedor
     */
    public Integer getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(Integer vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @return the comprador
     */
    public Integer getComprador() {
        return comprador;
    }

    /**
     * @param comprador the comprador to set
     */
    public void setComprador(Integer comprador) {
        this.comprador = comprador;
    }

    /**
     * @return the anulado
     */
    public Character getAnulado() {
        return anulado;
    }

    /**
     * @param anulado the anulado to set
     */
    public void setAnulado(Character anulado) {
        this.anulado = anulado;
    }

    /**
     * @return the subTotalExentas
     */
    public String getSubTotalExentas() {
        return subTotalExentas;
    }

    /**
     * @param subTotalExentas the subTotalExentas to set
     */
    public void setSubTotalExentas(String subTotalExentas) {
        this.subTotalExentas = subTotalExentas;
    }

    /**
     * @return the subTotalGravadas10
     */
    public String getSubTotalGravadas10() {
        return subTotalGravadas10;
    }

    /**
     * @param subTotalGravadas10 the subTotalGravadas10 to set
     */
    public void setSubTotalGravadas10(String subTotalGravadas10) {
        this.subTotalGravadas10 = subTotalGravadas10;
    }

    /**
     * @return the subTotalGravadas5
     */
    public String getSubTotalGravadas5() {
        return subTotalGravadas5;
    }

    /**
     * @param subTotalGravadas5 the subTotalGravadas5 to set
     */
    public void setSubTotalGravadas5(String subTotalGravadas5) {
        this.subTotalGravadas5 = subTotalGravadas5;
    }

    /**
     * @return the subtotal
     */
    public String getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the totalIva
     */
    public String getTotalIva() {
        return totalIva;
    }

    /**
     * @param totalIva the totalIva to set
     */
    public void setTotalIva(String totalIva) {
        this.totalIva = totalIva;
    }

    /**
     * @return the totalPagado
     */
    public String getTotalPagado() {
        return totalPagado;
    }

    /**
     * @param totalPagado the totalPagado to set
     */
    public void setTotalPagado(String totalPagado) {
        this.totalPagado = totalPagado;
    }

    /**
     * @return the descuento
     */
    public String getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the saldado
     */
    public Character getSaldado() {
        return saldado;
    }

    /**
     * @param saldado the saldado to set
     */
    public void setSaldado(Character saldado) {
        this.saldado = saldado;
    }

    /**
     * @return the c
     */
    public Transaccion getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(Transaccion c) {
        this.c = c;
    }

    /**
     * @return the lista
     */
    public List<Transaccion> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<Transaccion> lista) {
        this.lista = lista;
    }

    /**
     * @return the comprobante
     */
    public String getComprobante() {
        return comprobante;
    }

    /**
     * @param comprobante the comprobante to set
     */
    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    /**
     * @return the cuotas
     */
    public String getCuotas() {
        return cuotas;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(String cuotas) {
        this.cuotas = cuotas;
    }

    /**
     * @return the montoCuotaIgual
     */
    public String getMontoCuotaIgual() {
        return montoCuotaIgual;
    }

    /**
     * @param montoCuotaIgual the montoCuotaIgual to set
     */
    public void setMontoCuotaIgual(String montoCuotaIgual) {
        this.montoCuotaIgual = montoCuotaIgual;
    }

    /**
     * @return the toTal
     */
    public String getToTal() {
        return toTal;
    }

    /**
     * @param toTal the toTal to set
     */
    public void setToTal(String toTal) {
        this.toTal = toTal;
    }

    /**
     * @return the totalDescuento
     */
    public String getTotalDescuento() {
        return totalDescuento;
    }

    /**
     * @param totalDescuento the totalDescuento to set
     */
    public void setTotalDescuento(String totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    /**
     * @return the facade
     */
    public TransaccionFacade getFacade() {
        if (this.facade == null) {
            this.facade = new TransaccionFacade();
        }
        return facade;
    }

    /**
     * @return the entregaInicial
     */
    public String getEntregaInicial() {
        return entregaInicial;
    }

    /**
     * @param entregaInicial the entregaInicial to set
     */
    public void setEntregaInicial(String entregaInicial) {
        this.entregaInicial = entregaInicial;
    }

    /**
     * @return the totalIva5
     */
    public String getTotalIva5() {
        return totalIva5;
    }

    /**
     * @param totalIva5 the totalIva5 to set
     */
    public void setTotalIva5(String totalIva5) {
        this.totalIva5 = totalIva5;
    }

    /**
     * @return the totalIva10
     */
    public String getTotalIva10() {
        return totalIva10;
    }

    /**
     * @param totalIva10 the totalIva10 to set
     */
    public void setTotalIva10(String totalIva10) {
        this.totalIva10 = totalIva10;
    }

    /**
     * @return the listaProveedores
     */
    public List<Persona> getListaProveedores() {
        return listaProveedores;
    }

    /**
     * @param listaProveedores the listaProveedores to set
     */
    public void setListaProveedores(List<Persona> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    /**
     * @return the listaProveedor
     */
    public List<SelectItem> getListaProveedor() {
        return listaProveedor;
    }

    /**
     * @param listaProveedor the listaProveedor to set
     */
    public void setListaProveedor(List<SelectItem> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    /**
     * @return the cantidadItems
     */
    public String getCantidadItems() {
        return cantidadItems;
    }

    /**
     * @param cantidadItems the cantidadItems to set
     */
    public void setCantidadItems(String cantidadItems) {
        this.cantidadItems = cantidadItems;
    }
}

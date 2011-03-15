/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.PersonaPK;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.entities.Ubicacion;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.session.UbicacionFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class CompraMotosBean extends AbstractPageBean {

    @EJB
    private MotoFacade motoFacade;
    @EJB
    private UbicacionFacade ubicacionFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private MotostockFacade motostockFacade;
    @EJB
    private TransaccionFacade facade;
    private String id;
    private DateFormat formatFechaHora;
    private NumberFormat formatNumero;
    private List<Categoria> listaCategorias;
    private List<SelectItem> listaCategoria;
    private List<Persona> listaProveedores;
    private List<SelectItem> listaProveedor;
    private List<Moto> listaMotos;
    private List<SelectItem> listaMoto;
    private List<Ubicacion> listaUbicaciones;
    private List<SelectItem> listaUbicacion;
    private List<Pojo> listaMotosNuevas;
    private Short cantidad;
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
    private Boolean usarFechaAhora = false;
    private Boolean usarFechaEntregaAhora = false;
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
     * @return the facade
     */
    public CategoriaFacade getCategoriaFacade() {
        if (this.categoriaFacade == null) {
            this.categoriaFacade = new CategoriaFacade();
        }
        return categoriaFacade;
    }

    /**
     * @return the facade
     */
    public MotostockFacade getMotostockFacade() {
        if (this.motostockFacade == null) {
            this.motostockFacade = new MotostockFacade();
        }
        return motostockFacade;
    }

    /**
     * @return the facade
     */
    public PersonaFacade getPersonaFacade() {
        if (this.personaFacade == null) {
            this.personaFacade = new PersonaFacade();
        }
        return personaFacade;
    }

    /**
     * @return the facade
     */
    public UbicacionFacade getUbicacionFacade() {
        if (this.ubicacionFacade == null) {
            this.ubicacionFacade = new UbicacionFacade();
        }
        return ubicacionFacade;
    }

    /**
     * @return the facade
     */
    public MotoFacade getMotoFacade() {
        if (this.motoFacade == null) {
            this.motoFacade = new MotoFacade();
        }
        return motoFacade;
    }

    @Override
    void deEntity() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        formatNumero = NumberFormat.getInstance(Locale.US);
        this.id = facade.getEntity().getId().toString();
        this.codigo = facade.getEntity().getCodigo().getId();
        this.comprobante = facade.getEntity().getComprobante();
        this.fechaOperacion = formatFechaHora.format(facade.getEntity().getFechaOperacion());
        this.fechaEntrega = formatFechaHora.format(facade.getEntity().getFechaEntrega());
        this.comprador = facade.getEntity().getComprador().getPersonaPK().getId();
        this.vendedor = facade.getEntity().getVendedor().getPersonaPK().getId();
        this.anulado = facade.getEntity().getAnulado();
        this.subTotalExentas = facade.getEntity().getSubTotalExentas().toString();
        this.subTotalGravadas10 = facade.getEntity().getSubTotalGravadas10().toString();
        this.subTotalGravadas5 = facade.getEntity().getSubTotalGravadas5().toString();
        this.subtotal = facade.getEntity().getSubTotal().toString();
        this.totalIva5 = facade.getEntity().getTotalIva5().toString();
        this.totalIva10 = facade.getEntity().getTotalIva10().toString();
        this.totalIva = facade.getEntity().getTotalIva().toString();
        this.descuento = facade.getEntity().getDescuento().toString();
        this.toTal = facade.getEntity().getTotal().toString();
        this.totalDescuento = facade.getEntity().getTotalDescuento().toString();
        this.totalPagado = facade.getEntity().getTotalPagado().toString();
        this.entregaInicial = facade.getEntity().getEntregaInicial().toString();
        this.cuotas = facade.getEntity().getCuotas().toString();
        this.montoCuotaIgual = facade.getEntity().getMontoCuotaIgual().toString();
        this.saldado = facade.getEntity().getSaldado();
        this.cantidadItems = facade.getEntity().getCantidadItems().toString();
    }

    @Override
    void deCampos() {
        try {
            if (id != null && !id.trim().equals("")) {
                Number n = null;
                try {
                    n = new Integer(id.trim());
                    if (n != null) {
                        facade.getEntity().setId((Integer) n);
                    }
                } catch (Exception e) {
                    e.getMessage();
                    setErrorMessage("frmBuscar:id", "Ingrese un numero");
                    return;
                }
            }
            if (codigo != null && !codigo.equals(-1)) {
                facade.getEntity().setCodigo(new Categoria(codigo));
            }
            if (comprobante != null && !comprobante.trim().equals("")) {
                facade.getEntity().setComprobante(comprobante.trim());
            }
            if (fechaEntrega != null && !fechaEntrega.equals("")) {
                facade.getEntity().setFechaEntrega(formatFechaHora.parse(fechaEntrega.trim()));
            }
            if (comprador != null && !comprador.equals(-1)) {
                Persona comp = personaFacade.find(new PersonaPK(comprador, "1275758"));
                facade.getEntity().setComprador(comp);
            }
            if (vendedor != null && !vendedor.equals(-1)) {
                PersonaPK vpk = new PersonaPK(vendedor, null);
                getPersonaFacade().setEntity(new Persona(vpk));
                Persona vend = getPersonaFacade().findById();
                facade.getEntity().setVendedor(vend);
            }
            if (anulado != null && !anulado.equals('X')) {
                facade.getEntity().setAnulado(anulado);
            }
            if (subTotalExentas != null && !subTotalExentas.trim().equals("")) {
                facade.getEntity().setSubTotalExentas(BigDecimal.valueOf(formatNumero.parse(this.subTotalExentas).longValue()));
            }
            if (subTotalGravadas10 != null && !subTotalGravadas10.trim().equals("")) {
                facade.getEntity().setSubTotalGravadas10(BigDecimal.valueOf(formatNumero.parse(this.subTotalGravadas10).longValue()));
            }
            if (subTotalGravadas5 != null && !subTotalGravadas5.trim().equals("")) {
                facade.getEntity().setSubTotalGravadas5(BigDecimal.valueOf(formatNumero.parse(this.subTotalGravadas5).longValue()));
            }
            if (subtotal != null && !subtotal.trim().equals("")) {
                facade.getEntity().setSubTotal(BigDecimal.valueOf(formatNumero.parse(this.subtotal).longValue()));

            }
            if (totalIva5 != null && !totalIva5.trim().equals("")) {
                facade.getEntity().setTotalIva5(BigDecimal.valueOf(formatNumero.parse(this.totalIva5).longValue()));
            }
            if (totalIva10 != null && !totalIva10.trim().equals("")) {
                facade.getEntity().setTotalIva10(BigDecimal.valueOf(formatNumero.parse(this.totalIva10).longValue()));
            }
            if (totalIva != null && !totalIva.trim().equals("")) {
                facade.getEntity().setTotalIva(BigDecimal.valueOf(formatNumero.parse(this.totalIva).longValue()));
            }
            if (descuento != null && !descuento.trim().equals("")) {
                facade.getEntity().setDescuento(new Float(descuento.trim()));
            }
            if (toTal != null && !toTal.trim().equals("")) {
                facade.getEntity().setTotal(BigDecimal.valueOf(formatNumero.parse(this.toTal).longValue()));
            }
            if (totalDescuento != null && !totalDescuento.trim().equals("")) {
                facade.getEntity().setTotalDescuento(BigDecimal.valueOf(formatNumero.parse(this.totalDescuento).longValue()));
            }
            if (totalPagado != null && !totalPagado.trim().equals("")) {
                facade.getEntity().setTotalPagado(BigDecimal.valueOf(formatNumero.parse(this.totalPagado).longValue()));
            }
            if (entregaInicial != null && !entregaInicial.trim().equals("")) {
                facade.getEntity().setEntregaInicial(BigDecimal.valueOf(formatNumero.parse(this.entregaInicial).longValue()));
            }
            if (cuotas != null && !cuotas.trim().equals("")) {
                facade.getEntity().setCuotas(new Short(cuotas.trim()));
            }
            if (montoCuotaIgual != null && !montoCuotaIgual.trim().equals("")) {
                facade.getEntity().setMontoCuotaIgual(BigDecimal.valueOf(formatNumero.parse(this.montoCuotaIgual).longValue()));
            }
            if (saldado != null && !saldado.equals('X')) {
                facade.getEntity().setSaldado(new Character(saldado));
            }
            if (cantidadItems != null && !cantidadItems.trim().equals("")) {
                facade.getEntity().setCantidadItems(new Short(cantidadItems.trim()));
            }
            if (saldado != null) {
                facade.getEntity().setSaldado(saldado);
            }
        } catch (ParseException ex) {
            Logger.getLogger(CompraMotosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void limpiarCampos() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        formatNumero = NumberFormat.getNumberInstance(Locale.US);
        id = null;
        codigo = null;
        comprobante = null;
        fechaEntrega = null;
        comprador = null;
        vendedor = null;
        anulado = null;
        subTotalExentas = BigDecimal.ZERO.toString();
        subTotalGravadas10 = BigDecimal.ZERO.toString();
        subTotalGravadas5 = BigDecimal.ZERO.toString();
        subtotal = BigDecimal.ZERO.toString();
        totalIva = BigDecimal.ZERO.toString();
        totalIva5 = BigDecimal.ZERO.toString();
        totalIva10 = BigDecimal.ZERO.toString();
        descuento = null;
        toTal = BigDecimal.ZERO.toString();
        totalDescuento = BigDecimal.ZERO.toString();
        totalPagado = BigDecimal.ZERO.toString();
        entregaInicial = BigDecimal.ZERO.toString();
        cuotas = null;
        montoCuotaIgual = BigDecimal.ZERO.toString();
        saldado = null;
        cantidadItems = null;
        usarFechaAhora = false;
        usarFechaEntregaAhora = false;
        cantidad = null;
    }

    @Override
    public String listar() {
        setNav("listacompramotos");
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
        listaProveedor = new ArrayList<SelectItem>();
        listaProveedor.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaUbicacion = new ArrayList<SelectItem>();
        listaUbicacion.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem('X', "-SELECCIONAR-"));
        obtenerListas();
        return getNav();
    }

    @Override
    List filtrar() {
        facade.setEntity(new Transaccion());
        deCampos();
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategorias = getCategoriaFacade().findBetween(30, 40);
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }

        listaProveedores = getPersonaFacade().findByPersona(20);
        if (!listaProveedores.isEmpty()) {
            Iterator<Persona> it = listaProveedores.iterator();
            do {
                Persona x = it.next();
                listaProveedor.add(new SelectItem(x.getPersonaPK().getId(), x.getNombre()));
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
        facade.setEntity(new Transaccion());
        deCampos();
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        limpiarCampos();
        facade.setEntity(new Transaccion());
        facade.getEntity().setCodigo(new Categoria());
        facade.getEntity().setVendedor(new Persona());
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaProveedor = new ArrayList<SelectItem>();
        listaProveedor.add(new SelectItem(-1, "-SELECCIONAR-"));
        listaUbicacion = new ArrayList<SelectItem>();
        listaUbicacion.add(new SelectItem("-1", "-SELECCIONAR-"));
        obtenerListas();
        listaMotos = getMotoFacade().findAll();
        if (!listaMotos.isEmpty()) {
            Iterator<Moto> it = listaMotos.iterator();
            do {
                Moto x = it.next();
                listaMoto.add(new SelectItem(x.getCodigo(), x.getModelo()));
            } while (it.hasNext());
        }
        comprador = 6;
        return "comprarmotos";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        setId((String) request.getParameter("radio"));
        if (getId() != null) {
            try {
                facade.setEntity(facade.find(new Integer(getId())));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            deEntity();
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
    public String guardarNuevo() {
        boolean validado = validarNuevo();
        try {
            if (validado) {
                sumarResultados();
                deCampos();
                facade.getEntity().setSaldado('N');
                facade.getEntity().setAnulado('N');
                List<Motostock> lm = new ArrayList<Motostock>();
                Motostock m;
                for (Pojo p : listaMotosNuevas) {
                    m = new Motostock(null, new Moto(p.getModelo()), null, p.getChasis(),
                            null, null, BigDecimal.valueOf(formatNumero.parse(p.getPrecio()).longValue()), null,
                            new Ubicacion(new Integer(p.getUbicacion())));
                    lm.add(m);
                }
                int r = facade.guardarCompra(lm);
                setInfoMessage(null, getFacade().ex1);
                limpiarCampos();
                return this.listar();
            } else {
                return null;
            }
        } catch (ParseException p) {
            p.getLocalizedMessage();
            return null;
        }
    }

    @Override
    public boolean validarNuevo() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        boolean res = true;
        if (getCodigo() == -1) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        if (getComprobante().trim().equals("")) {
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
                getFacade().getEntity().setFechaOperacion(fecha);
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
                getFacade().getEntity().setFechaEntrega(fecha);
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage("frm:fechaEntrega", "Ingrese una fecha con el formato dd/MM/yyyy - HH:mm");
                res = false;
            }
        }
        if (this.vendedor == -1) {
            setErrorMessage("frm:vendedor", "Seleccione un valor");
            res = false;
        }
        if (this.getSubTotalExentas().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getSubTotalExentas().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getSubTotalGravadas5().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getSubTotalGravadas5().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getSubTotalGravadas10().trim().equals("")) {
            setErrorMessage("frm:subTotalExentas", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getSubTotalGravadas10().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:subTotalExentas", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getTotalPagado().trim().equals("")) {
            setErrorMessage("frm:totalPagado", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getTotalPagado().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:totalPagado", "Ingrese un valor positivo");
                    res = false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (this.getEntregaInicial().trim().equals("")) {
            setErrorMessage("frm:entregaInicial", "Ingrese un valor");
            res = false;
        } else {
            Number subt = null;
            try {
                subt = formatNumero.parse(this.getEntregaInicial().trim());
                if (subt.longValue() < 0) {
                    setErrorMessage("frm:entregaInicial", "Ingrese un valor positivo");
                    res = false;
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
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public boolean validarModificar() {
        formatFechaHora = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        boolean res = true;
        if (getComprobante().trim().equals("")) {
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
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public String guardarModificar() {
        boolean validado = validarModificar();
        if (validado) {
            deCampos();
            facade.guardar();
            setInfoMessage(null, getFacade().ex2);
            return this.listar();
        } else {
            return null;
        }
    }

    @Override
    public String todos() {
        limpiarCampos();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Integer[]{0, 10});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
    }

    @Override
    public String cancelar() {
        limpiarCampos();
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

    public boolean sumarResultados() {
        boolean res = true;
        try {
            subTotalExentasX = BigDecimal.valueOf(formatNumero.parse(this.subTotalExentas).longValue());
            subTotalGravadas5X = BigDecimal.valueOf(formatNumero.parse(this.subTotalGravadas5).longValue());
            totalIva5X = BigDecimal.valueOf(formatNumero.parse(this.totalIva5).longValue());
            totalIva10X = BigDecimal.valueOf(formatNumero.parse(this.totalIva10).longValue());
            subTotalGravadas10X = BigDecimal.valueOf(formatNumero.parse(this.subTotalGravadas10).longValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (descuentoX.longValue() > 0) {
            totalDescuentoX = subtotalX.multiply(descuentoX);
        } else {
            totalDescuentoX = BigDecimal.ZERO;
        }
        toTalX = subtotalX.subtract(totalDescuentoX);
        //Pasar Datos
        totalIva5 = totalIva5X.toString();
        totalIva10 = totalIva10X.toString();
        subtotal = subtotalX.toString();
        totalDescuento = BigDecimal.ZERO.toString();
        descuento = totalDescuento;
        toTal = toTalX.toString();
        return res;
    }

    public void cargarMotosNuevas() {
        if (cantidadItems != null && !cantidadItems.trim().equals("")) {
            cantidad = null;
            try {
                cantidad = new Short(cantidadItems.trim());
                Pojo p;
                if (cantidad > 0) {
                    listaMotosNuevas = new ArrayList<Pojo>();
                    for (int x = 0; x < cantidad; x++) {
                        p = new Pojo();
                        listaMotosNuevas.add(p);
                    }

                } else {
                    setErrorMessage("frm:cantidadItems", "Ingrese un valor mayor a cero");
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
    }

    @Override
    protected void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    @Override
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
        if (usarFechaAhora) {
            fechaOperacion = formatFechaHora.format(new Date());
            usarFechaAhora = false;
        }
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
        if (usarFechaEntregaAhora) {
            fechaEntrega = formatFechaHora.format(new Date());
            usarFechaEntregaAhora = false;
        }
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
        Number n = null;
        try {
            n = new BigDecimal(subTotalExentas.trim());
            subTotalExentas = formatNumero.format(n.doubleValue());
        } finally {
            return subTotalExentas;
        }
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
        Number n = null;
        try {
            n = new BigDecimal(subTotalGravadas10.trim());
            subTotalGravadas10 = formatNumero.format(n.doubleValue());
        } finally {
            return subTotalGravadas10;
        }
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
        Number n = null;
        try {
            n = new BigDecimal(subTotalGravadas5.trim());
            subTotalGravadas5 = formatNumero.format(n.doubleValue());
        } finally {
            return subTotalGravadas5;
        }
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
        Number n = null;
        try {
            n = new BigDecimal(totalPagado.trim());
            totalPagado = formatNumero.format(n.doubleValue());
        } finally {
            return totalPagado;
        }
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
        Number n = null;
        try {
            n = new BigDecimal(montoCuotaIgual.trim());
            montoCuotaIgual = formatNumero.format(n.doubleValue());
        } finally {
            return montoCuotaIgual;
        }
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
        Number n = null;
        try {
            n = new BigDecimal(toTal.trim());
            toTal = formatNumero.format(n.doubleValue());
        } finally {
            return toTal;
        }
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
     * @return the entregaInicial
     */
    public String getEntregaInicial() {
        Number n = null;
        try {
            n = new BigDecimal(entregaInicial.trim());
            entregaInicial = formatNumero.format(n.doubleValue());
        } finally {
            return entregaInicial;
        }
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

    /**
     * @return the usarFechaAhora
     */
    public Boolean getUsarFechaAhora() {
        return usarFechaAhora;
    }

    /**
     * @param usarFechaAhora the usarFechaAhora to set
     */
    public void setUsarFechaAhora(Boolean usarFechaAhora) {
        this.usarFechaAhora = usarFechaAhora;

    }

    /**
     * @return the usarFechaEntregaAhora
     */
    public Boolean getUsarFechaEntregaAhora() {
        return usarFechaEntregaAhora;
    }

    /**
     * @param usarFechaEntregaAhora the usarFechaEntregaAhora to set
     */
    public void setUsarFechaEntregaAhora(Boolean usarFechaEntregaAhora) {
        this.usarFechaEntregaAhora = usarFechaEntregaAhora;
    }

    /**
     * @return the cantidad
     */
    public Short getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the listaUbicacion
     */
    public List<SelectItem> getListaUbicacion() {
        return listaUbicacion;
    }

    /**
     * @param listaUbicacion the listaUbicacion to set
     */
    public void setListaUbicacion(List<SelectItem> listaUbicacion) {
        this.listaUbicacion = listaUbicacion;
    }

    /**
     * @return the listaMotosNuevas
     */
    public List<Pojo> getListaMotosNuevas() {
        return listaMotosNuevas;
    }

    /**
     * @param listaMotosNuevas the listaMotosNuevas to set
     */
    public void setListaMotosNuevas(List<Pojo> listaMotosNuevas) {
        this.listaMotosNuevas = listaMotosNuevas;
    }

    /**
     * @return the listaMoto
     */
    public List<SelectItem> getListaMoto() {
        return listaMoto;
    }
}

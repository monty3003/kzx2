/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class MotoBean extends AbstractPageBean<Moto> {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private MotoFacade facade;
    private Moto moto;
    //Listas
    private List<SelectItem> listaCategoria;
    private List<SelectItem> listaPersona;
    //Campos de busqueda
    private String codigo;
    private String categoria;
    private String codigoFabrica;
    private String marca;
    private String modelo;
    private String color;
    private String fabricante;
    private Boolean activoFiltro;

    /** Creates a new instance of MotoBean */
    public MotoBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        this.codigo = null;
        this.categoria = null;
        this.codigoFabrica = null;
        this.marca = null;
        this.modelo = null;
        this.color = null;
        this.fabricante = null;
    }

    /**
     * @return the facade
     */
    public MotoFacade getFacade() {
        if (this.facade == null) {
            this.facade = new MotoFacade();
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

    @Override
    public String listar() {
        LoginBean.getInstance().setUbicacion("Motos");
        limpiarCampos();
        setNav("moto");
        if (facade.getOrden() == null) {
            getFacade().setOrden(new Orden("codigo", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return getNav();
    }

    @Override
    List filtrar() {
        Persona fab = new Persona();
        Categoria cat = new Categoria();
        if (fabricante != null && !fabricante.equals("-1")) {
            fab = new Persona(fabricante);
        }
        if (categoria != null && !categoria.equals("-1")) {
            cat = new Categoria(Integer.valueOf(categoria));
        }
        getFacade().setEntity(new Moto(codigo, codigoFabrica, marca, modelo, color,
                fab, cat));
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategoria = JsfUtils.getSelectItems(new CategoriaFacade().findBetween(30, 40), !getModificar());
        listaPersona = JsfUtils.getSelectItems(new PersonaFacade().findByPersona(20), !getModificar());
    }

    @Override
    public String buscar() {
        Persona fab = new Persona();
        Categoria cat = new Categoria();
        if (fabricante != null && !fabricante.equals("-1")) {
            fab = new Persona(fabricante);
        }
        if (categoria != null && !categoria.equals("-1")) {
            cat = new Categoria(Integer.valueOf(categoria));
        }
        if (codigo.equals("")) {
            codigo = null;
        }
        if (codigoFabrica.equals("")) {
            codigoFabrica = null;
        }
        if (marca.equals("")) {
            marca = null;
        }
        if (modelo.equals("")) {
            modelo = null;
        }
        if (color.equals("")) {
            color = null;
        }
        getFacade().setEntity(new Moto(codigo, codigoFabrica, marca, modelo, color, fab, cat));
        if (activoFiltro != null) {
            getFacade().getEntity().setActivo(activoFiltro ? 'S' : 'N');
        }
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
        setMoto(new Moto());
        obtenerListas();
        return "moto";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.setCodigo((String) request.getParameter("radio"));
        if (this.getCodigo() != null) {
            try {
                moto = facade.find(this.getCodigo());
                setActivo(moto.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(UbicacionBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            categoria = String.valueOf(moto.getCategoria().getId());
            fabricante = String.valueOf(moto.getCategoria().getId());
            setCategoria(String.valueOf(moto.getCategoria().getId()));
            return "moto";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    boolean validar() {
        if (getMoto().getCodigo().trim().equals("")) {
            setErrorMessage("frm:codigo", "Ingrese un valor");
            return false;
        } else {
            Moto existe = null;
            existe = getFacade().find(codigo.trim());
            if (existe != null && facade.getEntity() == null) {
                setErrorMessage("frm:codigo", "Ya existe una moto con este código");
                return false;
            }
        }
        boolean res = true;
        if (getMoto().getCodigoFabrica().trim().equals("")) {
            setErrorMessage("frm:codigoFabrica", "Ingrese un valor");
            res = false;
        }
        if (getMoto().getMarca().trim().equals("")) {
            setErrorMessage("frm:marca", "Ingrese un valor");
            res = false;
        }
        if (getMoto().getModelo().trim().equals("")) {
            setErrorMessage("frm:modelo", "Ingrese un valor");
            res = false;
        }
        if (getMoto().getColor().trim().equals("")) {
            setErrorMessage("frm:color", "Ingrese un valor");
            res = false;
        }
        if (fabricante == null || fabricante.equals("-1")) {
            setErrorMessage("frm:fabricante", "Seleccione un valor");
            res = false;
        }
        if (categoria == null || categoria.equals("-1")) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        return res;
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            moto.getCategoria().setId(Integer.valueOf(categoria));
            moto.getFabricante().setId(Integer.valueOf(fabricante));
            getFacade().setEntity(moto);
            if (getModificar()) {
                getFacade().getEntity().setActivo(getActivo() ? 'S' : 'N');
                getFacade().guardar();
                setInfoMessage(null, facade.ex2);
            } else {
                getFacade().create();
                setInfoMessage(null, getFacade().ex1);
            }
        } else {
            return null;
        }
        return this.listar();
    }

    @Override
    public String cancelar() {
        setMoto(new Moto());
        getFacade().setEntity(getMoto());
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
        setMoto(new Moto());
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("codigo", false));
        this.filtrar();
        return getNav();
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigoFabrica
     */
    public String getCodigoFabrica() {
        return codigoFabrica;
    }

    /**
     * @param codigoFabrica the codigoFabrica to set
     */
    public void setCodigoFabrica(String codigoFabrica) {
        this.codigoFabrica = codigoFabrica;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the fabricante
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * @param fabricante the fabricante to set
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the listaCategoria
     */
    public List<SelectItem> getListaCategoria() {
        return listaCategoria;
    }

    /**
     * @return the listaPersona
     */
    public List<SelectItem> getListaPersona() {
        return listaPersona;
    }

    /**
     * @return the moto
     */
    public Moto getMoto() {
        return moto;
    }

    /**
     * @param moto the moto to set
     */
    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public Boolean getActivoFiltro() {
        return activoFiltro;
    }

    public void setActivoFiltro(Boolean activoFiltro) {
        this.activoFiltro = activoFiltro;
    }
}

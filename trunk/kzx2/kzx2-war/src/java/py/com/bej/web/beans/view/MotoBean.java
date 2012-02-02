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
import py.com.bej.orm.utils.Orden;
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
    private PersonaFacade personaFacade;
    @EJB
    private MotoFacade facade;
    private Moto moto;
    //Listas
    private List<SelectItem> listaCategoria;
    private List<SelectItem> listaPersona;
    //Campos de busqueda
    private String codigo;
    private Integer categoria;
    private String codigoFabrica;
    private String marca;
    private String modelo;
    private String color;
    private Integer fabricante;

    /** Creates a new instance of MotoBean */
    public MotoBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
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
    public PersonaFacade getPersonaFacade() {
        if (this.personaFacade == null) {
            this.personaFacade = new PersonaFacade();
        }
        return personaFacade;
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
        limpiarCampos();
        setNav("moto");
        setDesde(0);
        setMax(10);
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
        getFacade().setEntity(new Moto(codigo, codigoFabrica, marca, modelo, color, new Persona(fabricante), new Categoria(categoria)));
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
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
        getFacade().setEntity(new Moto(codigo, codigoFabrica, marca, modelo, color, new Persona(fabricante), new Categoria(categoria)));
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
                setMoto(facade.find(this.getCodigo()));
                setActivo(getMoto().getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(UbicacionBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            setFabricante(facade.getEntity().getCategoria().getId());
            setCategoria(facade.getEntity().getCategoria().getId());
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
            existe = facade.find(getCodigo().trim());
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
        if (getMoto().getFabricante() == null) {
            setErrorMessage("frm:fabricante", "Seleccione un valor");
            res = false;
        }
        if (getMoto().getCategoria() == null) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        return res;
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
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
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
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
    public Integer getFabricante() {
        return fabricante;
    }

    /**
     * @param fabricante the fabricante to set
     */
    public void setFabricante(Integer fabricante) {
        this.fabricante = fabricante;
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
}

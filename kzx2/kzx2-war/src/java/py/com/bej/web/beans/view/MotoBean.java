/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.PersonaPK;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class MotoBean extends AbstractPageBean {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private MotoFacade facade;
    private List<Categoria> listaCategorias;
    private List<SelectItem> listaCategoria;
    private List<Persona> listaPersonas;
    private List<SelectItem> listaPersona;
    //Moto
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
    void deEntity() {
        this.setCodigo(facade.getEntity().getCodigo());
        this.setCodigoFabrica(facade.getEntity().getCodigoFabrica());
        this.setMarca(facade.getEntity().getMarca());
        this.setModelo(facade.getEntity().getModelo());
        this.setColor(facade.getEntity().getColor());
        if (facade.getEntity().getFabricante() != null) {
            this.setFabricante(facade.getEntity().getFabricante().getPersonaPK().getId());
        }
        if (facade.getEntity().getCategoria() != null) {
            setCategoria(facade.getEntity().getCategoria().getId());
        }
    }

    @Override
    void deCampos() {
        if (this.codigo != null && !this.codigo.trim().equals("")) {
            facade.getEntity().setCodigo(getCodigo().trim().toUpperCase());
        } else {
            facade.getEntity().setCodigo(null);
        }
        if (this.getCodigoFabrica() != null && !this.getCodigoFabrica().trim().equals("")) {
            facade.getEntity().setCodigoFabrica(getCodigoFabrica().trim().toUpperCase());
        } else {
            facade.getEntity().setCodigoFabrica(null);
        }
        if (this.getMarca() != null && !this.getMarca().trim().equals("")) {
            facade.getEntity().setMarca(marca.trim().toUpperCase());
        } else {
            facade.getEntity().setMarca(null);
        }
        if (this.getModelo() != null && !this.getModelo().trim().equals("")) {
            facade.getEntity().setModelo(modelo.trim().toUpperCase());
        } else {
            facade.getEntity().setModelo(null);
        }
        if (this.getColor() != null && !this.getColor().trim().equals("")) {
            facade.getEntity().setColor(color.trim().toUpperCase());
        } else {
            facade.getEntity().setColor(null);
        }
    }

    @Override
    void limpiarCampos() {
        getFacade().setEntity(new Moto());
        if (facade.getOrden() == null) {
            getFacade().setOrden(new Orden("codigo", false));
        }
        setCodigo(null);
        setCodigoFabrica(null);
        setMarca(null);
        setModelo(null);
        setColor(null);
        setFabricante(null);
        setCategoria(null);
        setDesde(0);
        setMax(10);
        setNav("listamotos");
    }

    @Override
    public String listar() {
        setNav("listamotos");
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
        getFacade().setEntity(new Moto());
        deCampos();
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategorias = new CategoriaFacade().findBetween(50, 60);
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                getListaCategoria().add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }
        listaPersonas = new PersonaFacade().findByPersona(20);
        if (!listaPersonas.isEmpty()) {
            Iterator<Persona> it = listaPersonas.iterator();
            do {
                Persona x = it.next();
                getListaPersona().add(new SelectItem(x.getPersonaPK().getId(), x.getNombre()));
            } while (it.hasNext());

        }
    }

    @Override
    public String buscar() {
        getFacade().setEntity(new Moto());
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
        getFacade().setEntity(null);
        listaCategoria = new ArrayList<SelectItem>();
        getListaCategoria().add(new SelectItem("-1", "-SELECCIONAR-"));
        listaPersona = new ArrayList<SelectItem>();
        getListaPersona().add(new SelectItem(-1, "-SELECCIONAR-"));
        obtenerListas();
        return "crearmoto";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.setCodigo((String) request.getParameter("radio"));
        if (this.getCodigo() != null) {
            try {
                getFacade().setEntity(facade.find(this.getCodigo()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            deEntity();
            listaCategoria = new ArrayList<SelectItem>();
            listaPersona = new ArrayList<SelectItem>();
            obtenerListas();
            setFabricante(facade.getEntity().getCategoria().getId());
            setCategoria(facade.getEntity().getCategoria().getId());
            return "modificarmoto";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    boolean validarNuevo() {
        if (getCodigo().trim().equals("")) {
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
        if (getCodigoFabrica().trim().equals("")) {
            setErrorMessage("frm:codigoFabrica", "Ingrese un valor");
            res = false;
        }
        if (getMarca().trim().equals("")) {
            setErrorMessage("frm:marca", "Ingrese un valor");
            res = false;
        }
        if (getModelo().trim().equals("")) {
            setErrorMessage("frm:modelo", "Ingrese un valor");
            res = false;
        }
        if (getColor().trim().equals("")) {
            setErrorMessage("frm:color", "Ingrese un valor");
            res = false;
        }
        if (getFabricante() == -1) {
            setErrorMessage("frm:fabricante", "Seleccione un valor");
            res = false;
        }
        if (getCategoria() == -1) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        return res;
    }

    @Override
    public String guardarNuevo() {
        boolean validado = validarNuevo();
        if (validado) {
            getFacade().setEntity(new Moto());
            deCampos();
            Persona xfabricante = null;
            Categoria xcategoria = null;
            getPersonaFacade().setEntity(new Persona(new PersonaPK(getFabricante(), null)));
            xfabricante = getPersonaFacade().findById();
            xcategoria = getCategoriaFacade().find(getCategoria());
            facade.getEntity().setFabricante(xfabricante);
            facade.getEntity().setCategoria(xcategoria);
            facade.create();
            setInfoMessage(null, facade.ex1);
        } else {
            return null;
        }
        limpiarCampos();
        return this.listar();
    }

    @Override
    public String guardarModificar() {
        boolean validado = validarNuevo();
        if (validado) {
            deCampos();
            if (!facade.getEntity().getFabricante().getPersonaPK().getId().equals(getFabricante())) {
                Persona xfabricante = null;
                xfabricante = getPersonaFacade().find(getFabricante());
                facade.getEntity().setFabricante(xfabricante);
            }
            if (!facade.getEntity().getCategoria().getId().equals(getCategoria())) {
                Categoria xcategoria = null;
                xcategoria = getCategoriaFacade().find(getCategoria());
                facade.getEntity().setCategoria(xcategoria);
            }
            facade.guardar();
            setInfoMessage(null, facade.ex2);
            limpiarCampos();
            return this.listar();
        } else {
            return null;
        }
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

    @Override
    public String todos() {
        limpiarCampos();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Integer[]{0, 10});
        getFacade().setOrden(new Orden("codigo", false));
        this.setValido((Boolean) true);
        deCampos();
        getFacade().setRango(new Integer[]{0, 10});
        this.filtrar();
        return getNav();
    }

    @Override
    boolean validarModificar() {
        throw new UnsupportedOperationException("Not supported yet.");
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
}

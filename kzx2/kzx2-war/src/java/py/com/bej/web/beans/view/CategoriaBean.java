/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.session.CategoriaFacade;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class CategoriaBean {

    @EJB
    private CategoriaFacade facade;
    private Categoria c;
    private List lista;
    private Integer desde;
    private Integer max;
    private Integer total;
    private String nav = "listacategoria";
    private String id;
    private String descripcion;
    private Boolean valido;

    /** Creates a new instance of CategoriaBean */
    public CategoriaBean() {
    }

    public String listar() {
        this.id = null;
        this.descripcion = null;
        mapearValores();
        this.setDesde(new Integer(0));
        this.setMax(new Integer(10));
        this.valido = true;
        this.filtrar();
        if (this.lista.isEmpty()) {
            setErrorMessage(null, facade.r0, null);
        }
        return nav;
    }

    public List<Categoria> filtrar() {
        mapearValores();
        setLista(new ArrayList<Categoria>());
        int[] range = {this.getDesde(), this.getMax()};
        lista = facade.findRange(range, c);
        return getLista();
    }

    public String buscar() {
        mapearValores();
        desde = 0;
        max = 10;
        this.filtrar();
        if (this.lista.isEmpty()) {
            setErrorMessage(null, facade.c0, null);
        }
        return nav;
    }

    public String nuevo() {
        c = new Categoria();
        return "crearcategoria";
    }

    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.id = (String) request.getParameter("radio");
        if (this.id != null) {
            try {
                this.c = facade.find(new Integer(id));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            this.id = c.getId().toString();
            this.descripcion = c.getDescripcion();
            return "modificarcategoria";
        } else {
            setErrorMessage(null, facade.sel, null);
            return null;
        }
    }

    public String guardar() {
        boolean exito = facade.create(c);
        if (exito) {
            setInfoMessage(null, facade.ex1, null);
            return this.listar();
        } else {
            FacesContext.getCurrentInstance().addMessage("frm:id", new FacesMessage("Id ya existe"));
            return null;
        }
    }

    public String guardarModificar() {
        if (this.descripcion.trim().equals("")) {
            FacesContext.getCurrentInstance().addMessage("frm:descripcion", new FacesMessage("Ingrese una descripción"));
            return null;
        } else {
            this.c.setDescripcion(descripcion);
            facade.guardar(c);
            setInfoMessage(null, facade.ex2, null);
            return this.listar();
        }
    }

    public String cancelar() {
        return this.listar();
    }

    public String todos() {
        id = null;
        descripcion = null;
        c = new Categoria();
        facade.setCol(null);
        this.valido = true;
        mapearValores();
        desde = 0;
        max = 10;
        this.filtrar();
        return nav;
    }

    public String anterior() {
        desde -= max;
        int[] range = {desde, max};
        this.lista = getFacade().anterior(range, getC());
        return nav;
    }

    public String siguiente() {
        desde += max;
        int[] range = {desde, max};
        this.lista = getFacade().siguiente(range, getC());
        return nav;
    }

    public Integer getUltimoItem() {
        mapearValores();
        CategoriaFacade.c = c;
        int[] range = {desde, max};
        return getFacade().getUltimoItem(range);
    }

    private void mapearValores() {
        c = new Categoria();
        if (this.id != null && !this.id.trim().equals("")) {
            this.c.setId(new Integer(id));
        } else {
            this.c.setId(null);
        }
        if (this.descripcion != null && !this.descripcion.trim().equals("")) {
            this.c.setDescripcion(descripcion);
        } else {
            this.c.setDescripcion(null);
        }
    }

    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
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

    /**
     * @return the facade
     */
    public CategoriaFacade getFacade() {
        return facade;
    }

    /**
     * @return the c
     */
    public Categoria getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(Categoria c) {
        this.c = c;
    }

    protected void setErrorMessage(UIComponent component, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(((component != null) ? component.getClientId(FacesContext.getCurrentInstance()) : null), new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    protected void setInfoMessage(UIComponent component, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(((component != null) ? component.getClientId(FacesContext.getCurrentInstance()) : null), new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;

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
        if (id != null && !id.trim().equals("")) {
            try {
                new Integer(id);
                this.valido = true;
            } catch (Exception e) {
                this.valido = false;
                FacesContext.getCurrentInstance().addMessage("frmBuscar:id", new FacesMessage(null, "Ingrese un número válido"));
            } finally {
                this.id = id;
            }
        }
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
}

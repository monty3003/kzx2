/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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

    /** Creates a new instance of CategoriaBean */
    public CategoriaBean() {
    }

    public String listar() {
        c= new Categoria();
        this.setDesde(new Integer(0));
        this.setMax(new Integer(10));
        this.filtrar();
        return nav;
    }

    public List<Categoria> filtrar() {
        setLista(new ArrayList<Categoria>());
        int[] range = {this.getDesde(), this.getMax()};
        lista = facade.findRange(range, c);
        return getLista();
    }

    public String buscar() {
        desde = 0;
        max = 10;
        this.filtrar();
        return nav;
    }

    public String todos() {
        desde = 0;
        max = 0;
        c  = null;
        this.filtrar();
        return nav;
    }

    public String anterior() {
        int[] range = {desde, max};
        this.lista = getFacade().anterior(range, getC());
        return nav;
    }

    public Integer getUltimoItem() {
        int[] range = {desde, max};
        return getFacade().getUltimoItem(range);
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
        if (this.total == null) {
            this.total = getFacade().count();
        }
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
    }

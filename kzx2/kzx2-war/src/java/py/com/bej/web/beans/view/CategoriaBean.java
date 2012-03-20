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
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class CategoriaBean extends AbstractPageBean<Categoria> {

    @EJB
    private CategoriaFacade facade;
    //Entity
    private Categoria categoria;
    //Campos de busqueda
    private Integer id;
    private String descripcion;

    /** Creates a new instance of CategoriaBean */
    public CategoriaBean() {
    }

    /**
     * @return the facade
     */
    public CategoriaFacade getFacade() {
        if (this.facade == null) {
            this.facade = new CategoriaFacade();
        }
        return facade;
    }

    @Override
    void limpiarCampos() {
        setModificar(Boolean.FALSE);
        setAgregar(Boolean.FALSE);
        this.id = null;
        this.descripcion = null;
    }

    @Override
    public String listar() {
        limpiarCampos();
        setNav("categoria");
        categoria = new Categoria();
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        setValido(true);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return getNav();
    }

    @Override
    List filtrar() {
        getFacade().setEntity(categoria);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String buscar() {
        categoria = new Categoria(id, descripcion, null, null);
        getFacade().setEntity(categoria);
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        categoria = new Categoria();
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        return "categoria";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String xid = null;
        xid = ((String) request.getParameter("radio"));
        if (xid != null) {
            try {
                categoria = getFacade().find(new Integer(xid));
                setActivo(categoria.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(CategoriaBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setModificar(Boolean.TRUE);
            setAgregar(Boolean.FALSE);
            return "categoria";
        } else {
            setErrorMessage(null, getFacade().sel);
            return null;
        }
    }

    @Override
    boolean validar() {
        boolean res = true;
        if (categoria.getId() == null) {
            setErrorMessage("frm:id", "Ingrese un valor");
            return false;
        } else {
            Integer x = null;
            try {
                x = new Integer(categoria.getId());
                if (x < 0) {
                    setErrorMessage("frm:id", "Ingrese un valor positivo");
                    res = false;
                } else if (getAgregar()) {
                    Categoria existe = null;
                    existe = getFacade().find(x);
                    if (existe != null) {
                        setErrorMessage("frm:id", "Esta categoria ya existe");
                        res = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            getFacade().setEntity(categoria);
            if (getModificar()) {
                getFacade().getEntity().setActivo(getActivo() ? 'S' : 'N');
                getFacade().guardar();
                setInfoMessage(null, facade.ex2);
            } else {
                getFacade().create();
                setInfoMessage(null, getFacade().ex1);
            }
            return this.listar();
        } else {
            return null;
        }
    }

    @Override
    public String cancelar() {
        categoria = new Categoria();
        getFacade().setEntity(categoria);
        return getNav();
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
        categoria = new Categoria();
        getFacade().setEntity(categoria);
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Long[]{0L, 10L});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
}

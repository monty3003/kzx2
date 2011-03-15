/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class CategoriaBean extends AbstractPageBean {

    @EJB
    private CategoriaFacade facade;
    private String id;
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
    void deEntity() {
        this.setId(getFacade().getEntity().getId().toString());
        this.setDescripcion(getFacade().getEntity().getDescripcion());
    }

    @Override
    void deCampos() {
        getFacade().setEntity(new Categoria());
        if (this.getId() != null && !this.id.trim().equals("")) {
            getFacade().getEntity().setId(new Integer(getId().trim()));
        }
        if (this.getDescripcion() != null) {
            getFacade().getEntity().setDescripcion(this.getDescripcion().trim());
        }
    }

    @Override
    void limpiarCampos() {
        getFacade().setEntity(new Categoria());
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setDesde(0);
        setMax(10);
        this.setId(null);
        this.setDescripcion(null);
        setNav("listacategoria");
    }

    @Override
    public String listar() {
        setNav("listacategoria");
        setDesde(0);
        setMax(10);
        setValido(true);
       if(getFacade().getOrden()==null){
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
        deCampos();
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String buscar() {
        if (id != null && !id.trim().equals("")) {
            Integer x = null;
            try {
                x = new Integer(id.trim());
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage("frm:id", "Ingrese un número válido");
                return null;
            }
        }
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
        return "crearcategoria";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String xid = null;
        xid = ((String) request.getParameter("radio"));
        if (xid != null) {
            try {
                getFacade().setEntity(getFacade().find(new Integer(xid)));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            deEntity();
            setModificar(true);
            return "modificarcategoria";
        } else {
            setErrorMessage(null, getFacade().sel);
            return null;
        }
    }

    @Override
    boolean validarNuevo() {
        boolean res = true;
        if (this.getId().trim().equals("")) {
            setErrorMessage("frm:id", "Ingrese un valor");
            return false;
        } else {
            Integer x = null;
            try {
                x = new Integer(this.getId());
                if (x < 0) {
                    setErrorMessage("frm:id", "Ingrese un valor positivo");
                    res = false;
                } else {
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

        if (this.getDescripcion().trim().equals("")) {
            setErrorMessage("frm:descripcion", "Ingrese un valor");
            res = false;
        }
        return res;
    }

    @Override
    boolean validarModificar() {
        if (this.getDescripcion().trim().equals("")) {
            setErrorMessage("frm:descripcion", "Ingrese un valor");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String guardarNuevo() {
        boolean validado = validarNuevo();
        if (validado) {
            getFacade().create();
            setInfoMessage(null, getFacade().ex1);
            return this.listar();
        } else {
            FacesContext.getCurrentInstance().addMessage("frm:id", new FacesMessage("Id ya existe"));
            return null;
        }
    }

    @Override
    public String guardarModificar() {
        boolean validado = validarModificar();
        if (validado) {
            getFacade().guardar();
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
        this.setValido((Boolean) true);
        getFacade().setRango(new Integer[]{0, 10});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
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

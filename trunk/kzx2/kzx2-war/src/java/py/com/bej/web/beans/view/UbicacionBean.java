/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Ubicacion;
import py.com.bej.orm.session.UbicacionFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class UbicacionBean extends AbstractPageBean<Ubicacion> {

    @EJB
    private UbicacionFacade facade;
    private Integer id;
    private String descripcion;
    private Ubicacion ubicacion;

    /** Creates a new instance of UbicacionBean */
    public UbicacionBean() {
    }

    /**
     * @return the facade
     */
    public UbicacionFacade getFacade() {
        if (this.facade == null) {
            this.facade = new UbicacionFacade();
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
        ubicacion = new Ubicacion();
        setNav("ubicacion");
        setDesde(0);
        setMax(10);
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
        getFacade().setEntity(ubicacion);
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
        getFacade().setEntity(new Ubicacion(id, descripcion, null, null));
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        ubicacion = new Ubicacion();
        getFacade().setEntity(ubicacion);
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        return "ubicacion";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String xid = null;
        xid = ((String) request.getParameter("radio"));
        if (xid != null) {
            try {
                ubicacion = getFacade().find(new Integer(xid));
                setActivo(ubicacion.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(CategoriaBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setModificar(Boolean.TRUE);
            setAgregar(Boolean.FALSE);
            return "ubicacion";
        } else {
            setErrorMessage(null, getFacade().sel);
            return null;
        }
    }

    @Override
    boolean validar() {
        boolean res = true;
        Ubicacion u = null;
        try {
            u = getFacade().findByDescripcion(ubicacion.getDescripcion().trim());
        } catch (Exception ex) {
            Logger.getLogger(UbicacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (u != null) {
            setErrorMessage("frm:descripcion", "Esta ubicacion ya existe");
            res = false;
        }
        return res;
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            getFacade().setEntity(ubicacion);
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
            FacesContext.getCurrentInstance().addMessage("frm:id", new FacesMessage("Id ya existe"));
            return null;
        }
    }

    @Override
    public String cancelar() {
        ubicacion = new Ubicacion();
        getFacade().setEntity(ubicacion);
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
        ubicacion = new Ubicacion();
        getFacade().setEntity(ubicacion);
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

    /**
     * @return the ubicacion
     */
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}

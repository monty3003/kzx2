/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.session.FinanciacionFacade;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class FinanciacionBean extends AbstractPageBean<Financiacion> {

    @EJB
    private FinanciacionFacade facade;

    /** Creates a new instance of FinanciacionBean */
    public FinanciacionBean() {
    }

    public FinanciacionFacade getFacade() {
        if (facade == null) {
            facade = new FinanciacionFacade();
        }
        return facade;
    }

    @Override
    void limpiarCampos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String listar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    List<Financiacion> filtrar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void obtenerListas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String buscar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String nuevo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String modificar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    boolean validar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String guardar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String cancelar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String anterior() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String siguiente() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String todos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

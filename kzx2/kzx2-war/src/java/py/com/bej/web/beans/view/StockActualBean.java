/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.web.utils.GeneradorReporte;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class StockActualBean implements Serializable {

    @EJB
    private MotostockFacade motostockFacade;
    private static final long serialVersionUID = 3434563L;
    private List<Motostock> lista;

    /** Creates a new instance of StockActualBean */
    public StockActualBean() {
        this.motostockFacade = new MotostockFacade();
        lista = motostockFacade.findStockActual();
    }

    public MotostockFacade getMotostockFacade() {
        if (motostockFacade == null) {
            motostockFacade = new MotostockFacade();
        }
        return motostockFacade;
    }

    public String verStockActual() {
        lista = getMotostockFacade().findStockActual();
        return null;
    }

    public List<Motostock> getLista() {
        return lista;
    }

    public void setLista(List<Motostock> lista) {
        this.lista = lista;
    }

    public void imprimirStock() {
        new GeneradorReporte().generarStock(lista, "stock");
    }

    public void imprimirStockPorUbicacion() {
        new GeneradorReporte().generarStock(lista, "stockPorUbicacion");
    }
    public void imprimirStockPorModelo() {
        new GeneradorReporte().generarStock(lista, "stockPorModelo");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Pagare;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.PagareFacade;
import py.com.bej.orm.session.TransaccionFacade;
import py.com.bej.orm.utils.CategoriaEnum;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@RequestScoped
public class MainBean implements Serializable {

    @EJB
    private FinanciacionFacade financiacionFacade;
    @EJB
    private PagareFacade pagareFacade;
    @EJB
    private TransaccionFacade transaccionFacade;
    private List<Pagare> listaPagareProximosAVencer;
    private List<Transaccion> listaUltimasVentas;
    private List<Financiacion> listaCuotasProximasAVencer;
    private static final long serialVersionUID = 1L;

    /** Creates a new instance of MainBean */
    public MainBean() {
        transaccionFacade = new TransaccionFacade();
        pagareFacade = new PagareFacade();
        financiacionFacade = new FinanciacionFacade();
        //Obtener listas
        listaUltimasVentas = transaccionFacade.findUltimasVentas();
        listaPagareProximosAVencer = pagareFacade.findPagaresProximosAVencer();
        try {
            listaCuotasProximasAVencer = financiacionFacade.buscarCuotasQueVencen(null, new Date(), 15);
        } catch (Exception ex) {
            Logger.getLogger(MainBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Financiacion> getListaCuotasProximasAVencer() {
        return listaCuotasProximasAVencer;
    }

    public void setListaCuotasProximasAVencer(List<Financiacion> listaCuotasProximasAVencer) {
        this.listaCuotasProximasAVencer = listaCuotasProximasAVencer;
    }

    public List<Pagare> getListaPagareProximosAVencer() {
        return listaPagareProximosAVencer;
    }

    public void setListaPagareProximosAVencer(List<Pagare> listaPagareProximosAVencer) {
        this.listaPagareProximosAVencer = listaPagareProximosAVencer;
    }

    public List<Transaccion> getListaUltimasVentas() {
        return listaUltimasVentas;
    }

    public void setListaUltimasVentas(List<Transaccion> listaUltimasVentas) {
        this.listaUltimasVentas = listaUltimasVentas;
    }
}

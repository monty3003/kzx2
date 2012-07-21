/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Pagare;
import py.com.bej.orm.session.PagareFacade;
import py.com.bej.orm.utils.Orden;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class PagareBean extends AbstractPageBean<Pagare> {

    @EJB
    private PagareFacade facade;
    //Campos de busqueda
    private String idFiltro;
    private String creditoFiltro;
    private String numeroFiltro;
    private Date fechaVencimientoFiltro;
    private Character vencidoFiltro;
    private Date fechaDeCancelacionFiltro;
    private Pagare pagare;

    /** Creates a new instance of PagareBean */
    public PagareBean() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
    }

    public PagareFacade getFacade() {
        if (facade == null) {
            facade = new PagareFacade();
        }
        return facade;
    }

    @Override
    void limpiarCampos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String listar() {
        setModificar(Boolean.FALSE);
        setAgregar(Boolean.FALSE);
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return null;
    }

    @Override
    List<Pagare> filtrar() {
        pagare = new Pagare((idFiltro != null && !idFiltro.trim().equals("")) ? Integer.valueOf(idFiltro) : null,
                (creditoFiltro != null && !creditoFiltro.trim().equals("")) ? new Credito(Integer.valueOf(creditoFiltro)) : null,
                (numeroFiltro != null && !numeroFiltro.trim().equals("")) ? Short.valueOf(numeroFiltro) : null,
                (fechaVencimientoFiltro != null) ? fechaVencimientoFiltro : null, null, null, null,
                (fechaDeCancelacionFiltro != null) ? fechaDeCancelacionFiltro : null,
                null, null, null, null, null);
        if (vencidoFiltro != null && !vencidoFiltro.equals('X')) {
            pagare.setVencido(vencidoFiltro.equals('S'));
        }
        getFacade().setEntity(pagare);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        return getFacade().findRange();
    }

    @Override
    void obtenerListas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String buscar() {
        getFacade().setContador(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return null;
    }

    @Override
    public String nuevo() {
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        pagare = new Pagare();
        obtenerListas();
        return null;
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer idFiltr = new Integer(request.getParameter("radio"));
        if (idFiltr != null) {
            try {
                pagare = facade.find(idFiltr);
                setActivo(pagare.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(PagareBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
        } else {
            setErrorMessage(null, facade.sel);
        }
        return null;
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
        setLista(getFacade().siguiente());
        return null;
    }

    @Override
    public String todos() {
        limpiarCampos();
        pagare = new Pagare();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        this.setValido((Boolean) true);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("id", false));
        filtrar();
        return null;
    }

    public String getCreditoFiltro() {
        return creditoFiltro;
    }

    public void setCreditoFiltro(String creditoFiltro) {
        this.creditoFiltro = creditoFiltro;
    }

    public Date getFechaDeCancelacionFiltro() {
        return fechaDeCancelacionFiltro;
    }

    public void setFechaDeCancelacionFiltro(Date fechaDeCancelacionFiltro) {
        this.fechaDeCancelacionFiltro = fechaDeCancelacionFiltro;
    }

    public Date getFechaVencimientoFiltro() {
        return fechaVencimientoFiltro;
    }

    public void setFechaVencimientoFiltro(Date fechaVencimientoFiltro) {
        this.fechaVencimientoFiltro = fechaVencimientoFiltro;
    }

    public String getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(String idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getNumeroFiltro() {
        return numeroFiltro;
    }

    public void setNumeroFiltro(String numeroFiltro) {
        this.numeroFiltro = numeroFiltro;
    }

    public Pagare getPagare() {
        return pagare;
    }

    public void setPagare(Pagare pagare) {
        this.pagare = pagare;
    }

    public Character getVencidoFiltro() {
        return vencidoFiltro;
    }

    public void setVencidoFiltro(Character vencidoFiltro) {
        this.vencidoFiltro = vencidoFiltro;
    }
}

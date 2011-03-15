/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author diego
 */
public abstract class AbstractPageBean {

    private Integer desde;
    private Integer max;
    private Integer total;
    private String nav;
    private Boolean valido;
    private DateFormat formatFechaHora;
    private NumberFormat formatNumero;
    private Calendar ahora;
    private Boolean modificar;
    private List lista;

    abstract void deEntity();

    abstract void deCampos();

    abstract void limpiarCampos();

    public abstract String listar();

    abstract List filtrar();

    abstract void obtenerListas();

    public abstract String buscar();

    public abstract String nuevo();

    public abstract String modificar();

    abstract boolean validarNuevo();

    abstract boolean validarModificar();

    public abstract String guardarNuevo();

    public abstract String guardarModificar();

    public abstract String cancelar();

    public abstract String anterior();

    public abstract String siguiente();

    public abstract String todos();

    void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
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
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the nav
     */
    public String getNav() {
        return nav;
    }

    /**
     * @param nav the nav to set
     */
    public void setNav(String nav) {
        this.nav = nav;
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

    /**
     * @return the formatFechaHora
     */
    public DateFormat getFormatFechaHora() {
        return formatFechaHora;
    }

    /**
     * @param formatFechaHora the formatFechaHora to set
     */
    public void setFormatFechaHora(DateFormat formatFechaHora) {
        this.formatFechaHora = formatFechaHora;
    }

    /**
     * @return the formatNumero
     */
    public NumberFormat getFormatNumero() {
        return formatNumero;
    }

    /**
     * @param formatNumero the formatNumero to set
     */
    public void setFormatNumero(NumberFormat formatNumero) {
        this.formatNumero = formatNumero;
    }

    /**
     * @return the ahora
     */
    public Calendar getAhora() {
        return ahora;
    }

    /**
     * @param ahora the ahora to set
     */
    public void setAhora(Calendar ahora) {
        this.ahora = ahora;
    }

    /**
     * @return the modificar
     */
    public Boolean getModificar() {
        return modificar;
    }

    /**
     * @param modificar the modificar to set
     */
    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
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
}

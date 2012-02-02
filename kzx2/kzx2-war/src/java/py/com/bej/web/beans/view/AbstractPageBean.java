/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import py.com.bej.orm.interfaces.WithId;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.web.servlets.security.LoginBean;

/**
 *
 * @author diego
 */
public abstract class AbstractPageBean<T extends WithId> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer desde = Integer.parseInt(ConfiguracionEnum.PAG_DESDE.getSymbol());
    private Integer max = Integer.parseInt(ConfiguracionEnum.PAG_MAX.getSymbol());
    private Integer total;
    private String nav;
    private Boolean valido;
    private DateFormat formatFechaHora;
    private NumberFormat formatNumero;
    private Calendar ahora;
    private Boolean agregar;
    private Boolean modificar;
    private List<T> lista;
    private Boolean activo;

    abstract void limpiarCampos();

    public abstract String listar();

    abstract List<T> filtrar();

    abstract void obtenerListas();

    public abstract String buscar();

    public abstract String nuevo();

    public abstract String modificar();

    abstract boolean validar();

    public abstract String guardar();

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
    public List<T> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<T> lista) {
        this.lista = lista;
    }

    /**
     * @return the agregar
     */
    public Boolean getAgregar() {
        return agregar;
    }

    /**
     * @param agregar the agregar to set
     */
    public void setAgregar(Boolean agregar) {
        this.agregar = agregar;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

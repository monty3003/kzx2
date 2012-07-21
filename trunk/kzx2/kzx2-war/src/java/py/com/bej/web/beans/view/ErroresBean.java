/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.servlets.security.SessionBean;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@RequestScoped
public class ErroresBean {

    private String mensaje;

    /** Creates a new instance of ErroresBean */
    public ErroresBean() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (SessionBean.getInstance().getSesionAbierta().containsValue(LoginBean.getInstance().getUserName())) {
            SessionBean.getInstance().getSesionAbierta().remove(req.getSession().getId());
            req.getSession().invalidate();
        }
        mensaje = "Vuelva a ingresar al sistema.";
    }

    public String salir() {
        return null;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

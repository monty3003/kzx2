/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.servlets.security;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

    private String userName;
    private String password;
    private Date loginDesde;
    private String ubicacion;

    public String login() {
        if (getUserName() != null && getPassword() != null) {
            if (SessionBean.getInstance().getSesionAbierta().containsValue(getUserName())) {
                setErrorMessage(null, "El usuario " + getUserName() + " ya se encuentra autenticado");
                return null;
            }
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            SessionBean.getInstance().registrarSesion(req.getSession().getId(), getUserName());
            setUbicacion("Inicio");
            return "./secure/app/main.bej";
        } else {
            return null;
        }
    }

    void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the loginDesde
     */
    public Date getLoginDesde() {
        return loginDesde;
    }

    public static LoginBean getInstance() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return (LoginBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "loginBean");
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

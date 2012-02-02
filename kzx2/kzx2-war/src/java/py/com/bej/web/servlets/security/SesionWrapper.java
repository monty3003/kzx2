/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.servlets.security;

import java.util.Date;

/**
 *
 * @author Diego_M
 */
public class SesionWrapper {

    private String httpSesionId;
    private String userName;
    private Date sesionIniciada;

    public SesionWrapper() {
    }

    public SesionWrapper(String httpSesionId, String userName, Date sesionIniciada) {
        this.httpSesionId = httpSesionId;
        this.userName = userName;
        this.sesionIniciada = sesionIniciada;
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
     * @return the sesionIniciada
     */
    public Date getSesionIniciada() {
        return sesionIniciada;
    }

    /**
     * @param sesionIniciada the sesionIniciada to set
     */
    public void setSesionIniciada(Date sesionIniciada) {
        this.sesionIniciada = sesionIniciada;
    }

    /**
     * @return the httpSesionId
     */
    public String getHttpSesionId() {
        return httpSesionId;
    }

    /**
     * @param httpSesionId the httpSesionId to set
     */
    public void setHttpSesionId(String httpSesionId) {
        this.httpSesionId = httpSesionId;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.servlets.security;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Diego_M
 */
@Singleton
@LocalBean
public class SessionBean implements HttpSessionListener {

    private HashMap<String, String> sesionAbierta;
    private static SessionBean instance;
    private final static Logger LOGGER = Logger.getLogger(SessionBean.class.getName());

    /**
     * @return the instance
     */
    public static SessionBean getInstance() {
        if (instance == null) {
            instance = new SessionBean();
        }
        return instance;
    }

    public void registrarSesion(String sessionId, String userName) {
        this.getSesionAbierta().put(sessionId, userName);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOGGER.log(Level.INFO, "Se ha iniciado la sesion {0}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        this.getSesionAbierta().remove(se.getSession().getId());
        LOGGER.log(Level.INFO, "Se ha destruido la sesion {0} ", se.getSession().getId());
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     * @return the sesionAbierta
     */
    public HashMap<String, String> getSesionAbierta() {
        if (sesionAbierta == null) {
            sesionAbierta = new HashMap<String, String>();
        }
        return sesionAbierta;
    }

    /**
     * @param sesionAbierta the sesionAbierta to set
     */
    public void setSesionAbierta(HashMap sesionAbierta) {
        this.sesionAbierta = sesionAbierta;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.servlets.security;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import py.com.bej.web.log.logger.AppLogger;

/**
 *
 * @author Diego_M
 */
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            AppLogger.setup();
        } catch (IOException ex) {
            throw new RuntimeException("Ocurrio un error al intentar crear los logs files ", ex);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (SessionBean.getInstance().getSesionAbierta().containsKey(req.getSession().getId())) {
            chain.doFilter(request, response);
        } else {
            new HttpServletResponseWrapper((HttpServletResponse) response).sendRedirect(req.getContextPath());
        }
    }

    @Override
    public void destroy() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getELContext();
        SessionBean.getInstance().getSesionAbierta().remove(req.getSession().getId());
    }
}

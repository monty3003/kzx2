/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.servlets.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final static Logger LOGGER = Logger.getLogger(SecurityFilter.class.getName());

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
        if (!req.getSession().isNew() && SessionBean.getInstance().getSesionAbierta().containsKey(req.getSession().getId())) {
            chain.doFilter(request, response);
        } else {
            LOGGER.log(Level.INFO, "El HOST REMOTO ES {0}", request.getRemoteHost());
            Enumeration<String> names = req.getHeaderNames();
            do {
                String name = names.nextElement();
                LOGGER.log(Level.INFO, "El Valor de name es {0}", name);
                LOGGER.log(Level.INFO, "El valor del atributo es {0}", req.getHeader(name));
            } while (names.hasMoreElements());
//                LOGGER.log(Level.INFO, "El User-Agent dice :{0}", userAgent);
            new HttpServletResponseWrapper((HttpServletResponse) response).sendRedirect(req.getContextPath());
        }
    }

    @Override
    public void destroy() {
    }
}

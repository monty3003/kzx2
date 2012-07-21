/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import py.com.bej.orm.entities.Pago;
import py.com.bej.orm.session.PagoFacade;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@RequestScoped
public class PagoConverter implements Converter {

    /** Creates a new instance of PagoConverter */
    public PagoConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return new PagoFacade().find(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Pago p = (Pago) value;
        return p.toString();
    }
}

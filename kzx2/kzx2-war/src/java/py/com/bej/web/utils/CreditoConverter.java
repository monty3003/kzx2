/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.session.CreditoFacade;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@RequestScoped
public class CreditoConverter implements Converter {

    @EJB
    private CreditoFacade creditoFacade;

    /** Creates a new instance of CreditoConverter */
    public CreditoConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return creditoFacade.find(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Credito) value).getId());
    }
}

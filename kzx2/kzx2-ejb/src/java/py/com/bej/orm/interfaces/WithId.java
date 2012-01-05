/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.interfaces;

import java.util.Date;

/**
 *
 * @author Diego_M
 */
public interface WithId<T> {

    public abstract void setId(T id);

    public abstract T getId();

    public abstract void setActivo(Character activo);

    public abstract Character getActivo();

    public abstract void setUltimaModificacion(Date ultimaModificacion);

    public abstract Date getUltimaModificacion();

    public abstract String getlabel();
}

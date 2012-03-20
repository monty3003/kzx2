/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.bej.base.prod.entity.Vmventamotos;

/**
 *
 * @author Diego_M
 */
public class ReflexUtils {

    public static Vmventamotos transferirATransaccion(Object[] ventaProduccion) {
        Vmventamotos res = new Vmventamotos();
        Field[] campo = Vmventamotos.class.getDeclaredFields();
        Field c = null;
        try {
            for (int i = 0; i < campo.length; i++) {
                c = campo[i];

                c.set(res, ventaProduccion[i]);

            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflexUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflexUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}

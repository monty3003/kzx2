/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import py.com.bej.orm.interfaces.WithId;

/**
 *
 * @author Diego_M
 */
public class JsfUtils {

    public static List<SelectItem> getSelectItems(List<? extends WithId> entities, boolean nuevo) {
        List<SelectItem> items = null;
        items = new ArrayList<SelectItem>();
        if (entities == null || entities.isEmpty()) {
            items.add(new SelectItem(-1, "SELECCIONAR"));
        } else {
            int i = 0;
            if (nuevo) {
                Object o = null;
                o = (entities.isEmpty()) ? String.valueOf(i) : entities.get(0).getId();
                if (o instanceof String) {
                    items.add(new SelectItem("X", "SELECCIONAR"));
                } else {
                    items.add(new SelectItem(new Integer(-1), "SELECCIONAR"));
                    i++;
                }
            }
            for (WithId x : entities) {
                if (x.getActivo() != null && x.getActivo().equals('S')) {
                    items.add(new SelectItem(x.getId(), x.getlabel()));
                }
            }
        }
        return items;
    }

    public static SelectItem[] getSelectItemsVector(List<? extends WithId> entities, boolean nuevo) {
        SelectItem[] items = null;
        int i = 0;
        if (entities == null || entities.isEmpty()) {
            items = new SelectItem[1];
            items[0] = new SelectItem(-1, "SELECCIONAR");
        } else {
            if (nuevo) {
                items = new SelectItem[entities.size() + 1];
                Object o = null;
                o = (entities.isEmpty()) ? String.valueOf(i) : entities.get(i).getId();
                if (o instanceof String) {
                    items[i] = new SelectItem("X", "SELECCIONAR");
                } else {
                    items[i] = new SelectItem(-1, "SELECCIONAR");
                }
                do {
                    items[i + 1] = new SelectItem(entities.get(i).getId(), entities.get(i).getlabel());
                    i++;
                } while (i < entities.size());
            } else {
                items = new SelectItem[entities.size()];
                do {
                    items[i] = new SelectItem(entities.get(i).getId(), entities.get(i).getlabel());
                    i++;
                } while (i < entities.size());
            }
        }
        return items;
    }
}

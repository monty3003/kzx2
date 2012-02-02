/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.dto;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego_M
 */
public abstract class ReflectDtoEntity<E, T extends SimpleDto> {

    private E entity;
    private T dto;

    public abstract void setEntity(Class<E> entity);

    public abstract void setDto(Class<T> dto);

    public E transferirDatos() {
        Field[] campoEntity = entity.getClass().getDeclaredFields();
        Field[] campoDto = dto.getClass().getDeclaredFields();
        for (Field fe : campoEntity) {
            for (Field fd : campoDto) {
                if (fd.getName().equalsIgnoreCase(fe.getName())) {
                    try {
                        fe.setAccessible(true);
                        fe.set(campoEntity, fd.get(fe));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ReflectDtoEntity.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ReflectDtoEntity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return entity;
    }
}

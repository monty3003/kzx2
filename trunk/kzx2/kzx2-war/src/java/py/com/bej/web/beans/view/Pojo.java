/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author diego
 */
public class Pojo {

    private Integer id;
    private String modelo;
    private String chasis;
    private String motor;
    private String precio;
    private String ubicacion;
    private Integer venta;
    private NumberFormat formatNumero = NumberFormat.getNumberInstance(Locale.US);

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the chasis
     */
    public String getChasis() {
        if (chasis != null) {
            return chasis.toUpperCase();
        } else {
            return chasis;
        }
    }

    /**
     * @param chasis the chasis to set
     */
    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    /**
     * @return the precio
     */
    public String getPrecio() {
        Number n = null;
        try {
            n = new BigDecimal(precio.trim());
            precio = formatNumero.format(n.doubleValue());
        } finally {
            return precio;
        }
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the venta
     */
    public Integer getVenta() {
        return venta;
    }

    /**
     * @param venta the venta to set
     */
    public void setVenta(Integer venta) {
        this.venta = venta;
    }

    /**
     * @return the motor
     */
    public String getMotor() {
        if (motor != null) {
            return motor.toUpperCase();
        } else {
            return motor;
        }
    }

    /**
     * @param motor the motor to set
     */
    public void setMotor(String motor) {
        this.motor = motor;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.base.prod.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Diego_M
 */
@Embeddable
public class ClientesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "Cedula_Ruc", nullable = false, length = 14)
    private String cedulaRuc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CodigoCliente", nullable = false)
    private int codigoCliente;

    public ClientesPK() {
    }

    public ClientesPK(String cedulaRuc, int codigoCliente) {
        this.cedulaRuc = cedulaRuc;
        this.codigoCliente = codigoCliente;
    }

    public String getCedulaRuc() {
        return cedulaRuc;
    }

    public void setCedulaRuc(String cedulaRuc) {
        this.cedulaRuc = cedulaRuc;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedulaRuc != null ? cedulaRuc.hashCode() : 0);
        hash += (int) codigoCliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientesPK)) {
            return false;
        }
        ClientesPK other = (ClientesPK) object;
        if ((this.cedulaRuc == null && other.cedulaRuc != null) || (this.cedulaRuc != null && !this.cedulaRuc.equals(other.cedulaRuc))) {
            return false;
        }
        if (this.codigoCliente != other.codigoCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.bej.base.prod.entity.ClientesPK[ cedulaRuc=" + cedulaRuc + ", codigoCliente=" + codigoCliente + " ]";
    }
    
}

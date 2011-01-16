/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.PersonaFacade;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class MotoBean {

    @EJB
    private MotoFacade facade;
    private Moto c;
    private List<Moto> lista;
    private Integer desde;
    private Integer max;
    private Integer total;
    private String nav = "listamotos";
    private String id;
    private Boolean valido;
    private List<Categoria> listaCategorias;
    private List<SelectItem> listaCategoria;
    private List<Persona> listaPersonas;
    private List<SelectItem> listaPersona;
    //Moto
    private String codigo;
    private String codigoFabrica;
    private String marca;
    private String modelo;
    private String color;
    private Integer fabricante;

    /** Creates a new instance of MotoBean */
    public MotoBean() {
    }

    private void deEntity() {
        this.setCodigo(c.getCodigo());
        this.setCodigoFabrica(c.getCodigoFabrica());
        this.setMarca(c.getMarca());
        this.setModelo(c.getModelo());
        this.setColor(c.getColor());
        this.setFabricante(c.getFabricante());
    }

    private void deCampos() {
        setC(new Moto());
        if (this.id != null && !this.id.trim().equals("")) {
            this.getC().setCodigo(getCodigo());
        } else {
            this.getC().setCodigo(null);
        }
        if (this.getCodigoFabrica() != null && !this.getCodigoFabrica().trim().equals("")) {
            this.getC().setCodigoFabrica(getCodigoFabrica());
        } else {
            this.getC().setCodigoFabrica(null);
        }
        if (this.getMarca() != null && !this.getMarca().trim().equals("")) {
            this.c.setMarca(marca);
        } else {
            this.c.setMarca(null);
        }
        if (this.getModelo() != null && !this.getModelo().trim().equals("")) {
            this.c.setModelo(modelo);
        } else {
            this.c.setModelo(null);
        }
        if (this.getColor() != null && !this.getColor().trim().equals("")) {
            this.c.setColor(color);
        } else {
            this.c.setColor(null);
        }
    }

    private void limpiarCampos() {
        this.setCodigo(null);
        this.setCodigoFabrica(null);
        this.setMarca(null);
        this.setModelo(null);
        this.setColor(null);
        this.setFabricante(null);
    }

    public String listar() {
        limpiarCampos();
        deCampos();
        this.setDesde(new Integer(0));
        this.setMax(new Integer(10));
        this.setValido((Boolean) true);
        this.filtrar();
        if (this.getLista().isEmpty()) {
            setErrorMessage(null, facade.r0);
        }
        return nav;
    }

    public List<Moto> filtrar() {
        deCampos();
        setLista(new ArrayList<Moto>());
        int[] range = {this.getDesde(), this.getMax()};
        setLista(facade.findRange(range, getC()));
        return getLista();
    }

    public String buscar() {
        setDesde((Integer) 0);
        setMax((Integer) 10);
        this.filtrar();
        if (this.getLista().isEmpty()) {
            setErrorMessage(null, facade.c0);
        }
        return nav;
    }

    private void obtenerListas() {
        listaCategorias = new CategoriaFacade().findBetween(50, 60);
        if (!listaCategorias.isEmpty()) {
            Iterator<Categoria> it = listaCategorias.iterator();
            do {
                Categoria x = it.next();
                listaCategoria.add(new SelectItem(x.getId(), x.getDescripcion()));
            } while (it.hasNext());

        }
        listaPersonas = new PersonaFacade().findByPersona(20);
        if (!listaPersonas.isEmpty()) {
            Iterator<Persona> it = listaPersonas.iterator();
            do {
                Persona x = it.next();
                listaPersona.add(new SelectItem(x.getPersonaPK().getId(), x.getNombre()));
            } while (it.hasNext());

        }
    }

    public String nuevo() {
        setC(new Moto());
        listaCategoria = new ArrayList<SelectItem>();
        listaCategoria.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaPersona = new ArrayList<SelectItem>();
        listaPersona.add(new SelectItem(-1, "-SELECCIONAR-"));
        obtenerListas();
        return "crearmoto";
    }

    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.setCodigo((String) request.getParameter("radio"));
        if (this.getCodigo() != null) {
            try {
                this.setC(facade.find(this.getCodigo()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            deEntity();
            listaCategoria = new ArrayList<SelectItem>();
            listaPersona = new ArrayList<SelectItem>();
            obtenerListas();
            return "modificarmoto";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    public String guardarNuevo() {
        boolean validado = validarNuevo();
        if (validado) {
            boolean exito = facade.create(getC());
            if (exito) {
                setInfoMessage(null, facade.ex1);
                return this.listar();
            } else {
                FacesContext.getCurrentInstance().addMessage("frm:id", new FacesMessage("Id ya existe"));
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean validarNuevo() {
        if (this.c.getCodigo().trim().equals("")) {
            setErrorMessage("frm:codigo", "Ingrese un valor");
            return false;
        }
        boolean res = true;
        if (this.c.getCodigoFabrica().trim().equals("")) {
            setErrorMessage("frm:codigoFabrica", "Ingrese un valor");
            res = false;
        }
        if (this.c.getMarca().trim().equals("")) {
            setErrorMessage("frm:marca", "Ingrese un valor");
            res = false;
        }
        if (this.c.getModelo().trim().equals("")) {
            setErrorMessage("frm:modelo", "Ingrese un valor");
            res = false;
        }
        if (this.c.getColor().trim().equals("")) {
            setErrorMessage("frm:color", "Ingrese un valor");
            res = false;
        }
        if (this.c.getFabricante() == -1) {
            setErrorMessage("frm:fabricante", "Seleccione un valor");
            res = false;
        }
        if (this.c.getCategoria() == -1) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        return res;
    }

    public String guardarModificar() {
        boolean validado = validarNuevo();
        if (validado) {
            facade.guardar(getC());
            setInfoMessage(null, facade.ex2);
            return this.listar();
        } else {
            return null;
        }
    }

    public String cancelar() {
        return this.listar();
    }

    public String todos() {
        limpiarCampos();
        facade.setCol(null);
        deCampos();
        this.setValido((Boolean) true);
        setDesde((Integer) 0);
        setMax((Integer) 10);
        this.filtrar();
        return nav;
    }

    public String anterior() {
        setDesde((Integer) (getDesde() - getMax()));
        int[] range = {getDesde(), getMax()};
        this.setLista(getFacade().anterior(range, getC()));
        return nav;
    }

    public String siguiente() {
        setDesde((Integer) (getDesde() + getMax()));
        int[] range = {getDesde(), getMax()};
        this.setLista(getFacade().siguiente(range, getC()));
        return nav;
    }

    public Integer getUltimoItem() {
        deCampos();
        MotoFacade.c = getC();
        int[] range = {getDesde(), getMax()};
        return getFacade().getUltimoItem(range);
    }

    /**
     * @return the lista
     */
    public List<Moto> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<Moto> lista) {
        this.lista = lista;
    }

    /**
     * @return the desde
     */
    public Integer getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    /**
     * @return the max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        this.total = getFacade().count();
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the facade
     */
    public MotoFacade getFacade() {
        return facade;
    }

    /**
     * @return the c
     */
    public Moto getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(Moto c) {
        this.c = c;
    }

    protected void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    protected void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

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
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the fabricante
     */
    public Integer getFabricante() {
        return fabricante;
    }

    /**
     * @param fabricante the fabricante to set
     */
    public void setFabricante(Integer fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * @return the valido
     */
    public Boolean getValido() {
        return valido;
    }

    /**
     * @param valido the valido to set
     */
    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    /**
     * @return the listaPersona
     */
    public List<SelectItem> getListaPersona() {
        return listaPersona;
    }

    /**
     * @param listaPersona the listaPersona to set
     */
    public void setListaPersona(List<SelectItem> listaPersona) {
        this.listaPersona = listaPersona;
    }

    /**
     * @return the listaCategoria
     */
    public List<SelectItem> getListaCategoria() {
        return listaCategoria;
    }

    /**
     * @param listaCategoria the listaCategoria to set
     */
    public void setListaCategoria(List<SelectItem> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    /**
     * @return the codigoFabrica
     */
    public String getCodigoFabrica() {
        return codigoFabrica;
    }

    /**
     * @param codigoFabrica the codigoFabrica to set
     */
    public void setCodigoFabrica(String codigoFabrica) {
        this.codigoFabrica = codigoFabrica;
    }
}

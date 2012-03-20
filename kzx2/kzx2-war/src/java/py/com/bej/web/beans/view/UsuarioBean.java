/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Rol;
import py.com.bej.orm.entities.Usuario;
import py.com.bej.orm.session.RolFacade;
import py.com.bej.orm.session.UsuarioFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class UsuarioBean extends AbstractPageBean<Usuario> {

    @EJB
    private RolFacade rolFacade;
    @EJB
    private UsuarioFacade facade;
    private Usuario usuario;
    //Listas
    List<SelectItem> listaRoles;
//Campos de busqueda
    private Integer idFiltro;
    private String nombreFiltro;
    private String userNameFiltro;
    private String documentoFiltro;
    private String rolFiltro;
//Modificar
    private Boolean activo;

    /** Creates a new instance of UsuarioBean */
    public UsuarioBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));        
        this.idFiltro = null;
        this.nombreFiltro = null;
        this.rolFiltro = null;
        this.documentoFiltro = null;
        this.userNameFiltro = null;
    }

    /**
     * @return the facade
     */
    public UsuarioFacade getFacade() {
        if (this.facade == null) {
            this.facade = new UsuarioFacade();
        }
        return facade;
    }

    /**
     * @return the facade
     */
    public RolFacade getRolFacade() {
        if (this.rolFacade == null) {
            this.rolFacade = new RolFacade();
        }
        return rolFacade;
    }

    @Override
    public String listar() {
        limpiarCampos();
        setNav("usuario");
        if (facade.getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return getNav();
    }

    @Override
    List filtrar() {
        Rol r = new Rol();
        if (rolFiltro != null && !rolFiltro.equals("-1")) {
            r = new Rol(Integer.valueOf(rolFiltro));
        }
        getFacade().setEntity(new Usuario(idFiltro, documentoFiltro, nombreFiltro, userNameFiltro, r, null, null, null, null, null));
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaRoles = JsfUtils.getSelectItems(getRolFacade().findAll(), !getModificar());
    }

    @Override
    public String buscar() {
        if (nombreFiltro != null && nombreFiltro.trim().equals("")) {
            nombreFiltro = null;
        }
        if (rolFiltro != null && rolFiltro.trim().equals("-1")) {
            this.rolFiltro = null;
        }
        if (documentoFiltro != null && documentoFiltro.trim().equals("")) {
            this.documentoFiltro = null;
        }
        if (userNameFiltro != null && userNameFiltro.trim().equals("")) {
            this.userNameFiltro = null;
        }
        getFacade().setEntity(new Usuario(idFiltro, documentoFiltro, nombreFiltro, userNameFiltro, null, null, null, null, null, null));
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        obtenerListas();
        return "usuario";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        idFiltro = Integer.valueOf(request.getParameter("radio"));
        if (idFiltro != null) {
            try {
                usuario = getFacade().find(idFiltro);
                setActivo(usuario.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(UbicacionBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            return "moto";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    boolean validar() {

        return false;
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            if (getModificar()) {
                getFacade().getEntity().setActivo(getActivo() ? 'S' : 'N');
                getFacade().guardar();
                setInfoMessage(null, facade.ex2);
            } else {
                getFacade().create();
                setInfoMessage(null, getFacade().ex1);
            }
        } else {
            return null;
        }
        return this.listar();
    }

    @Override
    public String cancelar() {
        usuario = new Usuario();
        getFacade().setEntity(usuario);
        return this.listar();
    }

    @Override
    public String anterior() {
        setLista(getFacade().anterior());
        return getNav();
    }

    @Override
    public String siguiente() {
        setLista(getFacade().siguiente());
        return getNav();
    }

    @Override
    public String todos() {
        limpiarCampos();
        usuario = new Usuario();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
    }

    public String getDocumentoFiltro() {
        return documentoFiltro;
    }

    public void setDocumentoFiltro(String documentoFiltro) {
        this.documentoFiltro = documentoFiltro;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public List<SelectItem> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<SelectItem> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public String getRolFiltro() {
        return rolFiltro;
    }

    public void setRolFiltro(String rolFiltro) {
        this.rolFiltro = rolFiltro;
    }

    public String getUserNameFiltro() {
        return userNameFiltro;
    }

    public void setUserNameFiltro(String userNameFiltro) {
        this.userNameFiltro = userNameFiltro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

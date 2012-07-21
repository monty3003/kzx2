/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.servlets.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.orm.entities.Password;
import py.com.bej.orm.entities.Usuario;
import py.com.bej.orm.session.PasswordFacade;
import py.com.bej.orm.session.UsuarioFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private PasswordFacade passwordFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    private String userName;
    private String password;
    private Date loginDesde;
    private String ubicacion;
    private Usuario usuario;
    private String passwordNuevo;
    private String passwordRepetido;
    private Password passwordActivo;
    private Boolean necesitaCambiarPassword;
    private TimeZone timeZone = TimeZone.getDefault();
    private Locale locale = Locale.getDefault();
    private String numberPattern = ConfiguracionEnum.NUMBER_PATTERN.getSymbol();
    private String monedaPattern = ConfiguracionEnum.MONEDA_PATTERN.getSymbol();
    private String fechaHoraPattern = ConfiguracionEnum.DATETIME_PATTERN.getSymbol();
    private String fechaCortaPattern = ConfiguracionEnum.DATE_PATTERN_CORTO.getSymbol();
    //Importacion
    private Boolean proveedoresImportados;
    private Boolean clientesImportados;
    private Boolean motosImportadas;
    private Boolean comprasImportadas;
    private Boolean stockImportado;
    private Boolean ventasImportadas;
    private Boolean pagosImportados;
    private Boolean consolidarCreditos;

    public UsuarioFacade getUsuarioFacade() {
        if (this.usuarioFacade == null) {
            this.usuarioFacade = new UsuarioFacade();
        }
        return this.usuarioFacade;
    }

    public PasswordFacade getPasswordFacade() {
        if (this.passwordFacade == null) {
            this.passwordFacade = new PasswordFacade();
        }
        return this.passwordFacade;
    }

    public Integer usuariosConectados() {
        return SessionBean.getInstance().getSesionAbierta().size();
    }

    public String login() {
        boolean loginOk = false;
        necesitaCambiarPassword = Boolean.FALSE;
        if (userName != null && password != null) {
            List<Password> passwordsDelUsuario = new ArrayList<Password>();
            try {
                usuario = getUsuarioFacade().findByUserName(userName.trim());
                if (usuario != null) {
                    //Buscar password activo y comparar
                    passwordsDelUsuario = getPasswordFacade().findByUsuario(usuario.getId());
                    String passCodificado = Crypto.encriptar(password);
                    boolean existePassword = false;
                    if (!passwordsDelUsuario.isEmpty()) {
                        for (Password p : passwordsDelUsuario) {
                            if (p.getPassword().equals(passCodificado)) {
                                if (p.getActivo().equals('N')) {
                                    setErrorMessage(null, "Este password ya está inactivo");
                                } else {
                                    passwordActivo = p;
                                    Date now = new Date();
                                    if (p.getVence().after(now)) {
                                        loginOk = true;
                                        existePassword = true;
                                        break;
                                    } else {
                                        necesitaCambiarPassword = Boolean.TRUE;
                                        setInfoMessage(null, "Su password ha vencido, deberá cambiarlo por otro");
                                        return null;
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (!existePassword) {
                        setErrorMessage(null, "Password Incorrecto");
                        return null;

                    }
                } else {
//                    if (userName.equals("admin")) {
//                        passwordFacade = new PasswordFacade();
//                        Calendar ahora = new GregorianCalendar();
//                        Calendar vence = new GregorianCalendar();
//                        vence.add(Calendar.DAY_OF_YEAR, 45);
//                        usuario = new Usuario(null, "3231339", "Diego Montiel Tejera", "admin", null,
//                                "webmaster@bej.com.py", new Date(), null, 'S', new Date());
//                        Rol r = new Rol(null, "Administrador", "Administrador del sistema", 0, 'S', new Date());
//                        usuario.setRol(r);
//                        //Password
//                        Password p = new Password(null, null, Crypto.encriptar(password), ahora.getTime(), vence.getTime(), 'S', ahora.getTime());
//                        usuario.setPasswords(Arrays.asList(p));
//                        //Guardar
//                        getUsuarioFacade().setEntity(usuario);
//                        getUsuarioFacade().create();
//                    } else {
                    setErrorMessage(null, "Usuario no existe");
                    return null;
//                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (SessionBean.getInstance().getSesionAbierta().containsValue(userName)) {
                setErrorMessage(null, "El usuario " + userName + " ya se encuentra autenticado");
                return null;
            }
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            SessionBean.getInstance().registrarSesion(req.getSession().getId(), userName);
            setUbicacion("Inicio");
            loginDesde = new Date();

            clientesImportados = Boolean.FALSE;
            proveedoresImportados = Boolean.FALSE;

            return "./secure/app/main.bej";
        } else {
            return null;
        }
    }

    public String cambiarPasswordDesdeMain() {

        return "./../../cambioPassword.bej";
    }

    private int validarCambioPassword() {
        int res = 0;
        if ((password == null || password.trim().equals(""))
                || (passwordNuevo == null || passwordNuevo.trim().equals(""))
                || (passwordRepetido == null || passwordRepetido.trim().equals(""))) {
            return 2;
        } else {
            if (!passwordActivo.getPassword().equals(Crypto.encriptar(password))) {
                res = 1;
            } else if (!passwordNuevo.equals(passwordRepetido)) {
                res = 3;
            }
            //Buscar password actual y comparar
            String passCodificado = Crypto.encriptar(passwordNuevo);
            List<Password> passwordsDelUsuario = new ArrayList<Password>();
            passwordsDelUsuario = getPasswordFacade().findByUsuario(usuario.getId());
            if (!passwordsDelUsuario.isEmpty()) {
                for (Password p : passwordsDelUsuario) {
                    if (passCodificado.equals(p.getPassword())) {
                        res = 4;
                        break;
                    }
                }
            }
        }
        return res;
    }

    public String cambiarPassword() {
        Calendar ahora = GregorianCalendar.getInstance();
        Calendar vence = GregorianCalendar.getInstance();
        vence.add(Calendar.DAY_OF_YEAR, 45);
        boolean validado = false;
        switch (validarCambioPassword()) {
            case 0:
                validado = true;
                break;
            case 1:
                setErrorMessage(null, "El password actual es incorrecto");
                break;
            case 2:
                setErrorMessage(null, "Ingrese los valores requeridos");
                break;
            case 3:
                setErrorMessage(null, "Los passwords no coinciden");
                break;
            case 4:
                setErrorMessage(null, "El password nuevo ya fue utilizado.");
                break;
        }
        if (validado) {
            Password passwordN = new Password(null, usuario, Crypto.encriptar(passwordNuevo), ahora.getTime(), vence.getTime(), 'S', ahora.getTime());
            List<Password> passwordsAnteriores;
            passwordsAnteriores = getPasswordFacade().findByUsuario(usuario.getId());
            if (!passwordsAnteriores.isEmpty()) {
                for (Password p : passwordsAnteriores) {
                    if (p.getActivo().equals('S')) {
                        p.setActivo('N');
                        getPasswordFacade().setEntity(p);
                        getPasswordFacade().guardar();
                        break;
                    }
                }
                getPasswordFacade().setEntity(passwordN);
                getPasswordFacade().create();
                necesitaCambiarPassword = Boolean.FALSE;
                //Cerrar sesion si ya esta logueado
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                SessionBean.getInstance().getSesionAbierta().remove(request.getSession().getId());
                request.getSession().invalidate();
                setInfoMessage(null, "Su password se ha cambiado, vuelva a ingresar.");
                //            return "./secure/app/main.bej";
            } else {
                return null;
            }
        } else {
            return null;
        }
        return "login";
    }

    public String logout() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        SessionBean.getInstance().getSesionAbierta().remove(request.getSession().getId());
        request.getSession().invalidate();
        return "logout";
    }

    void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    public Boolean procesarPassword() {
        Boolean res = Boolean.FALSE;
        if (passwordRepetido.equals(passwordNuevo)) {
            setErrorMessage(null, "Passwords no coinciden");
            return res;
        } else {
            //Buscar password actual y comparar
            String passCodificado = Crypto.encriptar(passwordNuevo);
            if (passCodificado.equals(passwordActivo.getPassword())) {
                setErrorMessage(null, "Ingrese un password distinto al actual");
                return res;
            } else {
                for (Password p : usuario.getPasswords()) {
                    if (p.getPassword().equals(passCodificado)) {
                        if (p.getActivo().equals('N')) {
                            setErrorMessage(null, "Este password ya utilizó. Ingrese uno distinto");
                            return res;
                        }
                    }
                }
            }
        }
        return res;
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
     * @return the loginDesde
     */
    public Date getLoginDesde() {
        return loginDesde;
    }

    public static LoginBean getInstance() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return (LoginBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "loginBean");
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordNuevo() {
        return passwordNuevo;
    }

    public void setPasswordNuevo(String passwordNuevo) {
        this.passwordNuevo = passwordNuevo;
    }

    public String getPasswordRepetido() {
        return passwordRepetido;
    }

    public void setPasswordRepetido(String passwordRepetido) {
        this.passwordRepetido = passwordRepetido;
    }

    public Boolean getNecesitaCambiarPassword() {
        return necesitaCambiarPassword;
    }

    public void setNecesitaCambiarPassword(Boolean necesitaCambiarPassword) {
        this.necesitaCambiarPassword = necesitaCambiarPassword;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getFechaCortaPattern() {
        return fechaCortaPattern;
    }

    public String getFechaHoraPattern() {
        return fechaHoraPattern;
    }

    public String getMonedaPattern() {
        return monedaPattern;
    }

    public String getNumberPattern() {
        return numberPattern;
    }

    public Boolean getClientesImportados() {
        return clientesImportados;
    }

    public void setClientesImportados(Boolean clientesImportados) {
        this.clientesImportados = clientesImportados;
    }

    public Boolean getComprasImportadas() {
        return comprasImportadas;
    }

    public void setComprasImportadas(Boolean comprasImportadas) {
        this.comprasImportadas = comprasImportadas;
    }

    public Boolean getConsolidarCreditos() {
        return consolidarCreditos;
    }

    public void setConsolidarCreditos(Boolean consolidarCreditos) {
        this.consolidarCreditos = consolidarCreditos;
    }

    public Boolean getMotosImportadas() {
        return motosImportadas;
    }

    public void setMotosImportadas(Boolean motosImportadas) {
        this.motosImportadas = motosImportadas;
    }

    public Boolean getPagosImportados() {
        return pagosImportados;
    }

    public void setPagosImportados(Boolean pagosImportados) {
        this.pagosImportados = pagosImportados;
    }

    public Boolean getProveedoresImportados() {
        return proveedoresImportados;
    }

    public void setProveedoresImportados(Boolean proveedoresImportados) {
        this.proveedoresImportados = proveedoresImportados;
    }

    public Boolean getStockImportado() {
        return stockImportado;
    }

    public void setStockImportado(Boolean stockImportado) {
        this.stockImportado = stockImportado;
    }

    public Boolean getVentasImportadas() {
        return ventasImportadas;
    }

    public void setVentasImportadas(Boolean ventasImportadas) {
        this.ventasImportadas = ventasImportadas;
    }
}

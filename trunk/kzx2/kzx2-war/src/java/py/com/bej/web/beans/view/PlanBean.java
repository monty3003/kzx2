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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Plan;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.PlanFacade;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class PlanBean extends AbstractPageBean<Plan> {

    @EJB
    private PlanFacade facade;
    private Plan plan;
    private List<SelectItem> listaCategoria;
    //Variables de busqueda
    private Integer idFiltro;
    private Integer categoriaFiltro;
    private String nombreFiltro;
    private Character activoFiltro;
    //Variables de calculo
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor")
    @Max(value = 100, message = "Valor fuera de rango")
    private Double porcentajeEntregaMinima;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo")
    @Max(value = 100, message = "Valor fuera de rango")
    private Double tasaAnual;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo")
    @Max(value = 100, message = "Valor fuera de rango")
    private Double tasaEfectiva;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Max(value = 4, message = "Valor fuera de rango")
    private Double tasaMoratoria;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Max(value = 4, message = "Valor fuera de rango")
    private Double tasaPunitoria;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 0, message = "Ingrese un valor positivo")
    @Max(value = 100, message = "Valor fuera de rango")
    private Double porcentajeDescuento;

    public PlanFacade getFacade() {
        if (facade == null) {
            facade = new PlanFacade();
        }
        return facade;
    }

    /** Creates a new instance of PlanBean */
    public PlanBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        setDesde(Long.parseLong(ConfiguracionEnum.PAG_DESDE.getSymbol()));
        setMax(Long.parseLong(ConfiguracionEnum.PAG_MAX.getSymbol()));
        plan = null;
        porcentajeEntregaMinima = null;
        tasaAnual = null;
        tasaEfectiva = null;
        tasaMoratoria = null;
        porcentajeDescuento = null;
        idFiltro = null;
        categoriaFiltro = null;
        nombreFiltro = null;
        activoFiltro = null;
    }

    @Override
    public String listar() {
        setModificar(Boolean.FALSE);
        setAgregar(Boolean.FALSE);
        LoginBean.getInstance().setUbicacion("Planes");
        limpiarCampos();
        setNav("plan");
        if (getFacade().getOrden() == null) {
            getFacade().setOrden(new Orden("id", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return getNav();
    }

    @Override
    List<Plan> filtrar() {
        getFacade().setEntity(new Plan(idFiltro,
                (categoriaFiltro != null && categoriaFiltro > 0) ? new Categoria(categoriaFiltro) : new Categoria(),
                nombreFiltro,
                (activoFiltro != null && !activoFiltro.equals('X')) ? activoFiltro : null));
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategoria = JsfUtils.getSelectItems(
                new CategoriaFacade().findBetween(CategoriaEnum.PLAN_DESDE.getSymbol(), CategoriaEnum.PLAN_HASTA.getSymbol()), !getModificar());
    }

    @Override
    public String buscar() {
        getFacade().setEntity(new Plan(idFiltro,
                (categoriaFiltro != null && categoriaFiltro > 0) ? new Categoria(categoriaFiltro) : new Categoria(),
                nombreFiltro,
                (activoFiltro != null && !activoFiltro.equals('X')) ? activoFiltro : null));
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
    }

    @Override
    public String nuevo() {
        LoginBean.getInstance().setUbicacion("Nuevo Plan");
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        obtenerListas();
        plan = new Plan();
        plan.setCategoria(new Categoria(CategoriaEnum.S_PER.getSymbol()));
        return "plan";
    }

    @Override
    public String modificar() {
        LoginBean.getInstance().setUbicacion("Modificar Plan");
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        idFiltro = Integer.valueOf(request.getParameter("radio"));
        if (idFiltro != null) {
            try {
                plan = getFacade().find(idFiltro);
                setActivo(plan.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(PlanBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            //Variables de calculo
            porcentajeEntregaMinima = Double.valueOf(plan.getPorcentajeMontoEntrega() * 100);
            porcentajeDescuento = Double.valueOf(plan.getPorcentajeDescuento() * 100);
            tasaAnual = Double.valueOf(plan.getTan() * 100);
            tasaEfectiva = Double.valueOf(plan.getTae() * 100);
            tasaMoratoria = Double.valueOf(plan.getTasaInteresMoratorio() * 100);
            tasaPunitoria = Double.valueOf(plan.getTasaInteresPunitorio() * 100);
            return "plan";
        } else {
            setErrorMessage(null, getFacade().sel);
            return null;
        }
    }

    @Override
    boolean validar() {
        plan.setPorcentajeMontoEntrega(new Float((porcentajeEntregaMinima / 100)));
        plan.setTan(new Float((tasaAnual / 100)));
        plan.setTae(new Float((tasaEfectiva / 100)));
        plan.setTasaInteresMoratorio(new Float((tasaMoratoria / 100)));
        plan.setTasaInteresPunitorio(new Float((tasaPunitoria / 100)));
        plan.setPorcentajeDescuento(new Float((porcentajeDescuento / 100)));
        return true;
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            getFacade().setEntity(plan);
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
        plan = new Plan();
        getFacade().setEntity(new Plan());
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
        LoginBean.getInstance().setUbicacion("Planes");
        setModificar(Boolean.FALSE);
        setAgregar(Boolean.FALSE);
        limpiarCampos();
        plan = new Plan();
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Long[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("id", false));
        this.filtrar();
        return getNav();
    }

    public Character getActivoFiltro() {
        return activoFiltro;
    }

    public void setActivoFiltro(Character activoFiltro) {
        this.activoFiltro = activoFiltro;
    }

    public Integer getCategoriaFiltro() {
        return categoriaFiltro;
    }

    public void setCategoriaFiltro(Integer categoriaFiltro) {
        this.categoriaFiltro = categoriaFiltro;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<SelectItem> getListaCategoria() {
        return listaCategoria;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Double getPorcentajeEntregaMinima() {
        return porcentajeEntregaMinima;
    }

    public void setPorcentajeEntregaMinima(Double porcentajeEntregaMinima) {
        this.porcentajeEntregaMinima = porcentajeEntregaMinima;
    }

    public Double getTasaAnual() {
        return tasaAnual;
    }

    public void setTasaAnual(Double tasaAnual) {
        this.tasaAnual = tasaAnual;
    }

    public Double getTasaEfectiva() {
        return tasaEfectiva;
    }

    public void setTasaEfectiva(Double tasaEfectiva) {
        this.tasaEfectiva = tasaEfectiva;
    }

    public Double getTasaMoratoria() {
        return tasaMoratoria;
    }

    public void setTasaMoratoria(Double tasaMoratoria) {
        this.tasaMoratoria = tasaMoratoria;
    }

    public Double getTasaPunitoria() {
        return tasaPunitoria;
    }

    public void setTasaPunitoria(Double tasaPunitoria) {
        this.tasaPunitoria = tasaPunitoria;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Plan;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PlanFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.JsfUtils;
import py.com.bej.web.utils.PlanWrapper;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@SessionScoped
public class AsignarPlanBean implements Serializable {

    @EJB
    private PlanFacade planFacade;
    @EJB
    private MotostockFacade motostockFacade;
    @EJB
    private MotoFacade motoFacade;
    //Listas
    private List<SelectItem> listaMoto;
    private List<SelectItem> listaStock;
    private List<SelectItem> listaPlan;
    private List<PlanWrapper> listaDePlanes;
    List<BigDecimal> listaDeEntrega;
    //Objectos variables
    private Moto moto;
    private Motostock motostock;
    private Plan plan;
    private Integer planId;
    private Integer stockId;
    //Patterns
    private String numberPattern = ConfiguracionEnum.NUMBER_PATTERN.getSymbol();
    private String monedaPattern = ConfiguracionEnum.MONEDA_PATTERN.getSymbol();

    /** Creates a new instance of AsignarPlanBean */
    public AsignarPlanBean() {
    }

    public MotoFacade getMotoFacade() {
        if (motoFacade == null) {
            motoFacade = new MotoFacade();
        }
        return motoFacade;
    }

    public MotostockFacade getMotostockFacade() {
        if (motostockFacade == null) {
            motostockFacade = new MotostockFacade();
        }
        return motostockFacade;
    }

    public PlanFacade getPlanFacade() {
        if (planFacade == null) {
            planFacade = new PlanFacade();
        }
        return planFacade;
    }

    public String consultarPlan() {
        moto = new Moto();
        motostock = new Motostock();
        plan = new Plan();
        listaDeEntrega = null;
        listaDePlanes = null;
        LoginBean.getInstance().setUbicacion("Consultar Plan");
        obtenerListas();
        return "consultarPlan";
    }

    private void obtenerListas() {
        listaStock = new ArrayList<SelectItem>();
        listaStock.add(new SelectItem("-1", "-SELECCIONAR-"));
        listaPlan = JsfUtils.getSelectItems(getPlanFacade().findAll(), true);
        obtenerListaMoto();
    }

    public void asignarPlan() {
        //Buscar Plan
        plan = getPlanFacade().find(planId);
        //Buscar Stock
        motostock = getMotostockFacade().find(stockId);
        //Generar Entregas
        BigDecimal entregaMinX;
        BigDecimal entregaMaxX;
        Float porcentajeEntregaMinX;
        listaDePlanes = new ArrayList<PlanWrapper>();
        listaDeEntrega = new ArrayList<BigDecimal>();
        listaDeEntrega.add(null);
        entregaMaxX = plan.getMontoEntregaMaximo();
        porcentajeEntregaMinX = plan.getPorcentajeMontoEntrega();
        if (porcentajeEntregaMinX != null && porcentajeEntregaMinX > 0) {
            entregaMinX = new BigDecimal(motostock.getPrecioBase().doubleValue() * porcentajeEntregaMinX).setScale(-5, RoundingMode.HALF_UP);
            if (entregaMinX.min(plan.getMontoEntregaMinimo()).equals(entregaMinX)) {
                //Manda el monto Entrega Minimo
                entregaMinX = plan.getMontoEntregaMinimo();
            }
            BigDecimal aumento = entregaMaxX.subtract(entregaMinX).divide(BigDecimal.valueOf(12), RoundingMode.HALF_DOWN);
            BigDecimal cienMil = BigDecimal.valueOf(100000);
            BigDecimal doscientosMil = BigDecimal.valueOf(200000);
            BigDecimal quinientosMil = BigDecimal.valueOf(500000);
            BigDecimal faltaPara = entregaMaxX;
            listaDeEntrega.add(entregaMinX);
            for (int i = 0; i < 11; i++) {
                BigDecimal parteEntera = BigDecimal.valueOf(entregaMinX.intValue());
                if (cienMil.equals(quinientosMil.subtract(parteEntera).max(cienMil))) {
                    //es 400.000
                    entregaMinX = entregaMinX.add(cienMil);
                    faltaPara = faltaPara.subtract(cienMil);
                    if (faltaPara.equals(faltaPara.max(cienMil))) {
                        listaDeEntrega.add(entregaMinX);
                    } else {
                        listaDeEntrega.add(entregaMaxX);
                    }
                } else if (cienMil.equals(quinientosMil.multiply(BigDecimal.valueOf(2)).subtract(parteEntera).max(cienMil))) {
                    //es 900.000
                    entregaMinX = quinientosMil.multiply(BigDecimal.valueOf(2));
                    faltaPara = faltaPara.subtract(cienMil);
                    if (faltaPara.equals(faltaPara.max(cienMil))) {
                        listaDeEntrega.add(entregaMinX);
                    } else {
                        listaDeEntrega.add(entregaMaxX);
                    }

                } else if (cienMil.equals(quinientosMil.multiply(BigDecimal.valueOf(3)).subtract(parteEntera).max(cienMil))) {
                    //es 1.400.000
                    entregaMinX = quinientosMil.multiply(BigDecimal.valueOf(3));
                    faltaPara = faltaPara.subtract(cienMil);
                    if (faltaPara.equals(faltaPara.max(cienMil))) {
                        listaDeEntrega.add(entregaMinX);
                    } else {
                        listaDeEntrega.add(entregaMaxX);
                    }
                } else if (cienMil.equals(quinientosMil.multiply(BigDecimal.valueOf(4)).subtract(parteEntera).max(cienMil))) {
                    //es 1.900.000
                    entregaMinX = quinientosMil.multiply(BigDecimal.valueOf(4));
                    faltaPara = faltaPara.subtract(cienMil);
                    if (faltaPara.equals(faltaPara.max(cienMil))) {
                        listaDeEntrega.add(entregaMinX);
                    } else {
                        listaDeEntrega.add(entregaMaxX);
                    }
                } else if (cienMil.equals(quinientosMil.multiply(BigDecimal.valueOf(5)).subtract(parteEntera).max(cienMil))) {
                    //es 2.400.000
                    entregaMinX = quinientosMil.multiply(BigDecimal.valueOf(5));
                    faltaPara = faltaPara.subtract(cienMil);
                    if (faltaPara.equals(faltaPara.max(cienMil))) {
                        listaDeEntrega.add(entregaMinX);
                    } else {
                        listaDeEntrega.add(entregaMaxX);
                    }
                } else {
                    entregaMinX = entregaMinX.add(aumento);
                    faltaPara = faltaPara.subtract(aumento);
                    if (faltaPara.equals(faltaPara.max(aumento))) {
                        listaDeEntrega.add(entregaMinX);
                    } else {
                        listaDeEntrega.add(entregaMaxX);
                    }
                }
            }
        }
        BigDecimal precioContadoX = motostock.getPrecioContado();
        BigDecimal precioBaseX = motostock.getPrecioBase();
        BigDecimal cuotaX;
        BigDecimal interesMensual = BigDecimal.valueOf(plan.getTan() / 12).setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal interesAcumulado;
        List<BigDecimal> listaCuotas;
        PlanWrapper pwr;
        for (short x = plan.getFinanciacionMinima(); x <= plan.getFinanciacionMaxima(); x++) {
            listaCuotas = new ArrayList<BigDecimal>();
            interesAcumulado = interesMensual.multiply(BigDecimal.valueOf(x)).setScale(2, RoundingMode.HALF_DOWN);
            for (BigDecimal entrega : listaDeEntrega) {
                if (entrega != null) {
                    BigDecimal saldo = precioBaseX.subtract(entrega);
                    BigDecimal saldoConInteres = saldo.multiply(interesAcumulado.add(BigDecimal.ONE));
                    cuotaX = saldoConInteres.divide(BigDecimal.valueOf(x), RoundingMode.HALF_DOWN).setScale(plan.getIndiceRedondeo(), RoundingMode.HALF_DOWN);
                    listaCuotas.add(cuotaX);
                }
            }
            pwr = new PlanWrapper(x, listaCuotas);
            listaDePlanes.add(pwr);
        }
    }

    private void obtenerListaMoto() {
        listaMoto = new ArrayList<SelectItem>();
        listaMoto.add(new SelectItem("X", "-SELECCIONAR-"));
        List<Moto> listaMotos = new ArrayList<Moto>();
        listaMotos = getMotoFacade().findGrupoByModelo();
        if (!listaMotos.isEmpty()) {
            for (Moto m : listaMotos) {
                listaMoto.add(new SelectItem(m.getModelo(), m.getMarca() + " " + m.getModelo()));
            }
        }
    }

    public void obtenerListaStock() {
        listaStock = new ArrayList<SelectItem>();
        listaStock.add(new SelectItem(-1, "-TODOS-"));
        List<Motostock> listaMotostock = new ArrayList<Motostock>();
        try {
            listaMotostock = getMotostockFacade().findByModelo(moto.getModelo());
        } catch (Exception ex) {
            Logger.getLogger(AsignarPlanBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!listaMotostock.isEmpty()) {
            for (Motostock stock : listaMotostock) {
                listaStock.add(new SelectItem(stock.getId(), stock.getId() + " - "
                        + stock.getMoto().getMarca() + " "
                        + stock.getMoto().getModelo() + " " + stock.getMoto().getColor()));
            }
        }
    }

    public String asignar() {
        int totalMigrado = 0;
        try {
            if (stockId < 0) {
                List<Motostock> stockParaAsignar = null;
                //Asignar el plan por modelo
                stockParaAsignar = getMotostockFacade().findByModelo(moto.getModelo());
                if (!stockParaAsignar.isEmpty()) {
                    for (Motostock m : stockParaAsignar) {
                        m.setPlan(plan);
                        getMotostockFacade().setEntity(m);
                        getMotostockFacade().guardar();
                        totalMigrado++;
                    }
                }
            } else {
                Motostock m = getMotostockFacade().find(stockId);
                m.setPlan(plan);
                getMotostockFacade().setEntity(m);
                getMotostockFacade().guardar();
                totalMigrado++;
            }
        } catch (Exception ex) {
            Logger.getLogger(AsignarPlanBean.class.getName()).log(Level.SEVERE, null, ex);
            setErrorMessage(null, "Ocurrió un error al intentar asignar plan a las motos.");
            return "moto";
        }
        setInfoMessage(null, "Se asigno el plan " + plan.getlabel() + " a " + totalMigrado + " motos.");
        return "moto";
    }

    protected void setErrorMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    protected void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the listaMoto
     */
    public List<SelectItem> getListaMoto() {
        return listaMoto;
    }

    /**
     * @return the listaStock
     */
    public List<SelectItem> getListaStock() {
        return listaStock;
    }

    /**
     * @return the listaPlan
     */
    public List<SelectItem> getListaPlan() {
        return listaPlan;
    }

    /**
     * @return the listaDePlanes
     */
    public List<PlanWrapper> getListaDePlanes() {
        return listaDePlanes;
    }

    /**
     * @param listaDePlanes the listaDePlanes to set
     */
    public void setListaDePlanes(List<PlanWrapper> listaDePlanes) {
        this.listaDePlanes = listaDePlanes;
    }

    /**
     * @return the moto
     */
    public Moto getMoto() {
        return moto;
    }

    /**
     * @param moto the moto to set
     */
    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    /**
     * @return the motostock
     */
    public Motostock getMotostock() {
        return motostock;
    }

    /**
     * @param motostock the motostock to set
     */
    public void setMotostock(Motostock motostock) {
        this.motostock = motostock;
    }

    /**
     * @return the plan
     */
    public Plan getPlan() {
        return plan;
    }

    /**
     * @param plan the plan to set
     */
    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getMonedaPattern() {
        return monedaPattern;
    }

    public String getNumberPattern() {
        return numberPattern;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public List<BigDecimal> getListaDeEntrega() {
        return listaDeEntrega;
    }

    public void setListaDeEntrega(List<BigDecimal> listaDeEntrega) {
        this.listaDeEntrega = listaDeEntrega;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Plan;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PlanFacade;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.web.servlets.security.LoginBean;
import py.com.bej.web.utils.GeneradorReporte;
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
    @NotNull(message = "Seleccione un valor")
    @Min(value = 1, message = "Seleccione un valor")
    private Integer planId;
    @NotNull(message = "Seleccione un valor")
    @Min(value = 1, message = "Seleccione un valor")
    private Integer stockId;
    @NotNull(message = "Ingrese un valor")
    @Min(value = 1, message = "Ingrese un valor positivo")
    @Max(value = 25, message = "Valor fuera de rango")
    private Integer cantidadEntregas;
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
        stockId = null;
        planId = null;
        cantidadEntregas = null;
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
    
    public String asignarPlan() {
        //Validar
        if (moto.getModelo() == null || moto.getModelo().equals("X")) {
            setErrorMessage("frm:modelo", "Seleccione un valor");
            return null;
        }
        //Buscar Plan
        plan = getPlanFacade().find(planId);
        //Buscar Stock
        motostock = getMotostockFacade().find(stockId);
        //Generar Entregas
        BigDecimal entregaMinX;
        BigDecimal entregaMaxX;
        //Minimo de cuota
        BigDecimal montoMinimo = new BigDecimal(ConfiguracionEnum.MONTO_MINIMO_CUOTA.getSymbol());
        Float porcentajeEntregaMinX;
        listaDePlanes = new ArrayList<PlanWrapper>();
        listaDeEntrega = new ArrayList<BigDecimal>();
        listaDeEntrega.add(null);
        listaDeEntrega.add(BigDecimal.ZERO);
        entregaMaxX = plan.getMontoEntregaMaximo();
        porcentajeEntregaMinX = plan.getPorcentajeMontoEntrega();
        if (porcentajeEntregaMinX != null && porcentajeEntregaMinX > 0) {
            entregaMinX = new BigDecimal(motostock.getPrecioBase().doubleValue() * porcentajeEntregaMinX).setScale(-5, RoundingMode.HALF_UP);
            if (entregaMinX.min(plan.getMontoEntregaMinimo()).equals(entregaMinX)) {
                //Manda el monto Entrega Minimo
                entregaMinX = plan.getMontoEntregaMinimo();
            }
            BigDecimal aumento = entregaMaxX.subtract(entregaMinX).divide(BigDecimal.valueOf(cantidadEntregas), RoundingMode.HALF_DOWN);
            BigDecimal faltaPara = entregaMaxX;
            listaDeEntrega.add(entregaMinX);
            for (int i = 0; i < cantidadEntregas; i++) {
                entregaMinX = entregaMinX.add(aumento);
                faltaPara = faltaPara.subtract(aumento);
                if (faltaPara.subtract(aumento).compareTo(aumento) > 0 && entregaMinX.compareTo(entregaMaxX) < 0) {
                    listaDeEntrega.add(entregaMinX.setScale(-5, RoundingMode.HALF_DOWN));
                }
            }
            listaDeEntrega.add(entregaMaxX.setScale(-5, RoundingMode.HALF_DOWN));
        }
        BigDecimal precioBaseX = motostock.getPrecioBase();
        BigDecimal cuotaX;
        BigDecimal interesAcumulado;
        BigDecimal cuotaCapital;
        BigDecimal cuotaConInteres;
        List<BigDecimal> listaCuotas;
        PlanWrapper pwr;
        for (short x = plan.getFinanciacionMinima(); x <= plan.getFinanciacionMaxima(); x++) {
            listaCuotas = new ArrayList<BigDecimal>();
            interesAcumulado = BigDecimal.valueOf((plan.getTan() * x) / (x >= 12 ? 12 : 1)).setScale(4, RoundingMode.HALF_DOWN);
            for (BigDecimal entrega : listaDeEntrega) {
                if (entrega != null) {
                    if (entrega.compareTo(BigDecimal.ZERO) == 0) {
                        //Columna de Cuotas Corridas
                        if (x >= plan.getCuotaCorridaDesde() && x <= plan.getCuotaCorridaHasta()) {
                            cuotaCapital = precioBaseX.divide(BigDecimal.valueOf(x), 0, RoundingMode.HALF_DOWN);
                            cuotaConInteres = cuotaCapital.multiply(interesAcumulado.add(BigDecimal.ONE));
                            cuotaX = cuotaConInteres.setScale(plan.getIndiceRedondeo(), RoundingMode.HALF_UP);
                        } else {
                            cuotaX = null;
                        }
                        listaCuotas.add(cuotaX);
                    } else {
                        BigDecimal saldo = precioBaseX.subtract(entrega);
                        Logger.getLogger(AsignarPlanBean.class.getName()).log(Level.INFO, "El saldo es {0} y las cuotas son {1}", new Object[]{saldo, x});
                        cuotaCapital = saldo.divide(BigDecimal.valueOf(x), 4, RoundingMode.HALF_DOWN);
                        cuotaConInteres = cuotaCapital.multiply(interesAcumulado.add(BigDecimal.ONE));
                        cuotaX = cuotaConInteres.setScale(plan.getIndiceRedondeo(), RoundingMode.HALF_UP);
                        if (cuotaX.compareTo(montoMinimo) >= 0) {
                            listaCuotas.add(cuotaX);
                        } else {
                            listaCuotas.add(null);
                        }
                    }
                }
            }
            pwr = new PlanWrapper(x, listaCuotas);
            listaDePlanes.add(pwr);
        }
        return null;
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
    
    public void generarReporte() throws Exception {
        NumberFormat nf = new DecimalFormat(ConfiguracionEnum.MONEDA_PATTERN.getSymbol());
        FastReportBuilder drb = new FastReportBuilder();
        Style s = new Style();
        s.setHorizontalAlign(HorizontalAlign.CENTER);
        String n = null;
        listaDeEntrega.remove(1);
        int i = 0;
        for (BigDecimal entrega : listaDeEntrega) {
            if (entrega == null) {
                drb.addColumn(" ", "numeroCuota", Short.class.getName(), 5, s);
                drb.addColumn("Cuota Corrida", "cuotaCorrida", String.class.getName(), 10, s);
            } else {
                n = nf.format(entrega.longValue());
                drb.addColumn(n, "entrega" + i, String.class.getName(), 10, s);
            }
            i++;
        }
        drb.setTitle("Plan de Cuotas - " + motostock.getMoto().getMarca() + " " + motostock.getMoto().getModelo());
        drb.setSubtitle(plan.getNombre() + "                  Precio Contado: " + nf.format(motostock.getPrecioContado()));
        drb.setPrintBackgroundOnOddRows(true);
        Page p = new Page(612, 936, Boolean.FALSE);
        drb.setPageSizeAndOrientation(p);
        drb.setUseFullPageWidth(Boolean.TRUE); //make colums to fill the page width  
        DynamicReport dr = drb.build();
        
        net.sf.jasperreports.engine.JRDataSource ds = new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(listaDePlanes);
        net.sf.jasperreports.engine.JasperPrint jp =
                DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
//        JasperViewer.viewReport(jp);    //finally display the report report   
        new GeneradorReporte().generarReportePdf(jp);
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

    /**
     * @return the cantidadEntregas
     */
    public Integer getCantidadEntregas() {
        return cantidadEntregas;
    }

    /**
     * @param cantidadEntregas the cantidadEntregas to set
     */
    public void setCantidadEntregas(Integer cantidadEntregas) {
        this.cantidadEntregas = cantidadEntregas;
    }
}

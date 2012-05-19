/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import py.com.bej.orm.entities.Credito;
import py.com.bej.orm.entities.Documento;
import py.com.bej.orm.entities.Financiacion;
import py.com.bej.orm.entities.Motostock;
import py.com.bej.orm.entities.Pagare;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.entities.Transaccion;
import py.com.bej.orm.session.CreditoFacade;
import py.com.bej.orm.session.DocumentoFacade;
import py.com.bej.orm.session.FinanciacionFacade;
import py.com.bej.orm.session.MotostockFacade;
import py.com.bej.orm.session.PagareFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.CategoriaEnum;
import py.com.bej.orm.utils.ConfiguracionEnum;
import py.com.bej.orm.utils.Conversor;
import py.com.bej.orm.utils.ConversorDeNumeroALetra;
import py.com.bej.web.servlets.security.LoginBean;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@RequestScoped
public class GeneradorReporte {

    private Credito credito;
    private Motostock stock;
    private List<Financiacion> financiacion;

    /** Creates a new instance of GeneradorReporte */
    public GeneradorReporte() {
    }

    public void generarReportePdf(String sNameReport, Map parameters, JRBeanCollectionDataSource jrDataSource, Set<JasperPrint> anexos) throws Exception {
        try {
            // llenar y devolver el JasperPrint
            JasperPrint jp = getJasperPrint(parameters, sNameReport, jrDataSource);
            if (anexos != null && !anexos.isEmpty()) {
                for (JasperPrint an : anexos) {
                    for (JRPrintPage page : an.getPages()) {
                        jp.addPage(page);
                    }
                }
            }
            exportarAPdf(jp);
        } catch (Exception e) {
            throw e;
        } catch (Error err) {
            throw err;
        }

    }

    /**
     * Genera el reporte en Pdf en la misma página.
     *
     * @param params Parametros del reporte.
     * @param fileNameJrxmlRelative Ruta relativa del Archivo Jrxml.
     * @return
     */
    public boolean generarReportePdf(Map<String, Object> params, String fileNameJasperRelative, JRBeanCollectionDataSource dataSource) {

        //Lee el jrxml para llenar luego con sus parametros
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = getJasperPrint(params, fileNameJasperRelative, dataSource);
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        exportarAPdf(jasperPrint);
        return true;
    }

    private void exportarAPdf(JasperPrint jp) {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        ServletOutputStream output = null;
        try {
            File pdfFile = File.createTempFile(jp.getName(), ".pdf");
            //Exportar a Pdf
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfFile.getAbsolutePath());
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.exportReport();

            //Deployar en una pagina web en la misma pagina.
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!ctx.getResponseComplete()) {

                response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                //Y enviamos el pdf a la salida del navegador como podríamos hacer con cualquier otro pdf
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"" + jp.getName() + "\"");
                //inline es para la misma pagina
                //attachment es para diferente pagina
//                response.setHeader("Content-disposition", "attachment; filename=\"" + pdfFile.getAbsolutePath() + "\"");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setDateHeader("Expires", 0);
                response.setHeader("Content-Transfer-Encoding", "binary");

                output = response.getOutputStream();

                byte[] fichero = FileManagementUtils.convertFileToBytes(pdfFile);

                //Eliminar el archivo pdf
                pdfFile.delete();

                response.setContentLength(fichero.length);
                output.write(fichero, 0, fichero.length);

                output.flush();
                output.close();



                ctx.responseComplete();
            }
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Genera el reporte en Pdf en la misma página.
     *
     * @param params Parametros del reporte.
     * @param fileNameJrxmlRelative Ruta relativa del Archivo Jrxml.
     * @return
     */
    public JasperPrint getJasperPrint(Map<String, Object> params, String sNameReport, JRBeanCollectionDataSource dataSource) throws Exception {
        JasperPrint res = null;
        File pdfFile = null;
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        try {
            pdfFile = new File(context.getRealPath(sNameReport));
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(pdfFile);
            // llenar y devolver el JasperPrint
            if (dataSource == null || dataSource.getRecordCount() < 1) {
                res = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            } else {
                res = JasperFillManager.fillReport(jasperReport, params, dataSource);
            }
        } catch (Exception e) {
            throw e;
        } catch (Error err) {
            throw err;
        }

        return res;
    }

    /**
     * Genera el reporte en Pdf en la misma página.
     *
     * @param params Parametros del reporte.
     * @param fileNameJrxmlRelative Ruta relativa del Archivo Jrxml.
     * @return
     */
    public boolean generarReportePdf(Map<String, Object> params, JasperPrint jasperPrint) {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        ServletOutputStream output = null;
        try {

            //Generar nombre aleatorio
            String letras = "abcdefghijklmnopqrstuvwxyz";
            String pdfFileName = "";
            Random rnd = new Random();
            for (int i = 0; i < 15; i++) {
                pdfFileName = pdfFileName + letras.charAt(rnd.nextInt(26));
            }

            File pdfFile = File.createTempFile(pdfFileName, ".pdf");

            //Exportar a Pdf
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfFile.getAbsolutePath());
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.exportReport();

            //Deployar en una pagina web en la misma pagina.
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!ctx.getResponseComplete()) {

                response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                //Y enviamos el pdf a la salida del navegador como podríamos hacer con cualquier otro pdf
                response.setContentType("application/pdf");
//                response.setHeader("Content-disposition", "inline; filename=\"" + pdfFileName + "\"");
                //inline es para la misma pagina
                //attachment es para diferente pagina
                response.setHeader("Content-disposition", "attachment; filename=\"" + pdfFile.getAbsolutePath() + "\"");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setDateHeader("Expires", 0);
                response.setHeader("Content-Transfer-Encoding", "binary");

                output = response.getOutputStream();

                byte[] fichero = FileManagementUtils.convertFileToBytes(pdfFile);

                //Eliminar el archivo pdf
                pdfFile.delete();

                response.setContentLength(fichero.length);
                output.write(fichero, 0, fichero.length);

                output.flush();
                output.close();



                ctx.responseComplete();
            }
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean generarDocumentosDeVenta(Transaccion venta) {
        String conjunto = "CR";
        String encabezado = null;
        String pie = null;
        if (venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCO.getSymbol())) {
            conjunto = "CO";
        }
        try {
            List<Documento> parrafos = new DocumentoFacade().findByConjunto(new String[]{conjunto, "A"});
            Map<String, String> params = obtenerParametrosDelContrato(venta);
            short x = 0;
            for (Documento p : parrafos) {
                p.setOrden(x++);
                for (int i = 1; i <= params.size(); i++) {
                    if (p.getContenido().contains("<" + i + ">")) {
                        p.setContenido(p.getContenido().replace("<" + i + ">", params.get(String.valueOf(i))));
                        if (p.getOrden() == 0) {
                            //Encabezado
                            encabezado = p.getContenido();
                        }
                    } else {
                        continue;
                    }
                }
                if (p.getOrden() == parrafos.size() - 1) {
                    //Pie del contrato
                    pie = p.getContenido();
                }
            }
            parrafos.remove(0);
            parrafos.remove(x - 2);
            String fileNameJasperRelative = "resources/reportes/contratoVenta.jasper";
            String empresa = "BEJ MOTOS";
            String empresaDireccion = "Casa Central: 14 de Mayo c/ Cap. Caballero. Telefax: 0786 232126<br/>Sucursal: Avenida Irala Nº 9143 Barrio Loma Clavel. Telef: 0786 231903<br/>Pilar - Paraguay";
            String piePagina = "este documento fue impreso el "
                    + Conversor.deDateToString(new Date(), "dd/MM/yyyy")
                    + " a las " + Conversor.deDateToString(new Date(), "HH:mm:s")
                    + " hs. por el usuario " + LoginBean.getInstance().getUsuario().getNombre() + ".";
            Map param = new HashMap();
            Set<JasperPrint> anexos = new HashSet<JasperPrint>();
            if (venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCR.getSymbol())) {
                param.put("TITULO", "CONTRATO DE VENTA A CRÉDITO");
                //Obtener la financiacion
                financiacion = new FinanciacionFacade().findByTransaccion(venta.getId());
                if (financiacion != null && !financiacion.isEmpty()) {
                    Map param1 = new HashMap();
                    param1.put("DOCUMENTO", venta.getComprador().getDocumento());
                    param1.put("NOMBRE", venta.getComprador().getNombre());
                    param1.put("MOTO", "[" + stock.getId() + "] " + stock.getMoto().getCodigo());
                    param1.put("CREDITO_N", "" + credito.getId());
                    param1.put("AMORTIZACION", "" + credito.getAmortizacion());
                    param1.put("MONTO_TOTAL_VENTA", Conversor.numberToStringPattern(venta.getTotal()));
                    param1.put("ENTREGA_INICIAL", Conversor.numberToStringPattern(venta.getEntregaInicial()));
                    param1.put("CAPITAL", Conversor.numberToStringPattern(credito.getCapital()));
                    param1.put("CREDITO_TOTAL", Conversor.numberToStringPattern(credito.getCreditoTotal()));
                    param1.put("PIE_PAGINA", piePagina);
                    anexos.add(getJasperPrint(param1, "resources/reportes/PlanDePago.jasper", new JRBeanCollectionDataSource(financiacion)));
                }
            } else {
                param.put("TITULO", "CONTRATO DE VENTA A CONTADO");
            }
            //Garantia
            List<Documento> registrosGarantia = new DocumentoFacade().findByConjunto(new String[]{"GA"});
            Map param2 = new HashMap();
            param2.put("MARCA", stock.getMoto().getMarca().toUpperCase());
            param2.put("COBERTURA", "4 Meses o 2.000Kms.");
            param2.put("EMPRESA", empresa);
            param2.put("EMPRESA_DIRECCION", empresaDireccion);
            param2.put("5", "3");
            param2.put("6", "1ro. a los 300Kms, 2do a los 1.000Kms y 3ro a los 1.500Kms.");
            param2.put("FECHA", Conversor.deDateToString(venta.getFechaOperacion(), "dd/MM/yyyy"));
            param2.put("N_VENTA", "" + venta.getId());
            param2.put("PIE_PAGINA", piePagina);
            for (Documento p : registrosGarantia) {
                for (int i = 1; i <= param2.size(); i++) {
                    if (p.getContenido().contains("<" + i + ">")) {
                        p.setContenido(p.getContenido().replace("<" + i + ">", String.valueOf(param2.get(String.valueOf(i)))));
                    } else {
                        continue;
                    }
                }
                param2.put("CONDICIONES_GENERALES", registrosGarantia.get(0).getContenido());
            }
            anexos.add(getJasperPrint(param2, "resources/reportes/garantia.jasper", new JRBeanCollectionDataSource(registrosGarantia)));
            //Contrato
            param.put("EMPRESA", empresa);
            param.put("EMPRESA_DIRECCION", empresaDireccion);
            param.put("ENCABEZADO", encabezado);
            param.put("PIE", pie);
            param.put("FIRMA_TITULAR", "Firma del Comprador<br/>" + venta.getComprador().getNombre() + "<br/>CI: " + venta.getComprador().getDocumento());
            param.put("FIRMA_VENDEDOR", "Firma del Vendedor<br/>" + venta.getVendedor().getNombre() + "<br/>CI: " + venta.getVendedor().getDocumento());
            if (params.containsKey("33")) {
                param.put("FIRMA_GARANTE", "Firma del Codeudor<br/>" + params.get("33") + "<br/>CI: " + params.get("34"));
            } else {
                param.put("FIRMA_GARANTE", null);
            }
            param.put("PIE_PAGINA", piePagina);

            generarReportePdf(fileNameJasperRelative, param, new JRBeanCollectionDataSource(parrafos), anexos);
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, "Excepcion al intentar generar el reporte : ", ex);
        } catch (Error err) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, "ERROR al intentar generar el reporte : ", err);
        }
        return true;
    }

    private Map<String, String> obtenerParametrosDelContrato(Transaccion venta) {
        ConversorDeNumeroALetra letras = new ConversorDeNumeroALetra();
        Map<String, String> res = new HashMap<String, String>();
        Calendar c = new GregorianCalendar();
        c.setTime(venta.getFechaOperacion());
        credito = null;
        stock = null;
        try {
            stock = new MotostockFacade().findByVenta(venta.getId());
            if (venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCR.getSymbol())) {
                credito = new CreditoFacade().findByTransaccion(venta.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        res.put("1", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
        res.put("2", c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        res.put("3", String.valueOf(c.get(Calendar.YEAR)));
        res.put("4", venta.getComprador().getSexo().equals('H') ? "El" : "La");
        res.put("5", venta.getComprador().getTratamiento());
        res.put("6", venta.getComprador().getNombre());
        res.put("7", venta.getComprador().getDocumento());
        res.put("8", venta.getComprador().getDireccion1());
        res.put("9", venta.getVendedor().getSexo().equals('H') ? "El" : "La");
        res.put("10", venta.getVendedor().getTratamiento());
        res.put("11", venta.getVendedor().getNombre());
        res.put("12", venta.getVendedor().getDocumento());
        res.put("13", venta.getVendedor().getDireccion1());
        res.put("14", stock.getMoto().getMarca());
        res.put("15", stock.getMoto().getModelo());
        res.put("16", stock.getMoto().getColor());
        res.put("17", stock.getChasis());
        res.put("18", stock.getMotor());
        res.put("19", Conversor.numberToStringPattern(venta.getTotal()));
        res.put("20", letras.getStringOfCurrency(venta.getTotal().floatValue()));
        res.put("21", Conversor.numberToStringPattern(venta.getEntregaInicial()));
        res.put("22", letras.getStringOfCurrency(venta.getEntregaInicial().floatValue()));
        if (credito != null) {
            res.put("23", Conversor.numberToStringPattern(credito.getCapital()));
            res.put("24", letras.getStringOfCurrency(credito.getCapital().floatValue()));
            res.put("25", String.valueOf(credito.getAmortizacion()));
            res.put("26", letras.getStringOfNumber(credito.getAmortizacion()));
            res.put("27", Conversor.numberToStringPattern(venta.getMontoCuotaIgual()));
            res.put("28", letras.getStringOfCurrency(venta.getMontoCuotaIgual().floatValue()));
            res.put("29", Conversor.deDateToString(credito.getFechaFin(), "dd/MM/yyyy"));
            res.put("30", venta.getVendedor().getDireccion1());
            res.put("31", credito.getGarante().getSexo().equals('H') ? "El" : "La");
            res.put("32", credito.getGarante().getTratamiento());
            res.put("33", credito.getGarante().getNombre());
            res.put("34", credito.getGarante().getDocumento());
            res.put("35", credito.getGarante().getDireccion1());
            res.put("36", Conversor.numberToStringPattern(stock.getPlan().getTae()));
            res.put("37", Conversor.numberToStringPattern(stock.getPlan().getTasaInteresMoratorio()));
        }
        return res;
    }

    private Map<String, String> obtenerListaDelPagare(Transaccion venta) {
        Map<String, String> res = new HashMap<String, String>();
        Calendar c = new GregorianCalendar();

        c.setTime(venta.getFechaOperacion());
        credito = null;
        stock = null;
        try {
            stock = new MotostockFacade().findByVenta(venta.getId());
            if (venta.getCodigo().getId().equals(CategoriaEnum.VENTA_MCR.getSymbol())) {
                credito = new CreditoFacade().findByTransaccion(venta.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        res.put("1", null);
        res.put("2", (venta.getVendedor().getSexo().equals('H') ? "El " : "La ")
                + venta.getVendedor().getTratamiento()
                + " " + venta.getVendedor().getNombre());
        res.put("3", venta.getVendedor().getDireccion1());
        res.put("4", null);
        res.put("5", stock.getMoto().getMarca());
        res.put("6", stock.getMoto().getModelo());
        res.put("7", stock.getMoto().getColor());
        res.put("8", stock.getChasis());
        res.put("9", stock.getMotor());

        res.put("10", Conversor.numberToStringPattern(credito.getTae()));
        res.put("11", Conversor.numberToStringPattern(stock.getPlan().getTasaInteresMoratorio()));
        res.put("12", venta.getVendedor().getNombre());
        res.put("13", "Pilar");

        return res;
    }

    public boolean generarPagares(Transaccion venta) {
        Map<String, String> params = obtenerListaDelPagare(venta);
        String fileNameJasperRelative = "resources/reportes/pagare.jasper";
        List<Pagare> pagares = new ArrayList<Pagare>();
        pagares = new PagareFacade().findByTransaccion(venta.getId());
        if (!pagares.isEmpty()) {
            List<Documento> contenidoPagare = new ArrayList<Documento>();
            contenidoPagare = new DocumentoFacade().findByConjunto("PA");
            String condicionesPagare = contenidoPagare.get(1).getContenido();
            for (int x = 10; x <= params.size(); x++) {
                if (condicionesPagare.contains("<" + x + ">")) {
                    condicionesPagare = condicionesPagare.replace("<" + x + ">", String.valueOf(params.get(String.valueOf(x))));
                }
            }
            financiacion = new ArrayList<Financiacion>();
            Long cantidadDeCuotas = null;
            cantidadDeCuotas = new FinanciacionFacade().findCantidadByTransaccion(venta.getId());
            int x = 1;
            for (Pagare p : pagares) {
                Financiacion f = new Financiacion();
                String descripcionPagare = contenidoPagare.get(0).getContenido();
                f.setTotalAPagar(p.getMonto());
                for (int i = 1; i <= params.size(); i++) {
                    if (i == 1) {
                        //Fecha de Vencimiento
                        if (p.getVencimientoImpreso()) {
                            descripcionPagare = descripcionPagare.replace("<1>", "El día " + p.getFechaVencimientoString());
                            f.setFechaVencimiento(p.getFechaVencimiento());
                        } else {
                            descripcionPagare = descripcionPagare.replace("<1>", "");
                            f.setFechaVencimiento(null);
                        }
                    } else if (i == 4) {
                        //Monto en Letras
                        descripcionPagare = descripcionPagare.replace("<4>", f.getTotalAPagarEnLetras());
                    } else if (descripcionPagare.contains("<" + i + ">")) {
                        descripcionPagare = descripcionPagare.replace("<" + i + ">", String.valueOf(params.get(String.valueOf(i))));
                    } else {
                        continue;
                    }
                }
                //Numero de Pagares
                if (pagares.size() < cantidadDeCuotas) {
                    f.setNumeroCuota(Short.parseShort("" + x++));
                    p.getCredito().setAmortizacion(Short.parseShort("" + pagares.size()));
                } else {
                    f.setNumeroCuota(p.getNumero());
                }
                f.setId(p.getId());
                f.setCredito(p.getCredito());
                f.setDescripcionPagare(descripcionPagare);
                f.setCondicionesPagare(condicionesPagare);
                f.setNombreTitular(venta.getComprador().getNombre());
                f.setDocumentoTitular(venta.getComprador().getDocumento());
                f.setDomicilioTitular(venta.getComprador().getDireccion1());
                f.setNombreGarante(credito.getGarante().getNombre());
                f.setDocumentoGarante(credito.getGarante().getDocumento());
                f.setDomicilioGarante(credito.getGarante().getDireccion1());
                financiacion.add(f);
            }
            String piePagina = "este documento fue impreso el "
                    + Conversor.deDateToString(new Date(), "dd/MM/yyyy")
                    + " a las " + Conversor.deDateToString(new Date(), "HH:mm:s")
                    + " hs. por el usuario " + LoginBean.getInstance().getUsuario().getNombre() + ".";
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("PIE_PAGINA", piePagina);
            generarReportePdf(param, fileNameJasperRelative, new JRBeanCollectionDataSource(financiacion));
        }
        return true;
    }

    public boolean generarResposabilidadCivil(Transaccion venta) {
        //Responsabilidad Civil
        String fileNameJasperRelative = "resources/reportes/responsabilidadCivil.jasper";
        String empresa = "BEJ MOTOS";
        String empresaDireccion = "Casa Central: 14 de Mayo c/ Cap. Caballero. Telefax: (0786)232126<br/>Sucursal: Avenida Irala Nº 9143 Barrio Loma Clavel. Telef: 0786 231903<br/>Pilar - Paraguay";
        String piePagina = "este documento fue impreso el "
                + Conversor.deDateToString(new Date(), "dd/MM/yyyy")
                + " a las " + Conversor.deDateToString(new Date(), "HH:mm:s")
                + " hs. por el usuario " + LoginBean.getInstance().getUsuario().getNombre() + ".";
        try {
            stock = new MotostockFacade().findByVenta(venta.getId());

            List<Documento> registrosResponsabilidad = new DocumentoFacade().findByConjunto(new String[]{"RC"});
            Map param2 = new HashMap();
            param2.put("1", empresa);
            for (Documento p : registrosResponsabilidad) {
                for (int i = 1; i <= param2.size(); i++) {
                    if (p.getContenido().contains("<" + i + ">")) {
                        p.setContenido(p.getContenido().replace("<" + i + ">", String.valueOf(param2.get(String.valueOf(i)))));
                    } else {
                        continue;
                    }
                }
                param2.put("DECLARACION", registrosResponsabilidad.get(0).getContenido());
            }
            param2.put("EMPRESA", empresa);
            param2.put("MARCA", stock.getMoto().getMarca().toUpperCase());
            param2.put("MODELO", stock.getMoto().getModelo());
            param2.put("COLOR", stock.getMoto().getColor());
            param2.put("CHASIS", stock.getChasis());
            param2.put("MOTOR", stock.getMotor());
            param2.put("EMPRESA_DIRECCION", empresaDireccion);
            param2.put("FECHA", Conversor.deDateToString(new Date(), "dd/MM/yyyy"));
            param2.put("N_VENTA", "" + venta.getId());
            param2.put("PIE_PAGINA", piePagina);
            param2.put("FIRMA_TITULAR", "Firma del Comprador<br/>" + venta.getComprador().getNombre() + "<br/>CI: " + venta.getComprador().getDocumento());

            generarReportePdf(param2, fileNameJasperRelative, null);
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean generarReciboInicial(Transaccion venta) {
        List<Persona> personas = new ArrayList<Persona>();
        String propietario = new PersonaFacade().findByDocumento(ConfiguracionEnum.PROPIETARIO.getSymbol()).getNombre();
        ConversorDeNumeroALetra letras = new ConversorDeNumeroALetra();
        Calendar c = new GregorianCalendar();
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        c.setTime(venta.getFechaOperacion());
        //Recibo Inicial
        String fileNameJasperRelative = "resources/reportes/recibo.jasper";
        String empresa = "BEJ MOTOS";
        String empresaDireccion = "<i>de " + propietario + "</i><br/>Casa Central: 14 de Mayo c/ Cap. Caballero. Telefax: 0786 232126<br/>Sucursal: Avenida Irala Nº 9143 Barrio Loma Clavel. Telef: 0786 231903<br/>Pilar - Paraguay";
        String piePagina = "este documento fue impreso el "
                + Conversor.deDateToString(new Date(), "dd/MM/yyyy")
                + " a las " + Conversor.deDateToString(new Date(), "HH:mm:s")
                + " hs. por el usuario " + LoginBean.getInstance().getUsuario().getNombre() + ".";
        try {
            stock = new MotostockFacade().findByVenta(venta.getId());

            Map param2 = new HashMap();
            param2.put("IMAGEN_URL", context.getRealPath("/resources/images/logo_bej.gif"));
            param2.put("EMPRESA", empresa);
            param2.put("TOTAL_NUMERO", "Gs. " + Conversor.numberToStringPattern(venta.getEntregaInicial()));
            param2.put("TOTAL_LETRA", "La suma de " + letras.getStringOfCurrency(venta.getEntregaInicial().floatValue()));
            param2.put("CONCEPTO", "En concepto de pago por Entrega Inicial de una Moto"
                    + " Marca <b>" + stock.getMoto().getMarca().toUpperCase()
                    + "</b> Modelo <b>" + stock.getMoto().getModelo()
                    + "</b> Color <b>" + stock.getMoto().getColor()
                    + "</b> Chasis Nº <b>" + stock.getChasis()
                    + "</b> Motor Nº <b>" + stock.getMotor() + "</b>");
            param2.put("EMPRESA_DIRECCION", empresaDireccion);
            param2.put("FECHA", "Pilar, " + String.valueOf(c.get(Calendar.DAY_OF_MONTH))
                    + " de " + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                    + " de " + String.valueOf(c.get(Calendar.YEAR)) + ".-");
            param2.put("N_VENTA", "" + venta.getId());
            param2.put("PIE_PAGINA", piePagina);
            param2.put("FIRMA_VENDEDOR", venta.getVendedor().getNombre() + "<br/>CI: " + venta.getVendedor().getDocumento());
            personas.add(venta.getComprador());
            personas.add(venta.getComprador());
            generarReportePdf(param2, fileNameJasperRelative, new JRBeanCollectionDataSource(personas));
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean generarReportePdf(JasperPrint jp) {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        ServletOutputStream output = null;
        try {
            //Generar nombre aleatorio
            String letras = "abcdefghijklmnopqrstuvwxyz";
            String pdfFileName = "";
            Random rnd = new Random();
            for (int i = 0; i < 15; i++) {
                pdfFileName = pdfFileName + letras.charAt(rnd.nextInt(26));
            }

            File pdfFile = File.createTempFile(pdfFileName, ".pdf");

            //Exportar a Pdf
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfFile.getAbsolutePath());
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.exportReport();

            //Deployar en una pagina web en la misma pagina.
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!ctx.getResponseComplete()) {

                response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                //Y enviamos el pdf a la salida del navegador como podríamos hacer con cualquier otro pdf
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"" + pdfFileName + "\"");
                //inline es para la misma pagina
                //attachment es para diferente pagina
//                response.setHeader("Content-disposition", "attachment; filename=\"" + pdfFile.getAbsolutePath() + "\"");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setDateHeader("Expires", 0);
                response.setHeader("Content-Transfer-Encoding", "binary");

                output = response.getOutputStream();

                byte[] fichero = FileManagementUtils.convertFileToBytes(pdfFile);

                //Eliminar el archivo pdf
                pdfFile.delete();

                response.setContentLength(fichero.length);
                output.write(fichero, 0, fichero.length);

                output.flush();
                output.close();



                ctx.responseComplete();
            }
        } catch (Exception ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}

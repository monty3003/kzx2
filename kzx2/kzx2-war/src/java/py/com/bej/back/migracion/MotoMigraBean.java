/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.back.migracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import py.com.bej.base.prod.entity.Vmmotos;
import py.com.bej.base.prod.session.MotosProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.PersonaFacade;

/**
 *
 * @author Diego_M
 */
@ManagedBean
@ViewScoped
public class MotoMigraBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private MotosProduccionFacade motosProduccionFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private MotoFacade facade;
    public final static Logger LOGGER = Logger.getLogger(MotoMigraBean.class.getName());

    /** Creates a new instance of MotoMigraBean */
    public MotoMigraBean() {
    }

    /**
     * @return the facade
     */
    public MotoFacade getFacade() {
        if (this.facade == null) {
            this.facade = new MotoFacade();
        }
        return facade;
    }

    public MotosProduccionFacade getMotosProduccionFacade() {
        if (this.motosProduccionFacade == null) {
            this.motosProduccionFacade = new MotosProduccionFacade();
        }
        return motosProduccionFacade;
    }

    /**
     * @return the facade
     */
    public PersonaFacade getPersonaFacade() {
        if (this.personaFacade == null) {
            this.personaFacade = new PersonaFacade();
        }
        return personaFacade;
    }

    /**
     * @return the facade
     */
    public CategoriaFacade getCategoriaFacade() {
        if (this.categoriaFacade == null) {
            this.categoriaFacade = new CategoriaFacade();
        }
        return categoriaFacade;
    }

    public String importar() {

        return "importarMoto";
    }

    public String migrarMotos() throws Exception {
        String res = null;
        int total = 0;
        //Produccion
        motosProduccionFacade = new MotosProduccionFacade();
        List<Vmmotos> listaMotosEnProduccion = null;
        //Desarrollo
        Categoria categoriaProduccion = null;
        //MIGRAR
        listaMotosEnProduccion = motosProduccionFacade.findAll();
        if (!listaMotosEnProduccion.isEmpty()) {
            Moto m = null;
            List<Moto> lista = new ArrayList<Moto>();
            String codigoProduccion = null;
            String codigoFabricaProduccion = null;
            String marcaProduccion = null;
            String modeloProduccion = null;
            String colorProduccion = null;
            Persona fabricanteProduccion = null;
            Character activoProduccion = null;
            categoriaProduccion = getCategoriaFacade().find(30);

            for (Vmmotos v : listaMotosEnProduccion) {
                //Codigo
                if (v.getCodMoto().trim().length() < 6) {
                    codigoProduccion = v.getCodMoto() + "X";
                } else if (v.getCodMoto().trim().length() > 20) {
                    codigoProduccion = v.getCodMoto().trim().substring(0, 19);
                } else {
                    codigoProduccion = v.getCodMoto().trim().toUpperCase();
                }
                codigoFabricaProduccion = codigoProduccion;
                //Marca
                if (v.getMarca() == null || v.getMarca().trim().equals("")) {
                    if (v.getCodMoto().trim().equals("HPDLX65R")) {
                        marcaProduccion = "HERO PUCH";
                    } else {
                        marcaProduccion = "Sin Marca";
                    }
                } else if (v.getMarca().trim().length() < 6) {
                    if (v.getMarca().trim().equalsIgnoreCase("MS")
                            || v.getMarca().trim().equalsIgnoreCase("MOROSTAR")
                            || v.getMarca().trim().equalsIgnoreCase("STAR")) {
                        marcaProduccion = "MOTOSTAR";
                    } else {
                        marcaProduccion = v.getMarca().toUpperCase() + "X";
                    }
                } else if (v.getMarca().trim().length() > 20) {
                    marcaProduccion = v.getMarca().trim().toUpperCase().substring(0, 19);
                } else {
                    marcaProduccion = v.getMarca().trim().toUpperCase();
                }
                //Fabricante
                activoProduccion = 'N';
                if (marcaProduccion.equals("MOTOSTAR")) {
                    fabricanteProduccion = getPersonaFacade().findByDocumento("80002740-0");
                    activoProduccion = 'S';
                } else if (marcaProduccion.equals("LEOPARD") || marcaProduccion.equals("MARUTI") || marcaProduccion.equals("MONTANA")) {
                    fabricanteProduccion = getPersonaFacade().findByDocumento("80001307-7");
                } else if (marcaProduccion.equals("KENTON") || marcaProduccion.equals("MAGNUM") || marcaProduccion.equals("HERO PUCH")
                        || marcaProduccion.equals("HUSQVARNA")) {
                    fabricanteProduccion = getPersonaFacade().findByDocumento("80013744-2");
                } else if (marcaProduccion.equals("PRISSA")) {
                    fabricanteProduccion = getPersonaFacade().findByDocumento("PLUA-996530");
                } else {
                    fabricanteProduccion = getPersonaFacade().findByDocumento("80020496-4");
                }
                //Modelo
                if (v.getModelo() == null) {
                    modeloProduccion = "Sin Modelo";
                } else if (v.getModelo().trim().length() > 20) {
                    modeloProduccion = v.getModelo().trim().substring(0, 19).toUpperCase();
                } else {
                    modeloProduccion = v.getModelo().trim().toUpperCase();
                }
                //Color
                if (v.getColor() == null) {
                    colorProduccion = "Sin Color";
                } else if (v.getColor().trim().length() > 20) {
                    colorProduccion = v.getColor().trim().substring(0, 19).toUpperCase();
                } else {
                    colorProduccion = v.getColor().trim().toUpperCase();
                }
                //Fabricante
                if (codigoProduccion.trim().equals("MSSK150MAXANA")) {
                    int c = 0;
                }
                m = new Moto(codigoProduccion, codigoFabricaProduccion, marcaProduccion, modeloProduccion, colorProduccion, fabricanteProduccion, categoriaProduccion);
                m.setActivo(activoProduccion);
                m.setUltimaModificacion(new Date());
                lista.add(m);
                total++;
            }
            try {
                total = getFacade().cargaMasiva(lista);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
            setInfoMessage(null, "Total de registros importados: " + total);
        }


        return "moto";

    }

    void setInfoMessage(String component, String summary) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}

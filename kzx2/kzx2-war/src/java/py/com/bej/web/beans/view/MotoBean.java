/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.beans.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import py.com.bej.base.prod.entity.Vmmotos;
import py.com.bej.base.prod.session.MotosProduccionFacade;
import py.com.bej.orm.entities.Categoria;
import py.com.bej.orm.entities.Moto;
import py.com.bej.orm.entities.Persona;
import py.com.bej.orm.session.CategoriaFacade;
import py.com.bej.orm.session.MotoFacade;
import py.com.bej.orm.session.PersonaFacade;
import py.com.bej.orm.utils.Orden;
import py.com.bej.web.utils.JsfUtils;

/**
 *
 * @author diego
 */
@ManagedBean
@SessionScoped
public class MotoBean extends AbstractPageBean<Moto> {

    @EJB
    private MotosProduccionFacade motosProduccionFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private PersonaFacade personaFacade;
    @EJB
    private MotoFacade facade;
    private Moto moto;
    //Listas
    private List<SelectItem> listaCategoria;
    private List<SelectItem> listaPersona;
    //Campos de busqueda
    private String codigo;
    private String categoria;
    private String codigoFabrica;
    private String marca;
    private String modelo;
    private String color;
    private String fabricante;

    /** Creates a new instance of MotoBean */
    public MotoBean() {
    }

    @Override
    void limpiarCampos() {
        setAgregar(Boolean.FALSE);
        setModificar(Boolean.FALSE);
        this.codigo = null;
        this.categoria = null;
        this.codigoFabrica = null;
        this.marca = null;
        this.modelo = null;
        this.color = null;
        this.fabricante = null;
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

    @Override
    public String listar() {
        limpiarCampos();
        setNav("moto");
        setDesde(0);
        setMax(10);
        if (facade.getOrden() == null) {
            getFacade().setOrden(new Orden("codigo", false));
        }
        setLista(filtrar());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().r0);
        }
        return getNav();
    }

    @Override
    List filtrar() {
        Persona fab = new Persona();
        Categoria cat = new Categoria();
        if (fabricante != null && !fabricante.equals("-1")) {
            fab = new Persona(fabricante);
        }
        if (categoria != null && !categoria.equals("-1")) {
            cat = new Categoria(Integer.valueOf(categoria));
        }
        getFacade().setEntity(new Moto(codigo, codigoFabrica, marca, modelo, color,
                fab, cat));
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        setLista(getFacade().findRange());
        return getLista();
    }

    @Override
    void obtenerListas() {
        listaCategoria = JsfUtils.getSelectItems(new CategoriaFacade().findBetween(30, 40), !getModificar());
        listaPersona = JsfUtils.getSelectItems(new PersonaFacade().findByPersona(20), !getModificar());
    }

    @Override
    public String buscar() {
        Persona fab = new Persona();
        Categoria cat = new Categoria();
        if (fabricante != null && !fabricante.equals("-1")) {
            fab = new Persona(fabricante);
        }
        if (categoria != null && !categoria.equals("-1")) {
            cat = new Categoria(Integer.valueOf(categoria));
        }
        if (codigo.equals("")) {
            codigo = null;
        }
        if (codigoFabrica.equals("")) {
            codigoFabrica = null;
        }
        if (marca.equals("")) {
            marca = null;
        }
        if (modelo.equals("")) {
            modelo = null;
        }
        if (color.equals("")) {
            color = null;
        }
        getFacade().setEntity(new Moto(codigo, codigoFabrica, marca, modelo, color, fab, cat));
        getFacade().setContador(null);
        setLista(getFacade().findRange());
        if (getLista().isEmpty()) {
            setErrorMessage(null, getFacade().c0);
        }
        return getNav();
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


        return todos();

    }

    @Override
    public String nuevo() {
        setAgregar(Boolean.TRUE);
        setModificar(Boolean.FALSE);
        setMoto(new Moto());
        obtenerListas();
        return "moto";
    }

    @Override
    public String modificar() {
        //recuperar la seleccion
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.setCodigo((String) request.getParameter("radio"));
        if (this.getCodigo() != null) {
            try {
                moto = facade.find(this.getCodigo());
                setActivo(moto.getActivo().equals('S') ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                Logger.getLogger(UbicacionBean.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
            setAgregar(Boolean.FALSE);
            setModificar(Boolean.TRUE);
            obtenerListas();
            categoria = String.valueOf(moto.getCategoria().getId());
            fabricante = String.valueOf(moto.getCategoria().getId());
            setCategoria(String.valueOf(moto.getCategoria().getId()));
            return "moto";
        } else {
            setErrorMessage(null, facade.sel);
            return null;
        }
    }

    @Override
    boolean validar() {
        if (getMoto().getCodigo().trim().equals("")) {
            setErrorMessage("frm:codigo", "Ingrese un valor");
            return false;
        } else {
            Moto existe = null;
            existe = getFacade().find(codigo.trim());
            if (existe != null && facade.getEntity() == null) {
                setErrorMessage("frm:codigo", "Ya existe una moto con este código");
                return false;
            }
        }
        boolean res = true;
        if (getMoto().getCodigoFabrica().trim().equals("")) {
            setErrorMessage("frm:codigoFabrica", "Ingrese un valor");
            res = false;
        }
        if (getMoto().getMarca().trim().equals("")) {
            setErrorMessage("frm:marca", "Ingrese un valor");
            res = false;
        }
        if (getMoto().getModelo().trim().equals("")) {
            setErrorMessage("frm:modelo", "Ingrese un valor");
            res = false;
        }
        if (getMoto().getColor().trim().equals("")) {
            setErrorMessage("frm:color", "Ingrese un valor");
            res = false;
        }
        if (fabricante == null || fabricante.equals("-1")) {
            setErrorMessage("frm:fabricante", "Seleccione un valor");
            res = false;
        }
        if (categoria == null || categoria.equals("-1")) {
            setErrorMessage("frm:categoria", "Seleccione un valor");
            res = false;
        }
        return res;
    }

    @Override
    public String guardar() {
        boolean validado = validar();
        if (validado) {
            moto.getCategoria().setId(Integer.valueOf(categoria));
            moto.getFabricante().setId(Integer.valueOf(fabricante));
            getFacade().setEntity(moto);
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
        setMoto(new Moto());
        getFacade().setEntity(getMoto());
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
        setMoto(new Moto());
        getFacade().setContador(null);
        getFacade().setUltimo(null);
        getFacade().setRango(new Integer[]{getDesde(), getMax()});
        getFacade().setOrden(new Orden("codigo", false));
        this.filtrar();
        return getNav();
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
    public String getFabricante() {
        return fabricante;
    }

    /**
     * @param fabricante the fabricante to set
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the listaCategoria
     */
    public List<SelectItem> getListaCategoria() {
        return listaCategoria;
    }

    /**
     * @return the listaPersona
     */
    public List<SelectItem> getListaPersona() {
        return listaPersona;
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
}

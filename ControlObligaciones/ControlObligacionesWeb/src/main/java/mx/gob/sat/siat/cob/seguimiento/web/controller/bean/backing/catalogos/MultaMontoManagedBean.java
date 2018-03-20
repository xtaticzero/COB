package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MultaMontoService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.MultaMontoModel;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("catalogoMultaMontoMB")
@Scope("view")
public class MultaMontoManagedBean extends AbstractBaseMB {

    @Autowired
    private MultaMontoService oblisancionService;
    private MultaMontoModel oblisancionModel = new MultaMontoModel();
    private String sancionTemp;
    private String infraccionTemp;
    private String motivacionTemp;
    private Long montoTemp;
    private String decsripcionMontoTemp;
    private static final String TAGPERIODO = ":periodo:";
    private static final String TAGEJERCICIO = ":ejercicio:";
    private static final String TAGNUMCONTROL = ":numcontrol:";
    private static final String TAGFECHA = ":fechanotificacion:";
    private Boolean muestraConfirmacion = false;

   
    /**
     *
     */
    public MultaMontoManagedBean() {
        super();
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
       
                
        try {
            obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(),
                    ConstantsCatalogos.IDENTIFICADOR_MULTAMONTO,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                oblisancionModel.setListOblisancion(oblisancionService.todasLasOblisanciones());
            }
        } catch (SessionRolNullException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(e);
        } catch (AccesoDenegadoException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(e);
        } catch (AccesoDenegadoFielException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().error(e);
        }

    }

    /**
     *
     */
    public void agrega() {
        MultaMonto oblisancion = new MultaMonto();

        oblisancion.setSancion(oblisancionModel.getSancion());
        oblisancion.setInfraccion(oblisancionModel.getInfraccion());
        Calendar cal = Calendar.getInstance();
        oblisancion.setFechaInicio(cal.getTime());
        oblisancion.setIdObligacion(oblisancionModel.getIdObligacionSan());
        oblisancion.setOrden(ConstantsCatalogos.ORDEN);
        oblisancion.setResolMotivo(oblisancionModel.getIdTipoMultaSel());
        oblisancion.setMotivacion(oblisancionModel.getMotivacion());
        oblisancion.setMonto(oblisancionModel.getMonto());
        oblisancion.setDescripcionMonto(oblisancionModel.getDescripcionMonto());
        Integer reg = oblisancionService.buscarMultaMontoRepetida(oblisancionModel.getIdObligacionSan(), oblisancionModel.getIdTipoMultaSel());
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_MULTAMONTO, new Date(), new Date(), ConstantsCatalogos.ALTA_MOV_MULTAMONTO, ConstantsCatalogos.ALTA_MULTAMONTO_OBS);
            oblisancionService.agregaOblisancion(oblisancion, dto, (reg > 0)?true:false);
            oblisancionModel.setListOblisancion(oblisancionService.todasLasOblisanciones());
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage());
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        } catch (DaoException e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void edita() {
        
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_MULTAMONTO, new Date(), new Date(), ConstantsCatalogos.MODIFICACION_MOV_MULTAMONTO, ConstantsCatalogos.MODIFICACION_MULTAMONTO_OBS);
            Integer reg = oblisancionService.buscarMultaMontoRepetida(oblisancionModel.getIdObligacionSan(), oblisancionModel.getIdTipoMultaSel());
            if (reg == null) {
                AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
                return;
            }
            oblisancionModel.getOblisancionEdit().setResolMotivo(oblisancionModel.getIdTipoMultaSel());
            oblisancionService.editaOblisancion(oblisancionModel.getOblisancionEdit(), dto, true);
            oblisancionModel.setListOblisancion(oblisancionService.todasLasOblisanciones());

            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEDITADO);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage());
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        } catch (DaoException e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void elimina() {

        Calendar cal = Calendar.getInstance();
        oblisancionModel.getOblisancionEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_MULTAMONTO,
                    new Date(), new Date(),
                    ConstantsCatalogos.BAJA_MOV_MULTAMONTO,
                    ConstantsCatalogos.BAJA_MULTAMONTO_OBS);
            oblisancionService.eliminaOblisancion(oblisancionModel.getOblisancionEli(), dto);
            oblisancionModel.setListOblisancion(oblisancionService.todasLasOblisanciones());
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROELIMINADO);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage());
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        } catch (DaoException e) {
            getLogger().error(e.getMessage());
        }
    }

             
    
    /**
     *
     */
    public void generaExcel() {

        byte[] excel;

        if (oblisancionModel.getListOblisancionTmp() != null) {
            excel = oblisancionService.generaExcel(oblisancionModel.getListOblisancionTmp()).toByteArray();
        } else {
            excel = oblisancionService.generaExcel(oblisancionModel.getListOblisancion()).toByteArray();
        }

        despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);

    }

    /**
     *
     */
    public void generaPdf() {

        byte[] pdf;

        if (oblisancionModel.getListOblisancionTmp() != null) {
            pdf = oblisancionService.generaPDF(oblisancionModel.getListOblisancionTmp()).toByteArray();
        } else {
            pdf = oblisancionService.generaPDF(oblisancionModel.getListOblisancion()).toByteArray();
        }

        despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);

    }


    /**
     *
     */
    public void abrirNuevo() {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantsCatalogos.TAGS_TITULO, "") );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_PERIODO) );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_EJERCICIO) );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_FECHA) );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_NUMCONTROL) );
        oblisancionModel.setListaComboObligacion(oblisancionService.obtenerListaComboObligacion());
        oblisancionModel.setListaComboTipoMulta(oblisancionService.obtenerListaComboTipoMulta());
        oblisancionModel.setIdTipoMultaSel(null);
        oblisancionModel.setMotivacion(null);
        oblisancionModel.setMonto(null);
        oblisancionModel.setDescripcionMonto(null);
        this.getOblisancionModel().setTblVisible(false);
        this.getOblisancionModel().setNvoVisible(true);
    }
    

    /**
     *
     */
    public void abrirEditar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantsCatalogos.TAGS_TITULO, "") );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_PERIODO) );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_EJERCICIO) );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_FECHA) );
        fc.addMessage("tags", new FacesMessage(FacesMessage.SEVERITY_INFO, "", ConstantsCatalogos.TAGS_MENSAJE_NUMCONTROL) );
        setSancionTemp(oblisancionModel.getOblisancionEdit().getSancion());
        setInfraccionTemp(oblisancionModel.getOblisancionEdit().getInfraccion());
        setMotivacionTemp(oblisancionModel.getOblisancionEdit().getMotivacion());
        setMontoTemp(oblisancionModel.getOblisancionEdit().getMonto());
        setDecsripcionMontoTemp(oblisancionModel.getOblisancionEdit().getDescripcionMonto());
        oblisancionModel.setListaComboObligacion(oblisancionService.obtenerListaComboObligacion());
        oblisancionModel.setIdObligacionSan(oblisancionModel.getOblisancionEdit().getIdObligacion());

        oblisancionModel.setListaComboTipoMulta(oblisancionService.obtenerListaComboTipoMulta());
        oblisancionModel.setIdTipoMultaSel(oblisancionModel.getOblisancionEdit().getResolMotivo());
        this.getOblisancionModel().setTblVisible(false);
        this.getOblisancionModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        
        oblisancionModel.setListaComboObligacion(oblisancionService.obtenerListaComboObligacion());
        oblisancionModel.setIdObligacionSan(oblisancionModel.getOblisancionEli().getIdObligacion());
        oblisancionModel.setListaComboTipoMulta(oblisancionService.obtenerListaComboTipoMulta());
        oblisancionModel.setIdTipoMultaSel(oblisancionModel.getOblisancionEli().getResolMotivo());
        oblisancionModel.setMotivacion(oblisancionModel.getOblisancionEli().getMotivacion());
        oblisancionModel.setMonto(oblisancionModel.getOblisancionEli().getMonto());
        oblisancionModel.setDescripcionMonto(oblisancionModel.getOblisancionEli().getDescripcionMonto());
        this.getOblisancionModel().setTblVisible(false);
        this.getOblisancionModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getOblisancionModel().setTblVisible(true);
        this.getOblisancionModel().setNvoVisible(false);
        this.getOblisancionModel().setEdtVisible(false);
        this.getOblisancionModel().setElmVisible(false);
        muestraConfirmacion = false;
        clean();
    }

    /**
     *
     */
    public void clean() {
        if (sancionTemp != null) {
            oblisancionModel.getOblisancionEdit().setSancion(getSancionTemp());
        }
        if (infraccionTemp != null) {
            oblisancionModel.getOblisancionEdit().setInfraccion(getInfraccionTemp());
        }
        if (motivacionTemp != null) {
            oblisancionModel.getOblisancionEdit().setMotivacion(getMotivacionTemp());
        }
        if (montoTemp != null) {
            oblisancionModel.getOblisancionEdit().setMonto(getMontoTemp());
        }
        if (decsripcionMontoTemp != null) {
            oblisancionModel.getOblisancionEdit().setDescripcionMonto(getDecsripcionMontoTemp());
        }
        getOblisancionModel().setSancion(null);
        getOblisancionModel().setInfraccion(null);
        getOblisancionModel().setIdObligacionSan(null);
        getOblisancionModel().setMonto(null);
        getOblisancionModel().setOrden(null);
        getOblisancionModel().setIdObligacion(null);
        getOblisancionModel().setResolMotivo(null);
        getOblisancionModel().setIdTipoMultaSel(null);
        getOblisancionModel().setMotivacion(null);
        getOblisancionModel().setMonto(null);
        getOblisancionModel().setDescripcionMonto(null);
    }
    
    
     public void validaTags(){
        Pattern pattern1 = Pattern.compile("(:[A-Za-z]+:)");
        Matcher matcher1 = pattern1.matcher(this.oblisancionModel.getMotivacion());
    
        while (matcher1.find()) {
            if (!(matcher1.group(1).equals(TAGEJERCICIO) || matcher1.group(1).equals(TAGFECHA) || 
                    matcher1.group(1).equals(TAGNUMCONTROL) || matcher1.group(1).equals(TAGPERIODO))) {
              AbstractBaseMB.msgWarn(ConstantsCatalogos.VERIFICARTAG+" "+matcher1.group(1));
                                   
            }
            
        }
             
    }
     
    public void validaTagsEditar(){
       Pattern pattern1 = Pattern.compile("(:[A-Za-z]+:)");
       Matcher matcher1 = pattern1.matcher(this.oblisancionModel.getOblisancionEdit().getMotivacion());
    
       while (matcher1.find()) {
           if (!(matcher1.group(1).equals(TAGEJERCICIO) || matcher1.group(1).equals(TAGFECHA) || 
                   matcher1.group(1).equals(TAGNUMCONTROL) || matcher1.group(1).equals(TAGPERIODO))) {
             AbstractBaseMB.msgWarn(ConstantsCatalogos.VERIFICARTAG+" "+matcher1.group(1));
                                  
           }
           
       }
            
    }
    
    /**
     *
     * @param oblisancionService
     */
    public void setOblisancionService(MultaMontoService oblisancionService) {
        this.oblisancionService = oblisancionService;
    }

    /**
     *
     * @return
     */
    public MultaMontoService getOblisancionService() {
        return oblisancionService;
    }

    /**
     *
     * @param oblisancionModel
     */
    public void setOblisancionModel(MultaMontoModel oblisancionModel) {
        this.oblisancionModel = oblisancionModel;
    }

    /**
     *
     * @return
     */
    public MultaMontoModel getOblisancionModel() {
        return oblisancionModel;
    }

    public void setSancionTemp(String sancionTemp) {
        this.sancionTemp = sancionTemp;
    }

    public String getSancionTemp() {
        return sancionTemp;
    }

    public void setInfraccionTemp(String infraccionTemp) {
        this.infraccionTemp = infraccionTemp;
    }

    public String getInfraccionTemp() {
        return infraccionTemp;
    }

    public void setMotivacionTemp(String motivacionTemp) {
        this.motivacionTemp = motivacionTemp;
    }

    public String getMotivacionTemp() {
        return motivacionTemp;
    }

    public void setMontoTemp(Long montoTemp) {
        this.montoTemp = montoTemp;
    }

    public Long getMontoTemp() {
        return montoTemp;
    }
    
     public Boolean getMuestraConfirmacion() {
        return muestraConfirmacion;
    }

    public void setMuestraConfirmacion(Boolean muestraConfirmacion) {
        this.muestraConfirmacion = muestraConfirmacion;
    }

    public void setDecsripcionMontoTemp(String decsripcionMontoTemp) {
        this.decsripcionMontoTemp = decsripcionMontoTemp;
    }

    public String getDecsripcionMontoTemp() {
        return decsripcionMontoTemp;
    }
}

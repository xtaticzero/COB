package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;


import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MotRechazoVigService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.MotRechazoVigModel;
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


@Controller("motRechazoVigMB")
@Scope("view")
public class MotRechazoVigManagedBean  extends AbstractBaseMB {
   
    
    @Autowired
    private MotRechazoVigService motRechazoVigService;

    private MotRechazoVigModel motRechazoVigModel = new MotRechazoVigModel();

    private String nombreTemp;
    private String descripcionTemp;

    
    
    public MotRechazoVigManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_MTVORECHAZO,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                motRechazoVigModel.setListMotRechazoVig(motRechazoVigService.todosLosMotivos());
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
        MotRechazoVig motRechazoVig = new MotRechazoVig();
       
        motRechazoVig.setNombre(motRechazoVigModel.getNombre());
        motRechazoVig.setDescripcion(motRechazoVigModel.getDescripcion());
        motRechazoVig.setOrden(ConstantsCatalogos.ORDEN);
        Calendar cal = Calendar.getInstance();
        motRechazoVig.setFechaInicio(cal.getTime());

        Integer reg = motRechazoVigService.buscaMotivoPorIdYNom(motRechazoVig);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), 
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_MTVORECHAZO, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.ALTA_MOV_MTVORECHAZO, 
                    ConstantsCatalogos.ALTA_MTVORECHAZO_OBS);
            motRechazoVigService.agregaMotivo(motRechazoVig, dto);
            motRechazoVigModel.setListMotRechazoVig(motRechazoVigService.todosLosMotivos());
            
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
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_MTVORECHAZO, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.MODIFICACION_MOV_MTVORECHAZO, 
                    ConstantsCatalogos.MODIFICACION_MTVORECHAZO_OBS);
            motRechazoVigService.editaMotivo(motRechazoVigModel.getMotRechazoVigEdit(), dto);
            motRechazoVigModel.setListMotRechazoVig(motRechazoVigService.todosLosMotivos());
           
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
        motRechazoVigModel.getMotRechazoVigEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), 
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_MTVORECHAZO, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.BAJA_MOV_MTVORECHAZO, 
                    ConstantsCatalogos.BAJA_MTVORECHAZO_OBS);
            motRechazoVigService.eliminaMotivo(motRechazoVigModel.getMotRechazoVigEli(), dto);
            motRechazoVigModel.setListMotRechazoVig(motRechazoVigService.todosLosMotivos());
           
            this.cerrar();
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
        if (motRechazoVigModel.getListMotRechazoVigTmp() != null) {
                excel = motRechazoVigService.generaExcel(motRechazoVigModel.getListMotRechazoVigTmp()).toByteArray();
            } else {
                excel = motRechazoVigService.generaExcel(motRechazoVigModel.getListMotRechazoVig()).toByteArray();
            }
                despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
        }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (motRechazoVigModel.getListMotRechazoVigTmp() != null) {
                pdf = motRechazoVigService.generaPDF(motRechazoVigModel.getListMotRechazoVigTmp()).toByteArray();
            } else {
                pdf = motRechazoVigService.generaPDF(motRechazoVigModel.getListMotRechazoVig()).toByteArray();
            }
            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
        }

    /**
     *
     */
    public void abrirNuevo() {
        this.getMotRechazoVigModel().setTblVisible(false);
        this.getMotRechazoVigModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        
        setNombreTemp(motRechazoVigModel.getMotRechazoVigEdit().getNombre());
        setDescripcionTemp(motRechazoVigModel.getMotRechazoVigEdit().getDescripcion());
        this.getMotRechazoVigModel().setTblVisible(false);
        this.getMotRechazoVigModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        this.getMotRechazoVigModel().setTblVisible(false);
        this.getMotRechazoVigModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getMotRechazoVigModel().setTblVisible(true);
        this.getMotRechazoVigModel().setNvoVisible(false);
        this.getMotRechazoVigModel().setEdtVisible(false);
        this.getMotRechazoVigModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(nombreTemp!=null){
             motRechazoVigModel.getMotRechazoVigEdit().setNombre(getNombreTemp());
        }
        if(descripcionTemp!=null){
           motRechazoVigModel.getMotRechazoVigEdit().setDescripcion(getDescripcionTemp()); 
        }
        
        getMotRechazoVigModel().setIdMotRechazoVig(null);
        getMotRechazoVigModel().setNombre("");
        getMotRechazoVigModel().setDescripcion("");
        getMotRechazoVigModel().setOrden(null);

    }

  

    public void setNombreTemp(String nombreTemp) {
        this.nombreTemp = nombreTemp;
    }

    public String getNombreTemp() {
        return nombreTemp;
    }

    public void setDescripcionTemp(String descripcionTemp) {
        this.descripcionTemp = descripcionTemp;
    }

    public String getDescripcionTemp() {
        return descripcionTemp;
    }


    public void setMotRechazoVigService(MotRechazoVigService motRechazoVigService) {
        this.motRechazoVigService = motRechazoVigService;
    }

    public MotRechazoVigService getMotRechazoVigService() {
        return motRechazoVigService;
    }

    public void setMotRechazoVigModel(MotRechazoVigModel motRechazoVigModel) {
        this.motRechazoVigModel = motRechazoVigModel;
    }

    public MotRechazoVigModel getMotRechazoVigModel() {
        return motRechazoVigModel;
    }
}



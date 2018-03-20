package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MtvoCancelDocService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.MtvoCancelDocModel;
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


@Controller("catalogoMotivoMB")
@Scope("view")
public class MtvoCancelDocManagedBean extends AbstractBaseMB {

    @Autowired
    private MtvoCancelDocService mtvoCancelDocService;

    private MtvoCancelDocModel mtvoCancelDocModel = new MtvoCancelDocModel();

    private String nombreTemp;
    private String descripcionTemp;

    /**
     *
     */
    public MtvoCancelDocManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_MTVOCANCELDOC,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                mtvoCancelDocModel.setListMtvoCancelDoc(mtvoCancelDocService.todosLosMotivos());
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
        MtvoCancelDoc mtvoCancelDoc = new MtvoCancelDoc();

        mtvoCancelDoc.setIdMotivoCancelacion(mtvoCancelDocModel.getIdMotivoCancelacion());
        mtvoCancelDoc.setNombre(mtvoCancelDocModel.getNombre());
        mtvoCancelDoc.setDescripcion(mtvoCancelDocModel.getDescripcion());
        mtvoCancelDoc.setOrden(ConstantsCatalogos.ORDEN);
        Calendar cal = Calendar.getInstance();
        mtvoCancelDoc.setFechaInicio(cal.getTime());

        Integer reg = mtvoCancelDocService.buscaMotivoPorIdYNom(mtvoCancelDoc);
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
                    ConstantsCatalogos.IDENTIFICADOR_MTVOCANCELDOC, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.ALTA_MOV_MTVOCANCELDOC, 
                    ConstantsCatalogos.ALTA_MTVOCANCELDOC_OBS);
            mtvoCancelDocService.agregaMotivo(mtvoCancelDoc, dto);
            mtvoCancelDocModel.setListMtvoCancelDoc(mtvoCancelDocService.todosLosMotivos());
            
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
                    ConstantsCatalogos.IDENTIFICADOR_MTVOCANCELDOC, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.MODIFICACION_MOV_MTVOCANCELDOC, 
                    ConstantsCatalogos.MODIFICACION_MTVOCANCELDOC_OBS);
            mtvoCancelDocService.editaMotivo(mtvoCancelDocModel.getMtvoCancelDocEdit(), dto);
            mtvoCancelDocModel.setListMtvoCancelDoc(mtvoCancelDocService.todosLosMotivos());
           
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
        mtvoCancelDocModel.getMtvoCancelDocEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), 
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_MTVOCANCELDOC, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.BAJA_MOV_MTVOCANCELDOC, 
                    ConstantsCatalogos.BAJA_MTVOCANCELDOC_OBS);
            mtvoCancelDocService.eliminaMotivo(mtvoCancelDocModel.getMtvoCancelDocEli(), dto);
            mtvoCancelDocModel.setListMtvoCancelDoc(mtvoCancelDocService.todosLosMotivos());
           
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
        if (mtvoCancelDocModel.getListMtvoCancelDocTmp() != null) {
                excel = mtvoCancelDocService.generaExcel(mtvoCancelDocModel.getListMtvoCancelDocTmp()).toByteArray();
            } else {
                excel = mtvoCancelDocService.generaExcel(mtvoCancelDocModel.getListMtvoCancelDoc()).toByteArray();
            }
                despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
        }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (mtvoCancelDocModel.getListMtvoCancelDocTmp() != null) {
                pdf = mtvoCancelDocService.generaPDF(mtvoCancelDocModel.getListMtvoCancelDocTmp()).toByteArray();
            } else {
                pdf = mtvoCancelDocService.generaPDF(mtvoCancelDocModel.getListMtvoCancelDoc()).toByteArray();
            }
            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
        }

    /**
     *
     */
    public void abrirNuevo() {
        this.getMtvoCancelDocModel().setTblVisible(false);
        this.getMtvoCancelDocModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        
        setNombreTemp(mtvoCancelDocModel.getMtvoCancelDocEdit().getNombre());
        setDescripcionTemp(mtvoCancelDocModel.getMtvoCancelDocEdit().getDescripcion());
        this.getMtvoCancelDocModel().setTblVisible(false);
        this.getMtvoCancelDocModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        this.getMtvoCancelDocModel().setTblVisible(false);
        this.getMtvoCancelDocModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getMtvoCancelDocModel().setTblVisible(true);
        this.getMtvoCancelDocModel().setNvoVisible(false);
        this.getMtvoCancelDocModel().setEdtVisible(false);
        this.getMtvoCancelDocModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(nombreTemp!=null){
             mtvoCancelDocModel.getMtvoCancelDocEdit().setNombre(getNombreTemp());
        }
        if(descripcionTemp!=null){
           mtvoCancelDocModel.getMtvoCancelDocEdit().setDescripcion(getDescripcionTemp()); 
        }
        
        getMtvoCancelDocModel().setIdMotivoCancelacion(null);
        getMtvoCancelDocModel().setNombre("");
        getMtvoCancelDocModel().setDescripcion("");
        getMtvoCancelDocModel().setOrden(null);

    }

    /**
     *
     * @param mtvoCancelDocService
     */
    public void setMtvoCancelDocService(MtvoCancelDocService mtvoCancelDocService) {
        this.mtvoCancelDocService = mtvoCancelDocService;
    }

    /**
     *
     * @return
     */
    public MtvoCancelDocService getMtvoCancelDocService() {
        return mtvoCancelDocService;
    }

    /**
     *
     * @param mtvoCancelDocModel
     */
    public void setMtvoCancelDocModel(MtvoCancelDocModel mtvoCancelDocModel) {
        this.mtvoCancelDocModel = mtvoCancelDocModel;
    }

    /**
     *
     * @return
     */
    public MtvoCancelDocModel getMtvoCancelDocModel() {
        return mtvoCancelDocModel;
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
}

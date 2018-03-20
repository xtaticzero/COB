package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.AdmonLocalService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.AdmonLocalModel;
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

@Controller("catalogoAdmonLocalMB")
@Scope("view")
public class AdmonLocalManagedBean extends AbstractBaseMB {
    
    @Autowired
    private AdmonLocalService admonLocalService;
    private AdmonLocalModel admonLocalModel = new AdmonLocalModel();
    private String nombreTemp;
    private String descripcionTemp;
    
    public AdmonLocalManagedBean() {
        super();
    }
    
    /**
     *
     */
    @PostConstruct
    public void init() {
        try {
            super.obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(),
                    ConstantsCatalogos.IDENTIFICADOR_ADMON_LOCAL,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                admonLocalModel.setListAdmonLocal(admonLocalService.todasLasAdmon());
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
        AdmonLocal admonLocal = new AdmonLocal();
        
        admonLocal.setIdAdmonLocal(admonLocalModel.getIdAdmonLocal());
        admonLocal.setNombre(admonLocalModel.getNombre());
        admonLocal.setDescripcion(admonLocalModel.getDescripcion());
        admonLocal.setOrden(ConstantsCatalogos.ORDEN);
        
        Calendar cal = Calendar.getInstance();
        admonLocal.setFechaInicio(cal.getTime());

        Integer reg = admonLocalService.buscaAdmonPorId(admonLocal);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_ADMON_LOCAL, new Date(), new Date(), ConstantsCatalogos.ALTA_MOV_ADMONLOCAL, ConstantsCatalogos.ALTA_ADMONLOCAL_OBS);
            admonLocalService.agregaAdmon(admonLocal, dto);

            admonLocalModel.setListAdmonLocal(admonLocalService.todasLasAdmon());
            
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
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_ADMON_LOCAL, new Date(), new Date(), ConstantsCatalogos.MODIFICACION_MOV_ADMONLOCAL, ConstantsCatalogos.MODIFICACION_ADMONLOCAL_OBS);
            admonLocalService.editaAdmon(admonLocalModel.getAdmonLocalEdit(), dto);
            admonLocalModel.setListAdmonLocal(admonLocalService.todasLasAdmon());
            
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
    public void reactiva() {

        try {
            admonLocalService.reactivaAdmon(admonLocalModel.getAdmonLocalEdit());
            admonLocalModel.setListAdmonLocal(admonLocalService.todasLasAdmon());
           
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROHABILITADO);
        } catch (SeguimientoDAOException e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     *
     */
    public void elimina() {
        Calendar cal = Calendar.getInstance();
        admonLocalModel.getAdmonLocalEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_ADMON_LOCAL, new Date(), new Date(), ConstantsCatalogos.BAJA_MOV_ADMONLOCAL, ConstantsCatalogos.BAJA_ADMONLOCAL_OBS);
            admonLocalService.eliminaAdmon(admonLocalModel.getAdmonLocalEli(), dto);
            admonLocalModel.setListAdmonLocal(admonLocalService.todasLasAdmon());
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
        if (admonLocalModel.getListAdmonLocalTmp() != null) {
                excel = admonLocalService.generaExcel(admonLocalModel.getListAdmonLocalTmp()).toByteArray();
            } else {
                excel = admonLocalService.generaExcel(admonLocalModel.getListAdmonLocal()).toByteArray();
            }
            despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);

        }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (admonLocalModel.getListAdmonLocalTmp() != null) {
                pdf = admonLocalService.generaPDF(admonLocalModel.getListAdmonLocalTmp()).toByteArray();
            } else {
                pdf = admonLocalService.generaPDF(admonLocalModel.getListAdmonLocal()).toByteArray();
            }
            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
    }

    /**
     *
     */
    public void abrirNuevo() {
        this.getAdmonLocalModel().setTblVisible(false);
        this.getAdmonLocalModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        setNombreTemp(admonLocalModel.getAdmonLocalEdit().getNombre());
        setDescripcionTemp(admonLocalModel.getAdmonLocalEdit().getDescripcion());
        admonLocalModel.setFechaFinStr(admonLocalModel.getAdmonLocalEdit().getFechaFinStr());
        this.getAdmonLocalModel().setTblVisible(false);
        this.getAdmonLocalModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {

        this.getAdmonLocalModel().setTblVisible(false);
        this.getAdmonLocalModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getAdmonLocalModel().setTblVisible(true);
        this.getAdmonLocalModel().setNvoVisible(false);
        this.getAdmonLocalModel().setEdtVisible(false);
        this.getAdmonLocalModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(nombreTemp!=null){
            admonLocalModel.getAdmonLocalEdit().setNombre(getNombreTemp());
        }
        if(descripcionTemp!=null){
            admonLocalModel.getAdmonLocalEdit().setDescripcion(getDescripcionTemp());
        }
        getAdmonLocalModel().setIdAdmonLocal("");
        getAdmonLocalModel().setNombre("");
        getAdmonLocalModel().setDescripcion("");
    }

    public void setAdmonLocalService(AdmonLocalService admonLocalService) {
        this.admonLocalService = admonLocalService;
    }

    public AdmonLocalService getAdmonLocalService() {
        return admonLocalService;
    }

    public void setAdmonLocalModel(AdmonLocalModel admonLocalModel) {
        this.admonLocalModel = admonLocalModel;
    }

    public AdmonLocalModel getAdmonLocalModel() {
        return admonLocalModel;
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

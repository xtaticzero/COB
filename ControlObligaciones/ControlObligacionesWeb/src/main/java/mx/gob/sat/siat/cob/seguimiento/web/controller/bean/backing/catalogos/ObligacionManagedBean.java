package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.ObligacionService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.ObligacionModel;
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


@Controller("catalogoObligacionMB")
@Scope("view")
public class ObligacionManagedBean extends AbstractBaseMB {

    @Autowired
    private ObligacionService obligacionService;

    private ObligacionModel obligacionModel = new ObligacionModel();
    private String descripcionTemp;

    /**
     *
     */
    public ObligacionManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_OBLIGACION,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                obligacionModel.setListObligacion(obligacionService.todasLasObligaciones());
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
        Obligacion obligacion = new Obligacion();
        ValorBooleano valorBooleano = new ValorBooleano();

        obligacion.setIdObligacion(obligacionModel.getIdObligacionSan());
        obligacion.setConcepto(obligacionModel.getConcepto());
        obligacion.setDescripcion(obligacionModel.getDescripcion());
        valorBooleano.setIdValorBooleano(obligacionModel.getIdValorBooleano());
        obligacion.setValorBooleano(valorBooleano);
        obligacion.setOrden(ConstantsCatalogos.ORDEN);
        Calendar cal = Calendar.getInstance();
        obligacion.setFechaInicio(cal.getTime());

        obligacion.setValorBooleano(valorBooleano);

        Integer reg = obligacionService.buscaObligacionPorConcYDesc(obligacion);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_OBLIGACION, new Date(), new Date(), ConstantsCatalogos.ALTA_MOV_OBLIGACION, ConstantsCatalogos.ALTA_OBLIGACION_OBS);
            obligacionService.agregaObligacion(obligacion, dto);
            obligacionModel.setListObligacion(obligacionService.todasLasObligaciones());
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEXITOSO);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e);
        } catch (SeguimientoDAOException e) {
            getLogger().error(e);
        } catch (DaoException e) {
            getLogger().error(e);
        }
    }

    /**
     *
     */
    public void edita() {
        ValorBooleano valorBooleano = new ValorBooleano();
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA, 
                    ConstantsCatalogos.IDENTIFICADOR_OBLIGACION, 
                    new Date(), new Date(), 
                    ConstantsCatalogos.MODIFICACION_MOV_OBLIGACION, 
                    ConstantsCatalogos.MODIFICACION_OBLIGACION_OBS);
            valorBooleano.setIdValorBooleano(obligacionModel.getIdValorBooleano());
            obligacionModel.getObligacionEdit().setValorBooleano(valorBooleano);
            obligacionModel.getObligacionEdit().setIdObligacion(obligacionModel.getIdObligacionSan());
            obligacionService.editaObligacion(obligacionModel.getObligacionEdit(), dto);
            obligacionModel.setListObligacion(obligacionService.todasLasObligaciones());
            cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROEDITADO);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e);
        } catch (SeguimientoDAOException e) {
            getLogger().error(e);
        } catch (DaoException e) {
            getLogger().error(e);
        }
    }
    
    /**
     *
     */
    public void reactiva() {

        try {
            obligacionService.reactivaObligacion(obligacionModel.getObligacionEdit());
            obligacionModel.setListObligacion(obligacionService.todasLasObligaciones());
           
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
        obligacionModel.getObligacionEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_OBLIGACION, new Date(), new Date(), ConstantsCatalogos.BAJA_MOV_OBLIGACION, ConstantsCatalogos.BAJA_OBLIGACION_OBS);
            obligacionService.eliminaObligacion(obligacionModel.getObligacionEli(), dto);
            obligacionModel.setListObligacion(obligacionService.todasLasObligaciones());
            this.cerrar();
            AbstractBaseMB.msgInfo(ConstantsCatalogos.REGISTROELIMINADO);
        } catch (AccesoDenegadoException e) {
            getLogger().error(e);
        } catch (SeguimientoDAOException e) {
            getLogger().error(e);
        } catch (DaoException e) {
            getLogger().error(e);
        }
    }

    /**
     *
     */
    public void cargaConcepto() {
              
       obligacionModel.setConcepto( obligacionService.obtenerConceptoImpuesto(obligacionModel.getIdObligacionSan()));

    }

    /**
     *
     */
    public void generaExcel() {
        
        byte[] excel;
       
            if (obligacionModel.getListObligacionTmp() != null) {
                excel = obligacionService.generaExcel(obligacionModel.getListObligacionTmp()).toByteArray();
            } else {
                excel = obligacionService.generaExcel(obligacionModel.getListObligacion()).toByteArray();
            }

            despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);

    }

    /**
     *
     */
    public void generaPdf() {
   
        byte[] pdf;
        
            if (obligacionModel.getListObligacionTmp() != null) {
                pdf = obligacionService.generaPDF(obligacionModel.getListObligacionTmp()).toByteArray();
            } else {
                pdf = obligacionService.generaPDF(obligacionModel.getListObligacion()).toByteArray();
            }

            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);

    }

    /**
     *
     */
    public void abrirNuevo() {
        obligacionModel.setListaComboObligacion(obligacionService.obtenerListaComboObligancion());
        obligacionModel.setListValorBooleano(obligacionService.obtenerTodosLosValoresBool());
        obligacionModel.setConcepto(null);
        getObligacionModel().setIdValorBooleano(null);
        getObligacionModel().setIdObligacionSan(null);
        getObligacionModel().setTblVisible(false);
        getObligacionModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        obligacionModel.setFechaFinStr(obligacionModel.getObligacionEdit().getFechaFinStr());
        setDescripcionTemp(obligacionModel.getObligacionEdit().getDescripcion());
        obligacionModel.setListaComboObligacion(obligacionService.obtenerListaComboObligancion());
        obligacionModel.setListValorBooleano(obligacionService.obtenerTodosLosValoresBool());
        obligacionModel.setIdValorBooleano(obligacionModel.getObligacionEdit().getValorBooleano().getIdValorBooleano());
        obligacionModel.setIdObligacionSan(obligacionModel.getObligacionEdit().getIdObligacion());
        this.getObligacionModel().setTblVisible(false);
        this.getObligacionModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        obligacionModel.setListaComboObligacion(obligacionService.obtenerListaComboObligancion());
        obligacionModel.setListValorBooleano(obligacionService.obtenerTodosLosValoresBool());
        obligacionModel.setIdValorBooleano(obligacionModel.getObligacionEli().getValorBooleano().getIdValorBooleano());
        obligacionModel.setIdObligacionSan(obligacionModel.getObligacionEli().getIdObligacion());
        this.getObligacionModel().setTblVisible(false);
        this.getObligacionModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getObligacionModel().setTblVisible(true);
        this.getObligacionModel().setNvoVisible(false);
        this.getObligacionModel().setEdtVisible(false);
        this.getObligacionModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(descripcionTemp!=null){
            obligacionModel.getObligacionEdit().setDescripcion(getDescripcionTemp());
        }    
      
        getObligacionModel().setConcepto("");
        getObligacionModel().setDescripcion("");
        getObligacionModel().setIdValorBooleano(null);
        getObligacionModel().setIdObligacionSan(null);
      }

    /**
     *
     * @param obligacionService
     */
    public void setObligacionService(ObligacionService obligacionService) {
        this.obligacionService = obligacionService;
    }

    /**
     *
     * @return
     */
    public ObligacionService getObligacionService() {
        return obligacionService;
    }

    /**
     *
     * @param obligacionModel
     */
    public void setObligacionModel(ObligacionModel obligacionModel) {
        this.obligacionModel = obligacionModel;
    }

    /**
     *
     * @return
     */
    public ObligacionModel getObligacionModel() {
        return obligacionModel;
    }

    public void setDescripcionTemp(String descripcionTemp) {
        this.descripcionTemp = descripcionTemp;
    }

    public String getDescripcionTemp() {
        return descripcionTemp;
    }
}

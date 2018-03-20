package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.EjercicioFiscalService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.EjercicioFiscalModel;
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


@Controller("catalogoEjercicioFisMB")
@Scope("view")
public class EjercicioFiscalManagedBean extends AbstractBaseMB {

    @Autowired
    private EjercicioFiscalService ejercicioFiscalService;
    private EjercicioFiscalModel ejercicioFiscalModel = new EjercicioFiscalModel();
    private String nombreTemp;
    private String descripcionTemp;

    /**
     *
     */
    public EjercicioFiscalManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_EJERCICIOFISCAL,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                ejercicioFiscalModel.setListEjercicioFiscal(ejercicioFiscalService.todosLosEjercicios());
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
        EjercicioFiscal ejercicioFiscal = new EjercicioFiscal();

        ejercicioFiscal.setIdEjercicioFiscal(ejercicioFiscalModel.getIdEjercicioFiscal());
        ejercicioFiscal.setNombre(ejercicioFiscalModel.getNombre());
        ejercicioFiscal.setDescripcion(ejercicioFiscalModel.getDescripcion());
        ejercicioFiscal.setOrden(ConstantsCatalogos.ORDEN);
        Calendar cal = Calendar.getInstance();
        ejercicioFiscal.setFechaInicio(cal.getTime());

        getLogger().error("buscando ejercicio duplicado");
        Integer reg = ejercicioFiscalService.buscaEjercicioPorNomYDesc(ejercicioFiscal);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_EJERCICIOFISCAL, new Date(), new Date(), ConstantsCatalogos.ALTA_MOV_EJERCICIOFISCAL, ConstantsCatalogos.ALTA_EJERCICIOFISCAL_OBS);
            ejercicioFiscalService.agregaEjercicio(ejercicioFiscal, dto);

            ejercicioFiscalModel.setListEjercicioFiscal(ejercicioFiscalService.todosLosEjercicios());
            
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
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_EJERCICIOFISCAL, new Date(), new Date(), ConstantsCatalogos.MODIFICACION_MOV_EJERCICIOFISCAL, ConstantsCatalogos.MODIFICACION_EJERCICIOFISCAL_OBS);
            ejercicioFiscalService.editaEjercicio(ejercicioFiscalModel.getEjercicioFiscalEdit(), dto);
            ejercicioFiscalModel.setListEjercicioFiscal(ejercicioFiscalService.todosLosEjercicios());
            
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
            ejercicioFiscalService.reactivaEjercicio(ejercicioFiscalModel.getEjercicioFiscalEdit());
            ejercicioFiscalModel.setListEjercicioFiscal(ejercicioFiscalService.todosLosEjercicios());
           
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
        ejercicioFiscalModel.getEjercicioFiscalEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_EJERCICIOFISCAL, new Date(), new Date(), ConstantsCatalogos.BAJA_MOV_EJERCICIOFISCAL, ConstantsCatalogos.BAJA_EJERCICIOFISCAL_OBS);
            ejercicioFiscalService.eliminaEjercicio(ejercicioFiscalModel.getEjercicioFiscalEli(), dto);
            ejercicioFiscalModel.setListEjercicioFiscal(ejercicioFiscalService.todosLosEjercicios());
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
        if (ejercicioFiscalModel.getListEjercicioFiscalTmp() != null) {
                excel = ejercicioFiscalService.generaExcel(ejercicioFiscalModel.getListEjercicioFiscalTmp()).toByteArray();
            } else {
                excel = ejercicioFiscalService.generaExcel(ejercicioFiscalModel.getListEjercicioFiscal()).toByteArray();
            }
            despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);

        }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (ejercicioFiscalModel.getListEjercicioFiscalTmp() != null) {
                pdf = ejercicioFiscalService.generaPDF(ejercicioFiscalModel.getListEjercicioFiscalTmp()).toByteArray();
            } else {
                pdf = ejercicioFiscalService.generaPDF(ejercicioFiscalModel.getListEjercicioFiscal()).toByteArray();
            }
            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
    }

    /**
     *
     */
    public void abrirNuevo() {
        this.getEjercicioFiscalModel().setTblVisible(false);
        this.getEjercicioFiscalModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        setNombreTemp(ejercicioFiscalModel.getEjercicioFiscalEdit().getNombre());
        setDescripcionTemp(ejercicioFiscalModel.getEjercicioFiscalEdit().getDescripcion());
        ejercicioFiscalModel.setFechaFinStr(ejercicioFiscalModel.getEjercicioFiscalEdit().getFechaFinStr());
        this.getEjercicioFiscalModel().setTblVisible(false);
        this.getEjercicioFiscalModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {

        this.getEjercicioFiscalModel().setTblVisible(false);
        this.getEjercicioFiscalModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getEjercicioFiscalModel().setTblVisible(true);
        this.getEjercicioFiscalModel().setNvoVisible(false);
        this.getEjercicioFiscalModel().setEdtVisible(false);
        this.getEjercicioFiscalModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(nombreTemp!=null){
            ejercicioFiscalModel.getEjercicioFiscalEdit().setNombre(getNombreTemp());
        }
        if(descripcionTemp!=null){
            ejercicioFiscalModel.getEjercicioFiscalEdit().setDescripcion(getDescripcionTemp());
        }
        getEjercicioFiscalModel().setIdEjercicioFiscal(null);
        getEjercicioFiscalModel().setNombre("");
        getEjercicioFiscalModel().setDescripcion("");
        getEjercicioFiscalModel().setOrden(null);

    }

    /**
     *
     * @param ejercicioFiscalService
     */
    public void setEjercicioFiscalService(EjercicioFiscalService ejercicioFiscalService) {
        this.ejercicioFiscalService = ejercicioFiscalService;
    }

    /**
     *
     * @return
     */
    public EjercicioFiscalService getEjercicioFiscalService() {
        return ejercicioFiscalService;
    }

    /**
     *
     * @param ejercicioFiscalModel
     */
    public void setEjercicioFiscalModel(EjercicioFiscalModel ejercicioFiscalModel) {
        this.ejercicioFiscalModel = ejercicioFiscalModel;
    }

    /**
     *
     * @return
     */
    public EjercicioFiscalModel getEjercicioFiscalModel() {
        return ejercicioFiscalModel;
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

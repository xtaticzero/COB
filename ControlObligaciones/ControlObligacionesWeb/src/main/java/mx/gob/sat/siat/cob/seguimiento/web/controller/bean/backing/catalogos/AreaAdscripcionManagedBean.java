package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.AreaAdscripcionService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.AreaAdscripcionModel;
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

@Controller("catalogoAdscripcionMB")
@Scope("view")
public class AreaAdscripcionManagedBean extends AbstractBaseMB {
    
    @Autowired
    private AreaAdscripcionService areaAdscripcionService;
    private AreaAdscripcionModel areaAdscripcionModel = new AreaAdscripcionModel();
    private String nombreTemp;
    private String descripcionTemp;
    
    public AreaAdscripcionManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_AREA_ADSCRIPCION,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                areaAdscripcionModel.setListAreaAdscripcion(areaAdscripcionService.todasLasAreas());
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
        AreaAdscripcion areaAdscripcion = new AreaAdscripcion();

        areaAdscripcion.setNombre(areaAdscripcionModel.getNombre());
        areaAdscripcion.setDescripcion(areaAdscripcionModel.getDescripcion());
        areaAdscripcion.setOrden(ConstantsCatalogos.ORDEN);
        Calendar cal = Calendar.getInstance();
        areaAdscripcion.setFechaInicio(cal.getTime());

        getLogger().error("buscando area duplicado");
        Integer reg = areaAdscripcionService.buscaAreaPorId(areaAdscripcion);
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_AREA_ADSCRIPCION, new Date(), new Date(), ConstantsCatalogos.ALTA_MOV_AREAADSCRIPCION, ConstantsCatalogos.ALTA_AREAADSCRIPCION_OBS);
            areaAdscripcionService.agregaArea(areaAdscripcion, dto);

            areaAdscripcionModel.setListAreaAdscripcion(areaAdscripcionService.todasLasAreas());
            
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
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_AREA_ADSCRIPCION, new Date(), new Date(), ConstantsCatalogos.MODIFICACION_MOV_AREAADSCRIPCION, ConstantsCatalogos.MODIFICACION_AREAADSCRIPCION_OBS);
            areaAdscripcionService.editaArea(areaAdscripcionModel.getAreaAdscripcionEdit(), dto);
            areaAdscripcionModel.setListAreaAdscripcion(areaAdscripcionService.todasLasAreas());
            
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
            areaAdscripcionService.reactivaArea(areaAdscripcionModel.getAreaAdscripcionEdit());
            areaAdscripcionModel.setListAreaAdscripcion(areaAdscripcionService.todasLasAreas());
           
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
        areaAdscripcionModel.getAreaAdscripcionEli().setFechaFin(cal.getTime());

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_AREA_ADSCRIPCION, new Date(), new Date(), ConstantsCatalogos.BAJA_MOV_AREAADSCRIPCION, ConstantsCatalogos.BAJA_AREAADSCRIPCION_OBS);
            areaAdscripcionService.eliminaArea(areaAdscripcionModel.getAreaAdscripcionEli(), dto);
            areaAdscripcionModel.setListAreaAdscripcion(areaAdscripcionService.todasLasAreas());
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
        if (areaAdscripcionModel.getListAreaAdscripcionTmp() != null) {
                excel = areaAdscripcionService.generaExcel(areaAdscripcionModel.getListAreaAdscripcionTmp()).toByteArray();
            } else {
                excel = areaAdscripcionService.generaExcel(areaAdscripcionModel.getListAreaAdscripcion()).toByteArray();
            }
            despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);

        }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (areaAdscripcionModel.getListAreaAdscripcionTmp() != null) {
                pdf = areaAdscripcionService.generaPDF(areaAdscripcionModel.getListAreaAdscripcionTmp()).toByteArray();
            } else {
                pdf = areaAdscripcionService.generaPDF(areaAdscripcionModel.getListAreaAdscripcion()).toByteArray();
            }
            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
    }

    /**
     *
     */
    public void abrirNuevo() {
        this.getAreaAdscripcionModel().setTblVisible(false);
        this.getAreaAdscripcionModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        setNombreTemp(areaAdscripcionModel.getAreaAdscripcionEdit().getNombre());
        setDescripcionTemp(areaAdscripcionModel.getAreaAdscripcionEdit().getDescripcion());
        areaAdscripcionModel.setFechaFinStr(areaAdscripcionModel.getAreaAdscripcionEdit().getFechaFinStr());
        this.getAreaAdscripcionModel().setTblVisible(false);
        this.getAreaAdscripcionModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {

        this.getAreaAdscripcionModel().setTblVisible(false);
        this.getAreaAdscripcionModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getAreaAdscripcionModel().setTblVisible(true);
        this.getAreaAdscripcionModel().setNvoVisible(false);
        this.getAreaAdscripcionModel().setEdtVisible(false);
        this.getAreaAdscripcionModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(nombreTemp!=null){
            areaAdscripcionModel.getAreaAdscripcionEdit().setNombre(getNombreTemp());
        }
        if(descripcionTemp!=null){
            areaAdscripcionModel.getAreaAdscripcionEdit().setDescripcion(getDescripcionTemp());
        }
        getAreaAdscripcionModel().setIdAreaAdscripcion(null);
        getAreaAdscripcionModel().setNombre("");
        getAreaAdscripcionModel().setDescripcion("");
    }

    public void setAreaAdscripcionService(AreaAdscripcionService areaAdscripcionService) {
        this.areaAdscripcionService = areaAdscripcionService;
    }

    public AreaAdscripcionService getAreaAdscripcionService() {
        return areaAdscripcionService;
    }

    public void setAreaAdscripcionModel(AreaAdscripcionModel areaAdscripcionModel) {
        this.areaAdscripcionModel = areaAdscripcionModel;
    }

    public AreaAdscripcionModel getAreaAdscripcionModel() {
        return areaAdscripcionModel;
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

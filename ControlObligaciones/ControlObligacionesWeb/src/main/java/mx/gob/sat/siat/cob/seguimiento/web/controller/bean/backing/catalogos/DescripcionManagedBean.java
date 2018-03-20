package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;


import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.DescripcionService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.DescripcionModel;
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

@Controller("descripcionMB")
@Scope("view")
public class DescripcionManagedBean extends AbstractBaseMB {

    @Autowired
    private DescripcionService descripcionService;
    private DescripcionModel descripcionModel = new DescripcionModel();
    private String descripcionTemp;

    /**
     *
     */
    public DescripcionManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_DESCRIPCION,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                descripcionModel.setListDescripcion(descripcionService.todasLasDescripciones());
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
        Descripcion descripcion = new Descripcion();

        descripcion.setDescripcion(descripcionModel.getDescripcion());
        descripcion.setOrden(ConstantsCatalogos.ORDEN);
        Calendar cal = Calendar.getInstance();
        descripcion.setFechaInicio(cal.getTime());

        Integer reg = descripcionService.buscaDescripcionPorIdYDes(descripcion);
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
                    ConstantsCatalogos.IDENTIFICADOR_DESCRIPCION,
                    new Date(), new Date(),
                    ConstantsCatalogos.ALTA_MOV_DESCRIPCION,
                    ConstantsCatalogos.ALTA_DESCRIPCION_OBS);
            descripcionService.agregaDescripcion(descripcion, dto);
            descripcionModel.setListDescripcion(descripcionService.todasLasDescripciones());

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

        Integer reg = descripcionService.buscaDescripcionPorIdYDes(descripcionModel.getDescripcionEdit());
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        if (reg > 0) {
            AbstractBaseMB.msgError(ConstantsCatalogos.REGISTRO_DUPLICADO);
            return;
        }
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_DESCRIPCION, new Date(), new Date(), ConstantsCatalogos.MODIFICACION_MOV_DESCRIPCION, ConstantsCatalogos.MODIFICACION_DESCRIPCION_OBS);
            descripcionService.editaDescripcion(descripcionModel.getDescripcionEdit(), dto);
            descripcionModel.setListDescripcion(descripcionService.todasLasDescripciones());

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
        descripcionModel.getDescripcionEli().setFechaFin(cal.getTime());
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_DESCRIPCION, new Date(), new Date(), ConstantsCatalogos.BAJA_MOV_DESCRIPCION, ConstantsCatalogos.BAJA_DESCRIPCION_OBS);
            descripcionService.eliminaDescripcion(descripcionModel.getDescripcionEli(), dto);
            descripcionModel.setListDescripcion(descripcionService.todasLasDescripciones());

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
        if (descripcionModel.getListDescripcionTmp() != null) {
            excel = descripcionService.generaExcel(descripcionModel.getListDescripcionTmp()).toByteArray();
        } else {
            excel = descripcionService.generaExcel(descripcionModel.getListDescripcion()).toByteArray();
        }
        despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
    }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (descripcionModel.getListDescripcionTmp() != null) {
            pdf = descripcionService.generaPDF(descripcionModel.getListDescripcionTmp()).toByteArray();
        } else {
            pdf = descripcionService.generaPDF(descripcionModel.getListDescripcion()).toByteArray();
        }
        despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
    }

    /**
     *
     */
    public void abrirNuevo() {
        this.getDescripcionModel().setTblVisible(false);
        this.getDescripcionModel().setNvoVisible(true);
    }

    /**
     *
     */
    public void abrirEditar() {
        setDescripcionTemp(descripcionModel.getDescripcionEdit().getDescripcion());
        this.getDescripcionModel().setTblVisible(false);
        this.getDescripcionModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void abrirEliminar() {
        this.getDescripcionModel().setTblVisible(false);
        this.getDescripcionModel().setElmVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getDescripcionModel().setTblVisible(true);
        this.getDescripcionModel().setNvoVisible(false);
        this.getDescripcionModel().setEdtVisible(false);
        this.getDescripcionModel().setElmVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if(descripcionTemp!=null){
            descripcionModel.getDescripcionEdit().setDescripcion(getDescripcionTemp());
        }
        getDescripcionModel().setIdDescripcion(null);
        getDescripcionModel().setDescripcion("");
        getDescripcionModel().setOrden(null);

    }

    public void setDescripcionService(DescripcionService descripcionService) {
        this.descripcionService = descripcionService;
    }

    public DescripcionService getDescripcionService() {
        return descripcionService;
    }

    public void setDescripcionModel(DescripcionModel descripcionModel) {
        this.descripcionModel = descripcionModel;
    }

    public DescripcionModel getDescripcionModel() {
        return descripcionModel;
    }

    public void setDescripcionTemp(String descripcionTemp) {
        this.descripcionTemp = descripcionTemp;
    }

    public String getDescripcionTemp() {
        return descripcionTemp;
    }
}

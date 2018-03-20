package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Date;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.EstadoAdmonLocalMATService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.EstadoAdmonLocalMATModel;
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

/**
 *
 * @author ulises
 */
@Controller("estadoAdmonLocalMATMB")
@Scope("view")
public class EstadoAdmonLocalMATManagedBean extends AbstractBaseMB {

    @Autowired
    private EstadoAdmonLocalMATService estadoAdmonLocalMATService;
    private EstadoAdmonLocalMATModel estadoAdmonLocalMATModel = new EstadoAdmonLocalMATModel();
    private String idAdmonLocalTemp;
    private Integer esOperacionMATTemp;
    private String entidadDescTemp;

    /**
     *
     */
    public EstadoAdmonLocalMATManagedBean() {
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
                    ConstantsCatalogos.IDENTIFICADOR_OPERACION_ADMONLOCAL_MAT,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                estadoAdmonLocalMATModel.setListEstadoAdmonLocalMAT(estadoAdmonLocalMATService.todosLosEstadosAdmonLocalMAT());
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
    public void edita() {
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_OPERACION_ADMONLOCAL_MAT,
                    new Date(), new Date(),
                    ConstantsCatalogos.MODIFICACION_MOV_OPERACION_MAT,
                    ConstantsCatalogos.MODIFICACION_OPERACIONMAT_OBS);

            estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().setIdAdmonLocal(estadoAdmonLocalMATModel.getIdAdmonLocal());
            estadoAdmonLocalMATService.editaEstadoAdmonLocalMAT(estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit(), dto);
            estadoAdmonLocalMATModel.setListEstadoAdmonLocalMAT(estadoAdmonLocalMATService.todosLosEstadosAdmonLocalMAT());

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
    public void generaExcel() {
        byte[] excel;
        if (estadoAdmonLocalMATModel.getListEstadoAdmonLocalMATTmp() != null) {
            excel = estadoAdmonLocalMATService.generaExcel(estadoAdmonLocalMATModel.getListEstadoAdmonLocalMATTmp()).toByteArray();
        } else {
            excel = estadoAdmonLocalMATService.generaExcel(estadoAdmonLocalMATModel.getListEstadoAdmonLocalMAT()).toByteArray();
        }
        despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);
    }

    /**
     *
     */
    public void generaPdf() {
        byte[] pdf;
        if (estadoAdmonLocalMATModel.getListEstadoAdmonLocalMATTmp() != null) {
            pdf = estadoAdmonLocalMATService.generaPDF(estadoAdmonLocalMATModel.getListEstadoAdmonLocalMATTmp()).toByteArray();
        } else {
            pdf = estadoAdmonLocalMATService.generaPDF(estadoAdmonLocalMATModel.getListEstadoAdmonLocalMAT()).toByteArray();
        }
        despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);
    }

    /**
     *
     */
    public void abrirEditar() {

        this.getEstadoAdmonLocalMATModel().setListaComboBoolean(estadoAdmonLocalMATService.obtenerComboEstado());
        this.getEstadoAdmonLocalMATModel().setIdAdmonLocal(estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().getIdAdmonLocal());
        setIdAdmonLocalTemp(estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().getIdAdmonLocal());
        setEntidadDescTemp(estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().getEntidadDesc());
        setEsOperacionMATTemp(estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().getEsOperacionMAT());
        this.getEstadoAdmonLocalMATModel().setTblVisible(false);
        this.getEstadoAdmonLocalMATModel().setEdtVisible(true);
    }

    /**
     *
     */
    public void cerrar() {
        this.getEstadoAdmonLocalMATModel().setTblVisible(true);
        this.getEstadoAdmonLocalMATModel().setEdtVisible(false);
        clean();
    }

    /**
     *
     */
    public void clean() {
        if (idAdmonLocalTemp != null) {
            estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().setIdAdmonLocal(getIdAdmonLocalTemp());
        }
        if (entidadDescTemp != null) {
            estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().setEntidadDesc(getEntidadDescTemp());
        }
        if (esOperacionMATTemp != null) {
            estadoAdmonLocalMATModel.getEstadoAdmonLocalMATEdit().setEsOperacionMAT(getEsOperacionMATTemp());
        }

        getEstadoAdmonLocalMATModel().setIdAdmonLocal("");
        getEstadoAdmonLocalMATModel().setEntidadDesc("");
        getEstadoAdmonLocalMATModel().setEsOperacionMAT(null);

    }

    public EstadoAdmonLocalMATService getEstadoAdmonLocalMATService() {
        return estadoAdmonLocalMATService;
    }

    public void setEstadoAdmonLocalMATService(EstadoAdmonLocalMATService estadoAdmonLocalMATService) {
        this.estadoAdmonLocalMATService = estadoAdmonLocalMATService;
    }

    public EstadoAdmonLocalMATModel getEstadoAdmonLocalMATModel() {
        return estadoAdmonLocalMATModel;
    }

    public void setEstadoAdmonLocalMATModel(EstadoAdmonLocalMATModel estadoAdmonLocalMATModel) {
        this.estadoAdmonLocalMATModel = estadoAdmonLocalMATModel;
    }

    public String getIdAdmonLocalTemp() {
        return idAdmonLocalTemp;
    }

    public void setIdAdmonLocalTemp(String idAdmonLocalTemp) {
        this.idAdmonLocalTemp = idAdmonLocalTemp;
    }

    public Integer getEsOperacionMATTemp() {
        return esOperacionMATTemp;
    }

    public void setEsOperacionMATTemp(Integer esOperacionMATTemp) {
        this.esOperacionMATTemp = esOperacionMATTemp;
    }

    public String getEntidadDescTemp() {
        return entidadDescTemp;
    }

    public void setEntidadDescTemp(String entidadDescTemp) {
        this.entidadDescTemp = entidadDescTemp;
    }
}
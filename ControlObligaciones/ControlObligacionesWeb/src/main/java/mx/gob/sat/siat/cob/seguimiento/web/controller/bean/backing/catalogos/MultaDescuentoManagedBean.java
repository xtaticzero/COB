package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MultaDescuentoService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model.MultaDescuentoModel;
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


@Controller("multaDescuentoManagedBean")
@Scope("view")
public class MultaDescuentoManagedBean extends AbstractBaseMB {

    @Autowired
    private MultaDescuentoService multaMontoService;
    private MultaDescuentoModel multaMontoModel = new MultaDescuentoModel();
    private Boolean muestraConfirmacion = false;

    public MultaDescuentoManagedBean() {
        super();
    }

    @PostConstruct
    public void init() {

        try {
            obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(),
                    ConstantsCatalogos.IDENTIFICADOR_MULTADESCUENTO,
                    ConstantsCatalogos.PARAMETRO_FIEL)) {
                MultaDescuento multa = new MultaDescuento();
                multaMontoModel.setListMultaMonto(multaMontoService.todasLasMultaMonto());
                multaMontoModel.setMultaMonto(multa);
            }
        } catch (SessionRolNullException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().debug(e);
        } catch (AccesoDenegadoException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().debug(e);
        } catch (AccesoDenegadoFielException e) {
            getSession().setAttribute("mensaje", e.getMessage());
            redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
            getLogger().debug(e);
        }

    }

    public void agrega() {

        Calendar cal = Calendar.getInstance();
        multaMontoModel.getMultaMonto().setFechaInicio(cal.getTime());
        multaMontoModel.getMultaMonto().setIdResolMotivo(multaMontoModel.getIdTipoMultaSel());
        multaMontoModel.getMultaMonto().setIdMultaDescuento(multaMontoModel.getIdMultaDescuentoSel());
        Integer reg = multaMontoService.buscarMultaMontoRepetida(multaMontoModel.getMultaMonto());
        if (reg == null) {
            AbstractBaseMB.msgError(ConstantsCatalogos.ERROR_CONSULTA_DB);
            return;
        }
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_MULTADESCUENTO, new Date(), new Date(), ConstantsCatalogos.ALTA_MOV_MULTADESCUENTO, ConstantsCatalogos.ALTA_MULTADESCUENTO_OBS);
            if (reg > 0) {
                multaMontoService.agregarMultaMonto(multaMontoModel.getMultaMonto(), dto, true);
            } else {
                multaMontoService.agregarMultaMonto(multaMontoModel.getMultaMonto(), dto, false);
            }

            multaMontoModel.setListMultaMonto(multaMontoService.todasLasMultaMonto());
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

    public void verificaRegistro() {
        multaMontoModel.getMultaMonto().setIdResolMotivo(multaMontoModel.getIdTipoMultaSel());
        Integer reg = multaMontoService.buscarMultaMontoRepetida(multaMontoModel.getMultaMonto());
        if (reg > 0) {
            muestraConfirmacion = true;

        } else {
            agrega();
        }
    }

    public void generaExcel() {
        
        byte[] excel;
        
            if (multaMontoModel.getListMultaMontoTmp() != null) {
                excel = multaMontoService.generaExcel(multaMontoModel.getListMultaMontoTmp()).toByteArray();
            } else {
                excel = multaMontoService.generaExcel(multaMontoModel.getListMultaMonto()).toByteArray();
            }

            despachaArchivo(excel, ConstantsCatalogos.TYPE_EXCEL, "tabla.xls", ConstantsCatalogos.ERROR_GENERA_EXCEL);

    }

    /**
     *
     */
    public void generaPdf() {
        
        byte[] pdf;
       
            if (multaMontoModel.getListMultaMontoTmp() != null) {
                pdf = multaMontoService.generaPDF(multaMontoModel.getListMultaMontoTmp()).toByteArray();
            } else {
                pdf = multaMontoService.generaPDF(multaMontoModel.getListMultaMonto()).toByteArray();
            }

            despachaArchivo(pdf, ConstantsCatalogos.TYPE_PDF, "tabla.pdf", ConstantsCatalogos.ERROR_GENERA_PDF);

    }

    public void abrirNuevo() {
        getMultaMontoModel().setListaComboTipoMulta(multaMontoService.obtenerComboResolMotivo());
        getMultaMontoModel().setListaComboMultaDescuento(multaMontoService.obtenerComboMultaDescuento());
        getMultaMontoModel().setIdMultaDescuentoSel(null);
        getMultaMontoModel().setIdTipoMultaSel(null);
        getMultaMontoModel().setTblVisible(false);
        getMultaMontoModel().setNvoVisible(true);

    }

    public void cerrar() {
        getMultaMontoModel().setTblVisible(true);
        getMultaMontoModel().setNvoVisible(false);
        muestraConfirmacion = false;
        clean();
    }

    public void clean() {
        getMultaMontoModel().setIdMultaDescuentoSel(null);
        getMultaMontoModel().setIdTipoMultaSel(null);

    }

    public void setMultaMontoService(MultaDescuentoService multaMontoService) {
        this.multaMontoService = multaMontoService;
    }

    public MultaDescuentoService getMultaMontoService() {
        return multaMontoService;
    }

    public void setMultaMontoModel(MultaDescuentoModel multaMontoModel) {
        this.multaMontoModel = multaMontoModel;
    }

    public MultaDescuentoModel getMultaMontoModel() {
        return multaMontoModel;
    }

    public void setMuestraConfirmacion(Boolean muestraConfirmacion) {
        this.muestraConfirmacion = muestraConfirmacion;
    }

    public Boolean getMuestraConfirmacion() {
        return muestraConfirmacion;
    }
}

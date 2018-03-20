package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos;

import java.util.Date;

import java.util.List;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.carga.CargaOmisosEntidadFederativaService;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.UsuarioEFService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;

import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("cargaOmisosEf")
@Scope(value = "view")
public class CargaOmisosEntidadFederativaManagedBean extends AbstractBaseMB {

    @Autowired
    private CargaOmisosEntidadFederativaService cargaOmisosEntidadFederativaService;

    private List<VigilanciaEntidadFederativa> vigilancias;
    private List<MotRechazoVig> motivosRechazo;

    @Autowired
    private UsuarioEFService usuarioEFService;

    private VigilanciaEntidadFederativa vigilanciaAceptada = new VigilanciaEntidadFederativa();

    private boolean mostrarPanelRechazar;
    private boolean mostrarPanelVigilancias;

    private String identificador;

    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        identificador = ConstantsCatalogos.IDENTIFICADOR_CARGA_OMISOS_EF;
        autorizar(identificador);
    }

    public void obtenerVigilanciasEF() {
        try {
            String rfc = "";
            if (getAccesoUsr() != null) {
                rfc = getAccesoUsr().getUsuario();
            }
            Long claveEF = usuarioEFService.obtenerUsuarioPorRfcCorto(rfc).get(0).getIdEntidadFederativa();
            vigilancias = cargaOmisosEntidadFederativaService.obtenerVigilancias(claveEF);
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        }
    }

    public void obtenerTodasVigilancias() {
        try {
            vigilancias = cargaOmisosEntidadFederativaService.obtenerVigilancias();
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        }
    }

    public void inicializarPaneles() {
        setMostrarPanelRechazar(false);
        setMostrarPanelVigilancias(true);
    }

    public StreamedContent getArchivoDescarga() {
        SegbMovimientoDTO dto = null;
        StreamedContent sc = null;
        try {
            dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_CARGA_OMISOS_EF,
                    new Date(), new Date(),
                    ConstantsCatalogos.DESCARGA_OMISOS_EF,
                    ConstantsCatalogos.DESCARGAR_ACEPTAR_VIGILANCIA_OMISOS_EF);
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
        }

        try {
            Long claveEF = usuarioEFService.obtenerUsuarioPorRfcCorto(getAccesoUsr().getUsuario()).get(0).getIdEntidadFederativa();
            vigilanciaAceptada.setIdEntidadFederativa(claveEF);
            if (vigilanciaAceptada.getFechaDescarga().equals("")) {
                cargaOmisosEntidadFederativaService.aceptarVigilancia(vigilanciaAceptada, dto);
            }
            sc = new DefaultStreamedContent(cargaOmisosEntidadFederativaService.generarArchivoVigilanciaEF(vigilanciaAceptada.getRutaArchivoOmisos()),
                    "text/plain", vigilanciaAceptada.getRutaArchivoOmisos());
            vigilancias = cargaOmisosEntidadFederativaService.obtenerVigilancias(claveEF);

        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        }
        return sc;
    }

    public StreamedContent getArchivoFundamento() {
        StreamedContent sc = null;
        try {
            if (!vigilanciaAceptada.isDescargarFundamento()) {
                super.addWarningMessage("Favor de descargar y aceptar la vigilancia", "");
                return null;
            }
            sc = new DefaultStreamedContent(cargaOmisosEntidadFederativaService.generarArchivoVigilanciaEF(vigilanciaAceptada.getRutaArchivoFundamentos()),
                    "text/plain", vigilanciaAceptada.getRutaArchivoFundamentos());
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage("Error: ", e.getMessage());
        }
        return sc;

    }

    public StreamedContent getArchivoFactura() {
        StreamedContent sc = null;
        try {
            if (!vigilanciaAceptada.isDescargarFactura()) {
                super.addWarningMessage("Favor de descargar y aceptar la vigilancia", "");
                return null;
            }
            sc = new DefaultStreamedContent(cargaOmisosEntidadFederativaService.generarArchivoVigilanciaEF(vigilanciaAceptada.getRutaArchivoFactura()),
                    "text/plain", vigilanciaAceptada.getRutaArchivoFactura());
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage("Error: ", e.getMessage());
        }
        return sc;
    }

    public void abrirRechazar() {

        setMostrarPanelRechazar(true);
        setMostrarPanelVigilancias(false);
        try {
            motivosRechazo = cargaOmisosEntidadFederativaService.obtenerMotivosRechazo();
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
    }

    public void setMostrarPanelRechazar(boolean mostrarPanelRechazar) {
        this.mostrarPanelRechazar = mostrarPanelRechazar;
    }

    public boolean isMostrarPanelRechazar() {
        return mostrarPanelRechazar;
    }

    public void setMostrarPanelVigilancias(boolean mostrarPanelVigilancias) {
        this.mostrarPanelVigilancias = mostrarPanelVigilancias;
    }

    public boolean isMostrarPanelVigilancias() {
        return mostrarPanelVigilancias;
    }

    public void setMotivosRechazo(List<MotRechazoVig> motivosRechazo) {
        this.motivosRechazo = motivosRechazo;
    }

    public List<MotRechazoVig> getMotivosRechazo() {
        return motivosRechazo;
    }

    public void setVigilancias(List<VigilanciaEntidadFederativa> vigilancias) {
        this.vigilancias = vigilancias;
    }

    public List<VigilanciaEntidadFederativa> getVigilancias() {
        return vigilancias;
    }

    public void setVigilanciaAceptada(VigilanciaEntidadFederativa vigilanciaAceptada) {
        this.vigilanciaAceptada = vigilanciaAceptada;
    }

    public VigilanciaEntidadFederativa getVigilanciaAceptada() {
        return vigilanciaAceptada;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
}

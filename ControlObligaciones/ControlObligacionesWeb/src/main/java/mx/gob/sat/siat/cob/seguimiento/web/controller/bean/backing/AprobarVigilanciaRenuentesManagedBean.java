/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.AprobarVigilanciaRenuentesHelper;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("aprobarVigilanciaRenuentesMB")
@Scope(value = "view")
public class AprobarVigilanciaRenuentesManagedBean extends AbstractBaseMB {

    @Autowired
    private AprobarRenuentesService aprobarRenuentesService;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    private AprobarVigilanciaRenuentesHelper aprobarVigilanciaRenuentesHelper;
    private boolean botonDescargaVisible = false;
    private AdministrativoNivelCargo administrativo;

    public AprobarVigilanciaRenuentesManagedBean() {
        aprobarVigilanciaRenuentesHelper = new AprobarVigilanciaRenuentesHelper();
        aprobarVigilanciaRenuentesHelper.setVisualizaVigilanciaRenuentes((VisualizaVigilanciaRenuentes) ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).
                getAttribute("vigilanciaGrupo"));
        ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).removeAttribute("vigilanciaGrupo");
        if (aprobarVigilanciaRenuentesHelper.getVisualizaVigilanciaRenuentes() != null) {
            botonDescargaVisible = true;
        }
    }

    @PostConstruct
    public void init() {
        try {
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_DOCUMENTOS_RENUENTES);
            administrativo = administracionFuncionariosService.buscarCargoAdministrativo(this.getAccesoUsr().getNumeroEmp(), EventoCargaEnum.CARGA_OMISOS);
            aprobarVigilanciaRenuentesHelper.setVigilancias(aprobarRenuentesService.listarVigilanciaRenuentes(administrativo));
        } catch (SGTServiceException ex) {
            this.getLogger().error(ex);
            addErrorMessage("Error", ex.getMessage());
        }
    }

    public AprobarVigilanciaRenuentesHelper getAprobarVigilanciaRenuentesHelper() {
        return aprobarVigilanciaRenuentesHelper;
    }

    public void setVigilanciaGrupo(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes) {
        ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).setAttribute("vigilanciaGrupoDocumentos", visualizaVigilanciaRenuentes);
    }

    public boolean isBotonDescargaVisible() {
        return botonDescargaVisible;
    }

    public StreamedContent getDescargaEmision() {
        StreamedContent sc = null;
        try {
            sc = new DefaultStreamedContent(aprobarRenuentesService.generarArchivoEmision(aprobarVigilanciaRenuentesHelper.getVisualizaVigilanciaRenuentes()),
                    "text/csv", "emisiondocumento.txt");
        } catch (SGTServiceException e) {
            this.getLogger().error(e);
            addErrorMessage("Error", e.getMessage());
            aprobarVigilanciaRenuentesHelper.setVisualizaVigilanciaRenuentes(null);
            botonDescargaVisible = false;
        }
        return sc;
    }

    public void mensajeEmision() {
        if (aprobarVigilanciaRenuentesHelper.getVisualizaVigilanciaRenuentes() != null) {
            if (aprobarVigilanciaRenuentesHelper.getVisualizaVigilanciaRenuentes().getCantidadDocumentos().
                    equals(aprobarVigilanciaRenuentesHelper.getVisualizaVigilanciaRenuentes().getCantidadDocumentosEmitidos())) {
                addMessage("Exito",
                        "Los documentos fueron emitidos exitosamente. Verifique el archivo de descarga");
            } else {
                addErrorMessage("Error", "No todos los documentos pudieron ser emitidos. Verifique el archivo de descarga");
            }
            botonDescargaVisible = true;
        }

    }

    public void irDocumentosRenuentes() {
        redirigir("/pages/cob/aprobarVigilancias/aprobarDocumentoRenuentes.jsf");
    }
}

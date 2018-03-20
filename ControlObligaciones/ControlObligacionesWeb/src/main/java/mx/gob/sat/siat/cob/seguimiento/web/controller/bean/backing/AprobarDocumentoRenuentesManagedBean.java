package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;


import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoFirmaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.AprobarDocumentoRenuentesHelper;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("aprobarDocumentoRenuentesMB")
@Scope(value = "view")
public class AprobarDocumentoRenuentesManagedBean extends AbstractBaseMB {

    private static final String MSG_ERROR="Error";
    private AprobarDocumentoRenuentesHelper aprobarDocumentoRenuentesHelper;
    @Autowired
    private AprobarRenuentesService aprobarRenuentesService;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    private AdministrativoNivelCargo administrativo;
    private Integer progress;

    public AprobarDocumentoRenuentesManagedBean() {
        aprobarDocumentoRenuentesHelper = new AprobarDocumentoRenuentesHelper();

        aprobarDocumentoRenuentesHelper.setVisualizaVigilanciaRenuentes((VisualizaVigilanciaRenuentes) ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).
                getAttribute("vigilanciaGrupoDocumentos"));
        ((HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(true)).removeAttribute("vigilanciaGrupoDocumentos");
    }

    @PostConstruct
    public void init() {
        try {
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_EMISION_MULTAS);
            administrativo = administracionFuncionariosService.buscarCargoAdministrativo(this.getAccesoUsr().getNumeroEmp(), EventoCargaEnum.CARGA_OMISOS);
                        
            generaCookies();
            if (aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes() != null) {                    
                    aprobarDocumentoRenuentesHelper.setPaginador(aprobarRenuentesService.crearPaginador(aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes(), 20, administrativo));
                    cargarPagina();
                    aprobarDocumentoRenuentesHelper.setPagina(aprobarDocumentoRenuentesHelper.getPaginador().getNumeroPagina());
                }
             progress = 0;
        } catch (SGTServiceException ex) {
            addErrorMessage(MSG_ERROR, ex.getMessage());
        }
    }

    public AprobarDocumentoRenuentesHelper getAprobarDocumentoRenuentesHelper() {
        return aprobarDocumentoRenuentesHelper;
    }

    public void setAprobarDocumentoRenuentesHelper(AprobarDocumentoRenuentesHelper aprobarDocumentoRenuentesHelper) {
        this.aprobarDocumentoRenuentesHelper = aprobarDocumentoRenuentesHelper;
    }

    private void cargarPagina() throws SGTServiceException {
        aprobarDocumentoRenuentesHelper.setDocumentos(
                aprobarRenuentesService.
                listarDocumentosRenuentes(aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes(), aprobarDocumentoRenuentesHelper.getPaginador(), administrativo));
    }

    public void siguiente() {
        try {
            if (aprobarDocumentoRenuentesHelper.getPaginador().next()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentoRenuentesHelper.getPaginador().back();
            addErrorMessage(MSG_ERROR, e.getMessage());
        }
        aprobarDocumentoRenuentesHelper.setPagina(aprobarDocumentoRenuentesHelper.getPaginador().getNumeroPagina());
    }

    public void anterior() {
        try {
            if (aprobarDocumentoRenuentesHelper.getPaginador().back()) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentoRenuentesHelper.getPaginador().next();
            addErrorMessage(MSG_ERROR, e.getMessage());
        }
        aprobarDocumentoRenuentesHelper.setPagina(aprobarDocumentoRenuentesHelper.getPaginador().getNumeroPagina());
    }

    public void irA() {
        long buffer = aprobarDocumentoRenuentesHelper.getPaginador().getNumeroPagina();
        try {

            if (aprobarDocumentoRenuentesHelper.getPaginador().goTo(aprobarDocumentoRenuentesHelper.getPagina())) {
                cargarPagina();
            }
        } catch (SGTServiceException e) {
            aprobarDocumentoRenuentesHelper.getPaginador().goTo(buffer);
            addErrorMessage(MSG_ERROR, e.getMessage());
        }
        aprobarDocumentoRenuentesHelper.setPagina(aprobarDocumentoRenuentesHelper.getPaginador().getNumeroPagina());
    }

    public void obtieneDetalles() {
        try {
            aprobarDocumentoRenuentesHelper.setListaDetalle(aprobarRenuentesService.listarDetallesRenuentes(aprobarDocumentoRenuentesHelper.getDocumentoSeleccionado()));
        } catch (SGTServiceException ex) {
            addErrorMessage(MSG_ERROR, ex.getMessage());
        }
    }

    public void emision() {

        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_DOCUMENTOS_RENUENTES, new Date(), new Date(), ConstantsCatalogos.EMISION_DOCUMENTOS_RENUENTES, ConstantsCatalogos.EMISION_DOC_RENUENTES_OBS);
            Integer documentosEmitidos = aprobarRenuentesService.emitirDocumentos(aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes(), dto, administrativo);
            
            if (documentosEmitidos != null && documentosEmitidos > 0) {
                aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes().setCantidadDocumentosEmitidos(documentosEmitidos.longValue());

                ((HttpSession) FacesContext.
                        getCurrentInstance().
                        getExternalContext().
                        getSession(true)).setAttribute("vigilanciaGrupo", aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes());
                redirigir("/pages/cob/aprobarVigilancias/aprobarVigilanciaRenuentes.jsf");
            } else {
                addMessage(MSG_ERROR,
                        this.getBundle().getString("aprobarDocumentoRenuentes.errorEmitirDocumentos"));
            }

        } catch (SGTServiceException e) {
            this.getLogger().error(e);
            addErrorMessage(MSG_ERROR, e.getMessage());
        } catch (AccesoDenegadoException e) {
            getLogger().error(e.getMessage());
        }
    }
    public boolean isFirmaDigital() {
        return aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes().getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor());
    }
    public String getRfc() {
        return getAccesoUsr().getUsuario();
    }
    
    private void generaCookies() {
        ExternalContext externalContext;
        HttpServletResponse response;
        Cookie cookieAdministrativo;
        Cookie cookieRenuenteGrupo;

        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        response = (HttpServletResponse) externalContext.getResponse();

        cookieAdministrativo = new Cookie("administrativo", administrativo.getNumeroEmpleado());
        cookieRenuenteGrupo = new Cookie("renuenteGrupo", aprobarDocumentoRenuentesHelper.getVisualizaVigilanciaRenuentes().toString());

        cookieAdministrativo.setMaxAge(-1);
        cookieAdministrativo.setPath("/app/PE/cob/restServices");

        cookieRenuenteGrupo.setMaxAge(-1);
        cookieRenuenteGrupo.setPath("/app/PE/cob/restServices");


        response.addCookie(cookieAdministrativo);
        response.addCookie(cookieRenuenteGrupo);

    }
    public void increment() {
        progress++;
        getLogger().info("\nel número de llamadas del poll va en " + progress);
    }
}
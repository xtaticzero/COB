package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.AfectacionService;
import mx.gob.sat.siat.cob.seguimiento.service.arca.DoctoEnvioArcaService;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MtvoCancelDocService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.util.ConstantesWeb;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Marco Murakami
 */
@Controller("afectacion")
@Scope(value = "view")
public class AfectacionXAutoridadManagedBean extends AbstractBaseMB {
    @SuppressWarnings("compatibility:-1737564122481946159")
    private static final long serialVersionUID = 301L;
    //BEANS
    @Autowired
    private transient AfectacionService afectacionService;
    @Autowired
    private transient DoctoEnvioArcaService doctoService;
    @Autowired
    private transient MtvoCancelDocService cancelacionService;
    @Autowired
    private transient DocumentoService documentoService;
    //VARIABLES
    private BeanAfectacionXAutoridadMain beanCancelacionXAutorizacionMain;
    private Integer selected;
    private List<AfectacionXAutoridad> listaAfectacion;
    private AfectacionXAutoridad afectacionXAutoridad;
    private List<RequerimientosAnteriores> listaDocsAsociadosPre;
    private List<RequerimientosAnteriores> listaDocsAsociadosPost;
    private String selectedNumControl;
    private AfectacionXAutoridad afectacionDetail;
    private List<AfectacionXAutoridad> listaAfectacionDetail;
    private List<MtvoCancelDoc> catalogoCancelacion;
    private MtvoCancelDoc cancelacion;
    private String docsCancelar;
    private String numControl;
    private String rfc;
    private String tipoPersona;
    //DISPLAY AND DISABLED FLAGS
    private boolean renderBusqueda;
    private boolean disabledNC;
    private boolean disabledRFC;
    private boolean disabledProcessButton;
    private boolean showResults;
    private boolean showProcessButton;
    private boolean showRFC;
    private boolean showNumControl;
    private boolean showTipoPersona;

    /**
     * Creates a new instance of CancelacionXAutorizacionManagedBean
     */
    public AfectacionXAutoridadManagedBean() {
    }

    /**
     *
     */
    @PostConstruct
    public void init() {

        try {
            super.obtenerAccesoUsrEmpleados();
            if (AccesoProceso.validaAccesoProceso(getSession(), ConstantsCatalogos.IDENTIFICADOR_AFECTACIONXAUTORIDAD, ConstantsCatalogos.PARAMETRO_FIEL)) {
                disabledNC = true;
                disabledRFC = true;

                beanCancelacionXAutorizacionMain = new BeanAfectacionXAutoridadMain();
                cancelacion = new MtvoCancelDoc();
            }
        } catch (SessionRolNullException e) {
            manejaException(e);
        } catch (AccesoDenegadoException e) {
            manejaException(e);
        } catch (AccesoDenegadoFielException e) {
            manejaException(e);
        }

    }

    /**
     * Metodo para habilitar o deshabilitar los campos de texto dependiendo de
     * la eleccion del usuario
     */
    public void enableDisable() {
        if (selected == 1) {
            disabledNC = false;
            disabledRFC = true;
            beanCancelacionXAutorizacionMain.setRfc("");
            showTipoPersona = false;
        } else {
            disabledNC = true;
            disabledRFC = false;
            beanCancelacionXAutorizacionMain.setNumeroControl("");
            showTipoPersona = true;
        }
        showResults = false;
    }

    public void cleanScreen() {
        showResults = false;
    }

    private boolean validaRFC() {

        boolean resp = false;





        return resp;
    }

    //ACTIONS
    /**
     * Metodo para la busqueda de afectaciones por autoridad ya sea por numero
     * de control o por RFC.
     */
    public void searchAction() {


        if (selected == null) {
            addErrorMessage(ConstantesWeb.ERROR, "Debe seleccionar un criterio de b\u00fasqueda");
            showResults = false;
            return;
        }

        if (selected == 1 && beanCancelacionXAutorizacionMain.getNumeroControl().isEmpty()) {
            addErrorMessage(ConstantesWeb.ERROR, "El campo N\u00famero de Control no puede estar vac\u00edo");
            showResults = false;
            return;
        }

        if (selected == 2 && beanCancelacionXAutorizacionMain.getRfc().isEmpty()) {
            addErrorMessage(ConstantesWeb.ERROR, "El campo RFC no puede estar vac\u00fao");
            showResults = false;
            return;
        }

        if (beanCancelacionXAutorizacionMain.getNumeroControl().isEmpty() && beanCancelacionXAutorizacionMain.getRfc().isEmpty()) {
            addErrorMessage(ConstantesWeb.ERROR, "El campo no puede estar vac\u00fao");
            showResults = false;
            return;
        }

        try {
            if (selected == 1) {
                listaAfectacion = afectacionService.searchDocsAfectacionByNumeroControl(
                        beanCancelacionXAutorizacionMain.getNumeroControl());
                this.showNumControl = true;
                this.showRFC = false;
            } else {
                if (validaRFC()) {
                    listaAfectacion = afectacionService.searchAfectacionXAutoridadByRFC(
                            beanCancelacionXAutorizacionMain.getRfc());
                    this.showNumControl = false;
                    this.showRFC = true;
                } else {
                    return;
                }
            }
            if (listaAfectacion.isEmpty()) {
                if (this.showNumControl) {
                    addErrorMessage(ConstantesWeb.ERROR, "El n\u00famero de control que busca no existe, por favor verifique e intente de nuevo.");
                    showResults = false;
                } else if (this.showRFC) {
                    addErrorMessage(ConstantesWeb.ERROR, "El RFC que busca no existe, por favor verifique e intente de nuevo.");
                    showResults = false;
                }
            } else {
                showResults = true;
                if (this.showNumControl) {
                    afectacionXAutoridad = new AfectacionXAutoridad();
                    afectacionXAutoridad.setNumeroControl(listaAfectacion.get(0).getNumeroControl());
                    afectacionXAutoridad.setRfc(listaAfectacion.get(0).getRfc());
                    afectacionXAutoridad.setFechaRegistro(listaAfectacion.get(0).getFechaRegistro());
                    afectacionXAutoridad.setFechaNotificacion(listaAfectacion.get(0).getFechaNotificacion());
                    afectacionXAutoridad.setFechaVencimiento(listaAfectacion.get(0).getFechaVencimiento());
                    afectacionXAutoridad.setEstado(listaAfectacion.get(0).getEstado());
                }
                //MUESTRA LOS DOCUMENTOS ASOCIADOS
                this.buscaDocsAntecesores();
                this.buscaDocsPosteriores();
            }
        } catch (SGTServiceException ex) {
            addErrorMessage(ConstantesWeb.ERROR, ex.getMessage());
            showResults = false;
        }

    }

    /**
     * Metodo para buscar la informacion del documento asociado seleccionado
     */
    public void searchAsociateDetail() {

        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            String idDocumento = params.get("idDocumento");

            listaAfectacionDetail = afectacionService.searchDocsAfectacionByNumeroControl(idDocumento);

            if (listaAfectacionDetail.isEmpty()) {
                afectacionDetail = null;
                context.addMessage("validacion", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "No existen resultados."));
            } else {
                afectacionDetail = new AfectacionXAutoridad();
                afectacionDetail.setNumeroControl(listaAfectacionDetail.get(0).getNumeroControl());
                afectacionDetail.setRfc(listaAfectacionDetail.get(0).getRfc());
                afectacionDetail.setFechaRegistro(listaAfectacionDetail.get(0).getFechaRegistro());
                afectacionDetail.setFechaNotificacion(listaAfectacionDetail.get(0).getFechaNotificacion());
                afectacionDetail.setFechaVencimiento(listaAfectacionDetail.get(0).getFechaVencimiento());
                afectacionDetail.setEstado(listaAfectacionDetail.get(0).getEstado());
            }
        } catch (SGTServiceException ex) {
            if (ex.toString().contains("Incorrect result size")) {
                addMessage(ConstantesWeb.AVISO, "No se econtraron registros para esta b\u00fasqueda.");
            } else {
                addErrorMessage(ConstantesWeb.ERROR, "Se present\u00f3 un error al momento de consultar la informaci\u00f3n.");
            }
            showResults = false;
        }

    }

    /**
     * Metodo para la busqueda del documento suceptible a cancelancion
     */
    public void cancelacionAction() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (beanCancelacionXAutorizacionMain.getNumeroControl().isEmpty()) {
            context.addMessage("validacion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El campo no puede estar vac\u00edo."));
            showResults = false;
            return;
        }
        try {
            listaAfectacion = afectacionService.searchAfectacionXAutoridadByNumeroControl(
                    beanCancelacionXAutorizacionMain.getNumeroControl());

            if (listaAfectacion.isEmpty()) {
                TipoDocumento tipoDoc = documentoService.getTipoDocumentoXNumControl(beanCancelacionXAutorizacionMain.getNumeroControl());
                if (tipoDoc != null) {
                    String tipo = tipoDoc.getNombre();
                    addMessage(ConstantesWeb.AVISO, "El n\u00FAmero de control capturado no es susceptible de cancelaci\u00F3n. El estado del documento actual es: " + tipo);
                    showResults = false;
                }
            } else {
                showResults = true;
                disabledProcessButton = false;
                afectacionXAutoridad = new AfectacionXAutoridad();
                afectacionXAutoridad.setNumeroControl(listaAfectacion.get(0).getNumeroControl());
                afectacionXAutoridad.setRfc(listaAfectacion.get(0).getRfc());
                afectacionXAutoridad.setFechaRegistro(listaAfectacion.get(0).getFechaRegistro());
                afectacionXAutoridad.setFechaNotificacion(listaAfectacion.get(0).getFechaNotificacion());
                afectacionXAutoridad.setFechaVencimiento(listaAfectacion.get(0).getFechaVencimiento());
                afectacionXAutoridad.setEstado(listaAfectacion.get(0).getEstado());

                //MUESTRA LOS DOCUMENTOS ASOCIADOS POSTERIORES
                this.buscaDocsPosteriores();

                //CARGA LOS MOTIVOS DE CANCELACION
                catalogoCancelacion = cancelacionService.todosLosMotivos();

                //GUARDA LOS NUMEROS DE CONTROL DE LOS DOCUMENTO A CANCELAR
                docsCancelar = "\n\n" + beanCancelacionXAutorizacionMain.getNumeroControl() + "\n";
                for (int i = 0; i < listaDocsAsociadosPost.size(); i++) {
                    docsCancelar += listaDocsAsociadosPost.get(i).getIdDocumento() + "\n";
                }
            }
        } catch (SGTServiceException e) {
            addErrorMessage(ConstantesWeb.ERROR, e.getMessage());
            showResults = false;
        }

    }

    /**
     * Metodo para buscar documentos anteriores
     */
    private void buscaDocsAntecesores() {

        try {
            listaDocsAsociadosPre = doctoService.origenMultaArcaAntecesores(beanCancelacionXAutorizacionMain.getNumeroControl());
            if (listaDocsAsociadosPre.isEmpty()) {
                addMessage(ConstantesWeb.AVISO, "No se encontraron documentos asociados anteriores.");
            }
        } catch (SGTServiceException e) {
            addErrorMessage(ConstantesWeb.ERROR, e.getMessage());
        }

    }

    /**
     * Metodo para buscar documentos posteriores
     */
    private void buscaDocsPosteriores() {

        try {
            listaDocsAsociadosPost = doctoService.origenMultaArcaPosteriores(beanCancelacionXAutorizacionMain.getNumeroControl());

            if (listaDocsAsociadosPost.isEmpty()) {
                addMessage(ConstantesWeb.AVISO, "No se encontraron documentos asociados posteriores.");
            }
        } catch (SGTServiceException e) {
            addErrorMessage(ConstantesWeb.ERROR, e.getMessage());
        }

    }

    /**
     * Metodo para la cancelacion del documento en la base de datos
     */
    public void processAction() {

        List<Documento> listaDocumentos = new ArrayList<Documento>();

        disabledProcessButton = true;

        try {
            Documento documento = documentoService.consultaXNumControl(beanCancelacionXAutorizacionMain.getNumeroControl());
            listaDocumentos.add(documento);

            if (listaDocsAsociadosPost != null) {
                for (int i = 0; i < listaDocsAsociadosPost.size(); i++) {
                    Documento docPost = documentoService.consultaXNumControl(listaDocsAsociadosPost.get(i).getIdDocumento());
                    listaDocumentos.add(docPost);
                }
            }
            afectacionService.cancelarDocumentoXAutoridad(listaDocumentos, cancelacion);
            addMessage(ConstantesWeb.AVISO, "El documento fue cancelado exitosamente.");
            try {
                SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_AFECTACIONXAUTORIDAD, new Date(), new Date(), ConstantsCatalogos.ACTUALIZA_MOV_AFECTACION, ConstantsCatalogos.ACTUALIZA_AFECTACION_OBS);
                afectacionService.registraBitacora(dto);
            } catch (AccesoDenegadoException e) {
                getLogger().debug(e.getMessage());
            }
        } catch (SGTServiceException e) {
            addErrorMessage(ConstantesWeb.ERROR, e.getMessage());
        }

    }

    //GETTER AND SETTERS
    /**
     *
     * @return
     */
    public BeanAfectacionXAutoridadMain getBeanCancelacionXAutorizacionMain() {
        return beanCancelacionXAutorizacionMain;
    }

    /**
     *
     * @param beanCancelacionXAutorizacionMain
     */
    public void setBeanCancelacionXAutorizacionMain(BeanAfectacionXAutoridadMain beanCancelacionXAutorizacionMain) {
        this.beanCancelacionXAutorizacionMain = beanCancelacionXAutorizacionMain;
    }

    /**
     *
     * @return
     */
    public Integer getSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public boolean isDisabledNC() {
        return disabledNC;
    }

    /**
     *
     * @param disabledNC
     */
    public void setDisabledNC(boolean disabledNC) {
        this.disabledNC = disabledNC;
    }

    /**
     *
     * @return
     */
    public boolean isDisabledRFC() {
        return disabledRFC;
    }

    /**
     *
     * @param disabledRFC
     */
    public void setDisabledRFC(boolean disabledRFC) {
        this.disabledRFC = disabledRFC;
    }

    /**
     *
     * @return
     */
    public boolean isDisabledProcessButton() {
        return disabledProcessButton;
    }

    /**
     *
     * @param disabledProcessButton
     */
    public void setDisabledProcessButton(boolean disabledProcessButton) {
        this.disabledProcessButton = disabledProcessButton;
    }

    /**
     *
     * @return
     */
    public List<AfectacionXAutoridad> getListaAfectacion() {
        return listaAfectacion;
    }

    /**
     *
     * @param listaAfectacion
     */
    public void setListaAfectacion(List<AfectacionXAutoridad> listaAfectacion) {
        this.listaAfectacion = listaAfectacion;
    }

    /**
     *
     * @return
     */
    public boolean isShowResults() {
        return showResults;
    }

    /**
     *
     * @param showResults
     */
    public void setShowResults(boolean showResults) {
        this.showResults = showResults;
    }

    /**
     *
     * @return
     */
    public boolean isShowProcessButton() {
        return showProcessButton;
    }

    /**
     *
     * @param showProcessButton
     */
    public void setShowProcessButton(boolean showProcessButton) {
        this.showProcessButton = showProcessButton;
    }

    /**
     *
     * @return
     */
    public AfectacionXAutoridad getAfectacionXAutoridad() {
        return afectacionXAutoridad;
    }

    /**
     *
     * @param afectacionXAutoridad
     */
    public void setAfectacionXAutoridad(AfectacionXAutoridad afectacionXAutoridad) {
        this.afectacionXAutoridad = afectacionXAutoridad;
    }

    /**
     *
     * @return
     */
    public boolean isRenderBusqueda() {
        return renderBusqueda;
    }

    /**
     *
     * @param renderBusqueda
     */
    public void setRenderBusqueda(boolean renderBusqueda) {
        this.renderBusqueda = renderBusqueda;
    }

    /**
     *
     * @return
     */
    public List<RequerimientosAnteriores> getListaDocsAsociadosPre() {
        return listaDocsAsociadosPre;
    }

    /**
     *
     * @param listaDocsAsociadosPre
     */
    public void setListaDocsAsociadosPre(List<RequerimientosAnteriores> listaDocsAsociadosPre) {
        this.listaDocsAsociadosPre = listaDocsAsociadosPre;
    }

    /**
     *
     * @return
     */
    public List<RequerimientosAnteriores> getListaDocsAsociadosPost() {
        return listaDocsAsociadosPost;
    }

    /**
     *
     * @param listaDocsAsociadosPost
     */
    public void setListaDocsAsociadosPost(List<RequerimientosAnteriores> listaDocsAsociadosPost) {
        this.listaDocsAsociadosPost = listaDocsAsociadosPost;
    }

    /**
     *
     * @return
     */
    public String getSelectedNumControl() {
        return selectedNumControl;
    }

    /**
     *
     * @param selectedNumControl
     */
    public void setSelectedNumControl(String selectedNumControl) {
        this.selectedNumControl = selectedNumControl;
    }

    /**
     *
     * @return
     */
    public AfectacionXAutoridad getAfectacionDetail() {
        return afectacionDetail;
    }

    /**
     *
     * @param afectacionDetail
     */
    public void setAfectacionDetail(AfectacionXAutoridad afectacionDetail) {
        this.afectacionDetail = afectacionDetail;
    }

    /**
     *
     * @return
     */
    public List<AfectacionXAutoridad> getListaAfectacionDetail() {
        return listaAfectacionDetail;
    }

    /**
     *
     * @param listaAfectacionDetail
     */
    public void setListaAfectacionDetail(List<AfectacionXAutoridad> listaAfectacionDetail) {
        this.listaAfectacionDetail = listaAfectacionDetail;
    }

    /**
     *
     * @return
     */
    public List<MtvoCancelDoc> getCatalogoCancelacion() {
        return catalogoCancelacion;
    }

    /**
     *
     * @param catalogoCancelacion
     */
    public void setCatalogoCancelacion(List<MtvoCancelDoc> catalogoCancelacion) {
        this.catalogoCancelacion = catalogoCancelacion;
    }

    /**
     *
     * @return
     */
    public MtvoCancelDoc getCancelacion() {
        return cancelacion;
    }

    /**
     *
     * @param cancelacion
     */
    public void setCancelacion(MtvoCancelDoc cancelacion) {
        this.cancelacion = cancelacion;
    }

    /**
     *
     * @return
     */
    public String getDocsCancelar() {
        return docsCancelar;
    }

    /**
     *
     * @param docsCancelar
     */
    public void setDocsCancelar(String docsCancelar) {
        this.docsCancelar = docsCancelar;
    }

    /**
     *
     * @return
     */
    public boolean isShowRFC() {
        return showRFC;
    }

    /**
     *
     * @param showRFC
     */
    public void setShowRFC(boolean showRFC) {
        this.showRFC = showRFC;
    }

    /**
     *
     * @return
     */
    public boolean isShowNumControl() {
        return showNumControl;
    }

    /**
     *
     * @param showNumControl
     */
    public void setShowNumControl(boolean showNumControl) {
        this.showNumControl = showNumControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public boolean isShowTipoPersona() {
        return showTipoPersona;
    }

    public void setShowTipoPersona(boolean showTipoPersona) {
        this.showTipoPersona = showTipoPersona;
    }

    private void manejaException(Exception e) {
        getSession().setAttribute("mensaje", e.getMessage());
        redirigir(ConstantsCatalogos.URL_EN_CONSTRUCCION);
        getLogger().error(e);
    }
}

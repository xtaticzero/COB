package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultasRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteJasperAfectacionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.AfectacionService;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.MtvoCancelDocService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ReporterService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.util.ConstantesWeb;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller("consultaAfectacion")
@Scope(value = "view")
public class ConsultasAfectacionManagedBean extends AbstractBaseMB {
    private static final long serialVersionUID = -5197691180506547942L;

    //BEANS
    @Autowired
    private  AfectacionService afectacionService;
    @Autowired
    private  ReporterService reporterServiceImpl;
    @Autowired
    private  DocumentoService documentoService;
    @Autowired
    private  MtvoCancelDocService cancelacionService;
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
    private List<MultaDocumentoAfectaciones> listaMultasDocumento;
    private MtvoCancelDoc cancelacion;
    private String docsCancelar;
    private String numControl;
    private String rfc;
    private String idTipoPersona;
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
    private boolean panelCriterios;
    private boolean panelIceps;
    private Integer indexTab;
    private boolean btnImprimir;
    private List<MultaDocumentoAfectaciones> listaMultasDocumentoDetalle;
    private List<ConsultasRenuentes> listaAfectacionRenuentes;
    private List<ComboStr> listaBoId;
    private MultaDocumentoAfectaciones detalleMulta;
    private Long montoTotalMultas;
    private Long totMulta;
    private String boIds = "";
    private List<ReporteAfectacionXAutoridadDTO> listaAfectacionesRep;
    private List<MultaDocumentoAfectaciones> listaMultasRep;
    private List<MultaDocumentoAfectaciones> listaNumeroResolucion;
    private List<MultaDocumentoAfectaciones> listaNumeroTemp;
    private int progress;
    private String identificador;
    
    

    public ConsultasAfectacionManagedBean() {
    }

    /**
     *
     */
    @PostConstruct
    public void init() {

            super.obtenerAccesoUsrEmpleados();
            if (Boolean.getBoolean(getRequest().getParameter("consulta"))) {
                identificador = ConstantsCatalogos.IDENTIFICADOR_AFECTACIONXAUTORIDAD;
            } else {
                identificador = ConstantsCatalogos.IDENTIFICADOR_CONSULTADOCUMENTOS;
            }
            
            beanCancelacionXAutorizacionMain = new BeanAfectacionXAutoridadMain();
            cleanScreen();
            cancelacion = new MtvoCancelDoc();
            autorizar(identificador);
        }

    /**
     * Metodo para habilitar o deshabilitar los campos de texto dependiendo de
     * la eleccion del usuario
     */
    public void enableDisable() {
        if (selected == ConstantsCatalogos.UNO) {
            disabledNC = false;
            disabledRFC = true;
            beanCancelacionXAutorizacionMain.setRfc("");
            showTipoPersona = true;
            idTipoPersona = null;
            panelCriterios = true;
            panelIceps = false;
            btnImprimir = true;
        } else {
            disabledNC = true;
            disabledRFC = false;
            beanCancelacionXAutorizacionMain.setNumeroControl("");
            showTipoPersona = false;
            idTipoPersona = null;
            panelCriterios = true;
            panelIceps = false;
            btnImprimir = true;
        }
        setListaDocsAsociadosPre(null);
        setListaDocsAsociadosPost(null);
        showResults = false;
    }

    public void cleanScreen() {
        disabledNC = true;
        disabledRFC = true;
        showTipoPersona = true;
        beanCancelacionXAutorizacionMain.setRfc("");
        beanCancelacionXAutorizacionMain.setNumeroControl("");
        showResults = false;
        idTipoPersona = null;
        panelCriterios = true;
        panelIceps = false;
        limpiarListas();
        btnImprimir = true;
    }

    public void limpiarListas() {
        setListaMultasDocumento(null);
        setListaAfectacionDetail(null);
        setListaDocsAsociadosPre(null);
        setListaDocsAsociadosPost(null);

    }

    public boolean validaFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    //ACTIONS
    /**
     * Metodo para la busqueda de afectaciones por autoridad ya sea por numero
     * de control o por RFC.
     */
    public void searchAction() {

        setListaAfectacionRenuentes(null);
        if (selected == null) {
            addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_ERROR_DOCUMENTOS_CRITERIO);
            showResults = false;
            return;
        }

        if (selected == ConstantsCatalogos.NUMERO_CONTROL && beanCancelacionXAutorizacionMain.getNumeroControl().isEmpty()) {
            addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_ERROR_DOCUMENTOS_NUM_CONT);
            showResults = false;
            return;
        }

        if (selected == ConstantsCatalogos.RFC && beanCancelacionXAutorizacionMain.getRfc().isEmpty()) {
            addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_ERROR_DOCUMENTOS_RFC);
            showResults = false;
            return;
        }


        if (selected == ConstantsCatalogos.RFC && idTipoPersona == null) {
            addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_ERROR_DOCUMENTOS_TIPO_PERSONA);
            showResults = false;
            return;
        }

        if (beanCancelacionXAutorizacionMain.getNumeroControl().isEmpty()
                && beanCancelacionXAutorizacionMain.getRfc().isEmpty()) {
            addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_ERROR_DOCUMENTOS_CAMPO_VACIO);
            showResults = false;
            return;
        }

        if (selected == ConstantsCatalogos.RFC && idTipoPersona.equals("M")) {
            if (beanCancelacionXAutorizacionMain.getRfc().length() < ConstantsCatalogos.NUEVE) {
                addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_ERROR_DATO_CAMPO);
                showResults = false;
                return;
            }
            
            for(int i=0; i<3; i++){
               char caracter = beanCancelacionXAutorizacionMain.getRfc().charAt(i);
               if (!(String.valueOf(caracter).equals("&")) ){
                   if(!Character.isLetter(caracter)){
                      addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_CAMPO_INCORRECTO_TRES_CARACTERES);
                    showResults = false;
                    return; 
                   }                    
                }
            }
            

            
            if (!validaFecha(beanCancelacionXAutorizacionMain.getRfc().substring(ConstantsCatalogos.SIETE, ConstantsCatalogos.NUEVE) + "/"
                    + beanCancelacionXAutorizacionMain.getRfc().substring(ConstantsCatalogos.CINCO, ConstantsCatalogos.SIETE) + "/"
                    + beanCancelacionXAutorizacionMain.getRfc().substring(ConstantsCatalogos.TRES, ConstantsCatalogos.CINCO))) {
                addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_FECHA_INVALIDA);
                showResults = false;
                return;
            }

        } else if (selected == ConstantsCatalogos.RFC && idTipoPersona.equals("F")) {
            if (beanCancelacionXAutorizacionMain.getRfc().length() < ConstantsCatalogos.DIEZ) {
                addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_CAMPO_VALIDO_PERSONA_FISICA);
                showResults = false;
                return;
            }
           
            for(int i=0; i<4; i++){
               char caracter = beanCancelacionXAutorizacionMain.getRfc().charAt(i);
               if (!(String.valueOf(caracter).equals("&")) ){
                   if(!Character.isLetter(caracter)){
                      addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_CARACTERES_INCORRECTOS);
                    showResults = false;
                    return; 
                   }                    
                }
            }

            if (!validaFecha(beanCancelacionXAutorizacionMain.getRfc().substring(ConstantsCatalogos.OCHO, ConstantsCatalogos.DIEZ) + "/"
                    + beanCancelacionXAutorizacionMain.getRfc().substring(ConstantsCatalogos.SEIS, ConstantsCatalogos.OCHO) + "/"
                    + beanCancelacionXAutorizacionMain.getRfc().substring(ConstantsCatalogos.CUATRO, ConstantsCatalogos.SEIS))) {
                addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_FECHA_INVALIDA);
                showResults = false;
                return;
            }
        }

        try {
            if (selected == ConstantsCatalogos.NUMERO_CONTROL) {
                searchAsociateDetail(beanCancelacionXAutorizacionMain.getNumeroControl(), idTipoPersona);

            } else {

                listaBoId = afectacionService.obtenerBoId(beanCancelacionXAutorizacionMain.getRfc(), idTipoPersona);
                if (listaBoId == null || listaBoId.isEmpty()) {
                    addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_SIN_RESULTADOS_AL_CONSULTAR);
                    showResults = false;
                    panelIceps = false;
                    panelCriterios = true;
                } else {
    
                    StringBuilder buf = new StringBuilder();
                    for (ComboStr boId : listaBoId) {
                        buf.append("'").append(boId.getIdCombo()).append("',");
                    }
                    boIds = buf.toString();

                    listaAfectacionRenuentes =
                            afectacionService.searchAfectacionXAutoridadByBoId(boIds.substring(ConstantsCatalogos.CERO, boIds.length() - ConstantsCatalogos.UNO),
                            idTipoPersona);

                    showResults = true;
                    panelIceps = false;
                    panelCriterios = true;

                    if (listaAfectacionRenuentes.isEmpty()) {
                        addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_SIN_RESULTADOS_AL_CONSULTAR);
                        showResults = false;
                        panelIceps = false;
                        panelCriterios = true;
                    }

                }


            }

        } catch (SGTServiceException ex) {
            addErrorMessage(ConstantesWeb.ERROR, ex.getMessage());
            showResults = false;
        }

    }

    public void recargaCancelacionDocumentos(){
        limpiarListas();
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params =
                    context.getExternalContext().getRequestParameterMap();

            String idDocumento = params.get(ConstantsCatalogos.ID_DOCUMENTO);
                   
        beanCancelacionXAutorizacionMain.setNumeroControl(idDocumento);
        cancelacionAction();
    }

    /**
     * Metodo para buscar la informacion del documento asociado seleccionado
     */
    public void searchAsociateDetail() {

        limpiarListas();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Map<String, String> params =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            String idDocumento = params.get(ConstantsCatalogos.ID_DOCUMENTO);
            String tipoPersona = params.get(ConstantsCatalogos.ID_TIPO_PERSONA);
            if (tipoPersona.equals("null")) {
                listaAfectacionDetail = afectacionService.searchDocsAfectacionNumControl(idDocumento); 
            }else{
                listaAfectacionDetail = afectacionService.searchDocsAfectacionByNumeroControl(idDocumento, tipoPersona); 
            }


            if (listaAfectacionDetail == null || listaAfectacionDetail.isEmpty()) {
                afectacionDetail = null;
                context.addMessage(ConstantsCatalogos.VALIDACION,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantsCatalogos.AVISO, ConstantsCatalogos.SIN_RESULTADOS));
            } else {
                afectacionDetail=listaAfectacionDetail.get(0);
                
                btnImprimir = false;
                //MUESTRA LOS DOCUMENTOS ASOCIADOS
                this.buscaDocsAntecesores(idDocumento);
                this.buscaDocsPosteriores(idDocumento);

                // Buscar Multas
                listaMultasDocumento = afectacionService.obtenerMultasPorNumControl(idDocumento);

                panelIceps = true;
                if (tipoPersona.equals("null")) {
                    showResults = false;
                } else {
                    showResults = true;
                }
                panelCriterios = true;
            }
        } catch (SGTServiceException ex) {
            if (ex.toString().contains(ConstantsCatalogos.TAMANIO_INCORRECTO)) {
                addMessage(ConstantesWeb.AVISO, ConstantsCatalogos.MSG_SIN_RESULTADOS);
            } else {
                addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
            }
            showResults = false;
            panelIceps = false;
            panelCriterios = true;
            btnImprimir = true;
        }
        setIndexTab(0);

    }

    public void searchAsociateDetail(String numCont, String tipoPer) {
        limpiarListas();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if(tipoPer==null){
                listaAfectacionDetail = afectacionService.searchDocsAfectacionNumControl(numCont); 
            }else{
                listaAfectacionDetail = afectacionService.searchDocsAfectacionByNumeroControl(numCont, tipoPer); 
            }
           

            if (listaAfectacionDetail == null || listaAfectacionDetail.isEmpty()) {
                afectacionDetail = null;
                context.addMessage("validaci\u00f3n",
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", "No existen resultados."));
            } else {

                afectacionDetail=listaAfectacionDetail.get(0);
                btnImprimir = false;
                this.buscaDocsAntecesores(numCont);
                this.buscaDocsPosteriores(numCont);

                // Buscar Multas
                listaMultasDocumento = afectacionService.obtenerMultasPorNumControl(numCont);


                panelIceps = true;
                if (tipoPer == null) {
                    showResults = false;
                } else {
                    showResults = true;
                }
                panelCriterios = true;
            }
        } catch (SGTServiceException ex) {
            if (ex.toString().contains("Incorrect result size")) {
                addMessage(ConstantesWeb.AVISO, ConstantsCatalogos.MSG_SIN_RESULTADOS_AL_CONSULTAR);
            } else {
                addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
            }
            showResults = false;
            panelIceps = false;
            panelCriterios = true;
        }
        setIndexTab(0);

    }

    public void btnRegresar() {

        searchAction();

    }

    public void obtenerDetalleMulta() throws SGTServiceException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String tipoMulta = params.get("tipoMulta");
        String numRes = params.get("numRes");
        setTotMulta(ConstantsCatalogos.CERO_LONG);
        setMontoTotalMultas(ConstantsCatalogos.CERO_LONG);
        listaMultasDocumentoDetalle = afectacionService.obtenerMultasPorNumControlTipoMulta(numRes, tipoMulta);
        if (listaMultasDocumentoDetalle == null || listaMultasDocumentoDetalle.isEmpty()) {
            addMessage(ConstantesWeb.AVISO, ConstantsCatalogos.MSG_SIN_MULTAS);
        } else {

            for (MultaDocumentoAfectaciones multa : listaMultasDocumentoDetalle) {
                totMulta += multa.getMonto();

            }
            for (MultaDocumentoAfectaciones multa : listaMultasDocumentoDetalle) {
                multa.setMontoTotal(totMulta);

            }
            montoTotalMultas = totMulta;
        }

    }

    public List<MultaDocumentoAfectaciones> obtenerDetalleMultaReporte(String numRes, String tipoMulta) throws SGTServiceException {
        List<MultaDocumentoAfectaciones> res;
        res = afectacionService.obtenerMultasPorNumControlTipoMulta(numRes, tipoMulta);

        return res;
    }

    public void limpiaListasReporte() {
        setListaAfectacionesRep(null);
        setListaMultasRep(new LinkedList<MultaDocumentoAfectaciones>());
        setListaNumeroResolucion(null);
        setListaNumeroTemp(null);
    }

    public void btnGenerarReporte() {

        ReporteJasperAfectacionDTO datosAfectacionJasper = new ReporteJasperAfectacionDTO();
        limpiaListasReporte();

        if (afectacionDetail != null) {
            try {
                listaAfectacionesRep = (List<ReporteAfectacionXAutoridadDTO>) afectacionService.obtenerDatosReporte(afectacionDetail.getNumeroControl());

                listaNumeroResolucion = afectacionService.obtenerMultasPorNumControl(afectacionDetail.getNumeroControl());
                if (!(listaNumeroResolucion == null || listaNumeroResolucion.isEmpty())) {
                    for (MultaDocumentoAfectaciones icep : listaNumeroResolucion) {
                        listaNumeroTemp = obtenerDetalleMultaReporte(icep.getNumResolucion(), icep.getDescObligacion());
                        if (!(listaNumeroTemp == null || listaNumeroTemp.isEmpty())) {
                            for (MultaDocumentoAfectaciones multa : listaNumeroTemp) {
                                multa.setNombreEstado(icep.getNombreEstado()!=null?icep.getNombreEstado():"");
                                listaMultasRep.add(multa);
                            }
                        }
                    }
                }
                setTotMulta(ConstantsCatalogos.CERO_LONG);
                setMontoTotalMultas(ConstantsCatalogos.CERO_LONG);
                if (!(listaMultasRep == null || listaMultasRep.isEmpty())) {
                    for (MultaDocumentoAfectaciones multa : listaMultasRep) {
                        totMulta += multa.getMonto();
                    }
                    for (MultaDocumentoAfectaciones multa : listaMultasRep) {
                        multa.setMontoTotal(totMulta);
                    }
                }
                if (!(listaAfectacionesRep == null || listaAfectacionesRep.isEmpty())) {
                    datosAfectacionJasper.setNumeroControl(listaAfectacionesRep.get(0).getNumeroControl());
                    datosAfectacionJasper.setEstado(listaAfectacionesRep.get(0).getEstadoDocumento());
                    datosAfectacionJasper.setFechaNotificacion(listaAfectacionesRep.get(0).getFechaNotificacion());
                    datosAfectacionJasper.setFechaRegistro(listaAfectacionesRep.get(0).getFechaRegistro());
                    datosAfectacionJasper.setFechaVencimiento(listaAfectacionesRep.get(0).getFechaVencimiento());
                    datosAfectacionJasper.setFechaNoTrabajado(listaAfectacionesRep.get(0).getFechaNoTrabajado());
                    datosAfectacionJasper.setFechaNoLocalizado(listaAfectacionesRep.get(0).getFechaNoLocalizado());
                    datosAfectacionJasper.setTipoDocumento(listaAfectacionesRep.get(0).getTipoDocumento());
                    datosAfectacionJasper.setAdmonLocal(afectacionDetail.getAdmonLocal());
                    datosAfectacionJasper.setRfc(afectacionDetail.getRfc());
                    datosAfectacionJasper.setNombre(afectacionDetail.getNombre());
                    datosAfectacionJasper.setTipoMedio(afectacionDetail.getTipoMedio());
                    datosAfectacionJasper.setFechaCitatorio(afectacionDetail.getFechaCitatorio());
                    datosAfectacionJasper.setListaObligaciones(listaAfectacionesRep);
                    datosAfectacionJasper.setListaMultas((listaMultasRep == null || listaMultasRep.isEmpty()) ? null : listaMultasRep);
                }
                byte[] bytesFile;
                bytesFile = null;
                bytesFile = reporterServiceImpl.makeReport(datosAfectacionJasper);

                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile);
            } catch (SGTServiceException e) {
                addErrorMessage(ConstantesWeb.ERROR, ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
                getLogger().error(e);
            }
        }

    }

    public void generaDocumento(byte[] arregloBytes) {
        if (arregloBytes != null) {
            despachaArchivo(arregloBytes, ConstantsCatalogos.TYPE_PDF, ConstantsCatalogos.NOMBRE_DOC_PDF,
                    ConstantsCatalogos.ERROR_GENERA_PDF);
        }
    }

    /**
     * Metodo para buscar documentos anteriores
     */
    private void buscaDocsAntecesores(String numCont) {

        try {
            listaDocsAsociadosPre = afectacionService.origenMultaArcaAnteriores(numCont);
        } catch (SGTServiceException e) {

            addErrorMessage(ConstantesWeb.ERROR, e.getMessage());
        }

    }

    /**
     * Metodo para buscar documentos posteriores
     */
    private void buscaDocsPosteriores(String numCont) {

        try {

            listaDocsAsociadosPost = afectacionService.origenMultaArcaPosteriores(numCont);
        } catch (SGTServiceException e) {

            addErrorMessage(ConstantesWeb.ERROR, e.getMessage());
        }

    } 
    
    /**
     * Metodo para la busqueda del documento suceptible a cancelancion
     */
    public void cancelacionAction() { 

        FacesContext context = FacesContext.getCurrentInstance();

        if (beanCancelacionXAutorizacionMain.getNumeroControl().isEmpty()) {
            context.addMessage("validacion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El campo no puede estar vac\u00edo."));
            panelIceps = false;
            return;
        }
        try {
            listaAfectacion =  afectacionService.searchDocsAfectacionByNumeroControlCancelacion(beanCancelacionXAutorizacionMain.getNumeroControl());

            if (listaAfectacion.isEmpty()) {
                TipoDocumento tipoDoc = documentoService.getTipoDocumentoXNumControl(beanCancelacionXAutorizacionMain.getNumeroControl());
                if (tipoDoc != null) {
                    addMessage(ConstantesWeb.AVISO, "El n\u00FAmero de control capturado no es susceptible de cancelaci\u00F3n. El estado del documento actual es: " +  tipoDoc.getNombre());
                    panelIceps = false;
                    
                }
            } else {
                               
                limpiarListas();
                listaAfectacionDetail = new LinkedList<AfectacionXAutoridad>(listaAfectacion);
                
                afectacionDetail=listaAfectacion.get(0);
                                  
                this.buscaDocsPosteriores(beanCancelacionXAutorizacionMain.getNumeroControl());

                // Buscar Multas
                listaMultasDocumento = afectacionService.obtenerMultasPorNumControl(beanCancelacionXAutorizacionMain.getNumeroControl());

                panelIceps = true;
                disabledProcessButton = false;
                 
               
                setIndexTab(0);

                //CARGA LOS MOTIVOS DE CANCELACION
                catalogoCancelacion = cancelacionService.todosLosMotivos();

                //GUARDA LOS NUMEROS DE CONTROL DE LOS DOCUMENTO A CANCELAR
                docsCancelar =  beanCancelacionXAutorizacionMain.getNumeroControl();
               
                }
        } catch (SGTServiceException e) {
            addWarningMessage(ConstantesWeb.AVISO, ConstantsCatalogos.MSG_SIN_RESULTADOS_NUM_CONTROL);
            panelIceps = false;
        }

    }
    
    public void cleanScreenCancelacion() {
        beanCancelacionXAutorizacionMain.setNumeroControl(null);
        setListaAfectacionDetail(null);
        setAfectacionDetail(null);
        cancelacion.setIdMotivoCancelacion(null);
        limpiarListas();
        panelIceps = false;      
    }
    
    public void validaCombo(){
            if (cancelacion.getIdMotivoCancelacion()==null){
                addErrorMessage(ConstantesWeb.ERROR, "El campo motivo de cancelaci\u00f3n es requerido");
            }
        }    

    /**
     * Metodo para la cancelacion del documento en la base de datos
     */
    public void processAction() {

        List<Documento> listaDocumentos = new ArrayList<Documento>();


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
            addMessage(ConstantesWeb.AVISO, "El documento No."+beanCancelacionXAutorizacionMain.getNumeroControl()+"  fue cancelado exitosamente.");
            panelIceps = false;
            beanCancelacionXAutorizacionMain.setNumeroControl(null);
                try {
                    SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_AFECTACIONXAUTORIDAD, new Date(), new Date(), ConstantsCatalogos.ACTUALIZA_MOV_AFECTACION, ConstantsCatalogos.ACTUALIZA_AFECTACION_OBS);
                    afectacionService.registraBitacora(dto);
                } catch (AccesoDenegadoException e) {
                    getLogger().debug(e.getMessage());
                }
        } catch (SGTServiceException e) {
            panelIceps = true;
            addErrorMessage(ConstantesWeb.ERROR, e.getMessage());
        }

    }

    

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

    public boolean isShowTipoPersona() {
        return showTipoPersona;
    }

    public void setShowTipoPersona(boolean showTipoPersona) {
        this.showTipoPersona = showTipoPersona;
    }

    public void setIdTipoPersona(String idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    public String getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setListaAfectacionRenuentes(List<ConsultasRenuentes> listaAfectacionRenuentes) {
        this.listaAfectacionRenuentes = listaAfectacionRenuentes;
    }

    public List<ConsultasRenuentes> getListaAfectacionRenuentes() {
        return listaAfectacionRenuentes;
    }

    public void setListaBoId(List<ComboStr> listaBoId) {
        this.listaBoId = listaBoId;
    }

    public List<ComboStr> getListaBoId() {
        return listaBoId;
    }

    public void setPanelCriterios(boolean panelCriterios) {
        this.panelCriterios = panelCriterios;
    }

    public boolean isPanelCriterios() {
        return panelCriterios;
    }

    public void setPanelIceps(boolean panelIceps) {
        this.panelIceps = panelIceps;
    }

    public boolean isPanelIceps() {
        return panelIceps;
    }

    public void setListaMultasDocumento(List<MultaDocumentoAfectaciones> listaMultasDocumento) {
        this.listaMultasDocumento = listaMultasDocumento;
    }

    public List<MultaDocumentoAfectaciones> getListaMultasDocumento() {
        return listaMultasDocumento;
    }

    public void setIndexTab(Integer indexTab) {
        this.indexTab = indexTab;
    }

    public Integer getIndexTab() {
        return indexTab;
    }

    public void setBtnImprimir(boolean btnImprimir) {
        this.btnImprimir = btnImprimir;
    }

    public boolean isBtnImprimir() {
        return btnImprimir;
    }

    public void setDetalleMulta(MultaDocumentoAfectaciones detalleMulta) {
        this.detalleMulta = detalleMulta;
    }

    public MultaDocumentoAfectaciones getDetalleMulta() {
        return detalleMulta;
    }

    public void setListaMultasDocumentoDetalle(List<MultaDocumentoAfectaciones> listaMultasDocumentoDetalle) {
        this.listaMultasDocumentoDetalle = listaMultasDocumentoDetalle;
    }

    public List<MultaDocumentoAfectaciones> getListaMultasDocumentoDetalle() {
        return listaMultasDocumentoDetalle;
    }

    public void setMontoTotalMultas(Long montoTotalMultas) {
        this.montoTotalMultas = montoTotalMultas;
    }

    public Long getMontoTotalMultas() {
        return montoTotalMultas;
    }

    public void setTotMulta(Long totMulta) {
        this.totMulta = totMulta;
    }

    public Long getTotMulta() {
        return totMulta;
    }

    public void setBoIds(String boIds) {
        this.boIds = boIds;
    }

    public String getBoIds() {
        return boIds;
    }

    public void setListaMultasRep(List<MultaDocumentoAfectaciones> listaMultasRep) {
        this.listaMultasRep = listaMultasRep;
    }

    public void setListaAfectacionesRep(List<ReporteAfectacionXAutoridadDTO> listaAfectacionesRep) {
        this.listaAfectacionesRep = listaAfectacionesRep;
    }

    public void setListaNumeroResolucion(List<MultaDocumentoAfectaciones> listaNumeroResolucion) {
        this.listaNumeroResolucion = listaNumeroResolucion;
    }

    public void setListaNumeroTemp(List<MultaDocumentoAfectaciones> listaNumeroTemp) {
        this.listaNumeroTemp = listaNumeroTemp;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }
    
    public void increment() {  
        progress++;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
}

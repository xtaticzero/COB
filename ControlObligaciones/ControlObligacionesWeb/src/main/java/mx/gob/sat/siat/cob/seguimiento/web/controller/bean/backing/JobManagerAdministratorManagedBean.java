package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.ErrorCampo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoJob;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PrioridadJob;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ProcesoDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoProcesamientoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoJobEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.PrioridadJobEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoProcesamientoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.BusinessException;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.jobmanager.ProcesoDetalleService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper.JobManagerAdministratorHelper;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("jobManagerAdminMB")
@Scope(value = "view")
public class JobManagerAdministratorManagedBean extends AbstractBaseMB {

    @Autowired
    private ProcesoDetalleService procesoDetalleService;
    @Autowired
    private SGTService sgtService;
    @Autowired
    private MailService mailService;
    @Autowired
    private EmailReporteProcesoDAO emailReporteProcesoDAO;
    private ProcesoDetalle procesoDetalle;
    private Proceso[] selectedAdd;
    private Proceso[] selectedExcAdd;
    private Integer[] numIntentos;
    private List<Proceso> lanzadores;
    private List<Proceso> excluyentes;
    private List<PrioridadJob> lstPrioridadJob;
    private List<EstadoJob> lstEstadoJob;
    private List<Proceso> actuales;
    private List<TipoProcesamientoDTO> lstTipoProcesamiento;
    private Proceso procesoSelected;
    private String idProcesoDetalle;
    private String headerActivoDesactivo;
    private String dialogActivoDesactivo;
    private JobManagerAdministratorHelper administratorHelper;
    private List<Proceso> cadenaLanzadores;
    private List<Proceso> lanzadoresConfirma;
    /**
     * Objeto que se mandara por correo, para ver el antes y despues del
     * proceso.
     */
    private ProcesoDetalle procesoDetalleActual;

    @PostConstruct
    public void init() {
        //Parametros de Seguridad
        obtenerAccesoUsrEmpleados();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_PROCESOS_BATCH);
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
            administratorHelper = new JobManagerAdministratorHelper();
            administratorHelper.setProjectId(paramMap.get("idProceso"));
            if (administratorHelper.getProjectId() != null) {
                procesoDetalle = procesoDetalleService.consultarProcesoDetalle(Integer.parseInt(administratorHelper.getProjectId()));
                if (procesoDetalle.getProceso().getLanzador() != null) {
                    procesoDetalle.getProceso().setPrioridad(PrioridadJobEnum.NULL.getIdVlue());
                }
                actualizarClon();
                if (administratorHelper.isInactivo(procesoDetalle.getProceso().getEstado())) {
                    administratorHelper.setEstado(EstadoJobEnum.INACTIVO.getIdVlue());
                } else {
                    administratorHelper.setEstado(EstadoJobEnum.ACTIVO.getIdVlue());
                }
                lanzadores = procesoDetalle.getLanzadores();
                excluyentes = procesoDetalle.getExcluyentes();
            } else {
                if (procesoDetalle != null && procesoDetalle.getProceso() != null
                        && procesoDetalle.getProceso().getIdProceso() != null) {
                    procesoDetalle = procesoDetalleService
                            .consultarProcesoDetalle(procesoDetalle.getProceso().getIdProceso());
                } else {
                    procesoDetalle = new ProcesoDetalle();
                    procesoDetalle.setProceso(new Proceso());
                    lanzadores = new ArrayList<Proceso>();
                    excluyentes = new ArrayList<Proceso>();
                    procesoDetalle.setLanzadores(lanzadores);
                    procesoDetalle.setExcluyentes(excluyentes);
                    procesoDetalle.getProceso().setEstado(EstadoJobEnum.ACTIVO.getIdVlue());
                    procesoDetalle.getProceso().setPrioridad(PrioridadJobEnum.NULL.getIdVlue());
                }
            }
            populateNumeroIntentos(ConstantsCatalogos.NUEVE);
            actuales = quitarLanzadorDelProceso(quitarLanzadorSeleccionado(
                    procesoDetalleService.consultarPorAgregar(getAllProces())));
            administratorHelper.clear();
            populateLstPrioridad();
            populateTipoProcesamiento();
            validaBtnRegresar(paramMap);
            visualizaTipoProcesamiento();
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
    }

    private void actualizarClon() {
        try {
            procesoDetalleActual = procesoDetalle.clone();
        } catch (CloneNotSupportedException ex) {
            getLogger().error(ex);
        }
    }

    public void guardado() {
        try {
            if (allRequeridos()) {
                if (isMinimoRequerido(administratorHelper.getMinimumLength())) {
                    procesoDetalleService.guardar(procesoDetalle);
                    enviarCorreo();
                    if (administratorHelper.getNumeroMensajes() == ConstantsCatalogos.UNO) {
                        super.addMessage("Transacción completada correctamente", "");
                    } else if (!administratorHelper.isActivadoGuardadoCadena()) {
                        super.addMessage("Transacción completada correctamente", "");
                    }
                    administratorHelper.clear();
                    actualizarClon();
                }
            }
        } catch (SGTServiceException sgtEx) {
            super.addErrorMessage(sgtEx.getMessage(), "");
            getLogger().error(sgtEx);
        } catch (BusinessException bsEx) {
            super.addErrorMessage(bsEx.getNotificacion().getMensaje(), "");
            super.addErrorMessage(bsEx.getNotificacion().getTitulo(), "");
            getLogger().error(bsEx);
            for (ErrorCampo er : bsEx.getErrores()) {
                if (er.getNombreCampo().equals("proceso.programacion")) {
                    administratorHelper.setMsgErrorProgramacion(er.getToolTip());
                } else if (er.getNombreCampo().equals("proceso.estado")) {
                    super.addErrorMessage(er.getToolTip(), "");
                } else if (er.getNombreCampo().equals("proceso.tipoProcesamiento")) {
                    administratorHelper.setMsgErrorTipoCadena(er.getToolTip());
                    administratorHelper.setTipoProcesamiento(false);
                } else if (er.getNombreCampo().equals("proceso.prioridad")) {
                    administratorHelper.setMsgErrorPrioridad(er.getToolTip());
                }
            }
        }
    }

    public void validaBtnRegresar(Map<String, String> paramMap) {
        if (paramMap != null) {
            if (paramMap.size() > ConstantsCatalogos.CERO) {
                administratorHelper.setBtnRegresar(true);
            } else {
                administratorHelper.setBtnRegresar(false);
            }
        }
    }

    public boolean isMinimoRequerido(int min) {
        boolean flag = true;
        if (procesoDetalle.getProceso().getNombre() != null) {
            if (procesoDetalle.getProceso().getNombre().length() < min) {
                administratorHelper.setMsgErrNomLength(true);
                flag = false;
            } else {
                administratorHelper.setMsgErrNomLength(false);
            }
        }
        if (procesoDetalle.getProceso().getDescripcion() != null) {
            if (procesoDetalle.getProceso().getDescripcion().length() < min) {
                administratorHelper.setMsgErrDescLength(true);
                flag = false;
            } else {
                administratorHelper.setMsgErrDescLength(false);
            }
        }
        return flag;
    }

    public boolean allRequeridos() {
        boolean flag = false;
        if (procesoDetalle.getProceso().getNombre().length() == ConstantsCatalogos.CERO) {
            administratorHelper.setMsgErrNomReq(true);
            flag = false;
        } else {
            administratorHelper.setMsgErrNomReq(false);
            flag = true;
        }
        if (procesoDetalle.getProceso().getDescripcion().length() == ConstantsCatalogos.CERO) {
            administratorHelper.setMsgErrDescReq(true);
            flag = false;
        } else {
            administratorHelper.setMsgErrDescReq(false);
            flag = true;
        }
        return flag;
    }

    public void redirigirJobManager() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("jobManager.jsf");
    }

    public void addProcesos() {
        if (selectedAdd != null) {
            lanzadores.addAll(Arrays.asList(selectedAdd));
            try {
                actuales = quitarLanzadorDelProceso(
                        quitarLanzadorSeleccionado(procesoDetalleService.consultarPorAgregar(getAllProces())));
            } catch (SGTServiceException ex) {
                getLogger().error(ex);
            }
            if (!lanzadores.isEmpty()) {
                administratorHelper.setTipoProcesamiento(true);
                procesoDetalle.getProceso().setTipoCadena(0);
                procesoDetalle.getProceso().setPrioridad(PrioridadJobEnum.NULL.getIdVlue());
                administratorHelper.setMsgErrorTipoCadena("");
                administratorHelper.setMsgErrorPrioridad("");
            }
        }
    }

    public void addExcProcesos() {
        if (selectedAdd != null) {
            excluyentes.addAll(Arrays.asList(selectedExcAdd));
            actualizaProcesos();
        }
    }

    public void removed() {
        lanzadores.remove(procesoSelected);
        actualizaProcesos();
        if (lanzadores.isEmpty()) {
            administratorHelper.setTipoProcesamiento(false);
            procesoDetalle.getProceso().setTipoCadena(0);
            procesoDetalle.getProceso().setPrioridad(PrioridadJobEnum.NULL.getIdVlue());
        }

    }

    public void removedExc() {
        excluyentes.remove(procesoSelected);
        actualizaProcesos();
    }

    public List<Proceso> getAllProces() {
        List<Proceso> lstTmp = new ArrayList<Proceso>();
        if (lanzadores != null) {
            for (Proceso itm : lanzadores) {
                lstTmp.add(itm);
            }
        }
        if (excluyentes != null) {
            for (Proceso itm : excluyentes) {
                lstTmp.add(itm);
            }
        }
        return lstTmp;
    }

    public void populateLstPrioridad() {
        lstPrioridadJob = new ArrayList<PrioridadJob>();
        lstEstadoJob = new ArrayList<EstadoJob>();
        for (int i = ConstantsCatalogos.CERO; i < ConstantsCatalogos.CUATRO; i++) {
            PrioridadJob prioridad = new PrioridadJob();
            EstadoJob estado;
            switch (i) {
                case ConstantsCatalogos.CERO:
                    estado = new EstadoJob();
                    prioridad.setStrPrioridad(PrioridadJobEnum.NULL.getEtiqueta());
                    prioridad.setPrioridad(PrioridadJobEnum.NULL.getIdVlue());
                    estado.setStrEstado(EstadoJobEnum.INACTIVO.getEtiqueta());
                    estado.setEstado(EstadoJobEnum.INACTIVO.getIdVlue());
                    lstEstadoJob.add(estado);
                    lstPrioridadJob.add(prioridad);
                    break;
                case ConstantsCatalogos.UNO:
                    estado = new EstadoJob();
                    prioridad.setStrPrioridad(PrioridadJobEnum.ALTA.getEtiqueta());
                    prioridad.setPrioridad(PrioridadJobEnum.ALTA.getIdVlue());
                    estado.setStrEstado(EstadoJobEnum.ACTIVO.getEtiqueta());
                    estado.setEstado(EstadoJobEnum.ACTIVO.getIdVlue());
                    lstEstadoJob.add(estado);
                    lstPrioridadJob.add(prioridad);
                    break;
                case ConstantsCatalogos.DOS:
                    prioridad.setStrPrioridad(PrioridadJobEnum.MEDIA.getEtiqueta());
                    prioridad.setPrioridad(PrioridadJobEnum.MEDIA.getIdVlue());
                    lstPrioridadJob.add(prioridad);
                    break;
                case ConstantsCatalogos.TRES:
                    prioridad.setStrPrioridad(PrioridadJobEnum.BAJA.getEtiqueta());
                    prioridad.setPrioridad(PrioridadJobEnum.BAJA.getIdVlue());
                    lstPrioridadJob.add(prioridad);
                    break;
                default:
                    break;
            }
        }

    }

    public void populateTipoProcesamiento() {
        //Aqui se hace la consulta y llenado de la lista
        lstTipoProcesamiento = new ArrayList<TipoProcesamientoDTO>();
        for (TipoProcesamientoEnum i : TipoProcesamientoEnum.values()) {
            TipoProcesamientoDTO item = new TipoProcesamientoDTO();
            item.setId(i.getIdVlue());
            item.setProcesamiento(i.getEtiqueta());
            lstTipoProcesamiento.add(item);
        }
    }

    public void populateNumeroIntentos(int maxInt) {
        numIntentos = new Integer[maxInt];
        for (int i = ConstantsCatalogos.CERO; i < maxInt; i++) {
            numIntentos[i] = (i + ConstantsCatalogos.UNO);
        }
    }

    public void visualizaTipoProcesamiento() {
        if (procesoDetalle.getProceso().getLanzador() != null) {
            administratorHelper.setTipoProcesamiento(!procesoDetalle.getProceso().getLanzador().isEmpty());
        } else {
            administratorHelper.setTipoProcesamiento(false);
        }

    }

    public void actualizaProcesos() {
        try {
            actuales = quitarLanzadorDelProceso(
                    quitarLanzadorSeleccionado(procesoDetalleService.consultarPorAgregar(getAllProces())));
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
    }

    private void llenaCadena(Proceso proceso) {
        try {
            cadenaLanzadores = procesoDetalleService.cadenaLanzadores(proceso);
            Proceso procesoInicial = proceso;
            for (Proceso p : cadenaLanzadores) {
                if (p.getLanzador() == null) {
                    procesoInicial = p;
                }
            }
            Set<Proceso> procesosSet = new HashSet<Proceso>();
            procesosSet.add(procesoInicial);
            procesosSet.addAll(procesoDetalleService.cadenaLanzadores(procesoInicial));
            cadenaLanzadores.clear();
            cadenaLanzadores.addAll(procesosSet);
            Collections.sort(cadenaLanzadores);
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
    }

    public void detalleCadenaLanzadores() {
        if (isSave()) {
            guardado();
        } else {
            llenaCadena(procesoDetalle.getProceso());
            if (administratorHelper.cambioEstado(procesoDetalle.getProceso().getEstado(), administratorHelper.getEstado())) {
                procesoDetalle.getProceso().setEstado(administratorHelper.getEstado());
            }
            if (isShowLanzadoresCadena()
                    && modifcanDetalleProceso()
                    && (!procesoDetalleActual.getProceso().getEstado().equals(procesoDetalle.getProceso().getEstado()))) {
                ejecutarShowVista("cadenaWV");
            } else if (modifcanDetalleProceso()) {
                guardado();
            } else {
                super.addWarningMessage("No existió cambio en ningún parámetro de la configuración actual del proceso.", "");
            }
        }
    }
    
    /**
     * Regresa true si es un proceso nuevo
     *
     * @param proceso
     * @return
     */
    public boolean isSave() {
        return procesoDetalle.getProceso().getIdProceso() == null;
    }

    public void guardarCadena() {
        llenaCadena(procesoDetalle.getProceso());
        if (procesoDetalle.getProceso().getEstado().equals(EstadoJobEnum.INACTIVO.getIdVlue())) {

            List<Proceso> ejecutandose = administratorHelper.lanzadoresPorEjecutarEnEjecucion(cadenaLanzadores);
            if (ejecutandose.size() > ConstantsCatalogos.CERO) {
                List<String> procesosEjecunatdo = new ArrayList<String>();
                for (Proceso ejecutando : ejecutandose) {
                    procesosEjecunatdo.add(ejecutando.getDescripcion());
                }
                super.addWarningMessage(ConstantsCatalogos.MSG_DESACTIVA_CADENA_POR_EJECUCION_EN_EJECUCION.
                        replace("[*]", procesosEjecunatdo.toString().replace("[", "").replace("]", "")), "");
            } else {
                validaGuardadoCadena();
            }
        } else {
            validaGuardadoCadena();
        }
    }

    public void validaGuardadoCadena() {
        try {
            administratorHelper.setActivadoGuardadoCadena(true);
            guardado();
            int estado = procesoDetalle.getProceso().getEstado();
            /**
             * procesoPadre se le asigna el objeto ya actualizado, para despues
             * reasignarlo y que la vista no pierda su foco.
             */
            ProcesoDetalle procesoPadre = procesoDetalle.clone();
            for (Proceso p : cadenaLanzadores) {

                if (estado == ConstantsCatalogos.UNO
                        && p.getEstado().equals(EstadoJobEnum.INACTIVO.getIdVlue())) {
                    ejecutaGuardadoCadena(p, estado);
                } else if (estado == ConstantsCatalogos.CERO) {
                    ejecutaGuardadoCadena(p, estado);
                }
            }
            administratorHelper.setNumeroMensajes(ConstantsCatalogos.UNO);
            administratorHelper.setActivadoGuardadoCadena(false);
            /**
             * Se regresa el procesoDetalle con los valores correspondientes.
             */
            procesoDetalle = procesoPadre.clone();
            actualizarClon();

        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        } catch (CloneNotSupportedException ex) {
            getLogger().error(ex);
        }

    }

    public void ejecutaGuardadoCadena(Proceso p, int estado) throws SGTServiceException {
        administratorHelper.setNumeroMensajes(administratorHelper.getNumeroMensajes() + 1);
        procesoDetalle = procesoDetalleService.consultarProcesoDetalle(p.getIdProceso());
        int estadoCadena = procesoDetalle.getProceso().getEstado();
        procesoDetalle.getProceso().setEstado(estado);
        actualizarClon();
        procesoDetalleActual.getProceso().setEstado(estadoCadena);
        guardado();
    }

    private void enviarCorreo() {
        try {
            List<ParametrosSgtDTO> parametrosVigentesSgt = sgtService.obtenerParametrosVigentesSgt();
            String ambiente = "";
            for (ParametrosSgtDTO parametro : parametrosVigentesSgt) {
                if (parametro.getIdParametro() == ConstantsCatalogos.SEIS) {
                    ambiente = parametro.getValor();
                }
            }
            List<EmailReporteProceso> emails = emailReporteProcesoDAO.todosLosEmailReporteProceso(false);
            StringBuilder sEmails = new StringBuilder("");
            for (EmailReporteProceso emailReporteProceso : emails) {
                sEmails.append(emailReporteProceso.getCorreoElectronico()).append(",");
                if (emailReporteProceso.getCorreoElectronicoAlterno() != null) {
                    sEmails.append(emailReporteProceso.getCorreoElectronicoAlterno()).append(",");
                }
            }
            String[] destinatarios = sEmails.toString().split(",");
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("El usuario ").append(getAccesoUsr().getUsuario()).append(" - ")
                    .append(getAccesoUsr().getNombreCompleto())
                    .append(" modificó parámetros de configuración del proceso; a continuación se muestra el detalle: <br/><br/>")
                    .append(administratorHelper.crearTabla(procesoDetalleActual, procesoDetalle))
                    .append("<br/><br/> Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electrónico no monitoreada.");
            getLogger().info(mensaje);
            mailService.enviarCorreoPara(destinatarios, "MAT CO " + ambiente + " - " + "Modificación a los parámetros de configuración del proceso", mensaje.toString());
        } catch (MessagingException ex) {
            getLogger().error(ex.getMessage(), ex);
        }
    }

    public void validaLanzadores() {
        if (cadenaLanzadores.size() > ConstantsCatalogos.CERO) {
            if (procesoDetalle.getProceso().getEstado().equals(EstadoJobEnum.ACTIVO.getIdVlue())) {
                validaActivacionProceso();
            } else if (procesoDetalle.getProceso().getEstado().equals(EstadoJobEnum.INACTIVO.getIdVlue())) {
                validaDesactivacionProceso();
            }
        }
    }

    /**
     * Metodo de validaciones para la activacion de un proceso.
     */
    private void validaActivacionProceso() {
        if (administratorHelper.estanDiferenteInactivo(procesoDetalle.getLanzadores())) {
            administratorHelper.setConfirmarDialogo(false);
            guardado();
        } else if (administratorHelper.estanInactivos(procesoDetalle.getLanzadores())) {
            administratorHelper.setMensajeInfo(ConstantsCatalogos.MSG_PROCESOS_NO_INACTIVOS.replace(
                    administratorHelper.getProjectId(), administratorHelper.getProjectId()));
            administratorHelper.setMensajeAccion(ConstantsCatalogos.MSG_ELIMINAR_LANZADORES);
            lanzadoresConfirma = procesoDetalle.getLanzadores();
            administratorHelper.setConfirmarDialogo(true);
            administratorHelper.setMostrarBotones(false);
            ejecutarShowVista("activacion1");
        } else if (administratorHelper.numeroDiferenteDeInactivos(procesoDetalle.getLanzadores()) > 0) {
            administratorHelper.setMensajeInfo(ConstantsCatalogos.MSG_LANZADORES_INACTIVOS);
            administratorHelper.setMensajeAccion(ConstantsCatalogos.MSG_ACTIVAR_PROCESO);
            administratorHelper.setConfirmarDialogo(true);
            administratorHelper.setMostrarBotones(true);
            lanzadoresConfirma = administratorHelper.lanzadoresInactivos(procesoDetalle.getLanzadores());
            ejecutarShowVista("activacion1");
        }
    }

    /**
     * Metodo de validaciones para la desactivacion de un proceso.
     */
    private void validaDesactivacionProceso() {
        if (desactivoPrimeroEnSerie()) {
            getLogger().info("Desactivando primero en serie");
        } else if (desactivoProcesoIntermedio()) {
            getLogger().info("Desactivando proceso intermedio");
        } else if (desactivoUltimoProceso()) {
            getLogger().info("Desactivando ultimo proceso");
        }
    }

    /**
     * Regresa true cuando se lleva a cabo la desactivacion del primer proceso
     * de la serie.
     *
     * @return
     */
    private boolean desactivoPrimeroEnSerie() {
        try {
            if (isPrimeroEnCadena(procesoDetalle.getProceso())) {
                List<Proceso> lanzadoresProceso = procesoDetalleService
                        .consultarPorLanzadores(procesoDetalle.getProceso().getIdProceso().toString());
                /**
                 * Si al menos un lanzador esta inactivo, mensaje toma su lugar.
                 */
                if (administratorHelper.lanzadoresDiferentesDeInactivos(lanzadoresProceso).size() == ConstantsCatalogos.UNO) {
                    List<Proceso> procesosHijos = procesoDetalleService.procesosHijos(procesoDetalle.getProceso());
                    for (Proceso pHijo : procesosHijos) {
                        if (pHijo.getEstado().intValue() != EstadoJobEnum.INACTIVO.getIdVlue()) {
                            administratorHelper.setMensajeInfo(ConstantsCatalogos.MSG_PROCESO_INICIADOR_SERIE
                                    .replace("[*]", pHijo.getDescripcion()));
                            break;
                        }
                    }
                    administratorHelper.setMensajeAccion(ConstantsCatalogos.MSG_DESACTIVAR_PROCESO);
                    administratorHelper.setConfirmarDialogo(true);
                    ejecutarShowVista("activacion1");
                    return true;
                } /**
                 * Si existe mas de un lanzador diferente de inactivo, mensaje
                 * fallido, no puede tomar la desicion de cual lanzador tomar
                 * como cabecera.
                 */
                else if (administratorHelper.lanzadoresDiferentesDeInactivos(lanzadoresProceso).size() > ConstantsCatalogos.UNO) {
                    administratorHelper.setMensajeInfo(ConstantsCatalogos.MSG_PROCESO_INICIADOR_SERIE_Y_LANZADOR);
                    lanzadoresConfirma = lanzadoresProceso;
                    administratorHelper.setConfirmarDialogo(true);
                    administratorHelper.setMostrarBotones(false);
                    ejecutarShowVista("activacion1");
                    return true;
                } /**
                 * Si los lanzadores estan inactivos, guarda.
                 */
                else if (!procesoDetalle.getLanzadores().isEmpty()) {
                    if (administratorHelper.estanInactivos(procesoDetalle.getLanzadores())) {
                        guardado();
                        administratorHelper.setConfirmarDialogo(false);
                        return true;
                    }
                }
            }
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
        return false;
    }

    /**
     * Regresa true cuando se lleva a cabo la desactivacion de un proceso
     * intermedio.
     *
     * @return
     */
    private boolean desactivoProcesoIntermedio() {
        try {
            if (isIntermedioEnCadena(procesoDetalle.getProceso())) {
                List<Proceso> hijos = procesoDetalleService
                        .consultarPorLanzadores(procesoDetalle.getProceso().getIdProceso().toString());
                ProcesoDetalle procesoDetalleHijo = validaHijoAEvaluar(hijos);
                if (procesoDetalleHijo != null) {
                    /**
                     * Si su lanzador, solo tiene un lanzador y esta con estado
                     * diferente de inactivo manda mensaje de informacion y
                     * accion.
                     */
                    if (procesoDetalleHijo.getLanzadores().size() == ConstantsCatalogos.UNO) {
                        if (procesoDetalleHijo.getLanzadores().get(ConstantsCatalogos.CERO)
                                .getEstado().intValue() != EstadoJobEnum.INACTIVO.getIdVlue()) {
                            administratorHelper.setMensajeInfo(ConstantsCatalogos.MSG_DESACTIVACION_PROCESO_SERIE
                                    .replace("[*]", procesoDetalleHijo.getProceso().getDescripcion()));
                            administratorHelper.setMensajeAccion(ConstantsCatalogos.MSG_EJECUCION_PROCESO_INDEPENDIENTE
                                    .replace("[*]", procesoDetalleHijo.getProceso().getDescripcion())
                                    .replace("[**]", procesoDetalle.getProceso().getDescripcion()));
                            administratorHelper.setConfirmarDialogo(true);
                            administratorHelper.setMostrarBotones(false);
                            ejecutarShowVista("activacion1");
                            return true;
                        }
                    }/**
                     * Si su lanzador, tiene mas de un lanzador.
                     */
                    else if (procesoDetalleHijo.getLanzadores().size() > ConstantsCatalogos.UNO) {
                        /**
                         * Si su lanzador, tiene mas de un lanzador y todos
                         * estan con estado inactivo manda mensaje de
                         * informacion y accion.
                         */
                        ProcesoDetalle procesoDetalleDesactiva = procesoDetalleHijo;
                        procesoDetalleDesactiva.setLanzadores(quitarLanzadorSeleccionado(procesoDetalleDesactiva.getLanzadores()));
                        if (administratorHelper.estanInactivos(procesoDetalleDesactiva.getLanzadores())) {
                            administratorHelper.setMensajeInfo(ConstantsCatalogos.MSG_DESACTIVACION_PROCESO_SERIE
                                    .replace("[*]", procesoDetalleHijo.getProceso().getDescripcion()));
                            administratorHelper.setConfirmarDialogo(true);
                            administratorHelper.setMostrarBotones(false);
                            ejecutarShowVista("activacion1");
                            return true;
                        }/**
                         * Si su lanzador, tiene mas de un lanzador y todos
                         * estan con estado diferente de inactivo; se puede
                         * guardar.
                         */
                        else if (administratorHelper.estanDiferenteInactivo(procesoDetalleDesactiva.getLanzadores())) {
                            guardado();
                            administratorHelper.setConfirmarDialogo(false);
                            return true;
                        }
                    }
                }
            }
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
        return false;
    }

    /**
     * Regresa true cuando se lleva a cabo la desactivacion de un ultimo
     * proceso.
     *
     * @return
     */
    private boolean desactivoUltimoProceso() {
        try {
            if (procesoDetalleService
                    .isUltimoEnCadena(procesoDetalle.getProceso())) {
                guardado();
                administratorHelper.setConfirmarDialogo(false);
                return true;
            }
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
        return false;
    }

    /**
     * Regresa true si es el primer proceso dentro de la serie
     *
     * @param proceso
     * @return
     */
    private boolean isPrimeroEnCadena(Proceso proceso) {
        try {
            return procesoDetalleService.isPrimeroEnCadena(proceso);
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
        return false;
    }

    /**
     * Regresa true si es un proceso intermedio dentro de la serie.
     *
     * @param proceso
     * @return
     */
    private boolean isIntermedioEnCadena(Proceso proceso) {
        try {
            return !isPrimeroEnCadena(proceso) && !procesoDetalleService.isUltimoEnCadena(proceso);
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
        return false;
    }

    /**
     * Determina que hijo debe evaluar primero; debe retornar el hijo que solo
     * tenga un lanzador, de no haber uno en la lista regresa el primero.
     *
     * @param hijos
     * @return
     */
    private ProcesoDetalle validaHijoAEvaluar(List<Proceso> hijos) {
        try {
            List<ProcesoDetalle> detallesHijos = new ArrayList<ProcesoDetalle>();
            for (Proceso proceso : hijos) {
                detallesHijos.add(procesoDetalleService.consultarProcesoDetalle(proceso.getIdProceso()));
            }
            for (ProcesoDetalle detalleHijo : detallesHijos) {
                if (detalleHijo.getLanzadores().size() == ConstantsCatalogos.UNO
                        && detalleHijo.getProceso().getEstado().intValue()
                        != EstadoJobEnum.INACTIVO.getIdVlue()) {
                    return detalleHijo;
                }
            }
            for (ProcesoDetalle detalleHijo : detallesHijos) {
                if (detalleHijo.getLanzadores().size() > ConstantsCatalogos.UNO
                        && detalleHijo.getProceso().getEstado().intValue()
                        != EstadoJobEnum.INACTIVO.getIdVlue()) {
                    return detalleHijo;
                }
            }
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
        return null;
    }

    /**
     * Ejecuta el componente dialog pot id.
     *
     * @param activacionShow
     *
     */
    private void ejecutarShowVista(String activacionShow) {
        RequestContext.getCurrentInstance().execute(activacionShow + ".show()");
    }

    /**
     * Regresa true si en la pantalla se modifico un valor.
     *
     * @return
     */
    private boolean modifcanDetalleProceso() {
        if (!(procesoDetalleActual.getProceso().equals(procesoDetalle.getProceso()))) {
            return true;
        }
        if (!procesoDetalle.getLanzadores().isEmpty()) {
            Integer sizeLanzadores = procesoDetalle.getLanzadores().size();
            Integer lanzadoresIguales = 0;
            for (Proceso lanzadorClon : procesoDetalleActual.getLanzadores()) {
                for (Proceso lanzador : procesoDetalle.getLanzadores()) {
                    if (lanzadorClon.equals(lanzador)) {
                        lanzadoresIguales++;
                    }
                }
            }
            if ((!lanzadoresIguales.equals(sizeLanzadores))
                    || (procesoDetalle.getLanzadores().size() != procesoDetalleActual.getLanzadores().size())) {
                return true;
            }
        }
        if (!procesoDetalle.getExcluyentes().isEmpty()) {
            Integer sizeExcluyentes = procesoDetalle.getExcluyentes().size();
            Integer exluyentesIguales = 0;
            for (Proceso excluyenteClon : procesoDetalleActual.getExcluyentes()) {
                for (Proceso excluyente : procesoDetalle.getExcluyentes()) {
                    if (excluyenteClon.equals(excluyente)) {
                        exluyentesIguales++;
                    }
                }
            }
            if ((!exluyentesIguales.equals(sizeExcluyentes))
                    || (procesoDetalle.getExcluyentes().size() != procesoDetalleActual.getExcluyentes().size())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Regresa lista sin el proceso seleccionado.
     *
     * @param lanzadores
     *
     * @return
     */
    private List<Proceso> quitarLanzadorSeleccionado(List<Proceso> lanzadores) {
        List<Proceso> lanzadoresSinSeleccionado = new ArrayList<Proceso>();
        for (Proceso p : lanzadores) {
            if (!p.getIdProceso().equals(procesoDetalle.getProceso().getIdProceso())) {
                lanzadoresSinSeleccionado.add(p);
            }
        }
        return lanzadoresSinSeleccionado;
    }

    /**
     * Regresa lista sin procesos que ciclen el job manager.
     *
     * @param lanzadores
     * @return
     */
    private List<Proceso> quitarLanzadorDelProceso(List<Proceso> lanzadores) {
        Set<Proceso> lanzadoresDelProceso = new HashSet<Proceso>();
        try {
            for (Proceso lanzadorAdd : lanzadores) {
                List<Proceso> padres = procesoDetalleService.procesosPadres(lanzadorAdd);
                if (padres.isEmpty()) {
                    lanzadoresDelProceso.add(lanzadorAdd);
                } else {
                    int vecesEncontradas = 0;
                    for (Proceso padre : padres) {
                        if (padre.getIdProceso().equals(procesoDetalle.getProceso().getIdProceso())) {
                            vecesEncontradas++;
                        }
                    }
                    if (vecesEncontradas == 0) {
                        lanzadoresDelProceso.add(lanzadorAdd);
                    }
                }
            }
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        }
        return new ArrayList<Proceso>(lanzadoresDelProceso);
    }

    /**
     * Getters ans setters.
     */
    /**
     * @param lanzadores
     */
    public void setLanzadores(List<Proceso> lanzadores) {
        this.lanzadores = lanzadores;
    }

    /**
     *
     * @return
     */
    public List<Proceso> getLanzadores() {
        return lanzadores;
    }

    /**
     *
     * @param excluyentes
     */
    public void setExcluyentes(List<Proceso> excluyentes) {
        this.excluyentes = excluyentes;
    }

    /**
     *
     * @return
     */
    public List<Proceso> getExcluyentes() {
        return excluyentes;
    }

    /**
     *
     * @param procesoSelected
     */
    public void setProcesoSelected(Proceso procesoSelected) {
        this.procesoSelected = procesoSelected;
    }

    /**
     *
     * @return
     */
    public Proceso getProcesoSelected() {
        return procesoSelected;
    }

    /**
     *
     * @param procesoDetalle
     */
    public void setProcesoDetalle(ProcesoDetalle procesoDetalle) {
        this.procesoDetalle = procesoDetalle;
    }

    /**
     *
     * @return
     */
    public ProcesoDetalle getProcesoDetalle() {
        return procesoDetalle;
    }

    /**
     *
     * @param lstEstadoJob
     */
    public void setLstEstadoJob(List<EstadoJob> lstEstadoJob) {
        this.lstEstadoJob = lstEstadoJob;
    }

    /**
     *
     * @return
     */
    public List<EstadoJob> getLstEstadoJob() {
        return lstEstadoJob;
    }

    /**
     *
     * @param actuales
     */
    public void setActuales(List<Proceso> actuales) {
        this.actuales = actuales;
    }

    /**
     *
     * @return
     */
    public List<Proceso> getActuales() {
        return actuales;
    }

    /**
     *
     * @return
     */
    public Integer[] getNumIntentos() {
        if (numIntentos != null) {
            return (Integer[]) numIntentos.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param numIntentos
     */
    public void setNumIntentos(Integer[] numIntentos) {
        if (numIntentos != null) {
            this.numIntentos = (Integer[]) numIntentos.clone();
        }
    }

    /**
     *
     * @return
     */
    public List<PrioridadJob> getLstPrioridadJob() {
        return lstPrioridadJob;
    }

    /**
     *
     * @param lstPrioridadJob
     */
    public void setLstPrioridadJob(List<PrioridadJob> lstPrioridadJob) {
        this.lstPrioridadJob = lstPrioridadJob;
    }

    /**
     *
     * @return
     */
    public Proceso[] getSelectedAdd() {
        if (selectedAdd != null) {
            return (Proceso[]) selectedAdd.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param selectedAdd
     */
    public void setSelectedAdd(Proceso[] selectedAdd) {
        if (selectedAdd != null) {
            this.selectedAdd = (Proceso[]) selectedAdd.clone();
        }
    }

    /**
     *
     * @return
     */
    public Proceso[] getSelectedExcAdd() {
        if (selectedExcAdd != null) {
            return (Proceso[]) selectedExcAdd.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param selectedExcAdd
     */
    public void setSelectedExcAdd(Proceso[] selectedExcAdd) {
        if (selectedExcAdd != null) {
            this.selectedExcAdd = (Proceso[]) selectedExcAdd.clone();
        }
    }

    /**
     *
     * @return
     */
    public List<TipoProcesamientoDTO> getLstTipoProcesamiento() {
        return lstTipoProcesamiento;
    }

    /**
     *
     * @param lstTipoProcesamiento
     */
    public void setLstTipoProcesamiento(List<TipoProcesamientoDTO> lstTipoProcesamiento) {
        this.lstTipoProcesamiento = lstTipoProcesamiento;
    }

    /**
     *
     * @return
     */
    public String getIdProcesoDetalle() {
        return idProcesoDetalle;
    }

    /**
     *
     * @param idProcesoDetalle
     */
    public void setIdProcesoDetalle(String idProcesoDetalle) {
        this.idProcesoDetalle = idProcesoDetalle;
    }

    /**
     *
     * @return
     */
    public List<Proceso> getCadenaLanzadores() {
        return cadenaLanzadores;
    }

    /**
     *
     * @return
     */
    public List<Proceso> getLanzadoresConfirma() {
        return lanzadoresConfirma;
    }

    /**
     *
     * @return
     */
    public boolean isShowLanzadoresConfirma() {
        if (lanzadoresConfirma != null) {
            return lanzadoresConfirma.size() > 0;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isShowLanzadoresCadena() {
        if (cadenaLanzadores != null) {
            return cadenaLanzadores.size() > 0;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getHeaderActivoDesactivo() {
        if (procesoDetalle.getProceso().getEstado() == 1) {
            headerActivoDesactivo = ConstantsCatalogos.ACTIVACION_PROCESOS;
        } else {
            headerActivoDesactivo = ConstantsCatalogos.DESACTIVACION_PROCESOS;
        }
        return headerActivoDesactivo;
    }

    /**
     *
     * @return
     */
    public String getDialogActivoDesactivo() {
        if (procesoDetalle.getProceso().getEstado() == 1) {
            dialogActivoDesactivo = ConstantsCatalogos.DIALOGO_ACTIVACION_JM;
        } else {
            dialogActivoDesactivo = ConstantsCatalogos.DIALOGO_DESACTIVACION_JM;
        }
        return dialogActivoDesactivo;
    }

    /**
     *
     * @return
     */
    public JobManagerAdministratorHelper getAdministratorHelper() {
        return administratorHelper;
    }
    
}

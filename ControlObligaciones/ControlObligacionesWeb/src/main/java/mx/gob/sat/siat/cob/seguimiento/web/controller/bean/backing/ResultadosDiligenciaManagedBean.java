package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoDiligencia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ResultadoDiligencia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.OpcionResultadoDiligenciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.UsuarioEFService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.ArchivoDiligenciaService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.util.constante.FilesPath;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.util.CreaArchivoSistema;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("resultDig")
@Scope(value = "view")
public class ResultadosDiligenciaManagedBean extends AbstractBaseMB {

    public ResultadosDiligenciaManagedBean() {
        super();
    }
    private static final String RUTA_ARCHIVO_DILIGENCIA = "/siat/cob/tmp/rd_archivoResultado";
    private static final String RESULTADOS_DILIGENCIAS_EN_EJECUCION = "El proceso de carga de resultados se está ejecutando. Al finalizar exitosamente recibirá un correo electrónico de notificación";
    @Value("#{properties['ruta.carpeta.temporal']}")
    private String carpetaArchivoDiligencia;
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private ArchivoDiligenciaService archivoDiligenciaService;
    @Autowired
    private UsuarioEFService usuarioEFService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ConcurrenceService concurrenceServiceImpl;
    private boolean mostrarBotonDescarga = false;
    private OpcionResultadoDiligenciaEnum selected = OpcionResultadoDiligenciaEnum.VACIO;
    private UploadedFile file;
    private List<CatalogoBase> comboResultados;
    private ResultadoDiligencia input = new ResultadoDiligencia();
    private String rutaArchivoResultado;
    private String rfc;
    private boolean mostrarPaneles = false;
    private Integer numeroRegistroDiligencias;
    private Integer numeroRegistroDiligenciasRegistrados;
    private String nombreArchivoDiligencias;
    private Date fechaCarga;
    private String fechaCargaStr;
    private boolean mostrarBotonDescargaArchivo;
    private boolean mostrarMensajeArchivo;
    private boolean mostrarPanelArchivo = false;
    private List<UsuarioEF> usuarioEF = new ArrayList<UsuarioEF>();
    private Integer porcentaje;
    private boolean isCargaPorArchivo = false;
    private boolean formatoFechaIncorrecta = false;
    private boolean isEstadoValido = false;
    private String muestraMensaje;
    private Long entidad;
    private String firma;
    private boolean procesoActivo = true;
    private boolean cargaExitosa = false;
    private int counter = 0;
    private final static int MAX_NUM_FILES = 1;

    @PostConstruct
    public void init() {
        try {
            obtenerAccesoUsrEmpleados();
            autorizar(ConstantsCatalogos.IDENTIFICADOR_RESULTADOS_DILIGENCIA);
            rfc = getAccesoUsr().getUsuario();
            usuarioEF = usuarioEFService.obtenerUsuarioPorRfcCorto(rfc);
            if (usuarioEF.size() > 0) {
                input.setClaveEf(usuarioEF.get(0).getIdEntidadFederativa());
                mostrarPaneles = true;
                comboResultados = documentoService.obtenerResultadosDiligenciacion();
                consultaArchivoPrevio();
            }
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        } finally {
            limpiarCampos();
        }
    }

    public void guardarArchivo() {
        if (file != null) {
            try {
                generaFirma(usuarioEF.get(0).getIdEntidadFederativa());
            } catch (SGTServiceException ex) {
                getLogger().error(ex);
                file = null;
                super.addErrorMessage(ex.getMessage(), "");
                return;
            }
            try {
                int numeroRegDil = 0;
                int numeroRegDilReg = 0;
                double cien = 100;
                porcentaje = 0;

                CreaArchivoSistema.cargarResultadoDiligencia(file, carpetaArchivoDiligencia);
                StringBuilder ruta = new StringBuilder();
                ruta.append(carpetaArchivoDiligencia).append(file.getFileName());
                File server = leeerArchivoCreado(ruta.toString());
                List<ResultadoDiligencia> resultados = new ArrayList<ResultadoDiligencia>();
                ArchivoDiligencia archivo = new ArchivoDiligencia();

                List<String> info = procesarArchivoCargado(IOUtils.toString(new FileInputStream(server)));
                isCargaPorArchivo = true;
                if (!info.isEmpty()) {
                    numeroRegDil = info.size();
                }
                Double random = Math.random();
                StringBuilder srandom = new StringBuilder(random.toString().substring(2, 10));
                srandom.append(FilesPath.TYPE_TXT);
                rutaArchivoResultado = RUTA_ARCHIVO_DILIGENCIA + "_" + srandom.toString();
                for (String s : info) {
                    input = new ResultadoDiligencia();
                    input.setClaveEf(usuarioEF.get(0).getIdEntidadFederativa());
                    resultados.add(documentoService.actualizarBitacoraDocumento(getInput(s.split("\\|")), OpcionResultadoDiligenciaEnum.REGISTRAR,
                            getAccesoUsr().getUsuario(), isCargaPorArchivo, formatoFechaIncorrecta, isEstadoValido));
                    numeroRegDilReg += 1;
                    porcentaje = new Double(new Double(numeroRegDilReg) / new Double(numeroRegDil) * cien).intValue();
                    archivo.setIdEntidadFederativa(usuarioEF.get(0).getIdEntidadFederativa());
                    archivo.setNombreArchivoCarga(server.getName());
                    archivo.setRutaArchivoResultado(rutaArchivoResultado);
                    archivo.setTotalRegistrosCarga(numeroRegDil);
                    archivo.setTotalRegistrosProcesados(numeroRegDilReg);
                    isEstadoValido = false;
                    formatoFechaIncorrecta = false;
                    archivoDiligenciaService.agregaArchivoDiligencia(archivo);
                }
                CreaArchivoSistema.crearArchivoResultadoDiligencias(resultados, rutaArchivoResultado);
                archivoDiligenciaService.agregaRutaArchivoDiligencia(archivo);
                setMostrarBotonDescarga(true);
                input = new ResultadoDiligencia();
                input.setClaveEf(usuarioEF.get(0).getIdEntidadFederativa());
                cargaExitosa = true;
//                enviarCorreo();
                file = null;
                desbloqueaFirma();
                super.addMessage("Archivo procesado correctamente, para obtenerlo presione el bot\u00f3n Descargar", "");
            } catch (IOException e) {
                setMostrarBotonDescarga(false);
                getLogger().error(e.getMessage());
                super.addErrorMessage("Error al procesar el archivo", "");
            } catch (SGTServiceException e) {
                file = null;
                getLogger().error(e.getMessage());
                setMostrarBotonDescarga(false);
                cargaExitosa = false;
//                enviarCorreo();
                super.addErrorMessage(e.getMessage(), "");
            } catch (ParseException ex) {
                setMostrarBotonDescarga(false);
                getLogger().error(ex.getMessage());
                super.addErrorMessage("Formato de fecha incorrecto, Ingrese DD/MM/AAAA", "");
            } finally {
                limpiarCampos();
                isCargaPorArchivo = false;
                consultaArchivoPrevio();
                try {
                    desbloqueaFirma();
                } catch (SGTServiceException ex) {
                    getLogger().error(ex.getMessage());
                }
            }
        }
    }

    private void consultaArchivoPrevio() {
        ArchivoDiligencia archivoDiligencia = archivoDiligenciaService.obtenerArchivoDiligencia(
                usuarioEF.get(0).getIdEntidadFederativa());
        if (archivoDiligencia != null) {
            if (!archivoDiligencia.getRutaArchivoResultado().equals("NULL")) {
                setMostrarPanelArchivo(true);
                numeroRegistroDiligencias = archivoDiligencia.getTotalRegistrosCarga();
                numeroRegistroDiligenciasRegistrados = archivoDiligencia.getTotalRegistrosProcesados();
                nombreArchivoDiligencias = archivoDiligencia.getNombreArchivoCarga();
                fechaCarga = archivoDiligencia.getFechaCarga();
                fechaCargaStr = archivoDiligencia.getFechaCargaStr();
                rutaArchivoResultado = archivoDiligencia.getRutaArchivoResultado();

                File arc = new File(rutaArchivoResultado);
                if (arc.exists()) {
                    setMostrarBotonDescargaArchivo(true);
                    setMostrarMensajeArchivo(false);
                } else {
                    muestraMensaje = ConstantsCatalogos.MSG_CARGA_DILIGENCIA;
                    setMostrarMensajeArchivo(true);
                    setMostrarBotonDescargaArchivo(false);
                }
            } else {
                setMostrarPanelArchivo(true);
                numeroRegistroDiligencias = archivoDiligencia.getTotalRegistrosCarga();
                numeroRegistroDiligenciasRegistrados = archivoDiligencia.getTotalRegistrosProcesados();
                nombreArchivoDiligencias = archivoDiligencia.getNombreArchivoCarga();
                fechaCarga = archivoDiligencia.getFechaCarga();
                fechaCargaStr = archivoDiligencia.getFechaCargaStr();
                rutaArchivoResultado = archivoDiligencia.getRutaArchivoResultado();
                if (procesoActivo) {
                    muestraMensaje = RESULTADOS_DILIGENCIAS_EN_EJECUCION;
                } else {
                    muestraMensaje = ConstantsCatalogos.MSG_CARGA_DILIGENCIA_INTERRUMPIDA;
                }
                setMostrarMensajeArchivo(true);
                setMostrarBotonDescargaArchivo(false);
            }
        }
    }

    private ResultadoDiligencia getInput(String[] args) throws ParseException {
        String regex = "^([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})$";
        int tamArgs = args.length;
        if (tamArgs == ConstantsCatalogos.CUATRO) {
            input.setNumeroControl(args[0].replaceAll("\\s+", ""));
            input.setResultado(args[1].replaceAll("\\s+", ""));
            if (input.getResultado().equals("")) {
                isEstadoValido = true;
            }
            String fechaNotificacion = args[2].replaceAll("\\s+", "");
            String fechaCitatorio = args[3].replaceAll("\\s+", "");
            boolean cumplePatronCitatorio = true;
            if (!fechaCitatorio.equals("null")) {
                cumplePatronCitatorio = Pattern.matches(regex, fechaCitatorio);
            }
            boolean cumplePatronNotificacion = Pattern.matches(regex, fechaNotificacion);
            if (cumplePatronNotificacion && cumplePatronCitatorio) {
                input.setFechaDiligencia(Utilerias.formatearFechaDDMMAAAAHHMM(fechaNotificacion));
                if (!fechaCitatorio.equals("null")) {
                    input.setFechaCitatorio(Utilerias.formatearFechaDDMMAAAAHHMM(fechaCitatorio));
                }
            } else {
                formatoFechaIncorrecta = true;
            }
        } else {
            input.setNumeroControl(args[0].replaceAll("\\s+", ""));
            input.setResultado(args[1].replaceAll("\\s+", ""));
        }
        return input;
    }

    private List<String> procesarArchivoCargado(String cadena) throws SGTServiceException {
        final int TAMANIO = 4;
        char finCadena = '|';
        List<String> info = new ArrayList<String>();
        char car;
        String tmpString = cadena.replace("\n", "").replace("\r", "");
        car = tmpString.charAt(tmpString.length() - 1);
        String cadenaTmp = cadena.replace("||||", "|null|null|null|").replace("|||", "|null|null|").replace("||", "|null|");
        if (car == (finCadena)) {
            String[] arr = cadenaTmp.replace("\n", "").replace("\r", "").trim().split("\\|");
            if (arr.length % TAMANIO == 0) {
                for (int i = 0; i < (arr.length / TAMANIO); i++) {
                    input = new ResultadoDiligencia();
                    input.setClaveEf(usuarioEF.get(0).getIdEntidadFederativa());
                    int multNosControl = 0 + (TAMANIO * i);
                    int multResultados = 1 + (TAMANIO * i);
                    int multFechaNotificacion = 2 + (TAMANIO * i);
                    int multFechaCitatorio = 3 + (TAMANIO * i);
                    info.add(arr[multNosControl] + "|" + arr[multFechaNotificacion] + "|" + arr[multResultados] + "|" + arr[multFechaCitatorio]);
                }
            } else {
                throw new SGTServiceException("Formato de Archivo Inv\u00e1lido");
            }
        } else {
            throw new SGTServiceException("Formato de Archivo Inv\u00e1lido");
        }
        return info;
    }

    public StreamedContent getArchivoResultado() {
        try {
            File arc = new File(rutaArchivoResultado);
            InputStream is = new FileInputStream(arc);
            return new DefaultStreamedContent(is, "text/plain", rutaArchivoResultado);
        } catch (IOException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage("Error al descargar el archivo", "");
        }
        return null;
    }

    public void manejarBusqueda() {
        if (input.getNumeroControl().length() < 21) {
            super.addErrorMessage("Debe capturar los 21 caracteres del numero de control ", "");
            return;
        }
        capturarResultado();
    }

    public void capturarResultado() {
        try {
            ResultadoDiligencia diligencia = documentoService.actualizarBitacoraDocumento(input, selected, getAccesoUsr().getUsuario(), isCargaPorArchivo, formatoFechaIncorrecta, isEstadoValido);
            super.addMessage("Registro de Diligenciaci\u00f3n: " + diligencia.getNumeroControl() + ", con estatus: " + diligencia.getResultado(), "");

        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
            super.addErrorMessage(e.getMessage(), "");
        } finally {
            limpiarCampos();
        }
    }

    public void limpiarCampos() {
        input.setNumeroControl("");
        input.setFechaDiligencia(null);
        input.setFechaCitatorio(null);
        input.setResultado("");
        porcentaje = 0;
        selected = OpcionResultadoDiligenciaEnum.VACIO;
    }

    private File leeerArchivoCreado(String carpetaArchivoDiligencia) {
        return new File(carpetaArchivoDiligencia);
    }

    /**
     *
     * @param mensaje
     */
    private void enviarCorreo() {
        try {
            entidad = usuarioEF.get(0).getIdEntidadFederativa();
            StringBuilder mensaje = new StringBuilder();

            List<UsuarioEF> emails = usuarioEFService.obtenerUsuariosPorEntidad(entidad);
            StringBuilder sEmails = new StringBuilder("");
            for (UsuarioEF usuarioEF : emails) {
                sEmails.append(usuarioEF.getCorreoElectronico()).append(",");
            }
            String[] destinatarios = sEmails.toString().split(",");
            if (cargaExitosa) {
                mensaje.append("<br/> El proceso de carga de resultados de la notificación concluyo exitosamente. Ingrese al aplicativo para descargar el archivo correspondiente.").
                        append("<br/> Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electrónico no monitoreada.");
            } else {
                mensaje.append("<br/> El proceso de carga de resultados de la notificación No concluyo exitosamente, inicie nuevamente la carga.").
                        append("<br/> Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electrónico no monitoreada.");
            }
            mailService.enviarCorreoPara(destinatarios, "MAT CO " + " - " + "Retroalimentación de diligencia", mensaje.toString());
        } catch (MessagingException ex) {
            getLogger().error(ex.getMessage(), ex);
        } catch (SGTServiceException ex) {
            getLogger().error(ex.getMessage(), ex);
        }
    }

    public void reloadPage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("resultadosDiligencia.jsf");
    }

    private void generaFirma(Long idEntidadFederativa) throws SGTServiceException {
        firma = idEntidadFederativa + "";
        getLogger().info("Intentando bloquear el grupo de multas " + firma);
        procesoActivo = true;
        if (!concurrenceServiceImpl.lockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_RESULTADOS_DILIGENCIA, firma)) {
            throw new SGTServiceException(RESULTADOS_DILIGENCIAS_EN_EJECUCION);
        }
        getLogger().info("Se logro hacer el bloqueo " + firma);
    }

    private void desbloqueaFirma() throws SGTServiceException {
        getLogger().info("Desbloqueando la firma " + firma);
        procesoActivo = false;
        if (!concurrenceServiceImpl.unlockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_RESULTADOS_DILIGENCIA, firma)) {
            throw new SGTServiceException("No se pudo hacer el desbloqueo de este proceso de resultados diligencia.");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (counter < MAX_NUM_FILES) {
            file = event.getFile();
            counter++;
            guardarArchivo();
        } else {
            super.addWarningMessage("Solo se permite un archivo por carga.", "");
        }
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    public DocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setComboResultados(List<CatalogoBase> comboResultados) {
        this.comboResultados = comboResultados;
    }

    public List<CatalogoBase> getComboResultados() {
        return comboResultados;
    }

    public OpcionResultadoDiligenciaEnum getSelected() {
        return selected;
    }

    public void setSelected(OpcionResultadoDiligenciaEnum selected) {
        this.selected = selected;
    }

    public void setInput(ResultadoDiligencia input) {
        this.input = input;
    }

    public ResultadoDiligencia getInput() {
        return input;
    }

    public void setMostrarBotonDescarga(boolean mostrarBotonDescarga) {
        this.mostrarBotonDescarga = mostrarBotonDescarga;
    }

    public boolean isMostrarBotonDescarga() {
        return mostrarBotonDescarga;
    }

    public void setRutaArchivoResultado(String rutaArchivoResultado) {
        this.rutaArchivoResultado = rutaArchivoResultado;
    }

    public String getRutaArchivoResultado() {
        return rutaArchivoResultado;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setMostrarPaneles(boolean mostrarPaneles) {
        this.mostrarPaneles = mostrarPaneles;
    }

    public boolean isMostrarPaneles() {
        return mostrarPaneles;
    }

    public Integer getNumeroRegistroDiligencias() {
        return numeroRegistroDiligencias;
    }

    public void setNumeroRegistroDiligencias(Integer numeroRegistroDiligencias) {
        this.numeroRegistroDiligencias = numeroRegistroDiligencias;
    }

    public Integer getNumeroRegistroDiligenciasRegistrados() {
        return numeroRegistroDiligenciasRegistrados;
    }

    public void setNumeroRegistroDiligenciasRegistrados(Integer numeroRegistroDiligenciasRegistrados) {
        this.numeroRegistroDiligenciasRegistrados = numeroRegistroDiligenciasRegistrados;
    }

    public String getNombreArchivoDiligencias() {
        return nombreArchivoDiligencias;
    }

    public void setNombreArchivoDiligencias(String nombreArchivoDiligencias) {
        this.nombreArchivoDiligencias = nombreArchivoDiligencias;
    }

    public Date getFechaCarga() {
        if (fechaCarga == null) {
            return null;
        } else {
            return (Date) fechaCarga.clone();
        }
    }

    public void setFechaCarga(Date fechaCarga) {
        if (fechaCarga != null) {
            this.fechaCarga = (Date) fechaCarga.clone();
        } else {
            this.fechaCarga = null;
        }
    }

    public boolean isMostrarBotonDescargaArchivo() {
        return mostrarBotonDescargaArchivo;
    }

    public void setMostrarBotonDescargaArchivo(boolean mostrarBotonDescargaArchivo) {
        this.mostrarBotonDescargaArchivo = mostrarBotonDescargaArchivo;
    }

    public boolean isMostrarMensajeArchivo() {
        return mostrarMensajeArchivo;
    }

    public void setMostrarMensajeArchivo(boolean mostrarMensajeArchivo) {
        this.mostrarMensajeArchivo = mostrarMensajeArchivo;
    }

    public boolean isMostrarPanelArchivo() {
        return mostrarPanelArchivo;
    }

    public void setMostrarPanelArchivo(boolean mostrarPanelArchivo) {
        this.mostrarPanelArchivo = mostrarPanelArchivo;
    }

    public String getFechaCargaStr() {
        return fechaCargaStr;
    }

    public void setFechaCargaStr(String fechaCargaStr) {
        this.fechaCargaStr = fechaCargaStr;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public boolean isIsCargaPorArchivo() {
        return isCargaPorArchivo;
    }

    public void setIsCargaPorArchivo(boolean isCargaPorArchivo) {
        this.isCargaPorArchivo = isCargaPorArchivo;
    }

    public String getMuestraMensaje() {
        return muestraMensaje;
    }

    public Long getEntidad() {
        return entidad;
    }

    public void setEntidad(Long entidad) {
        this.entidad = entidad;
    }
}

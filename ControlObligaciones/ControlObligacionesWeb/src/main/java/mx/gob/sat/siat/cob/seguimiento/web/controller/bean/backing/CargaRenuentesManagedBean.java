package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaMotivoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoBDEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.ArchivoRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.BuscaRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.CargaMotivoRenuenteService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.DoctoRenuenteService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.util.CreaArchivoSistema;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Marco Murakami
 */
@Controller("cargaRenuentes")
@Scope(value = "view")
public class CargaRenuentesManagedBean extends AbstractBaseMB {

    @SuppressWarnings("compatibility:-1737564122481946159")
    private static final long serialVersionUID = 302L;
    @Autowired
    private DoctoRenuenteService doctoRenuenteService;
    @Autowired
    private transient BuscaRenuentesService buscaRenuentesService;
    @Value("#{properties['filename.renuentes.bitacora']}")
    private String bitacoraFileName;
    @Autowired
    private transient ServiceCatalogos catalogos;
    @Autowired
    private ConcurrenceService concurrenceService;
    @Autowired
    private ArchivoRenuentesService archivoRenuentesService;
    @Autowired
    private CargaMotivoRenuenteService cargaMotivoRenuenteService;
    private String fileBitacoraFullName;
    private boolean showButtonBitacora;
    private List<CatalogoBase> catalogoMotivoRenuente = Collections.emptyList();
    private int counter = 0;
    private final static int MAX_NUM_FILES = 1;
    private UploadedFile file;
    @Value("#{properties['ruta.carpeta.temporal']}")
    private String carpetaArchivoRenuentes;
    private ArchivoRenuente archivoRenuente;
    private boolean mostrarPanelArchivo;
    private boolean mostrarBotonDescargaArchivo;
    private boolean mostrarMensajeArchivo;
    private String muestraMensaje;
    private static final String RESULTADOS_RENUENTES_EN_EJECUCION = "El proceso de carga de renuentes se está ejecutando. Al finalizar exitosamente recibirá un correo electrónico de notificación";

    public CargaRenuentesManagedBean() {
        super();
    }

    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        autorizar(ConstantsCatalogos.IDENTIFICADOR_CARGARENUENTES);
        catalogoMotivoRenuente = catalogos.consultar("motivo_renuente");
        Collections.sort(catalogoMotivoRenuente);
        ultimoArchivoCargado();
    }

    /**
     * Metodo encargado de recopilar la informacion, del ultimo archivo cargado.
     */
    private void ultimoArchivoCargado() {
        archivoRenuente = archivoRenuentesService.obtenerArchivoRenuente();
        if (isProcesoBloqueado()) {
            mostrarPanelArchivo = true;
            muestraMensaje = RESULTADOS_RENUENTES_EN_EJECUCION;
            mostrarMensajeArchivo = true;
            mostrarBotonDescargaArchivo = false;
            archivoRenuente.setTotalRegistrosErrores(ConstantsCatalogos.CERO);
        } else {
            if (archivoRenuente != null) {
                mostrarPanelArchivo = true;
                if (archivoRenuente.getRutaArchivoResultado() != null) {
                    File arc = new File(archivoRenuente.getRutaArchivoResultado());
                    if (arc.exists()) {
                        mostrarBotonDescargaArchivo = true;
                        mostrarMensajeArchivo = false;
                    } else {
                        muestraMensaje = ConstantsCatalogos.MSG_CARGA_DILIGENCIA;
                        mostrarMensajeArchivo = true;
                        mostrarBotonDescargaArchivo = false;
                    }
                } else {
                    mostrarBotonDescargaArchivo = false;
                    mostrarMensajeArchivo = false;
                }
            } else {
                mostrarPanelArchivo = false;
                mostrarBotonDescargaArchivo = false;
            }
        }
    }

    /**
     * Metodo para descargar la bitacora de errores
     *
     * @return
     */
    public StreamedContent getBitacoraFile() {
        StreamedContent sc = null;
        try {
            File arc = new File(archivoRenuente.getRutaArchivoResultado());
            InputStream is = new FileInputStream(arc);
            sc = new DefaultStreamedContent(is, "text/plain", arc.getName());
        } catch (FileNotFoundException fnfe) {
            getLogger().error(fnfe.getMessage());
            super.addErrorMessage("Error al descargar el archivo", "");
        }
        return sc;
    }

    /**
     * Metodo para guardar el archivo en el servidor
     *
     * @param content
     * @param nameFile
     * @param ext
     * @throws IOException
     */
    private void saveFile(String content, String nameFile) throws IOException {
        String nom = getFileNameRFC(nameFile);
        if (nameFile.equals(bitacoraFileName)) {
            fileBitacoraFullName = nom;
        }
        File fileSave = new File(nom);
        if (!fileSave.exists()) {
            boolean resp = fileSave.createNewFile();
            if (resp) {
                BufferedWriter bw = new BufferedWriter(
                        new FileWriterWithEncoding(fileSave.getAbsoluteFile(),
                                Charset.defaultCharset()));
                bw.write(content);
                bw.close();
            }
        }
    }

    private void bloquearProceso() throws SGTServiceException {
        String proceso = "cargaRenuentes";
        getLogger().info("Intentando bloquear el grupo de multas " + proceso);
        if (!concurrenceService.lockServices(
                TipoServiciosConcurrentesEnum.IDENTIFICADOR_CARGARENUENTES, proceso)) {
            throw new SGTServiceException("No se pudo hacer el bloqueo de este proceso.");
        }
        getSession().setAttribute("datosProceso", proceso);
        getLogger().info("Se logro hacer el bloqueo " + proceso);
    }

    private void desbloquearProceso() throws SGTServiceException {
        String proceso = getSession().getAttribute("datosProceso").toString();
        getLogger().info("Desbloqueando el proceso " + proceso);
        if (!concurrenceService.unlockServices(
                TipoServiciosConcurrentesEnum.IDENTIFICADOR_CARGARENUENTES, proceso)) {
            throw new SGTServiceException("No se pudo hacer el desbloqueo de este proceso.");
        }
    }

    private boolean isProcesoBloqueado() {
        String proceso = "cargaRenuentes";
        return concurrenceService.isLockedServices(
                TipoServiciosConcurrentesEnum.IDENTIFICADOR_CARGARENUENTES, proceso);
    }

    /**
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        if (isProcesoBloqueado()) {
            RequestContext request = RequestContext.getCurrentInstance();
            request.execute("msgErrorConcurrencia()");
        } else {
            if (counter < MAX_NUM_FILES) {
                file = event.getFile();
                counter++;
                leerArchivo();
            } else {
                super.addWarningMessage("Solo se permite un archivo por carga.", "");
            }
        }
    }

    /**
     *
     */
    public void leerArchivo() {
        BufferedReader br = null;
        try {
            bloquearProceso();
            CreaArchivoSistema.cargarResultadoDiligencia(file, carpetaArchivoRenuentes);
            StringBuilder ruta = new StringBuilder();
            ruta.append(carpetaArchivoRenuentes).append(file.getFileName());
            File server = new File(ruta.toString());

            List<String> listaErrores = new ArrayList<String>();
            int totalReg = 0;
            int badReg = 0;
            String line;
            boolean resp;

            List<CargaMotivoRenuente> motivosRenuentes = new ArrayList<CargaMotivoRenuente>();
            Long idRenuente = archivoRenuentesService.obtenerIdCargaRenuents();

            br = new BufferedReader(new InputStreamReader(new FileInputStream(server), Charset.defaultCharset()));
            while ((line = br.readLine()) != null) {
                CargaMotivoRenuente mtvRenuente = new CargaMotivoRenuente();
                mtvRenuente.setIdCargaRenuente(idRenuente);
                /**
                 * Busqueda de numeroControl.
                 */
                Documento documento = buscaXNumControl(line);
                totalReg++;
                if (documento != null) {
                    /**
                     * BUSQUEDA DEL ESTADO DEL DOCUMENTO, solo vencido y vencido
                     * parcial pueden cambiar la bandera de renuente.
                     */
                    if (documento.getUltimoEstado() == EstadoDocumentoEnum.VENCIDO.getValor()
                            || documento.getUltimoEstado() == EstadoDocumentoEnum.VENCIDO_PARCIAL.getValor()) {
                        /**
                         * ACTUALIZACION DEL DOCUMENTO, EXITO.
                         */
                        resp = updateRenuente(line);
                        if (resp) {
                            mtvRenuente.setIdMotivoNoRenuente(null);
                            mtvRenuente.setLineaArchivo(line);
                        } else {
                            /**
                             * Aqui poner el motivo 99 directo.
                             */
                            badReg++;
                            listaErrores.add(line + " | " + descripcionMotivoRenuente(EstadoBDEnum.ERROR.getValor()));
                            mtvRenuente.setIdMotivoNoRenuente(EstadoBDEnum.ERROR.getValor());
                            mtvRenuente.setLineaArchivo(line);
                        }
                    } else {
                        /**
                         * NO SE ACTUALIZA EL DOCUMENTO DEBIDO A QUE TIENE UN
                         * ESTADO DE CANCELACION, buscas estado en
                         * sgtc_mtvonorenuent para poner el el mensaje de error.
                         */
                        badReg++;
                        listaErrores.add(line + " | " + descripcionMotivoRenuente(documento.getUltimoEstado()));
                        mtvRenuente.setIdMotivoNoRenuente(documento.getUltimoEstado());
                        mtvRenuente.setLineaArchivo(line);
                    }
                } else {
                    /**
                     * NO SE ENCUENTRA EL DOCUMENTO EN LA BASE DE DATOS, Aqui se
                     * pone el 98.
                     */
                    badReg++;
                    listaErrores.add(line + " | " + descripcionMotivoRenuente(EstadoBDEnum.INCONSISTENCIA.getValor()));
                    mtvRenuente.setIdMotivoNoRenuente(EstadoBDEnum.INCONSISTENCIA.getValor());
                    /**
                     * Si es tamanio de la cadena es mas de 22, se corta, por el
                     * espacion en base de datos.
                     */
                    if (line.length() > 49) {
                        mtvRenuente.setLineaArchivo(line.substring(0, 49));
                    } else {
                        mtvRenuente.setLineaArchivo(line);
                    }
                }
                motivosRenuentes.add(mtvRenuente);
            }
            /**
             * Genera archivo de errores.
             */
            this.generaArchivoErrores(listaErrores);

            /**
             * Carga la informacion del archivo procesado en base de datos, en
             * la tabla SGTT_CARGARENUENTS.
             */
            ArchivoRenuente archivoRenuenteSave = new ArchivoRenuente();
            archivoRenuenteSave.setIdCargaRenunetes(idRenuente);
            archivoRenuenteSave.setUsuarioCarga(getAccesoUsr().getUsuario());
            archivoRenuenteSave.setNumEmpleadoCarga(getAccesoUsr().getNumeroEmp());
            archivoRenuenteSave.setNombreArchivoCarga(server.getName());
            archivoRenuenteSave.setTotalRegistrosArchivoCarga(totalReg);
            archivoRenuenteSave.setRutaArchivoResultado(this.fileBitacoraFullName);
            archivoRenuenteSave.setTotalRegistrosErrores(badReg);

            archivoRenuentesService.agregaArchivoRenuente(archivoRenuenteSave);

            /**
             * Carga la informacion recopilada del archivo de carga.
             */
            getLogger().info("-*-* Insert por registro");
            for (CargaMotivoRenuente renuente : motivosRenuentes) {
                cargaMotivoRenuenteService.agregaMotivoRenuente(renuente);
            }
            getLogger().info("***** Nombre completo del archivo: " + server.getName() + " *****");
            cargaMotivoRenuenteService.enviaCorreo(getAccesoUsr().getNumeroEmp());
            super.addMessage("Archivo cargado exitosamente", server.getName() + " is uploaded.");

        } catch (IOException e) {
            getLogger().error(e.getMessage());
        } catch (SGTServiceException ex) {
            getLogger().error(ex);
        } finally {
            try {
                fileBitacoraFullName = "";
                file = null;
                counter = 0;
                desbloquearProceso();
                ultimoArchivoCargado();
            } catch (SGTServiceException ex) {
                getLogger().error(ex);
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    getLogger().error(e.getMessage());
                }
            }
        }
    }

    /**
     * obtiene la descripcion del catalogo SGTC_MTVONORENUENT enviando su
     * IDMOTIVONORENUENTE
     *
     * @param indiceBusqueda
     * @return
     */
    private String descripcionMotivoRenuente(int indiceBusqueda) {
        int index = Collections.binarySearch(catalogoMotivoRenuente,
                new CatalogoBase(indiceBusqueda));
        return catalogoMotivoRenuente.get(index).getNombre();

    }

    /**
     * Metodo para generar el archivo de bitacora
     *
     * @param listaErrores
     */
    private void generaArchivoErrores(List<String> listaErrores) {
        if (listaErrores.size() > 0) {
            try {
                this.showButtonBitacora = true;
                StringBuilder infoBuilder = new StringBuilder();
                for (String info : listaErrores) {
                    infoBuilder.append(info).append("\n");
                }
                this.saveFile(infoBuilder.toString(), bitacoraFileName);
            } catch (IOException ex) {
                getLogger().error(ex.getMessage());
            }
        } else {
            this.showButtonBitacora = false;
        }
        try {
            guardarBitacora(ConstantsCatalogos.CARGA_MOV_RENUENTES,
                    ConstantsCatalogos.CARGA_RENUENTES_OBS);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
    }

    /**
     * @param numControl
     */
    private boolean updateRenuente(String numControl) {
        boolean resp = false;
        try {
            resp = doctoRenuenteService.updateConsideraRenuenciaDocto(numControl);
            try {
                guardarBitacora(ConstantsCatalogos.ACTUALIZA_ESTADO_RENUENTES,
                        ConstantsCatalogos.CARGA_RENUENTES_OBS);
            } catch (Exception e) {
                getLogger().error(e.getMessage());
            }
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
        return resp;
    }

    private void guardarBitacora(int movimiento, String observacion) {
        try {
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(),
                    ConstantsCatalogos.CVE_SISTEMA,
                    ConstantsCatalogos.IDENTIFICADOR_BUSCARENUENTES,
                    new Date(), new Date(), movimiento, observacion);
            buscaRenuentesService.registraBitacora(dto);
        } catch (SGTServiceException ex) {
            getLogger().error(ex.getMessage());
        } catch (AccesoDenegadoException ex) {
            getLogger().error(ex.getMessage());
        }
    }

    /**
     * @param numControl
     * @return
     */
    public Documento buscaXNumControl(String numControl) {
        Documento documento = null;
        try {
            documento = doctoRenuenteService.consultaXNumControl(numControl);
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
        return documento;
    }

    public void reloadPage() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("cargaRenuentes.jsf");
    }

    /**
     * Getters and setters.
     */
    public boolean isShowButtonBitacora() {
        return showButtonBitacora;
    }

    public void setShowButtonBitacora(boolean showButtonBitacora) {
        this.showButtonBitacora = showButtonBitacora;
    }

    public ArchivoRenuente getArchivoRenuente() {
        return archivoRenuente;
    }

    public void setArchivoRenuente(ArchivoRenuente archivoRenuente) {
        this.archivoRenuente = archivoRenuente;
    }

    public String getMuestraMensaje() {
        return muestraMensaje;
    }

    public boolean isMostrarMensajeArchivo() {
        return mostrarMensajeArchivo;
    }

    public boolean isMostrarPanelArchivo() {
        return mostrarPanelArchivo;
    }

    public boolean isMostrarBotonDescargaArchivo() {
        return mostrarBotonDescargaArchivo;
    }
}

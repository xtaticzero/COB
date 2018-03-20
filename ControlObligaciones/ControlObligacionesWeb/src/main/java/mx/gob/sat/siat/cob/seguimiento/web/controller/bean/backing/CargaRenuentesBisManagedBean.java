package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatosCargaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.BuscaRenuentesService;
import mx.gob.sat.siat.cob.seguimiento.service.renuente.DoctoRenuenteService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import mx.gob.sat.siat.cob.seguimiento.web.util.ConstantesWeb;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.AccesoDenegadoFielException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
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
@Controller("cargaRenuentesBis")
@Scope(value = "view")
public class CargaRenuentesBisManagedBean extends AbstractBaseMB {
    @SuppressWarnings("compatibility:-1737564122481946159")
    private static final long serialVersionUID = 302L;
    @Autowired
    private DoctoRenuenteService doctoRenuenteService;
    @Autowired
    private transient BuscaRenuentesService buscaRenuentesService;
    @Value("#{properties['filename.renuentes.bitacora']}")
    private String bitacoraFileName;
    private String fileBitacoraFullName;
    private List<DatosCargaRenuentes> listaDatosCarga;
    private boolean showButtonBitacora;
    private UploadedFile file;

    @PostConstruct
    public void init() {
        obtenerAccesoUsrEmpleados();
        try {
            AccesoProceso.validaAccesoProceso(getSession(), ConstantsCatalogos.IDENTIFICADOR_CARGARENUENTESBIS, ConstantsCatalogos.PARAMETRO_FIEL);
        } catch (SessionRolNullException ex) {
            getLogger().error(ex);
            redirecionarPaginaError(ex);
        } catch (AccesoDenegadoException ex) {
            getLogger().error(ex);
            redirecionarPaginaError(ex);
        } catch (AccesoDenegadoFielException ex) {
            getLogger().error(ex);
            redirecionarPaginaError(ex);
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
            sc = this.getFile(this.fileBitacoraFullName);
        } catch (IOException ioe) {
            addErrorMessage(ConstantesWeb.ERROR, ioe.getMessage());
        }
        return sc;
    }

    /**
     * Metodo para la descarga del archivo de renuentes
     *
     * @param nameFile
     * @param ext
     * @return
     * @throws IOException
     */
    private StreamedContent getFile(String fileFullName) throws IOException {
       
        File arc = new File(fileFullName);
        InputStream is = new FileInputStream(arc);
        StreamedContent sc = new DefaultStreamedContent(is, "text/plain", arc.getName());
       
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
        String nombre = getFileName(nameFile);

        if (nameFile.equals(bitacoraFileName)) {
            fileBitacoraFullName = nombre;
        }
        File archivo = new File(nombre);
        if (!archivo.exists()) {
            boolean resp = archivo.createNewFile();
            if (resp) {
                BufferedWriter bw = new BufferedWriter(new FileWriterWithEncoding(archivo.getAbsoluteFile(), Charset.defaultCharset()));
                bw.write(content);
                bw.close();
            }
        }
      
    }

    /**
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        addMessage("Succesful", event.getFile().getFileName() + " is uploaded.");

        try {
            leerArchivo(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException ioE) {
            getLogger().error(ioE.getMessage());
        }
    }
    
    public void procesarArchivo(){
        if (file != null){
            try {
                leerArchivo(file.getFileName(), file.getInputstream());
            } catch (IOException ex) {
                getLogger().error(ex.getMessage());
            }
        }
    }

    /**
     * @param nombreArchivo
     * @param is
     */
    public void leerArchivo(String nombreArchivo, InputStream is) {

        BufferedReader br = null;
        String line;
        boolean resp;
        int totalReg = 0;
        int badReg = 0;
        int goodReg = 0;
        List<String> listaErrores = new ArrayList<String>();

        try {
            br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));

            while ((line = br.readLine()) != null) {

                //BUSQUEDA DEL DOCUMENTO POR NUMERO DE CONTROL
                Documento documento = buscaXNumControl(line);
                totalReg++;
                if (documento != null) {
                    //BUSQUEDA DEL ESTADO DEL DOCUMENTO
                    EstadoDocumento ed = this.buscaEstadoDoctoXNumControl(line);
                    if (ed == null) {
                        //ACTUALIZACION DEL DOCUMENTO
                        resp = updateRenuente(line);
                        if (resp) {
                            goodReg++;
                        } else {
                            badReg++;
                            listaErrores.add(line);
                        }//Proceso por renglon
                    } else {
                        //NO SE ACTUALIZA EL DOCUMENTO DEBIDO A QUE TIENE UN ESTADO DE CANCELACION
                        badReg++;
                        listaErrores.add(line + " | " + ed.getNombreEstado());
                    }
                } else {
                    //NO SE ENCUENTRA EL DOCUMENTO EN LA BASE DE DATOS
                    badReg++;
                    listaErrores.add(line + " | Inconsistencia de datos.");
                }
            }
            // ACORTA NOMBRE DEL ARCHIVO
            String shortFileName = null;
            if (nombreArchivo.contains("\\")) {
                String[] rutaAbsoluta = nombreArchivo.split("\\\\");
                shortFileName = rutaAbsoluta[rutaAbsoluta.length - 1];
            } else {
                shortFileName = nombreArchivo;
            }

            DatosCargaRenuentes datosCargaRenuentes = new DatosCargaRenuentes();
            datosCargaRenuentes.setGoodRegistros(goodReg);
            datosCargaRenuentes.setNombreArchivo(shortFileName);
            datosCargaRenuentes.setTotRegistros(totalReg);
            datosCargaRenuentes.setWrongRegistros(badReg);
            listaDatosCarga = new ArrayList<DatosCargaRenuentes>();
            listaDatosCarga.add(datosCargaRenuentes);

            getLogger().info("***** Nombre completo del archivo: " + nombreArchivo + " *****");
            getLogger().info("***** Nombre del archivo: " + shortFileName + " *****");

            //Generar archivo de errores
            this.generaArchivoErrores(listaErrores);

        } catch (IOException e) {
            getLogger().error(e.getMessage());
        } finally {
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
            guardarBitacora(ConstantsCatalogos.CARGA_MOV_RENUENTES, ConstantsCatalogos.CARGA_RENUENTES_OBS);
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
                guardarBitacora(ConstantsCatalogos.ACTUALIZA_ESTADO_RENUENTES, ConstantsCatalogos.CARGA_RENUENTES_OBS);
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
            SegbMovimientoDTO dto = MovimientoUtil.addMovimiento(getSession(), ConstantsCatalogos.CVE_SISTEMA, ConstantsCatalogos.IDENTIFICADOR_BUSCARENUENTES, new Date(), new Date(), movimiento, observacion);
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

    public EstadoDocumento buscaEstadoDoctoXNumControl(String numControl) {
        EstadoDocumento ed = null;
        try {
            ed = doctoRenuenteService.consultaEstadoDoctoXNumControl(numControl);
        } catch (SGTServiceException e) {
            getLogger().error(e.getMessage());
        }
        return ed;
    }

    public List<DatosCargaRenuentes> getListaDatosCarga() {
        return listaDatosCarga;
    }

    public void setListaDatosCarga(List<DatosCargaRenuentes> listaDatosCarga) {
        this.listaDatosCarga = listaDatosCarga;
    }

    public boolean isShowButtonBitacora() {
        return showButtonBitacora;
    }

    public void setShowButtonBitacora(boolean showButtonBitacora) {
        this.showButtonBitacora = showButtonBitacora;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}

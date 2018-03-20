package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.support;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.BitacoraRepositorioBase;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.TareaDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleCarga;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.web.util.validador.ValidadorRegistro;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class UploadHandler implements Serializable{

    @SuppressWarnings("compatibility:8296992753145968850")
    private static final long serialVersionUID = 1L;
    private static final int BUFFER_SIZE=2*1024*1024;
    private static Logger log = Logger.getLogger(UploadHandler.class);
    private BitacoraRepositorioBase bitacora;
    private BitacoraRepositorioBase repositorio;
    private TareaDTO dto;
    private DetalleCarga currentBeanCarga;
    private String strTipoCatalogo;
    
    /**
     *
     * @param dto
     * @param rutaRepositorio
     * @param rutaRepoBitacoraErrores
     */
    public UploadHandler(TareaDTO dto,String rutaRepositorio,String rutaRepoBitacoraErrores) {
        super();
        this.bitacora = new BitacoraRepositorioBase(rutaRepoBitacoraErrores);
        this.repositorio = new BitacoraRepositorioBase(rutaRepositorio);
        this.dto = dto;
    }

    /**
     * 
     * @param fileName
     * @param inputStream
     * @param catalogo
     * @return 
     */
    public DetalleCarga handleFileUpload(String fileName, InputStream inputStream, String catalogo) {
        InputStream input1=null;
        InputStream input2=null;
        try {
            log.info("handleFileUpload:"+fileName);
            this.currentBeanCarga = new DetalleCarga();
            String nombreArchivo=fileName;
            if(fileName.contains("\\")){
                String [] rutaAbsoluta=fileName.split("\\\\");
                nombreArchivo=rutaAbsoluta[rutaAbsoluta.length-1];
            }
            log.info("empieza duplicado");
            byte[] byteArray = IOUtils.toByteArray(inputStream);     
            input1 = new ByteArrayInputStream(byteArray);
            input2 = new ByteArrayInputStream(byteArray);
            log.info("termina duplicado");
            String codificacion=Utilerias.detectaCodificacionStream(input1);
            log.info(codificacion+":"+nombreArchivo);
            this.currentBeanCarga.setNombreOriginalArchivo(nombreArchivo);
            strTipoCatalogo=catalogo;
            if( !(codificacion.equals("UTF-8") || codificacion.equals("WINDOWS-1252")) ){
                log.info("error");
                this.currentBeanCarga.setCodificacionCorrecta(false);
            }else{
                this.currentBeanCarga.setCodificacionCorrecta(true);
                if (!parseRequest(input2,codificacion)) {
                    log.info("exito");
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo v\u00E1lido y subido con \u00E9xito","");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    log.info("error");
                     FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Archivo con inconsistencia, verifique el archivo de inconsistencias","");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e);
            rollBackRecursos();
        } catch (IOException e) {
            log.error(e);
            rollBackRecursos();
        }  catch (Exception e) {
            log.error(e);
            rollBackRecursos();
        } finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
                if(input1!=null){
                    input1.close();
                }
                if(input2!=null){
                    input2.close();
                }
                cierraRecursos();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        return this.currentBeanCarga;
    }

    /**
     *
     */
    public void rollBackRecursos() {
        try {
            this.bitacora.rollback();
            this.repositorio.rollback();
        } catch (Exception e) {
            log.error(e);
        }
    }

    private void cierraRecursos() {
        try {
            log.info("cierraRecursos");
            this.bitacora.cierra();
            this.repositorio.cierra();
        } catch (Exception e) {
            log.error(e);
        }
    }

    private boolean parseRequest(InputStream stream,String codificacion) throws IOException  {
        BufferedReader br = null;
        InputStreamReader isr = new InputStreamReader(stream, codificacion);
        br = new BufferedReader(isr, BUFFER_SIZE);
        boolean resp = validacion(br,codificacion);
        br.close();
        log.info("BufferReader cerrado ....");
        return resp;
    }

    private boolean validacion(BufferedReader br,String codificacion) throws IOException  {
        boolean errorGlobal = false;
        int currNumLinea = 0;
        int currSumaErrores = 0;
        StringBuilder resp = null;
        String lineaLectura = br.readLine();
        lineaLectura=Utilerias.removeUTF8BOM(lineaLectura);
        while (lineaLectura != null) {
            currNumLinea++;
            if(!strTipoCatalogo.isEmpty()){
                resp = ValidadorRegistro.validaCatalogo(lineaLectura,strTipoCatalogo);
            }else{
                resp = ValidadorRegistro.valida(lineaLectura);
            }
            if (resp.toString().equals("EXITO")) {
                if (!errorGlobal) {
                if (!repositorio.isInicializado()){
                        repositorio.inicializa(dto,codificacion);
                        this.currentBeanCarga.setNombreEnRepositorio(repositorio.getNombreIdentificador());
                        this.currentBeanCarga.setRutaEnRepositorio(repositorio.getRutaFinal());
                }                    
                    repositorio.escribe(lineaLectura);
                    
                }
            } else {
                errorGlobal = true;
                if (!bitacora.isInicializado()){
                    bitacora.inicializa(dto,codificacion);
                    this.currentBeanCarga.setNombreEnRepositorio(bitacora.getNombreIdentificador());
                    this.currentBeanCarga.setRutaEnBitacora(bitacora.getRutaFinal());
                }
                if (repositorio.isInicializado() && !repositorio.isRollbacked()) {
                    repositorio.rollback();
                }
                this.bitacora.escribe(lineaLectura + " @Error:"+ resp.toString() +
                        ":[numlinea] " + currNumLinea + "\n\r");
                
                currSumaErrores++;
            }
            lineaLectura = br.readLine();
        }
        log.info("repositorio correcto:"+this.repositorio.getRutaFinal());
        log.info("bitacora errores:"+this.bitacora.getRutaFinal());
        this.currentBeanCarga.setCargaInvalida(errorGlobal);
        this.currentBeanCarga.setNumeroRegistros(currNumLinea);
        this.currentBeanCarga.setTotalRegistrosConError(currSumaErrores);
        log.info("termina escritura:"+this.currentBeanCarga);
        return errorGlobal;
    }
}

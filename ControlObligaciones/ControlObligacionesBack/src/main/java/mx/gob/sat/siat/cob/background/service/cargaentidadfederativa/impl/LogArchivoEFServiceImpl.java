package mx.gob.sat.siat.cob.background.service.cargaentidadfederativa.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import mx.gob.sat.siat.cob.background.service.cargaentidadfederativa.LogArchivoEFService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LogArchivoEFServiceImpl implements LogArchivoEFService {

    private Logger log =Logger.getLogger(LogArchivoEFServiceImpl.class);
    private String nombreArchivo;
    private String nombreArchivoTmp;
    private boolean tieneDatos;
    private Map<Integer,String> listaArchivo = new TreeMap<Integer,String>();

    private static final String EXT_ARCH_TXT = ".txt";
    private static final String EXT_ARCH_ZIP = ".zip";
    private static final String RUTA_ENTIDAD_FED = "/siat/cob/archivoEF/";
    private static final String RUTA_TMP_ENTIDAD_FED = "/siat/cob/tmp/archivoEF/";

    /**
     *
     * @param pCadena
     */
    @Override
    public synchronized void escribirLog(String pCadena) {
        if(nombreArchivoTmp!=null){
            OutputStreamWriter out=null;
            try {
                out = new OutputStreamWriter(
                        new FileOutputStream(nombreArchivoTmp,true),"UTF-8");
                PrintWriter escribirLinea = new PrintWriter(out);
                escribirLinea.println(pCadena);
                escribirLinea.flush();
                escribirLinea.close();
            } catch (IOException ex) {
                log.error(ex);
            } finally {
                try {
                    setTieneDatos(true);
                    if(out!=null){
                        out.close();
                    }
                } catch (IOException ex) {
                    log.error(ex);
                }
            }
        }
    }

    /**
     *
     * @param ruta
     * @param nombreArchivo
     * @param extencion
     */
    @Override
    public void setNombreArchivo(String ruta, String nombreArchivo, String extencion) {
        File dirTmp=new File(RUTA_ENTIDAD_FED + ruta + "/");
        boolean estatus = dirTmp.mkdirs();
        log.debug(estatus);
        dirTmp=new File(RUTA_TMP_ENTIDAD_FED + ruta + "/");
        estatus = dirTmp.mkdirs();
        log.debug(estatus);
        this.nombreArchivoTmp = RUTA_TMP_ENTIDAD_FED + ruta + "/" + nombreArchivo + extencion;
        this.nombreArchivo = RUTA_ENTIDAD_FED + ruta + "/" + nombreArchivo + EXT_ARCH_ZIP;
        File validaArchivo=new File(nombreArchivoTmp);
        if(validaArchivo.exists()){
            estatus=validaArchivo.delete();
            log.debug(estatus);
        }
        log.debug(this.nombreArchivo);
        this.setTieneDatos(false);
    }

    /**
     *
     * @param ruta
     * @param nombreArchivo
     */
    @Override
    public void setNombreArchivo(String ruta, String nombreArchivo) {
        setNombreArchivo(ruta, nombreArchivo, EXT_ARCH_TXT);
    }

    /**
     * 
     * @return
     */
    @Override
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getNombreArchivoTmp() {
        return nombreArchivoTmp;
    }
    
    /**
     *
     * @return
     */
    public boolean isTieneDatos() {
        return tieneDatos;
    }

    /**
     *
     * @param tieneDatos
     */
    public synchronized void setTieneDatos(boolean tieneDatos) {
        this.tieneDatos = tieneDatos;
    }

    /**
     * 
     * @return
     */
    public static String getRUTAENTIDADFED() {
        return RUTA_ENTIDAD_FED;
    }

    /**
     * @return the listaArchivo
     */
    @Override
    public Map<Integer, String> getListaArchivo() {
        return listaArchivo;
    }

    /**
     * @param listaArchivo the listaArchivo to set
     */
    public void setListaArchivo(Map<Integer, String> listaArchivo) {
        this.listaArchivo = listaArchivo;
    }

}

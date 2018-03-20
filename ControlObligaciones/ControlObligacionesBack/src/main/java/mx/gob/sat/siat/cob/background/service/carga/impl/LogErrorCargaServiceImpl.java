package mx.gob.sat.siat.cob.background.service.carga.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaDAO;
import org.apache.log4j.Logger;
import mx.gob.sat.siat.cob.background.service.carga.LogErrorCargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christian.ventura
 */
@Service
public class LogErrorCargaServiceImpl implements LogErrorCargaService {

    private Logger log =Logger.getLogger(LogErrorCargaServiceImpl.class);
    private String nombreArchivo;
    private boolean tieneDatos;
    private Set<Integer> vigilancia =Collections.synchronizedSet(new HashSet<Integer>());
    private Set<String> listaArchivos = Collections.synchronizedSet(new HashSet<String>());

    private static final String NOMBRE_PARC_ARCH = "bitacora_";
    private static final String EXT_ARCH = ".txt";
    private static final String RUTA_REPOSITORIO = "/siat/cob/repositorio/";

    @Autowired
    private transient VigilanciaDAO vigilanciaDAO;

    /**
     *
     * @param tieneDatos
     */
    public void setTieneDatos(boolean tieneDatos) {
        this.tieneDatos = tieneDatos;
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
     * @return
     */
    @Override
    public Set<String> getListaArchivos() {
        return listaArchivos;
    }
    /**
     *
     * @param listaArchivos
     */
    public void setListaArchivos(Set<String> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }
    /**
     *
     * @return
     */
    @Override
    public Set<Integer> getVigilancia() {
        return vigilancia;
    }
    /**
     * 
     * @param vigilancia
     */
    public void setVigilancia(Set<Integer> vigilancia) {
        this.vigilancia = vigilancia;
    }

    /**
     *
     * @param nombreArchivo
     */
    @Override
    public void setNombreArchivo(String nombreArchivo) {
        String fecha=new SimpleDateFormat("dd_MM_yyyy_HH_mm").format(new Date());
        this.nombreArchivo = RUTA_REPOSITORIO + NOMBRE_PARC_ARCH + nombreArchivo + fecha;
        log.debug(this.nombreArchivo);
        this.vigilancia.clear();
        this.listaArchivos.clear();
        this.setTieneDatos(false);
    }

    public String getNombreArchivo(String idVigilancia) {
        return nombreArchivo + "_" + idVigilancia + EXT_ARCH;
    }
    
    /**
     *
     * @param pCadena
     */
    @Override
    public void escribirLog(String pCadena,String idVigilancia) {
        String nombreArch=getNombreArchivo(idVigilancia);
        if(nombreArch!=null){
            OutputStreamWriter out=null;
            try {
                out = new OutputStreamWriter(
                        new FileOutputStream(nombreArch,true),"UTF-8");
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
     * guarda en base de datos las rutsa de los arcxhivos
     */
    @Override
    @Transactional
    public void guardarLogVigilancia(){
        String nombreArch;
        log.debug("guardarLogVigilancia");
        if(isTieneDatos()){
            for(Integer vig:this.vigilancia){
                nombreArch=getNombreArchivo(vig.toString());
                for(String arch:this.listaArchivos){
                    vigilanciaDAO.guardarBitacoraErrores(vig,arch,nombreArch);
                }
            }
        }
        this.setNombreArchivo(null);
        this.setTieneDatos(false);
        this.vigilancia.clear();
        this.listaArchivos.clear();
    }

}

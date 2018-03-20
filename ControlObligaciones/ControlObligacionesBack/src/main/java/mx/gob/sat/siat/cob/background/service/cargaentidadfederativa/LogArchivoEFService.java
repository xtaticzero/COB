package mx.gob.sat.siat.cob.background.service.cargaentidadfederativa;

import java.util.Map;

/**
 *
 * @author christian.ventura
 */
public interface LogArchivoEFService {

    /**
     *
     * @param pCadena
     */
    void escribirLog(String pCadena);

    /**
     *
     * @param ruta
     * @param nombreArchivo
     */
    void setNombreArchivo(String ruta, String nombreArchivo);

    /**
     *
     * @param ruta
     * @param nombreArchivo
     * @param extencion
     */
    void setNombreArchivo(String ruta, String nombreArchivo, String extencion);

    /**
     *
     * @return
     */
    String getNombreArchivo();

    /**
     * 
     * @return 
     */
    String getNombreArchivoTmp();
    
    /**
     *
     * @return
     */
    Map<Integer, String> getListaArchivo();

}

package mx.gob.sat.siat.cob.background.service.carga;

import java.util.Set;

/**
 *
 * @author christian.ventura
 */
public interface LogErrorCargaService {

    /**
     * 
     * @param pCadena
     * @param idVigilancia 
     */
    void escribirLog(String pCadena,String idVigilancia);

    /**
     *
     * @param nombreArchivo
     */
    void setNombreArchivo(String nombreArchivo);

    /**
     *
     */
    void guardarLogVigilancia();

    /**
     *
     * @return
     */
    Set<String> getListaArchivos();

    /**
     *
     * @return
     */
    Set<Integer> getVigilancia();
    
}

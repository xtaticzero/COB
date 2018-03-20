/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author Ulises
 */
public interface MonitorArchivoArcaDAO {

    /**
     *
     * @param
     */
    Integer insert(List<MonitorArchivoArca> monitorArchivoArca) throws SeguimientoDAOException ;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @param esEnvioResolucion
     * @return
     */
    MonitorArchivoArca consultarMonitorArchivoArca(Long idVigilancia, String idAlsc, Integer esEnvioResolucion) throws SeguimientoDAOException;
    
    /**
     * @param esEnvioResolucion
     * @return
     */
    List<MonitorArchivoArca> consultarListaMonitorArchivoArca(Integer esEnvioResolucion) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer actualizaMonitorArchivoArca(MonitorArchivoArca monitorArchivoArca) throws SeguimientoDAOException ;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @param esEnvioResolucion
     * @return
     */
    Integer deleteMonitorArchivoArca(Long idVigilancia, String idAlsc, Integer esEnvioResolucion) throws SeguimientoDAOException;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoDiligencia;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author root
 */
public interface ArchivoDiligenciaDAO {
    
     ArchivoDiligencia obtenerArchivoDiligencia(Long idEntidadFederativa);
     void agregaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SeguimientoDAOException;
     void agregaRutaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SeguimientoDAOException;
    
}

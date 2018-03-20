/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoDiligencia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface ArchivoDiligenciaService {

    ArchivoDiligencia obtenerArchivoDiligencia(Long idEntidadFederativa);

    void agregaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SGTServiceException;

    void agregaRutaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SGTServiceException;
}

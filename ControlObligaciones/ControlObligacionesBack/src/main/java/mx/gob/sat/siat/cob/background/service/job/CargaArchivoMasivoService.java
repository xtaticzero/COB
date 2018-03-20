/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.job;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author Juan
 */
public interface CargaArchivoMasivoService {

    void ejecutar(VigilanciaAdministracionLocal vigilancia) throws SeguimientoDAOException;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface SolventarIcepsCumplimientoService {
 void solventarICEPS(List<DetalleDocumento> detalles) throws SGTServiceException;   
}

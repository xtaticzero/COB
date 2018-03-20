/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion;

import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface RechazarVigilanciasService {

    void rechazar(VigilanciaAdministracionLocal vigilancia, String numeroEmpleado) throws SGTServiceException;

    void rechazarPorNumeroDocumento(Set<DocumentoAprobar> documentos) throws SGTServiceException;
}

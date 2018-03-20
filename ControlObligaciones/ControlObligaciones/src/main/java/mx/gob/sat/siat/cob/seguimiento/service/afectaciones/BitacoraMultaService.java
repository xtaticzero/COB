/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Rodrigo
 */
public interface BitacoraMultaService {

    void registraBitacoraMulta(String numeroResolucion, int estatus) throws SGTServiceException;
    void actualizaEstadoBitacoraMulta(MultaDocumento multaDocumento, EstadoMultaEnum estadoMultaEnum)  throws SGTServiceException;
}

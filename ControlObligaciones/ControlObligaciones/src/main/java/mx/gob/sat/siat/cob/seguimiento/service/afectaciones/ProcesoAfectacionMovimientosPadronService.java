/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface ProcesoAfectacionMovimientosPadronService {


    void solventarPorMovimientoAlPadronTotal(Documento documento) throws SGTServiceException;

    void solventarPorMovimientoAlPadronParcial(Documento documento) throws SGTServiceException;

    void borrarDatosBajaIcep(List<Documento> documentosNoProcesados) throws SGTServiceException;

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.renuente;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaMotivoRenuente;

/**
 *
 * @author juan
 */
public interface CargaMotivoRenuenteService {

    /**
     *
     * @param renuente
     */
    void agregaMotivoRenuente(CargaMotivoRenuente renuente);

    void enviaCorreo(String numeroEmpleado);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.otros;

import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

/**
 *
 * @author root
 */
public interface MovimientosService {

    void registrarMovimiento(SegbMovimientoDTO movimiento);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Citatorio;

/**
 *
 * @author juan
 */
public interface CitatorioDAO {

    /**
     *
     * @param citatorio
     * @return
     */
    Integer insertaCitatorio(Citatorio citatorio);
}

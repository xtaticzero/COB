/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDeclaracionEnum;

/**
 *
 * @author emmanuel
 */
public interface TipoDeclaracionDAO {
    List<Integer> gettipoDeclaracion(TipoDeclaracionEnum tipoDeclaracion);    
}

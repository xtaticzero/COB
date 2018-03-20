/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciasLogDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;

/**
 *
 * @author emmanuel
 */
public interface APVigilanciasLogDAO {

    Integer insertarError(Long idVigilancia, String idAdmonLocal, String descripcion);

    Integer updateError(Long idVigilancia, String idAdmonLocal, String descripcion);

    Integer cleanError(Long idVigilancia, String idAdmonLocal);

    VigilanciasLogDTO getEerrorVigilancias(Long idVigilancia, String idAdmonLocal);

    Boolean vigilanciaConError(Long idVigilancia, String idAdmonLocal);
    
    List<VigilanciasLogDTO> getVigilanciasConError(NivelEmisionEnum nivelEmi,Long[] arrIdVigilancia,String idAdmonLocal);
}

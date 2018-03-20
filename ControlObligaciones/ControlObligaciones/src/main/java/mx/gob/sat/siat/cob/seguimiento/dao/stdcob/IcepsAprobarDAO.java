/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.IcepsAprobarSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IcepsAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;

/**
 *
 * @author root
 */
public interface IcepsAprobarDAO extends IcepsAprobarSQL {

    List<IcepsAprobar> listarIcepsPorPagina(String idLocalidad,
            String numeroCarga, Paginador paginador);

    Long contarIceps(String idLocalidad, String numeroCarga);

    Integer realizarCumplimientoIcep(Documento documento);

    Integer actualizarEstadoIcep(Documento documento, SituacionIcepEnum situacion);
}

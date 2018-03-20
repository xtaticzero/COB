/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author juan
 */
public interface CitatorioSQL {

    String INSERT_CITATORIO = "insert into sgtt_citatoriodoc(numerocontrol,fechacitatorio)\n"
            + "values(?,?)";

    String UPDATE_CITATORIO = "update sgtt_citatoriodoc set fechacitatorio = to_date(?,'dd/mm/yy')\n"
            + "where numeroControl = ?";
}

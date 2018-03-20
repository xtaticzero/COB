/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class HistoricoCumplimientoMapper implements RowMapper<HistoricoCumplimiento> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */

    @Override
    public HistoricoCumplimiento mapRow(ResultSet rs, int i) throws SQLException {
        HistoricoCumplimiento historicoCumplimiento
                = new HistoricoCumplimiento();
        historicoCumplimiento.setbOID(rs.getString("BOID"));
        historicoCumplimiento.setClaveICEP(rs.getString("CLAVEICEP"));
        historicoCumplimiento.setIdentificadorCumplimiento(rs.getString("IDENTIFICADORCUMPLIMIENTO"));
        historicoCumplimiento.setImportePagar(rs.getDouble("IMPORTEAPAGAR"));
        historicoCumplimiento.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        historicoCumplimiento.setEstadoVigilable(rs.getInt("EDOVIGILABLE"));
        historicoCumplimiento.setTipoDeclaracion(rs.getString("TIPODECLARACION"));
        historicoCumplimiento.setEstadoIcep(rs.getInt("EDOICEP"));
        historicoCumplimiento.setFechaMantenimiento(rs.getDate("FECHAMANTENIMIENTO"));
        return historicoCumplimiento;
    }

}

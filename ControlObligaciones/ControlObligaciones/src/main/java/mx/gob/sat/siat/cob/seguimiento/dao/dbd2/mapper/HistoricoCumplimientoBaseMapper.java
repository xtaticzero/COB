/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author emmanuel
 */
public class HistoricoCumplimientoBaseMapper implements RowMapper<HistoricoCumplimiento> {
    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    private final boolean validacion;

    public HistoricoCumplimientoBaseMapper(boolean validacion) {
        this.validacion = validacion;
    }

    @Override
    public HistoricoCumplimiento mapRow(ResultSet rs, int i) throws SQLException {
        HistoricoCumplimiento historicoCumplimiento
                = new HistoricoCumplimiento();
        historicoCumplimiento.setbOID(rs.getString("BOID"));
        historicoCumplimiento.setClaveICEP(rs.getString("CLAVEICEP"));
        historicoCumplimiento.setIdentificadorCumplimiento(rs.getString("IDENTIFICADORCUMPLIMIENTO"));
        if(rs.getString("IMPORTEAPAGAR")!=null){
            historicoCumplimiento.setImportePagar(rs.getDouble("IMPORTEAPAGAR"));
        }        
        historicoCumplimiento.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        historicoCumplimiento.setEstadoVigilable(rs.getInt("EDOVIGILABLE"));
        historicoCumplimiento.setTipoDeclaracion(rs.getString("TIPODECLARACION"));
        if (validacion) {
            historicoCumplimiento.setEstadoIcep(rs.getInt("EDOICEP"));
        }
        return historicoCumplimiento;
    }
}

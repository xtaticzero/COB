/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author juan
 */
public class PeriodoBusquedaMapper implements RowMapper<PeriodicidadHelper> {

    @Override
    public PeriodicidadHelper mapRow(ResultSet rs, int i) throws SQLException {
        PeriodicidadHelper ph = new PeriodicidadHelper();

        ph.setIdBusquedaRenuentes(rs.getLong("IDBUSQUEDARENUENTES"));

        CatalogoBase periodicidad = new CatalogoBase();
        periodicidad.setIdString(rs.getString("IDPERIODICIDAD"));

        ph.setPeriodicidad(periodicidad);
        ph.setPeriodoInicialSelected(rs.getString("IDPERIODOINICIAL"));
        ph.setPeriodoFinalSelected(rs.getString("IDPERIODOFINAL"));
        ph.setEjercicioInicialSelected(rs.getString("IDEJERCICIOINICIAL"));
        ph.setEjercicioFinalSelected(rs.getString("IDEJERCICIOFINAL"));

        return ph;
    }

}

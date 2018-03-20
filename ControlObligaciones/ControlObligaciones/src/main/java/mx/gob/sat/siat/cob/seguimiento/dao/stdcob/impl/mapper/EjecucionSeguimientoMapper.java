package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionSeguimiento;
import org.springframework.jdbc.core.RowMapper;

public class EjecucionSeguimientoMapper implements RowMapper{
    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException{
        EjecucionSeguimiento ejec = new EjecucionSeguimiento();
        ejec.setId(resultSet.getInt("ID"));
        ejec.setEnEjecucion(resultSet.getInt("EJECUCION"));
        return ejec;
    }
}

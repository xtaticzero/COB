package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import org.springframework.jdbc.core.RowMapper;

public class BusquedaFundamentoLegalMapper implements RowMapper<FundamentoLegal>{
    /**
     *
     */
    public BusquedaFundamentoLegalMapper() {
        super();
    }

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public FundamentoLegal mapRow(ResultSet rs, int i) throws SQLException {
        FundamentoLegal fundamentoLegal = new FundamentoLegal();
              
        fundamentoLegal.setIdFundamentoLegal(rs.getLong("IDFUNDAMENTOLEGAL"));
        fundamentoLegal.setIdObligacion(rs.getLong("IDOBLIGACION"));
        fundamentoLegal.setIdRegimen(rs.getLong("IDREGIMEN"));
        fundamentoLegal.setIdEjercicioFiscal(rs.getLong("IDEJERCICIOFISCAL"));
        fundamentoLegal.setIdPeriodo(rs.getString("IDPERIODO")+"|"+rs.getString("IDPERIODICIDAD"));
        fundamentoLegal.setFundamentoLegal(rs.getString("FUNDAMENTOLEGAL"));
        fundamentoLegal.setFechaInicio(rs.getDate("FECHAINICIO"));
        fundamentoLegal.setFechaFin(rs.getDate("FECHAFIN"));
        fundamentoLegal.setIdObligacionDescr(rs.getString("DESCRIPCION"));
        
        return fundamentoLegal;
    }        
}

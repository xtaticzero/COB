package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class FundamentoLegalMapper implements RowMapper<FundamentoLegal>{
    /**
     *
     */
    public FundamentoLegalMapper() {
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
        fundamentoLegal.setIdObligacionDescr(rs.getString("CONCEPTO"));
        fundamentoLegal.setIdRegimen(rs.getLong("IDREGIMEN"));
        fundamentoLegal.setIdRegimenDescr(rs.getString("DESCRIPCIONREGIMEN"));
        fundamentoLegal.setIdEjercicioFiscal(rs.getLong("IDEJERCICIOFISCAL"));
        fundamentoLegal.setIdEjercicioFiscalDescr(rs.getString("EJERCICIOFIS"));
        fundamentoLegal.setIdPeriodo(rs.getString("IDPERIODO")+"|"+rs.getString("IDPERIODICIDAD"));
        fundamentoLegal.setIdPeriodoDescr(rs.getString("PERIODICIDAD"));
        fundamentoLegal.setFundamentoLegal(rs.getString("FUNDAMENTOLEGAL"));
        fundamentoLegal.setFechaInicio(rs.getDate("FECHAINICIO"));
        fundamentoLegal.setFechaFin(rs.getDate("FECHAFIN"));
        fundamentoLegal.setDescripcionLarga(rs.getString("DESCRIPCIONLARGA"));
        fundamentoLegal.setDescripcionCorta(rs.getString("DESCRIPCIONCORTA"));
        fundamentoLegal.setFechaVencimiento(rs.getDate("FECHAVENCIMIENTOOBL"));
        if(rs.getDate("FECHAVENCIMIENTOOBL")!=null){
            fundamentoLegal.setFechaVencimientoStr(Utilerias.formatearFechaDDMMYYYY(rs.getDate("FECHAVENCIMIENTOOBL")));
        }
        if(rs.getDate("FECHAINICIO")!=null){
            fundamentoLegal.setFechaInicioStr(Utilerias.formatearFechaDDMMYYYY(rs.getDate("FECHAINICIO")));
        }
        if(rs.getDate("FECHAFIN")!=null){
            fundamentoLegal.setFechaFinStr(Utilerias.formatearFechaDDMMYYYY(rs.getDate("FECHAFIN")));
        }
        return fundamentoLegal;
    }

}

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class MultaDocumentoAfectacionesMapper implements RowMapper<MultaDocumentoAfectaciones> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public MultaDocumentoAfectaciones mapRow(ResultSet rs, int i) throws SQLException {
        MultaDocumentoAfectaciones multa = new MultaDocumentoAfectaciones();
        multa.setTipoMulta(rs.getString("TIPOMULTA"));
        multa.setNumResolucion(rs.getString("NUMRESOLUCION"));
        multa.setIdObligacion(rs.getLong("IDOBLIGACION"));
        multa.setDescObligacion(rs.getString("DESCOBLIGACION"));
        multa.setMonto(rs.getLong("MONTO"));
        multa.setPeriodo(rs.getString("PERIODO"));
        multa.setEjercicio(rs.getString("EJERCICIO"));
        return multa;
    }
}

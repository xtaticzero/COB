package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;

public class CatParametroMapper implements RowMapper<ParametrosSgtDTO> {

    @Override
    public ParametrosSgtDTO mapRow(ResultSet rs, int i) throws SQLException {
        ParametrosSgtDTO parametros = new ParametrosSgtDTO();

        parametros.setDescripcion(rs.getString("descripcion"));
        parametros.setFechaFin(rs.getDate("fechafin"));
        parametros.setFechaInicio(rs.getDate("fechainicio"));
        parametros.setIdParametro(rs.getInt("idparametro"));
        parametros.setNombre(rs.getString("nombre"));
        parametros.setOrden(rs.getInt("orden"));
        parametros.setTipoDato(rs.getString("tipodato"));
        parametros.setPrecision(rs.getString("precision"));
        if (Utilerias.existeColumna(rs, "valor")) {
            parametros.setValor(rs.getString("valor"));
        }

        return parametros;
    }
}

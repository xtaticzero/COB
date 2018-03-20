package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;

public class VigilanciaEFSingleMapper implements RowMapper {

    public VigilanciaEFSingleMapper() {
        super();
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        VigilanciaEntidadFederativa vef = new VigilanciaEntidadFederativa();

        vef.setIdVigilancia(resultSet.getLong("idvigilancia"));
        vef.setIdEntidadFederativa(resultSet.getLong("IDENTIDADFEDERATIVA"));
        vef.setIdEstadoVigilancia(resultSet.getInt("idsituacionvigilancia"));
        vef.setIdSituacionArchivo(resultSet.getLong("idsituacionarchivo"));
        vef.setFechaDescarga(resultSet.getDate("fechadescarga") != null ? Utilerias.formatearFechaDDMMYYYY(resultSet.getDate("fechadescarga")) : "");
        vef.setRutaArchivoOmisos(resultSet.getString("rutaarchivoomisos"));
        vef.setRutaArchivoFundamentos(resultSet.getString("rutaarchivofundamentos"));
        vef.setRutaArchivoFactura(resultSet.getString("rutaarchivofactura"));

        return vef;
    }
}

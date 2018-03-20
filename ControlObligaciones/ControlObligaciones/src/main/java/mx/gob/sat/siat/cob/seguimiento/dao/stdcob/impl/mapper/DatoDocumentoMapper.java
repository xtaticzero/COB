package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDocumento;
import org.springframework.jdbc.core.RowMapper;


public class DatoDocumentoMapper implements RowMapper<DatoDocumento> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DatoDocumento mapRow(ResultSet resultSet, int i) throws SQLException {

            DatoDocumento datoDocumento = new DatoDocumento();

            datoDocumento.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
            datoDocumento.setNombreALSC(resultSet.getString("NOMBREALSC"));
            datoDocumento.setRfc(resultSet.getString("RFC"));
            datoDocumento.setCurp(resultSet.getString("CURP"));
            datoDocumento.setNombreContribuyente(resultSet.getString("NOMBRECONTRIBUYENTE"));
            datoDocumento.setDomicilioContribuyente(resultSet.getString("DOMICILIOCONTRIBUYENTE"));
            datoDocumento.setIdVigilancia(resultSet.getInt("IDVIGILANCIA"));
            datoDocumento.setFraccionALSC(resultSet.getString("FRACCCIONALSC"));
            datoDocumento.setLocalidadALSC(resultSet.getString("LOCALIDADALSC"));
            datoDocumento.setNombreAdminALSC(resultSet.getString("NOMBREADMINISTRADORALSC"));
            datoDocumento.setIdEstadoGeneracion(resultSet.getInt("IDESTADOGENERACION"));
            datoDocumento.setDomicilioALSC(resultSet.getString("DOMICILIOALSC"));
            datoDocumento.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));

            return datoDocumento;
        }
}



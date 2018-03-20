package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.idc.Ubicacion;
import org.springframework.jdbc.core.RowMapper;


public class BaseDocumentoMapper implements RowMapper {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setClvALR(resultSet.getString("ClaveALR"));
        ubicacion.setCodPostal(resultSet.getString("CodigoPostal"));
        ubicacion.setClvCRH(resultSet.getString("ClaveCRH"));
        ubicacion.setClvEntidadFederativa(resultSet.getString("numeric_cd"));
        return ubicacion;
    }

}

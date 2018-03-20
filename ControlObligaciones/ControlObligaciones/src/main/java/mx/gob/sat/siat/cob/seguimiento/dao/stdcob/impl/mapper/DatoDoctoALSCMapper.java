package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDoctoALSC;
import org.springframework.jdbc.core.RowMapper;

public class DatoDoctoALSCMapper implements RowMapper {

    /**
     *
     */
    public DatoDoctoALSCMapper() {
        super();
    }

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        List<DatoDoctoALSC> lstDatoDoctoALSC = new ArrayList<DatoDoctoALSC>();
        do {

            DatoDoctoALSC datoDoctoALSC = new DatoDoctoALSC();

            datoDoctoALSC.setDomicilioAlsc(resultSet.getString("DOMICILIOALSC"));
            datoDoctoALSC.setFraccionAlsc(resultSet.getString("FRACCIONALSC"));
            datoDoctoALSC.setLocalidadAlsc(resultSet.getString("LOCALIDADALSC"));
            datoDoctoALSC.setNombreAlsc(resultSet.getString("NOMBREALSC"));
            datoDoctoALSC.setNombreAdminAlsc(resultSet.getString("NOMBREADMINALSC"));
            datoDoctoALSC.setNombreContribuyenteAlsc(resultSet.getString("NOMBRECONTRIBUYENTEALSC"));

            lstDatoDoctoALSC.add(datoDoctoALSC);

        } while (resultSet.next());

        return lstDatoDoctoALSC;
    }
}

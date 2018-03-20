package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import org.springframework.jdbc.core.RowMapper;


public class VigilanciaEtapaMapper implements RowMapper {
    /**
     *
     */
    public VigilanciaEtapaMapper() {
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
        List<EmisionVigilancia> emisiones = new ArrayList<EmisionVigilancia>();
        do {

            EmisionVigilancia emision = new EmisionVigilancia();

            emision.setIdVigilancia(resultSet.getInt("IDVIGILANCIA"));
            emision.setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));
            emision.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));
            emision.setEsPlantillaDIOT(resultSet.getInt("ESPLANTILLADIOT"));

            emisiones.add(emision);
        } while (resultSet.next());

        return emisiones;
    }
}

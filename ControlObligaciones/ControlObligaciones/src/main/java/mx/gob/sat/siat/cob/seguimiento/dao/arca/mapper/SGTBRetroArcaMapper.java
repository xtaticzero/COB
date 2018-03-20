package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SGTBRetroARCA;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class SGTBRetroArcaMapper implements RowMapper {

    /**
     *
     */
    private final Logger log = Logger.getLogger(SGTBRetroArcaMapper.class);

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public SGTBRetroARCA mapRow(ResultSet resultSet, int i) throws SQLException {
        SGTBRetroARCA procesado = new SGTBRetroARCA();
        try {
            procesado.setIdProcesado(resultSet.getInt("IDPROCESADO"));
            if (resultSet.getString("FECHAPROCESADO") != null) {
                procesado.setFechaProcesado(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHAPROCESADO")));
            }
        } catch (ParseException pe) {
            log.error("error SGTBRetroArcaMapper Parceo de Fechas ", pe);
        }
        return procesado;
    }
}

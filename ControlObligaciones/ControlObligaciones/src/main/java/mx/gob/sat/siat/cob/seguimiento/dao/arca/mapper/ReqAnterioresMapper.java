package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class ReqAnterioresMapper implements RowMapper<RequerimientosAnteriores> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public RequerimientosAnteriores mapRow(ResultSet resultSet, int i) throws SQLException {

        RequerimientosAnteriores reqsAnteriores = new RequerimientosAnteriores();
        reqsAnteriores.setIdDocumento(resultSet.getString("IDDocumento"));
        reqsAnteriores.setIdDocumentoPadre(resultSet.getString("IDDocumentoPadre"));
        reqsAnteriores.setDescrTipoRequ(resultSet.getString("fcDescripcionTipoDeReq"));
        reqsAnteriores.setFechaNotificacion(resultSet.getString("fdFechaDeNotificacion"));
        reqsAnteriores.setDescrObligacion(resultSet.getString("fcDescripcionDeObligacion"));
        reqsAnteriores.setDescrPeriodo(resultSet.getString("fcDescripcionDePeriodo"));
        reqsAnteriores.setEjercicio(resultSet.getInt("fiEjercicio"));
        reqsAnteriores.setFechaPresentacionObligacion(resultSet.getString("fdFechaDePresDeObligacion"));
        reqsAnteriores.setFechaNotificacion(resultSet.getString("fdFechaDeVencDeRequerimiento"));
        reqsAnteriores.setEdoObligacionVencimiento(resultSet.getString("fcEstadoDeObligDeVenc"));
        
        return reqsAnteriores;
    }
}

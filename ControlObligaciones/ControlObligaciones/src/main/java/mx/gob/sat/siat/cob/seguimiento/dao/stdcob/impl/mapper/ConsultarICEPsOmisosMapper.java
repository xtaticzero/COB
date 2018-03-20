package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;

import org.apache.log4j.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;


public class ConsultarICEPsOmisosMapper implements RowMapper<DetalleDocumento> {

    private final Logger log = Logger.getLogger(ConsultarICEPsOmisosMapper.class.getName());

    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public DetalleDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {



        DetalleDocumento icepOmiso = new DetalleDocumento();
        try {
            icepOmiso.setNumeroControl(rs.getString("NUMEROCONTROL"));
            icepOmiso.setClaveIcep(rs.getLong("CLAVEICEP"));
            icepOmiso.setIdObligacion(rs.getInt("IDOBLIGACION"));
            icepOmiso.setIdEjercicio(rs.getString("EJERCICIO"));
            icepOmiso.setIdPeriodo(rs.getString("IDPERIODO"));
            icepOmiso.setFechaVencimiento(rs.getString("FECHAVENCIMIENTOOBLIGACION"));
            if (rs.getDate("FECHACUMPLIMIENTO") != null) {
                icepOmiso.setFechaCumplimiento(rs.getDate("FECHACUMPLIMIENTO"));
            }
            icepOmiso.setIdPeriodicidad(rs.getString("IDPERIODICIDAD"));
            icepOmiso.setIdSituacionIcep(rs.getInt("IDSITUACIONICEP"));
            icepOmiso.setIdRegimen(rs.getString("IDREGIMEN"));
            icepOmiso.setImporteCargo(rs.getDouble("IMPORTEPAGAR"));
            icepOmiso.setIdTipoDeclaracion(rs.getInt("IDTIPODECLARACION"));
        } catch (DataAccessException e) {
            log.error("error DocumentoMapper actualizarSeguimientoDocumento ", e);
            throw new SQLException(e);
        }
        return icepOmiso;
    }
}
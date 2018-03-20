package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;

import org.springframework.jdbc.core.RowMapper;

public class CancelacionXAutoridadMapper implements RowMapper<AfectacionXAutoridad> {
    @Override
    public AfectacionXAutoridad mapRow(ResultSet rs, int i) throws SQLException {

        AfectacionXAutoridad afectacionXAutoridad = new AfectacionXAutoridad();
        afectacionXAutoridad.setNumeroControl(rs.getString("NUMEROCONTROL"));
        afectacionXAutoridad.setRfc(rs.getString("RFC"));
        afectacionXAutoridad.setFechaRegistro(rs.getString("FECHAREGISTRO"));
        afectacionXAutoridad.setFechaNotificacion(rs.getString("FECHANOTIFICACION"));
        afectacionXAutoridad.setFechaVencimiento(rs.getString("FECHAVENCIMIENTO"));
        afectacionXAutoridad.setEstado(rs.getString("ESTADO"));
        afectacionXAutoridad.setClaveObligacion(rs.getInt("CLAVEOBLIGACION"));
        afectacionXAutoridad.setObDescripcion(rs.getString("DESCRIPCION"));
        afectacionXAutoridad.setSolventado(rs.getString("SITUACIONICEP"));
        afectacionXAutoridad.setNumResolucion(rs.getString("NUMERORESOLUCION"));
    
        return afectacionXAutoridad;
    }
}


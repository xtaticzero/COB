package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;

import org.springframework.jdbc.core.RowMapper;


public class ConsultaReporteMapper implements RowMapper<ReporteAfectacionXAutoridadDTO>{
    
        /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public ReporteAfectacionXAutoridadDTO mapRow(ResultSet rs, int i) throws SQLException {

        ReporteAfectacionXAutoridadDTO afectacionXAutoridad = new ReporteAfectacionXAutoridadDTO();
        afectacionXAutoridad.setNumeroControl(rs.getString("numerocontrol"));
        afectacionXAutoridad.setEstado(rs.getString("situacionicep"));
        afectacionXAutoridad.setEstadoDocumento(rs.getString("estadoRequerimiento"));
        afectacionXAutoridad.setFechaRegistro(rs.getString("fechaRegistro"));
        afectacionXAutoridad.setFechaNotificacion(rs.getString("fechanotificacion"));
        afectacionXAutoridad.setFechaVencimiento(rs.getString("fechavencimiento"));
        afectacionXAutoridad.setClaveObligacion(rs.getInt("claveobligacion"));
        afectacionXAutoridad.setObDescripcion(rs.getString("descripcion"));
        afectacionXAutoridad.setEjercicio(rs.getString("ejercicio"));
        afectacionXAutoridad.setPeriodo(rs.getString("descripcionperiodo"));
        afectacionXAutoridad.setTipoDocumento(rs.getString("tipodocumento"));
        afectacionXAutoridad.setFechaNoTrabajado(rs.getString("FECHANOTRABAJADO"));
        afectacionXAutoridad.setFechaNoLocalizado(rs.getString("FECHANOLOCALIZADOCONTRIBUYENTE"));
        return afectacionXAutoridad;
    }
}

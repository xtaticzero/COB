package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;


import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraSeguimiento;

import org.springframework.jdbc.core.RowMapper;

public class BitacoraSeguimientoMapper implements RowMapper{
    public BitacoraSeguimientoMapper() {
        super();
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        BitacoraSeguimiento bitacora=new BitacoraSeguimiento();
        
        bitacora.setFechaMovimiento(resultSet.getDate("fechamovimiento"));
        bitacora.setIdEstadoDocumento(resultSet.getInt("idestadodocto"));
        bitacora.setNumeroControl(resultSet.getString("numerocontrol"));
        
        return bitacora;
    }
}

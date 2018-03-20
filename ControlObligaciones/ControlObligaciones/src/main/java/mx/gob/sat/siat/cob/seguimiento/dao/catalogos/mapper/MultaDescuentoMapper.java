package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;

import org.springframework.jdbc.core.RowMapper;


public class MultaDescuentoMapper implements RowMapper<MultaDescuento>{
    public MultaDescuentoMapper() {
        super();
    }

    @Override
    public MultaDescuento mapRow(ResultSet rs, int i) throws SQLException {
        MultaDescuento multa = new MultaDescuento();
              
        multa.setIdMultaMonto(rs.getLong("IDMULTADESCUENTO"));
        multa.setFechaInicio(rs.getDate("FECHAINICIO"));
        multa.setFechaFin(rs.getDate("FECHAFIN"));
        multa.setIdResolMotivo(rs.getString("CONSTANTERESOLMOTIVO"));
        multa.setIdMultaDescuento(rs.getLong("IDMULTADESCUENTO"));
        multa.setResolMotivoDescr(rs.getString("RESOLDESCR"));
        multa.setMultaDescuentoDescr(rs.getString("MULTADESCUENTO"));
                         
        return multa;
    }
}

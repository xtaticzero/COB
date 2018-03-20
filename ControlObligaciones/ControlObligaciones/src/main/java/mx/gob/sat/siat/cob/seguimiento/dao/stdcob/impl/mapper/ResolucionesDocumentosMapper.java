package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;

import org.springframework.jdbc.core.RowMapper;


public class ResolucionesDocumentosMapper implements RowMapper<MultaDocumentoAfectaciones>{
    @Override
    public MultaDocumentoAfectaciones mapRow(ResultSet rs, int i) throws SQLException {
               
        MultaDocumentoAfectaciones multa = new MultaDocumentoAfectaciones();
        
        multa.setTipoMulta(rs.getString("TIPOMULTA"));
        multa.setNumResolucion(rs.getString("NUMERORESOLUCION"));
        multa.setDescObligacion(rs.getString("IDTIPOMULTA"));    
        multa.setNombreEstado(rs.getString("NOMBREESTADO"));                
        
        return multa;
    }
}
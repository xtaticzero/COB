/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class MultaDocumentoMapper implements RowMapper<MultaDocumento> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public MultaDocumento mapRow(ResultSet rs, int i) throws SQLException {
        return new MultaDocumento(rs.getString("numeroresolucion"),
                rs.getString("idresolucion") == null ? null : rs.getLong("idresolucion"),
                rs.getString("numerocontrol"),
                rs.getDate("fecharegistro"),
                rs.getString("constanteresolmotivo"),
                rs.getShort("ultimoestado"));
     }
    
}

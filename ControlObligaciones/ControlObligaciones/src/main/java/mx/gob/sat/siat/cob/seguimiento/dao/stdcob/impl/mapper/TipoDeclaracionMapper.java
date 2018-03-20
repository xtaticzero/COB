/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author emmanuel
 */
public class TipoDeclaracionMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs, int i) throws SQLException {
        return rs.getInt("IDTIPODECLARACION");
    }

}

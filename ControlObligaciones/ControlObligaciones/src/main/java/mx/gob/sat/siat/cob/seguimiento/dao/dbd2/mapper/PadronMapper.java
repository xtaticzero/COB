/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Padron;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class PadronMapper implements RowMapper<Padron>{

    @Override
    public Padron mapRow(ResultSet rs, int i) throws SQLException {
        Padron padron=new Padron();
        padron.setBoid(rs.getString("BOID"));
        padron.setIcep(rs.getLong("ICEP"));
        padron.setFechaMantenimiento(rs.getDate("fecha_mantenimiento"));
        return padron;
    }
    
}

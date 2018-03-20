/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaIcep;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author root
 */
public class MultaIcepMapper implements RowMapper<MultaIcep> {

    @Override
    public MultaIcep mapRow(ResultSet rs, int i) throws SQLException {
        MultaIcep multaIcep = new MultaIcep();
        multaIcep.setClaveIcep(rs.getLong("claveicep"));
        multaIcep.setNumeroResolucion(rs.getString("numeroresolucion"));
        multaIcep.setNumeroControl(rs.getString("numerocontrol"));
        return multaIcep;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.io.IOException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import sun.misc.BASE64Decoder;

/**
 *
 * @author root
 */
public class ArchivoPlantillaArcaMapper implements RowMapper<byte[]> {

    private final Logger log = Logger.getLogger(ArchivoPlantillaArcaMapper.class);

    @Override
    public byte[] mapRow(ResultSet rs, int i) throws SQLException {
        byte[] plantilla = new byte[0];
        try {
            Clob c = rs.getClob("contenido");
            plantilla = new BASE64Decoder().decodeBuffer(c.getAsciiStream());
        } catch (IOException e) {
            log.error(e);
        }
        return plantilla;
    }

}

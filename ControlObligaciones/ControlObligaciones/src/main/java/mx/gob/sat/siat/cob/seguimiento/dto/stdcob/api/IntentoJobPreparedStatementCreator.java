package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.IntentoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.PreparedStatementCreator;


/**
 *
 * @author root
 */
public class IntentoJobPreparedStatementCreator implements PreparedStatementCreator {
    private Logger logger = Logger.getLogger(IntentoJobPreparedStatementCreator.class);
    private IntentoDTO intento;

    
    /**
     *
     * @param intento
     */
    public IntentoJobPreparedStatementCreator(IntentoDTO intento) {
        super();
        this.intento = intento;
    }

    /**
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = null;
        
        try{
            ps = connection.prepareStatement(IntentoSQL.INSERTAR, new String[]{"IDINTENTOJOB"});
            ps.setInt(1, intento.getIdEjecucion());
            ps.setInt(2, intento.getIntento());
            ps.setInt(3, intento.getEstado());
        }
        finally{
            logger.debug("Statement Created!");
        }
        return ps;
    }

    /**
     *
     * @param intento
     */
    public void setIntento(IntentoDTO intento) {
        this.intento = intento;
    }

    /**
     *
     * @return
     */
    public IntentoDTO getIntento() {
        return intento;
    }
}

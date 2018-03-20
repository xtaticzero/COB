package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.EjecucionJobSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.PreparedStatementCreator;


/**
 *
 * @author root
 */
public class EjecucionJobPreparedStatementCreator implements PreparedStatementCreator {

    private Logger logger = Logger.getLogger(EjecucionJobPreparedStatementCreator.class);
    private EjecucionDTO ejecucion;

    /**
     *
     * @param ejecucion
     */
    public EjecucionJobPreparedStatementCreator(EjecucionDTO ejecucion) {
        super();
        this.ejecucion = ejecucion;
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
        try {
            ps = connection.prepareStatement(EjecucionJobSQL.INSERTAR, new String[]{"IDEJECUCION"});
            ps.setInt(1, ejecucion.getIdProceso());
            ps.setInt(2, ejecucion.getIntento());
            ps.setInt(3, ejecucion.getEstado());
            ps.setString(4, ejecucion.getObservaciones());
        } finally {
            logger.debug("Statement Created!");
        }
        return ps;
    }

    /**
     *
     * @param ejecucion
     */
    public void setEjecucion(EjecucionDTO ejecucion) {
        this.ejecucion = ejecucion;
    }

    /**
     *
     * @return
     */
    public EjecucionDTO getEjecucion() {
        return ejecucion;
    }
}

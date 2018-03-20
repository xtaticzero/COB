package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ProcesosSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.PreparedStatementCreator;


public class ProcesoPreparedStatementCreator implements PreparedStatementCreator {

    private Logger logger = Logger.getLogger(ProcesoPreparedStatementCreator.class);
    private Proceso proceso;

    public ProcesoPreparedStatementCreator(Proceso proceso) {
        super();
        this.proceso = proceso;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(ProcesosSQL.SQL_INSERTAR, new String[]{"IDPROCESO"});
            ps.setString(ConstantsCatalogos.UNO, proceso.getNombre());
            ps.setString(ConstantsCatalogos.DOS, proceso.getDescripcion());
            ps.setString(ConstantsCatalogos.TRES, proceso.getLanzador());
            ps.setString(ConstantsCatalogos.CUATRO, proceso.getProgramacion());
            ps.setInt(ConstantsCatalogos.CINCO, proceso.getEstado());
            ps.setInt(ConstantsCatalogos.SEIS, proceso.getIntentosMax());
            ps.setString(ConstantsCatalogos.SIETE, proceso.getExcluir());
            if (proceso.getPrioridad() != null) {
                ps.setInt(ConstantsCatalogos.OCHO, proceso.getPrioridad());
            } else {
                ps.setNull(ConstantsCatalogos.OCHO, Types.INTEGER);
            }
            if (proceso.getTipoCadena() != null) {
                ps.setInt(ConstantsCatalogos.NUEVE, proceso.getTipoCadena());
            } else {
                ps.setNull(ConstantsCatalogos.NUEVE, Types.INTEGER);
            }
            
        } finally {
            logger.debug("Statement Created!");
        }
        return ps;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Proceso getProceso() {
        return proceso;
    }
}

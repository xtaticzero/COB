/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.management.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ProcessInfo;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class ProcessInfoMapper implements RowMapper<ProcessInfo>{
    
    public ProcessInfoMapper(){
        super();
    }

    @Override
    public ProcessInfo mapRow(ResultSet rs, int i) throws SQLException {
        ProcessInfo processInfo = new ProcessInfo();
        
        processInfo.setProcessId(rs.getInt("ID"));
        processInfo.setActive(rs.getShort("ACTIVO"));
        processInfo.setCounter(rs.getLong("CONTADOR"));
        processInfo.setMax(rs.getLong("MAX"));
        processInfo.setMaxActive(rs.getLong("MAXACTIVO"));
        processInfo.setMaxActiveTimestamp(rs.getLong("TIEMPOMAXACTIVO"));
        processInfo.setMean(rs.getDouble("MEDIA"));
        processInfo.setMin(rs.getLong("MIN"));
        processInfo.setMinTimestamp(rs.getLong("MINTIEMPO"));
        processInfo.setStandardDeviation(rs.getDouble("DESVIACION"));
        processInfo.setTimestamp(rs.getTimestamp("TIEMPO"));
        processInfo.setTotal(rs.getLong("TOTAL"));
        processInfo.setVariance(rs.getDouble("VARIANZA"));
        processInfo.setVarianceN(rs.getDouble("VARIANZAN"));
        
        return processInfo;
    }
    
}

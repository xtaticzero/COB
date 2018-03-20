/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.management.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ServerInfo;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class ServerInfoMapper implements RowMapper<ServerInfo>{
    
    public ServerInfoMapper(){
        super();
    }

    @Override
    public ServerInfo mapRow(ResultSet rs, int i) throws SQLException {
        ServerInfo serverInfo = new ServerInfo();
        
        serverInfo.setCommitedVirtualMemorySize(rs.getLong("COMMITEDVIRTUALMEMORYSIZE"));
        serverInfo.setFreePhysicalMemorySize(rs.getLong("FREEPHYSICALMEMOYSIZE"));
        serverInfo.setFreeSwapSpaceSize(rs.getLong("FREESWAPSPACESIZE"));
        serverInfo.setProcessCPULoad(rs.getDouble("PROCESSCPULOAD"));
        serverInfo.setProcessCPUTime(rs.getLong("PROCESSCPUTIME"));
        serverInfo.setServerId(rs.getInt("SERVERID"));
        serverInfo.setSystemCPULoad(rs.getDouble("SYSTEMCPULOAD"));
        serverInfo.setTimestamp(rs.getTimestamp("TIEMPO"));
        serverInfo.setTotalPhysicalMemorySize(rs.getLong("TOTALPHYSICALMEMORYSIZE"));
        serverInfo.setTotalSwapSpaceSize(rs.getLong("TOTALSWAPSPACESIZE"));
        
        return serverInfo;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.management.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.management.ManagementMonitorDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.management.impl.mapper.ProcessInfoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.management.impl.mapper.ServerInfoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.management.sql.ManagementMonitorSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ProcessInfo;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ServerInfo;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marco Murakami
 */
@Repository
public class ManagementMonitorDAOImpl implements ManagementMonitorDAO {

    private final Logger log=Logger.getLogger(ManagementMonitorDAOImpl.class);

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    

    @Override
    @Propagable(catched = true)
    public ServerInfo getServerInfoData() {
        log.error("****** INICIA getServerInfoData ******");
        ServerInfo serverInfo = null;
        String query = ManagementMonitorSQL.SERVER_INFO_QUERY;
        log.debug(query);
        List<ServerInfo> serverInfoList = this.template.query(query, new ServerInfoMapper());
        if (!serverInfoList.isEmpty()) {
            serverInfo = serverInfoList.get(0);
        }
        log.error("****** INICIA getServerInfoData ******");
        return serverInfo;
    }

    @Override
    @Propagable(catched = true)
    public List<ProcessInfo> getProcessInfoDataById() {
        log.error("****** INICIA getProcessInfoDataById ******");
        String query = ManagementMonitorSQL.PROCESS_INFO_QUERY;
        log.debug(query);
        log.error("****** TERMINA getProcessInfoDataById ******");
        return  this.template.query(query, new ProcessInfoMapper());
    }
}

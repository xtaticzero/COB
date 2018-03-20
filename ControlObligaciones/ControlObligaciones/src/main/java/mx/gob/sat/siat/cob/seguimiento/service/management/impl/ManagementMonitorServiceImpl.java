/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.management.impl;

import java.util.List;
import java.util.logging.Logger;
import mx.gob.sat.siat.cob.seguimiento.dao.management.ManagementMonitorDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ProcessInfo;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ServerInfo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.management.ManagementMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class ManagementMonitorServiceImpl implements ManagementMonitorService{
    
    // LOGGER
    private final Logger log = Logger.getLogger(ManagementMonitorServiceImpl.class.getName());
    // DAOs
    @Autowired
    private ManagementMonitorDAO managementMonitorDAO;

    @Override
    @Transactional(readOnly = true)
    public ServerInfo getServerInfoData() throws SGTServiceException{
        log.info("***** INICIA getServerInfoData *****");
        ServerInfo serverInfo = managementMonitorDAO.getServerInfoData();
        if (serverInfo == null){
            throw new SGTServiceException("No se encontraron datos para la informacion del servidor. ");
        }
        log.info("***** INICIA getServerInfoData *****");
        return serverInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessInfo> getProcessInfoData() throws SGTServiceException {
        log.info("***** INICIA getProcessesIds *****");
        List<ProcessInfo> processInfoList = managementMonitorDAO.getProcessInfoDataById();
        if (processInfoList == null){
            throw new SGTServiceException("No se encontraron datos para la informacion del proceso. ");
        }
        log.info("***** INICIA getProcessesIds *****");
        return processInfoList;
    }
}

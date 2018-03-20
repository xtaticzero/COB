/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.management;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ProcessInfo;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ServerInfo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface ManagementMonitorService {
    
    /**
     * 
     * @return
     * @throws SGTServiceException 
     */
    ServerInfo getServerInfoData() throws SGTServiceException;
    
    /**
     * 
     * @return
     * @throws SGTServiceException 
     */
    List<ProcessInfo> getProcessInfoData() throws SGTServiceException;
    
}

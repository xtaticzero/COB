/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.management;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ProcessInfo;
import mx.gob.sat.siat.cob.seguimiento.dto.management.ServerInfo;

/**
 *
 * @author Marco Murakami
 */
public interface ManagementMonitorDAO {
    
    ServerInfo getServerInfoData();
    
    List<ProcessInfo> getProcessInfoDataById();
    
}

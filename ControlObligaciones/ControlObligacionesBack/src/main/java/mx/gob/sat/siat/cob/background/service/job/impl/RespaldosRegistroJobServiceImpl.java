package mx.gob.sat.siat.cob.background.service.job.impl;

import java.util.List;
import mx.gob.sat.siat.cob.background.service.job.RespaldosRegistroJobService;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IntentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christian.ventura
 */
@Service
public class RespaldosRegistroJobServiceImpl implements RespaldosRegistroJobService {
    
    private final Logger log =Logger.getLogger(RespaldosRegistroJobServiceImpl.class);
    
    @Autowired
    private EjecucionDAO ejecucionDAO;
    
    @Autowired
    private IntentoDAO intentoDAO;
    
    /**
     * para guardar registros en las tablas de historicos de ejecucion e intento
     */
    @Transactional
    @Override
    public void ejecutarRespaldo(){
        try {
            List<EjecucionDTO> listaEjecuciones = ejecucionDAO.consultarAnteriores();
            log.debug("listaEjecuciones.size:"+listaEjecuciones.size());
            for(EjecucionDTO ejecucion:listaEjecuciones){
                ejecucionDAO.insertarEnHistorico(ejecucion);
                
                List<IntentoDTO> listaIntentos=intentoDAO.consultarPorIdEjecucion(ejecucion.getId());
                log.debug("listaIntentos.size:"+listaIntentos.size());
                for(IntentoDTO intento:listaIntentos){
                    intentoDAO.insertarEnHistorico(intento);
                    intentoDAO.borrarRegistro(intento);
                }
                ejecucionDAO.borrarRegistro(ejecucion);
            }
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
        }
        
    }
    
}

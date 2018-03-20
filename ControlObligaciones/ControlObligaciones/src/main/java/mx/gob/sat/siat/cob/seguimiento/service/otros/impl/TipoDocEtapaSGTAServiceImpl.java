/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.math.BigInteger;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.TipoDocEtapaDAO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocEtapa;
import mx.gob.sat.siat.cob.seguimiento.service.otros.TipoDocEtapaSGTAService;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author root
 */
@Service(value = "tipoDocEtapaSGTA_Service")
@Transactional
public class TipoDocEtapaSGTAServiceImpl implements TipoDocEtapaSGTAService{
    
    private final Logger logger=Logger.getLogger(TipoDocEtapaSGTAServiceImpl.class);
    
    @Autowired
    private TipoDocEtapaDAO tipoDocEtapaDAO;
    @Autowired
    @Qualifier("segbMovimientosDAOImpl")
    private SegbMovimientosDAO segbMovimientosDAO;
    
    /**
     *
     */
    public TipoDocEtapaSGTAServiceImpl() {
        super();
    }

    /**
     *
     * @param id
     * @return
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
                   isolation = Isolation.DEFAULT,
                   propagation = Propagation.NESTED,
                   rollbackFor ={SeguimientoDAOException.class})
    @Override
    public List<TipoDocEtapa> consultarTipoDocEtapa(String id) throws SGTServiceException {
        try {
            return tipoDocEtapaDAO.consultarTipoDocEtapa(id);
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException(e);
        } 
    }
    /**
     *
     * @param id
     * @param segMovDto
     * @throws SGTServiceException
     */
    @Transactional(readOnly = false,
                   isolation = Isolation.DEFAULT,
                   propagation = Propagation.NESTED,
                   rollbackFor ={SeguimientoDAOException.class})
    @Override
    public void actualizarParametrosPorTipoDocumento(TipoDocEtapa id,SegbMovimientoDTO segMovDto) throws SGTServiceException {
        BigInteger folio;       
        try {
            tipoDocEtapaDAO.actualizarParametrosPorTipoDocumento(id);
            folio = segbMovimientosDAO.insert(segMovDto);
            logger.info(folio);
        } catch (SeguimientoDAOException e) {
            throw new SGTServiceException(e);
        }catch (DaoException e) {
            throw new SGTServiceException(e);
        }
        
    }
}

package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EstadoGeneraDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoGenera;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.EstadoGeneraService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoGeneraServiceImpl implements EstadoGeneraService {

    @Autowired
    private EstadoGeneraDAO estadoGeneraDAO;
    private final Logger logger = Logger.getLogger(EstadoGeneraServiceImpl.class);

    /**
     * @param estadoGenera
     */
    @Override
    public void insert(EstadoGenera estadoGenera) throws SGTServiceException {
        try {
            estadoGeneraDAO.insert(estadoGenera);
        } catch (SeguimientoDAOException e) {
            logger.error(e);
            throw new SGTServiceException("No se pudo registrar el estado: " + e);
        }
    }

    /**
     * @return
     */
    @Override
    public List<EstadoGenera> consultaEstadoGenera() throws SGTServiceException {
        List<EstadoGenera> estadoGenera;
        try {
            estadoGenera = estadoGeneraDAO.consultaEstadoGenera();
        } catch (SeguimientoDAOException e) {
            logger.error(e);
            throw new SGTServiceException("No se pudo consultar el estado: " + e);
        }
        return estadoGenera;
    }
}

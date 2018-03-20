package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetallePeriodoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetallePeriodos;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetallePeriodoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePeriodoServiceImpl implements DetallePeriodoService {

    @Autowired
    private DetallePeriodoDAO detallePeriodoDAO;
    private final Logger log = Logger.getLogger(DetallePeriodoServiceImpl.class);

    /**
     *
     */
    public DetallePeriodoServiceImpl() {
        super();
    }

    /**
     * @param detallePeriodo
     * @throws SGTServiceException
     */
    @Override
    public void insert(DetallePeriodos detallePeriodo) throws SGTServiceException {
        try {
            detallePeriodoDAO.insert(detallePeriodo);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo registrar el detalle del periodo",e);
        }
    }

    /**
     * @return @throws SGTServiceException
     */
    @Override
    public List<DetallePeriodos> consultaDetallePeriodos() throws SGTServiceException {
        List<DetallePeriodos> detallePeriodos;
        try {
            detallePeriodos = detallePeriodoDAO.consultaDetallePeriodos();
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo consultar el detalle del periodo",e);
        }
        return detallePeriodos;
    }

    /**
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<DetallePeriodos> detallePeriodosXNumControl(String numControl) throws SGTServiceException {
        List<DetallePeriodos> detallePeriodos;
        try {
            detallePeriodos = detallePeriodoDAO.detallePeriodosXNumControl(numControl);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo registrar el detalle del periodo por numero de control",e);
        }
        return detallePeriodos;
    }
}

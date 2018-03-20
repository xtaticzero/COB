package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleObligaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObliga;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetalleObligaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleObligaServiceImpl implements DetalleObligaService {

    @Autowired
    private DetalleObligaDAO detalleObligaDAO;
    private final Logger log = Logger.getLogger(DetalleObligaServiceImpl.class);

    /**
     *
     */
    public DetalleObligaServiceImpl() {
        super();
    }

    /**
     * @param detalleObliga
     * @throws SGTServiceException
     */
    @Override
    public void insert(DetalleObliga detalleObliga) throws SGTServiceException {
        try {
            detalleObligaDAO.insert(detalleObliga);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo registrar detalleObliga",e);
        }
    }

    /**
     * @return @throws SGTServiceException
     */
    @Override
    public List<DetalleObliga> consultaDetalleObliga() throws SGTServiceException {
        List<DetalleObliga> detalleObligas;
        try {
            detalleObligas = detalleObligaDAO.consultaDetalleObliga();
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo consultar detalleObliga",e);
        }
        return detalleObligas;
    }

    /**
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<DetalleObliga> detalleObligaXNumControl(String numControl) throws SGTServiceException {
        List<DetalleObliga> detalleObligas;
        try {
            detalleObligas = detalleObligaDAO.detalleObligaXNumControl(numControl);
        } catch (SeguimientoDAOException e) {
            log.error(e);
            throw new SGTServiceException("No se pudo consultar detalleObliga por numero de control",e);
        }
        return detalleObligas;
    }
}

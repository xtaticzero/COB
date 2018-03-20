package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EmisionVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.EmisionVigilanciaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmisionVigilanciaServiceImpl implements EmisionVigilanciaService {

    @Autowired
    private EmisionVigilanciaDAO emisionVigilanciaDAO;
private final Logger logger=Logger.getLogger(EmisionVigilanciaServiceImpl.class);

    /**
     * @param emisionVigilancia
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public void insert(EmisionVigilancia emisionVigilancia) throws SGTServiceException {
        try {
            emisionVigilanciaDAO.insert(emisionVigilancia);
        } catch (SeguimientoDAOException e) {
            logger.error(e);
            throw new SGTServiceException("No se pudo registrar la emision de la vigilancia",e);
        }
    }

    /**
     * @return
     * @throws SGTServiceException
     */
    @Override
    public List<EmisionVigilancia> consultaEmisionVigilancia() throws SGTServiceException {
        List<EmisionVigilancia> emisionVigilancias;
        try {
            emisionVigilancias = emisionVigilanciaDAO.consultaEmisionVigilancia();
        } catch (SeguimientoDAOException e) {
            logger.error(e);
            throw new SGTServiceException("No se pudo consultar las emisiones de la vigilancia",e);
        }
        return emisionVigilancias;
    }

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public List<EmisionVigilancia> emisionVigXEdoEmision() throws SGTServiceException {
        List<EmisionVigilancia> emisionVigilancias;
        try {
            emisionVigilancias = emisionVigilanciaDAO.emisionVigXEdoEmision();
        } catch (SeguimientoDAOException e) {
            logger.error(e);
            throw new SGTServiceException("No se pudo consultar las emisiones por estado de emision",e);
        }
        return emisionVigilancias;
    }
}

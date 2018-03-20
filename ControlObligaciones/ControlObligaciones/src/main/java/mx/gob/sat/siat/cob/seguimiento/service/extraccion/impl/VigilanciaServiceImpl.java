package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class VigilanciaServiceImpl implements VigilanciaService {

    @Autowired
    private VigilanciaDAO vigilanciaDAO;
    private final Logger logger = Logger.getLogger(VigilanciaServiceImpl.class);

    @Override
    public List<EmisionVigilancia> conusltaVigilanciaXEtapa() throws SGTServiceException {
        List<EmisionVigilancia> emisionVigilancias;
        try {
            emisionVigilancias = vigilanciaDAO.conusltaVigilanciaXEtapa();
        } catch (SeguimientoDAOException e) {
            logger.error(e);
            throw new SGTServiceException("No se pudieron consultar las vigilancias por etapa", e);
        }
        return emisionVigilancias;
    }

    @Override
    public Integer consultaIdTipoFirma(Long idvigilancia) throws SGTServiceException {
        try {
            return vigilanciaDAO.consultaIdTipoFirma(idvigilancia);
        } catch (SeguimientoDAOException ex) {
            logger.error(ex);
            throw new SGTServiceException(ex);
        }
    }
}

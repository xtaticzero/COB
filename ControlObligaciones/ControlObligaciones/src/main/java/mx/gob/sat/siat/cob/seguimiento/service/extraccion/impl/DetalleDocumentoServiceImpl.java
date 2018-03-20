package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetalleDocumentoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class DetalleDocumentoServiceImpl implements DetalleDocumentoService {
    
    private Logger log = Logger.getLogger(DetalleDocumentoServiceImpl.class.getName());
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;

    /**
     *
     * @param detalleDocumento
     */
    @Override
    @Transactional
    public void guardaDetalleDocumento(DetalleDocumento detalleDocumento) {
        try {
            detalleDocumentoDAO.guardaDetalleDocumento(detalleDocumento);
        } catch (SeguimientoDAOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

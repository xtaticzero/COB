package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BitacoraDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.BitacoraDocumentoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("bitacoraDocumentoService")
public class BitacoraDocumentoServiceImpl implements BitacoraDocumentoService {

    private final Logger log = Logger.getLogger(BitacoraDocumentoServiceImpl.class);
    @Autowired
    private BitacoraDocumentoDAO bitacoraDocumentoDAO;

    @Override
    public void registraBitacoraMulta(String numeroControl, int idEstadoDocto) throws SGTServiceException {
        log.info("### Registrar en bitacora " + numeroControl);
        BitacoraDocumento bitacora = new BitacoraDocumento();
        bitacora.setIdEstadoDocto(idEstadoDocto);
        bitacora.setNumeroControl(numeroControl);
        bitacoraDocumentoDAO.insertaBitacoraDocumento(bitacora);
    }
}

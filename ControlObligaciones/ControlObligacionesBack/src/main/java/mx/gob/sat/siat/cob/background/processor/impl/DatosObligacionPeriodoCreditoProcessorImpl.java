package mx.gob.sat.siat.cob.background.processor.impl;

import mx.gob.sat.siat.cob.background.processor.DatosObligacionPeriodoCreditoProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SustitucionMotivacionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service("datosObligacionPeriodoCreditoProcessor")
public class DatosObligacionPeriodoCreditoProcessorImpl implements DatosObligacionPeriodoCreditoProcessor {

    private static Logger logger = Logger.getLogger(DatosObligacionPeriodoCreditoProcessorImpl.class);
    @Autowired
    private SustitucionMotivacionService motivacionService;

    @Override
    public ObligacionPeriodo process(ObligacionPeriodo obligacionPeriodo) {
        try {
            motivacionService.sustituirMotivacion(obligacionPeriodo.getObligacion());
        } catch (SGTServiceException ex) {
            logger.error("No se genero la cadena motivacion: " + obligacionPeriodo.getObligacion().getIdDocumento());
            logger.error(ex);
        }
        return obligacionPeriodo;
    }
}

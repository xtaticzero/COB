package mx.gob.sat.siat.cob.background.processor;

import org.springframework.batch.item.ItemProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.SeguimientoARCA;
import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionPorNotificacionService;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("afectacion-notificacion-processor")
public class AfectacionNotificacionProcessor implements ItemProcessor<SeguimientoARCA, SeguimientoARCA> {

    @Autowired
    private AfectacionPorNotificacionService afectacionPorNotificacionService;

    /**
     *
     * @param elemento
     * @return
     * @throws Exception
     */
    @Override
    public SeguimientoARCA process(SeguimientoARCA elemento) throws SGTServiceException {
        afectacionPorNotificacionService.actualizaEstadoDocumento(elemento);
        return elemento;
    }

    /**
     *
     * @param afectacionPorNotificacionService
     */
    public void setAfectacionPorNotificacionService(AfectacionPorNotificacionService afectacionPorNotificacionService) {
        this.afectacionPorNotificacionService = afectacionPorNotificacionService;
    }
}

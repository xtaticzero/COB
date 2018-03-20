/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionMovimientosPadronService;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author root
 */
public class AfectacionMovimientosPadronTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void ejecutarProcesoCargaAfectacion() {
        try {
            AfectacionMovimientosPadronService afectacionMovimientosPadronService
                    = (AfectacionMovimientosPadronService) getContext().getBean("afectacionMovimientosPadronService");
            afectacionMovimientosPadronService.cargarMovimientoPadron();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    @Test
    @Ignore
    public void ejecutarAfectacionPorMovimientosAlPadron() {
         AfectacionMovimientosPadronService afectacionMovimientosPadronService
                = (AfectacionMovimientosPadronService) getContext().getBean("afectacionMovimientosPadronService");
        try {
            afectacionMovimientosPadronService.afectarPorMovimientoEnPadron();
        } catch (SGTServiceException e) {
            log.error(e);
        }
    }
}

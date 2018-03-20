/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.sat.siat.cob.background.service.afectaciones.ExtraccionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author root
 */
public class ExtraccionCumplimientoTest extends ContextLoaderTest{
    @Test
    @Ignore
    public void consultaHistoricosTest(){
        try {
            ExtraccionCumplimientoService extraccionCumplimientoService =
                    (ExtraccionCumplimientoService) getContext().getBean("extraccionCumplimientoService");
            extraccionCumplimientoService.extraerCumplimientosPaginados();
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }
}

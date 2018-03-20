/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;


import mx.gob.sat.siat.cob.background.service.afectaciones.CargaCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.junit.Ignore;

import org.junit.Test;


/**
 *
 * @author rodrigo
 */
public class HistoricoCumplimientoTest extends ContextLoaderTest{
    
    
    @Test
    @Ignore
    public void consultaHistoricosTest() throws SGTServiceException{
        CargaCumplimientoService cargaCumplimientoService =
                (CargaCumplimientoService) getContext().getBean("cargaCumplimientoPaginadoService");
        cargaCumplimientoService.cargarCumplimientos(Utilerias.getYesterday());
    }
}

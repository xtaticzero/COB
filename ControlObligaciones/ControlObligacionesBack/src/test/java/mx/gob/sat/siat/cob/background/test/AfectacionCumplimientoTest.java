package mx.gob.sat.siat.cob.background.test;

import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionCumplimientoService;

import org.junit.Ignore;
import org.junit.Test;

public class AfectacionCumplimientoTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void afectarPorCumplimientoTest() {
        AfectacionCumplimientoService afectacionCumplimientoService =
                (AfectacionCumplimientoService) getContext().getBean("afectacionCumplimientoService");
        try {
            afectacionCumplimientoService.afectarPorCumplimientos();
        } catch (Exception ex) {
            System.out.println("Error en prueba " + ex);
            assert false;
        }
    }
}

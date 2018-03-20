package mx.gob.sat.siat.cob.background.test;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetalleObligaPeriodoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetalleObligaService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetallePeriodoService;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObligaPeriodo;

import org.junit.Ignore;
import org.junit.Test;


public class DetalleObligaPeriodoTest extends ContextLoaderTest {

    public DetalleObligaPeriodoTest() {
        super();
    }

    @Test
    @Ignore
    public void prueba() {

        System.out.print("Prueba : +++++++++++++++++++++++++");
        DetalleObligaPeriodoService detalleObligaPeriodoService =
            (DetalleObligaPeriodoService)getContext().getBean("detalleObligaPeriodoServiceImpl");
        DetalleObligaService obligaService = (DetalleObligaService)getContext().getBean("detalleObligaServiceImpl");
        DetallePeriodoService periodoService =
            (DetallePeriodoService)getContext().getBean("detallePeriodoServiceImpl");
        List<DetalleObligaPeriodo> obligaPeriodos;
        try {

            obligaPeriodos = detalleObligaPeriodoService.buscaDetalleOblicaPeriodos();
            assert obligaPeriodos != null;

            for (DetalleObligaPeriodo obligaPeriodo : obligaPeriodos) {

                obligaService.insert(obligaPeriodo.getDetalleObliga());
                periodoService.insert(obligaPeriodo.getDetallePeriodo());

            }

        } catch (Exception ex) {
        }
    }


}

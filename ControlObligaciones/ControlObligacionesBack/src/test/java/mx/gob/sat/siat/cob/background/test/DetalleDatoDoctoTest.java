package mx.gob.sat.siat.cob.background.test;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DatoDoctoVigilanciaService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.EmisionVigilanciaService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaService;
import mx.gob.sat.siat.cob.seguimiento.dto.idc.Persona;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDoctoVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;

import org.junit.Ignore;
import org.junit.Test;


public class DetalleDatoDoctoTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void vigilanciaXEtapa() {
        System.out.print("Prueba : +++++++++++++++++++++++++");

        VigilanciaService vigilanciaService = (VigilanciaService)getContext().getBean("vigilanciaServiceImpl");

        EmisionVigilanciaService emisionVigilanciaService =
            (EmisionVigilanciaService)getContext().getBean("emisionVigilanciaServiceImpl");

        List<EmisionVigilancia> emisionVigilancias = new ArrayList<EmisionVigilancia>();
        try {
            emisionVigilancias = vigilanciaService.conusltaVigilanciaXEtapa();

            System.out.println("TAM: " + emisionVigilancias.size());

            for (EmisionVigilancia emisionVigilancia : emisionVigilancias) {
                System.out.println("\n ··· " + emisionVigilancia.getEsPlantillaDIOT());
                System.out.println("··· " + emisionVigilancia.getIdEtapaVigilancia());
                System.out.println("··· " + emisionVigilancia.getIdTipoDocumento());
                System.out.println("··· " + emisionVigilancia.getIdVigilancia());

                emisionVigilancia.setIdEstadoEmision(0);

                emisionVigilanciaService.insert(emisionVigilancia);

            }
        } catch (Exception e) {
            log.error(e);
        }

    }


}

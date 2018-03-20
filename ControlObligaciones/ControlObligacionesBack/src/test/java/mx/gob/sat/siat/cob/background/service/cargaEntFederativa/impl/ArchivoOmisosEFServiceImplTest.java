package mx.gob.sat.siat.cob.background.service.cargaEntFederativa.impl;

import java.util.List;
import mx.gob.sat.siat.cob.background.service.cargaentidadfederativa.ArchivoOmisosEFService;
import mx.gob.sat.siat.cob.background.test.ContextLoaderTest;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionEFDTO;
import mx.gob.sat.siat.cob.seguimiento.service.otros.OmisosEFService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author christian.ventura
 */
public class ArchivoOmisosEFServiceImplTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void ejecutarArchivoOmisosEFTest() {
        ArchivoOmisosEFService c = (ArchivoOmisosEFService) getContext().getBean("archivoOmisosEFServiceImpl");
        c.generaArchivoOmisos();
        System.out.println("termino.....");
    }

    @Test
    @Ignore
    public void testConsultarDatosUbicacion(){
        OmisosEFService omisosEFService=(OmisosEFService) getContext().getBean("omisosEFServiceImpl");
        List<UbicacionEFDTO> lista=omisosEFService.consultarDatosUbicacion("542176075040988484241511325464");
        for(UbicacionEFDTO dato:lista){
            System.out.println(dato.getReferenciaAdicionales()+" otro dato");
        }
        lista=omisosEFService.consultarDatosUbicacion("504553078626279165699816284810");
        for(UbicacionEFDTO dato:lista){
            System.out.println("salto:");
            System.out.println(dato.getReferenciaAdicionales()+" otro dato");
            System.out.println("quita salto:");
            System.out.println(Utilerias.quitarSaltosDeLinea(dato.getReferenciaAdicionales()+" otro dato mas \n otro"));
        }
    }
    
}

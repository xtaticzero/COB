package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import mx.gob.sat.siat.cob.background.test.ContextLoaderTest;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SustitucionMotivacionService;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author root
 */
public class SustitucionMotivacionServiceImplTest extends ContextLoaderTest {
    
    
    @Test
    @Ignore
    public void ejecutarCargaOmisosTest() {
        SustitucionMotivacionService c = (SustitucionMotivacionService) getContext().getBean("sustitucionMotivacionServiceImpl");
        
        Obligacion obligacion=new Obligacion();
        obligacion.setIdPeriodo("1");
        obligacion.setIdPeriodicidad("M");
        obligacion.setEjercicio(2013);
        obligacion.setIdDocumento("23423423423423424");
        obligacion.setFechaNotificacion("12/06/2014");
        obligacion.setIdObligacion("2");
        obligacion.setConstanteResolMotivo("RESOLMOTIVO_EXTEMPORANEIDAD");
        try {
            c.sustituirMotivacion(obligacion);
        } catch (SGTServiceException ex) {
            System.out.println(ex);
        }
        System.out.println(obligacion);
        System.out.println("termino.....");
    }
    
}

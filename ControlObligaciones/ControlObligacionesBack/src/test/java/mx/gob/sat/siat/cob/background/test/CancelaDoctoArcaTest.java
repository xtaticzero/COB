package mx.gob.sat.siat.cob.background.test;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.CancelaDoctoService;

import org.apache.log4j.Logger;

import org.junit.Ignore;
import org.junit.Test;


public class CancelaDoctoArcaTest extends ContextLoaderTest{
   
    private final Logger log =Logger.getLogger(CancelaDoctoArcaTest.class);
        
    @Test
    @Ignore
    public void prueba(){        
        DocumentoARCA doctoArca = new DocumentoARCA();
        CancelaDoctoService cds = (CancelaDoctoService) getContext().getBean("cancelaDoctoServiceImpl");        
               
        log.info("\n=========================== CancelaDoctoArcaTest ==============================\n");
               
        try {
            doctoArca.setId("PRUEBA01");                      
            
            cds.cancelaDoctoArca("PRUEBA01");
            
            System.out.println("Exit Status : ");
            assert cds!=null;
            
        } catch (SGTServiceException e) {
            log.error(e);
        }
        
        
        log.info("Done");
    }
}

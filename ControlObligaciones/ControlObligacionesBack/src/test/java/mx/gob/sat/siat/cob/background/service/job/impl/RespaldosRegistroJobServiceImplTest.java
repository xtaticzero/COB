package mx.gob.sat.siat.cob.background.service.job.impl;

import mx.gob.sat.siat.cob.background.service.job.RespaldosRegistroJobService;
import mx.gob.sat.siat.cob.background.test.ContextLoaderTest;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author root
 */
public class RespaldosRegistroJobServiceImplTest extends ContextLoaderTest {
    
    /**
     * Test of ejecutarRespaldo method, of class RespaldosRegistroJobServiceImpl.
     */
    @Test
    @Ignore
    public void testEjecutarRespaldo() {
        System.out.println("--------------ejecutarRespaldo---------------");
        RespaldosRegistroJobService instance = (RespaldosRegistroJobService)getContext().getBean("respaldosRegistroJobServiceImpl");
        instance.ejecutarRespaldo();
        System.out.println("termino.....");
    }
}
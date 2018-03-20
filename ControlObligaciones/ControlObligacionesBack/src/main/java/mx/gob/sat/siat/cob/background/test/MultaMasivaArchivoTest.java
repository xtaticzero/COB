package mx.gob.sat.siat.cob.background.test;

import mx.gob.sat.siat.cob.background.service.multa.CargaArchivosMasivosArca;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *
 * @author Juan
 */
public final class MultaMasivaArchivoTest {

    private MultaMasivaArchivoTest() {
        super();
    }

    public static void main(String[] arg) throws SGTServiceException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("development");
        context.load("applicationContext-main.xml");
        context.refresh();
        CargaArchivosMasivosArca cargaArchivosMasivosArca = (CargaArchivosMasivosArca) context.getBean("cargaArchivosMasivosArca");
        cargaArchivosMasivosArca.cargaArchivos();
    }
}

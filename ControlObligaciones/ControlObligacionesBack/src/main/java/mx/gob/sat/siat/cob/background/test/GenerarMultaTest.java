/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.text.ParseException;
import mx.gob.sat.siat.cob.background.service.multa.GenerarMulta;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cobranza.negocio.util.excepcion.FacadeNegocioException;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *
 * @author Juan
 */
public final class GenerarMultaTest {

    private GenerarMultaTest() {
        super();
    }

    public static void main(String[] arg) throws ParseException, SGTServiceException, FacadeNegocioException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("development");
        context.load("applicationContext-main.xml");
        context.refresh();
        GenerarMulta generaMulta = (GenerarMulta) context.getBean("generarMulta");
        generaMulta.generarMulta();
        /**
         * Baja en cobranza. BajasFacade bajaResolucion = (BajasFacade)
         * context.getBean("bajasFacade");
         * bajaResolucion.bajasPorImprocedencia(new Long(193233), new Date(),
         * 1213L);
         */
    }
}
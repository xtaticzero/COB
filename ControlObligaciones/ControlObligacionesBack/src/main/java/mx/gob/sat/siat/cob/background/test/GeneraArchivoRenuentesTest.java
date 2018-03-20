/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.text.ParseException;
import mx.gob.sat.siat.cob.background.service.renuente.GeneraArchivoRenuentes;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cobranza.negocio.util.excepcion.FacadeNegocioException;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *
 * @author juan
 */
public class GeneraArchivoRenuentesTest {

    private GeneraArchivoRenuentesTest() {
        super();
    }

    public static void main(String[] arg) throws ParseException, SGTServiceException, FacadeNegocioException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("development");
        context.load("applicationContext-main.xml");
        context.refresh();
        GeneraArchivoRenuentes archivoRenuentes = (GeneraArchivoRenuentes) context.getBean("generaArchivoRenuentes");
        archivoRenuentes.generarArchivo();

    }
}

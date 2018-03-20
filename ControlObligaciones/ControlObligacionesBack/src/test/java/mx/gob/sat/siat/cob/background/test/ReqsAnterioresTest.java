/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.DoctoEnvioArcaService;
import mx.gob.sat.siat.cob.seguimiento.service.arca.ReqsAnterioresARCAService;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Juan
 */
public class ReqsAnterioresTest extends ContextLoaderTest{
    
    @Test    
    @Ignore
    public void ReqsAnterioresTest() {
        
        log.info("### Empieza proceso para obtener Reqs Anteriores");
                    DoctoEnvioArcaService doctoEnvioArcaService =
                            (DoctoEnvioArcaService) getContext().getBean("doctoEnvioArcaService");
                    ReqsAnterioresARCAService reqsAnterioresARCAService =
                            (ReqsAnterioresARCAService) getContext().getBean("reqsAnterioresARCAService");

                    List<RequerimientosAnteriores> reqsAnteriores = null;
                    Set<String> numerosControl = new HashSet<String>();
                    try {
                        numerosControl.add("8500001001004");
                        numerosControl.add("8500001001004");
                        numerosControl.add("8500001001007");
                        
                        reqsAnteriores =
                                doctoEnvioArcaService.obtenerReqsAnteriores(numerosControl);
                    } catch (SGTServiceException ex) {
                        log.error("### Fallo al traer Requerimientos Anteriores" + ex);
                    }
                    try {
                        log.info("### Empieza a guardar Reqs Anteriores en ARCA");
                        if(reqsAnteriores!=null){
                            reqsAnterioresARCAService.guardaReqsAnteriores(reqsAnteriores);
                        }else{
                            log.info("### La lista de requerimientos es null ");
                        }
                    } catch (SGTServiceException ex) {
                        log.error("### Fallo al insertar Requerimientos Anteriores");
                    }

    }
    
}

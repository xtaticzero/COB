/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.AfectacionService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author root
 */
public class CancelarMultaTest extends ContextLoaderTest {
    
    private MtvoCancelDoc cancelacion;
    
     @Test
    @Ignore
    public void CancelarMulta () throws SGTServiceException{
     
    
        MultaService multa = (MultaService) getContext().getBean("multaServiceImpl");
        
    
        
         List<MultaDocumento> multas = multa.buscarMultasPorNumeroControl("101206140011694C53046");
         if (multas.get(0) != null){
             multa.cancelarMulta(multas.get(0));
         }
     }
     
    @Test
    @Ignore
    public void CancelarMultaAutoridad () throws SGTServiceException{
    
         cancelacion = new MtvoCancelDoc();
         cancelacion.setIdMotivoCancelacion(32L);
         
        AfectacionService afectacionService = (AfectacionService) getContext().getBean("afectacionServiceImpl");

        DocumentoService documentoService = (DocumentoService)getContext().getBean("documentoServiceImpl");

        
        List<Documento> listaDocumentos = new ArrayList<Documento>();


            Documento documento = documentoService.consultaXNumControl("101206140009879C67258");
            listaDocumentos.add(documento);

        afectacionService.cancelarDocumentoXAutoridad(listaDocumentos, cancelacion);
        
     } 
     
}

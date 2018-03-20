/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.AfectacionEnum;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.AfectacionService;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author root
 */
public class AfectacionTest extends ContextLoaderTest {
    @Test
    @Ignore
    public void generaDocumentosAfectacionTest(){
        Documento documento;
        DocumentoDAO documentoDAO = (DocumentoDAO)getContext().getBean("documentoDAOImpl");
        DetalleDocumentoDAO detalleDocumentoDAO = (DetalleDocumentoDAO)getContext().getBean("detalleDocumentoDAOImpl");
        AfectacionService afectacionService = (AfectacionService) getContext().getBean("afectacionServiceImpl");
        try {
            documento = documentoDAO.consultaXNumControl("99984");
            documento.setDetalles(detalleDocumentoDAO.consultaXNumControl(documento.getNumeroControl()));
            System.out.println ("El id de documento es " + documento.getNumeroControl());
            afectacionService.generaDocumentosAfectacion(documento, AfectacionEnum.AFECTACION_CUMPLIMIENTO);
        } catch (Exception ex) {
            log.error("\n Error al generar documentos afectacion \n" + ex.getMessage());
            assert false;
        }
        
    }
    @Test
    @Ignore
    public void cancelarDocumentoTest(){
        Documento documento;
        DocumentoDAO documentoDAO = (DocumentoDAO)getContext().getBean("documentoDAOImpl");
        AfectacionService afectacionService = (AfectacionService) getContext().getBean("afectacionServiceImpl");
        try {
            log.info("cancelando documento");
            documento = documentoDAO.consultaXNumControl("99984");
            System.out.println ("El id de documento es " + documento.getNumeroControl());
            afectacionService.cancelarDocumento(documento);
        } catch (Exception ex) {
            log.error("\n Error al cancelar el documento \n" + ex.toString());
            assert false;
        }
    }
}
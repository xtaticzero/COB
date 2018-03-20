/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Vigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author root
 */
public class MultaTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void probarMulta() throws Exception {
        
        DetalleDocumentoDAO detalleDocumentoDAO = (DetalleDocumentoDAO)getContext().getBean("detalleDocumentoDAOImpl");
        
        Documento d=new Documento();
        d.setNumeroControl("103005148597335C36105");
        d.setBoid("681410442547635260931016109304");
        Documento d1=d.clone();
        
        Vigilancia vigilancia = new Vigilancia();
        vigilancia.setIdVigilancia(352L);
        vigilancia.setIdTipoMedio(2);
        d1.setVigilancia(vigilancia);
        
        d1.setDetalles(detalleDocumentoDAO.consultaXNumControl(d1.getNumeroControl()));
        MultaService m = (MultaService) getContext().getBean("multaServiceImpl");
        m.generarMulta(d1, TipoMultaEnum.INCUMPLIMIENTO);

    }
}

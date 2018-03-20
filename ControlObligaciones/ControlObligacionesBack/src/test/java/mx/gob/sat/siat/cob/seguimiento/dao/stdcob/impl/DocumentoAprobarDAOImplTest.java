package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.background.test.ContextLoaderTest;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author christian.ventura
 */
public class DocumentoAprobarDAOImplTest extends ContextLoaderTest {
    
    @Test
    @Ignore
    public void ejecutarArchivoOmisosEFTest() {
        log.debug("-----------------------------------------------------------");
        DocumentoAprobarDAO documentoAprobarDAO = (DocumentoAprobarDAO) getContext().getBean("documentoAprobarDAOImpl");
        Paginador pag=new Paginador(15027, 1500);
        log.debug(pag);
        List<DocumentoAprobar> lista = documentoAprobarDAO.listarDocumentosIcep(pag, "464", "01");
        log.debug(lista);
        for(DocumentoAprobar dato:lista){
            log.debug(dato);
        }

    }
    
}

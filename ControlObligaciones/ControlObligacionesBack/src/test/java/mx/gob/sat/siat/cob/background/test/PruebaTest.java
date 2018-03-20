package mx.gob.sat.siat.cob.background.test;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import org.junit.Ignore;

import org.junit.Test;

public class PruebaTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void prueba() {
        DocumentoDAO d = (DocumentoDAO) getContext().getBean("documentoDAOImpl");
         List<Documento> documentos = d.documentosAprocesar(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});

        if (documentos == null) {
            log.error("No se pudo obtener la lista");
            return;
        }
//List<Documento> documentos = d.documentosAprocesar(null);
        for (Documento documento : documentos) {
            log.info(documento.getNumeroControl());
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import org.apache.log4j.Logger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 *
 * @author root
 */
public final class UtileriasDocx {

    private static final String PATH_DOCX = "/siat/cob/tmp/doc";
    private static final String EXT_DOCX = ".docx";
    private static final Logger LOG = Logger.getLogger(UtileriasDocx.class);

    private UtileriasDocx() {
    }

    public static String mergeDocumentos(List<byte[]> documentos) throws SGTServiceException {
        String ruta = PATH_DOCX + new Date() + EXT_DOCX;
        try {
            File docx = new File(ruta);
            if (docx.createNewFile()) {
                WordprocessingMLPackage original = null;
                for (byte[] bs : documentos) {
                    InputStream ism = new ByteArrayInputStream(bs);
                    if (original == null) {
                        original = WordprocessingMLPackage.load(ism);
                    } else {
                        WordprocessingMLPackage merge = WordprocessingMLPackage.load(ism);
                        for (Object object : merge.getMainDocumentPart().getContent()) {
                            original.getMainDocumentPart().addObject(object);
                        }
                    }
                    original.save(docx);
                }
            }else{
                throw new SGTServiceException("No se pudo crear el archivo del documento");
            }
        } catch (Docx4JException e) {
            ruta = null;
            LOG.error(e);
        } catch (IOException e) {
            ruta = null;
            LOG.error(e);
        }
        return ruta;
    }
}

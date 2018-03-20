/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.DocumentoARCAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("documentoARCAService")
public class DocumentoARCAServiceImpl implements DocumentoARCAService {

    @Autowired
    private DocumentoARCADAO documentoArcaDAO;

    @Override
    public void insertarDocumento(List<DocumentoARCA> documentos) throws SGTServiceException {
        if (documentoArcaDAO.insert(documentos) == null) {
            throw new SGTServiceException("Fallo al insertar registros en T_Documento");
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;

/**
 *
 * @author root
 */
public interface TmpDocumentoARCADAO {

    /**
     *
     * @param doctoARCA
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion
     */
    void updateTmpDocumentoARCA(DocumentoARCA doctoARCA) throws ARCADAOExcepcion;

    /**
     *
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion
     */
    List<DocumentoARCA> selectTmpDocumentoARCA() throws ARCADAOExcepcion;
}

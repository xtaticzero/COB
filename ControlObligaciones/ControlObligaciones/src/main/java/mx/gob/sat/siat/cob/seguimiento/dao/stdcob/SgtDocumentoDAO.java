package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;


import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;


public interface SgtDocumentoDAO {

    /**
     * @param documento
     * @return
     * @throws SeguimientoDAOException
     */
    int guardaSgtDocumento(Documento documento) throws SeguimientoDAOException;
    
    /**
     * proceso de insertar en batch
     * @param documentos
     * @return 
     */
    int guardaSgtDocumentoBatch(final List<Documento> documentos);
    
    /**
     * Actualiza bitacora de un documento
     *
     * @param documento
     * @throws SeguimientoDAOException 
     */
    void actualizarBitacoraEdoDocumento(Documento documento) throws SeguimientoDAOException;
}

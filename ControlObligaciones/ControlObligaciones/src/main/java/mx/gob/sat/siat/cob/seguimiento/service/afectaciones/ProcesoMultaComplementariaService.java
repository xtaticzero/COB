/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 * Servicio para procesar los detalles de documento a los que se les aplican
 * multas complementarias.
 *
 * @author Gabriel García
 */
public interface ProcesoMultaComplementariaService {

    /**
     * Método para actualizar los detalles de documento que apliquen para una
     * multa complementaria.
     
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    void afectarDetallesConComplementaria() throws SGTServiceException;

    /**
     * Método para obtener una lista de los documentos cuyos detalles apliquen
     * para multa complementaria
     *
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    List<Documento> documentosMultaComplementaria() throws SGTServiceException;
    
    List<Documento> documentosMultaComplementaria(String idVigilancia, String idLocal) throws SGTServiceException;

    /**
     * Método para generar las multas complementarias de un documento y
     * actualizar su detalle de documento
     *
     * @param documento Documento a procesar.
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    void procesarDocumentoMultaComplementaria(Documento documento) throws SGTServiceException;
    
    /**
     * Método para generar multas complementarias de forma masiva
     *
     * @param documentos Documentos que aplican para generar multas complementarias.
     */
     void generarMultasComplementarias(List<Documento> documentos) throws SGTServiceException;
}

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.NumeroControlDTO;

/**
 *
 * @author root
 */
public interface BaseDocumentoDAO {

    /**
     * Obtiene un nuevo identificador(numerocontrol) para el insert de documento y su detalle
     * @param boid
     * @param tipoDocumento
     * @param etapaVigilancia
     * @return numero de control
     */
    NumeroControlDTO getNumeroControl(String boid, Integer tipoDocumento, Integer etapaVigilancia);

    /**
     * Obtiene un nuevo identificador(numerocontrol) para el insert de documento y su detalle
     * @param boid
     * @param resolMotivo
     * @return numero de resolucion
     */
    NumeroControlDTO getNumeroResolucion(String boid, String resolMotivo);

    /**
     * genera conjunto de numero de control
     * @param boids 
     * @param tipoDocumento
     * @param etapaVigilancia
     * @param contador 
     * @return
     */
    int generaNumerosControl(List<String> boids,
            Integer tipoDocumento, Integer etapaVigilancia, int contador);

    /**
     * obtiene el numero de control generado consultando por boid y lo transforma en objeto
     * numerocontroldto junto con informacion de ubicacion
     * @param boid
     * @return
     */
    NumeroControlDTO getTransformacionNumeroControl(String boid);

    /**
     * genera conjunto de numero de resoluciones 
     * @param documentos
     * @param resolMotivo
     * @param flgCommit 
     * @return numero de resolucion
     */
    int generaNumeroResoluciones(List<Documento> documentos, String resolMotivo, Integer flgCommit);

    /**
     * guarda el documento y sus detalles
     * @param documentos
     * @param tipoDocumento
     * @param etapaVigilancia
     * @return
     * @throws SQLException 
     */
    String guardaDocumentosDetalles(Documento documentos, Integer tipoDocumento, Integer etapaVigilancia)throws SQLException;
    
    /**
     * borra los datos de la tabla temporal
     * @return int
     */
    Integer borrarTablaNumeroResolucion();
    
    /**
     * 
     * @return 
     */
    List<NumeroControlDTO> consultaResoluciones();
    
    /**
     *
     */
    void initCallerDocs();

    /**
     *
     */
    void cleanCallerDocs();
}

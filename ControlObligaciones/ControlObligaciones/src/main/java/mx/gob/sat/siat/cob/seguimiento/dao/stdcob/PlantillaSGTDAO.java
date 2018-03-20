package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;

/**
 *
 * @author root
 */
public interface PlantillaSGTDAO {
    
    void eliminarPlantillasRepetidas(PlantillaDocumento plantillaDocumento);

    /**
     *
     * @param plantillaDocumento
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    int guardarPlantilla(PlantillaDocumento plantillaDocumento) throws SeguimientoDAOException;

    /**
     *
     * @return @throws
     * mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<PlantillaDocumento> buscarPlantillasDocumento() throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param idTipoDocumento
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    String buscarPlantilla(long idVigilancia, int idTipoDocumento) throws SeguimientoDAOException;

    /**
     *
     * @param plantillaDocumento
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    void guardarPlantillaArca(PlantillaDocumento plantillaDocumento) throws SeguimientoDAOException;

    /**
     *
     * @return @throws
     * mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<PlantillaDocumento> buscarPlantillasArca() throws SeguimientoDAOException;

    /**
     *
     * @param idPlantilla
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    void actualizarEstadoPlantillaArca(int idPlantilla) throws SeguimientoDAOException;

    /**
     *
     * @param p
     * @param tipo
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    PlantillaDocumento buscarPlantillaArcaPorParametros(PlantillaDocumento p, String tipo) throws SeguimientoDAOException;
    
    PlantillaDocumento buscarPlantillaArcaPorParametros(PlantillaDocumento p) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<BitacoraErrores> buscarBitacoraErroresPorIdVigilancia(Integer idVigilancia, String fechaInicio, String fechaFin) throws SeguimientoDAOException;
    
    /**
     *
     * @param idVigilancia
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<BitacoraErrores> buscarBitacoraErroresPorIdVigilanciaSinRutaBitacora(Integer idVigilancia, String fechaInicio, String fechaFin) throws SeguimientoDAOException;

    /**
     *
     * @throws SeguimientoDAOException
     */
    void guardaPlantillaArcaBatch(EsMultaEnum esMulta) throws SeguimientoDAOException;

    /**
     *
     * @throws SeguimientoDAOException
     */
    void eliminaPlantillaArcaBatch() throws SeguimientoDAOException;
}

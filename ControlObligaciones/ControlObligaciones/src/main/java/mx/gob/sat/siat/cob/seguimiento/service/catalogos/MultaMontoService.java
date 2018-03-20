package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


/**
 *
 * @author root
 */
public interface MultaMontoService {
     /**
     *
     * @return
     */
    List<Combo> obtenerListaComboObligacion();
     /**
     *
     * @return
     */
    List<MultaMonto> todasLasOblisanciones();
     /**
     *
     * @param oblisancion
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void agregaOblisancion(MultaMonto oblisancion, SegbMovimientoDTO segMovDto, boolean bol)throws SeguimientoDAOException, DaoException;
     /**
     *
     * @param oblisancion
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void editaOblisancion(MultaMonto oblisancion, SegbMovimientoDTO segMovDto,  boolean bol)throws SeguimientoDAOException, DaoException;
     /**
     *
     * @param oblisancion
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void eliminaOblisancion(MultaMonto oblisancion, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
    
     /**
     *
     * @param list
     * @return
     */
    ByteArrayOutputStream generaExcel(List<MultaMonto> list);
     /**
     *
     * @param list
     * @return
     */
    ByteArrayOutputStream generaPDF(List<MultaMonto> list);
     /**
     *
     * @return
     */
    List<ComboStr> obtenerListaComboTipoMulta();
     /**
     *
     * @return
     */
    List<CatalogoBase> getOblisancionMotivaciones();

    Integer buscarMultaMontoRepetida(Long idObligacionSan, String idTipoMultaSel);
}

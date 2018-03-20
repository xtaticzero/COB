package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


/**
 *
 * @author root
 */
public interface FundamentoLegalService {
     /**
     *
     * @return
     */
    List<FundamentoLegal> todosLosFundamentos();
     /**
     *
     * @param fundamentoLegal
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void agregaFundamentoLegal(FundamentoLegal fundamentoLegal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException ;
     /**
     *
     * @param fundamentoLegal
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void editaFundamentoLegal(FundamentoLegal fundamentoLegal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
     /**
     *
     * @param fundamentoLegal
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void eliminaFundamentoLegal(FundamentoLegal fundamentoLegal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
     /**
     *
     * @param fundamentoLegal
     * @return
     */
    Integer buscarFundamentoLegalPorIdObligYIdReg(FundamentoLegal fundamentoLegal);
     /**
     *
     * @param list
     * @return
     */
    ByteArrayOutputStream generaExcel(List<FundamentoLegal> list);
     /**
     *
     * @param list
     * @return
     */
    ByteArrayOutputStream generaPDF(List<FundamentoLegal> list);
     /**
     *
     * @return
     */
    List<Combo> obtenerComboObligacion();
     /**
     *
     * @return
     */
    List<Combo> obtenerComboRegimenPorIdObligacion();
     /**
     *
     * @return
     */
    List<Combo> obtenerComboEjercicioFiscal();
     /**
     *
     * @return
     */
    List<Combo> obtenerComboPeriodicidad();
     /**
     *
     * @param fundamento
     * @return
     * @throws SGTServiceException
     */
    FundamentoLegal buscarFundamentoLegalParaExportacion(FundamentoLegal fundamento)throws SGTServiceException;
     /**
      * 
      * @param rutaEnRepositorio
      * @param nombreOriginalArchivo
      * @return
      * @throws SeguimientoDAOException 
      */
    Map<String,String> guardaFundamentoLegalBatch(String rutaEnRepositorio,String nombreOriginalArchivo) throws SeguimientoDAOException;

    /**
     * 
     * @return 
     */
    List<CatalogoBase> getFundamentosExistentes();

    List<FundamentoLegal> todosLosFundamentosPorEjercicioFiscal(Long idEjercicioFiscalFun);

}
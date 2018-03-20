package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface FundamentoLegalDAO {
     /**
     *
     * @return
     */
    List<FundamentoLegal> todosLosFundamentos();
    /**
     *
     * @return
     */
    List<FundamentoLegal> todosLosFundamentosPorEjercicioFiscal(Long idEjercicio);
     /**
     *
     * @param fundamento
     * @throws SeguimientoDAOException
     */
    void agregarFundamentoLegal(FundamentoLegal fundamento) throws SeguimientoDAOException;
     /**
     *
     * @param fundamento
     * @throws SeguimientoDAOException
     */
    void editaFundamentoLegal(FundamentoLegal fundamento) throws SeguimientoDAOException;
     /**
     *
     * @param fundamento
     * @return
     * @throws SeguimientoDAOException
     */
    Integer eliminaFundamentoLegal(FundamentoLegal fundamento) throws SeguimientoDAOException;
     /**
     *
     * @param fundamento
     * @return
     */
    FundamentoLegal buscaFundamentoLegalPorID(FundamentoLegal fundamento);
     /**
     *
     * @param fundamento
     * @return
     */
    Integer buscarFundamentoLegalPorIdObligYIdReg(FundamentoLegal fundamento);
     /**
     *
     * @return
     */
    List<Combo> obtenerComboObligacion();
     /**
     *
     * @return
     * @throws SeguimientoDAOException
     */
    List<Combo> obtenerListRegimenPorIdObligacion() throws SeguimientoDAOException;
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
     */
    FundamentoLegal buscarFundamentoLegalParaExportacion(FundamentoLegal fundamento);
     /**
     *
     * @param detalles
     * @return
     * @throws SeguimientoDAOException
     */
    Map<String,String> guardaFundamentoLegalBatch(final List<FundamentoLegal> detalles) throws SeguimientoDAOException;
    
    /**
     * 
     * @return 
     */
    List<CatalogoBase> getFundamentosExistentes();
}
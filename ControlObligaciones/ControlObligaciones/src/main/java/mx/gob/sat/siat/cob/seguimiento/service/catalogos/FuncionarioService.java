package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface FuncionarioService {

    /**
     *
     * @return
     */
    List<Funcionario> todosLosFuncionarios();

    /**
     *
     * @param funcionario
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void agregaFuncionario(Funcionario funcionario, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    /**
     *
     * @param funcionario
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void editaFuncionario(Funcionario funcionario, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    /**
     *
     * @param funcionario
     * @throws SeguimientoDAOException
     */
    void reactivaFuncionario(Funcionario funcionario) throws SeguimientoDAOException;

    /**
     *
     * @param funcionario
     * @param segMovDto
     * @throws SeguimientoDAOException
     * @throws DaoException
     */
    void eliminaFuncionario(Funcionario funcionario, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;

    /**
     *
     * @param funcionario
     * @return
     */
    Integer buscaFuncionarioPorIdYNom(Funcionario funcionario);

    /**
     *
     * @param list
     * @return
     */
    ByteArrayOutputStream generaExcel(List<Funcionario> list);

    /**
     *
     * @param list
     * @return
     */
    ByteArrayOutputStream generaPDF(List<Funcionario> list);

    /**
     *
     * @return
     */
    List<Combo> obtenerComboArea();

    /**
     *
     * @throws SGTServiceException
     */
    void guardaNivelEmision() throws SGTServiceException;

    /**
     *
     * @throws SGTServiceException
     */
    void eliminaNivelEmision() throws SGTServiceException;
}

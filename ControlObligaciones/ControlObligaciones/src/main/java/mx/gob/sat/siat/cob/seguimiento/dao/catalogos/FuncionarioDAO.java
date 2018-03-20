package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface FuncionarioDAO {

    List<Funcionario> todosLosFuncionarios();

    void agregaFuncionario(Funcionario funcionario) throws SeguimientoDAOException;

    void editaFuncionario(Funcionario funcionario) throws SeguimientoDAOException;

    void reactivaFuncionario(Funcionario funcionario) throws SeguimientoDAOException;

    Integer eliminaFuncionario(Funcionario funcionario) throws SeguimientoDAOException;

    Funcionario buscaFuncionarioPorID(Funcionario funcionario);

    Integer buscarFuncionarioPorIdYNom(Funcionario funcionario);

    List<Combo> obtenerComboArea();

    /**
     *
     * @throws SeguimientoDAOException
     */
    void guardaNivelEmision() throws SeguimientoDAOException;

    /**
     *
     * @throws SeguimientoDAOException
     */
    void eliminaNivelEmision() throws SeguimientoDAOException;
}

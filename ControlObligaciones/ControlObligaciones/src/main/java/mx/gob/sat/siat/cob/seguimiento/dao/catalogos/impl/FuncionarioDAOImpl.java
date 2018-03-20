package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.sql.Types;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FuncionarioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.FuncionarioAprobarMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.FuncionarioMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.FuncionarioSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FuncionarioDAOImpl implements FuncionarioDAO {

    private static Logger logger = Logger.getLogger(FuncionarioDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     *
     * @return
     */
    @Override
    public List<Funcionario> todosLosFuncionarios() {
        List<Funcionario> listFuncionario;
        listFuncionario = template.query(FuncionarioSQL.OBTEN_TODOS_FUNCIONARIOS, new FuncionarioMapper());
        if (listFuncionario == null || listFuncionario.isEmpty()) {
            logger.info(FuncionarioSQL.OBTEN_TODOS_FUNCIONARIOS);
        }
        return listFuncionario;
    }

    /**
     *
     * @param funcionario
     *
     */
    @Override
    @Propagable
    public void agregaFuncionario(Funcionario funcionario) {
        int resultado = template.update(FuncionarioSQL.AGREGA_FUNCIONARIO, funcionario.getNumeroEmpleado(),
                funcionario.getNombreFuncionario(), funcionario.getCorreoElectronicoFuncionario(),
                funcionario.getCorreoElectronicoAlterno(), funcionario.getFechaInicio(),
                funcionario.getFechaFin(), funcionario.getAreaDeAdscripcion(), funcionario.getDescripcionCargo());
        if (resultado == -1) {
            logger.info(FuncionarioSQL.AGREGA_FUNCIONARIO);
        }
    }

    /**
     *
     * @param funcionario
     *
     */
    @Override
    @Propagable
    public void editaFuncionario(Funcionario funcionario) {
        int resultado = template.update(FuncionarioSQL.EDITA_FUNCIONARIO, funcionario.getNombreFuncionario(), funcionario.getCorreoElectronicoFuncionario(),
                funcionario.getCorreoElectronicoAlterno(), funcionario.getAreaDeAdscripcion(), funcionario.getDescripcionCargo(), agregaCeros(funcionario.getNumeroEmpleado().toString()));
        if (resultado == -1) {
            logger.info(FuncionarioSQL.EDITA_FUNCIONARIO);
        }

    }

    @Override
    @Propagable
    public void reactivaFuncionario(Funcionario funcionario) {

        int resultado = template.update(FuncionarioSQL.REACTIVA_FUNCIONARIO,
                agregaCeros(funcionario.getNumeroEmpleado().toString()));
        if (resultado == -1) {
            logger.info(FuncionarioSQL.REACTIVA_FUNCIONARIO);
        }
    }

    /**
     *
     * @param funcionario
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaFuncionario(Funcionario funcionario) {
        Integer reg;
        reg = template.update(FuncionarioSQL.ELIMINA_FUNCIONARIO, funcionario.getFechaFin(),
                agregaCeros(funcionario.getNumeroEmpleado().toString()));
        if (reg == -1) {
            logger.info(FuncionarioSQL.ELIMINA_FUNCIONARIO);
        }
        return reg;
    }

    /**
     *
     * @param funcionario
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Funcionario buscaFuncionarioPorID(Funcionario funcionario) {
        try {
            return template.queryForObject(FuncionarioSQL.BUSCA_FUNCIONARIO_POR_ID,
                    new Object[]{funcionario.getNumeroEmpleado()},
                    new int[]{Types.VARCHAR},
                    new FuncionarioAprobarMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + FuncionarioSQL.BUSCA_FUNCIONARIO_POR_ID);
            return null;
        }
    }

    /**
     *
     * @param funcionario
     * @return
     */
    @Override
    public Integer buscarFuncionarioPorIdYNom(Funcionario funcionario) {
        Integer reg = null;

        SqlRowSet srs = template.queryForRowSet(FuncionarioSQL.BUSCA_FUNCIONARIO_POR_IDYNOM,
                agregaCeros(funcionario.getNumeroEmpleado().toString()));

        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }

        return reg;
    }

    private String agregaCeros(String cadena) {
        StringBuilder cadenaReturn = new StringBuilder();
        for (int i = 11; i > cadena.toCharArray().length; i--) {
            cadenaReturn.append("0");
        }
        cadenaReturn.append(cadena);
        return cadenaReturn.toString();
    }

    @Override
    public List<Combo> obtenerComboArea() {
        List<Combo> combo;
        combo = template.query(FuncionarioSQL.LISTA_COMBO_AREA, new ComboMapper());
        return combo;
    }

    @Override
    @Propagable
    public void guardaNivelEmision() {
        template.update(FuncionarioSQL.INSERT_ADMTVONIVLCGO);
        template.update(FuncionarioSQL.INSERT_FUNCIONARIO);
    }

    @Override
    @Propagable
    public void eliminaNivelEmision() throws SeguimientoDAOException {
        template.execute(FuncionarioSQL.DELETE_ADMTVONIVLCGO);
        template.execute(FuncionarioSQL.DELETE_FUNCIONARIO);
    }
}

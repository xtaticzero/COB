package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.CatEmpleadosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.compartidos.mapper.CatalogoDescripcionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.impl.mapper.RegionAlrMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AdministrativoNivelCargoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.AdministracionFuncionariosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.AdministrativoNivelCargoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.FuncionariosResponsablesMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AdministrativoNivelCargoSQL.BUSCAR_POR_EVENTO_CARGA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AdministrativoNivelCargoSQL.ELIMINAR_FUNCIONARIO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FuncionarioSat;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Transactional
@Repository
public class AdministrativoNivelCargoDAOImpl implements AdministrativoNivelCargoDAO {

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    private final Logger logger = Logger.getLogger(AdministrativoNivelCargoDAOImpl.class);

    /**
     *
     * @param numeroEmpleado
     * @param evento
     * @return
     */
    @Override
    @Propagable(catched = true)
    public AdministrativoNivelCargo buscarPorNumeroEmpleado(String numeroEmpleado, EventoCargaEnum evento) {
        List<AdministrativoNivelCargo> lista = template.query(BUSCAR_POR_NUM_EMPLEADO, new Object[]{numeroEmpleado, evento.getIdEvento()},
                new int[]{Types.VARCHAR, Types.NUMERIC},
                new AdministrativoNivelCargoMapper());
        if (lista != null && lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * @param idVigilancia
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<AdministrativoNivelCargo> getEmpleadosCentrales(int idVigilancia) {
        return this.template.query(EMPLEADOS_CENTRALES, new Object[]{idVigilancia}, new int[]{Types.INTEGER},
                new AdministrativoNivelCargoMapper());
    }

    /**
     *
     * @param idVigilancia
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<AdministrativoNivelCargo> getEmpleadosLocales(int idVigilancia) {
        List<AdministrativoNivelCargo> lista = null;
        lista =
                this.template.query(EMPLEADOS_LOCALES, new Object[]{idVigilancia, idVigilancia}, new int[]{Types.INTEGER,
            Types.INTEGER},
                new AdministrativoNivelCargoMapper());
        return lista;
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<FuncionarioSat> buscarFuncionarios() {
        StringBuilder sql = new StringBuilder();
        sql.append(CONSULTA_EMPLEADOS);
        return template.query(sql.toString(), new AdministracionFuncionariosMapper());
    }

    /**
     *
     * @param numeroEmpleado
     */
    @Override
    @Propagable
    public void eliminarRegistroFuncionario(String numeroEmpleado, Integer eventoCarga) {
        template.update(ELIMINAR_FUNCIONARIO.replace("{0}", numeroEmpleado).replace("{1}", eventoCarga.toString()));
    }

    /**
     *
     * @param fn
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer guardarFuncionario(FuncionarioSat fn) {
        StringBuilder sql = new StringBuilder();
        sql.append(INSERTAR_FUNCIONARIO.replace("{0}", fn.getIdCargoAdministrativo().toString()).replace("{1}",
                fn.getNumeroEmpleado()).replace("{2}",
                fn.getIdRegionAlr()
                != null
                && !fn.getIdRegionAlr().equals("")
                ? fn.getIdRegionAlr()
                : "").replace("{3}",
                fn.getNivelEmision().toString()).
                // .replace("{4}", (fn.isConCopia()==true?1:0)+"")
                replace("{4}", "sysdate").replace("{5}", "null").replace("{6}", fn.getIdEventoCarga().toString()));
        return template.update(sql.toString());
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<CatalogoBase> buscarCargosAdministrativos() {
        StringBuilder sql = new StringBuilder();
        sql.append(BUSCAR_CARGOS_ADMINISTRATIVOS);
        return template.query(sql.toString(), new CatalogoDescripcionMapper());
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<CatalogoBase> buscarNivelesEmision() {
        StringBuilder sql = new StringBuilder();
        sql.append(BUSCAR_NIVELES_EMISION);
        return template.query(sql.toString(), new CatalogoDescripcionMapper());
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<CatalogoBase> buscarEventoCarga() {
        StringBuilder sql = new StringBuilder();
        sql.append(BUSCAR_EVENTO_CARGA);
        return template.query(sql.toString(), new CatalogoDescripcionMapper());
    }

    /**
     *
     * @param funcionarioSat
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer actualizarRegistroFuncionario(FuncionarioSat funcionarioSat) {
        return template.update(ACTUALIZAR_FUNCIONARIO,
                new Object[]{
            funcionarioSat.getIdCargoAdministrativo(),
            funcionarioSat.getNivelEmision(),
            funcionarioSat.getIdRegionAlr() != null
            ? !funcionarioSat.getIdRegionAlr().equals("") ? funcionarioSat.getIdRegionAlr() : "" : "",
            funcionarioSat.getNumeroEmpleado(),
            funcionarioSat.getIdEventoCarga()},
                new int[]{Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.INTEGER});
    }

    /**
     *
     * @return
     */
    @Override
    public List<ComboStr> obtenerEmpleadosAdministrativo() {

        StringBuilder sql = new StringBuilder();
        sql.append(OBTENER_EMPLEADOS_ADMTVO);
        return template.query(sql.toString(), new CatEmpleadosMapper());

    }

    /**
     *
     * @param numeroEmpleado
     */
    @Override
    @Propagable
    public Integer habilitarRegistroFuncionario(String numeroEmpleado, Integer eventoCarga) {
        return template.update(HABILITAR_FUNCIONARIO, new Object[]{numeroEmpleado, eventoCarga}, new int[]{Types.INTEGER, Types.NUMERIC});
    }

    /**
     *
     * @param idNivelEmision
     * @return
     */
    @Override
    @Propagable
    public List<CatalogoBase> buscarCargoPorNivelEmision(Integer idNivelEmision) {

        return template.query(BUSCAR_CARGO_POR_NIVEL_EMISION, new Object[]{idNivelEmision},
                new int[]{Types.INTEGER}, new CatalogoDescripcionMapper());
    }

    /**
     *
     * @param evento
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<AdministrativoNivelCargo> buscarPorEventoCarga(EventoCargaEnum evento) {
        return template.query(BUSCAR_POR_EVENTO_CARGA, new Object[]{evento.getIdEvento()},
                new int[]{Types.NUMERIC}, new AdministrativoNivelCargoMapper());
    }
    
    /**
     *
     * @param eventoYEmision
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<AdministrativoNivelCargo> buscarPorEventoCargaYNivelEmision(EventoCargaEnum evento) {
        return template.query(BUSCAR_POR_EVENTO_CARGA_EMISION, new Object[]{evento.getIdEvento()},
                new int[]{Types.NUMERIC}, new AdministrativoNivelCargoMapper());
    }
    

    /**
     *
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<ComboStr> buscarRegionALR() {
        StringBuilder sql = new StringBuilder();
        sql.append(BUSCAR_REGION_ALR);
        logger.debug(sql.toString());
        return template.query(sql.toString(), new RegionAlrMapper());
    }

    @Override
    public List<FuncionarioSat> buscarEmpleadosExistentes(FuncionarioSat fn) {
        try {

            if (fn.getIdRegionAlr() != null) {

                return template.query(BUSCAR_FUNCIONARIOS_EXISTENTES,
                        new Object[]{fn.getNivelEmision(), fn.getIdCargoAdministrativo(),
                    fn.getIdRegionAlr()},
                        new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR},
                        new FuncionariosResponsablesMapper());
            } else {
                return template.query(BUSCAR_FUNCIONARIOS_EXISTENTES_NULL,
                        new Object[]{fn.getNivelEmision(), fn.getIdCargoAdministrativo()},
                        new int[]{Types.NUMERIC, Types.NUMERIC},
                        new FuncionariosResponsablesMapper());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<FuncionarioSat> buscarEmpleadosNumEvento(FuncionarioSat fn) {
        try {

            return template.query(BUSCAR_FUNCIONARIOS_NUMEVENTO,
                    new Object[]{fn.getNumeroEmpleado(), fn.getIdEventoCarga(), fn.getNivelEmision()},
                    new int[]{Types.VARCHAR, Types.NUMERIC, Types.NUMERIC},
                    new FuncionariosResponsablesMapper());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void bajaEmpleadosExistentes(FuncionarioSat fn) {
        try {
            if (fn.getIdRegionAlr() != null) {
                template.update(BAJA_EMPLEADOS_EXISTENTES,
                        new Object[]{fn.getNivelEmision(), fn.getIdCargoAdministrativo(),
                    fn.getIdRegionAlr(), fn.getNumeroEmpleado()},
                        new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
            } else {
                template.update(BAJA_EMPLEADOS_EXISTENTES_NULL,
                        new Object[]{fn.getNivelEmision(), fn.getIdCargoAdministrativo(),
                    fn.getNumeroEmpleado()},
                        new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR});
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

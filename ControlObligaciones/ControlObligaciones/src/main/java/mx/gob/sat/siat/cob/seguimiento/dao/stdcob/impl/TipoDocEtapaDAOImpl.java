package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.TipoDocEtapaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultaTipoDocMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.SQLOracleSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocEtapa;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TipoDocEtapaDAOImpl implements TipoDocEtapaDAO, SQLOracleSeguimiento {

    private Logger logger = Logger.getLogger(TipoDocEtapaDAOImpl.class);

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * Consulta de la tabla SGTA_TIPODOCETAPA por idTipoDocumento
     *
     * @author Daniel Morales
     */
    @Override
    @Propagable
    public List<TipoDocEtapa> consultarTipoDocEtapa(String id) {
        return template.query(CONSULTAR_TIPODOCETAPA_BY_IDTIPODOC.replace("{0}", (id)), new ConsultaTipoDocMapper());
    }

    @Override
    @Propagable
    public Integer buscarEstadoSeguimientoActualPorRfc(String rfc) {
        return template.queryForObject(CONSULTAR_ESTADO_SEGUIMIENTO_ACTUAL_POR_RFC.replace("{0}", rfc), Integer.class);
    }

    @Override
    @Propagable
    public void actualizarRegistroEjecucionSeguimiento(Integer enEjecucion, String rfc) {
        StringBuilder sqlUpdate = new StringBuilder();
        sqlUpdate.append(ACTUALIZAR_REGISTRO_EJECUCION_SEGUIMIENTO.replace("{0}", (enEjecucion + "")).replace("{1}", rfc));
        logger.debug(sqlUpdate.toString());
        template.update(sqlUpdate.toString());
    }

    @Override
    @Propagable
    public void actualizarParametrosPorTipoDocumento(TipoDocEtapa tde) {
        StringBuilder sql = new StringBuilder();
        sql.append(ACTUALIZAR_PARAMETROS_POR_TIPO_DOCUMENTO.replace("{0}",
                tde.getDiasVencimiento() + "").
                replace("{1}", tde.getId() + "").
                replace("{2}", tde.getIdEtapaVigilancia() + ""));
        logger.debug(sql);
        template.update(sql.toString());
    }

    @Override
    @Propagable
    public List<ParametrosSeguimiento> buscarParametrosVigentes() {
        return template.query(BUSCAR_PARAMETROS_VIGENTES, new ParametrosSeguimientoMapper());
    }

    /**
     * EAGE Emmanuel Estrada Gonzalez Consulta para Obtener el numero de dias
     * para un tipo de Documento
     *
     * @param tipoDoc
     * @param etapaVijilancia
     * @return Integer numeroDias
     */
    @Override
    @Propagable
    public Integer getDiasDeVencimineto(Integer tipoDoc, Integer etapaVijilancia) {
        List<TipoDocEtapa> listaTipoDocEtapa;
        String consultaSQL;

        consultaSQL = OBTEN_DIAS_VENCIMIENTO;
        consultaSQL = consultaSQL.replace("{0}", tipoDoc.toString());
        consultaSQL = consultaSQL.replace("{1}", etapaVijilancia.toString());
        listaTipoDocEtapa = template.query(consultaSQL, new ConsultaTipoDocMapper());

        if (listaTipoDocEtapa != null && listaTipoDocEtapa.size() > 0) {
            return ((listaTipoDocEtapa.get(0)).getDiasVencimiento());
        }

        return null;
    }

    private static class ParametrosSeguimientoMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            ParametrosSeguimiento param = new ParametrosSeguimiento();
            param.setTipoDocumento(resultSet.getString("tipoDocumento"));
            param.setEtapa(resultSet.getString("etapa"));
            param.setDiasVencimiento(resultSet.getString("diasVencimiento"));
            return param;
        }
    }
}

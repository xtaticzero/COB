package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAdministracionLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciaAdministracionLocalBatchMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.ACTUALIZAR_FECHA_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.ACTUALIZAR_ID_SITUACION_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.ACTUALIZA_ESTADOEJECUCION_PROCESO_ARCHIVOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.CUENTA_REGISTRO_X_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.ID_VIGILANCIA_ENVIA_ARCHIVOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.INSERT_VIGILANCIA_ADMIN_LOCAL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.LOCAL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.OBTENER_VIGILANCIAS_CON_DOCUMENTOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.OBTENER_VIGILANCIA_A_PROCESAR;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.OBTENER_VIGILANCIA_MULTA_A_PROCESAR;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.UPDATE_FECHA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.OBTENER_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAdministracionLocalSQL.UPDATE_FECHA_ULTIMO_ENVIO_RESOL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import static mx.gob.sat.siat.cob.seguimiento.util.Utilerias.setFechaTrunk;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rodrigo
 */
@Repository
public class VigilanciaAdministracionLocalDAOImpl implements
        VigilanciaAdministracionLocalDAO, VigilanciaAdministracionLocalSQL {

    private final Logger log = Logger.getLogger(VigilanciaAdministracionLocalDAOImpl.class);
    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @param idVigilancia
     * @return
     */
    @Override
    @Propagable
    public int guardaVigAdminLocal(int idVigilancia) {
        log.debug("guardaVigAdminLocal");
        int resultado = template.update(INSERT_VIGILANCIA_ADMIN_LOCAL, new Object[]{
            idVigilancia
        }, new int[]{
            Types.DECIMAL
        });
        log.debug("guardaVigAdminLocal la vigilancia " + idVigilancia);
        log.debug(INSERT_VIGILANCIA_ADMIN_LOCAL);
        return resultado;
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarVigilancias(VigilanciaAdministracionLocal vigilanciaAdministracionLocal) {
        if (vigilanciaAdministracionLocal.getIdAdministracionLocal() == null) {
            return template.update(ACTUALIZAR_POR_VIGILANCIA, new Object[]{
                vigilanciaAdministracionLocal.getMotivoRechazoVigilancia().getIdMotivoRechazoVig(),
                vigilanciaAdministracionLocal.getSituacionVigilanciaEnum().getIdSituacion(),
                vigilanciaAdministracionLocal.getNumeroEmpleado(),
                vigilanciaAdministracionLocal.getNumeroCarga()},
                    new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.NUMERIC});
        } else {
            return template.update(ACTUALIZAR_POR_ADMINISTRACION, new Object[]{
                vigilanciaAdministracionLocal.getMotivoRechazoVigilancia().getIdMotivoRechazoVig(),
                vigilanciaAdministracionLocal.getSituacionVigilanciaEnum().getIdSituacion(),
                vigilanciaAdministracionLocal.getNumeroEmpleado(),
                vigilanciaAdministracionLocal.getIdAdministracionLocal(),
                vigilanciaAdministracionLocal.getNumeroCarga()},
                    new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC});
        }
    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciaAdministracionLocal> obtenerAdministracionLocalXIdVigilanica(Long idVigilancia) {
        return this.template.query(OBTENER_VIGILANCIA_A_PROCESAR, new Object[]{idVigilancia}, new int[]{Types.NUMERIC},
                new VigilanciaAdministracionLocalBatchMapper());
    }

    @Override
    @Propagable(catched = true)
    public Integer cuentaRegistrosPorVigilancia(VigilanciaAdministracionLocal vigilancia, String joinNivelEmision) {
        return this.template.queryForObject(CUENTA_REGISTRO_X_VIGILANCIA + joinNivelEmision,
                new Object[]{vigilancia.getIdVigilancia(), vigilancia.getIdAdministracionLocal()},
                new int[]{
            Types.NUMERIC, Types.NUMERIC},
                Integer.class);
    }

    @Override
    @Propagable
    public void actualizaIdSituacionVigilancia(VigilanciaAdministracionLocal vigilanciaAdministracionLocal,
            int idSituacionVigilancia) throws SeguimientoDAOException {
        template.update(ACTUALIZAR_ID_SITUACION_VIGILANCIA,
                new Object[]{idSituacionVigilancia,
            vigilanciaAdministracionLocal.getIdVigilancia(),
            vigilanciaAdministracionLocal.getIdAdministracionLocal()});
    }

    @Propagable(catched = true)
    @Override
    public List<VigilanciaAdministracionLocal> obtenerIdAdministracionLocal(List<String> numerosControl) {
        String numeros = Utilerias.formatearParaSQLIn(numerosControl.toString());
        List<VigilanciaAdministracionLocal> administracionLocals = this.template.query(OBTENER_VIGILANCIA_ADMIN_LOCAL_UPDATE.replace("#", numeros),
                new VigilanciaAdministracionLocalBatchMapper());

        return administracionLocals;
    }

    @Propagable(catched = true)
    @Override
    public Integer updateSituacionVigilancia(List<VigilanciaAdministracionLocal> vigilanciasAdministracionLocal, SituacionVigilanciaEnum situacionEnum) {
        List<String> idsAdmonLocal = new ArrayList<String>();
        for (VigilanciaAdministracionLocal vigilancia : vigilanciasAdministracionLocal) {
            log.info("### La vigilancia : " + vigilancia.getIdVigilancia() + " con ID_VAL : "
                    + vigilancia.getIdAdministracionLocal()
                    + " se actualiza a situacion " + situacionEnum.getIdSituacion());
            if (!idsAdmonLocal.contains(vigilancia.getIdAdministracionLocal())) {
                idsAdmonLocal.add(vigilancia.getIdAdministracionLocal());
            }
        }
        String idsAdmonLocales = Utilerias.formatearParaSQLIn(idsAdmonLocal.toString());
        return template.update(ACTUALIZA_ESTADOEJECUCION_PROCESO_ARCHIVOS.replace("#", idsAdmonLocales),
                new Object[]{situacionEnum.getIdSituacion(), new Date(), vigilanciasAdministracionLocal.get(0).getIdVigilancia()
        });
    }

    @Override
    @Propagable(catched = true)
    public Integer updateFechaValidacionCumplimiento(String numeroCarga, String idAdministracionLocal) {
        if (idAdministracionLocal == null) {
            return template.update(UPDATE_FECHA,
                    new Object[]{setFechaTrunk(new Date()), numeroCarga},
                    new int[]{Types.DATE, Types.NUMERIC});
        } else {
            return template.update(UPDATE_FECHA + LOCAL,
                    new Object[]{setFechaTrunk(new Date()), numeroCarga, idAdministracionLocal},
                    new int[]{Types.DATE, Types.NUMERIC, Types.VARCHAR});
        }
    }

    @Override
    @Propagable
    public List<VigilanciaAdministracionLocal> obtenerVigilanciasConDocumentos(VigilanciaAdministracionLocal vigilanciaAdminLocal) {
        return template.query(OBTENER_VIGILANCIAS_CON_DOCUMENTOS,
                new Object[]{vigilanciaAdminLocal.getIdVigilancia(), vigilanciaAdminLocal.getIdAdministracionLocal()},
                new VigilanciaAdministracionLocalBatchMapper());
    }

    @Override
    @Propagable(catched = true)
    public VigilanciaAdministracionLocal obtenerVigilanciaMultaBatch() {
        try {
            return this.template.queryForObject(OBTENER_VIGILANCIA_MULTA_A_PROCESAR, new VigilanciaAdministracionLocalBatchMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug("No hay resultados: \n  " + OBTENER_VIGILANCIA_MULTA_A_PROCESAR);
            return null;
        }
    }

    @Override
    @Propagable
    public void actualizaFechaVigilancia(Long idVigilancia, String idAdministracionLocal) {
        template.update(ACTUALIZAR_FECHA_VIGILANCIA,
                new Object[]{idVigilancia,
            idAdministracionLocal});
    }

    @Override
    @Propagable(catched = true)
    public VigilanciaAdministracionLocal obtenerVigilancia(Long idVigilancia, String idAdmonLocal) {
        try {
            return template.queryForObject(OBTENER_VIGILANCIA,
                    new Object[]{idVigilancia, idAdmonLocal},
                    new VigilanciaAdministracionLocalBatchMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Propagable(catched = true)
    public Long obtenetNumeroVigilancia() {
        try {
            return (Long) template.queryForObject(ID_VIGILANCIA_ENVIA_ARCHIVOS, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VigilanciaAdministracionLocal> obtenerAdministracionLocalMultaXIdVigilanica(Long idVigilancia) throws SeguimientoDAOException {
        return this.template.query(VIGILANCIA_CREDITOS, new Object[]{idVigilancia}, new int[]{Types.NUMERIC},
                new VigilanciaAdministracionLocalBatchMapper());
    }

    @Override
    @Propagable(catched = true)
    public Integer guardaVigAdminLocal(Long idVigilancia, String idAdmonLocal) throws SeguimientoDAOException {
        return template.update(INSERT_VIGILANCIA_ADMIN_LOCAL_VIGILANCIA, new Object[]{
            idVigilancia,
            idAdmonLocal
        }, new int[]{
            Types.DECIMAL,
            Types.VARCHAR
        });
    }

    @Override
    public void actualizarFechaUltimoEnvioResol(Long idVigilancia, String idAdmonLocal) throws SeguimientoDAOException {
        template.update(UPDATE_FECHA_ULTIMO_ENVIO_RESOL,
                new Object[]{idVigilancia, idAdmonLocal});
    }

    @Override
    public void actualizarFechaEnvioArca(Long idVigilancia, String idAdmonLocal) throws SeguimientoDAOException {
        template.update(UPDATE_FECHA_ENVIO_ARCA,
                new Object[]{idVigilancia, idAdmonLocal});
    }
}

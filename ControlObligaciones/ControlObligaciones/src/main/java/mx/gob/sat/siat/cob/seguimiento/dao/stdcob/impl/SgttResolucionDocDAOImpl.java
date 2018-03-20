package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgttResolucionDocDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.UbicacionResolMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.SgttResolucionDocSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionResolDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author christian.ventura
 */
@Repository
public class SgttResolucionDocDAOImpl implements SgttResolucionDocDAO {

    private final Logger log = Logger.getLogger(SgttResolucionDocDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @return
     */
    @Override
    @Propagable(catched = false)
    public Integer actualizarAdmonLocal(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum,
            AdministrativoNivelCargo administrativoNivelCargo) {
        log.debug("actualizarAdmonLocal");
        Integer retorno = 1;
        try {
            final List<UbicacionResolDTO> listaDatos = template.query(
                    SgttResolucionDocSQL.OBTENER_DATOS,
                    new Object[]{
                estadoMultaEnum.getValor(),
                multaAprobarGrupo.getFechaEmision(),
                administrativoNivelCargo.getNivelEmision().getIdNivelEmision(),
                administrativoNivelCargo.getIdCargoAdministrativo(),
                multaAprobarGrupo.getIdMedioEnvio(),
                multaAprobarGrupo.getIdTipoFirma(),
                multaAprobarGrupo.getIdTipoMulta(),
                administrativoNivelCargo.getIdAdministacionLocal()
            },
                    new int[]{
                Types.INTEGER,
                Types.DATE,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR
            },
                    new UbicacionResolMapper());
            log.debug("-----listaDatos.size" + listaDatos.size());
            template.batchUpdate(SgttResolucionDocSQL.UPDATE_RESOL, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    UbicacionResolDTO dato = listaDatos.get(i);
                    ps.setString(1, dato.getClvALR());
                    ps.setString(2, dato.getClvCRH());
                    ps.setString(3, dato.getNumeroControl());
                }

                @Override
                public int getBatchSize() {
                    return listaDatos.size();
                }
            });
        } catch (DataAccessException ex) {
            log.error(ex.getCause().getMessage());
            retorno = null;
        }
        log.debug("termina actualizarAdmonLocal:" + retorno);
        return retorno;
    }

    @Propagable
    @Override
    public Integer actualizarEstadoResolucion(Integer ultimoEstado, Long idVigilancia, String idAlsc, String fechaMonitor)
            throws ARCADAOExcepcion {
        return template.update(SgttResolucionDocSQL.ACTUALIZA_ESTADO_RESOLUCION, new Object[]{
            ultimoEstado,
            idVigilancia,
            idAlsc, fechaMonitor},
                new int[]{
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR,
            Types.VARCHAR
        });
    }
}

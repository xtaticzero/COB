package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocEtapa;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper de la tabla SGTA_TIPODOCETAPA
 * @author Daniel Morales
 */
public class ConsultaTipoDocMapper implements RowMapper{

            /**
     *
     * @param resultSet
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
            public TipoDocEtapa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                TipoDocEtapa t = new TipoDocEtapa();
                t.setId(resultSet.getInt("IDTIPODOCUMENTO"));
                t.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));
                t.setDiasVencimiento(resultSet.getInt("DIASVENCIMIENTO"));
                t.setNombreDocumentoEtapa(resultSet.getString("NOMBREDOCUMENTOETAPA"));
                return t;
            }
        }
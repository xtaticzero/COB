package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CicloDocEtapaDao;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository("daoCicloDocEtapa")
public class CicloDocEtapaDaoImpl implements CicloDocEtapaDao {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @param idEstadoOrigen
     * @param idTipoDocumento
     * @param idEtapaVigilancia
     * @param idEstadoDestino
     * @return
     */
    @Override
    public List<Integer> consultaEstados(EstadoDocumentoEnum idEstadoOrigen, TipoDocumentoEnum idTipoDocumento,
            EtapaVigilanciaEnum idEtapaVigilancia, EstadoDocumentoEnum idEstadoDestino) {
        String sql = "Select distinct  IdEstadoOrigen  as id from SGTA_CicloDocEtapa where (FechaFin is null "
                + "and IdEstadoDestino = " + idEstadoDestino.getValor() + " "
                + " and IdTipoDocumento = " + idTipoDocumento.getValor() + "and IdEtapaVigilancia = " + idEtapaVigilancia.getValor() + ")";
        return this.template.query(sql, new IdEstadosOrigenMapper());
    }

    /**
     * 
     */
    private static class IdEstadosOrigenMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            return (resultSet.getInt("id"));
        }
    }
}

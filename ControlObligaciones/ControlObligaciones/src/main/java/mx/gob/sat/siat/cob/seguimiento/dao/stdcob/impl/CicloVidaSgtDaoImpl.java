package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CicloVidaSgtDao;
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
@Repository("daoCicloVidaSgt")
public class CicloVidaSgtDaoImpl implements CicloVidaSgtDao {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @param idEtapaActual
     * @param idTipoDocumento
     * @param idEtapaRequerida
     * @return
     */
    @Override
    public List<Integer> consultaEtapa(Integer idEtapaActual, Integer idTipoDocumento,
            Integer idEtapaRequerida) {
        String sql = "Select distinct  IdEtapaActual  as id from SGTA_Ciclovidasgt where (FechaFin is null "
                + "and IdTipoDocumento = " + idTipoDocumento + " "
                + "and IdEtapaRequerida = " + idEtapaRequerida + ")";
        return this.template.query(sql, new IdEtapaMapper());
    }

    private static class IdEtapaMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            return (resultSet.getInt("id"));
        }
    }
}

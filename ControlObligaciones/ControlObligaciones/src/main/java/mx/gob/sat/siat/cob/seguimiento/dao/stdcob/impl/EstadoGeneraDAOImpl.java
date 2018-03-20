package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EstadoGeneraDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.EstadoGeneraMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.EstadoGeneraSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoGenera;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoGeneraDAOImpl implements EstadoGeneraDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param estadoGenera
     */
    @Override
    @Propagable
    public void insert(EstadoGenera estadoGenera) {
        String query
                = EstadoGeneraSQL.INSERT_ESTADO_GENERA + estadoGenera.getIdEstadoGeneracion() + ", " + estadoGenera.getNombre()
                + ", " + estadoGenera.getDescripcion() + ", " + estadoGenera.getFechaInicio() + ", "
                + estadoGenera.getFechaFin() + ", " + estadoGenera.getOrden() + ")";
        template.update(query);
    }

    /**
     */
    @Override
    public List<EstadoGenera> consultaEstadoGenera() {
            String query = EstadoGeneraSQL.SELECT_ESTADO_GENERA;
            return this.template.query(query, new EstadoGeneraMapper());
    }
}

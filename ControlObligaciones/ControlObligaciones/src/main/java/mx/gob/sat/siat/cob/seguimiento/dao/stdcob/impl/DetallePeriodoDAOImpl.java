package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetallePeriodoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetallePeriodosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DetallePeriodoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetallePeriodos;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DetallePeriodoDAOImpl implements DetallePeriodoDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param detallePeriodo
     */
    @Override
    @Propagable
    public void insert(DetallePeriodos detallePeriodo) {
        String query =
                DetallePeriodoSQL.INSERT_DETALLE_PERIODO + detallePeriodo.getNumeroControl() + ", " + detallePeriodo.getDescripcionPeriodo()
                + ", " + detallePeriodo.getEjercicio() + ", " + detallePeriodo.getConceptoImpuesto() + ")";
        template.update(query);
    }

    /**
     */
    @Override
    @Propagable
    public List<DetallePeriodos> consultaDetallePeriodos() {
        String query = DetallePeriodoSQL.SELECT_DETALLE_PERIODO;
        return this.template.query(query, new DetallePeriodosMapper());
    }

    /**
     * @param numControl
     * @return
     */
    @Override
    @Propagable
    public List<DetallePeriodos> detallePeriodosXNumControl(String numControl) {
        String query = DetallePeriodoSQL.DETALLE_PERIODO_X_NUM_CONTROL + numControl;
        return this.template.query(query, new DetallePeriodosMapper());
    }
}

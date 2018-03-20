package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleObligaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleObligaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DetalleObligaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObliga;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DetalleObligaDAOImpl implements DetalleObligaDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param detalleObliga
     
     */
    @Override
    @Propagable
    public void insert(DetalleObliga detalleObliga) {
            String query =
                    DetalleObligaSQL.INSERT_DETALLE_OBLIGA + detalleObliga.getNumeroControl() + ", " + detalleObliga.getDescripcionObligacion()
                    + ", " + detalleObliga.getFundamentoLegal() + ", " + detalleObliga.getFechaVencimiento() + ", "
                    + detalleObliga.getDescripcionPeriodo() + ", " + detalleObliga.getEjercicio() + ")";
            template.update(query);
    }

    /**
     * @return
     */
    @Override
    @Propagable
    public List<DetalleObliga> consultaDetalleObliga() {
            String query = DetalleObligaSQL.SELECT_DETALLE_OBLIGA;
            return  this.template.query(query, new DetalleObligaMapper());
    }

    /**
     * @param numControl
     * @return
     
     */
    @Override
    @Propagable
    public List<DetalleObliga> detalleObligaXNumControl(String numControl)  {
            String query = DetalleObligaSQL.DETALLE_OBLIGA_X_NUM_CONTROL + numControl;
            return this.template.query(query, new DetalleObligaMapper());
    }
}

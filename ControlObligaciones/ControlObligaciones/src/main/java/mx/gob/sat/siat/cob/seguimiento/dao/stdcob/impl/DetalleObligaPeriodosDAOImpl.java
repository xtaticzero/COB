package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleObligaPeriodosDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleObligaPeriodosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DetalleObligaPeriodosSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObligaPeriodo;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DetalleObligaPeriodosDAOImpl implements DetalleObligaPeriodosDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable
    public List<DetalleObligaPeriodo> buscaDetalleOblicaPeriodos() {
            String query = DetalleObligaPeriodosSQL.SELECT_OBLIGA_PERIODOS;
            return this.template.query(query, new DetalleObligaPeriodosMapper());
    }
}

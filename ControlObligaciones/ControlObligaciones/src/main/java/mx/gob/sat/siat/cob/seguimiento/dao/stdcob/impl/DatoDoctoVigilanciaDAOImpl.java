package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DatoDoctoVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DatoDoctoVigilanciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DatoDoctoVigilanciasSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDoctoVigilancia;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatoDoctoVigilanciaDAOImpl implements DatoDoctoVigilanciaDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     */
    @Override
    @Propagable
    public List<DatoDoctoVigilancia> consultaDatosDoctosVigilancia()  {
            String query = DatoDoctoVigilanciasSQL.CONSULTA_DATO_DOCTO_X_VIG;
            return this.template.query(query, new DatoDoctoVigilanciaMapper());
    }
}

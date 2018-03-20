package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleConsultaVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleConsultaVigilanciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DetalleConsultaVigilanciaDAOImpl implements DetalleConsultaVigilanciaDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable
    public List<DetalleConsultaVigilancia> consultaDetalleVigilancia(int idVigilancia) {
        String query = "SELECT RUTALOCALARCHIVO AS RUTA, NOMBREARCHIVO"
                + " FROM SGTT_DETALLECARGA WHERE IDVIGILANCIA = " + idVigilancia;
        List<DetalleConsultaVigilancia> listado = this.template.query(query,
                new DetalleConsultaVigilanciaMapper());
        return listado;
    }
}

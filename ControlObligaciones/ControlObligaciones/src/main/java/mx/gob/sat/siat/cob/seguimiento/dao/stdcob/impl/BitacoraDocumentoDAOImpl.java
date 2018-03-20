package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BitacoraDocumentoDAO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BitacoraDocumentoSQL.INSERT_BITACORA_DOCUMENTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraDocumento;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juan
 */
@Repository
public class BitacoraDocumentoDAOImpl implements BitacoraDocumentoDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Propagable(catched = true)
    @Override
    public Integer insertaBitacoraDocumento(BitacoraDocumento bitacora) {
        return template.update(INSERT_BITACORA_DOCUMENTO,
                new Object[]{bitacora.getNumeroControl(),
            bitacora.getIdEstadoDocto()},
                new int[]{Types.VARCHAR, Types.INTEGER});
    }
}

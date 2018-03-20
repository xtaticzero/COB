package mx.gob.sat.siat.cob.seguimiento.dao.psatgj.impl;

import java.sql.Types;
import mx.gob.sat.siat.cob.seguimiento.dao.psatgj.CorreoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.psatgj.sql.CorreoSQL;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class CorreoDAOImpl implements CorreoDAO {

    private Logger log = Logger.getLogger(CorreoDAOImpl.class.getName());

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched=true)
    public String obtenerCorreoPorEmpleado(String idEmpleado) {
        log.debug("obtenerCorreoPorEmpleado:"+idEmpleado);
        String correo = this.template.queryForObject(
                CorreoSQL.OBTENER_CORREO,
                new Object[]{idEmpleado},
                new int[]{Types.VARCHAR}, String.class);
        log.debug("correo:"+correo);
        return correo;
    }

}

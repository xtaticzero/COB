/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CitatorioDAO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CitatorioSQL.INSERT_CITATORIO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.CitatorioSQL.UPDATE_CITATORIO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Citatorio;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author juan
 */
@Repository
public class CitatorioDAOImpl implements CitatorioDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    public Integer insertaCitatorio(Citatorio citatorio) {
        Integer cambio = template.update(UPDATE_CITATORIO,
                new Object[]{citatorio.getFechaCitatorio(),
                    citatorio.getNumeroControl()},
                new int[]{Types.DATE, Types.VARCHAR});
        if (cambio.equals(ConstantsCatalogos.CERO)) {
            cambio = template.update(INSERT_CITATORIO,
                    new Object[]{citatorio.getNumeroControl(),
                        citatorio.getFechaCitatorio()},
                    new int[]{Types.VARCHAR, Types.DATE});
        }
        return cambio;
    }

}

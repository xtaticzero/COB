/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.TipoDeclaracionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.TipoDeclaracionMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.TipoDeclaracionSQL.SELECT_TIPO_DECLARACION;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDeclaracionEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author emmanuel
 */
@Repository
public class TipoDeclaracionDAOImpl implements TipoDeclaracionDAO{
    
    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<Integer> gettipoDeclaracion(TipoDeclaracionEnum tipoDeclaracion) {
        return template.query(SELECT_TIPO_DECLARACION,new Object[]{tipoDeclaracion.getValor()}, new TipoDeclaracionMapper());        
    }
    
}

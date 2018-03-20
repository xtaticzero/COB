/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgtcProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.SgtcProcesoMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.SgtcProcesoSQL.SELECT;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SgtcProcesoDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
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
public class SgtcProcesoDAOImpl implements SgtcProcesoDAO{
    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
        
    @Override
    public List<SgtcProcesoDTO> consultar() throws SeguimientoDAOException {
        return template.query(SELECT, new SgtcProcesoMapper());
    }
    
}

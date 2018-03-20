/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DocumentoPlantillaMapper implements RowMapper<CatalogoBase> {

    @Override
    public CatalogoBase mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogoBase cat = new CatalogoBase();
        cat.setId(resultSet.getInt("id"));
        cat.setNombre(resultSet.getString("fcDescripcionCorta"));
        cat.setIdString(cat.getId() + "" + "-" + cat.getNombre());

        return cat;
    }
}

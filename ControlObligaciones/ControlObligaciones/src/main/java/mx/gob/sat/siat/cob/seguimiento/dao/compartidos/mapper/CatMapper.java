/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.compartidos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.CampoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.CatalogoEnum;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author rodrigo
 */
public class CatMapper implements RowMapper {

    private final CatalogoEnum catalogo;

    public CatMapper(CatalogoEnum catalogo) {
        this.catalogo = catalogo;
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogoBase cb = new CatalogoBase();
        if (catalogo.getCurrentCampoID().equals("IDPERIODICIDAD")) {
            cb.setIdString(resultSet.getString("ID"));
        }else{ 
            if(catalogo.getCurrentTablaID().equals(CampoEnum.CARGOADMN_TABLE.getNombre())){
                cb.setId(resultSet.getInt("ID"));
                cb.setIdAux(resultSet.getString("IDAUX"));
            }else{
                cb.setId(resultSet.getInt("ID"));
            }
        }
        cb.setNombre(resultSet.getString("NOMBRE"));
        return cb;
    }
}

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;

import org.springframework.jdbc.core.RowMapper;

public class PlantillaSingleMapper implements RowMapper<PlantillaDocumento>{
    public PlantillaSingleMapper() {
        super();
    }

    @Override
    public PlantillaDocumento mapRow(ResultSet resultSet, int i) throws SQLException {
        PlantillaDocumento pd = new PlantillaDocumento();
        pd.setRutaArchivo(resultSet.getString("rutaarchivo"));
        pd.setIdPlantilla(Integer.parseInt(resultSet.getString("idplantilla")));
        return pd;
    }
}

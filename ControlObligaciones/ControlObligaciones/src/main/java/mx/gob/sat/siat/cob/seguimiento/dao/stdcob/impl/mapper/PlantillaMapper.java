package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;

import org.springframework.jdbc.core.RowMapper;

public class PlantillaMapper implements RowMapper<PlantillaDocumento>{
    public PlantillaMapper() {
        super();
    }

    @Override
    public PlantillaDocumento mapRow(ResultSet resultSet, int i) throws SQLException {
        PlantillaDocumento pd = new PlantillaDocumento();
        pd.setIdPlantilla(resultSet.getInt("idplantilla"));
        pd.setRutaArchivo(resultSet.getString("rutaarchivo"));
        pd.setDescripcionTipoDoc(resultSet.getString("nombre"));
        pd.setDescripcion(resultSet.getString("descripcion"));
        pd.setDescripcionEtapaVig(resultSet.getString("nombredocumentoetapa"));
        return pd;
    }
}

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraErrores;


import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;

public class BitacoraErroresMapper implements RowMapper<BitacoraErrores>{
    public BitacoraErroresMapper() {
        super();
    }

    @Override
    public BitacoraErrores mapRow(ResultSet resultSet, int i) throws SQLException {
        BitacoraErrores pd = new BitacoraErrores();
        pd.setIdVigilancia(resultSet.getInt("idvigilancia"));
        pd.setDescripcionVigilancia(resultSet.getString("descripcion"));
        pd.setNombreOriginalArchivo(resultSet.getString("nombrearchivo"));
        pd.setRutaBitacoraErrores(resultSet.getString("rutabitacoraerrores"));
        pd.setFechaCargaArchivo(resultSet.getDate("fechacargaarchivos"));
        if(resultSet.getDate("fechacargaarchivos")!=null){
            pd.setFechaCargaArchivoStr(Utilerias.formatearFechaDDMMYYYY(resultSet.getDate("fechacargaarchivos")));
            
        }
        return pd;
    }
}

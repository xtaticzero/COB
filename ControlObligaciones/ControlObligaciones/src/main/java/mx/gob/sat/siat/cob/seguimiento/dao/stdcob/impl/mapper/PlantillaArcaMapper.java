package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;

import org.springframework.jdbc.core.RowMapper;

public class PlantillaArcaMapper implements RowMapper<PlantillaDocumento>{
    public PlantillaArcaMapper() {
        super();
    }

    @Override
    public PlantillaDocumento mapRow(ResultSet resultSet, int i) throws SQLException{
        PlantillaDocumento pd = new PlantillaDocumento();
        pd.setIdPlantilla(resultSet.getInt("idplantilla"));
        pd.setDescripcionTipoDoc(resultSet.getString("nombretipodocumento"));
        pd.setIdTipoDocumento(resultSet.getInt("idtipodocumento"));
        pd.setDescripcion(resultSet.getString("descripcion"));
        pd.setDescripcionEtapaVig(resultSet.getString("nombredocumentoetapa"));
        pd.setIdEtapaVigilancia(resultSet.getInt("idetapavigilancia"));
        pd.setDescMedioEnvio(resultSet.getString("nombretipomedio"));
        pd.setIdMedioEnvio(resultSet.getInt("idtipomedio"));
        pd.setDescTipoFirma(resultSet.getString("nombrefirma"));
        pd.setIdTipoFirma(resultSet.getInt("idfirmatipo"));
        pd.setIdTipoDias(resultSet.getInt("dias"));
        pd.setIdTipoMotivo(resultSet.getString("descresolmotivo"));
        pd.setDescNivelEmision(resultSet.getString("nombre"));
        pd.setNivelEmision(resultSet.getInt("idnivelemision"));
        pd.setIdCargoAdministrativo(resultSet.getInt("idcargoadmtvo"));
        pd.setDescCargoAdmin(resultSet.getString("descripcionadmtvo"));
        
        

        return pd;
    }
}

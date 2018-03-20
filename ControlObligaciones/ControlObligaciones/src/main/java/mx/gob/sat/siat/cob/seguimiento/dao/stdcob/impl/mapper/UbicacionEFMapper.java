package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionEFDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author christian.ventura
 */
public class UbicacionEFMapper implements RowMapper<UbicacionEFDTO> {

    /**
     * 
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public UbicacionEFDTO mapRow(ResultSet rs, int i) throws SQLException {
        UbicacionEFDTO dato=new UbicacionEFDTO();
        dato.setClaveEntidadFed(rs.getInt("CLAVEENTIDADFED"));
        dato.setIdPersona(rs.getString("IDPERSONA"));
        dato.setCalle(rs.getString("CALLE"));
        dato.setNumeroExterior(rs.getString("NUMEROEXTERIOR"));
        dato.setNumeroInterior(rs.getString("NUMEROINTERIOR"));
        dato.setClaveColonia(rs.getString("CLAVECOLONIA"));
        dato.setDescripcionColonia(rs.getString("DESCRIPCIONCOLONIA"));
        dato.setClaveLocalidad(rs.getString("CLAVELOCALIDAD"));
        dato.setDescripcionLocalidad(rs.getString("DESCRIPCONLOCALIDAD"));
        dato.setEntreCalle1(rs.getString("ENTRECALLE1"));
        dato.setEntreCalle2(rs.getString("ENTRECALLE2"));
        dato.setReferenciaAdicionales(rs.getString("REFERENCIAADICIONALES"));
        dato.setCodigoPostal(rs.getString("CODIGOPOSTAL"));
        dato.setClaveMunicipio(rs.getString("CLAVEMUNICIPIO"));
        return dato;
    }

}

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class FirmaDocumentoAprobarMapper implements RowMapper<CadenaOriginal>{

    private String nombre;
    
    public FirmaDocumentoAprobarMapper() {
    }

    public FirmaDocumentoAprobarMapper(String nombre){
        this.nombre=nombre;
    }
    
    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public CadenaOriginal mapRow(ResultSet rs, int i) throws SQLException {
        CadenaOriginal documentoFirmar=new CadenaOriginal();
        documentoFirmar.setCadenaOriginal(rs.getString("precadenaoriginal")+nombre);
        return documentoFirmar;
    }
    
}

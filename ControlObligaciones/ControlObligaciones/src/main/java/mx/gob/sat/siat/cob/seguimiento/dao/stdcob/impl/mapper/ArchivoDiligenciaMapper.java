/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoDiligencia;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class ArchivoDiligenciaMapper implements RowMapper<ArchivoDiligencia>{
    
    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public ArchivoDiligencia mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArchivoDiligencia archivoDiligencia = new ArchivoDiligencia();
                
        archivoDiligencia.setIdEntidadFederativa(rs.getLong("IDENTIDADFEDERATIVA"));
        archivoDiligencia.setNombreArchivoCarga(rs.getString("NOMBREARCHIVOCARGA"));
        archivoDiligencia.setTotalRegistrosCarga(rs.getInt("TOTALREGISTROSARCHIVOCARGA"));
        archivoDiligencia.setTotalRegistrosProcesados(rs.getInt("TOTALREGISTROSPROCESADOS"));
        archivoDiligencia.setRutaArchivoResultado(rs.getString("RUTAARCHIVORESULTADO"));
        archivoDiligencia.setFechaCarga(rs.getDate("FECHACARGA"));
        archivoDiligencia.setFechaCargaStr(rs.getString("FECHACARGASTR"));
        
        return archivoDiligencia;
    }
}
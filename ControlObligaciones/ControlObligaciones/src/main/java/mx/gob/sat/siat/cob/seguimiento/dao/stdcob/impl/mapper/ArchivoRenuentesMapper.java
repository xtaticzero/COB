/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoRenuente;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Alex
 */
public class ArchivoRenuentesMapper implements RowMapper<ArchivoRenuente> {

    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public ArchivoRenuente mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArchivoRenuente archivoRenuente = new ArchivoRenuente();

        archivoRenuente.setIdCargaRenunetes(rs.getLong("IDCARGARENUENTES"));
        archivoRenuente.setUsuarioCarga(rs.getString("USUARIOCARGA"));
        archivoRenuente.setNumEmpleadoCarga(rs.getString("NUMEMPLEADOCARGA"));
        archivoRenuente.setNombreArchivoCarga(rs.getString("NOMBREARCHIVOCARGA"));
        archivoRenuente.setTotalRegistrosArchivoCarga(rs.getInt("TOTALREGISTROSARCHIVOCARGA"));
        archivoRenuente.setRutaArchivoResultado(rs.getString("rutaarchivoerrores"));
        archivoRenuente.setTotalRegistrosErrores(rs.getInt("TOTALREGISTROSERRORES"));
        archivoRenuente.setFechaCarga(rs.getDate("FECHACARGA"));
        archivoRenuente.setFechaCargaStr(rs.getString("FECHACARGASTR"));

        return archivoRenuente;
    }
}

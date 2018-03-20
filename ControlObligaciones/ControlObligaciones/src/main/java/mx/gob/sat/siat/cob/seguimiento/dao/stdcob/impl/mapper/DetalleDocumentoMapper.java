/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author root
 */
public class DetalleDocumentoMapper implements RowMapper<DetalleDocumento> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DetalleDocumento mapRow(ResultSet rs, int i) throws SQLException {
        DetalleDocumento detalleDocumento = new DetalleDocumento();
        detalleDocumento.setNumeroControl(rs.getString("NUMEROCONTROL"));
        detalleDocumento.setClaveIcep(rs.getLong("CLAVEICEP"));
        detalleDocumento.setIdObligacion(rs.getInt("IDOBLIGACION"));
        detalleDocumento.setIdEjercicio(rs.getString("EJERCICIO"));
        detalleDocumento.setIdPeriodo(rs.getString("IDPERIODO"));
        detalleDocumento.setFechaVencimiento(rs.getString("FECHAVENCIMIENTOOBLIGACION"));
        detalleDocumento.setFechaCumplimiento(rs.getDate("FECHACUMPLIMIENTO"));
        detalleDocumento.setIdPeriodicidad(rs.getString("IDPERIODICIDAD"));
        detalleDocumento.setIdSituacionIcep(rs.getInt("IDSITUACIONICEP"));
        detalleDocumento.setIdRegimen(rs.getString("IDREGIMEN"));
        detalleDocumento.setImporteCargo(rs.getDouble("IMPORTEPAGAR"));
        detalleDocumento.setIdTipoDeclaracion(rs.getInt("IDTIPODECLARACION"));
        detalleDocumento.setTieneMultaExtemporaneidad(rs.getInt("TIENEMULTAEXTEMPORANEIDAD"));
        detalleDocumento.setTieneMultaComplementaria(rs.getInt("TIENEMULTACOMPLEMENTARIA"));
        if (Utilerias.existeColumna(rs, "IDCUMPLIMIENTO") && rs.getString("IDCUMPLIMIENTO")!=null) {
            detalleDocumento.setIdCumplimiento(new BigInteger(rs.getString("IDCUMPLIMIENTO")));
        }
        if (Utilerias.existeColumna(rs, "IDCUMPLIMIENTOCOMPL") && rs.getString("IDCUMPLIMIENTOCOMPL")!=null) {
            detalleDocumento.setIdCumplimientoCompl(new BigInteger(rs.getString("IDCUMPLIMIENTOCOMPL")));
        }        
        detalleDocumento.setFechaPresentacionCompl(rs.getDate("FECHAPRESENTACIONCOMPL"));
        detalleDocumento.setFechaBaja(rs.getDate("FECHABAJA"));
        return detalleDocumento;
    }

}

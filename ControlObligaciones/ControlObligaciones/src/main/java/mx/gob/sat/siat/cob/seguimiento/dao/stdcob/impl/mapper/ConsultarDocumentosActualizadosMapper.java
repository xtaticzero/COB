package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.log4j.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;


public class ConsultarDocumentosActualizadosMapper implements RowMapper{

    private Logger log = Logger.getLogger(DocumentoMapper.class.getName());
    
    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Documento mapRow(ResultSet resultSet, int i) throws SQLException {
        Documento doc = new Documento();
        try{
        doc.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
        doc.setRfc(resultSet.getString("RFC"));
        doc.setBoid(resultSet.getString("BOID"));
        doc.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));
        doc.getVigilancia().setIdVigilancia(resultSet.getLong("IDVIGILANCIA"));
        doc.getVigilancia().setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));
        doc.setConsideraRenuencia(resultSet.getInt("CONSIDERARENUENCIA"));
        doc.setEsUltimoGenerado(1);
        doc.setIdAdmonLocal(resultSet.getString("IDADMONLOCAL"));
        if (resultSet.getString("FECHANOTIFICACION") != null) {
            doc.setFechaNotificacion(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHANOTIFICACION")));
        }
        if (resultSet.getString("FECHAIMPRESION") != null) {
            doc.setFechaImpresion(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHAIMPRESION")));
        }
        if (resultSet.getString("FECHAVENCIMIENTODOCTO") != null) {
            doc.setFechaVencimiento(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHAVENCIMIENTODOCTO")));
        }
        if (resultSet.getString("FECHANOLOCALIZADOCONTRIBUYENTE") != null) {
            doc.setFechaNoLocalizadoContribuyente(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHANOLOCALIZADOCONTRIBUYENTE")));
        }
        if (resultSet.getString("FECHANOTRABAJADO") != null) {
             doc.setFechaNoTrabajado(resultSet.getString("FECHANOTRABAJADO"));
        }
        } catch (DataAccessException e) {
            log.error("error DocumentoMapper actualizarSeguimientoDocumento ", e);
            throw new SQLException(e);
        } catch (ParseException pe) {
            log.error("error DocumentoMapper Parceo de Fechas ", pe);
            throw new SQLException(pe);
        }
        return doc;
    }
}

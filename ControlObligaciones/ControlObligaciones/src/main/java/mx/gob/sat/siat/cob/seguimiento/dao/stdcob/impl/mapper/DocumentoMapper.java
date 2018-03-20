package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

public class DocumentoMapper implements RowMapper<Documento> {

    private final Logger log = Logger.getLogger(DocumentoMapper.class);

    @Override
    public Documento mapRow(ResultSet resultSet, int i) throws SQLException {

        Documento documento = new Documento();

        documento.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
        documento.setNumeroControlPadre(resultSet.getString("NUMEROCONTROLPADRE"));
        documento.setRfc(resultSet.getString("RFC"));
        documento.setBoid(resultSet.getString("BOID"));
        documento.setIdAdmonLocal(resultSet.getString("IDADMONLOCAL"));
        documento.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));
        documento.getVigilancia().setIdVigilancia(resultSet.getLong("IDVIGILANCIA"));
        documento.setEsUltimoGenerado(resultSet.getInt("ESULTIMOGENERADO"));
        documento.setUltimoEstado(resultSet.getInt("ULTIMOESTADO"));
        documento.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));
        documento.setConsideraRenuencia(resultSet.getInt("CONSIDERARENUENCIA"));
        documento.setDateNoTrabajado(resultSet.getDate("FECHANOTRABAJADO"));

        try {
            documento.getVigilancia().setFechaCargaArchivos(resultSet.getDate("FECHACARGAARCHIVOS"));
        } catch (SQLException e) {
            log.error("No se pudo mapear correctamente el campo fechaCargarArchivos", e);
        }
        try {
            documento.setIdentidadFederativa(resultSet.getInt("identidadfederativa"));
        } catch (SQLException e) {
            log.error("No se pudo mapear correctamente el campo identidadfederativa", e);
        }
        try {
            documento.getVigilancia().setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));
        } catch (SQLException e) {
            log.error("No se pudo mapear correctamente el campo IDTIPODOCUMENTO", e);
        }
        try {
            documento.getVigilancia().setIdTipoMedio(resultSet.getInt("idtipomedio"));
        } catch (SQLException e) {
            log.error("No se pudo mapear correctamente el campo idtipomedio", e);
        }
        try {
            if (resultSet.getString("FECHANOTIFICACION") != null) {
                documento.setFechaNotificacion(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHANOTIFICACION")));
            }
            if (resultSet.getString("FECHAIMPRESION") != null) {
                documento.setFechaImpresion(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHAIMPRESION")));
            }
            if (resultSet.getString("FECHAVENCIMIENTODOCTO") != null) {
                documento.setFechaVencimiento(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHAVENCIMIENTODOCTO")));
            }
            if (resultSet.getString("FECHANOLOCALIZADOCONTRIBUYENTE") != null) {
                documento.setFechaNoLocalizadoContribuyente(Utilerias.formatearFechaAAAAMMDDHHMM(resultSet.getString("FECHANOLOCALIZADOCONTRIBUYENTE")));
            }
        } catch (ParseException e) {
            log.error("No se pudieron mapear correctamente los campos de fecha-->FECHANOTIFICACION,FECHAIMPRESION,FECHAVENCIMIENTODOCTO,FECHANOLOCALIZADOCONTRIBUYENTE", e);
        }

        return documento;
    }
}

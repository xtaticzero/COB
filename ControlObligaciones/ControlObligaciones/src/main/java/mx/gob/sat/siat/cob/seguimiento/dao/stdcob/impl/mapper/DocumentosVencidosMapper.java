package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import org.springframework.jdbc.core.RowMapper;


public class DocumentosVencidosMapper implements RowMapper<Documento> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Documento mapRow(ResultSet resultSet, int i) throws SQLException {

            Documento documento = new Documento();
            documento.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
            documento.setNumeroControlPadre(resultSet.getString("NUMEROCONTROLPADRE"));
            documento.setFechaNotificacion(resultSet.getDate("FECHANOTIFICACION"));
            documento.setFechaImpresion(resultSet.getDate("FECHAIMPRESION"));
            documento.setRfc(resultSet.getString("RFC"));
            documento.setBoid(resultSet.getString("BOID"));
            documento.setFechaVencimiento(resultSet.getDate("FECHAVENCIMIENTODOCTO"));
            documento.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));
            documento.setFechaNoLocalizadoContribuyente(resultSet.getDate("FECHANOLOCALIZADOCONTRIBUYENTE"));
            documento.getVigilancia().setIdVigilancia(resultSet.getLong("IDVIGILANCIA"));
            documento.setEsUltimoGenerado(resultSet.getInt("ESULTIMOGENERADO"));
            documento.setUltimoEstado(resultSet.getInt("ULTIMOESTADO"));
            documento.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));
            documento.setConsideraRenuencia(resultSet.getInt("CONSIDERARENUENCIA"));
            documento.getVigilancia().setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));

        return documento;
    }
}

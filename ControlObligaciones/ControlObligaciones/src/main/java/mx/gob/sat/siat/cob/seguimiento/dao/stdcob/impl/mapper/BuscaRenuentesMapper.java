/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author juan
 */
public class BuscaRenuentesMapper implements RowMapper<BuscaRenuentes> {

    @Override
    public BuscaRenuentes mapRow(ResultSet rs, int i) throws SQLException {

        BuscaRenuentes buscaRenuentes = new BuscaRenuentes();
        buscaRenuentes.setIdBusquedaRenuente(rs.getLong("IDBUSQUEDARENUENTES"));
        buscaRenuentes.setFechaBusqueda(rs.getDate("FECHAGUARDABUSQUEDA"));
        buscaRenuentes.setRutaArchivo(rs.getString("RUTAARCHIVOGENERADO"));
        buscaRenuentes.setEsFinalizada(rs.getInt("ESFINALIZADA"));
        buscaRenuentes.setEstadoDocumento(rs.getInt("IDESTADODOCTO"));
        buscaRenuentes.setFechaBusquedaStr(rs.getString("FECHAGUARDABUSQUEDASTR"));

        setTipoDoctos(buscaRenuentes, rs);
        setObligaciones(buscaRenuentes, rs);

        return buscaRenuentes;
    }

    private void setTipoDoctos(BuscaRenuentes buscaRenuentes, ResultSet rs) throws SQLException {
        String idTiposDoctos = rs.getString("LISTAIDTIPODOCUMENTO");
        buscaRenuentes.setSelectedTipoDocumento(Utilerias.cadenaToList(idTiposDoctos));
    }

    private void setObligaciones(BuscaRenuentes buscaRenuentes, ResultSet rs) throws SQLException {
        String idObligaciones = rs.getString("LISTAIDOBLIGACION");
        buscaRenuentes.setSelectedObligaciones(Utilerias.cadenaToList(idObligaciones));
    }

}

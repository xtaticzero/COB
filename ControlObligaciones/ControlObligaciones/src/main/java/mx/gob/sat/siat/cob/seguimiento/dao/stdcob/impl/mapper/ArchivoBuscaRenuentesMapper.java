/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author alex
 */
public class ArchivoBuscaRenuentesMapper implements RowMapper<BuscaRenuentes> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public BuscaRenuentes mapRow(ResultSet resultSet, int i) throws SQLException {
        BuscaRenuentes buscaRenuentes = new BuscaRenuentes();
        buscaRenuentes.setIdBusquedaRenuente(resultSet.getLong("IDBUSQUEDARENUENTES"));
        buscaRenuentes.setFechaBusqueda(resultSet.getDate("FECHAGUARDABUSQUEDA"));
        buscaRenuentes.setFechaBusquedaStr(resultSet.getString("FECHAGUARDABUSQUEDASTR"));
        buscaRenuentes.setRutaArchivo(resultSet.getString("RUTAARCHIVOGENERADO"));
        buscaRenuentes.setEsFinalizada(resultSet.getInt("ESFINALIZADA"));
        
        if (buscaRenuentes.getEsFinalizada().equals(ConstantsCatalogos.UNO)) {
            buscaRenuentes.setEsFinalizadaDesc("Si");
        } else {
            buscaRenuentes.setEsFinalizadaDesc("No");
        }
        
        return buscaRenuentes;
    }
}

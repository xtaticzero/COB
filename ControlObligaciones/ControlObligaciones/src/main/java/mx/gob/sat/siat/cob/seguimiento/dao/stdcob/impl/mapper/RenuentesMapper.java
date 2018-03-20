package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Renuentes;
import org.springframework.jdbc.core.RowMapper;

public class RenuentesMapper implements RowMapper<Renuentes> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Renuentes mapRow(ResultSet resultSet, int i) throws SQLException {
        Renuentes renuente = new Renuentes();
        renuente.setClave(resultSet.getString("CLAVEOBLIGACION"));
        renuente.setConcepto(resultSet.getString("DESCRIPCIONOBLIGACION"));
        renuente.setEjercicio(resultSet.getString("EJERCICIO"));
        renuente.setEstado(resultSet.getString("ESTADO"));
        renuente.setFechaCumplimiento(resultSet.getString("FECHACUMPLIMIENTO"));
        renuente.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
        renuente.setPeriodicidad(resultSet.getString("PERIODICIDAD"));
        renuente.setPeriodo(resultSet.getString("PERIODO"));
        renuente.setRfc(resultSet.getString("RFC"));
        renuente.setRegimenDesc(resultSet.getString("DESCRIPCIONREGIMEN"));
        renuente.setRegimen(resultSet.getString("CLAVEREGIMEN"));

        return renuente;
    }
}

package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.PagoIcepOmiso;
import org.springframework.jdbc.core.RowMapper;

public class HistoricoPagosICEPsOmisosMapper implements RowMapper<PagoIcepOmiso> {

    @Override
    public PagoIcepOmiso mapRow(ResultSet rs, int i) throws SQLException {

        PagoIcepOmiso pago = new PagoIcepOmiso();
                pago.setBoid(rs.getString("BOID"));
                pago.setClaveIcepHistPago(rs.getLong("CLAVEICEPHISTORICOPAGO"));
                pago.setEjercicio(rs.getInt("EJERCICIO"));
                pago.setPeriodo(rs.getInt("PERIODO"));
                pago.setPeriodicidad(rs.getString("PERIODICIDAD"));
                pago.setIdentificadorCumplimiento(rs.getLong("IDENTIFICADORCUMPLIMIENTO"));
                pago.setImporteaCargo(rs.getLong("IMPORTEACARGO"));
                pago.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
                pago.setTipoDeclaracion(rs.getInt("TIPODECLARACION"));
                pago.setNumOperacion(rs.getLong("NUMOPERACION"));
        
        return pago;
    }
}

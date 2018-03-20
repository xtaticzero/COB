package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;

public class MultaMontoMapper implements RowMapper<MultaMonto> {

    /**
     *
     */
    public MultaMontoMapper() {
        super();
    }

    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public MultaMonto mapRow(ResultSet rs, int rowNum) throws SQLException {
        MultaMonto oblisancion = new MultaMonto();

        oblisancion.setSancion(rs.getString("SANCION"));
        oblisancion.setInfraccion(rs.getString("INFRACCION"));
        oblisancion.setFechaInicio(rs.getDate("FECHAINICIO"));
        if(rs.getDate("FECHAINICIO")!=null){
            oblisancion.setFechaInicioStr(Utilerias.formatearFechaDDMMYYYY(rs.getDate("FECHAINICIO")));
        }
        oblisancion.setFechaFin(rs.getDate("FECHAFIN"));
        if(rs.getDate("FECHAFIN")!=null){
            oblisancion.setFechaFinStr(Utilerias.formatearFechaDDMMYYYY(rs.getDate("FECHAFIN")));
        }
        oblisancion.setIdObligacion(rs.getLong("IDOBLIGACION"));
        oblisancion.setOrden(rs.getLong("ORDEN"));
        oblisancion.setIdOblisancion(rs.getLong("IDMULTAMONTO"));
        oblisancion.setIdObligacionDescr(rs.getString("CONCEPTO"));
        oblisancion.setMotivacion(rs.getString("MOTIVACION"));
        oblisancion.setMonto(rs.getLong("MONTO"));
        oblisancion.setDescripcionMonto(rs.getString("DESCRIPCIONMONTO"));
     
        oblisancion.setResolMotivo(rs.getString("CONSTANTERESOLMOTIVO"));
        oblisancion.setResolMotivoDescr(rs.getString("RESOLMOTIVO"));
        oblisancion.setDescripcionLarga(rs.getString("DESCRIPCIONLARGA"));
        oblisancion.setDescripcionCorta(rs.getString("DESCRIPCIONCORTA"));

        return oblisancion;
    }
}

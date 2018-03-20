package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;

import org.springframework.jdbc.core.RowMapper;


public class ObligacionMapper implements RowMapper<Obligacion>{
    /**
     *
     */
    public ObligacionMapper() {
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
    public Obligacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Obligacion obligacion = new Obligacion();
        ValorBooleano valorBooleano = new ValorBooleano();
                
        obligacion.setIdObligacion(rs.getLong("IDOBLIGACION"));
        obligacion.setConcepto(rs.getString("CONCEPTO"));
        obligacion.setDescripcion(rs.getString("DESCRIPCION"));
        obligacion.setFechaInicio(rs.getDate("FECHAINICIO"));
        obligacion.setFechaFin(rs.getDate("FECHAFIN"));
        obligacion.setOrden(rs.getLong("ORDEN"));
        valorBooleano.setIdValorBooleano(rs.getLong("IDVALORBOOLEAN"));
        valorBooleano.setNombre(rs.getString("NOMBRE"));
        obligacion.setValorBooleano(valorBooleano);
        obligacion.setDescrObliIdc(rs.getString("DESCROBLIG"));
        obligacion.setDescrObliCompleta(rs.getString("DESCROBLICOMPLETA"));
        if (rs.getDate("FECHAFIN")==null){
            obligacion.setFechaFinStr("1");
        }else {
            obligacion.setFechaFinStr("0");
        }
         
        return obligacion;
    }
}

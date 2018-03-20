package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Tabla;
import org.springframework.jdbc.core.RowMapper;


public class TablaMapper implements RowMapper<Tabla>{
    /**
     *
     */
    public TablaMapper() {
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
    public Tabla mapRow(ResultSet rs, int rowNum) throws SQLException {
              Tabla tabla = new Tabla();
                                      
               tabla.setIdObligacion(rs.getString("IDOBLIGACION"));
               tabla.setIdRegimen(rs.getInt("IDREGIMEN"));
               tabla.setConcepto(rs.getString("CONCEPTO"));
               tabla.setDecripcion(rs.getString("DESCRIPCION"));
               tabla.setFundamentoLegal(rs.getString("FUNDAMENTOLEGAL"));
               tabla.setFechaInicio(rs.getDate("FECHAINICIO"));
               tabla.setFechaFin(rs.getDate("FECHAFIN"));
               tabla.setOrden(rs.getLong("ORDEN"));
               
              return tabla;
           }
    }

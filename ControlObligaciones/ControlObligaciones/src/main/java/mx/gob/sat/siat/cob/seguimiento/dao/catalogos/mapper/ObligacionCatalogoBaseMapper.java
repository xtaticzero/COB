/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Marco Murakami
 */
public class ObligacionCatalogoBaseMapper implements RowMapper<List<Obligacion>>{

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public List<Obligacion> mapRow(ResultSet rs, int i) throws SQLException {
        List<Obligacion> obligaciones = new ArrayList<Obligacion>();
        
        do{
            Obligacion obligacion = new Obligacion();
            ValorBooleano valorBooleano = new ValorBooleano();
            
            obligacion.setIdObligacion(rs.getLong("IDOBLIGACION"));
            obligacion.setConcepto(rs.getString("CONCEPTO"));
            valorBooleano.setIdValorBooleano(rs.getLong("APLICARENUENTES"));
            obligacion.setValorBooleano(valorBooleano);
            
            obligaciones.add(obligacion);
        }
        while(rs.next());
        return obligaciones;
    }
}

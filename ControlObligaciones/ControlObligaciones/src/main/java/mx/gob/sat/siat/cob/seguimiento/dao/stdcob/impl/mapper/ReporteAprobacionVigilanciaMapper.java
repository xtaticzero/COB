/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAprobacionVigilancia;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class ReporteAprobacionVigilanciaMapper implements RowMapper<ReporteAprobacionVigilancia>{

    private enum Columnas {NO_PROCESADO,ANULADO,CANCELADO,CUMPLIDO,CUMPLIDO_SIN_NOTIFICAR}
    
    @Override
    public ReporteAprobacionVigilancia mapRow(ResultSet rs, int i) throws SQLException {
        ReporteAprobacionVigilancia reporte=new ReporteAprobacionVigilancia();
        reporte.setExcluidosPorResponsable(rs.getLong(Columnas.NO_PROCESADO.toString()));
        Long canceladoReq=rs.getLong(Columnas.CANCELADO.toString());
        if(canceladoReq!=null){
            reporte.setCancelados(canceladoReq);
        }
        Long canceladoCarta=rs.getLong(Columnas.ANULADO.toString());
        if(canceladoCarta!=null){
            reporte.setCancelados(reporte.getCancelados()+canceladoCarta);
        }
        Long cumplidoReq=rs.getLong(Columnas.CUMPLIDO_SIN_NOTIFICAR.toString());
        if(cumplidoReq!=null){
            reporte.setCumplidos(cumplidoReq);
        }
        Long cumplidoCarta=rs.getLong(Columnas.CUMPLIDO.toString());
        if(cumplidoCarta!=null){
            reporte.setCumplidos(reporte.getCumplidos()+cumplidoCarta);
        }
        return reporte;
    }
    
}

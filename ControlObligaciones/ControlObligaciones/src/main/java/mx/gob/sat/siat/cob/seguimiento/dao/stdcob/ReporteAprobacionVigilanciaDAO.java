/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ReporteAprobacionVigilanciaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteVigilanciaAprobada;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAprobacionVigilancia;

/**
 *
 * @author root
 */
public interface ReporteAprobacionVigilanciaDAO extends ReporteAprobacionVigilanciaSQL {

    ReporteAprobacionVigilancia consultarCifrasDeVigilancia(long idVigilancia, String idLocalidad);

    List<DetalleReporteVigilanciaAprobada> detallesReporte(long idVigilancia, String idLocalidad);
    
    List<DetalleReporteVigilanciaAprobada> detallesExcluidosReporte(long idVigilancia, String idLocalidad);

    List<DetalleReporteVigilanciaAprobada> detallesCanceladosReporte(long idVigilancia, String idLocalidad);

    List<DetalleReporteVigilanciaAprobada> detallesCumplidosReporte(long idVigilancia, String idLocalidad);
}

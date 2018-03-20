/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author juan
 */
public interface PeriodoBusquedaSQL {

    String INSERT_PERIODO_BUSQUEDA = "insert into sgtt_periodoBusqda \n"
            + " (IDBUSQUEDARENUENTES, IDPERIODICIDAD, IDPERIODOINICIAL, IDPERIODOFINAL, \n"
            + " IDEJERCICIOINICIAL, IDEJERCICIOFINAL)\n"
            + " values(?,?,?,?,?,?)";
    String SELECT_X_ID_BUSQUEDA = "select * from sgtt_periodoBusqda\n"
            + "where idbusquedarenuentes=?";
}

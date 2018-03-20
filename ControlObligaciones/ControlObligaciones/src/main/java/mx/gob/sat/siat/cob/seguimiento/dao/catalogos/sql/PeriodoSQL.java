package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

/**
 *
 * @author root
 */
public interface PeriodoSQL {
    
     String PERIODOS_X_PERIODICIDAD =
                "select periodicidad.idperiodo, periodicidad.idperiodicidad, periodicidad.descripcionperiodo\n " +
                "from sgta_periodicidad periodicidad\n " +
                "where periodicidad.idperiodicidad = ?";

}

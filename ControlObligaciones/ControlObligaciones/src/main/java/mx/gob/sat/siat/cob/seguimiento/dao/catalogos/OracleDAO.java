package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.Date;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface OracleDAO {
    Date consultarFechaActual()throws SeguimientoDAOException;
}

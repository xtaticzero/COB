package mx.gob.sat.siat.cob.seguimiento.dao.dbd2;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dto.db2.PagoIcepOmiso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface HistoricoPagosIcepDAO {
    List<PagoIcepOmiso> consultarHistoricoPagosICEPsOmisos (BigDecimal boid, int claveIcep);
    
    Map<String,String> guardarHistoricoPagosLiquidacion(List<PagoIcepOmiso> pagos)throws SeguimientoDAOException ;
}

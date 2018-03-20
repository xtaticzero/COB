package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAlSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAl;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;

/**
 *
 * @author root
 */
public interface VigilanciaAlDAO extends VigilanciaAlSQL {

    /**
     *
     * @param numeroCarga
     * @return
     */
    VigilanciaAl buscarVigilancia(String numeroCarga);

    /**
     *
     * @param numeroCarga
     * @return
     */
    List<VigilanciaAdministracionLocal> buscarDetalleVigilancia(String numeroCarga);    
    
    /**
     *
     * @param numeroCarga
     * @return
     */
    List<VigilanciaEntidadFederativa> buscarDetalleVigilanciaEF(String numeroCarga);

    /**
     *
     * @param idVigilancia
     * @return
     */
    ReporteVigilancia consultaReporteVigilancia(String idVigilancia);
    
    /**
     *
     * @param idVigilancia
     * @return
     * @throws SQLException
     */
    List<AlscDTO> buscarALSCVigilancia(Integer idVigilancia) throws SQLException;
    
    /**
     *
     * @param idVigilancia
     * @return
     * @throws SQLException
     */
    List<AlscDTO> buscarEFVigilancia(Integer idVigilancia) throws SQLException;
}

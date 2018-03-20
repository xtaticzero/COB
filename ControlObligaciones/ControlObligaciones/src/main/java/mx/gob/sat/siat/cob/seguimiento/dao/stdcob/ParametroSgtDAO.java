package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface ParametroSgtDAO {
    void guardarParametrosLiquidacion(String montoMinimo, String montoTotal);
    void actualizarVigenciaLiquidacion();
    List<ParametrosSgtDTO> obtenerParametrosSgt() throws SeguimientoDAOException;
    void guardarParametroSgt(ParametrosSgtDTO param) throws SeguimientoDAOException;
    void actualizarParametroSgt(ParametrosSgtDTO param) throws SeguimientoDAOException;
    void actualizarVigenciaParametroSgt(int idParametro)  throws SeguimientoDAOException;
    List<ParametrosSgtDTO> obtenerParametrosSgt(String idParametro);
    ParametrosSgtDTO obtenerParametroSgtPorIdParametro(int idParametro);
    List<ParametrosSgtDTO> obtenerParametrosVigentesSgt();
}

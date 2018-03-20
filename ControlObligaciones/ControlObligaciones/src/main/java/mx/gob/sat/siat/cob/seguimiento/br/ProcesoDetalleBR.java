package mx.gob.sat.siat.cob.seguimiento.br;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ProcesoDetalle;
import mx.gob.sat.siat.cob.seguimiento.exception.BusinessException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface ProcesoDetalleBR {
    void validarCreacion(ProcesoDetalle procesoDetalle)throws BusinessException,SeguimientoDAOException;
    void validarEdicion(ProcesoDetalle procesoDetalle)throws BusinessException,SeguimientoDAOException;
}
